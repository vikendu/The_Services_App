package com.vikendu.theservicesapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "Firebase";

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private FirebaseAuth mAuth;
    private SharedPreferences loginPref;

    DialogInterface.OnClickListener dialogClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.login_email);
        mPasswordView = (EditText) findViewById(R.id.login_password);

        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.integer.login || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        mAuth = FirebaseAuth.getInstance();
        loginPref = getSharedPreferences("login",MODE_PRIVATE);

        if(loginPref.getBoolean("logged",false)){
            // TODO: put the function goTo<NameOfActivity>Activity() here
        }

        dialogClickListener = (dialog, which) -> {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:

                    loginPref.edit().putBoolean("logged",true).apply();
                    // TODO: put the function goTo<NameOfActivity>Activity() here
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    showLoginFailed("Failed to Login");
                    break;
            }
        };
    }
    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        attemptLogin();
    }
    // Executed when Register button pressed
    public void registerNewUser(View v) {
        // TODO: put the explicit intent to go to the RegisterActivity.java here
//        Intent intent = new Intent(this, com.vikendu.theservicesapp.RegisterActivity.class);
//        finish();
//        startActivity(intent);
    }

    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Due to limitations on Android 8.0+ there will be a persistant notification to make sure the App stays in Memory.\nTo continue using the application click Accept.").setPositiveButton("Accept", dialogClickListener)
                .setNegativeButton("Decline", dialogClickListener);

        if(email.equals("") || password.equals("")) { //TODO: If has empty body RECTIFY
        } else {
            Toast.makeText(this, "Login in progress", Toast.LENGTH_SHORT).show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
                Log.d("Login", "Login" + task.isSuccessful());

                if(!task.isSuccessful()) {
                    Log.d("Login","Login failed" + task.getException());
                    showLoginFailed("Failed to Login");
                } else {
                    builder.show();
                }
            });
        }
    }
    // TODO: rebuild the following function to redirect the user to the correct activity
//    private void goTo<NameOfActivity>Activity()
//    {
//        Intent intent = new Intent(LoginActivity.this, MainChatActivity.class);
//        finish();
//        startActivity(intent);
//    }
    private void showLoginFailed(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error Logging in...")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}