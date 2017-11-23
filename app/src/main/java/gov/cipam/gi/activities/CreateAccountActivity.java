package gov.cipam.gi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gov.cipam.gi.R;
import gov.cipam.gi.common.SharedPref;
import gov.cipam.gi.firebasemanager.FirebaseAuthentication;
import gov.cipam.gi.model.Users;
import gov.cipam.gi.utils.Constants;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEmailField,mPassField,mNameField;
    private Button mSignupButton;
    ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private static String email,password,name;
    private static String TAG="Create Account";
    private DatabaseReference mUsersDatabase,mUserExists;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference(Constants.KEY_USERS);

        mEmailField =findViewById(R.id.emailField);
        mPassField =findViewById(R.id.passField);
        mNameField =findViewById(R.id.nameField);

        mSignupButton =findViewById(R.id.signupButton);

        mProgressDialog = new ProgressDialog(this);

        mSignupButton.setOnClickListener(this);
    }

    private void signUp(){

        mProgressDialog.setTitle(getString(R.string.register_progress_dialog_title));
        mProgressDialog.setMessage(getString(R.string.register_progress_dialog_message));
        mProgressDialog.setCanceledOnTouchOutside(false);

        email = mEmailField.getText().toString().trim();
        password = mPassField.getText().toString().trim();
        name = mNameField.getText().toString().trim();

        if (TextUtils.isEmpty(email)||TextUtils.isEmpty(password)||TextUtils.isEmpty(name)){

            if (TextUtils.isEmpty(email)){ mEmailField.setError(getString(R.string.email_error));}
            if (TextUtils.isEmpty(password)){ mPassField.setError(getString(R.string.password_error));}
            if (TextUtils.isEmpty(name)){ mNameField.setError(getString(R.string.name_error));}

        }
        else {
            mProgressDialog.show();
            // register user to firebase authentication
            // store name and email to firebase database
            FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(this);
            firebaseAuthentication.startSignUp(email,password,name,mProgressDialog);
        }
    }

    @Override
    public void onClick(View view) {
         int id=view.getId();

         switch (id){
             case R.id.signupButton:
                 signUp();
         }
    }
}
