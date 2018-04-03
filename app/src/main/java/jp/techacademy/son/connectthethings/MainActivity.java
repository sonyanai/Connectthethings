package jp.techacademy.son.connectthethings;

import android.Manifest;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public MessageFragment fragmentMessage;
    public NotificationFragment fragmentNotification;
    public ProfileFragment fragmentProfile;
    public SearchFragment fragmentSearch;
    public TimeLineFragment fragmentTimeLine;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.item_TimeLine:
                    fragmentTimeLine = new TimeLineFragment();
                    transaction.replace(R.id.container, fragmentTimeLine);
                    transaction.commit();
                    return true;

                case R.id.item_Search:
                    fragmentSearch = new SearchFragment();
                    transaction.replace(R.id.container, fragmentSearch);
                    transaction.commit();
                    return true;

                case R.id.item_Notification:
                    fragmentNotification = new NotificationFragment();
                    transaction.replace(R.id.container, fragmentNotification);
                    transaction.commit();
                    return true;

                case R.id.item_Message:
                    fragmentMessage = new MessageFragment();
                    transaction.replace(R.id.container, fragmentMessage);
                    transaction.commit();
                    return true;


                case R.id.item_Profile:
                    fragmentProfile = new ProfileFragment();
                    transaction.replace(R.id.container, fragmentProfile);
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        //Fragmentで最初の画面の設定をする
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Fragmentを作成します
        TimeLineFragment fragmentTimeLines = new TimeLineFragment();
        // コードからFragmentを追加
        // Fragmentの追加や削除といった変更を行う際は、Transactionを利用します
        // 新しく追加を行うのでaddを使用します
        // メソッドの1つ目の引数は対象のViewGroupのID、2つ目の引数は追加するfragment
        transaction.add(R.id.container, fragmentTimeLines);
        // 最後にcommitを使用することで変更を反映します
        transaction.commit();

        //BottomNavigationViewの定義して設置する
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_view);
        //リスナーのセット
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }




}

