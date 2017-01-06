package com.lovejjfg.zhifou.view;

import com.lovejjfg.zhifou.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Joe on 2016/12/21.
 * Email lovejjfg@gmail.com
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,sdk = 21)
public class ListStoryTest {

    private ListStory listStory;

    @Before
    public void setUp() throws Exception {
    }

    //    @Test
//    public void testHelloWorld() throws Exception {
//        TextView helloWorldTextView = (TextView) activity.findViewById(R.id.textview_id);
//        assertThat(helloWorldTextView.getText().toString()).isEqualTo("Hello World!");
//    }
    @Test
    public void testListStory() {
        listStory = Robolectric.setupActivity(ListStory.class);
        assertNotNull(listStory);
        assertEquals("Activity的标题", listStory.getTitle());
    }

    @Test
    public void onClick() throws Exception {
    }

    @Test
    public void onItemClick() throws Exception {

    }

}