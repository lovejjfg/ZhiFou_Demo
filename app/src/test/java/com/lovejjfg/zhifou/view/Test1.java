package com.lovejjfg.zhifou.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lovejjfg.zhifou.data.Person;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joe on 2016/12/21.
 * Email lovejjfg@gmail.com
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23, manifest = Config.NONE)
public class Test1 {


    @Before
    public void setUp() throws Exception {

    }

    public <T> void onBackPressed(List<T> list) throws Exception {
        Gson gson = new Gson();
        String str = gson.toJson(list);
        System.out.println(str);
        List<T> ps = gson.fromJson(str, new TypeToken<List<T>>() {
        }.getType());
        for (int i = 0; i < ps.size(); i++) {
            T p = ps.get(i);
            System.out.println(p.toString());
        }
    }

    @Test
    public void onClick() throws Exception {
        Person p = new Person("xxxxx", "ooooo");
        Person p1 = new Person("xxxxx1", "ooooo1");
        Person p2 = new Person("xxxxx2", "ooooo2");
        Person p3 = new Person("xxxxx3", "ooooo3");
        ArrayList<Person> list = new ArrayList<>();
        list.add(p);
        list.add(p1);
        list.add(p2);
        list.add(p3);
//        List<Themes> list = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Themes themes = new Themes();
//            list.add(themes);
//        }
        onBackPressed(list);
    }

    @Test
    public void onItemClick() throws Exception {

    }

}