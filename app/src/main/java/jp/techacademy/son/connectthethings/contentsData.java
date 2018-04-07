package jp.techacademy.son.connectthethings;

/**
 * Created by taiso on 2018/04/07.
 */

public class contentsData {

    private String mShare;
    private String mGood;
    private String mNegotiation;
    private String mTime;
    private String mContents;
    private String mBitmapString;
    private String mExclusion;
    private String mUid;
    private String mUserName;
    private String mArea;


    public String getShare(){
        return mShare;
    }
    public String getGood(){
        return mGood;
    }
    public String getNegotiation(){
        return mNegotiation;
    }
    public String getTime(){
        return mTime;
    }
    public String getContents(){
        return mContents;
    }
    public String getBitmapString(){
        return mBitmapString;
    }
    public String getExclusion(){
        return mExclusion;
    }
    public String getUid(){
        return mUid;
    }
    public String getUserName(){
        return mUserName;
    }
    public String getArea(){
        return mArea;
    }


    public contentsData( String share, String good, String negotiation, String time, String contents, String bitmapString, String exclusion,String uid, String UserName, String area ) {

        mShare = share;
        mGood = good;
        mNegotiation = negotiation;
        mTime = time;
        mContents = contents;
        mBitmapString = bitmapString;
        mExclusion = exclusion;
        mUid = uid;
        mUserName = UserName;
        mArea = area;

    }
}
