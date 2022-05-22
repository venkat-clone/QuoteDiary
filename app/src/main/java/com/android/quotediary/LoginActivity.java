package com.android.quotediary;

import static com.android.quotediary.Helpers.Validators.ISEmail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.euicc.DownloadableSubscription;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.quotediary.Helpers.BaseClass;
import com.android.quotediary.Helpers.Room.DairyRepository;
import com.android.quotediary.Reterofit.Repository.LoginRepository;
import com.android.quotediary.databinding.ActivityLoginBinding;
import com.android.quotediary.models.Dairy;
import com.android.quotediary.models.UserModel;

import java.util.List;

import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    public static LoginActivityViewModel viewModel;
    public UserModel.Login userModel ;
    LoginRepository loginRepository;
    DairyRepository dairyRepository;
    ProgressDialog dialog;
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
        viewModel.DownloadResponce.setValue(100);
        dairyRepository = new DairyRepository(getApplication());
        loginRepository.Login(new UserModel.Login("venkey@123","venkey1single@gmail.com"),viewModel.loginresponce);
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
        viewModel.DownloadResponce.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 200:
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        dialog.cancel();
                        finish();
                        break;
                }
            }
        });
        viewModel.DairyList.observe(this, new Observer<List<Dairy.ServerDairy>>() {
            @Override
            public void onChanged(List<Dairy.ServerDairy> serverDairies) {
                if(serverDairies!=null){
                    dairyRepository.insertServerResponce(serverDairies,viewModel.DownloadResponce);
                }
            }
        });
        viewModel.loginresponce.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {

                switch (integer){
                    case 400:
                        Toast.makeText(getBaseContext(),"Something Went Wrong",Toast.LENGTH_LONG).show();
                        if(dialog!=null) dialog.cancel();
                        break;
                    case 404:
                        Toast.makeText(getBaseContext(),"Invalid Email Id",Toast.LENGTH_LONG).show();
                        if(dialog!=null) dialog.cancel();
                        break;
                    case 200:
                        if(dialog!=null) dialog.setMessage("Downloading Your Dairy's");
                        loginRepository.Download(viewModel.DownloadResponce,viewModel.DairyList);
                        break;
                }


//                if(integer==404)
//                    Toast.makeText(getBaseContext(),"Invalid Email Id",Toast.LENGTH_LONG).show();

            }
        });

    }



    public class ClickHandlers{

        public void onSignIn(View view,UserModel.Login userModel){

            if(userModel.getPassword().isEmpty())
                Toast.makeText(getBaseContext(),"Password Cannot Be Empty",Toast.LENGTH_LONG).show();
            else if(!Patterns.EMAIL_ADDRESS.matcher(userModel.getEmail()).matches())
                Toast.makeText(getBaseContext(),"Invalid Email Id",Toast.LENGTH_LONG).show();
            else if(!BaseClass.isNetworkConnected(LoginActivity.this))
                Toast.makeText(getBaseContext(),"Please Check Your Internet Connection",Toast.LENGTH_LONG).show();
            else {
                dialog = new ProgressDialog(LoginActivity.this);
                dialog.setMessage("Loading....");
                dialog.show();
                loginRepository.Login(userModel,viewModel.loginresponce);
            }

        }

        public void SignUp(View view){
            Intent intent = new Intent(getBaseContext(),RegisterUserActivity.class);
            startActivity(intent);
        }


    }
}