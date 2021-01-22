package com.andyeseapps.lab42;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragment extends Fragment {
    static int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);


        // Change text dynamically
        TextView textView = (TextView) view.findViewById(R.id.testId);
        textView.setText(ScreenSlidePagerActivity.equipmentList.getArrayList().get(counter).toString());
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        counter++;
        return view;
    }



}
