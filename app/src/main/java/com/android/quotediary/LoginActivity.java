package com.android.quotediary;

import static com.android.quotediary.Helpers.Validators.ISEmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.quotediary.Reterofit.Repository.LoginRepository;
import com.android.quotediary.databinding.ActivityLoginBindingImpl;
import com.android.quotediary.models.UserModel;

import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBindingImpl binding;
    public static LoginActivityViewModel viewModel;
    public UserModel.Login userModel ;
    LoginRepository loginRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
//        viewModel = new LoginActivityViewModel();
        binding.setModel(viewModel);
        binding.setLifecycleOwner(this);
        loginRepository = new LoginRepository(this);
        userModel = new UserModel.Login();
        binding.setUsermodel(userModel);
        binding.setClickHandler(new ClickHandlers());

        Observer();
    }

    public void Observer(){
//        viewModel.Email.observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                if(Patterns.EMAIL_ADDRESS.matcher(s).matches()){
//                    userModel.setEmail(s);
//                    viewModel.ErrorCode.setValue(-1);
//                }
//                else {
//                    viewModel.getErrorMessage().setValue("Invalid Email");
//                    viewModel.ErrorCode.setValue(1);
//                }
//            }
//        });

        viewModel.loginresponce.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                switch (integer){
                    case 400:
                        Toast.makeText(getBaseContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                        break;
                    case 404:
                        Toast.makeText(getBaseContext(),"User Not Found",Toast.LENGTH_LONG).show();
                        break;
                    case 200:
                        Toast.makeText(getBaseContext(),"LOGIN SUCCESSFUL",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                        break;
                }


                if(integer==404)
                    Toast.makeText(getBaseContext(),"Invalid Email Id",Toast.LENGTH_LONG).show();

            }
        });

    }



    public class ClickHandlers{

        public void onSignIn(View view,UserModel.Login userModel){
            if(Patterns.EMAIL_ADDRESS.matcher(userModel.getEmail()).matches() & !userModel.getPassword().isEmpty()){
                loginRepository.Login(userModel,viewModel.loginresponce);
            }
            else if(userModel.getPassword().isEmpty()){
                Toast.makeText(getBaseContext(),"Password Cannot Be Empty",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getBaseContext(),"Invalid Email Id",Toast.LENGTH_LONG).show();
            }
        }

        public void SignUp(View view){
            Intent intent = new Intent(getBaseContext(),RegisterUserActivity.class);
            startActivity(intent);
        }


    }
}