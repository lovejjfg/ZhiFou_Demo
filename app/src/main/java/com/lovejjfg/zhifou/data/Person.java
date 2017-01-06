package com.lovejjfg.zhifou.data;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by 张俊 on 2016/3/13.
 */
public class Person  extends AbsPerson{
    private String name;
    private String birthday;
    @Inject
    public Person(@Named("birth") String birthday, @Named("name") String name) {
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
