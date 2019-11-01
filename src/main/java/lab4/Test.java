package lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class Test {
    private final String testName;
    private final String expectedResult;
    private final Object[] params;
    private String result;
    private String check;

    @JsonCreator
    public Test(@JsonProperty("testName") String testName,
                @JsonProperty("expectedResult") String expectedResult,
                @JsonProperty("params") Object[] params){
        this.testName= testName;
        this.expectedResult = expectedResult;
        this.params = params;
        this.result = "NONE";
        this.check = "false";
    }

    public String getTestName(){
        return testName;
    }

    public String getExpectedResult(){
        return expectedResult;
    }

    public Object[] getParams(){
        return params;
    }

    public void writeResult(String result){
        this.result = result;
    }

    public String getResult(){return this.result;}

    public void writeCheck(){this.check = "true";}

    public String getCheck(){return this.check;}
}
