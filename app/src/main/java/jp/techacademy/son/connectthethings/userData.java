package jp.techacademy.son.connectthethings;

/**
 * Created by taiso on 2018/04/04.
 */

public class userData {

    private String mUid;
    private String mUserName;
    private String mFollow;
    private String mFollower;
    private String mPostCount;
    private String mEvaluation;
    private String mEvaluationPeople;
    private String mFavArea;
    private String mComment;
    private String mIconBitmapString;


    public String getUid(){
        return mUid;
    }

    public String getUserName(){
        return mUserName;
    }


    public String getFollow(){
        return mFollow;
    }

    public String getFollower(){
        return mFollower;
    }

    public String getPostCount(){
        return mPostCount;
    }

    public String getEvaluation(){
        return mEvaluation;
    }
    public String getEvaluationPeople(){
        return mEvaluationPeople;
    }
    public String getFavArea(){
        return mFavArea;
    }
    public String getComment(){
        return mComment;
    }
    public String getIconBitmapString(){
        return mIconBitmapString;
    }

    public userData(String uid, String UserName, String Follow, String Follower, String PostCount, String Evaluation,String EvaluationPeople,String FavArea, String Comment, String IconBitmapString ) {
        mUid = uid;
        mUserName = UserName;
        mFollow = Follow;
        mFollower = Follower;
        mPostCount = PostCount;
        mEvaluation = Evaluation;
        mEvaluationPeople = EvaluationPeople;
        mFavArea = FavArea;
        mComment = Comment;
        mIconBitmapString = IconBitmapString;
    }
}
