package com.vikendu.theservicesapp.util;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ActivityUtil {
    public static void hideKeyBoard(Context context, TextView textView) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
    }
}
