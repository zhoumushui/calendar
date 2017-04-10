package com.zhoumushui.calendar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhoumushui.calendar.Count;
import com.zhoumushui.calendar.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CountRecyclerAdapter extends RecyclerView.Adapter<CountRecyclerAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Count> arrayListCount;

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Calendar calendar = arrayListCount.get(position).getCalendar();
        // TODO
        holder.textCount.setText(calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH)
                + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH));
        if (onItemClickListener != null) {
            holder.viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(v, position);
                }
            });

            holder.viewItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayListCount.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        View viewItem;
        TextView textCount;

        public MyViewHolder(View view) {
            super(view);
            viewItem = view;
            textCount = (TextView) view.findViewById(R.id.textCount);
        }
    }

    public void addData(int position, Count count) {
        arrayListCount.add(position, count);
        notifyItemInserted(position);
    }

    public void removeData(int position) {
        arrayListCount.remove(position);
        notifyItemRemoved(position);
    }

}
