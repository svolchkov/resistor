package com.example.sergeyv.resistor;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;

/**
 * Created by sergeyv on 30/04/2017.
 */

//public class ColorPickerTable extends TableLayout {

//    private String mDescription;
//    private String mDescriptionSelected;
//
//    private int mSwatchLength;
//    private int mMarginSize;
//    private int mNumColumns;
//
//    public ColorPickerTable(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public ColorPickerTable(Context context) {
//        super(context);
//    }
//
//    /**
//     * Initialize the size, columns, and listener.  Size should be a pre-defined size (SIZE_LARGE
//     * or SIZE_SMALL) from ColorPickerDialogFragment.
//     */
//    public void init(int size, int columns) {
//        mNumColumns = columns;
//        Resources res = getResources();
////        if (size == ColorPickerDialog.SIZE_LARGE) {
////            mSwatchLength = res.getDimensionPixelSize(R.dimen.color_swatch_large);
////            mMarginSize = res.getDimensionPixelSize(R.dimen.color_swatch_margins_large);
////        } else {
////            mSwatchLength = res.getDimensionPixelSize(R.dimen.color_swatch_small);
////            mMarginSize = res.getDimensionPixelSize(R.dimen.color_swatch_margins_small);
////        }
//    }
//
//    private TableRow createTableRow() {
//        TableRow row = new TableRow(getContext());
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT);
//        row.setLayoutParams(params);
//        return row;
//    }
//
//    /**
//     * Adds swatches to table in a serpentine format.
//     */
//    public void drawPalette(ArrayList<Integer> colors, ArrayList<Integer> selectedColor) {
//        if (colors == null) {
//            return;
//        }
//
//        this.removeAllViews();
//        int tableElements = 0;
//        int rowElements = 0;
//        int rowNumber = 0;
//
//        // Fills the table with swatches based on the array of colors.
//        TableRow row = createTableRow();
//        for (int color : colors) {
//            View colorSwatch = createColorSwatch(color, selectedColor);
//            addSwatchToRow(row, colorSwatch, rowNumber);
//
//            tableElements++;
//            rowElements++;
//            if (rowElements == mNumColumns) {
//                addView(row);
//                row = createTableRow();
//                rowElements = 0;
//                rowNumber++;
//            }
//        }
//
//        addView(row);
//    }
//
//    /**
//     * Appends a swatch to the end of the row for even-numbered rows (starting with row 0),
//     * to the beginning of a row for odd-numbered rows.
//     */
//    private static void addSwatchToRow(TableRow row, View swatch, int rowNumber) {
//        row.addView(swatch);
//    }
//
//
//    /**
//     * Creates a color swatch.
//     */
//    private ColorPicker createColorSwatch(int color, ArrayList<Integer> selectedColor) {
//
//        ColorPicker view = new ColorPicker(getContext(), color,
//                selectedColor.contains(color));
//        TableRow.LayoutParams params = new TableRow.LayoutParams(mSwatchLength, mSwatchLength);
//        params.setMargins(mMarginSize, mMarginSize, mMarginSize, mMarginSize);
//        view.setLayoutParams(params);
//        return view;
//    }
//}
