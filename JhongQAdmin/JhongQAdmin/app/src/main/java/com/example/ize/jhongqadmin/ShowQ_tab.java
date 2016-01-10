package com.example.ize.jhongqadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ize.jhongqadmin.RequestRepository.AcceptSkipStandByQueue;
import com.example.ize.jhongqadmin.RequestRepository.AddQueueAnonymous;
import com.example.ize.jhongqadmin.RequestRepository.GetQueueListRepository;
import com.example.ize.jhongqadmin.RequestRepository.PushNotificationRestRepository;
import com.example.ize.jhongqadmin.RequestRepository.StartQueueSystemRepository;
import com.example.ize.jhongqadmin.StoreServiceData.QueueListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;


public class ShowQ_tab extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    public static String resName;
    public static String resBranch;
    public AcceptSkipStandByQueue dataStoreAcceptA;
    public AcceptSkipStandByQueue dataStoreAcceptB;
    public AcceptSkipStandByQueue dataStoreAcceptC;
    public AcceptSkipStandByQueue dataStoreSkipA;
    public AcceptSkipStandByQueue dataStoreSkipB;
    public AcceptSkipStandByQueue dataStoreSkipC;
    public ProgressDialog dialog;
    public GetQueueListRepository dataStoreA;
    public GetQueueListRepository dataStoreB;
    public GetQueueListRepository dataStoreC;
    public PushNotificationRestRepository dataStoreNotiA;
    public PushNotificationRestRepository dataStoreNotiB;
    public PushNotificationRestRepository dataStoreNotiC;
    public StartQueueSystemRepository dataStoreStop;

