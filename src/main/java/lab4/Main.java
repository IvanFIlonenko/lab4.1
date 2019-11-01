/*
 * Copyright (C) 2009-2019 Lightbend Inc. <https://www.lightbend.com>
 */

package lab4;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
//#imports
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.marshallers.jackson.Jackson;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
//#imports
import akka.http.javadsl.server.AllDirectives;
import akka.http.javadsl.server.Route;
import akka.pattern.Patterns;
import akka.stream.ActorMaterializer;
import akka.stream.javadsl.Flow;
import scala.concurrent.Future;

import java.io.IOException;
//#imports
import java.util.Map;
import java.util.concurrent.CompletableFuture;
//#imports
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

//#imports
import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.unmarshalling.StringUnmarshallers.INTEGER;

//#imports

public class Main extends AllDirectives {

    static ActorRef manager;


    //#unmarshall
    private Route appRoute() {
        return concat(
                get(
                        () -> parameter("packageId", (packageId) ->
                                {
                                    Future<Object> result = Patterns.ask(manager, new GetResult(Integer.parseInt(packageId)), 5000);
                                    return completeOKWithFuture(result, Jackson.marshaller());
                                }
                        )
                ),
                post(
                        () -> entity(Jackson.unmarshaller(JsonPackage.class),
                                message -> {
                                    manager.tell(message, ActorRef.noSender());
                                    return complete("");
                                }
                        )
                )
        );
    }

    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("routes");
        manager = system.actorOf(Props.create(Manager.class));

        final Http http = Http.get(system);
        final ActorMaterializer materializer = ActorMaterializer.create(system);

        //In order to access all directives we need an instance where the routes are define.
        Main app = new Main();

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = app.appRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding
                .thenCompose(ServerBinding::unbind) // trigger unbinding from the port
                .thenAccept(unbound -> system.terminate()); // and shutdown when done
    }
}
