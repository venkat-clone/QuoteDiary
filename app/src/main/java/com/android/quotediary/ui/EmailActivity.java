package com.android.quotediary.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.quotediary.LoginActivity;
import com.android.quotediary.R;
import com.android.quotediary.RegisterUserActivity;
import com.android.quotediary.databinding.ActivityEmailBinding;
import com.android.quotediary.models.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class EmailActivity extends AppCompatActivity {

    public static final String ACTIVITY_MODE = "ActivityMode";
    public static final String MODE_PASSWORD_RESET = "passwordMode";
    public static final String MODE_EMAIL_VERIFY ="VerifyMode";

    public String Mode = "";

    ActivityEmailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email);
        handleIntent(getIntent());

    }
    void handleIntent(Intent intent){
        Mode = intent.getStringExtra(ACTIVITY_MODE);
        if(!Mode.equals("")){
            binding.setIsPass(Mode.equals(MODE_PASSWORD_RESET));
            binding.setLifecycleOwner(this);
            binding.setClickHandler(new ClickHandler());
        }else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    public class ClickHandler{
        public void register(View v){
            startActivity(new Intent(getBaseContext(), RegisterUserActivity.class));
            finish();
        }
        public void login(View v){
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
        }
        public void sendEmail(View v){
            FirebaseUser user  =FirebaseAuth.getInstance().getCurrentUser();
            if(Mode.equals(MODE_PASSWORD_RESET)){
                FirebaseAuth.getInstance().sendPasswordResetEmail(binding.email.getText().toString())
                        .addOnSuccessListener(s->{
                            Snackbar.make(binding.getRoot(),"Password Reset Email Send",Snackbar.LENGTH_LONG).show();
                            login(binding.getRoot());
                        })
                        .addOnFailureListener(f->{
                            Snackbar.make(binding.getRoot(),f.getMessage()+"",Snackbar.LENGTH_LONG).show();
                            Log.e("Password mail",f.getMessage());
                            f.printStackTrace();
                        });
            }
            else if(Mode.equals(MODE_EMAIL_VERIFY)){
                if(user==null){
                    login(binding.getRoot());
                }
                else user.updateEmail(binding.email.getText().toString())
                        .addOnSuccessListener(s->{
                            startActivity(new Intent(getBaseContext(), VerifyAccountActivity.class));
                            finish();
                        })
                        .addOnFailureListener(f->{
                            Snackbar.make(binding.getRoot(),f.getMessage()+"",Snackbar.LENGTH_LONG).show();
                            Log.e("Update Email",f.getMessage());
                            f.printStackTrace();
                        });
            }
        }
    }


}