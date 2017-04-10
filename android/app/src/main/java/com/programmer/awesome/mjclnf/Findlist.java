package com.programmer.awesome.mjclnf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.programmer.awesome.mjclnf.customObject.ListItem;
import com.programmer.awesome.mjclnf.customObject.StaticValues;
import com.programmer.awesome.mjclnf.serverConnect.DownLoadList;

import java.util.ArrayList;

/**
 * Created by HyunSoo on 2016-10-01.
 */

public class Findlist extends Fragment implements AbsListView.OnScrollListener ,View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener, AsyncResponse {
    boolean lastItemVisibleFlag = false;
    boolean lastitem = true;
    private ArrayList<ListItem> mData=null;
    int pageNo = 1;
    private ListView listview;
    private FindItemAdapter adapter;
    private String s_keyword = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private SearchView searchView = null;
    View mFooterView = null;
    ProgressBar progressBar = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.findlist, container, false);
        // Adapter 생성
        // 리스트뷰 참조 및 Adapter달기
        mFooterView = inflater.inflate(R.layout.list_view_footer_layout,null);
        progressBar = (ProgressBar)mFooterView.findViewById(R.id.list_loading);
        listview = (ListView) view.findViewById(R.id.findlistview);
        listview.setOnScrollListener(this);
        listview.addFooterView(mFooterView);
        mData = new ArrayList<>();
        adapter = new FindItemAdapter(getContext(),mData);
        itemadd(s_keyword);
        listview.setAdapter(adapter);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.findswipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListItem item = (ListItem) parent.getItemAtPosition(position);

                String titleStr = "["+item.getCategory()+"] "+item.getTitle();
                String itemNo = item.getNo();

                Intent intent = new Intent(getActivity().getApplicationContext(),ItemView.class);
                intent.putExtra("location","습득장소");
                intent.putExtra("location_date","습득일자");
                intent.putExtra("title", titleStr);
                intent.putExtra("type", "found");
                intent.putExtra("itemNo",itemNo);
                startActivityForResult(intent, StaticValues.REQUEST_ITEMVIEW);
            }
        });

    return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && lastitem) {
            itemadd(s_keyword);
        }if(!lastitem){
            listview.removeFooterView(mFooterView);
        }

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//현재 화면에 보이는 첫번째 리스트 아이템의 번호(firstVisibleItem) + 현재 화면에 보이는 리스트 아이템의 갯수(visibleItemCount)가 리스트 전체의 갯수(totalItemCount) -1 보다 크거나 같을때
        lastItemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount-1);
    }

    public void itemadd(String keyword){
        DownLoadList downLoadList = new DownLoadList();
        downLoadList.delegate = Findlist.this;
        downLoadList.execute("found", String.valueOf(pageNo),keyword);
    }

    @Override
    public void processFinish(Object output) {
        ArrayList<ListItem> lostListItems = (ArrayList<ListItem>) output;
        if(lostListItems.size()>0){
            if(lostListItems.size()<15){
                listview.removeFooterView(mFooterView);
                Toast.makeText(getContext(),"마지막 게시물입니다.",Toast.LENGTH_SHORT);
            }
            adapter.addItem(lostListItems);
            ++pageNo;
        }else
            lastitem=false;
    }
    @Override
    public void processFinish(String output) {

    }

    @Override
    public void onClick(View v) {

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_view_menu,menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();
        menu.findItem(R.id.action_search).setVisible(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                pageNo=1;
                lastitem=true;
                mData = new ArrayList<>();
                adapter = new FindItemAdapter(getContext(),mData);
                itemadd(query);
                listview.setAdapter(adapter);
                s_keyword = query;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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
