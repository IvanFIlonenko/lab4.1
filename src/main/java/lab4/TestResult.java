package lab4;

public class TestResult {
    private final String testName;
    private final String expectedResult;
    private final Object[] params;
    private final String result;
    private final String check;

    public TestResult(String testName, String expectedResult, Object[] params, String result, String check){
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
        this.result = result;
        this.check = check;
    }

    public String getTestName(){return testName;}

    public String getExpectedResult(){return expectedResult;}

    public Object[] getParams(){return params;}

    public String getResult(){return result;}

    public String getCheck(){return  check;}
}
