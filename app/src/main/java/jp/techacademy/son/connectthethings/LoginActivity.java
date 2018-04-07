package jp.techacademy.son.connectthethings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taiso on 2018/04/03.
 */

public class LoginActivity extends AppCompatActivity {

 //   private RewardedVideoAd mRewardedVideoAd;

    EditText mEmailEditText;
    EditText mPasswordEditText;
    EditText UserNameEditText;

    String key;
    String UserName;

    FirebaseAuth mAuth;
    OnCompleteListener<AuthResult> mCreateAccountListener;
    OnCompleteListener<AuthResult> mLoginListener;
    DatabaseReference mDataBaseReference;
    DatabaseReference userRef;

    // アカウント作成時にフラグを立て、ログイン処理後に名前をFirebaseに保存する
    boolean mIsCreateAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


   /*     MobileAds.initialize(this, "ca-app-pub-7661426638199440/7496406716");

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);




        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
            }
            @Override
            public void onRewardedVideoStarted() {
                //動画開始
            }
            @Override
            public void onRewardedVideoAdOpened() {
                //動画開始する
            }
            @Override
            public void onRewardedVideoAdClosed() {
                //動画を閉じたとき
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onRewarded(RewardItem reward) {
                //動画を見終わったとき
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onRewardedVideoAdLeftApplication() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });*/
/*
        loadRewardedVideoAd();
*/

        mDataBaseReference = FirebaseDatabase.getInstance().getReference();

        // FirebaseAuthのオブジェクトを取得する
        mAuth = FirebaseAuth.getInstance();

        // アカウント作成処理のリスナー
        mCreateAccountListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // 成功した場合
                    // ログインを行う
                    FirebaseUser user = mAuth.getCurrentUser();
                    userRef = mDataBaseReference.child(Const.UsersPATH);

                    if (mIsCreateAccount) {
                        // アカウント作成の時は表示名をFirebaseに保存する
                        String email = mEmailEditText.getText().toString();
                        String password = mPasswordEditText.getText().toString();
                        login(email, password);

                        View view = findViewById(android.R.id.content);
                        Snackbar.make(view, "アカウント作成に成功しました", Snackbar.LENGTH_LONG).show();


                        String mUid = user.getUid();

                        UserName = UserNameEditText.getText().toString();
                        Map<String,String> data = new HashMap<String,String>();

                        String Follow = "0";
                        String Follower = "0";
                        String PostCount = "0";
                        String Evaluation = "0";
                        String EvaluationPeople = "0";
                        String FavArea = "未入力";
                        String Comment = "未入力";
                        String iconBitmapString = "未設定";



                        data.put("mUid",mUid);
                        data.put("UserName",UserName);
                        data.put("Follow",Follow);
                        data.put("Follower",Follower);
                        data.put("PostCount",PostCount);
                        data.put("Evaluation",Evaluation);
                        data.put("EvaluationPeople",EvaluationPeople);
                        data.put("FavArea",FavArea);
                        data.put("Comment",Comment);
                        data.put("IconBitmapString",iconBitmapString);

                        Map<String,Object> childUpdates = new HashMap<>();
                        childUpdates.put(mUid,data);
                        userRef.updateChildren(childUpdates);


                        // 表示名をPrefarenceに保存する
                        saveName(UserName);

                    } else {
                        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot snapshot) {
                                Map data = (Map) snapshot.getValue();
                                saveName((String)data.get("name"));
                            }
                            @Override
                            public void onCancelled(DatabaseError firebaseError) {
                            }
                        });
                    }

                } else {

                    // 失敗した場合
                    // エラーを表示する
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "アカウント作成に失敗しました", Snackbar.LENGTH_LONG).show();

                }
            }
        };

        // ログイン処理のリスナー
        mLoginListener = new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "ログインに成功しました", Snackbar.LENGTH_LONG).show();



                } else {
                    // 失敗した場合
                    // エラーを表示する
                    View view = findViewById(android.R.id.content);
                    Snackbar.make(view, "ログインに失敗しました", Snackbar.LENGTH_LONG).show();
                }
            }
        };

        // UIの準備
        setTitle("ログイン");

        mEmailEditText = (EditText) findViewById(R.id.emailText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordText);
        UserNameEditText = (EditText) findViewById(R.id.UserNameEditText);


        Button createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出てたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);



                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();
                UserName = UserNameEditText.getText().toString();

                if (email.length() != 0 && password.length() >= 6 && UserName.length() > 0) {
                    // ログイン時に表示名を保存するようにフラグを立てる
                    mIsCreateAccount = true;

                    createAccount(email, password);
                } else {
                    // エラーを表示する
                    Snackbar.make(v, "正しく入力してください", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードが出てたら閉じる
                InputMethodManager im = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                im.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                String email = mEmailEditText.getText().toString();
                String password = mPasswordEditText.getText().toString();

                if (email.length() != 0 && password.length() >= 6 && UserName.length() > 0) {
                    // フラグを落としておく
                    mIsCreateAccount = false;

                    login(email, password);
                } else {
                    // エラーを表示する
                    Snackbar.make(v, "正しく入力してください", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createAccount(String email, String password) {

        // アカウントを作成する
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(mCreateAccountListener);
        UserName = UserNameEditText.getText().toString();
   /*     //動画開始？
        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }*/
    }

    private void login(String email, String password) {

        // ログインする
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(mLoginListener);

        //動画開始？
       /* if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }*/

    }

    private void saveName(String name) {
        // Preferenceに保存する
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(Const.NameKEY, name);
        editor.commit();
    }

    /*private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-7661426638199440/7496406716",
                new AdRequest.Builder().build());
    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
*/
}