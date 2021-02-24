package com.friendlyitsolution.meet.irvapp.recy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.friendlyitsolution.meet.irvapp.R;

import java.util.List;
import java.util.Map;


/**
 * Created by Meet on 16-10-2017.
 */

public class rc_adp extends RecyclerView.Adapter<rc_adp.MyViewHolder> {

private List<rc_model> fineslist;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public TextView vehicalno,chas,cls;

    public MyViewHolder(View view) {
        super(view);
        vehicalno= (TextView) view.findViewById(R.id.vehicalno);
        chas= (TextView) view.findViewById(R.id.chassis);
        cls= (TextView) view.findViewById(R.id.cls);

    }
}


    public rc_adp(List<rc_model> moviesList) {
        this.fineslist = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rc_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        rc_model fine = fineslist.get(position);
        Map<String,Object> obj=fine.obj;

        holder.vehicalno.setText(obj.get("vehicalno")+"");
        holder.chas.setText(obj.get("chassis")+"");
        holder.cls.setText(obj.get("class")+"");

        // holder.date.setText(fine.date);


    }

    @Override
    public int getItemCount() {
        return fineslist.size();
    }
}
