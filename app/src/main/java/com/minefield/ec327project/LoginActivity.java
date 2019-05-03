package com.minefield.ec327project;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;


/**
 * login
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.Activity_Login_Number)
    EditText mNum;
    @BindView(R.id.Activity_Login_Password)
    EditText mPass;
    @BindView(R.id.Activity_Login_Login)
    ImageButton   mLogin;
    @BindView(R.id.Activity_Login_Chk)
    CheckBox compatCheckBox;
    int RC_SIGN_IN = 99;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        compatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SpUtils.putBoolean("chk", isChecked);
            }
        });
        //check if remember password checkbox is checked
        if (SpUtils.getBoolean("chk", true)) {
            mNum.setText(SpUtils.getString(StringUtils.PHONE, ""));
            mPass.setText(SpUtils.getString(StringUtils.PASSWORD, ""));
        }
        //check if user entered phone and password
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = mNum.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                if (TextUtils.isEmpty(num) || TextUtils.isEmpty(pass)) {
                    Toast.makeText(LoginActivity.this, "Phone or Password cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                //start login funtion if all fields entered
                Login(num, pass);
            }
        });
        //go to register activity if clicked
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

    public void Login(final String phone, final String pass) {
        CollectionReference collection = FirebaseFirestore.getInstance().collection(StringUtils.User);
        collection.whereEqualTo("phone", phone).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                Log.e("yxs", "Length：" + queryDocumentSnapshots.getDocuments().size());
                for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                    Log.e("yxs", "Looking for data：" + queryDocumentSnapshots.getDocuments().get(i).get("pass"));
                }
                //check database if the username exists
                if (queryDocumentSnapshots.getDocuments().size() == 0) {
                    Utils.showToast("User dose not exists!");
                    return;
                }
                //check if password matches the user
                if (!queryDocumentSnapshots.getDocuments().get(0).get("pass").equals(pass)) {
                    Utils.showToast("Password mistake!");
                    return;
                }
                SpUtils.getBoolean(StringUtils.isLogin, true);
                SpUtils.putString(StringUtils.USER_ID, phone);
                SpUtils.putString(StringUtils.PHONE, phone);
                SpUtils.putString(StringUtils.PASSWORD, pass);
                startActivity(new Intent(mActivity, MainActivity.class));
                Utils.showToast("Login Complete!");
                mActivity.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                SpUtils.putString(StringUtils.USER_ID, user.getPhoneNumber());
                startActivity(new Intent(mActivity, MainActivity.class));
                Utils.showToast("Login Complete!");
                mActivity.finish();
                // ...
            } else {
                Utils.showToast("Login Failed");
            }
        }
    }

}
