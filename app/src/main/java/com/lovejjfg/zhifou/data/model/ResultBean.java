package com.lovejjfg.zhifou.data.model;

import java.util.List;

/**
 * Created by 张俊 on 2016/3/14.
 */
public class ResultBean {


    @Override
    public String toString() {
        return "ResultBean{" +
                "results=" + results +
                '}';
    }

    /**
     * birthday : 1964-07-28
     * createdAt : 2016-03-14 21:42:24
     * mobile : 13228260785
     * name : 张大姑
     * objectId : 7ffe0cd0b0
     * updatedAt : 2016-03-14 21:42:24
     */

    private List<ResultsEntity> results;

    public void setResults(List<ResultsEntity> results) {
        this.results = results;
    }

    public List<ResultsEntity> getResults() {
        return results;
    }

    public static class ResultsEntity {
        private String birthday;
        private String createdAt;
        private String mobile;
        private String name;
        private String objectId;
        private String updatedAt;

        public String getWorkNumber() {
            return workNumber;
        }

        public void setWorkNumber(String workNumber) {
            this.workNumber = workNumber;
        }

        public String getWorkMobile() {
            return workMobile;
        }

        public void setWorkMobile(String workMobile) {
            this.workMobile = workMobile;
        }

        public String getHomeNumber() {
            return homeNumber;
        }

        public void setHomeNumber(String homeNumber) {
            this.homeNumber = homeNumber;
        }

        private String workNumber;
        private String workMobile;
        private String homeNumber;

        @Override
        public String toString() {
            return "ResultsEntity{" +
                    "birthday='" + birthday + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", mobile='" + mobile + '\'' +
                    ", name='" + name + '\'' +
                    ", objectId='" + objectId + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    '}';
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getBirthday() {
            return birthday;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getMobile() {
            return mobile;
        }

        public String getName() {
            return name;
        }

        public String getObjectId() {
            return objectId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
