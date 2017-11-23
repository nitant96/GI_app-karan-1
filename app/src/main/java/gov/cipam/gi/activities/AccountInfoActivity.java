package gov.cipam.gi.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import gov.cipam.gi.R;
import gov.cipam.gi.common.SharedPref;
import gov.cipam.gi.model.Users;
import gov.cipam.gi.utils.Constants;

public class AccountInfoActivity extends BaseActivity implements View.OnClickListener {
    private TextView mchangePass,mUpdatePass;
    private TextInputLayout mChangePassFieldLayout;
    private EditText mNameField,mEmailField,mChangePassField;
    private String name,email;
    private DatabaseReference mDatabaseUser;
    private static String TAG = "AccountInfoActivity";
    private static String user_id;
    private FirebaseAuth mAuth;
    private Users user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        setUpToolbar(this);
        mDatabaseUser = FirebaseDatabase.getInstance().getReference(Constants.KEY_USERS);
        mAuth = FirebaseAuth.getInstance();

        mNameField =findViewById(R.id.nameField);
        mEmailField =findViewById(R.id.emailField);
        mChangePassFieldLayout =findViewById(R.id.changePassFieldLayout);
        mChangePassField = findViewById(R.id.changePassField);
        mchangePass =findViewById(R.id.changePass);
        mUpdatePass =findViewById(R.id.updatePass);

        mchangePass.setOnClickListener(this);
        mUpdatePass.setOnClickListener(this);

        }

    @Override
    protected int getToolbarID() {
        return R.id.account_info_toolbar;
    }

    @Override
    protected void onStart() {
        Users user = SharedPref.getSavedObjectFromPreference(AccountInfoActivity.this,Constants.KEY_USER_INFO,Constants.KEY_USER_DATA,Users.class);
        if(user!=null) {
            mEmailField.setText(user.getEmail());
            mNameField.setText(user.getName());

        }
        else {
            Toast.makeText(AccountInfoActivity.this,getString(R.string.toast_signin_again_request),Toast.LENGTH_LONG).show();
        }

        super.onStart();
    }


    @Override
    public void onClick(View view) {
        int id =view.getId();

        switch (id){
            case R.id.changePass:
                mChangePassFieldLayout.setVisibility(View.VISIBLE);
                mUpdatePass.setVisibility(View.VISIBLE);
                break;
            case R.id.updatePass:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String newPassword = mChangePassField.getText().toString().trim();

                if(TextUtils.isEmpty(newPassword)){
                    mChangePassField.setError(getString(R.string.new_password_enter_error));}

                else{
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(AccountInfoActivity.this, getString(R.string.toast_password_change_success),
                                                Toast.LENGTH_SHORT).show();
                                        mChangePassFieldLayout.setVisibility(View.GONE);
                                        mUpdatePass.setVisibility(View.GONE);
                                        Log.d(TAG, "User password updated.");
                                    }
                                }
                            });

                }
        }
    }
}
