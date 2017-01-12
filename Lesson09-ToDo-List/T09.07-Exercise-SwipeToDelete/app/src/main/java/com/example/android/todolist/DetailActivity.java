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

package com.example.android.todolist;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.todolist.data.TaskContentProvider;
import com.example.android.todolist.data.TaskContract;


public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();

    /* previous data */
    private Cursor prevCursor;
    private int prevIdCol, prevDescCol, prevPriorCol;

    /* UI */
    private EditText etTaskDiscription;
    private RadioButton rbPriority1;
    private RadioButton rbPriority2;
    private RadioButton rbPriority3;

    /* updated data */
    private int mPriority;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        etTaskDiscription = (EditText) findViewById(R.id.editTextTaskDescription);
        rbPriority1 = (RadioButton) findViewById(R.id.radButton1);
        rbPriority2 = (RadioButton) findViewById(R.id.radButton2);
        rbPriority3 = (RadioButton) findViewById(R.id.radButton3);

        String idOfThisActivity = getIndexFromIntent();
        new TaskFetchTask().execute(idOfThisActivity);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prevCursor.close();
    }

    private String getIndexFromIntent() {
        Intent fromMainActivity = getIntent();
        if (fromMainActivity.hasExtra(getString(R.string.task_id))) {
            return fromMainActivity.getStringExtra(getString(R.string.task_id));
        } else {
            return "";
        }
    }

    public class TaskFetchTask extends AsyncTask<String, Void, Cursor> {
        @Override
        protected Cursor doInBackground(String... params) {
            String id = params[0];
            Log.e(TAG, id);
            Uri uri = TaskContract.TaskEntry.CONTENT_URI;
            uri = uri.buildUpon().appendPath(id).build();
            return getContentResolver().query(uri, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            prevCursor = cursor;
            prevIdCol = prevCursor.getColumnIndex(TaskContract.TaskEntry._ID);
            prevDescCol = prevCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
            prevPriorCol = prevCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY);

            prevCursor.moveToFirst();
            etTaskDiscription.setText(prevCursor.getString(prevDescCol));
            setPrevRadioButton(prevCursor.getString(prevPriorCol));
        }
    }

    public void onClickUpdateTask(View view) {

        String input = etTaskDiscription.getText().toString();
        if (input.length() == 0) {
            return;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);

        String id = prevCursor.getString(prevIdCol);
        Uri uri = TaskContract.TaskEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(id).build();

        int ret = getContentResolver().update(uri, contentValues, null, null);

        if (ret > 0) {
            Toast.makeText(getBaseContext(), ret + " updated", Toast.LENGTH_LONG).show();
        }

        finish();
    }

    private void setPrevRadioButton(String priority) {
        int p = Integer.parseInt(priority);
        switch (p) {
            case 1:
                rbPriority1.setChecked(true);
                break;
            case 2:
                rbPriority2.setChecked(true);
                break;
            case 3:
                rbPriority3.setChecked(true);
                break;
            default:
                break;
        }

        mPriority = p;
    }

    public void onPrioritySelected(View view) {
        if (rbPriority1.isChecked()) {
            mPriority = 1;
        } else if (rbPriority2.isChecked()) {
            mPriority = 2;
        } else if (rbPriority3.isChecked()) {
            mPriority = 3;
        }
    }
}
