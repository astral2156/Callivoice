package com.example.callivoice;

/**
 * Created by Lss on 2018-05-06.
 */
import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class ViewResultFragmentActivity extends Fragment {
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_view_result_fragment,null);
        textView = (TextView)view.findViewById(R.id.fragment_text);

        return view;
    }
}

/*
public class  ViewResultFragmentActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result_fragment);

        RelativeLayout temp = (RelativeLayout)this.findViewById(R.id.activity_view_result_fragment);
        ViewGroup.LayoutParams lp = temp.getLayoutParams();

        lp.height = getResources().getDisplayMetrics().heightPixels;
        lp.width = lp.height;
    }
*/

