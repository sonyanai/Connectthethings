package jp.techacademy.son.connectthethings;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import java.io.ByteArrayOutputStream;

/**
 * Created by taiso on 2018/04/09.
 */

public class ReProfileFragment extends Fragment{
    public static final String TAG = "ProfileFragment";


    public static ImageView reIconImageView;
    EditText reFavAreaEditText;
    EditText reUserNameEditText;
    EditText reCommentEditText;
    Button okButton;
    String newName;
    String newFavArea;
    String newComment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_reprofile,container,false);

        reIconImageView = (ImageView)v.findViewById(R.id.reIconImageView);
        reFavAreaEditText = (EditText)v.findViewById(R.id.reFavAreaEditText);
        reUserNameEditText = (EditText)v.findViewById(R.id.UserNameEditText);
        reCommentEditText = (EditText)v.findViewById(R.id.reCommentEditText);
        okButton = (Button)v.findViewById(R.id.okButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //profileからintentでデータを受け取る

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String name = sp.getString(Const.NameKEY, "");
        String favArea = sp.getString(Const.FavAreaKEY, "");
        String comment = sp.getString(Const.CommentKEY, "");


        reUserNameEditText.setText(name);
        reFavAreaEditText.setText(favArea);
        reCommentEditText.setText(comment);

        final String iconBitmapString = sp.getString(Const.IconBitmapStringKEY, "");
        byte[] bytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
        if(bytes.length != 0){
            Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length).copy(Bitmap.Config.ARGB_8888,true);
            reIconImageView.setImageBitmap(image);
        }


        reIconImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                activity.showChooser();
            }
        });





        view.findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

           //データを取得してpreferenceに保存

                newName =reUserNameEditText.getText().toString();
                newFavArea =reFavAreaEditText.getText().toString();
                newComment =reCommentEditText.getText().toString();

                BitmapDrawable drawable = (BitmapDrawable) reIconImageView.getDrawable();
                Bitmap bmp = drawable.getBitmap();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);
                String bitmapString = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());

                SharedPreferences.Editor editor = sp.edit();
                editor.putString(Const.NameKEY, newName);
                editor.putString(Const.FavAreaKEY, newFavArea);
                editor.putString(Const.CommentKEY, newComment);
                editor.putString(Const.IconBitmapStringKEY, bitmapString);

                editor.commit();


                Snackbar.make(v, "保存しました", Snackbar.LENGTH_LONG).show();

                ProfileFragment fragmentProfile = new ProfileFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentProfile,ProfileFragment.TAG)
                        .commit();

            }
        });

    }





}
