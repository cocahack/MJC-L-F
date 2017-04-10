package com.programmer.awesome.mjclnf;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.programmer.awesome.mjclnf.customObject.ListNoticeFaq;

import java.util.ArrayList;

public class NoticeItemAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<ListNoticeFaq> mData = null;
    LayoutInflater mInflater = null;

    public NoticeItemAdapter() {
    }

    public NoticeItemAdapter(Context context, ArrayList<ListNoticeFaq> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ListNoticeFaq getItem(int position) {
        return mData.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemLayout = convertView;
        PersonViewHolder viewHolder = null;

        if (itemLayout == null) {
            itemLayout = mInflater.inflate(R.layout.lost_item, null);
            viewHolder = new PersonViewHolder();
            viewHolder.title = (TextView) itemLayout.findViewById(R.id.losttextView1);
            viewHolder.eventdate = (TextView) itemLayout.findViewById(R.id.losttextView3);
            viewHolder.regitdate = (TextView) itemLayout.findViewById(R.id.losttextView2);
            itemLayout.setTag(viewHolder);

        } else {
            viewHolder = (PersonViewHolder) itemLayout.getTag();
        }
        viewHolder.title.setText(mData.get(position).getTitle());
        viewHolder.eventdate.setText(mData.get(position).getNo());
        viewHolder.regitdate.setText(mData.get(position).getRegitDate());


        return itemLayout;
    }

    public void addItem(ArrayList<ListNoticeFaq> data) {
        for (ListNoticeFaq i : data)
            mData.add(i);
        this.notifyDataSetChanged();
    }
}

