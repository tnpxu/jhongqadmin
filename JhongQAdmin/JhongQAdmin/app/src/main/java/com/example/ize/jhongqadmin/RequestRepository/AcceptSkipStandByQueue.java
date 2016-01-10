package com.example.ize.jhongqadmin.RequestRepository;

/**
 * Created by tnpxu on 17/7/2558.
 */
public class AcceptSkipStandByQueue {
    private int userid;
    private String resname;
    private String resbranch;
    private String queuetype;

    public AcceptSkipStandByQueue() {

    }

    public AcceptSkipStandByQueue(int userid, String resname, String resbranch, String queuetype) {
        this.userid = userid;
        this.resname = resname;
        this.resbranch = resbranch;
        this.queuetype = queuetype;
    }

    public int getUserid() {
        return this.userid;
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
