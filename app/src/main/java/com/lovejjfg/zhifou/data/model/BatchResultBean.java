package com.lovejjfg.zhifou.data.model;


/**
 * Created by zhangjun on 2016-03-19.
 */
public class BatchResultBean {

    @Override
    public String toString() {
        return "BatchResultBean{" +
                "success=" + success +
                '}';
    }

    /**
     * createdAt : YYYY-mm-dd HH:ii:ss
     * msg : ok
     * objectId : d746635d0b
     * updatedAt : YYYY-mm-dd HH:ii:ss
     */

    private SuccessEntity success;

    public void setSuccess(SuccessEntity success) {
        this.success = success;
    }

    public SuccessEntity getSuccess() {
        return success;
    }

    public static class SuccessEntity {
        private String createdAt;
        private String msg;
        private String objectId;
        private String updatedAt;

        @Override
        public String toString() {
            return "SuccessEntity{" +
                    "createdAt='" + createdAt + '\'' +
                    ", msg='" + msg + '\'' +
                    ", objectId='" + objectId + '\'' +
                    ", updatedAt='" + updatedAt + '\'' +
                    '}';
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getMsg() {
            return msg;
        }

        public String getObjectId() {
            return objectId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }
    }
}
