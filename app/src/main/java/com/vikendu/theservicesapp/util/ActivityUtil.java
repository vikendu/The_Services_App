package com.vikendu.theservicesapp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

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
}
