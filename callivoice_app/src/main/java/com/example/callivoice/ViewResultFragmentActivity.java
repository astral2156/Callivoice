package com.example.callivoice;

/**
 * Created by Lss on 2018-05-06.
 */
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewResultFragmentActivity extends Fragment {
    TextView[] textView = new TextView[6];
    ImageView imageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {  super.onCreate(savedInstanceState);  }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_view_result_fragment,null);

        return view;
    }

    public void changeFont()
    {
        textView[0] = (TextView)getActivity().findViewById(R.id.fragment_text_thefaceshop);
        textView[1] = (TextView)getActivity().findViewById(R.id.fragment_text_nanum);
        textView[2] = (TextView)getActivity().findViewById(R.id.fragment_text_seoul);
        textView[3] = (TextView)getActivity().findViewById(R.id.fragment_text_menbal);
        textView[4] = (TextView)getActivity().findViewById(R.id.fragment_text_leesunsin);
        textView[5] = (TextView)getActivity().findViewById(R.id.fragment_text_bingrae);


        Bundle extra = getArguments();
        String str = extra.getString("string", "아아아");
        int font = extra.getInt("font", 1);

        for(int i=0; i<6; i++) textView[i].setText("");
        textView[font-1].setText(str);

    }

    public void changeBackground()
    {
        imageView = (ImageView)getActivity().findViewById(R.id.fragment_background);
    }
}