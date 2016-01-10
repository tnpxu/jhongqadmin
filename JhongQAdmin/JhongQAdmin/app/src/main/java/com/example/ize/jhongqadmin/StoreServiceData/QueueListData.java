package com.example.ize.jhongqadmin.StoreServiceData;

/**
 * Created by tnpxu on 17/7/2558.
 */
public class QueueListData {
//            "UserId": 6,
//            "QueueNum": 1,
//            "QueueType": "A",
//            "Nickname": "benz",
//            "QueueCheck": false,
//            "Status": "standby"

    private int userid;
    private int queuenum;
    private String queuetype;
    private String nickname;
    private boolean queuecheck;
    private String status;

    public QueueListData() {

    }
    public QueueListData(int userid,int queuenum,String queuetype,String nickname,boolean queuecheck,String status) {
        this.userid = userid;
        this.queuenum = queuenum;
        this.queuetype = queuetype;
        this.nickname = nickname;
        this.queuecheck = queuecheck;
        this.status = status;
    }

    public int getUserid() {
        return this.userid;
    }
    public int getQueuenum() {
        return this.queuenum;
    }
    public String getQueuetype() {
        return this.queuetype;
    }
    public String getNickname() {
        return this.nickname;
    }
    public boolean getQueuecheck() {
        return this.queuecheck;
    }
    public String getStatus() {
        return this.getStatus();
    }
}
