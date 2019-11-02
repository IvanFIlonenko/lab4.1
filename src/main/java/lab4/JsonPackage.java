package lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class JsonPackage {
    private static final String PACKAGE_ID = "packageId";
    private static final String JS_SCRIPT = "jsScript";
    private static final String FUNCTION_NAME = "functionName";
    private static final String TESTS = "tests";
    private final int packageId;
    private final String jsScript;
    private final String functionName;
    private final Test[] tests;

    @JsonCreator
    public JsonPackage(@JsonProperty(PACKAGE_ID) String packageId,
                       @JsonProperty(JS_SCRIPT) String jsScript,
                       @JsonProperty(FUNCTION_NAME) String functionName,
                       @JsonProperty(TESTS) Test[] tests) {
        this.packageId = Integer.parseInt(packageId);
        this.jsScript= jsScript;
        this.functionName = functionName;
        this.tests = tests;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getJsScript() {
        return jsScript;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Test[] getTests() {
        return tests;
    }

    public void writeResult(int index, String result){
        tests[index].writeResult(result);
    }

    public void writeCheck(int index){tests[index].writeCheck();}
}
