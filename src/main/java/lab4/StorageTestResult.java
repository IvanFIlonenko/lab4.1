package lab4;

public class StorageTestResult {
    private final int packageId;
    private final TestResult testResult;

    public StorageTestResult(int packageId, TestResult testResult){
        this.packageId = packageId;
        this.testResult = testResult;
    }

    public int getPackageId(){
        return packageId;
    }

    public TestResult getTestResult(){
        return testResult;
    }
}
