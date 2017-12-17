package gov.cipam.gi.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;
import gov.cipam.gi.R;
import gov.cipam.gi.firebasemanager.FirebaseAuthentication;
import gov.cipam.gi.firebasemanager.GoogleAuthentication;
import gov.cipam.gi.utils.Constants;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mCreateAccount, mForgotPass;
    private EditText mEmailField, mPassField;
    Button mSignInButton;
    CircleImageView imageView;
    ProgressDialog mProgressDialog;
    private FirebaseAuth mAuth;
    private String email, password;
    private static String TAG = "SignInActivity";
    GoogleSignInClient mGoogleSignInClient;
    GoogleAuthentication gAuth;
    private static final int RC_SIGN_IN = 9001;
    SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        launchActivity();
        mAuth = FirebaseAuth.getInstance();
        mPrefs = getPreferences(MODE_PRIVATE);

        mCreateAccount =  findViewById(R.id.signupText);
        mForgotPass =  findViewById(R.id.forgotPass);

        mEmailField =  findViewById(R.id.signInEmailField);
        mPassField =  findViewById(R.id.signInPassField);
        mSignInButton =  findViewById(R.id.sign_in_button);
        imageView=findViewById(R.id.signin_image_view);

        mProgressDialog = new ProgressDialog(this);

        getWindow().setStatusBarColor(Color.TRANSPARENT);

        imageView.setImageResource(R.drawable.image1);
        mSignInButton.setOnClickListener(this);
        mForgotPass.setOnClickListener(this);
        mCreateAccount.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void forgotPassword() {
        email = mEmailField.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.email_error));
            Toast.makeText(SignInActivity.this, getString(R.string.toast_empty_email),
                    Toast.LENGTH_SHORT).show();
        } else {
            mEmailField.setError(getString(R.string.empty_email_error));
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignInActivity.this, getString(R.string.toast_password_reset_email),
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Email sent.");
                            }
                        }
                    });
        }
    }

    private void GoogleSignIn() {
        mProgressDialog.show();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                gAuth = new GoogleAuthentication(this);
                gAuth.firebaseAuthWithGoogle(account.getEmail(),account.getDisplayName(),account,mProgressDialog);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                Toast.makeText(this, "failed."+e,
                        Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();

            }
        }
    }
    public void launchActivity() {
        SharedPreferences preferences =
                getSharedPreferences(Constants.MY_PREFERENCES, MODE_PRIVATE);

        if (!preferences.getBoolean(Constants.ONBOARDING_COMPLETE, false)) {

            startActivity(new Intent(this, IntroActivity.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(this, HomePageActivity.class));
            finish();
        }
    }

    private void startSignIn(){

        mProgressDialog.setTitle(getString(R.string.register_progress_dialog_title));
        mProgressDialog.setMessage(getString(R.string.logging_in));
        mProgressDialog.setCanceledOnTouchOutside(false);

        email = mEmailField.getText().toString().trim();
        password = mPassField.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email)) {
                mEmailField.setError(getString(R.string.email_error));
            }
            if (TextUtils.isEmpty(password)) {
                mPassField.setError(getString(R.string.password_error));
            }

        } else {
            mProgressDialog.show();

            FirebaseAuthentication firebaseAuthentication=new FirebaseAuthentication(this);
            firebaseAuthentication.startSignIn(email,password,mProgressDialog);
        }
    }


    @Override
    public void onClick(View view) {
        int id=view.getId();

        switch (id){
            case R.id.sign_in_button:
               startSignIn();
                break;

            case R.id.forgotPass:
                forgotPassword();
                break;

            case R.id.signupText:
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                break;

            case R.id.google_signin_button:
                GoogleSignIn();
                break;
        }
    }
}
