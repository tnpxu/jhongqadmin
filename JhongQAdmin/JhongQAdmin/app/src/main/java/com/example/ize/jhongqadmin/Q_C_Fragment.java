package com.example.ize.jhongqadmin;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ize.jhongqadmin.RequestRepository.AcceptSkipStandByQueue;
import com.example.ize.jhongqadmin.RequestRepository.GetQueueListRepository;
import com.example.ize.jhongqadmin.StoreServiceData.Flag;
import com.example.ize.jhongqadmin.StoreServiceData.QueueListData;
import com.software.shell.fab.ActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Q_C_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Q_C_Fragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Custom ListView
    private ArrayList<HashMap<String, String>> list;
    public static final String FIRST_COLUMN = "Column 1";
    public static final String SECOND_COLUMN = "Column 2";

    public GetQueueListRepository dataStoreC;
    public AcceptSkipStandByQueue queueCStandby;
    public String Resname;
    public String Resbranch;
    public String testclick;
    View viewreturn;

    public ListView lview;
    public TextView texViewTotal;
    public TextView texViewNum;
    public TextView texViewName;

    public ArrayList<Integer> listQWait;
    public ArrayList<QueueListData> listadapt;

    public boolean flag = true;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Q_C_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Q_C_Fragment newInstance(String param1, String param2,String rn , String rb,String test) {
        Q_C_Fragment fragment = new Q_C_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString("ResName", rn);
        args.putString("ResBranch", rb);
        args.putString("test", test);
        fragment.setArguments(args);
        return fragment;
    }

    public Q_C_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            testclick = getArguments().getString("test");
        }

        setRetainInstance(true);
        String prefName = "RestDetail";
        SharedPreferences mPreferences = getActivity().getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        Resname = mPreferences.getString("ResName","");
        Resbranch = mPreferences.getString("ResBranch","");

    }

    Intent intent;
    View view;
    @Override
    public View onCreateView(final LayoutInflater inflater,final ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        view = inflater.inflate(R.layout.fragment_q_c_, container, false);
        lview = (ListView) view.findViewById(R.id.Fragment_QC_listView);
        MyApplication myApplication = ((MyApplication)getActivity().getApplicationContext());
        listadapt = myApplication.listadaptC;
        listQWait = myApplication.toTalQueueWaitC;
        Integer toTalQueueWait = listQWait.get(0);

        //show top red character !!
        texViewTotal =(TextView) view.findViewById(R.id.Fragment_QC_txt_numSumQ);
        texViewTotal.setText("" + toTalQueueWait.intValue());

        if(!(listadapt.isEmpty())) {
            QueueListData standBy = listadapt.get(0);
            texViewNum = (TextView) view.findViewById(R.id.Fragment_QC_txt_CurQ);
            texViewNum.setText(standBy.getQueuenum() + standBy.getQueuetype());

            String reserveCome = "";
            if (standBy.getQueuetype().equals("A"))
                reserveCome = "1 - 3ท่าน";
            if (standBy.getQueuetype().equals("B"))
                reserveCome = "4 - 6ท่าน";
            if (standBy.getQueuetype().equals("C"))
                reserveCome = "8 - 10ท่าน";
            texViewName = (TextView) view.findViewById(R.id.Fragment_QC_txt_CusName);
            texViewName.setText("( " + standBy.getNickname() + " , " + reserveCome + " )");
        }


        populateList(listadapt);
        listviewAdapter adapter = new listviewAdapter(getActivity(), list);
        lview.setAdapter(adapter);

        ActionButton fabBtn = (ActionButton) view.findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyApplication myApplication = ((MyApplication)getActivity().getApplicationContext());
                ArrayList<Flag> listadaptFlag = myApplication.flagObserver;
                listadaptFlag.clear();
                Flag a = new Flag(false);
                listadaptFlag.add(a);
                intent = new Intent(getActivity(), AddQA.class);
                startActivity(intent);
            }
        });

        return view;

    }

    public void onViewCreated(final View view, Bundle savedInstanceState) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(3000);
