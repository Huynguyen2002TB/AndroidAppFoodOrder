package com.pro.huyfood.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pro.huyfood.R;
import com.pro.huyfood.databinding.ActivityForgotPasswordBinding;
import com.pro.huyfood.utils.StringUtil;

public class ForgotPasswordActivity extends BaseActivity {

    private ActivityForgotPasswordBinding mActivityForgotPasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityForgotPasswordBinding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(mActivityForgotPasswordBinding.getRoot());

        mActivityForgotPasswordBinding.imgBack.setOnClickListener(v -> onBackPressed());
        mActivityForgotPasswordBinding.btnResetPassword.setOnClickListener(v -> onClickValidateResetPassword());
    }

    private void onClickValidateResetPassword() {
        String strEmail = mActivityForgotPasswordBinding.edtEmail.getText().toString().trim();
        if (StringUtil.isEmpty(strEmail)) {
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.msg_email_require), Toast.LENGTH_SHORT).show();
        } else if (!StringUtil.isValidEmail(strEmail)) {
            Toast.makeText(ForgotPasswordActivity.this, getString(R.string.msg_email_invalid), Toast.LENGTH_SHORT).show();
        } else {
            resetPassword(strEmail);
        }
    }

    private void resetPassword(String email) {
        showProgressDialog(true);
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this,
                                getString(R.string.msg_reset_password_successfully),
                                Toast.LENGTH_SHORT).show();
                        mActivityForgotPasswordBinding.edtEmail.setText("");
                    }
                });
    }
}