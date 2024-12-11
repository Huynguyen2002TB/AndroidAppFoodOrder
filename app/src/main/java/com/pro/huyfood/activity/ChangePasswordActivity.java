package com.pro.huyfood.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pro.huyfood.R;
import com.pro.huyfood.databinding.ActivityChangePasswordBinding;
import com.pro.huyfood.model.User;
import com.pro.huyfood.prefs.DataStoreManager;
import com.pro.huyfood.utils.StringUtil;

public class ChangePasswordActivity extends BaseActivity {

    private ActivityChangePasswordBinding mActivityChangePasswordBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityChangePasswordBinding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(mActivityChangePasswordBinding.getRoot());

        mActivityChangePasswordBinding.imgBack.setOnClickListener(v -> onBackPressed());
        mActivityChangePasswordBinding.btnChangePassword.setOnClickListener(v -> onClickValidateChangePassword());
    }

    private void onClickValidateChangePassword() {
        String strOldPassword = mActivityChangePasswordBinding.edtOldPassword.getText().toString().trim();
        String strNewPassword = mActivityChangePasswordBinding.edtNewPassword.getText().toString().trim();
        String strConfirmPassword = mActivityChangePasswordBinding.edtConfirmPassword.getText().toString().trim();
        if (StringUtil.isEmpty(strOldPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_old_password_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strNewPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_new_password_require), Toast.LENGTH_SHORT).show();
        } else if (StringUtil.isEmpty(strConfirmPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_confirm_password_require), Toast.LENGTH_SHORT).show();
        } else if (!DataStoreManager.getUser().getPassword().equals(strOldPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_old_password_invalid), Toast.LENGTH_SHORT).show();
        } else if (!strNewPassword.equals(strConfirmPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_confirm_password_invalid), Toast.LENGTH_SHORT).show();
        } else if (strOldPassword.equals(strNewPassword)) {
            Toast.makeText(ChangePasswordActivity.this, getString(R.string.msg_new_password_invalid), Toast.LENGTH_SHORT).show();
        } else {
            changePassword(strNewPassword);
        }
    }

    private void changePassword(String newPassword) {
        showProgressDialog(true);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        user.updatePassword(newPassword)
                .addOnCompleteListener(task -> {
                    showProgressDialog(false);
                    if (task.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this,
                                getString(R.string.msg_change_password_successfully), Toast.LENGTH_SHORT).show();
                        User userLogin = DataStoreManager.getUser();
                        userLogin.setPassword(newPassword);
                        DataStoreManager.setUser(userLogin);
                        mActivityChangePasswordBinding.edtOldPassword.setText("");
                        mActivityChangePasswordBinding.edtNewPassword.setText("");
                        mActivityChangePasswordBinding.edtConfirmPassword.setText("");
                    }
                });
    }
}