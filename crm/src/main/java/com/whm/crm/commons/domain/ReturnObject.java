package com.whm.crm.commons.domain;

/**
 * 用于响应时 使用
 * @author 15718
 */
public class ReturnObject {

    /**
     * 判断是否成功   1成功  0失败
     */
    private String code;

    /**
     * 提示信息
     */
    private String message;

    /**
     *其他数据
     */
    private Object retData;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRetData() {
        return retData;
    }

    public void setRetData(Object retData) {
        this.retData = retData;
    }
}
