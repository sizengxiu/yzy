package com.yzy.exception;

/**
 * 业务自定义异常
 * @user szx
 * @date 2021/5/18 20:19
 */
public class BussinessException extends RuntimeException {
    //异常码
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public BussinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public BussinessException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return "BussinessException{" +
                "code=" + code +",message="+getMessage()+
                '}';
    }
}
