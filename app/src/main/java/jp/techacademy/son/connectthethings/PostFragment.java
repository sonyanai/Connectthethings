package jp.techacademy.son.connectthethings;

import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
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
    String bitmapString;
    int idx;
    String key;
    String mUid;

    FirebaseUser user;
    DatabaseReference databaseReference;
    DatabaseReference contentsPathRef;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_post,container,false);

        selectedImageView = (ImageView)v.findViewById(R.id.selectedImage);
        contentsEditText = (EditText)v.findViewById(R.id.contentsEditText);
        spinner = (Spinner)v.findViewById(R.id.spinner);
        sendButton = (Button)v.findViewById(R.id.sendButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Contents = "";
        user = FirebaseAuth.getInstance().getCurrentUser();
        mUid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        contentsPathRef = databaseReference.child(Const.ContentsPATH).child(mUid);





        selectedImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){


                MainActivity activity = (MainActivity)getActivity();
                activity.showChooser();






            }
        });



        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                Bitmap bmp = ((BitmapDrawable)selectedImageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                String bitmapString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);


                Contents = contentsEditText.getText().toString();
                area = (String)spinner.getSelectedItem();
                idx = spinner.getSelectedItemPosition();
                String share = "0";
                String good = "0";
                String negotiation ="0";
                String time = "0";
                String Exclusion = "0";
                //画像取得spinner取得contents取得データベースに投げる

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

                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put(key, datas);
                            contentsPathRef.updateChildren(childUpdates);

                            activity.size=0;
                        }else{
                            //選択してくれ
                        }
                    }else {
                        //大きすぎ
                    }
                }else{
                    //画像を選択してください
                }

            }
        });
    }




}