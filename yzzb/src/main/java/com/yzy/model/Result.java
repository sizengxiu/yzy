package com.yzy.model;

/**
 * @user szx
 * @date 2021/5/18 20:57
 */
public class Result {

    //状态码
    private int code;
    //消息
    private String message;
    //是否成功
    private boolean success=true;
    //要返回的数据
    private Object data;


    public Result() {
    }

    public Result(int code, String message, boolean success, Object data) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 获取错误返回结果实体
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 21:02
     */
    public  static Result getErrorResult(int code,String message){
        Result result=new Result();
        result.code=code;
        result.message=message;
        result.success=false;
        return result;
    }
    /**
     * 获取错误返回结果实体
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 21:02
     */
    public  static Result getErrorResult(String message){
        Result result=new Result();
        result.code=500;
        result.message=message;
        result.success=false;
        return result;
    }
    /**
     * 获取成功返回结果实体
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 21:02
     */
    public  static Result getSuccessResult(String message,Object data){
        Result result=new Result();
        result.message=message;
        result.data=data;
        return result;
    }
    /**
     * 获取成功返回结果实体
     * @param:
     * @return:
     * @auther: szx
     * @date: 2021/5/18 21:02
     */
    public  static Result getSuccessResult(Object data){
        Result result=new Result();
        result.data=data;
        return result;
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
