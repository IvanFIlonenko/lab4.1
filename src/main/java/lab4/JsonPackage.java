/*
 * Copyright (C) 2009-2019 Lightbend Inc. <https://www.lightbend.com>
 */

package lab4;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class JsonPackage {
    private final int packageId;
    private final String jsScript;
    private final String functionName;
    private final Test[] tests;

    @JsonCreator
    public JsonPackage(@JsonProperty("packageId") String packageId,
                       @JsonProperty("jsScript") String jsScript,
                       @JsonProperty("functionName") String functionName,
                       @JsonProperty("tests") Test[] tests) {
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

    public void writeCheck(int index){tests[index].}
}
