package com.android.mcsin.lab12;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * Created by mcsin on 2015/12/11.
 */
public class MyFragment1 extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View myFragmentView = inflater.inflate(R.layout.fragmentlayout2, container, false);

        return myFragmentView;
    }


}
