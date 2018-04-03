package jp.techacademy.son.connectthethings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

/**
 * Created by taiso on 2018/04/03.
 */

public class PostFragment extends Fragment {
    public static final String TAG = "PostFragment";

    ImageView selectedImageView;
    EditText contentsEditText;
    Spinner spinner;
    Button sendButton;


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



        view.findViewById(R.id.sendButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                //画像取得spinner取得contents取得データベースに投げる




            }
        });
    }



}