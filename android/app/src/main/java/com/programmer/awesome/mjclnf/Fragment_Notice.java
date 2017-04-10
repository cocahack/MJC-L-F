package com.programmer.awesome.mjclnf;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.programmer.awesome.mjclnf.customObject.ListNoticeFaq;
import com.programmer.awesome.mjclnf.customObject.StaticValues;
import com.programmer.awesome.mjclnf.serverConnect.DownLoadList;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by HyunSoo on 2016-11-03.
 */

public class Fragment_Notice extends Fragment implements AbsListView.OnScrollListener, View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener{
    boolean lastItemVisibleFlag = false;
    boolean lastitem = true;
    private ArrayList<ListNoticeFaq> mData=null;
    int pageNo = 1;
    NoticeItemAdapter adapter = null;
    ListView listview = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public String s_keyword = null;
    SearchView searchView = null;
    // Adapter 생성

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.content_listview);
        listview.setOnScrollListener(this);
        mData = itemadd(s_keyword);

        adapter = new NoticeItemAdapter(getContext(),mData);
        listview.setAdapter(adapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.content_swipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //item클릭시 이벤트

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListNoticeFaq item = (ListNoticeFaq) parent.getItemAtPosition(position);
                String itemNo = item.getNo();
                Intent intent = new Intent(getActivity().getApplicationContext(), NoticeFaqView.class);
                intent.putExtra("type", "notice");
                intent.putExtra("itemNo",itemNo);
                startActivity(intent);
            }
        });
        return view;
    }


    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && lastitem) {
            ArrayList<ListNoticeFaq> extraItem = itemadd(s_keyword);
            if(extraItem!=null) {
                adapter.addItem(extraItem);
            }
        }
    }
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount-1);
    }

    public ArrayList<ListNoticeFaq> itemadd(String keyword){
        try {
            ArrayList<ListNoticeFaq> lostListItems = (ArrayList<ListNoticeFaq>) new DownLoadList().execute("notice", String.valueOf(pageNo),keyword).get();
            if(lostListItems.size()<1){
                lastitem = false;
                return null;
            }else{
                ++pageNo;
                return lostListItems;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        //1초후에 해당 adapter를 갱신하고 동글뱅이를 닫아준다.setRefreshing(false);
        new Handler().post(new Runnable() {
            @Override
            public void run() {

                //해당 어댑터를 서버와 통신한 값이 나오면 됨
                fragmentRefresh();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void fragmentRefresh(){
        pageNo=1;
        lastitem=true;
        s_keyword = null;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==StaticValues.REQUEST_MODIFIED){
            listview.setSelection(0);
            onRefresh();
        }else if(resultCode==StaticValues.REQUEST_DELETIED){
            listview.setSelection(0);
            onRefresh();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