//    public GetQueueListRepository dataStoreA;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_q_tab);


        Intent intent = this.getIntent();
        resName = intent.getStringExtra("ResName");
        resBranch = intent.getStringExtra("ResBranch");






        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }

        /**************************** REFRESH AREA ********************************************/

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(3000);

                        dataStoreA = new GetQueueListRepository(resName,resBranch,"A");
                        /***********************************/
                        /******* SERVICE TASK AREA QUEUEA**********/
                        /***********************************/

                        AsyncTask<String, Void, String> asyncBackground2 = new AsyncTask<String, Void, String>() {

                            public View callbackV;

                            public View callBack(View v) {

                                return v;
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

//                                Toast.makeText(getApplicationContext(), res,
//                                        Toast.LENGTH_LONG).show();
                                try {

                                    MyApplication myApplication = ((MyApplication)getApplicationContext());
                                    ArrayList<QueueListData> listadapt = myApplication.listadaptA;
                                    listadapt.clear();

                                    JSONObject json = new JSONObject(res);

                                    ArrayList<Integer> totalqueuewait = myApplication.toTalQueueWaitA;
                                    totalqueuewait.clear();
                                    Integer qWait = new Integer(json.getInt("TotalQueueWait"));
                                    totalqueuewait.add(qWait);

                                    JSONArray jsonarr = json.getJSONArray("QueueList");

                                    for(int i=0;i<jsonarr.length();i++){

//                                "UserId": 6,
//                                "QueueNum": 1,
//                                "QueueType": "A",
//                                "Nickname": "benz",
//                                "QueueCheck": false,
//                                "Status": "standby"

                                        JSONObject obj = jsonarr.getJSONObject(i);
                                        int userid = obj.getInt("UserId");
                                        int queuenum = obj.getInt("QueueNum");
                                        String queuetype = obj.getString("QueueType");
                                        String nickname = obj.getString("Nickname");
                                        Boolean queuecheck = obj.getBoolean("QueueCheck");
                                        String status = obj.getString("Status");



                                        QueueListData temp = new QueueListData(userid,queuenum,queuetype,nickname,queuecheck,status);
                                        listadapt.add(temp);


                                    }


                                    dataStoreB = new GetQueueListRepository(resName,resBranch,"B");
                                    /***********************************/
                                    /******* SERVICE TASK AREA QUEUE B**********/
                                    /***********************************/

                                    AsyncTask<String, Void, String> asyncBackground3 = new AsyncTask<String, Void, String>() {

                                        public View callbackV;

                                        public View callBack(View v) {

                                            return v;
                                        }

                                        @Override
                                        protected void onPreExecute() {

                                        }

                                        @Override
                                        protected String doInBackground(String... params) {



                                            String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/getqueuelist";
                                            ServiceTask serviceObject = new ServiceTask();
                                            return serviceObject.requestPostGetQueueListContent(realUrl, dataStoreB);


                                        }

                                        @Override
                                        protected void onPostExecute(String res) {

//                                Toast.makeText(getApplicationContext(), res,
//                                        Toast.LENGTH_LONG).show();
                                            try {

                                                MyApplication myApplication = ((MyApplication)getApplicationContext());
                                                ArrayList<QueueListData> listadapt = myApplication.listadaptB;
                                                listadapt.clear();


                                                JSONObject json = new JSONObject(res);

                                                ArrayList<Integer> totalqueuewait = myApplication.toTalQueueWaitB;
                                                totalqueuewait.clear();
                                                Integer qWait = new Integer(json.getInt("TotalQueueWait"));
                                                totalqueuewait.add(qWait);

                                                JSONArray jsonarr = json.getJSONArray("QueueList");

                                                for(int i=0;i<jsonarr.length();i++){

//                                "UserId": 6,
//                                "QueueNum": 1,
//                                "QueueType": "A",
//                                "Nickname": "benz",
//                                "QueueCheck": false,
//                                "Status": "standby"

                                                    JSONObject obj = jsonarr.getJSONObject(i);
                                                    int userid = obj.getInt("UserId");
                                                    int queuenum = obj.getInt("QueueNum");
                                                    String queuetype = obj.getString("QueueType");
                                                    String nickname = obj.getString("Nickname");
                                                    Boolean queuecheck = obj.getBoolean("QueueCheck");
                                                    String status = obj.getString("Status");



                                                    QueueListData temp = new QueueListData(userid,queuenum,queuetype,nickname,queuecheck,status);
                                                    listadapt.add(temp);


                                                }


                                                dataStoreC = new GetQueueListRepository(resName,resBranch,"C");
                                                /***********************************/
                                                /******* SERVICE TASK AREA QUEUEC**********/
                                                /***********************************/

                                                AsyncTask<String, Void, String> asyncBackground4 = new AsyncTask<String, Void, String>() {

                                                    public View callbackV;

                                                    public View callBack(View v) {

                                                        return v;
                                                    }

                                                    @Override
                                                    protected void onPreExecute() {

                                                    }

                                                    @Override
                                                    protected String doInBackground(String... params) {



                                                        String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/getqueuelist";
                                                        ServiceTask serviceObject = new ServiceTask();
                                                        return serviceObject.requestPostGetQueueListContent(realUrl, dataStoreC);


                                                    }

                                                    @Override
                                                    protected void onPostExecute(String res) {

//                                Toast.makeText(getApplicationContext(), res,
//                                        Toast.LENGTH_LONG).show();
                                                        try {

                                                            MyApplication myApplication = ((MyApplication)getApplicationContext());
                                                            ArrayList<QueueListData> listadapt = myApplication.listadaptC;
                                                            listadapt.clear();


                                                            JSONObject json = new JSONObject(res);

                                                            ArrayList<Integer> totalqueuewait = myApplication.toTalQueueWaitC;
                                                            totalqueuewait.clear();
                                                            Integer qWait = new Integer(json.getInt("TotalQueueWait"));
                                                            totalqueuewait.add(qWait);

                                                            JSONArray jsonarr = json.getJSONArray("QueueList");

                                                            for(int i=0;i<jsonarr.length();i++){

//                                "UserId": 6,
//                                "QueueNum": 1,
//                                "QueueType": "A",
//                                "Nickname": "benz",
//                                "QueueCheck": false,
//                                "Status": "standby"

                                                                JSONObject obj = jsonarr.getJSONObject(i);
                                                                int userid = obj.getInt("UserId");
                                                                int queuenum = obj.getInt("QueueNum");
                                                                String queuetype = obj.getString("QueueType");
                                                                String nickname = obj.getString("Nickname");
                                                                Boolean queuecheck = obj.getBoolean("QueueCheck");
                                                                String status = obj.getString("Status");



                                                                QueueListData temp = new QueueListData(userid,queuenum,queuetype,nickname,queuecheck,status);
                                                                listadapt.add(temp);


                                                            }


                                                            /****************** refresh command **********/
                                                            Intent intent = getIntent();
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                            finish();
                                                            startActivity(intent);

                                                        } catch (JSONException e) {
                                                            // manage exceptions
//                                    Toast.makeText(getApplicationContext(),e.toString(),
//                                            Toast.LENGTH_LONG).show();
                                                        }

                                                    }

                                                };


                                                try {
                                                    if (Build.VERSION.SDK_INT >= 11)
                                                        asyncBackground4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                                    else
                                                        asyncBackground4.execute();
                                                } catch(Exception e) {

                                                }





                                                /*************************************************************************/

                                            } catch (JSONException e) {
                                                // manage exceptions
//                                    Toast.makeText(getApplicationContext(),e.toString(),
//                                            Toast.LENGTH_LONG).show();
                                            }

                                        }

                                    };


                                    try {
                                        if (Build.VERSION.SDK_INT >= 11)
                                            asyncBackground3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                                        else
                                            asyncBackground3.execute();
                                    } catch(Exception e) {

                                    }





                                    /*************************************************************************/

                                } catch (JSONException e) {
                                    // manage exceptions
//                                    Toast.makeText(getApplicationContext(),e.toString(),
//                                            Toast.LENGTH_LONG).show();
                                }

                            }

                        };


                        try {
                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackground2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackground2.execute();
                        } catch(Exception e) {

                        }





                        /*************************************************************************/







                    } catch (Exception e) {

                    }
                }
            }});
        //DDOS!!!
        //thread.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_q_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        /*int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/


