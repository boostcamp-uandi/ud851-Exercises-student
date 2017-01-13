package com.example.android.waitlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import com.example.android.waitlist.data.WaitlistContract;
import com.example.android.waitlist.data.WaitlistDbHelper;




public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();


    private GuestListAdapter mAdapter;

    // TODO (1) Create a local field member of type SQLiteDatabase called mDb
    private SQLiteDatabase mDb;

    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView waitlistRecyclerView;

        // Set local attributes to corresponding views
        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        mNewGuestNameEditText = (EditText) findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText = (EditText) findViewById(R.id.party_count_edit_text);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Set layout for the RecyclerView, because it's a list we are using the linear layout

        // Create an adapter for that cursor to display the data
        //mAdapter = new GuestListAdapter(this);

        // TODO (2) Create a WaitlistDbHelper instance, pass "this" to the constructor as context
        WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);
        // TODO (3) Get a writable database reference using getWritableDatabase and store it in mDb
        mDb = dbHelper.getWritableDatabase();
        // TODO (4) call insertFakeData from TestUtil and pass the database reference mDb
        // TODO (7) Run the getAllGuests function and store the result in a Cursor variable
        Cursor cursor = getAllGuests();
        // TODO (12) Pass the resulting cursor count to the adapter
        mAdapter = new GuestListAdapter(this, cursor);
        // Link the adapter to the RecyclerView
        waitlistRecyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target){
                return false;
            }
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir){
                long id = (long) viewHolder.itemView.getTag();
                removeGuest(id);
                mAdapter.swapCursor(getAllGuests());
            }
        }).attachToRecyclerView(waitlistRecyclerView);
    }

    /**
     * This method is called when user clicks on the Add to waitlist button
     *
     * @param view The calling view (button)
     */
    public void addToWaitList(View view) {
//        Log.d(TAG, "addToWaitlist1");
        if(mNewGuestNameEditText.getText().length() == 0 ||
                mNewPartySizeEditText.getText().length() == 0){
            return;
        }

        int partySize = 1;
        try{
            partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
//        Log.d(TAG, "addToWaitlist2");
        addNewGuest(mNewGuestNameEditText.getText().toString(), partySize);

        mAdapter.swapCursor(getAllGuests());

        //clear UI text fields
        mNewPartySizeEditText.clearFocus();
        mNewGuestNameEditText.getText().clear();
        mNewPartySizeEditText.getText().clear();
    }

    // TODO (5) Create a private method called getAllGuests that returns a cursor
    private Cursor getAllGuests(){
        // TODO (6) Inside, call query on mDb passing in the table name and projection String [] order by COLUMN_TIMESTAMP
        return mDb.query(
                WaitlistContract.WaitlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
        );
    }

    private long addNewGuest(String name, int partySize){
//        Log.d(TAG, "addNewGuest1");
        ContentValues cv = new ContentValues();

        cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
        cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);
//        Log.d(TAG, "addNewGuest2");
        return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
    }

    private boolean removeGuest(long id){
        return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID + "=" + id, null) > 0;

    }


}