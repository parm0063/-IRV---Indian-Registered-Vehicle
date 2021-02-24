package com.friendlyitsolution.meet.irvapp.recy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendlyitsolution.meet.irvapp.R;

import java.util.List;


/**
 * Created by Meet on 16-10-2017.
 */

public class fines_adp extends RecyclerView.Adapter<fines_adp.MyViewHolder> {

private List<fines_model> fineslist;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView date,status,rule,amt;

    public MyViewHolder(View view) {
        super(view);
        rule = (TextView) view.findViewById(R.id.rule);
        status = (TextView) view.findViewById(R.id.status);
        amt = (TextView) view.findViewById(R.id.amt);
        date=(TextView)view.findViewById(R.id.date);
    }
}


    public fines_adp(List<fines_model> moviesList) {
        this.fineslist = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fines_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        fines_model fine = fineslist.get(position);
        holder.date.setText(fine.date);
        holder.amt.setText(fine.amt);
        holder.rule.setText(fine.rule);
        holder.status.setText(fine.status);

    }

    @Override
    public int getItemCount() {
        return fineslist.size();
    }
}
