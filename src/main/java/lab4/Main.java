package lab4;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.concurrent.Future;
import java.util.concurrent.CompletionStage;


public class Main extends AllDirectives {

    private static final String SUCCESS_MESSAGE  = "You sent successfully!";
    private static final String PACKAGE_ID = "packageId";
    private static final String ROUTES = "routes";
    private static final String LOCALHOST = "localhost";
    private static final int PORT = 8080;
    private static final String SERVER_ONLINE_MESSAGE = "Server online at http://localhost:8080/\nPress RETURN to stop...";

    static ActorRef manager;


    private Route appRoute() {
        return concat(
                get(
                        () -> parameter(PACKAGE_ID, (packageId) ->
                                {
                                    Future<Object> result = Patterns.ask(manager, new GetResult(Integer.parseInt(packageId)), 5000);
                                    return completeOKWithFuture(result, Jackson.marshaller());
                                }
                        )
                ),
                post(
                        () -> entity(Jackson.unmarshaller(FunctionPackage.class),
                                message -> {
                                    manager.tell(message, ActorRef.noSender());
                                    return complete(SUCCESS_MESSAGE);
                                }
                        )
                )
        );
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create(ROUTES);
        manager = system.actorOf(Props.create(Manager.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        Main app = new Main();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.appRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost(LOCALHOST, PORT), materializer);

        System.out.println(SERVER_ONLINE_MESSAGE);
        System.in.read();

        binding
                .thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }
}
