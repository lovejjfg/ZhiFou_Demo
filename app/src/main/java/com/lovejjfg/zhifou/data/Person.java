package com.lovejjfg.zhifou.data;

/**
 * Created by 张俊 on 2016/3/13.
 */
public class Person {
    private String name;
    private String birthday;

    //    @Keep
    public Person(String birthday, String name) {
        this.birthday = birthday;
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "birthday='" + birthday + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
