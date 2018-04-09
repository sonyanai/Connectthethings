package jp.techacademy.son.connectthethings;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int CHOOSER_REQUEST_CODE = 100;

    public MessageFragment fragmentMessage;
    public NotificationFragment fragmentNotification;
    public ProfileFragment fragmentProfile;
    public SearchFragment fragmentSearch;
    public TimeLineFragment fragmentTimeLine;
    private FirebaseUser user;
    Uri uri;
    public long size;
    Uri mPictureUri;

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


/*
            // パーミッションの許可状態を確認する
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
                } else {
                    // 許可されていないので許可ダイアログを表示する
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                    return;
                }
            } else {
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
            }

*/


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.postButton:
                user = FirebaseAuth.getInstance().getCurrentUser();
                if(user==null) {
                    intentLogin();
                }else{
                  //  onSelfCheck();
                    PostFragment fragmentPost = new PostFragment();
                    FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                    transactions.replace(R.id.container, fragmentPost);
                    transactions.commit();
                }
                break;
        }
        return false;
    }

    public void intentLogin(){
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void onSelfCheck() {
        // パーミッションの許可状態を確認する
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                showChooser();
            } else {
                // 許可されていないので許可ダイアログを表示する
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
                return;
            }
        } else {
            showChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("ANDROID", "許可された");
                    PostFragment fragmentPost = new PostFragment();
                    FragmentTransaction transactions = getSupportFragmentManager().beginTransaction();
                    transactions.replace(R.id.container, fragmentPost);
                    transactions.commit();
                } else {
                    Log.d("ANDROID", "許可されなかった");
                }
                break;
            default:
                break;
        }
    }

    public void showChooser() {
        // ギャラリーから選択するIntent
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_PICK);
        //galleryに飛ばして選択させる
        startActivityForResult(Intent.createChooser(galleryIntent,"画像/動画を選択"), CHOOSER_REQUEST_CODE);
    }


    //選択した結果を受け取る
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {


            if (resultCode != RESULT_OK) {
                if (mPictureUri != null) {
                    getContentResolver().delete(mPictureUri, null, null);
                    mPictureUri = null;
                }
                return;
            }

            // 画像を取得
            Uri uri = (data == null || data.getData() == null) ? mPictureUri : data.getData();

            // URIからBitmapを取得する
            Bitmap image;
            try {
                ContentResolver contentResolver = getContentResolver();

                //サイズを取得する
                size=0;
                String abc = getPath(this,uri);
                File fileSize = new File(abc);
                size = fileSize.length();

                InputStream inputStream = contentResolver.openInputStream(uri);
                image = BitmapFactory.decodeStream(inputStream);

                Bitmap bbb = Bitmap.createBitmap(image);

                PostFragment.selectedImageView.setImageBitmap(bbb);
                ReProfileFragment.reIconImageView.setImageBitmap(bbb);

                inputStream.close();
            } catch (Exception e) {
                return;
            }



/*
            // 取得したBimapの長辺を500ピクセルにリサイズする
            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            float scale = Math.min((float) 500 / imageWidth, (float) 500 / imageHeight); // (1)

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            Bitmap resizedImage =  Bitmap.createBitmap(image, 0, 0, imageWidth, imageHeight, matrix, true);
*/








            mPictureUri = null;





/*


            //選択されたのがnullでない場合
            if (data.getData() != null) {


                ClipData clipData = data.getClipData();
                if(clipData != null){
                    try {
                        size = 0;
                        //エラーが出なかった時にしたい処理
                        ClipData.Item item = clipData.getItemAt(0);

                        uri = item.getUri();

                        //サイズを取得する
                        String abc = getPath(this,uri);
                        File fileSize = new File(abc);
                        size = fileSize.length();
                        InputStream in = getContentResolver().openInputStream(uri);
                        Bitmap img = BitmapFactory.decodeStream(in);
                        //ファイルを開いたら閉じなければならない(書き込むときはtry-catch}のあとに書く)
                        in.close();
                        // 選択した画像[i]を表示
                        PostFragment.selectedImageView.setImageBitmap(img);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        //エラー処理
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //  }
                }
            }else{
                //ここで動画取得
                Log.d("aaa","動画取得");
            }
            */
        }
    }





    public static String getPath(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        String[] columns = { MediaStore.Images.Media.DATA };
        Cursor cursor = contentResolver.query(uri, columns, null, null, null);
        cursor.moveToFirst();
        String path = cursor.getString(0);
        cursor.close();
        return path;
    }





}

