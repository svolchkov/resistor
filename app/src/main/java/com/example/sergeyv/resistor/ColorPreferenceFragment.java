package com.example.sergeyv.resistor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ColorPreferenceFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ColorPreferenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorPreferenceFragment extends Fragment {

    // This is the fragment linking main activity menu options with colour picker dialogs.
    // It does not have UI.

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    OnColorPreferenceChanged colorPref;
    private static final int BACK_COLOR = 0;
    private static final int TEXT_COLOR = 1;
    private int mColor;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ColorPreferenceFragment() {
        // Required empty public constructor
    }


    // Below are constructors and various template functions generated by Android Studio.
    // Leaving them in place for now in case some further improvement will need to be implemented
    // later.

    public static ColorPreferenceFragment newInstance(String param1, String param2 ) {
        ColorPreferenceFragment fragment = new ColorPreferenceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        colorPref = (OnColorPreferenceChanged) context;
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    protected void setColor(int color) {
        mColor = color;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if colour has been selected using the dialog, pass it back to main activity
        switch(requestCode) {
            case BACK_COLOR:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null){
                        if (data.hasExtra("color")){
                            this.setColor(data.getIntExtra("color",Color.RED));
                            colorPref.onColorPreferenceChanged("BACK_COLOR",this.mColor);
                        }
                    }
                }

                break;
            case TEXT_COLOR:
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null){
                        if (data.hasExtra("color")){
                            this.setColor(data.getIntExtra("color",Color.RED));
                            colorPref.onColorPreferenceChanged("TEXT_COLOR",this.mColor);
                        }
                    }
                }

                break;
        }
    }

    // interface to be implemented by main activity
    public interface OnColorPreferenceChanged {
        public void onColorPreferenceChanged(String setting, int color);
    }
}