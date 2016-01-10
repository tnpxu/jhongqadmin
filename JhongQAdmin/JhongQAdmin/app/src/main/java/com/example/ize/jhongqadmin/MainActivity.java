package com.example.ize.jhongqadmin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ize.jhongqadmin.RequestRepository.GetQueueListRepository;
import com.example.ize.jhongqadmin.RequestRepository.StartQueueSystemRepository;
import com.example.ize.jhongqadmin.StoreServiceData.Flag;
import com.example.ize.jhongqadmin.StoreServiceData.QueueListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    ProgressDialog dialog;
    //for this res branch only
    public String resname = "EatAmAre";
    public String resbranch = "Center One";

    public GetQueueListRepository dataStoreA;
    public GetQueueListRepository dataStoreB;
    public GetQueueListRepository dataStoreC;
    public StartQueueSystemRepository dataStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String prefName = "RestDetail";
        SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("ResName", resname);
        editor.putString("ResBranch", resbranch);
        editor.commit();

        TextView texViewRes =(TextView) findViewById(R.id.main_txt_RestName);
        texViewRes.setText(resname);

        TextView texViewBranch =(TextView) findViewById(R.id.main_txt_Branch);
        texViewBranch.setText(resbranch);

        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<Flag> listadaptFlag = myApplication.flagObserver;
        listadaptFlag.clear();
        Flag a = new Flag(true);
        listadaptFlag.add(a);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        getMenuInflater().inflate(R.menu.menu_add_q, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //BntSystemStart Click
    public void systemStart (View view) {


        /***********************************/
        /******* SERVICE TASK AREA **********/
        /***********************************/

        dataStore = new StartQueueSystemRepository(resname, resbranch);

        AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
            @Override
            protected void onPreExecute() {

                dialog = ProgressDialog.show(MainActivity.this, "Loading", "Please wait");
            }

            @Override
            protected String doInBackground(String... params) {



                String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/startqueuesystem";
                ServiceTask serviceObject = new ServiceTask();
                return serviceObject.requestPostStartQueueSystemContent(realUrl, dataStore);


            }

            @Override
            protected void onPostExecute(String res) {

                try {

//                    //total queue store it and send back at reserving
//                    "TotalQueue": 0,
//                    //current queue
//                    "QueueNum": 0,
//                    "QueueType": "A"

                    JSONObject json = new JSONObject(res);
                    boolean systemstat = json.getBoolean("SystemStatus");

                    if(systemstat) {
                        dataStoreA = new GetQueueListRepository(resname,resbranch,"A");
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


                                    dataStoreB = new GetQueueListRepository(resname,resbranch,"B");
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


                                                dataStoreC = new GetQueueListRepository(resname,resbranch,"C");
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
                                                            dialog.dismiss();

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
                                                            // store to sharedpreference
                                                            String prefName = "Check";
                                                            SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
                                                            SharedPreferences.Editor editor = mPreferences.edit();
                                                            editor.putBoolean("System", true);
                                                            editor.commit();


                                                            Intent intent = new Intent(getApplicationContext()
                                                                    , ShowQ_tab.class);
                                                            intent.putExtra("ResName",resname);
                                                            intent.putExtra("ResBranch",resbranch);
                                                            startActivity(intent);
                                                            finish();

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





                    } else
                    {

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
