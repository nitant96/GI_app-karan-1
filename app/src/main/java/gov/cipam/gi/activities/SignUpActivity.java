package gov.cipam.gi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;
import gov.cipam.gi.R;
import gov.cipam.gi.firebasemanager.FirebaseAuthentication;
import gov.cipam.gi.utils.Constants;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEmailField,mPassField,mNameField;
    private TextView mLoginTextView;
    Button mSignupButton;
    ProgressDialog mProgressDialog;
    FirebaseAuth mAuth;
    CircleImageView imageView;
    private String email,password,name;
    private static String TAG="Create Account";
    private DatabaseReference mUsersDatabase,mUserExists;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mUsersDatabase = FirebaseDatabase.getInstance().getReference(Constants.KEY_USERS);

        mEmailField =findViewById(R.id.signUpEmailField);
        mPassField =findViewById(R.id.signUpPassField);
        mNameField =findViewById(R.id.nameField);

        mLoginTextView=findViewById(R.id.sign_in_text);

        mSignupButton =findViewById(R.id.sign_up_button);

        imageView=findViewById(R.id.ImageViewSignUp);
        mProgressDialog = new ProgressDialog(this);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        imageView.setImageResource(R.drawable.image1);

        mSignupButton.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);
    }

    private void signUp(){

        mProgressDialog.setTitle(R.string.register_progress_dialog_title);
        mProgressDialog.setMessage(String.valueOf(R.string.register_progress_dialog_message));
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
             case R.id.sign_up_button:
                 signUp();
                 break;
             case R.id.sign_in_text:
                 startActivity(new Intent(this,SignInActivity.class));
                 finish();
                 break;
         }
    }
}
