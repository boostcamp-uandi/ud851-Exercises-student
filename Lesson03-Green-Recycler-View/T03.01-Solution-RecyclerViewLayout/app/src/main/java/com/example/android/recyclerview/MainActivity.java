/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.recyclerview;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_LIST_ITEMS = 100;

    /*
     * References to RecyclerView and Adapter to reset the list to its
     * "pretty" state when the reset menu item is clicked.
     */
    private GreenAdapter mAdapter;
    private RecyclerView mNumbersList;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

        /*
         * A LinearLayoutManager is responsible for measuring and positioning item views within a
         * RecyclerView into a linear list. This means that it can produce either a horizontal or
         * vertical list depending on which parameter you pass in to the LinearLayoutManager
         * constructor. By default, if you don't specify an orientation, you get a vertical list.
         * In our case, we want a vertical list, so we don't need to pass in an orientation flag to
         * the LinearLayoutManager constructor.
         *
         * There are other LayoutManagers available to display your data in uniform grids,
         * staggered grids, and more! See the developer documentation for more details.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mNumbersList.setHasFixedSize(true);

        /*
         * The GreenAdapter is responsible for displaying each item in the list.
         */
        mAdapter = new GreenAdapter(NUM_LIST_ITEMS);

        mNumbersList.setAdapter(mAdapter);

        Thread1 example = new Thread1();
        example.start();

        new doitasync().execute();
    }

    class Thread1 extends Thread {

        public void run(){

            Log.i("123123","thread_start");

            for(int k=0;k<10;k++) {

                try {

                    Message msg = Message.obtain();

                    msg.obj = "second = " + k;

                    Log.i("123123", "thread "+msg.obj.toString());

                    Thread.sleep(1000);

                } catch (Exception e) {

                    e.printStackTrace();

                    Log.i("123123", e.toString());

                }

            }

            Message msg = Message.obtain();

        //    handler.sendMessage(msg);

        }

    }

    public class doitsomething extends Handler {

        public doitsomething(Message msg){

        //    tv_thread_second.setText("thread finish");

            Log.i("123123","thread finish");

        }

    }

    public class doitasync extends AsyncTask<Integer, Void, Integer> {

        int second=0;

        @Override

        protected void onPreExecute(){

            super.onPreExecute();

            Log.i("123123","async_start");

        }

        @Override

        protected Integer doInBackground(Integer... values){

            Log.i("123123","here");

            while(second<10){

                try{

                    Log.i("123123","async second = "+second);

                    Thread.sleep(1000);

                    second++;

                }catch (InterruptedException ex){  }

            }

            return second;

        }

        @Override

        protected void onPostExecute(Integer values){

        //    tv_async_second.setText(second+"가 되었습니다.");

            Log.i("123123","async finish");

        }

    }
}
