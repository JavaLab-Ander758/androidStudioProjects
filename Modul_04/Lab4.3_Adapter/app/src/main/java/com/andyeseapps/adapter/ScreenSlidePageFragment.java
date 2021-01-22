package com.andyeseapps.adapter;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragment extends Fragment {
    static int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);


        // Change text dynamically
//        TextView textView = (TextView) view.findViewById(R.id.testId);
//        textView.setText(ScreenSlidePagerActivity.equipmentList.getArrayList().get(counter).toString());
//        textView.setMovementMethod(LinkMovementMethod.getInstance());

        ListView listView = view.findViewById(R.id.testId);
        listView.setTag("a");


        counter++;
        return view;
    }



}
