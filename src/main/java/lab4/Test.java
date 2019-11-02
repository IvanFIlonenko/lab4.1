package lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

class Test {
    private static final String TEST_NAME = "testName";
    private static final String EXPECTED_RESULT = "expectedResult";
    private static final String PARAMS = "params";
    private static final String NONE = "NONE";
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    private final String testName;
    private final String expectedResult;
    private final Object[] params;
    private String result;
    private String check;

    @JsonCreator
    public Test(@JsonProperty(TEST_NAME) String testName,
                @JsonProperty(EXPECTED_RESULT) String expectedResult,
                @JsonProperty(PARAMS) Object[] params){
        this.testName= testName;
        this.expectedResult = expectedResult;
        this.params = params;
        this.result = NONE;
        this.check = FALSE;
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

    public void writeCheck(){this.check = TRUE;}

    public String getCheck(){return this.check;}
}
