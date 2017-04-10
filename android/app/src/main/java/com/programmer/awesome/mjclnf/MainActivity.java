package com.programmer.awesome.mjclnf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.programmer.awesome.mjclnf.Record.FindRecord;
import com.programmer.awesome.mjclnf.Record.LostRecord;
import com.programmer.awesome.mjclnf.customObject.StaticValues;
import com.programmer.awesome.mjclnf.login.LoginActivity;
import com.programmer.awesome.mjclnf.login.LoginConnect;
import com.programmer.awesome.mjclnf.customObject.Member;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener ,Fragment_Main.CustomOnClickListener{
    static public Member user;
    public Button m_toggleBtn;
    public TextView m_usernameTv;
    public TextView m_userstatusTv;

    public Fragment newFragment = null;
    public FragmentTransaction transaction = null;

    int mCurrentFragmentIndex;
    private long backKeyPressedTime = 0;
    final String TAG = "MainActivity";
    public final static int FRAGMENT_ONE = 0; // Fragment Main
    public final static int FRAGMENT_TWO = 1; // 공지사항
    public final static int FRAGMENT_THREE = 2; // FAQ
    public final static int FRAGMENT_FOUR = 3; //분실물 목록
    public final static int FRAGMENT_FIVE = 4; // 습득물 목록
    Toast toast;
    ConnectivityManager manager;
    Intent intent;
    TextView toolbar_Textview;
    public static Toolbar toolbar;
    public static SearchView searchView;
    public static Menu actionMenu;
    public static String search_Keyword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //복사
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        CoordinatorLayout appBarMain = (CoordinatorLayout) inflater.inflate(R.layout.app_bar_main, null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //복사 끝
        Intent intent = getIntent();
        int requestIntent = intent.getIntExtra("request",0);
        if(requestIntent!=0){
            String name = intent.getExtras().getString("name");
            String st_num = intent.getExtras().getString("st_num");
            user = new Member(st_num,name);
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        m_toggleBtn= (Button) headerView.findViewById(R.id.loginBtn);
        m_usernameTv= (TextView)headerView.findViewById(R.id.user_name);
        m_userstatusTv = (TextView)headerView.findViewById(R.id.user_num);
        setLoginState(user);
        m_toggleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user instance not exist, do login action.
                if(checkNetwork()){
                    if(user==null) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivityForResult(intent, StaticValues.REQUEST_LOGINACTIVITY);
                    }else{
                        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
                        String key = pref.getString("userkey","");
                        Boolean resultLogout = null;
                        try {
                            resultLogout = new LoginConnect.LogoutAction(MainActivity.this).execute(key).get().booleanValue();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        if(resultLogout){
                            SharedPreferences.Editor ed = pref.edit();
                            ed.remove("userkey");
                            ed.commit();
                            user= null;
                            setLoginState(user);
                        }
                    }
                }
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        mCurrentFragmentIndex = FRAGMENT_ONE;

        fragmentReplace(mCurrentFragmentIndex);

    }
    public void fragmentReplace(int reqNewFragmentIndex) {

        newFragment = null;

        newFragment = getFragment(reqNewFragmentIndex);

        // replace fragment
        transaction = getSupportFragmentManager()
                .beginTransaction();

        transaction.replace(R.id.content_main, newFragment,"my_flagment");

        // Commit the transaction
        transaction.commit();

    }
    private Fragment getFragment(int idx) {
        Fragment fragment = null;

        switch (idx) {
            case FRAGMENT_ONE:
                fragment = new Fragment_Main();
                getSupportActionBar().setTitle("명지전문대학 유실물");
                break;
            case FRAGMENT_TWO:
                fragment = new Fragment_Notice();
                getSupportActionBar().setTitle("공지사항");
                break;
            case FRAGMENT_THREE:
                fragment = new Fragment_Faq();
                getSupportActionBar().setTitle("FAQ");
                break;
            case FRAGMENT_FOUR:
                getSupportActionBar().setTitle("분실물 목록");
                fragment = new Lostlist();
                break;
            case FRAGMENT_FIVE:
                fragment = new Findlist();
                getSupportActionBar().setTitle("습득물 목록");
                break;

            default:
                break;
        }
        return fragment;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (mCurrentFragmentIndex != FRAGMENT_ONE) {
            mCurrentFragmentIndex = FRAGMENT_ONE;
            fragmentReplace(mCurrentFragmentIndex);
        } else if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            super.onBackPressed();
            toast.cancel();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(checkNetwork()){
            if (id == R.id.nav_lostrecord) {
                if(user!=null){
                    Intent intent = new Intent(getApplicationContext(),LostRecord.class);
                    intent.putExtra("user",user);
                    startActivityForResult(intent,StaticValues.REQUEST_LOSTRECORD);
                }else{
                    Toast.makeText(this,"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show();
                }
            } else if (id == R.id.nav_findrecord) {
                if(user!=null){
                    Intent intent = new Intent(getApplicationContext(), FindRecord.class);
                    intent.putExtra("user",user);
                    startActivityForResult(intent,StaticValues.REQUEST_FINDRECORD);
                }else{
                    Toast.makeText(this,"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show();
                }
            } else if (id == R.id.nav_lostlist) {
                mCurrentFragmentIndex = FRAGMENT_FOUR;
                fragmentReplace(mCurrentFragmentIndex);
            } else if (id == R.id.nav_findlist) {
                mCurrentFragmentIndex = FRAGMENT_FIVE;
                fragmentReplace(mCurrentFragmentIndex);
            } else if (id == R.id.nav_notice) {
                mCurrentFragmentIndex = FRAGMENT_TWO;
                fragmentReplace(mCurrentFragmentIndex);
            } else if (id == R.id.nav_qna) {
                mCurrentFragmentIndex = FRAGMENT_THREE;
                fragmentReplace(mCurrentFragmentIndex);
            }
        }else{
            networkErrorMsg();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onClick(View v) {

    }
    private void networkErrorMsg() {
        Toast.makeText(this, "인터넷 연결상태를 확인하세요", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onClicked(View v) {
        if(checkNetwork()){
            switch (v.getId()) {
                case R.id.lostrecode:
                    if(user!=null){
                        Intent intent = new Intent(getApplicationContext(),LostRecord.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this,"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.findrecode:
                    if(user!=null){
                        Intent intent = new Intent(getApplicationContext(), FindRecord.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }else{
                        Toast.makeText(this,"로그인이 필요한 서비스입니다.",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.lostlist:
                    mCurrentFragmentIndex = FRAGMENT_FOUR;
                    fragmentReplace(mCurrentFragmentIndex);
                    break;
                case R.id.findlist:
                    mCurrentFragmentIndex = FRAGMENT_FIVE;
                    fragmentReplace(mCurrentFragmentIndex);
                    break;
                case R.id.main_notice_more:
                    mCurrentFragmentIndex = FRAGMENT_TWO;
                    fragmentReplace(mCurrentFragmentIndex);
                    break;
                case R.id.main_faq_more:
                    mCurrentFragmentIndex = FRAGMENT_THREE;
                    fragmentReplace(mCurrentFragmentIndex);
                    break;
            }
        }else{
            networkErrorMsg();
        }
    }
    public void showGuide() {
        toast = Toast.makeText(this,
                "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case StaticValues.REQUEST_LOGINACTIVITY:
                if(resultCode==StaticValues.REQUEST_OK)
                {
                    String key = data.getStringExtra("userkey");
                    class RunnableForUser implements Runnable{
                        Handler handler = new Handler();
                        String key;
                        RunnableForUser(String key){
                            this.key = key;
                        }
                        @Override
                        public void run() {
                            try {
                                String jsonText = new LoginConnect.LoginCheckToKey().execute(key).get().toString();
                                JSONObject jsonObject = new JSONObject(jsonText);
                                String st_num = jsonObject.getString("st_num");
                                String name = jsonObject.getString("st_name");
                                user = new Member(st_num,name);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        setLoginState(user);
                                    }
                                });
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Thread t = new Thread(new RunnableForUser(key));
                    t.start();
                }
                break;
            case StaticValues.REQUEST_LOSTRECORD:
                if(resultCode==StaticValues.REQUEST_OK)
                {
                    Lostlist lostlist = (Lostlist) getSupportFragmentManager().findFragmentByTag("my_flagment");
                    if(lostlist !=null && lostlist.isVisible()){
                        lostlist.onRefresh();
                    }
                }
                break;
            case StaticValues.REQUEST_FINDRECORD:
                if(resultCode==StaticValues.REQUEST_OK)
                {
                    Findlist findlist = (Findlist) getSupportFragmentManager().findFragmentByTag("my_flagment");
                    if(findlist !=null && findlist.isVisible()){
                        findlist.onRefresh();
                    }
                }
                break;
        }
    }


    public void setLoginState(Member user){
        if(user==null){
            m_toggleBtn.setBackground(getDrawable(this,R.drawable.lock));
            m_usernameTv.setText("Guest");
            m_userstatusTv.setText("");
        }else{
            m_toggleBtn.setBackground(getDrawable(this,R.drawable.lock_open));
            m_usernameTv.setText(user.getName());
            m_userstatusTv.setText(user.getStnum());
        }
    }
    public static final Drawable getDrawable(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 21) {
            return ContextCompat.getDrawable(context, id);
        } else {
            return context.getResources().getDrawable(id);
        }
    }
    private boolean checkNetwork() {
        // low level sdk에서 오류날 수 있을듯
        manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if(activeNetwork==null){
            return false;
        }else {
            return true;
        }
    }

}
