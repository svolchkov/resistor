package com.example.sergeyv.resistor;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sergeyv.resistor.ColorPickerDialog;

import java.util.ArrayList;

/**
 * Created by sergeyv on 30/04/2017.
 */

public class ColorPicker extends Fragment implements View.OnClickListener {
    private static final int DIALOG_FRAGMENT = 0;
    public int mColor;
    private ImageView mSwatchImage;
    OnDataPass dataPasser;
    ArrayList<Integer> colors;
    int columns = 0;
    //TODO: Save instance state
    //private OnColorSelectedListener mOnColorSelectedListener;

//    /**
//     * Interface for a callback when a color square is selected.
//     */
//    public interface OnColorSelectedListener {
//
//        /**
//         * Called when a specific color square has been selected.
//         */
//        void onColorSelected(int color);
//    }

//    public ColorPicker(){
//
//    };
//
//    public ColorPicker(Context context, int color, boolean checked) {
//        super(context);
//        mColor = color;
//        //mOnColorSelectedListener = listener;
//
//        LayoutInflater.from(context).inflate(R.layout.color_picker, this);
//        mSwatchImage = (ImageView) findViewById(R.id.color_picker);
//        setColor(color);
//        setOnClickListener(this);
//
//    }

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        dataPasser = (OnDataPass) a;
    }

//    @Override
//    public void onSaveInstanceState(final Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putInt("color", mColor);
//    }
//
//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        if (savedInstanceState != null) {
//            //probably orientation change
//            mColor = (Integer) savedInstanceState.getInt("color");
//        } else {
//            if (mColor != 0) {
//                //returning from backstack, data is fine, do nothing
//            } else {
//                //newly created, compute data
//                mColor = Color.BLACK;
//            }
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //mColor = getArguments().getInt("color");
        mColor = Color.BLACK;
        View view = inflater.inflate(R.layout.color_picker, container, false);
        mSwatchImage = (ImageView) view.findViewById(R.id.color_picker_swatch);
        setColor(mColor);
        view.setOnClickListener(this);
        return view;
    }

    // sets the list of colours and the number palette columns
    public void setColorsAndColumns(ArrayList<Integer> colors, int columns){
        this.colors = colors;
        this.columns = columns;
    }

    public void setColor(int color) {
        // sets colour picker colour displayed in main activity
        GradientDrawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            drawable = (GradientDrawable) ContextCompat.getDrawable(getContext(), R.drawable.circle);
        }else{
            drawable = (GradientDrawable) ContextCompat.getDrawable(getActivity(), R.drawable.circle);
        }

        drawable.setColor(color);
        mColor = color;
        mSwatchImage.setImageDrawable(drawable);
    }


    @Override
    public void onClick(View v) {
//        if (mOnColorSelectedListener != null) {
//            mOnColorSelectedListener.onColorSelected(mColor);
//        }
        // opens colour picker dialog
        if (colors == null || columns == 0) return;
        ColorPickerDialog dialog = ColorPickerDialog.newInstance(
                ColorPickerDialog.SELECTION_SINGLE,
                colors,
                columns, // Number of columns
                ColorPickerDialog.SIZE_SMALL);
        dialog.setTargetFragment(this, DIALOG_FRAGMENT);
        dialog.show(getFragmentManager(), "FragmentDialog");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // passes the colour selected in the colour picker dialog to the main activity
        switch(requestCode) {
            case DIALOG_FRAGMENT:
                //Toast.makeText(getActivity(),"in progress",Toast.LENGTH_LONG);
                if (resultCode == Activity.RESULT_OK) {
                    if (data != null){
                        if (data.hasExtra("color")){
                            this.setColor(data.getIntExtra("color",Color.RED));
                            dataPasser.onDataPass(this.mColor);
                        }
                    }
                    // After Ok code.
                } else if (resultCode == Activity.RESULT_CANCELED){
                    // After Cancel code.
                }

                break;
        }
    }

    // data pass interface to be implemented by main activity
    public interface OnDataPass {
        public void onDataPass(int color);
    }
}

