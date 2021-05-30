package com.vikendu.theservicesapp.util;

import android.widget.AutoCompleteTextView;

public class ResourceUtil {

    public static String getString(AutoCompleteTextView acTextView) {
        return acTextView.getText().toString();
    }
}
