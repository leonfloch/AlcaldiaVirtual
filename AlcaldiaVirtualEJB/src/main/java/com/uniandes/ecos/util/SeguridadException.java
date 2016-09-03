package com.uniandes.ecos.util;

public class SeguridadException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    private String msg;

    /**
     *
     * @param msg
     */
    public SeguridadException(String msg) {
        this.msg = msg;
    }

    /**
     * @return the msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg the msg to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
