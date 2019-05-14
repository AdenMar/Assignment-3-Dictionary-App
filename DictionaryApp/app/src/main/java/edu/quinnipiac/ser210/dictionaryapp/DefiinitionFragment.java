package edu.quinnipiac.ser210.dictionaryapp;

/*
Dictionary App
5/13/19
Created by: Aden Mariyappa
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DefiinitionFragment extends Fragment {

    public DefiinitionFragment(){

    }

    // when frag is created, inflate the layout fragment_definition
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_definition, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
