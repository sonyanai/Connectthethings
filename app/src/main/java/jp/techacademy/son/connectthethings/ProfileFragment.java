package jp.techacademy.son.connectthethings;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by taiso on 2018/04/03.
 */

public class ProfileFragment extends Fragment {
    public static final String TAG = "ProfileFragment";

    public static ImageView iconImageView;
    TextView FavAreaTextView;
    TextView UserNameTextView;
    TextView FollowTextView;
    TextView FollowerTextView;
    TextView EvaluationTextView;
    TextView EvaluationPeopleTextView;
    TextView commentTextView;
    ListView PersonalList;
    String mUid;
    Button rewriteButton;
    public ArrayList<contentsData> mContentsDataArrayList;
    public ArrayList<contentsData> bContentsDataArrayList;
    private ContentsArrayListAdapter bAdapter;

    FirebaseUser user;
    DatabaseReference databaseReference;
    DatabaseReference userRef;
    DatabaseReference ContentsRef;


    //mEventListenerの設定と初期化
    ChildEventListener bEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
            HashMap map = (HashMap) dataSnapshot.getValue();
            final String share = (String) map.get("share");
            final String good = (String) map.get("good");
            final String negotiation = (String) map.get("negotiation");
            final String time = (String) map.get("time");
            final String contents = (String) map.get("Contents");
            final String bitmapString = (String) map.get("bitmapString");
            final String exclusion = (String) map.get("Exclusion");
            final String mUid = (String) map.get("mUid");
            final String UserName = (String) map.get("userName");
            final String area = (String) map.get("area");


            contentsData contentsData = new contentsData( share, good, negotiation, time, contents, bitmapString, exclusion, mUid, UserName, area );
            mContentsDataArrayList.add(contentsData);

            for(contentsData aaa : mContentsDataArrayList){
                if (user.getUid().equals(aaa.getUid())){
                    bContentsDataArrayList.add(aaa);
                }
            }

            bAdapter.setContentsDataArrayList(bContentsDataArrayList);
            PersonalList.setAdapter(bAdapter);
            bAdapter.notifyDataSetChanged();


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_profile,container,false);

        iconImageView = (ImageView)v.findViewById(R.id.iconImageView);
        rewriteButton = (Button)v.findViewById(R.id.rewriteButton);
        FavAreaTextView = (TextView)v.findViewById(R.id.FavAreaTextView);
        UserNameTextView = (TextView)v.findViewById(R.id.userNameTextView);
        FollowTextView = (TextView)v.findViewById(R.id.FollowTextView);
        FollowerTextView = (TextView)v.findViewById(R.id.FollowerTextView);
        EvaluationTextView = (TextView)v.findViewById(R.id.EvaluationTextView);
        EvaluationPeopleTextView = (TextView)v.findViewById(R.id.EvaluationPeopleTextView);
        commentTextView = (TextView)v.findViewById(R.id.commentTextView);
        PersonalList = (ListView)v.findViewById(R.id.PersonalList);
        user = FirebaseAuth.getInstance().getCurrentUser();
        mUid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userRef = databaseReference.child(Const.UsersPATH);
        ContentsRef = databaseReference.child(Const.ContentsPATH);
        mContentsDataArrayList = new ArrayList<contentsData>();
        bContentsDataArrayList = new ArrayList<contentsData>();
        bAdapter = new ContentsArrayListAdapter(this.getActivity(), R.layout.contents_list);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bContentsDataArrayList.clear();
        mContentsDataArrayList.clear();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String name = sp.getString(Const.NameKEY, "");
        UserNameTextView.setText(name);
        String followCount = sp.getString(Const.FollowCountKEY, "");
        FollowTextView.setText(followCount);
        String followerCount = sp.getString(Const.FollowerCountKEY, "");
        FollowerTextView.setText(followerCount);
        //String postCount = sp.getString(Const.PostCountKEY, "");
        //UserNameTextView.setText(postCount);
        String evaluation = sp.getString(Const.EvaluationKEY, "");
        UserNameTextView.setText(evaluation);
        //String evaluationPeople = sp.getString(Const.EvaluationPeopleKEY, "");
        //UserNameTextView.setText(evaluationPeople);
        String favArea = sp.getString(Const.FavAreaKEY, "");
        FavAreaTextView.setText(favArea);
        String comment = sp.getString(Const.CommentKEY, "");
        commentTextView.setText(comment);
        final String iconBitmapString = sp.getString(Const.IconBitmapStringKEY, "");
        byte[] bytes = Base64.decode(iconBitmapString,Base64.DEFAULT);
        if(bytes.length != 0){
            Bitmap image = BitmapFactory.decodeByteArray(bytes,0,bytes.length).copy(Bitmap.Config.ARGB_8888,true);
            iconImageView.setImageBitmap(image);
        }


        ContentsRef.addChildEventListener(bEventListener);

        view.findViewById(R.id.rewriteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ReProfileFragment fragmentReprofile = new ReProfileFragment();
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragmentReprofile,ReProfileFragment.TAG)
                        .commit();



            }
        });



    }


}
