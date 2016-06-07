package com.lovejjfg.zhifou.data.model;

import java.util.List;


/**
 * Created by 张俊 on 2016/3/14.
 */
public class BatchBean {

    /**
     * body : {}
     * method : POST
     * path : /1/classes/TableName
     */

    private List<RequestsEntity> requests;

    public void setRequests(List<RequestsEntity> requests) {
        this.requests = requests;
    }

    public List<RequestsEntity> getRequests() {
        return requests;
    }
    //插入的bean
    /*
    "method": "POST",
    "path": "/1/classes/TableName",
     */
    public static  class RequestsEntity<T> {
        private T body;
        private String method;
        private String path;

        public void setBody(T body) {
            this.body = body;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public T getBody() {
            return body;
        }

        public String getMethod() {
            return method;
        }

        public String getPath() {
            return path;
        }

        public static class BodyEntity {
            public BodyEntity(String name, String mobile) {
                this.name = name;
                this.mobile = mobile;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            private String name;
            private String nickName;
            private String birthday;
            private String mobile;
        }
    }
}
