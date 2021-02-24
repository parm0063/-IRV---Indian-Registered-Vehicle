package com.friendlyitsolution.meet.irvapp.recy;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.friendlyitsolution.meet.irvapp.MyApp;
import com.friendlyitsolution.meet.irvapp.R;

import java.util.List;
import java.util.Map;


/**
 * Created by Meet on 16-10-2017.
 */

public class puc_adp extends RecyclerView.Adapter<puc_adp.MyViewHolder> {

private List<puc_model> fineslist;

public class MyViewHolder extends RecyclerView.ViewHolder {
    public ImageView img;

    public MyViewHolder(View view) {
        super(view);
        img= (ImageView) view.findViewById(R.id.img);

    }
}


    public puc_adp(List<puc_model> moviesList) {
        this.fineslist = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.puc_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        puc_model fine = fineslist.get(position);

        Glide.with(holder.img.getContext()).load(fine.imgurl)
                .override(300, 300)
                .into(holder.img);
        // holder.date.setText(fine.date);


    }

    @Override
    public int getItemCount() {
        return fineslist.size();
    }
}
