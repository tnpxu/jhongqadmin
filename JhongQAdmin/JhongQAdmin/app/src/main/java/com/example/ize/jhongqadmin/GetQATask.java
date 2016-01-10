//package com.example.ize.jhongqadmin;
//
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.example.ize.jhongqadmin.RequestRepository.AcceptSkipStandByQueue;
//import com.example.ize.jhongqadmin.StoreServiceData.QueueListData;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
///**
// * Created by tnpxu on 18/7/2558.
// */
//public class GetQATask extends AsyncTask<String, Void, String> {
//
//    LayoutInflater inflater;
//    ViewGroup container;
//    Bundle savedInstanceState;
//    public View v;
//    public ListView lview;
//
//    public GetQATask(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
//        v = inflater.inflate(R.layout.fragment_q_a_, container, false);
//        lview = (ListView) v.findViewById(R.id.Fragment_QA_listView);
//        this.inflater = inflater;
//        this.container = container;
//        this.savedInstanceState = savedInstanceState;
//
//    }
//
//    public View callBackView() {
//        return v;
//    }
//
//    public ListView setAdapterCallBack() {
//        return lview;
//    }
//
//    @Override
//    protected void onPreExecute() {
//
//    }
//
//    @Override
//    protected String doInBackground(String... params) {
//
//
//
//        String realUrl = "http://jongqservice.azurewebsites.net/api/queueservice/getqueuelist";
//        ServiceTask serviceObject = new ServiceTask();
//        return serviceObject.requestPostGetQueueListContent(realUrl, Q_A_Fragment.dataStoreA);
//
//
//    }
//
//    @Override
//    protected void onPostExecute(String res) {
//
//        try {
//
//
//            JSONObject json = new JSONObject(res);
//            Q_A_Fragment.totalqueuewait = json.getInt("TotalQueueWait");
//            JSONArray jsonarr = new JSONArray("QueueList");
//            for(int i=0;i<jsonarr.length();i++){
//
////                                "UserId": 6,
////                                "QueueNum": 1,
////                                "QueueType": "A",
////                                "Nickname": "benz",
////                                "QueueCheck": false,
////                                "Status": "standby"
//
//                JSONObject obj = jsonarr.getJSONObject(i);
//                int userid = obj.getInt("UserId");
//                int queuenum = obj.getInt("QueueNum");
//                String queuetype = obj.getString("QueueType");
//                String nickname = obj.getString("Nickname");
//                Boolean queuecheck = obj.getBoolean("QueueCheck");
//                String status = obj.getString("Status");
//
//                if(status.equals("standby")) {
//                    Q_A_Fragment.queueAStandby = new AcceptSkipStandByQueue(userid,Q_A_Fragment.resname,Q_A_Fragment.resbranch,queuetype);
//                    Q_A_Fragment.Qnum = queuenum;
//                    Q_A_Fragment.Qtype = queuetype;
//                    Q_A_Fragment.Qnickname = nickname;
//
//                }
//
//                QueueListData temp = new QueueListData(userid,queuenum,queuetype,nickname,queuecheck,status);
//                Q_A_Fragment.listadapt.add(temp);
//
//
//            }
//
////            v = inflater.inflate(R.layout.fragment_q_a_, container, false);
//
//            TextView texViewNum =(TextView) v.findViewById(R.id.Fragment_QA_txt_CurQ);
//            texViewNum.setText(Q_A_Fragment.Qnum + Q_A_Fragment.Qtype);
//            String reserveCome = "";
//            if(Q_A_Fragment.Qtype.equals("A"))
//                reserveCome = "1 - 3????";
//            if(Q_A_Fragment.Qtype.equals("B"))
//                reserveCome = "4 - 6????";
//            if(Q_A_Fragment.Qtype.equals("C"))
//                reserveCome = "8 - 10????";
//            TextView texViewName =(TextView) v.findViewById(R.id.Fragment_QA_txt_CusName);
//            texViewName.setText("( " + Q_A_Fragment.Qnickname + " , "  + " )");
//
////            lview = (ListView) v.findViewById(R.id.Fragment_QA_listView);
//            Q_A_Fragment.populateList(Q_A_Fragment.listadapt);
////            listviewAdapter adapter = new listviewAdapter(getActivity(), Q_A_Fragment.list);
////            lview.setAdapter(adapter);
//
//            //show top red character !!
//            TextView texViewTotal =(TextView) v.findViewById(R.id.Fragment_QA_txt_numSumQ);
//            texViewTotal.setText("" + Q_A_Fragment.totalqueuewait);
//
//
//        } catch (JSONException e) {
//            // manage exceptions
//        }
//
//    }
//}
