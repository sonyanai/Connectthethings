package jp.techacademy.son.connectthethings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by taiso on 2018/04/03.
 */

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    ImageView iconImageView;
    TextView FavAreaTextView;
    TextView FollowTextView;
    TextView FollowerTextView;
    TextView EvaluationTextView;
    TextView EvaluationPeopleTextView;
    TextView commentTextView;
    String comment;
    ListView PersonalList;
    String mUid;
    String mImage;
    userData userdata;
    public ArrayList<userData> mUserDataArrayList;

    FirebaseUser user;
    DatabaseReference databaseReference;
    DatabaseReference userRef;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        iconImageView = (ImageView)v.findViewById(R.id.iconImageView);
        FavAreaTextView = (TextView)v.findViewById(R.id.FavAreaTextView);
        FollowTextView = (TextView)v.findViewById(R.id.FollowTextView);
        FollowerTextView = (TextView)v.findViewById(R.id.FollowerTextView);
        EvaluationTextView = (TextView)v.findViewById(R.id.FollowTextView);
        EvaluationPeopleTextView = (TextView)v.findViewById(R.id.EvaluationPeopleTextView);
        commentTextView = (TextView)v.findViewById(R.id.commentTextView);
        PersonalList = (ListView)v.findViewById(R.id.PersonalList);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mUid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userRef = databaseReference.child(Const.UsersPATH);
        mUserDataArrayList = new ArrayList<userData>();




        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //mEventListenerの設定と初期化
        ChildEventListener mEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                HashMap map = (HashMap) dataSnapshot.getValue();
                final String mUid = (String) map.get("mUid");
                final String UserName = (String) map.get("UserName");
                final String Follow = (String) map.get("Follow");
                final String Follower = (String) map.get("Follower");
                final String PostCount = (String) map.get("PostCount");
                final String Evaluation = (String) map.get("Evaluation");
                final String EvaluationPeople = (String) map.get("EvaluationPeople");
                final String FavArea = (String) map.get("FavArea");
                final String Comment = (String) map.get("Comment");
                final String IconBitmapString = (String) map.get("IconBitmapString");

                userdata = new userData(mUid, UserName, Follow, Follower, PostCount, Evaluation, EvaluationPeople, FavArea, Comment,IconBitmapString);
                mUserDataArrayList.add(userdata);
                for(userData aaa : mUserDataArrayList){
                    if (userdata.getUid()==mUid){
                        comment = userdata.getComment();
                        mImage = userdata.getIconBitmapString();

                        FavAreaTextView.setText("好きな分野"+userdata.getFavArea());
                        FollowTextView.setText("add"+userdata.getFollow());
                        FollowerTextView.setText("added"+userdata.getFollower());
                        EvaluationTextView.setText("評価"+userdata.getEvaluation());
                        EvaluationPeopleTextView.setText("評価した人数"+userdata.getEvaluationPeople());
                        commentTextView.setText("コメント"+comment);


                        byte[] bytes = Base64.decode(mImage,Base64.DEFAULT);
                        if(bytes.length != 0){
                            Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length).copy(Bitmap.Config.ARGB_8888,true);
                            iconImageView.setImageBitmap(image);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        userRef.addChildEventListener(mEventListener);



        //イベントリスナーで値を引っ張ってくる

/*


*/
    }


}