//                        getActivity().runOnUiThread(new Runnable() // start actions in UI thread
//                        {
//
//                            @Override
//                            public void run() {
//                                Toast.makeText(getActivity(), Resname + Resbranch,
//                                        Toast.LENGTH_LONG).show();
//                            }
//                        });



                        dataStoreC = new GetQueueListRepository(Resname,Resbranch,"C");
                        /***********************************/
                        /******* SERVICE TASK AREA QUEUE****/
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
                            protected void onPostExecute(final String res) {

                                try {


                                    JSONObject json = new JSONObject(res);
                                    //MyApplication myApplication = ((MyApplication)getActivity().getApplicationContext());

                                    // STATE FUNCTION CHECK QUEUE)
                                    //totalqueuewait = myApplication.toTalQueueWaitC;

                                    listQWait.clear();
                                    Integer qWait = new Integer(json.getInt("TotalQueueWait"));
                                    listQWait.add(qWait);
                                    //listadapt = myApplication.listadaptC;
                                    listadapt.clear();

                                    JSONArray jsonarr = json.getJSONArray("QueueList");

                                    for (int i = 0; i < jsonarr.length(); i++) {


                                        JSONObject obj = jsonarr.getJSONObject(i);
                                        int userid = obj.getInt("UserId");
                                        int queuenum = obj.getInt("QueueNum");
                                        String queuetype = obj.getString("QueueType");
                                        String nickname = obj.getString("Nickname");
                                        Boolean queuecheck = obj.getBoolean("QueueCheck");
                                        String status = obj.getString("Status");


                                        QueueListData temp = new QueueListData(userid, queuenum, queuetype, nickname, queuecheck, status);
                                        listadapt.add(temp);


                                    }



                                    if( getActivity() != null ) {

                                        MyApplication myApplication = ((MyApplication)getActivity().getApplicationContext());
                                        ArrayList<Flag> listadaptFlag = myApplication.flagObserver;
                                        Flag check = listadaptFlag.get(0);

                                        if(check.getFlagthread()) {

                                            getActivity().runOnUiThread(new Runnable() // start actions in UI thread
                                            {

                                                @Override
                                                public void run() {

                                                    /***************** detach attach ***************/
//                                            MyApplication myApplication = ((MyApplication)getActivity().getApplicationContext());
////                                            ListView lview = (ListView) getView().findViewById(R.id.Fragment_QA_listView);
//                                            ArrayList<QueueListData> listadaptGet = myApplication.listadaptC;
//                                            ArrayList<Integer> listQWaitGet = myApplication.toTalQueueWaitC;


                                                    //show top red character !!
//                                            TextView texViewTotal =(TextView) getView().findViewById(R.id.Fragment_QA_txt_numSumQ);


//                                            if(!(listadapt == null || listQWait == null)) {
//                                                Integer toTalQueueWait = listQWait.get(0);
//                                                texViewTotal.setText("" + toTalQueueWait.intValue());
//                                                QueueListData standBy = listadapt.get(0);
////                                                TextView texViewNum =(TextView) getView().findViewById(R.id.Fragment_QA_txt_CurQ);
//                                                texViewNum.setText(standBy.getQueuenum() + standBy.getQueuetype());
//
//
//                                                String reserveCome = "";
//                                                if (standBy.getQueuetype().equals("A"))
//                                                    reserveCome = "1 - 3ท่าน";
//                                                if (standBy.getQueuetype().equals("B"))
//                                                    reserveCome = "4 - 6ท่าน";
//                                                if (standBy.getQueuetype().equals("C"))
//                                                    reserveCome = "8 - 10ท่าน";
////                                                TextView texViewName = (TextView) getView().findViewById(R.id.Fragment_QA_txt_CusName);
//                                                texViewName.setText("( " + standBy.getNickname() + " , " + reserveCome + " )");
//                                            }


                                                    populateList(listadapt);
                                                    listviewAdapter adapter = new listviewAdapter(getActivity(), list);
                                                    lview.setAdapter(adapter);

                                                    int totalQwait = listadapt.size();
                                                    texViewTotal.setText("" + totalQwait);

                                                    if (listadapt.size() > 0) {
                                                        QueueListData standBy = listadapt.get(0);
                                                        if((texViewNum) != null && (texViewName != null)) {
                                                            if (standBy.getNickname() != null) {
                                                                texViewNum.setText(standBy.getQueuenum() + standBy.getQueuetype());
                                                                texViewName.setText("( " + standBy.getNickname() + " , " + "8-10 ท่าน" + " )");
                                                            } else {
                                                                texViewNum.setText("NONE");
                                                                texViewName.setText("NONE");
                                                            }
                                                        }
                                                    }
                                                }


                                            });
                                        }
                                    }


                                } catch (JSONException e) {

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



                    } catch (Exception e) {

                    }





                }
            }});
        thread.start();
    }


    private void populateList(ArrayList<QueueListData> listadapt) {

        list = new ArrayList<HashMap<String, String>>();


        for (int j = 0; j < listadapt.size(); j++) {
            QueueListData anObject = listadapt.get(j);
            String reserveCome = "";
            HashMap<String, String> temp = new HashMap<String, String>();

            temp.put(FIRST_COLUMN, anObject.getQueuenum() + anObject.getQueuetype());

            if (anObject.getQueuetype().equals("A"))
                reserveCome = "1 - 3ท่าน";
            if (anObject.getQueuetype().equals("B"))
                reserveCome = "4 - 6ท่าน";
            if (anObject.getQueuetype().equals("C"))
                reserveCome = "8 - 10ท่าน";
            temp.put(SECOND_COLUMN, "( " + anObject.getNickname() + " , " + reserveCome + " )");

            list.add(temp);
        }
    }


}
