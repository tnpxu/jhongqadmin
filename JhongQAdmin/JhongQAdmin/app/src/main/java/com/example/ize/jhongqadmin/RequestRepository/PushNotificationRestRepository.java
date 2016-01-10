package com.example.ize.jhongqadmin.RequestRepository;

/**
 * Created by tnpxu on 26/7/2558.
 */
public class PushNotificationRestRepository {
//            "ResName" : "",
//            "ResBranch" : "",
//            "QueueType" : ""
    private String resname;
    private String resbranch;
    private String queuetype;
    public PushNotificationRestRepository(String resname,String resbranch,String queuetype) {
        this.resname = resname;
        this.resbranch = resbranch;
        this.queuetype = queuetype;
    }

    public String getResname() {
        return this.resname;
    }
    public String getResbranch() {
        return this.resbranch;
    }
    public String getQueuetype() {
        return this.queuetype;
    }
}
