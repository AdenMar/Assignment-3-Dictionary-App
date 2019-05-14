package edu.quinnipiac.ser210.dictionaryapp;

/*
Dictionary App
5/13/19
Created by: Aden Mariyappa
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


// creates, attatches, and inflates fragment_main
public class MainFragment extends Fragment {

    WordListener myActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myActivity = (WordListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }
    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // creates interface with search button click
    static interface WordListener{
        void buttonClickedSearch(View view);
    }

}
