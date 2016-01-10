package com.example.ize.jhongqadmin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.ize.jhongqadmin.RequestRepository.AddQueueAnonymous;
import com.example.ize.jhongqadmin.RequestRepository.GetQueueListRepository;
import com.example.ize.jhongqadmin.StoreServiceData.Flag;
import com.example.ize.jhongqadmin.StoreServiceData.QueueListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AddQA extends ActionBarActivity {

    String text;
    Intent i =getIntent();
    public String resName;
    public String resBranch;
    public EditText mEdit;
    public AddQueueAnonymous addQ;
    public ProgressDialog dialog;
    public AddQueueAnonymous dataStore;
    public String queueTypeClick = "A";
    public String nickName;
    public int currentwaitqueue;
    public GetQueueListRepository dataStoreQueueList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_q);

        String prefName = "RestDetail";
        SharedPreferences mPreferences = getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        resName = mPreferences.getString("ResName", "");
        resBranch = mPreferences.getString("ResBranch", "");


        RadioButton rbA = (RadioButton) findViewById(R.id.radioButton);
        RadioButton rbB = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton rbC = (RadioButton) findViewById(R.id.radioButton3);

        MyApplication myApplication = ((MyApplication)getApplicationContext());
        ArrayList<Integer> listQWait = myApplication.toTalQueueWaitA;
        Integer toTalQueueWait = listQWait.get(0);
        currentwaitqueue = toTalQueueWait.intValue();

        rbA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queueTypeClick = "A";
                MyApplication myApplication = ((MyApplication) getApplicationContext());
                ArrayList<QueueListData> listadapt = myApplication.listadaptA;
                ArrayList<Integer> listQWait = myApplication.toTalQueueWaitA;
                Integer toTalQueueWait = listQWait.get(0);
                currentwaitqueue = toTalQueueWait.intValue();
            }
        });
        rbB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queueTypeClick = "B";
                MyApplication myApplication = ((MyApplication) getApplicationContext());
                ArrayList<QueueListData> listadapt = myApplication.listadaptB;
                ArrayList<Integer> listQWait = myApplication.toTalQueueWaitB;
                Integer toTalQueueWait = listQWait.get(0);
                currentwaitqueue = toTalQueueWait.intValue();
            }
        });
        rbC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                queueTypeClick = "C";
                MyApplication myApplication = ((MyApplication) getApplicationContext());
                ArrayList<QueueListData> listadapt = myApplication.listadaptC;
                ArrayList<Integer> listQWait = myApplication.toTalQueueWaitC;
                Integer toTalQueueWait = listQWait.get(0);
                currentwaitqueue = toTalQueueWait.intValue();
            }
        });


        //intent ShowQPage
        findViewById(R.id.addQ_btn_addQ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdit   = (EditText)findViewById(R.id.addQ_editText_Name);
                nickName = mEdit.getText().toString();


                dataStore = new AddQueueAnonymous(resName,resBranch,0,queueTypeClick,currentwaitqueue,nickName);
                /***********************************/
                /******* SERVICE TASK AREA **********/
                /***********************************/

                AsyncTask<String, Void, String> asyncBackground = new AsyncTask<String, Void, String>() {
                    @Override
                    protected void onPreExecute() {
                        dialog = ProgressDialog.show(AddQA.this, "Loading", "Please wait");
                    }

                    @Override
                    protected String doInBackground(String... params) {



                        String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/reservinganonymous";
                        ServiceTask serviceObject = new ServiceTask();
                        return serviceObject.requestPostReservingAnonymousContent(realUrl, dataStore);


                    }

                    @Override
                    protected void onPostExecute(String res) {

                        try {
                            // store to sharedpreference
                            JSONObject json = new JSONObject(res);
                            Boolean error = json.getBoolean("Error");
                            String errormsg = json.getString("MsgThai");


                            if(!error) {
//
//
//                                dataStoreQueueList = new GetQueueListRepository(resName,resBranch,queueTypeClick);
//                                /***********************************/
//                                /******* SERVICE TASK AREA QUEUE****/
//                                /***********************************/
//
//                                AsyncTask<String, Void, String> asyncBackground4 = new AsyncTask<String, Void, String>() {
//
//                                    public View callbackV;
//
//                                    public View callBack(View v) {
//
//                                        return v;
//                                    }
//
//                                    @Override
//                                    protected void onPreExecute() {
//
//                                    }
//
//                                    @Override
//                                    protected String doInBackground(String... params) {
//
//
//
//                                        String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/getqueuelist";
//                                        ServiceTask serviceObject = new ServiceTask();
//                                        return serviceObject.requestPostGetQueueListContent(realUrl, dataStoreQueueList);
//
//
//                                    }
//
//                                    @Override
//                                    protected void onPostExecute(String res) {
//
////                                Toast.makeText(getApplicationContext(), res,
////                                        Toast.LENGTH_LONG).show();
//                                        try {
//
//                                            MyApplication myApplication = ((MyApplication)getApplicationContext());
//                                            ArrayList<QueueListData> listadapt = null;
//                                            if(queueTypeClick.equals("A")) {
//                                                listadapt = myApplication.listadaptA;
//                                                listadapt.clear();
//                                            }
//                                            if(queueTypeClick.equals("B")) {
//                                                listadapt = myApplication.listadaptB;
//                                                listadapt.clear();
//                                            }
//                                             if(queueTypeClick.equals("C")) {
//                                                 listadapt = myApplication.listadaptC;
//                                                 listadapt.clear();
//                                             }
//
//
//
//
//                                            JSONObject json = new JSONObject(res);
//
//                                            ArrayList<Integer> totalqueuewait = null;
//
//                                            if(queueTypeClick.equals("A")) {
//                                                totalqueuewait = myApplication.toTalQueueWaitA;
//                                                totalqueuewait.clear();
//                                            }
//                                            if(queueTypeClick.equals("B")) {
//                                                totalqueuewait = myApplication.toTalQueueWaitB;
//                                                totalqueuewait.clear();
//                                            }
//                                            if(queueTypeClick.equals("C")) {
//                                                totalqueuewait = myApplication.toTalQueueWaitC;
//                                                totalqueuewait.clear();
//                                            }
//
//                                            Integer qWait = new Integer(json.getInt("TotalQueueWait"));
//                                            totalqueuewait.add(qWait);
//
//                                            JSONArray jsonarr = json.getJSONArray("QueueList");
//
//                                            for(int i=0;i<jsonarr.length();i++){
//
////                                "UserId": 6,
////                                "QueueNum": 1,
////                                "QueueType": "A",
////                                "Nickname": "benz",
////                                "QueueCheck": false,
////                                "Status": "standby"
//
//                                                JSONObject obj = jsonarr.getJSONObject(i);
//                                                int userid = obj.getInt("UserId");
//                                                int queuenum = obj.getInt("QueueNum");
//                                                String queuetype = obj.getString("QueueType");
//                                                String nickname = obj.getString("Nickname");
//                                                Boolean queuecheck = obj.getBoolean("QueueCheck");
//                                                String status = obj.getString("Status");
//
//
//
//                                                QueueListData temp = new QueueListData(userid,queuenum,queuetype,nickname,queuecheck,status);
//                                                listadapt.add(temp);
//
//
//                                            }
//
//
//                                            /****************** refresh command **********/
////                                            Intent intent = new Intent(getApplicationContext()
////                                                    , ShowQ_tab.class);
////                                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
////                                            // | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
////                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                                            startActivity(intent);
//                                            dialog.dismiss();
//                                            finish();
//
//                                        } catch (JSONException e) {
//                                            // manage exceptions
////                                    Toast.makeText(getApplicationContext(),e.toString(),
////                                            Toast.LENGTH_LONG).show();
//                                        }
//
//                                    }
//
//                                };
//
//
//                                try {
//                                    if (Build.VERSION.SDK_INT >= 11)
//                                        asyncBackground4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                                    else
//                                        asyncBackground4.execute();
//                                } catch(Exception e) {
//
//                                }
//
//
//
//
//
//                                /*************************************************************************/
                                MyApplication myApplication = ((MyApplication)getApplicationContext());
                                ArrayList<Flag> listadaptFlag = myApplication.flagObserver;
                                listadaptFlag.clear();
                                Flag a = new Flag(true);
                                listadaptFlag.add(a);

                                dialog.dismiss();
                                finish();

                            } else {

                                dialog.dismiss();
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(AddQA.this);
                                builder1.setMessage(errormsg);
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
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_q, menu);
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

    //Bnt Add Q
    public void AddQ(View view) {

    }
}
