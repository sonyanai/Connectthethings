package jp.techacademy.son.connectthethings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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

public class TimeLineFragment extends Fragment {
    public static final String TAG = "TimeLineFragment";

    public ArrayList<String> mUidArrayList;
    public ArrayList<contentsData> mContentsDataArrayList;
    DatabaseReference databaseReference;
    DatabaseReference userRef;
    //DatabaseReference contentsRef;
    String aaa = "0";
    private ContentsArrayListAdapter bAdapter;
    ListView ContentsList;








    //EventListenerの設定と初期化
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

            bAdapter.setContentsDataArrayList(mContentsDataArrayList);
            ContentsList.setAdapter(bAdapter);
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
        View v = inflater.inflate(R.layout.fragment_timeline,container,false);


        databaseReference = FirebaseDatabase.getInstance().getReference();
        userRef = databaseReference.child(Const.UsersPATH);
        //contentsRef = databaseReference.child(Const.ContentsPATH).child(aaa);
        mUidArrayList = new ArrayList<String>();
        mContentsDataArrayList = new ArrayList<contentsData>();
        ContentsList = (ListView)v.findViewById(R.id.ContentsList);
        bAdapter = new ContentsArrayListAdapter(this.getActivity(), R.layout.contents_list);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //EventListenerの設定と初期化
        ChildEventListener uEventListener = new ChildEventListener() {
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

                userData userdata = new userData(mUid, UserName, Follow, Follower, PostCount, Evaluation, EvaluationPeople, FavArea, Comment,IconBitmapString);
                //mUidArrayList.add(userdata.getUid());

                aaa = userdata.getUid();

                //contentsRef.addChildEventListener(bEventListener);
                DatabaseReference contentsRef =  databaseReference.child(Const.ContentsPATH).child(aaa);
                contentsRef.addChildEventListener(bEventListener);


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



        userRef.addChildEventListener(uEventListener);

/*

        for(String aaa : mUidArrayList){
            contentsRef.addChildEventListener(bEventListener);
        }

*/

    }





}
