package com.example.ize.jhongqadmin;

import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.example.ize.jhongqadmin.RequestRepository.GetQueueListRepository;

/**
 * Created by tnpxu on 18/7/2558.
 */
public class AsynFlagmentCall extends AsyncTask<String, Void, String> {

    //this object will be used to be a callback object
    private ViewPager mViewPager;
    private int tabPosition;
    public GetQueueListRepository dataStoreA;

    public AsynFlagmentCall(GetQueueListRepository dataStoreA) {
        this.dataStoreA = dataStoreA;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... params) {
        String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/getqueuelist";
        ServiceTask serviceObject = new ServiceTask();
        return serviceObject.requestPostGetQueueListContent(realUrl, dataStoreA);
    }

    @Override
    protected void onPostExecute(String res) {

        //TODO save res to sharedPreferences

        mViewPager.setCurrentItem(tabPosition);
    }

    public void setCallBackObject(ViewPager _mViewPager,int _tabPosition){
        mViewPager = _mViewPager;
        tabPosition = _tabPosition;
    }
}
