package jp.techacademy.son.connectthethings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by taiso on 2018/04/03.
 */

public class SearchFragment extends Fragment {
    public static final String TAG = "SearchFragment";

    EditText searchEditText;
    ImageButton closeButton;
    Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_search,container,false);

        searchEditText = (EditText)v.findViewById(R.id.searchEditText);
        closeButton = (ImageButton)v.findViewById(R.id.closeButton);
        searchButton = (Button)v.findViewById(R.id.searchButton);

        return v;
    }

    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);






    }


}
