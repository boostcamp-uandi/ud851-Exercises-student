package com.example.android.waitlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.waitlist.data.WaitlistContract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Context mContext;
    // TODO (8) Add a new local variable mCount to store the count of items to be displayed in the recycler view
    private Cursor mCursor;
    /**
     * Constructor using the context and the db cursor
     *
     * @param context the calling context/activity
     */
    // TODO (9) Update the Adapter constructor to accept an integer for the count along with the context
    public GuestListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        // TODO (10) Set the local mCount to be equal to count
        // 데이터베이스의 데이터를 가지고 있는 cursor가 넘어왔다..!
        // 리사이클러에서 뷰의 갯수를 mCursor를 통해서 딱 그 갯수만큼 만들어야한다..!
        mCursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        // onBindViewHolder는 실질적으로 뷰를 움직인다. 즉 어댑터 속에있는 뷰를 컨트롤 하는 부분인것이다.
        // 따라서 뷰를 생성해줄때 (뷰 홀더를 가지고) 데이터베이스 내용에 따라서 얼마만큼의 뷰를 어떤 정보로 표시할것인지 결정한다.
        if(!mCursor.moveToPosition(position))
            return;
        String name = mCursor.getString(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME));
        int partySize = mCursor.getInt(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE));
        holder.nameTextView.setText(name);
        holder.partySizeTextView.setText(String.valueOf(partySize));

        long id = mCursor.getLong(mCursor.getColumnIndex(WaitlistContract.WaitlistEntry._ID));
        holder.itemView.setTag(id);

    }


    // TODO (11) Modify the getItemCount to return the mCount value rather than 0
    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party size number
        TextView partySizeTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
    if (newCursor != null) {
        // Force the RecyclerView to refresh
        this.notifyDataSetChanged();
        }
    }
}