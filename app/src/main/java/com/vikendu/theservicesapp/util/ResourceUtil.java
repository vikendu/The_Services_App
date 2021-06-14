package com.vikendu.theservicesapp.util;

import android.widget.AutoCompleteTextView;
import com.google.firebase.database.FirebaseDatabase;

public class ResourceUtil {

    public static String getString(AutoCompleteTextView acTextView) {
        return acTextView.getText().toString();
    }

    public static FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app");
    }
}
