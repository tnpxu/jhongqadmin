package com.example.ize.jhongqadmin.RequestRepository;

/**
 * Created by tnpxu on 23/7/2558.
 */
public class AddQueueAnonymous {
//             ResName" : "BonChon",
//            //anonymouse UserId = 0
//            "UserId" : "0",
//            "Nickname" : "benz",
//            "QueueType" : "A",
//            "ResBranch" : "a",
//            //get it by from totalqueuewait
//            "CurrentUserQueue" : 0

    private String resname;
    private String resbranch;
    private int userid;
    private String queuetype;
    private int currentuserqueue;
    private String nickname;

    public AddQueueAnonymous(String resname, String resbranch, int userid,
                             String queuetype, int currentuserqueue,String nickname) {
        this.resname = resname;
        this.resbranch = resbranch;
        this.userid = userid;
        this.queuetype = queuetype;
        this.currentuserqueue = currentuserqueue;
        this.nickname = nickname;
    }

    public String getResname() {
        return this.resname;
    }
    public String getResbranch() {
        return this.resbranch;
    }
    public int getUserid() {
        return this.userid;
    }
    public String getQueuetype() {
        return this.queuetype;
    }
    public int getCurrentuserqueue() {
        return this.currentuserqueue;
    }
    public String getNickname() {
        return this.nickname;
    }
}
