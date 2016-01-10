package com.example.ize.jhongqadmin;

import android.app.Application;

import com.example.ize.jhongqadmin.StoreServiceData.Flag;
import com.example.ize.jhongqadmin.StoreServiceData.QueueListData;

import java.util.ArrayList;
import java.util.Observer;

/**
 * Created by tnpxu on 20/7/2558.
 */
public class MyApplication extends Application {

    public ArrayList<QueueListData> listadaptA = new ArrayList<>();
    public ArrayList<Integer> toTalQueueWaitA = new ArrayList<>();
//    public ArrayList<ObserverObject> ObservA = new ArrayList<>();

    public ArrayList<QueueListData> listadaptB = new ArrayList<>();
    public ArrayList<Integer> toTalQueueWaitB = new ArrayList<>();

    public ArrayList<QueueListData> listadaptC = new ArrayList<>();
    public ArrayList<Integer> toTalQueueWaitC = new ArrayList<>();

    public ArrayList<Flag> flagObserver = new ArrayList<>();

}