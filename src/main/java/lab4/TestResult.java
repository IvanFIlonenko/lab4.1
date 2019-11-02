package lab4;

public class TestResult {
    private final String testName;
    private final String expectedResult;
    private final Object[] params;
    private String result;
    private String check;
    private int packageId;

    public TestResult(String testName, String expectedResult, Object[] params, String result, String check, int packageId){
        this.testName = testName;
        this.expectedResult = expectedResult;
        this.params = params;
        this.result = result;
        this.check = check;
        this.packageId =
    }
}
