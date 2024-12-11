package com.pro.huyfood.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pro.huyfood.R;
import com.pro.huyfood.constant.Constant;
import com.pro.huyfood.constant.GlobalFunction;
import com.pro.huyfood.databinding.ActivitySignUpBinding;
import com.pro.huyfood.model.User;
import com.pro.huyfood.prefs.DataStoreManager;
import com.pro.huyfood.utils.StringUtil;

public class SignUpActivity extends BaseActivity {

    private ActivitySignUpBinding mActivitySignUpBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignUpBinding.getRoot());



        mActivitySignUpBinding.imgBack.setOnClickListener(v -> onBackPressed());
        mActivitySignUpBinding.layoutSignIn.setOnClickListener(v -> finish());
        mActivitySignUpBinding.btnSignUp.setOnClickListener(v -> onClickValidateSignUp());
    }

    private void onClickValidateSignUp() {
        String strEmail = mActivitySignUpBinding.edtEmail.getText().toString().trim();
        String strPassword = mActivitySignUpBinding.edtPassword.getText().toString().trim();

        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignUpActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {



            signUpUser(strEmail, strPassword);
        }
    }

    private void signUpUser(String email, String password) {
        showProgressDialog(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            User userObject = new User(user.getEmail(), password);

                            DataStoreManager.setUser(userObject);
                            GlobalFunction.gotoMainActivity(this);
                            finishAffinity();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, getString(R.string.msg_sign_up_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
