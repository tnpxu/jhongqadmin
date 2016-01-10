package com.example.ize.jhongqadmin.RequestRepository;

/**
 * Created by tnpxu on 16/7/2558.
 */
public class StartQueueSystemRepository {
    private String resname;
    private String resbranch;

    public StartQueueSystemRepository() {

    }

    public StartQueueSystemRepository(String resname, String resbranch) {
        this.resname = resname;
        this.resbranch = resbranch;
    }

    public String getResname() {
        return this.resname;
    }

    public String getResbranch() {
        return this.resbranch;
    }
}
