package com.friendlyitsolution.meet.irv_admin;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.friendlyitsolution.meet.irv_admin.ItemFragment.OnListFragmentInteractionListener;
import com.friendlyitsolution.meet.irv_admin.dummy.DummyContent.DummyItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyItemRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).details);
        holder.mContentView.setText(mValues.get(position).content);

        holder.btntver.setTag(mValues.get(position).id);

        holder.btver.setTag(mValues.get(position).id);

        holder.btntver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b=(Button)view;
                Toast.makeText(MainActivity.con,"Clickec "+b.getTag(),Toast.LENGTH_LONG).show();
                FirebaseDatabase fb=FirebaseDatabase.getInstance();
                DatabaseReference ref=fb.getReference("users");

                ref.child(b.getTag()+"").child("status").setValue("pending");
                ref.child(b.getTag()+"").child("licence").removeValue();
            }
        });


        holder.btver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button b=(Button)view;
                Toast.makeText(MainActivity.con,"Clickec "+b.getTag(),Toast.LENGTH_LONG).show();
                FirebaseDatabase fb=FirebaseDatabase.getInstance();
                DatabaseReference ref=fb.getReference("users");
                ref.child(b.getTag()+"").child("status").setValue("Verified");
            }
        });

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Button btver,btntver;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            btntver=(Button)view.findViewById(R.id.ntver);
            btver=(Button)view.findViewById(R.id.ver);
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
