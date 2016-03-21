package com.lovejjfg.zhifou.data.model;

import java.util.List;

/**
 * Created by zhangjun on 2016-03-19.
 */
public class SearchResult {

    /**
     * dailyTitle : 再遇到天价虾类似纠纷，大部分时候 110 可能真管不了
     * isMulti : false
     * questionTitle : 消费者在利益受损害时，如何通过工商执法机构维权？
     * questionTitleList : []
     * questionUrl : http://www.zhihu.com/question/36701739
     * questionUrlList : []
     * thumbnailUrl : http://pic1.zhimg.com/da727748ea7d5fcedb3501f34c73adb4.jpg
     */

    private ContentEntity content;
    /**
     * content : {"dailyTitle":"再遇到天价虾类似纠纷，大部分时候 110 可能真管不了","isMulti":false,"questionTitle":"消费者在利益受损害时，如何通过工商执法机构维权？","questionTitleList":[],"questionUrl":"http://www.zhihu.com/question/36701739","questionUrlList":[],"thumbnailUrl":"http://pic1.zhimg.com/da727748ea7d5fcedb3501f34c73adb4.jpg"}
     * date : 20151027
     */

    private String date;

    public ContentEntity getContent() {
        return content;
    }

    public void setContent(ContentEntity content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public static class ContentEntity {
        private String dailyTitle;
        private boolean isMulti;
        private String questionTitle;
        private String questionUrl;
        private String thumbnailUrl;
        private List<String> questionTitleList;
        private List<String> questionUrlList;

        public String getDailyTitle() {
            return dailyTitle;
        }

        public void setDailyTitle(String dailyTitle) {
            this.dailyTitle = dailyTitle;
        }

        public boolean isIsMulti() {
            return isMulti;
        }

        public void setIsMulti(boolean isMulti) {
            this.isMulti = isMulti;
        }

        public String getQuestionTitle() {
            return questionTitle;
        }

        public void setQuestionTitle(String questionTitle) {
            this.questionTitle = questionTitle;
        }

        public String getQuestionUrl() {
            return questionUrl;
        }

        public void setQuestionUrl(String questionUrl) {
            this.questionUrl = questionUrl;
        }

        public String getThumbnailUrl() {
            return thumbnailUrl;
        }

        public void setThumbnailUrl(String thumbnailUrl) {
            this.thumbnailUrl = thumbnailUrl;
        }

        public List<?> getQuestionTitleList() {
            return questionTitleList;
        }

        public void setQuestionTitleList(List<String> questionTitleList) {
            this.questionTitleList = questionTitleList;
        }

        public List<?> getQuestionUrlList() {
            return questionUrlList;
        }

        public void setQuestionUrlList(List<String> questionUrlList) {
            this.questionUrlList = questionUrlList;
        }
    }
}
