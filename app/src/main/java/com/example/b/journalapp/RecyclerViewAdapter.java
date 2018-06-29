package com.example.b.journalapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.b.journalapp.Models.Notes;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Notes[] mDataset;
    Context mContext;
    protected ItemListener mListener;
    int selected_position = RecyclerView.NO_POSITION;

    public RecyclerViewAdapter(Context ctx, Notes[] dataset, ItemListener itemListener) {

        mDataset = dataset;
        mContext = ctx;
        mListener = itemListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public Notes mItem;
        public TextView mTitle, mPreview, mDate;

        public ViewHolder(View v){

            super(v);
            v.setOnClickListener(this);

            mTitle = v.findViewById(R.id.notetitle_textView);
            mPreview = v.findViewById(R.id.notepreview_textView);
            mDate = v.findViewById(R.id.notedate_textView);

        }

        public void setData (Notes item) {
            this.mItem = item;
            mTitle.setText(mItem.text);
            mPreview.setText(mItem.text);
            mDate.setText(String.valueOf(mItem.timestamp));
        }

        @Override
        public void onClick(View v) {
            if(mListener != null) {
                mListener.onItemClick(mItem);
            }

            notifyItemChanged(selected_position);
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);

        }
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        RecyclerViewAdapter.ViewHolder cvh = new RecyclerViewAdapter.ViewHolder(view);
        return cvh;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.setData(mDataset[position]);
    }

    @Override
    public int getItemCount() { return mDataset.length;   }

    public interface ItemListener {
        void onItemClick(Notes item);
    }
}
