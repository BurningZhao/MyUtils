package com.zhao.test.bean;

import com.zhao.myutils.base.BaseModel;

/**
 * Description: User bean
 *
 * @author qingbo
 * @since 16/8/16
 */
public class User extends BaseModel {

    public static final int SEX_MAIL = 0;
    public static final int SEX_FEMAIL = 1;
    public static final int SEX_UNKNOW = 2;
    private static final long serialVersionUID = 1L;

    int sex;
    String head; //头像
    String name; //名字
    String phone; //电话号码
    String tag; //标签
    boolean starred; //星标

    /**
     * 默认构造方法，JSON等解析时必须要有
     */
    public User() {
        //default
    }

    public User(long id) {
        this(id, null);
    }

    public User(String name) {
        this(-1, name);
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean getStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    @Override
    public boolean isCorrect() {
        // 根据自己的需求决定，也可以直接 return true
        return id > 0;// && StringUtil.isNotEmpty(phone, true);
    }

    @Override
    public String toString() {
        return "姓名：" + name + "\n电话：" + phone;
    }
}
