package com.android.quotediary.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.quotediary.LoginActivity;

import com.android.quotediary.RegisterUserActivity;

import com.android.quotediary.databinding.ActivityVerifyAccountBinding;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class VerifyAccountActivity extends AppCompatActivity {

    ActivityVerifyAccountBinding binding;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = DataBindingUtil.setContentView(this,R.layout.activity_verify_account);
        binding = ActivityVerifyAccountBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        binding.setClickHandler(new ClickHandler());
        setContentView(binding.getRoot());

        sendMail();

    }

    void sendMail(){
        if(user==null) return;
        ProgressDialog dialog = new ProgressDialog(VerifyAccountActivity.this);
        dialog.setMessage("Sending Verification Email...");
        dialog.show();
        user.sendEmailVerification()
                .addOnSuccessListener(s->{
                    dialog.cancel();
                    Snackbar.make(binding.getRoot(),"Verification Email Send",Snackbar.LENGTH_LONG).show();
                })
                .addOnFailureListener(f->{
                    dialog.cancel();
                    Log.i("Validation Email",f.getMessage());
                    Snackbar.make(binding.getRoot(),f.getMessage()+"",Snackbar.LENGTH_LONG).show();
                });
    }




    public class ClickHandler {
        public String email=user.getEmail();
        public void resendEmail(View v){
            // Resend Route Implementation
            sendMail();
        }
        public void wrongEmail(View v){
            // Resend Route Implementation
            Intent intent = new Intent(getBaseContext(), EmailActivity.class);
            intent.putExtra(EmailActivity.ACTIVITY_MODE,EmailActivity.MODE_EMAIL_VERIFY);
            startActivity(intent);
        }
        public void logIn(View v){
            // Resend Route Implementation
            FirebaseAuth.getInstance().signOut();
            
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
        }

    }
}