package com.vikendu.theservicesapp.util;

import android.widget.AutoCompleteTextView;
import com.google.firebase.database.FirebaseDatabase;

public class ResourceUtil {

    /**
     * type cast AutoCompleteTextView field to string
     * @param acTextView which you want to convert
     * @return String
     */
    public static String getString(AutoCompleteTextView acTextView) {
        return acTextView.getText().toString();
    }

    /**
     * return the location of the rtdb
     * since it is in Singapore, needs to be
     * explicitly declared
     * @return FirebaseDatabase object
     */
    public static FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://the-services-app-default-rtdb.asia-southeast1.firebasedatabase.app");
    }
}
