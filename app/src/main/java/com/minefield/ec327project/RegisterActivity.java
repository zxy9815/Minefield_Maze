package com.minefield.ec327project;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import butterknife.BindView;

/**
 * Register
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.Activity_Regi_Phone)
    EditText mPhone;
    @BindView(R.id.Activity_Regi_Pass)
    EditText mPass;
    @BindView(R.id.Activity_Regi_AgainPass)
    EditText mAgainPass;
    private FirebaseFirestore instance;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        setTitle("Register");
        instance = FirebaseFirestore.getInstance();
        findViewById(R.id.Activity_Regi_regi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mPhone.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                String pass_again = mAgainPass.getText().toString().trim();
                if (phone.isEmpty() || pass.isEmpty() || pass_again.isEmpty()) {
                    Utils.showToast("The message cannot be empty!");
                    return;
                }

                if (!pass.equals(pass_again)) {
                    Utils.showToast("The passwords do not match!");
                    return;
                }
                userExists(phone, pass);
            }
        });
    }


    void userExists(final String phone, final String pass) {
        CollectionReference collection = instance.collection(StringUtils.User);
        collection.whereEqualTo("phone", phone).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                Log.e("yxs", "Length：" + queryDocumentSnapshots.getDocuments().size());
                for (int i = 0; i < queryDocumentSnapshots.getDocuments().size(); i++) {
                    Log.e("yxs", "Data：" + queryDocumentSnapshots.getDocuments().get(i).get("pass"));
                }
                if (queryDocumentSnapshots.getDocuments().size() != 0) {
                    Utils.showToast("User already exists!");
                    return;
                }
                AddUser(phone, pass);
            }
        });
    }


    void AddUser(String phone, String pass) {
        UserBean userBean = new UserBean(phone, pass);
        instance.collection(StringUtils.User).add(userBean).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Utils.showToast("Register Success!");
                mActivity.finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Utils.showToast("Register Failure!");
            }
        });
    }



}
