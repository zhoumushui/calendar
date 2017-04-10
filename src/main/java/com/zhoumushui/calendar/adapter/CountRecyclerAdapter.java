package com.zhoumushui.calendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhoumushui.calendar.Count;
import com.zhoumushui.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/4/10.
 */

public class CountRecyclerAdapter extends RecyclerView.Adapter<CountRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Count> arrayListCount;

    public CountRecyclerAdapter(Context context, ArrayList<Count> arrayListCount) {
        this.context = context;
        this.arrayListCount = arrayListCount;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_count_recycler, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Calendar calendar = arrayListCount.get(position).getCalendar();

        // TODO
        holder.textCount.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)
                + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public int getItemCount() {
        return arrayListCount.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textCount;

        public MyViewHolder(View view) {
            super(view);
            textCount = (TextView) view.findViewById(R.id.textCount);
        }
    }

}
