package com.alibaba.dubbo.rpc.protocol.resteasy;

import java.io.Serializable;

/**
 * 
 * @author sdcuike
 *
 *         Create At 2016年3月28日
 */
public class ModelResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String rescode;

    private String errcode;

    private String errmsg;
    private T data;
    private boolean success = true;

    public ModelResult() {

    }

    public String getRescode() {
        return rescode;
    }

    public void setRescode(String rescode) {
        this.rescode = rescode;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
        this.success = false;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
        this.success = false;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

}