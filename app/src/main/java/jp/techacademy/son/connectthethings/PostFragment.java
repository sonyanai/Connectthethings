package jp.techacademy.son.connectthethings;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by taiso on 2018/04/03.
 */

public class PostFragment extends Fragment {
    public static final String TAG = "PostFragment";

    public static ImageView selectedImageView;
    EditText contentsEditText;
    Spinner spinner;
    Button sendButton;
    String Contents;
    String area;
    //String bitmapString;
    int idx;
    String key;
    String mUid;
    TextView backText;


    FirebaseUser user;
    DatabaseReference databaseReference;
    DatabaseReference contentsPathRef;

    final Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);
    final int hour = calendar.get(Calendar.HOUR_OF_DAY);
    final int minute = calendar.get(Calendar.MINUTE);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_post,container,false);

        selectedImageView = (ImageView)v.findViewById(R.id.selectedImage);
        contentsEditText = (EditText)v.findViewById(R.id.contentsEditText);
        spinner = (Spinner)v.findViewById(R.id.spinner);
        sendButton = (Button)v.findViewById(R.id.sendButton);
        backText = (TextView)v.findViewById(R.id.backTextView);


        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Contents = "";
        user = FirebaseAuth.getInstance().getCurrentUser();
        mUid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        contentsPathRef = databaseReference.child(Const.ContentsPATH);

        selectedImageView.setImageDrawable(null);





        selectedImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                MainActivity activity = (MainActivity)getActivity();
                //activity.showChooser();
                activity.onSelfCheck();





            }
        });

        backText.setClickable(true);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeLineFragment fragmentTimeLine = new TimeLineFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentTimeLine,TimeLineFragment.TAG)
                        .commit();
            }
        });



        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                BitmapDrawable drawable = (BitmapDrawable) selectedImageView.getDrawable();
                Bitmap bmp = drawable.getBitmap();

                //Bitmap bmp = ((BitmapDrawable)selectedImageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                String bitmapString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                Contents = contentsEditText.getText().toString();
                area = (String)spinner.getSelectedItem();
                idx = spinner.getSelectedItemPosition();

                String share = "0";
                String good = "0";
                String negotiation ="0";
                String Exclusion = "0";
                String area = (String)spinner.getSelectedItem();

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String userName = sp.getString(Const.NameKEY, "");


                String dateString = year + "/" + String.format("%02d", (month + 1)) + "/" + String.format("%02d", day);
                String timeString = String.format("%02d", hour) + ":" + String.format("%02d", minute);
                String time = dateString + timeString;


                MainActivity activity = (MainActivity)getActivity();


                if(activity.size>0){
                    if (activity.size<6000000){
                        if (idx!=0){

                            Map<String, String> datas = new HashMap<String, String>();
                            key = contentsPathRef.push().getKey();
                            datas.put("share",share);
                            datas.put("good",good);
                            datas.put("negotiation",negotiation);
                            datas.put("time",time);
                            datas.put("Contents",Contents);
                            datas.put("bitmapString",bitmapString);
                            datas.put("Exclusion",Exclusion);
                            datas.put("mUid",mUid);
                            datas.put("userName" ,userName);
                            datas.put("area" ,area);

                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put(key, datas);
                            contentsPathRef.updateChildren(childUpdates);

                            contentsEditText.getText().clear();
                            selectedImageView.setImageDrawable(null);

                            //送信完了
                            Snackbar.make(v, "送信が完了しました", Snackbar.LENGTH_LONG).show();


                            activity.size=0;
                        }else{
                            //選択してくれ
                            Snackbar.make(v, "分野を選択してください", Snackbar.LENGTH_LONG).show();
                        }
                    }else {
                        //大きすぎ
                        Snackbar.make(v, "画像の容量が大き過ぎます", Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    //画像を選択してください
                    Snackbar.make(v, "画像を選択してください", Snackbar.LENGTH_LONG).show();
                }

            }
        });
    }




}