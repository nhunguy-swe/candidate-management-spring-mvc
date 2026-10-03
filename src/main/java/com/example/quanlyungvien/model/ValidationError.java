package com.example.quanlyungvien.model;

public class ValidationError {
    private int lineNum;
    private String errorMsg;

    public ValidationError(int lineNum, String errorMsg) {
        this.lineNum = lineNum;
        this.errorMsg = errorMsg;
    }
    public int getLineNum() { return lineNum; }
    public String getErrorMsg() { return errorMsg; }
}
