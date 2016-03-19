package com.lovejjfg.zhifou.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by 张俊 on 2016/2/21.
 */
public class DailyStories {
    /*[
    {
        "content": {
            "dailyTitle": "再遇到天价虾类似纠纷，大部分时候 110 可能真管不了",
            "isMulti": false,
            "questionTitle": "消费者在利益受损害时，如何通过工商执法机构维权？",
            "questionTitleList": [],
            "questionUrl": "http://www.zhihu.com/question/36701739",
            "questionUrlList": [],
            "thumbnailUrl": "http://pic1.zhimg.com/da727748ea7d5fcedb3501f34c73adb4.jpg"
        },
        "date": "20151027"
    },
    {
        "content": {
            "dailyTitle": "119、911 等紧急电话号码是怎么确定的？",
            "isMulti": false,
            "questionTitle": "110、119、911、999等世界各地紧急电话号码的由来是怎样的？",
            "questionTitleList": [],
            "questionUrl": "http://www.zhihu.com/question/20193507",
            "questionUrlList": [],
            "thumbnailUrl": "http://pic2.zhimg.com/eb40548bba417b8c0fdd4a3b59d1050d.jpg"
        },
        "date": "20150918"
    },
    {
        "content": {
            "dailyTitle": "什么情况可以拨打 110？",
            "isMulti": false,
            "questionTitle": "什么情况可以拨打 110？",
            "questionTitleList": [],
            "questionUrl": "http://www.zhihu.com/question/27811600",
            "questionUrlList": [],
            "thumbnailUrl": "http://pic2.zhimg.com/3e89846eed733c3ed4730965a5610784.jpg"
        },
        "date": "20150305"
    },
    {
        "content": {
            "dailyTitle": "为什么中国大陆使用 220V 交流电，而有的国家却使用 110V ？",
            "isMulti": false,
            "questionTitle": "为什么中国使用220V交流电，而有的国家却使用110V？",
            "questionTitleList": [],
            "questionUrl": "http://www.zhihu.com/question/19684486",
            "questionUrlList": [],
            "thumbnailUrl": "http://pic2.zhimg.com/e32b739cd83de76590071abc9a6646ea.jpg"
        },
        "date": "20150122"
    },
    {
        "content": {
            "dailyTitle": "派出所的人说，有时打 110 真不如打那一长串号码方便",
            "isMulti": false,
            "questionTitle": "为什么很多地方报警电话派出所都不推荐打110而推荐打一个又长又难记的本地固定电话？",
            "questionTitleList": [],
            "questionUrl": "http://www.zhihu.com/question/21249525",
            "questionUrlList": [],
            "thumbnailUrl": "http://pic4.zhimg.com/0bdcbfdb8ca366ee4533d0cbfce6a3b6.jpg"
        },
        "date": "20141208"
    },
    {
        "content": {
            "dailyTitle": "110 平房子装修预算 15 万，没经验，该怎么做？",
            "isMulti": false,
            "questionTitle": "110 平房子装修预算 15 万，没经验，该怎么做？",
            "questionTitleList": [],
            "questionUrl": "http://www.zhihu.com/question/26039287",
            "questionUrlList": [],
            "thumbnailUrl": "http://pic1.zhimg.com/eb70931053a72810664b721430fcf627.jpg"
        },
        "date": "20141027"
    },
    {
        "content": {
            "dailyTitle": "为什么手机没有置入 SIM 卡也能打 110 等紧急电话？",
            "isMulti": false,
            "questionTitle": "为什么手机没有置入 SIM 卡也能打 110 等紧急电话？",
            "questionTitleList": [],
            "questionUrl": "http://www.zhihu.com/question/23212125",
            "questionUrlList": [],
            "thumbnailUrl": "http://p2.zhimg.com/19/14/19142ff91f9125ada8dd4e53e9b06580.jpg"
        },
        "date": "20140402"
    }
]*/
    @Expose
    private String date;
    @Expose
    private List<Story> stories;
    @Expose
    @SerializedName("top_stories")
    private List<Story> topStories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<Story> getTopStories() {
        return topStories;
    }

    public void setTopStories(List<Story> topStories) {
        this.topStories = topStories;
    }
}
