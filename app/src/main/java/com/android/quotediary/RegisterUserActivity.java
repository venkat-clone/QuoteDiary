package com.android.quotediary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.quotediary.Reterofit.Repository.LoginRepository;
import com.android.quotediary.databinding.ActivityRegisterUserBinding;
import com.android.quotediary.models.UserModel;

public class RegisterUserActivity extends AppCompatActivity {
    ActivityRegisterUserBinding binding;
    LoginRepository loginRepository;
    RegisterUserActivityViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register_user);
        setContentView(binding.getRoot());
        mViewModel = new RegisterUserActivityViewModel();
        binding.setClickhandler(new ClickHandler());
        binding.setUserModel(new UserModel.Register());
        loginRepository = new LoginRepository(getBaseContext());
    }

    public void Observer(){
        mViewModel.Signinresponce.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer s) {
                switch (s){
                    case 200:
                        // SignIn Successful
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 404:
                        // User Id Not Found

                        break;
                }
            }
        });

    }



    public class ClickHandler {
        public void OnSignup(View view,UserModel.Register usermodel){

            if(usermodel.getUname().trim().isEmpty()){
                // User Name is Empty
                Toast.makeText(getBaseContext(),"User Name is Empty",Toast.LENGTH_LONG).show();
            }
            else {
                if(!Patterns.EMAIL_ADDRESS.matcher(usermodel.getEmail()).matches()){
                   // Invalid Email
                    Toast.makeText(getBaseContext(),"Invalid Email",Toast.LENGTH_LONG).show();
                }
                else{
                    if(usermodel.getPassword().length()<8){
                        // Password Must Be grater than or equal to 8 characters
                        Toast.makeText(getBaseContext(),"Password Must Be grater than or equal to 8 characters",Toast.LENGTH_LONG).show();
                    }
                    else {
                        loginRepository.SignUp(usermodel,mViewModel.Signinresponce);
                    }
                }


            }


        }
        public void OnSignin(View view){
            finish();
        }
    }
}