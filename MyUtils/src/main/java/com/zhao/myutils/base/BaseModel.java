package com.zhao.myutils.base;

import java.io.Serializable;

/**
 * 基础Model
 * isCorrect可以用于BaseModel子类的数据校验
 * implements Serializable 是为了网络传输字节流转换
 */
public abstract class BaseModel implements Serializable {

    /**
     * default, 怎么设置子类都有warning
     */
    private static final long serialVersionUID = 1L;

    public long id;

    /**
     * 数据正确性校验
     *
     * @param data
     * @return boolean
     */
    public static boolean isCorrect(BaseModel data) {
        return data != null && data.isCorrect();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    /**
     * 数据正确性校验
     *
     * @return boolean
     */
    public abstract boolean isCorrect();

}
