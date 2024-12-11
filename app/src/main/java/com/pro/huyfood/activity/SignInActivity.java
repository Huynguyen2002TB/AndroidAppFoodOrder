package com.pro.huyfood.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pro.huyfood.R;
import com.pro.huyfood.constant.Constant;
import com.pro.huyfood.constant.GlobalFunction;
import com.pro.huyfood.databinding.ActivitySignInBinding;
import com.pro.huyfood.model.User;
import com.pro.huyfood.prefs.DataStoreManager;
import com.pro.huyfood.utils.StringUtil;

public class SignInActivity extends BaseActivity {

    private ActivitySignInBinding mActivitySignInBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignInBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(mActivitySignInBinding.getRoot());

        mActivitySignInBinding.rdbUser.setChecked(true);//mac dinh o user

        mActivitySignInBinding.layoutSignUp.setOnClickListener(
                v -> GlobalFunction.startActivity(SignInActivity.this, SignUpActivity.class));

        mActivitySignInBinding.btnSignIn.setOnClickListener(v -> onClickValidateSignIn());
        mActivitySignInBinding.tvForgotPassword.setOnClickListener(v -> onClickForgotPassword());
    }

    private void onClickForgotPassword() {
        GlobalFunction.startActivity(this, ForgotPasswordActivity.class);
    }

    private void onClickValidateSignIn() {
        String strEmail = mActivitySignInBinding.edtEmail.getText().toString().trim();
        String strPassword = mActivitySignInBinding.edtPassword.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strPassword)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_password_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            if (mActivitySignInBinding.rdbAdmin.isChecked()) {
                if (!strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                    Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid_admin), Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(strEmail, strPassword);
                }
                return;
            }

            if (strEmail.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                Toast.makeText(SignInActivity.this, getString(R.string.msg_email_invalid_user), Toast.LENGTH_SHORT).show();
            } else {
                signInUser(strEmail, strPassword);
            }
        }
    }

    private void signInUser(String email, String password) {
        showProgressDialog(true);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)//gui yc
                .addOnCompleteListener(this, task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            User userObject = new User(user.getEmail(), password);
                            if (user.getEmail() != null && user.getEmail().contains(Constant.ADMIN_EMAIL_FORMAT)) {//neu k chua dinh dang email admin kich hoat isAdmin
                                userObject.setAdmin(true);
                            }
                            DataStoreManager.setUser(userObject);//luu tt user
                            GlobalFunction.gotoMainActivity(this);
                            finishAffinity();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, getString(R.string.msg_sign_in_error),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}