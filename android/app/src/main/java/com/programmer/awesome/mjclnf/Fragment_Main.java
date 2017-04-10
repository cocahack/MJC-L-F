package com.programmer.awesome.mjclnf;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.programmer.awesome.mjclnf.customObject.ListNoticeFaq;
import com.programmer.awesome.mjclnf.serverConnect.DownLoadList;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by HyunSoo on 2016-11-03.
 */

public class Fragment_Main extends Fragment implements OnClickListener, AsyncResponse {
    String[] notice_title = {"", "", ""};
    String[] notice_date = {"", "", ""};
    String[] faq_title = {"", "", ""};
    String[] faq_date = {"", "", ""};
    int x = 0;

    public interface CustomOnClickListener {
        public void onClicked(View v);
    }

    private CustomOnClickListener customOnClickListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        Button lostRecode = (Button) view.findViewById(R.id.lostrecode);
        Button findRecode = (Button) view.findViewById(R.id.findrecode);
        Button lostList = (Button) view.findViewById(R.id.lostlist);
        Button findList = (Button) view.findViewById(R.id.findlist);
        lostRecode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        findRecode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        lostList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        findList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });

        TextView tv_notice1 = (TextView) view.findViewById(R.id.main_notice1);
        TextView tv_notice1_date = (TextView) view.findViewById(R.id.main_notice1_date);
        TextView tv_notice2 = (TextView) view.findViewById(R.id.main_notice2);
        TextView tv_notice2_date = (TextView) view.findViewById(R.id.main_notice2_date);
        TextView tv_notice3 = (TextView) view.findViewById(R.id.main_notice3);
        TextView tv_notice3_date = (TextView) view.findViewById(R.id.main_notice3_date);

        TextView tv_faq1 = (TextView) view.findViewById(R.id.main_faq1);
        TextView tv_faq1_date = (TextView) view.findViewById(R.id.main_faq1_date);
        TextView tv_faq2 = (TextView) view.findViewById(R.id.main_faq2);
        TextView tv_faq2_date = (TextView) view.findViewById(R.id.main_faq2_date);
        TextView tv_faq3 = (TextView) view.findViewById(R.id.main_faq3);
        TextView tv_faq3_date = (TextView) view.findViewById(R.id.main_faq3_date);

        TextView tv_notice_more = (TextView) view.findViewById(R.id.main_notice_more);
        TextView tv_faq_more = (TextView) view.findViewById(R.id.main_faq_more);
        tv_notice_more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        tv_faq_more.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        String[] notice = {"notice", "1"};
        String[] faq = {"faq", "1"};
        notice_down(notice);
        faq_down(faq);
        tv_notice1.setText("ㆍ"+notice_title[0]);
        tv_notice2.setText("ㆍ"+notice_title[1]);
        tv_notice3.setText("ㆍ"+notice_title[2]);
        tv_notice1_date.setText(notice_date[0]);
        tv_notice2_date.setText(notice_date[1]);
        tv_notice3_date.setText(notice_date[2]);

        tv_faq1.setText("ㆍ"+faq_title[0]);
        tv_faq2.setText("ㆍ"+faq_title[1]);
        tv_faq3.setText("ㆍ"+faq_title[2]);
        tv_faq1_date.setText(faq_date[0]);
        tv_faq2_date.setText(faq_date[1]);
        tv_faq3_date.setText(faq_date[2]);

        return view;
    }

    public void buttonClicked(View v) {
        customOnClickListener.onClicked(v);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a = null;
        if (context instanceof Activity) {
            a = (Activity) context;
        }
        customOnClickListener = (CustomOnClickListener) a;
    }

    @Override
    public void onClick(View v) {
    }
    public void notice_down(String[] item) {
        try {
            List<ListNoticeFaq> lostListItems = (List<ListNoticeFaq>) new DownLoadList().execute(item).get();
            for (ListNoticeFaq i : lostListItems) {
                notice_title[x] = i.getTitle();
                notice_date[x] = i.getRegitDate();
                if (x==2)
                    break;
                x++;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void faq_down(String[] item) {
        try {
            x = 0;
            List<ListNoticeFaq> lostListItems = (List<ListNoticeFaq>) new DownLoadList().execute(item).get();
            for (ListNoticeFaq i : lostListItems) {
                faq_title[x] = i.getTitle();
                faq_date[x] = i.getRegitDate();
                if (x==2)
                    break;
                x++;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public Fragment_Main() {
        super();
    }

    @Override
    public void processFinish(Object output) {

    }

    @Override
    public void processFinish(String output) {

    }
}
