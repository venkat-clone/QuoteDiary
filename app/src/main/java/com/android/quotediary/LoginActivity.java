package com.android.quotediary;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;
import com.android.quotediary.Helpers.BaseClass;
import com.android.quotediary.Helpers.Room.DairyRepository;
import com.android.quotediary.Reterofit.Repository.LoginRepository;
import com.android.quotediary.databinding.ActivityLoginBinding;
import com.android.quotediary.models.UserModel;
import com.android.quotediary.ui.EmailActivity;
import com.android.quotediary.ui.VerifyAccountActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;


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
        viewModel.DownloadResponce.observe(this, integer -> {
            if (integer == 5000 || integer==200) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                dialog.cancel();
                finish();
            }
            else if(dialog!=null)
                dialog.cancel();
        });
        viewModel.DairyList.observe(this, serverDairies -> {
            if(serverDairies!=null){
                dairyRepository.insertServerResponce(serverDairies,viewModel.DownloadResponce);
            }
        });

    }

    public void getFireBaseToken(){

        FirebaseAuth.getInstance().getCurrentUser().getIdToken(true)
                .addOnSuccessListener(x-> {
                    sharedPreferenceServices.SetLogedIn(getBaseContext(), true);
                    Log.i("Log_token",x.getToken());
                    dialog.setMessage("Downloading...");

                    sharedPreferenceServices.SetLogedIn(getBaseContext(),true);
                    loginRepository.Download(viewModel.DownloadResponce,viewModel.DairyList,x.getToken());

                })
                .addOnFailureListener(f-> {
                    Snackbar.make(binding.getRoot(), f.getMessage() + "", Snackbar.LENGTH_LONG).show();
                    dialog.cancel();
                });
    }

    public void FireBaseLogin(UserModel.Login userModel){
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setCancelable(false);
        dialog.setMessage("Loading....");
        dialog.show();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(userModel.getEmail(), userModel.getPassword())
                .addOnCompleteListener(s-> {
//                    dialog.cancel();
                    if(s.isSuccessful()){
                        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified())
                            getFireBaseToken();
                        else {
                            startActivity(new Intent(getBaseContext(), VerifyAccountActivity.class));
                            finish();
                        }
                    }
                    else{
                        dialog.cancel();
                        Snackbar.make(binding.getRoot(), s.getException().getMessage()+"", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(f->{
                    Snackbar.make(binding.getRoot(),f.getMessage()+"",Snackbar.LENGTH_LONG).show();
                    dialog.cancel();
                    Log.i("Login response",f.getMessage());
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

                FireBaseLogin(userModel);
//                loginRepository.Login(userModel,viewModel.loginresponce);
            }

        }

        public void SignUp(View view){
            Intent intent = new Intent(getBaseContext(),RegisterUserActivity.class);
            startActivity(intent);
        }

        public void forgotPass(View v){
            Intent intent = new Intent(getBaseContext(), EmailActivity.class);
            intent.putExtra(EmailActivity.ACTIVITY_MODE,EmailActivity.MODE_PASSWORD_RESET);
            startActivity(intent);
        }


    }
}