package com.programmer.awesome.mjclnf;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.programmer.awesome.mjclnf.customObject.ListItem;

import java.util.ArrayList;

public class LostItemAdapter extends BaseAdapter {
    Context mContext = null;
    ArrayList<ListItem> mData = null;
    LayoutInflater mInflater = null;

    public LostItemAdapter() {
    }

    public LostItemAdapter(Context context, ArrayList<ListItem> data) {
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
    public ListItem getItem(int position) {
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
            viewHolder.eventdate = (TextView) itemLayout.findViewById(R.id.losttextView2);
            viewHolder.regitdate = (TextView) itemLayout.findViewById(R.id.losttextView3);
            itemLayout.setTag(viewHolder);

        } else {
            viewHolder = (PersonViewHolder) itemLayout.getTag();
        }
        viewHolder.title.setText("[" + mData.get(position).getCategory() + "] " + mData.get(position).getTitle());
        viewHolder.eventdate.setText("분실일자 "+mData.get(position).getEventDate());
        viewHolder.regitdate.setText(mData.get(position).getRegitDate());


        return itemLayout;
    }

    public void addItem(ArrayList<ListItem> data) {
        for (ListItem i : data)
            mData.add(i);
        this.notifyDataSetChanged();
    }
}

