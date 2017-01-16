package com.example.android.waitlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.waitlist.data.WaitlistContract.*;

// TODO (1) extend the SQLiteOpenHelper class
public class WaitlistDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "waitlist.db";
    private static final int DATABASE_VERSION = 1;

    public WaitlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //세번째 인자는 CursorFactory factory로 표준 cursor를 이용할경우 null로 해준다.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//DB가 처음 만들어질 때 호출. 여기서 테이블 생성 및 초기 레코드 삽입.
        final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE" + WaitlistEntry.TABLE_NAME +
                " (" + WaitlistEntry._ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                WaitlistEntry.COLUMN_GUEST_NAME + " TEXT NOT NULL, " +
                WaitlistEntry.COLUMN_PARTY_SIZE + " INTEGER NOT NULL," +
                WaitlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {//DB를 업그레이드할때 호출. 혹은 version이 바뀌었을때 호출.
        db.execSQL("DROP TABLE IF EXITSTS " + WaitlistEntry.TABLE_NAME);
        onCreate(db);
    }
}

    // TODO (2) Create a static final String called DATABASE_NAME and set it to "waitlist.db"

    // TODO (3) Create a static final String called DATABASE_VERSION and set it to 1

    // TODO (4) Create a Constructor that takes a context and calls the parent constructor

    // TODO (5) Override the onCreate method

        // TODO (6) Inside, create an String query called SQL_CREATE_WAITLIST_TABLE that will create the table

        // TODO (7) Execute the query by calling execSQL on sqLiteDatabase and pass the string query SQL_CREATE_WAITLIST_TABLE

    // TODO (8) Override the onUpgrade method

        // TODO (9) Inside, execute a drop table query, and then call onCreate to re-create it
