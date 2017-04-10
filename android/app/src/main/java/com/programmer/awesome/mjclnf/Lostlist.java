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
import android.widget.AbsListView.OnScrollListener;
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

public class Lostlist extends Fragment implements OnScrollListener , View.OnClickListener ,SwipeRefreshLayout.OnRefreshListener,AsyncResponse{
    boolean lastItemVisibleFlag = false;
    boolean lastitem = true;
    private ArrayList<ListItem> mData=null;
    int pageNo = 1;
    LostItemAdapter adapter = null;
    ListView listview = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    public String s_keyword = null;
    SearchView searchView = null;
    View mFooterView = null;
    ProgressBar progressBar = null;
    // Adapter 생성

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.lostlist, container, false);
        mFooterView = inflater.inflate(R.layout.list_view_footer_layout,null);
        progressBar = (ProgressBar)mFooterView.findViewById(R.id.list_loading);
        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) view.findViewById(R.id.lostlistview);
        listview.setOnScrollListener(this);
        listview.addFooterView(mFooterView);
        mData = new ArrayList<>();
        adapter = new LostItemAdapter(getContext(),mData);
        listview.setAdapter(adapter);
        itemadd(s_keyword);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.lostswipeRefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //item클릭시 이벤트
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListItem item = (ListItem) parent.getItemAtPosition(position);

                String titleStr = "["+item.getCategory()+"] "+item.getTitle();
                String itemNo = item.getNo();

                Intent intent = new Intent(getActivity().getApplicationContext(), ItemView.class);
                intent.putExtra("user",MainActivity.user);
                intent.putExtra("location", "분실장소");
                intent.putExtra("location_date", "분실일자");
                intent.putExtra("title", titleStr);
                intent.putExtra("type", "lost");
                intent.putExtra("itemNo", itemNo);
                startActivityForResult(intent, StaticValues.REQUEST_ITEMVIEW);
            }
        });
        return view;
    }


    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(scrollState == OnScrollListener.SCROLL_STATE_IDLE && lastItemVisibleFlag && lastitem) {
            itemadd(s_keyword);
        }
        if(!lastitem){
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
        downLoadList.delegate = Lostlist.this;
        downLoadList.execute("lost", String.valueOf(pageNo),keyword);
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
                mData = new ArrayList<ListItem>();
                adapter = new LostItemAdapter(getContext(),mData);
                if(mData.size()<15) listview.removeFooterView(mFooterView);
                listview.setAdapter(adapter);
                itemadd(query);
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
