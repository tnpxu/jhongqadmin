//package com.example.ize.jhongqadmin;
//
///**
// * Created by tnpxu on 24/7/2558.
// */
//public class UpdateUi {
//
//    private void update() {
//        (new Thread(new Runnable()
//        {
//
//            @Override
//            public void run()
//            {
//                while (!Thread.interrupted())
//                    try
//                    {
//                        Thread.sleep(1000);
//                        activity.runOnUiThread(new Runnable() // start actions in UI thread
//                        {
//
//                            @Override
//                            public void run()
//                            {
//
//                            }
//                        });
//                    }
//                    catch (InterruptedException e)
//                    {
//                        // ooops
//                    }
//            }
//        })).start(); // the while thread will start in BG thread
//    }
//}
