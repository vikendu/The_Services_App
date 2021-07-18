package com.vikendu.theservicesapp.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class ActivityUtil {

    /**
     * call this to hide the keyboard
     * after entering data into any field
     * @param context context of the activity from which it is called
     * @param textView the field after which you want to hide the keyboard
     */
    public static void hideKeyBoard(Context context, TextView textView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }

    /**
     * This function creates a snackbar with no Action Buttons
     * @param view view from the activity it is called
     * @param message the String to be displayed
     * @return Snackbar object/run .show()
     */
    public static Snackbar createNoActionSnackbar(View view, String message) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        return snackbar;
    }
}
