package com.example.sergeyv.resistor;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.sergeyv.resistor.ColorPickerDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

public class MainActivity extends AppCompatActivity implements ColorPicker.OnDataPass,
ColorPreferenceFragment.OnColorPreferenceChanged{
    TextView resistorValue, toleranceValue;
    ImageView bandOne, bandTwo, bandThree, tolerance;
    private Toolbar toolbar;
    // resistor band colours
    static ArrayList<Integer> colors;
    // tolerance band colours
    static ArrayList<Integer> toleranceColors;
    // background colours
    static ArrayList<Integer> backgroundColors;
    ColorPicker pickerOne,pickerTwo,pickerThree,tolerancePicker;
    int SILVER;
    int GOLD;
    // links colours to image names
    static Map<Integer,String> colorImages = new HashMap<Integer,String>();
    //links colour pickers to image views
    static Map<ColorPicker,ImageView> pickersToViews = new HashMap<ColorPicker,ImageView>();
    ArrayList<ColorPicker> colorPickers;
    ArrayList<TextView> textViews;
    int backgroundColor;
    int textColor;
    static final int MAX_FONT_SIZE_LANDSCAPE = 30; // max font size is limited in the landscape mode
    public static String MY_PREFS_NAME = "com.example.sergeyv.resistor.prefs"; // name of preferences file

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resistorValue = (TextView) findViewById(R.id.resistorValue);
        toleranceValue = (TextView) findViewById(R.id.toleranceValue);
        textColor = resistorValue.getCurrentTextColor();
        textViews = new ArrayList<TextView>(Arrays.asList(resistorValue,toleranceValue));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        // get custom colours
        int ORANGE = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            ORANGE =MainActivity.this.getResources().getColor(R.color.orange,null);
        }
        else{
            ORANGE =MainActivity.this.getResources().getColor(R.color.orange);
        }

        int BROWN = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            BROWN =MainActivity.this.getResources().getColor(R.color.brown,null);
        }else{
            BROWN =MainActivity.this.getResources().getColor(R.color.brown);
        }
        int VIOLET = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            VIOLET =MainActivity.this.getResources().getColor(R.color.violet,null);
        }else{
            VIOLET =MainActivity.this.getResources().getColor(R.color.violet);
        }

        SILVER = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            SILVER =MainActivity.this.getResources().getColor(R.color.silver,null);
        }else{
            SILVER =MainActivity.this.getResources().getColor(R.color.silver);
        }
        GOLD = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            GOLD =MainActivity.this.getResources().getColor(R.color.gold,null);
        }else{
            GOLD =MainActivity.this.getResources().getColor(R.color.gold);
        }

        colors = new ArrayList<Integer>(Arrays.asList(Color.BLACK,BROWN,Color.RED,
                ORANGE,Color.YELLOW,Color.GREEN, Color.BLUE,VIOLET,Color.GRAY,Color.WHITE));
        toleranceColors = new ArrayList<Integer>(Arrays.asList(GOLD,SILVER));
        backgroundColors = new ArrayList<Integer>(Arrays.asList(Color.RED,
                ORANGE,Color.YELLOW,Color.GREEN, Color.BLUE,VIOLET,Color.GRAY,Color.WHITE));

        pickerOne = (ColorPicker) getFragmentManager().findFragmentById(R.id.colorPicker1);
        // sets the colour pickers' colour palettes
        pickerOne.setColorsAndColumns(colors,5);

        pickerTwo = (ColorPicker) getFragmentManager().findFragmentById(R.id.colorPicker2);
        pickerTwo.setColorsAndColumns(colors,5);

        pickerThree = (ColorPicker) getFragmentManager().findFragmentById(R.id.colorPicker3);
        pickerThree.setColorsAndColumns(colors,5);

        tolerancePicker = (ColorPicker) getFragmentManager().findFragmentById(R.id.colorPicker4);
        tolerancePicker.setColorsAndColumns(toleranceColors,2);

        ArrayList<ImageView> colorImageViews = new ArrayList<ImageView>(Arrays.asList((ImageView) findViewById(R.id.colorOne),
                (ImageView) findViewById(R.id.colorTwo),(ImageView) findViewById(R.id.colorThree),
                (ImageView) findViewById(R.id.Tolerance)));

        colorPickers = new ArrayList<ColorPicker>(Arrays.asList(pickerOne,pickerTwo,pickerThree,tolerancePicker));



        ArrayList allColors = new ArrayList<Integer>(Arrays.asList(Color.BLACK,BROWN,Color.RED,
                ORANGE,Color.YELLOW,Color.GREEN, Color.BLUE,VIOLET,Color.GRAY,Color.WHITE,SILVER,GOLD));
        ArrayList colorFiles = new ArrayList<String>(Arrays.asList("black","brown","red","orange","yellow","green","blue","violet","gray","white","silver","gold"));

        // populate the map of resistor band images
        Iterator<Integer> i1 = allColors.iterator();
        Iterator<String> i2 = colorFiles.iterator();
        while (i1.hasNext() && i2.hasNext()) {
            colorImages.put(i1.next(), i2.next());
        }

        // populate the map linking colour pickers to image views
        Iterator<ColorPicker> j1 = colorPickers.iterator();
        Iterator<ImageView> j2 = colorImageViews.iterator();
        while (j1.hasNext() && j2.hasNext()) {
            pickersToViews.put(j1.next(), j2.next());
        }

        // if the user has set preferences, retrieve them
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        if (prefs != null) {
            setActivityBackgroundColor(prefs.getInt("backColor", Color.WHITE));
            for (TextView t: textViews){
                t.setTextColor(prefs.getInt("textColor", Color.GRAY));
                float size = prefs.getFloat("textSize", 20);
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
                if (this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE
                        && size > MAX_FONT_SIZE_LANDSCAPE){
                    t.setTextSize(TypedValue.COMPLEX_UNIT_SP,MAX_FONT_SIZE_LANDSCAPE);
                }
            }
        }

        // restore saved instance upon orientation change
        if (savedInstanceState != null) {
            setActivityBackgroundColor(savedInstanceState.getInt("backColor", Color.WHITE));
            for (TextView t: textViews){
                t.setTextColor(savedInstanceState.getInt("textColor", Color.GRAY));
                float size = savedInstanceState.getFloat("textSize", 20);
                t.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
                if (this.getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE
                        && size > MAX_FONT_SIZE_LANDSCAPE){
                    t.setTextSize(TypedValue.COMPLEX_UNIT_SP,MAX_FONT_SIZE_LANDSCAPE);
                }

            }
//            pickerOne = (ColorPicker) getFragmentManager().getFragment(savedInstanceState, "pickerOne");
//            pickerTwo = (ColorPicker) getFragmentManager().getFragment(savedInstanceState, "pickerTwo");
//            pickerThree = (ColorPicker) getFragmentManager().getFragment(savedInstanceState, "pickerThree");
//            tolerancePicker = (ColorPicker) getFragmentManager().getFragment(savedInstanceState, "tolerancePIcker");
            pickerOne.setColor(savedInstanceState.getInt("pickerOneColor", Color.BLACK));
            pickerTwo.setColor(savedInstanceState.getInt("pickerTwoColor", Color.BLACK));
            pickerThree.setColor(savedInstanceState.getInt("pickerThreeColor", Color.BLACK));
            tolerancePicker.setColor(savedInstanceState.getInt("tolerancePickerColor", Color.BLACK));

        }

        // calculate the resistor value
        onDataPass(0);
        //backgroundColor = Color.WHITE;
        //setActivityBackgroundColor(backgroundColor);
    }

    @Override
    protected void onStop(){
        // save current user settings to preferences
        super.onStop();
        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt("backColor", getActivityBackgroundColor());
        editor.putInt("textColor", resistorValue.getCurrentTextColor());
        float px = resistorValue.getTextSize();
        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        editor.putFloat("textSize", sp);
        editor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        // The background colour and the text colour options call colour picker dialogues via fragments
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ColorPreferenceFragment fragment = new ColorPreferenceFragment();
        switch (item.getItemId()) {
            case R.id.background_color:
                ArrayList<Integer> bColors = new ArrayList<Integer>();
                bColors.addAll(backgroundColors);
                // determine current text colour and remove it from the list of available background
                //colours
                int temp = resistorValue.getCurrentTextColor();
                if (bColors.indexOf(temp) != -1)
                    bColors.remove(bColors.indexOf(temp));
                ColorPickerDialog dialog = ColorPickerDialog.newInstance(
                        ColorPickerDialog.SELECTION_SINGLE,
                        bColors,
                        4, // Number of columns
                        ColorPickerDialog.SIZE_SMALL);
                fragmentTransaction.add(fragment,"backcolor");
                fragmentTransaction.commit();
                dialog.setTargetFragment(fragment, 0);
                dialog.show(fragmentManager, "FragmentDialog");
                return true;
            case R.id.text_color:
                ArrayList<Integer> textColors = new ArrayList<Integer>();
                textColors.addAll(colors);
                // same text colour as current background colour is not allowed
                int temp2 = getActivityBackgroundColor();
                if (textColors.indexOf(temp2) != -1)
                    textColors.remove(textColors.indexOf(temp2));
                ColorPickerDialog dialogText = ColorPickerDialog.newInstance(
                        ColorPickerDialog.SELECTION_SINGLE,
                        textColors,
                        4, // Number of columns
                        ColorPickerDialog.SIZE_SMALL);
                fragmentTransaction.add(fragment,"textcolor");
                fragmentTransaction.commit();
                dialogText.setTargetFragment(fragment, 1);
                dialogText.show(fragmentManager, "FragmentDialog");
                return true;
            case R.id.text_size:
                // open font size selection dialogue
                fontSizeDialogShow();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("backColor", getActivityBackgroundColor());
        outState.putInt("textColor", resistorValue.getCurrentTextColor());
        float px = resistorValue.getTextSize();
        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        outState.putFloat("textSize", sp);
//        getFragmentManager().putFragment(outState, "pickerOne", pickerOne);
//        getFragmentManager().putFragment(outState, "pickerTwo", pickerTwo);
//        getFragmentManager().putFragment(outState, "pickerThree", pickerThree);
//        getFragmentManager().putFragment(outState, "tolerancePicker", tolerancePicker);

        // saving colour picker colours
        outState.putInt("pickerOneColor", pickerOne.mColor);
        outState.putInt("pickerTwoColor", pickerTwo.mColor);
        outState.putInt("pickerThreeColor", pickerThree.mColor);
        outState.putInt("tolerancePickerColor", tolerancePicker.mColor);
    }

    @Override
    public void onDataPass(int color) {
        // if a colour picker colour is changed, this function will run and set the textviews
        long totalResistance = 0;
        int pickerOneValue = colors.indexOf(pickerOne.mColor);
        int pickerTwoValue = colors.indexOf(pickerTwo.mColor);
        int pickerThreeValue = colors.indexOf(pickerThree.mColor);
        // total resistance formula
        totalResistance = (long)((pickerOneValue * 10 + pickerTwoValue) * Math.pow(10, (double)pickerThreeValue));
        resistorValue.setText(withSuffix(totalResistance) + " Ohms");

        if (toleranceColors.contains(tolerancePicker.mColor)){
                if (tolerancePicker.mColor == GOLD)
                    toleranceValue.setText("Tolerance 5%");
                else if (tolerancePicker.mColor == SILVER)
                    toleranceValue.setText("Tolerance 10%");
            }

        //set colour picker colours
        for (ColorPicker c: colorPickers){
            String imageName = colorImages.get(c.mColor);
            int resId = MainActivity.this.getResources().getIdentifier(imageName, "drawable", MainActivity.this.getPackageName());
            pickersToViews.get(c).setImageResource(resId);
        }


    }

    // this function sets the resistance display format
    public static String withSuffix(long count) {
        if (count < 1000) return "" + count;
        int exp = (int) (Math.log(count) / Math.log(1000));
        double e = count / Math.pow(1000, exp);
        DecimalFormat format = new DecimalFormat("0.#");
        return String.format("%s %c",
                format.format(e),
                "KMGTPE".charAt(exp-1));
    }

    @Override
    public void onColorPreferenceChanged(String pref, int color){
        // calls the functions setting background and text colours
        switch(pref) {
            case "BACK_COLOR":
                setActivityBackgroundColor(color);
                break;
            case "TEXT_COLOR":
                for(TextView t: textViews){
                    t.setTextColor(color);
                }
                textColor = color;
                break;
        }
    }

    public void setActivityBackgroundColor(int color) {
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
        backgroundColor = color;
    }

    public int getActivityBackgroundColor() {
        View view = this.getWindow().getDecorView();
        int color = Color.TRANSPARENT;
        Drawable background = view.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        return color;
    }

    public void fontSizeDialogShow()
    {

        final Dialog d = new Dialog(MainActivity.this);
        d.setTitle("Set font size");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.button1); // Set button
        Button b2 = (Button) d.findViewById(R.id.button2); // Cancel button
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        if (this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
            np.setMaxValue(48);
        }
        else{
            np.setMaxValue(MAX_FONT_SIZE_LANDSCAPE);  //max text size in landscape format is smaller
        }
        np.setMinValue(8);
        float px = resistorValue.getTextSize();
        float sp = px / getResources().getDisplayMetrics().scaledDensity;  // converts pixels to scaled pixels
        np.setValue((int)sp);
        np.setWrapSelectorWheel(false);
        //np.setOnValueChangedListener(this);
        b1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //tv.setText(String.valueOf(np.getValue()));
                for (TextView t:textViews){
                    t.setTextSize(TypedValue.COMPLEX_UNIT_SP,np.getValue());
                }
                d.dismiss();
            }
        });
        b2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();


    }

}