//ActionMenu Click
        switch (item.getItemId()) {

            // action with ID action_PowerOff was selected
            case R.id.action_power:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                builder.setTitle("ปิดระบบ");
                builder.setMessage("คุณต้องการปิดระบบใช่หรือไม่?");
                builder.setPositiveButton("ยืนยัน", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {


                        dataStoreStop = new StartQueueSystemRepository(resName,resBranch);
                        /***********************************/
                        /******* SERVICE TASK AREA STOP**********/
                        /***********************************/

                        AsyncTask<String, Void, String> asyncBackground4 = new AsyncTask<String, Void, String>() {


                            @Override
                            protected void onPreExecute() {

                            }

                            @Override
                            protected String doInBackground(String... params) {



                                String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/stopqueuesystem";
                                ServiceTask serviceObject = new ServiceTask();
                                return serviceObject.requestPostStartQueueSystemContent(realUrl, dataStoreStop);


                            }

                            @Override
                            protected void onPostExecute(String res) {

                                dialog.dismiss();
                                finish();

                            }

                        };


                        try {
                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackground4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackground4.execute();
                        } catch(Exception e) {

                        }





                        /*************************************************************************/
                    }
                });
                builder.setNegativeButton("ยกเลิก", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

                break;

            // ActionMenu Refresh
            case R.id.ac_refresh:
                Toast.makeText(this, "Selected Refresh", Toast.LENGTH_SHORT)
                        .show();
                break;

            default:
                break;
        }

        return true;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            //Call FragmentPage
            switch (position){
                case 0:
                    return Q_A_Fragment.newInstance("","",resName,resBranch,"A");
                case 1:
                    return Q_B_Fragment.newInstance("","",resName,resBranch,"B");
                case 2:
                    return Q_C_Fragment.newInstance("","",resName,resBranch,"C");

            }
            return null;

        }

        @Override
        public int getCount() {

            // Show 3 total pages.
            return 3;
        }

        //Set Title Tab name
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    //BntAcceptA
    public void BtnAcceptQA(View view){
//        String msg = "Select AcceptQA";
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<QueueListData> listadapt = myApplication.listadaptA;
        if(!(listadapt.isEmpty())) {
            QueueListData standByQueue = listadapt.get(0);

            dataStoreAcceptA = new AcceptSkipStandByQueue(standByQueue.getUserid(), resName, resBranch, "A");
            /***********************************/
            /******* SERVICE TASK AREA **********/
            /***********************************/

            AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(ShowQ_tab.this, "Loading", "Please wait");
                }

                @Override
                protected String doInBackground(String... params) {


                    String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/acceptqueue";
                    ServiceTask serviceObject = new ServiceTask();
                    return serviceObject.requestPostAcceptQueueContent(realUrl, dataStoreAcceptA);


                }

                @Override
                protected void onPostExecute(String res) {

                    try {
                        // store to sharedpreference
                        JSONObject json = new JSONObject(res);
                        Boolean success = json.getBoolean("Success");


                        if (success) {

                            dataStoreNotiA = new PushNotificationRestRepository( resName, resBranch, "A");
                            /***********************************/
                            /******* SERVICE TASK AREA NOTI**********/
                            /***********************************/

                            AsyncTask<String, Void, String> asyncBackgroundNoti = new AsyncTask<String, Void, String>() {
                                @Override
                                protected void onPreExecute() {

                                }

                                @Override
                                protected String doInBackground(String... params) {


                                    String realUrl = "http://jongqservice.azurewebsites.net/api/notification/pushnotificationrest";
                                    ServiceTask serviceObject = new ServiceTask();
                                    return serviceObject.requestPostPushNotificationRestContent(realUrl, dataStoreNotiA);


                                }

                                @Override
                                protected void onPostExecute(String res) {

                                    try {
                                        JSONObject json = new JSONObject(res);

                                        dialog.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                                        builder1.setMessage("Success");
                                        builder1.setCancelable(true);
                                        builder1.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });


                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();

                                    } catch (JSONException e) {
                                        // manage exceptions
                                    }

                                }

                            };


                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackgroundNoti.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackgroundNoti.execute();


                            /*************************************************************************/




                            /****************** DETACH ATTACH FRAGMENT ************/


                        } else {

                            dialog.dismiss();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                            builder1.setMessage("Error");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }


                    } catch (JSONException e) {
                        // manage exceptions
                    }

                }

            };


            if (Build.VERSION.SDK_INT >= 11)
                asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                asyncBackground.execute();


            /*************************************************************************/


        }
    }

    //BntSkipA
    public void BtnSkipQA(View view){
//        String msg = "Select SkipQA";
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<QueueListData> listadapt = myApplication.listadaptA;
        if(!(listadapt.isEmpty())) {
            QueueListData standByQueue = listadapt.get(0);

            dataStoreSkipA = new AcceptSkipStandByQueue(standByQueue.getUserid(), resName, resBranch, "A");
            /***********************************/
            /******* SERVICE TASK AREA **********/
            /***********************************/

            AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(ShowQ_tab.this, "Loading", "Please wait");
                }

                @Override
                protected String doInBackground(String... params) {


                    String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/skipqueue";
                    ServiceTask serviceObject = new ServiceTask();
                    return serviceObject.requestPostSkipQueueContent(realUrl, dataStoreSkipA);


                }

                @Override
                protected void onPostExecute(String res) {

                    try {
                        // store to sharedpreference
                        JSONObject json = new JSONObject(res);
                        Boolean success = json.getBoolean("Success");


                        if (success) {


                            dataStoreNotiA = new PushNotificationRestRepository( resName, resBranch, "A");
                            /***********************************/
                            /******* SERVICE TASK AREA NOTI**********/
                            /***********************************/

                            AsyncTask<String, Void, String> asyncBackgroundNoti = new AsyncTask<String, Void, String>() {
                                @Override
                                protected void onPreExecute() {

                                }

                                @Override
                                protected String doInBackground(String... params) {


                                    String realUrl = "http://jongqservice.azurewebsites.net/api/notification/pushnotificationrest";
                                    ServiceTask serviceObject = new ServiceTask();
                                    return serviceObject.requestPostPushNotificationRestContent(realUrl, dataStoreNotiA);


                                }

                                @Override
                                protected void onPostExecute(String res) {

                                    try {
                                        JSONObject json = new JSONObject(res);
                                        dialog.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                                        builder1.setMessage("Success");
                                        builder1.setCancelable(true);
                                        builder1.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });


                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();


                                    } catch (JSONException e) {
                                        // manage exceptions
                                    }

                                }

                            };


                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackgroundNoti.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackgroundNoti.execute();


                            /*************************************************************************/




                        } else {

                            dialog.dismiss();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                            builder1.setMessage("Error");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }


                    } catch (JSONException e) {
                        // manage exceptions
                    }

                }

            };


            if (Build.VERSION.SDK_INT >= 11)
                asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                asyncBackground.execute();


            /*************************************************************************/


        }
    }
    //BntAcceptB
    public void BtnAcceptQB(View view){
//        String msg = "Select AcceptQB";
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<QueueListData> listadapt = myApplication.listadaptB;
        if(!(listadapt.isEmpty())) {
            QueueListData standByQueue = listadapt.get(0);

            dataStoreAcceptB = new AcceptSkipStandByQueue(standByQueue.getUserid(), resName, resBranch, "B");
            /***********************************/
            /******* SERVICE TASK AREA **********/
            /***********************************/

            AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(ShowQ_tab.this, "Loading", "Please wait");
                }

                @Override
                protected String doInBackground(String... params) {


                    String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/acceptqueue";
                    ServiceTask serviceObject = new ServiceTask();
                    return serviceObject.requestPostAcceptQueueContent(realUrl, dataStoreAcceptB);


                }

                @Override
                protected void onPostExecute(String res) {

                    try {
                        // store to sharedpreference
                        JSONObject json = new JSONObject(res);
                        Boolean success = json.getBoolean("Success");


                        if (success) {

                            dataStoreNotiB = new PushNotificationRestRepository( resName, resBranch, "B");
                            /***********************************/
                            /******* SERVICE TASK AREA NOTI**********/
                            /***********************************/

                            AsyncTask<String, Void, String> asyncBackgroundNoti = new AsyncTask<String, Void, String>() {
                                @Override
                                protected void onPreExecute() {

                                }

                                @Override
                                protected String doInBackground(String... params) {


                                    String realUrl = "http://jongqservice.azurewebsites.net/api/notification/pushnotificationrest";
                                    ServiceTask serviceObject = new ServiceTask();
                                    return serviceObject.requestPostPushNotificationRestContent(realUrl, dataStoreNotiB);


                                }

                                @Override
                                protected void onPostExecute(String res) {

                                    try {
                                        JSONObject json = new JSONObject(res);

                                        dialog.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                                        builder1.setMessage("Success");
                                        builder1.setCancelable(true);
                                        builder1.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });


                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();



                                    } catch (JSONException e) {
                                        // manage exceptions
                                    }

                                }

                            };


                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackgroundNoti.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackgroundNoti.execute();


                            /*************************************************************************/





                        } else {

                            dialog.dismiss();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                            builder1.setMessage("Error");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }


                    } catch (JSONException e) {
                        // manage exceptions
                    }

                }

            };


            if (Build.VERSION.SDK_INT >= 11)
                asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                asyncBackground.execute();


            /*************************************************************************/


        }
    }

    //BntSkipB
    public void BtnSkipQB(View view){
//        String msg = "Select SkipQB";
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<QueueListData> listadapt = myApplication.listadaptB;
        if(!(listadapt.isEmpty())) {
            QueueListData standByQueue = listadapt.get(0);

            dataStoreSkipB = new AcceptSkipStandByQueue(standByQueue.getUserid(), resName, resBranch, "B");
            /***********************************/
            /******* SERVICE TASK AREA **********/
            /***********************************/

            AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(ShowQ_tab.this, "Loading", "Please wait");
                }

                @Override
                protected String doInBackground(String... params) {


                    String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/skipqueue";
                    ServiceTask serviceObject = new ServiceTask();
                    return serviceObject.requestPostSkipQueueContent(realUrl, dataStoreSkipB);


                }

                @Override
                protected void onPostExecute(String res) {

                    try {
                        // store to sharedpreference
                        JSONObject json = new JSONObject(res);
                        Boolean success = json.getBoolean("Success");


                        if (success) {

                            dataStoreNotiB = new PushNotificationRestRepository( resName, resBranch, "B");
                            /***********************************/
                            /******* SERVICE TASK AREA NOTI**********/
                            /***********************************/

                            AsyncTask<String, Void, String> asyncBackgroundNoti = new AsyncTask<String, Void, String>() {
                                @Override
                                protected void onPreExecute() {

                                }

                                @Override
                                protected String doInBackground(String... params) {


                                    String realUrl = "http://jongqservice.azurewebsites.net/api/notification/pushnotificationrest";
                                    ServiceTask serviceObject = new ServiceTask();
                                    return serviceObject.requestPostPushNotificationRestContent(realUrl, dataStoreNotiB);


                                }

                                @Override
                                protected void onPostExecute(String res) {

                                    try {
                                        JSONObject json = new JSONObject(res);
                                        dialog.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                                        builder1.setMessage("Success");
                                        builder1.setCancelable(true);
                                        builder1.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });


                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();


                                    } catch (JSONException e) {
                                        // manage exceptions
                                    }

                                }

                            };


                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackgroundNoti.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackgroundNoti.execute();


                            /*************************************************************************/




                        } else {

                            dialog.dismiss();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                            builder1.setMessage("Error");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }


                    } catch (JSONException e) {
                        // manage exceptions
                    }

                }

            };


            if (Build.VERSION.SDK_INT >= 11)
                asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                asyncBackground.execute();


            /*************************************************************************/


        }
    }
    //BntAcceptC
    public void BtnAcceptQC(View view){
//        String msg = "Select AcceptQC";
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<QueueListData> listadapt = myApplication.listadaptC;
        if(!(listadapt.isEmpty())) {
            QueueListData standByQueue = listadapt.get(0);

            dataStoreAcceptC = new AcceptSkipStandByQueue(standByQueue.getUserid(), resName, resBranch, "C");
            /***********************************/
            /******* SERVICE TASK AREA **********/
            /***********************************/

            AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(ShowQ_tab.this, "Loading", "Please wait");
                }

                @Override
                protected String doInBackground(String... params) {


                    String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/acceptqueue";
                    ServiceTask serviceObject = new ServiceTask();
                    return serviceObject.requestPostAcceptQueueContent(realUrl, dataStoreAcceptC);


                }

                @Override
                protected void onPostExecute(String res) {

                    try {
                        // store to sharedpreference
                        JSONObject json = new JSONObject(res);
                        Boolean success = json.getBoolean("Success");


                        if (success) {

                            dataStoreNotiC = new PushNotificationRestRepository( resName, resBranch, "C");
                            /***********************************/
                            /******* SERVICE TASK AREA NOTI**********/
                            /***********************************/

                            AsyncTask<String, Void, String> asyncBackgroundNoti = new AsyncTask<String, Void, String>() {
                                @Override
                                protected void onPreExecute() {

                                }

                                @Override
                                protected String doInBackground(String... params) {


                                    String realUrl = "http://jongqservice.azurewebsites.net/api/notification/pushnotificationrest";
                                    ServiceTask serviceObject = new ServiceTask();
                                    return serviceObject.requestPostPushNotificationRestContent(realUrl, dataStoreNotiC);


                                }

                                @Override
                                protected void onPostExecute(String res) {

                                    try {
                                        JSONObject json = new JSONObject(res);

                                        dialog.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                                        builder1.setMessage("Success");
                                        builder1.setCancelable(true);
                                        builder1.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });


                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();

                                    } catch (JSONException e) {
                                        // manage exceptions
                                    }

                                }

                            };


                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackgroundNoti.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackgroundNoti.execute();


                            /*************************************************************************/




                        } else {

                            dialog.dismiss();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                            builder1.setMessage("Error");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }


                    } catch (JSONException e) {
                        // manage exceptions
                    }

                }

            };


            if (Build.VERSION.SDK_INT >= 11)
                asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                asyncBackground.execute();


            /*************************************************************************/


        }
    }

    //BntSkipC
    public void BtnSkipQC(View view){
//        String msg = "Select SkipQC";
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<QueueListData> listadapt = myApplication.listadaptC;
        if(!(listadapt.isEmpty())) {
            QueueListData standByQueue = listadapt.get(0);

            dataStoreSkipC = new AcceptSkipStandByQueue(standByQueue.getUserid(), resName, resBranch, "C");
            /***********************************/
            /******* SERVICE TASK AREA **********/
            /***********************************/

            AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                @Override
                protected void onPreExecute() {
                    dialog = ProgressDialog.show(ShowQ_tab.this, "Loading", "Please wait");
                }

                @Override
                protected String doInBackground(String... params) {


                    String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/skipqueue";
                    ServiceTask serviceObject = new ServiceTask();
                    return serviceObject.requestPostAcceptQueueContent(realUrl, dataStoreSkipC);


                }

                @Override
                protected void onPostExecute(String res) {

                    try {
                        // store to sharedpreference
                        JSONObject json = new JSONObject(res);
                        Boolean success = json.getBoolean("Success");


                        if (success) {

                            dataStoreNotiC = new PushNotificationRestRepository( resName, resBranch, "C");
                            /***********************************/
                            /******* SERVICE TASK AREA NOTI**********/
                            /***********************************/

                            AsyncTask<String, Void, String> asyncBackgroundNoti = new AsyncTask<String, Void, String>() {
                                @Override
                                protected void onPreExecute() {

                                }

                                @Override
                                protected String doInBackground(String... params) {


                                    String realUrl = "http://jongqservice.azurewebsites.net/api/notification/pushnotificationrest";
                                    ServiceTask serviceObject = new ServiceTask();
                                    return serviceObject.requestPostPushNotificationRestContent(realUrl, dataStoreNotiC);


                                }

                                @Override
                                protected void onPostExecute(String res) {

                                    try {
                                        JSONObject json = new JSONObject(res);
                                        dialog.dismiss();
                                        AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                                        builder1.setMessage("Success");
                                        builder1.setCancelable(true);
                                        builder1.setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });


                                        AlertDialog alert11 = builder1.create();
                                        alert11.show();


                                    } catch (JSONException e) {
                                        // manage exceptions
                                    }

                                }

                            };


                            if (Build.VERSION.SDK_INT >= 11)
                                asyncBackgroundNoti.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                            else
                                asyncBackgroundNoti.execute();


                            /*************************************************************************/




                        } else {

                            dialog.dismiss();
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(ShowQ_tab.this);
                            builder1.setMessage("Error");
                            builder1.setCancelable(true);
                            builder1.setPositiveButton("Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });


                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                        }


                    } catch (JSONException e) {
                        // manage exceptions
                    }

                }

            };


            if (Build.VERSION.SDK_INT >= 11)
                asyncBackground.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            else
                asyncBackground.execute();


            /*************************************************************************/


        }
    }




}


