package com.yzy.model;

/**
 * @user szx
 * @date 2020/10/18 16:25
 */
public class Result {
    private int code;
    private boolean success;
    private String message;
    private Object data;

    public static Result getSuccessResult(Object data){
        Result result=getSuccessResult();
        result.data=data;
        return result;
    }
    public static Result getSuccessResult(){
        Result result=new Result();
        result.code=200;
        result.success=true;
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
