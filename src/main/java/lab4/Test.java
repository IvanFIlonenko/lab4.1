package lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class Test {
    private final String testName;
    private final float expectedResult;
    private final int[] params;
    private String result;

    @JsonCreator
    public Test(@JsonProperty("testName") String testName,
                @JsonProperty("expectedResult") String expectedResult,
                @JsonProperty("params") int[] params){
        this.testName= testName;
        this.expectedResult = Float.parseFloat(expectedResult);
        this.params = params;
        this.result = "";
    }

    public String getTestName(){
        return testName;
    }

    public float getExpectedResult(){
        return expectedResult;
    }

    public int[] getParams(){
        return params;
    }

    public void writeResult(String result){
        this.result = result;
    }
}
