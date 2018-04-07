package jp.techacademy.son.connectthethings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by taiso on 2018/04/07.
 */


class ViewHolder {
    TextView userNameTextView;
    TextView areaTextView;
    TextView contentsTextView;
    TextView imageTextView;
    TextView timeTextView;
    TextView shareCountTextView;
    TextView goodCountTextView;
    TextView negotiationCountTextView;
}


public class ContentsArrayListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private int layoutId;
    private ArrayList<contentsData> contentsArrayList = new ArrayList<contentsData>();

    public ContentsArrayListAdapter(Context context, int layoutId) {
        super();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.layoutId = layoutId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        String mShare = contentsArrayList.get(position).getShare();
        String mGood = contentsArrayList.get(position).getGood();
        String mNegotiation = contentsArrayList.get(position).getNegotiation();
        String mTime = contentsArrayList.get(position).getTime();
        String mContents = contentsArrayList.get(position).getContents();
        String mBitmapString = contentsArrayList.get(position).getBitmapString();
        String mUid = contentsArrayList.get(position).getUid();
        String mUserName = contentsArrayList.get(position).getUserName();
        String mArea = contentsArrayList.get(position).getArea();


        ViewHolder holder;
        if (convertView == null) {
            // main.xml の <GridView .../> に grid_items.xml を inflate して convertView とする
            convertView = inflater.inflate(layoutId, parent,
                    false);
            // FolderViewHolder を生成
            holder = new ViewHolder();
            holder.userNameTextView = (TextView) convertView.findViewById(R.id.userNameTextView);
            holder.areaTextView = (TextView) convertView.findViewById(R.id.areaTextView);
            holder.contentsTextView = (TextView) convertView.findViewById(R.id.contentsTextView);
            holder.imageTextView = (TextView) convertView.findViewById(R.id.contentsImageView);
            holder.timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
            holder.shareCountTextView = (TextView) convertView.findViewById(R.id.shareCountTextView);
            holder.goodCountTextView = (TextView) convertView.findViewById(R.id.goodCountTextView);
            holder.negotiationCountTextView = (TextView) convertView.findViewById(R.id.negotiationCountTextView);
            convertView.setTag(holder);

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }



/*
        if(mUserName!=null){
            holder.companyTextView.setText(mCompanyName);
        }
        if(mBlackName!=null){
            holder.blackNameTextView.setText(mBlackName);
        }
        if(mCase!=null){
            holder.caseTextView.setText(mCase);
        }
        if(mDate!=null){
            holder.dateTextView.setText(mDate);
        }
*/

        return convertView;
    }

    @Override
    public int getCount() {
        // List<String> imgList の全要素数を返す
        return contentsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        //return null;
        return contentsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
        //return articleDataArrayList.get(position).getId();
    }
    public void setContentsDataArrayList(ArrayList<contentsData> contentsDataArrayList){
        this.contentsArrayList = contentsDataArrayList;
    }
}




