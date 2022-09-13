package com.android.quotediary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.android.quotediary.Reterofit.Repository.LoginRepository;
import com.android.quotediary.databinding.ActivityRegisterUserBinding;
import com.android.quotediary.models.UserModel;
import com.android.quotediary.ui.VerifyAccountActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {
    ActivityRegisterUserBinding binding;
    LoginRepository loginRepository;
    RegisterUserActivityViewModel mViewModel;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register_user);
        setContentView(binding.getRoot());
        mViewModel = new ViewModelProvider(this).get(RegisterUserActivityViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setClickhandler(new ClickHandler());
        binding.setUserModel(new UserModel.Register());

        loginRepository = new LoginRepository(getBaseContext());
        Observer();
    }

    public void Observer() {
        mViewModel.Signinresponce.observe(this, s -> {
            switch (s) {
                case 200:
                    // SignIn Successful
                    if (dialog != null) dialog.cancel();
                    Intent intent = new Intent(getBaseContext(), VerifyAccountActivity.class);

                    startActivity(intent);
                    finish();
                    break;
                case 404:
                    // User Id Not Found
                    if (dialog != null) dialog.cancel();
                    binding.UnameTextField.setError("User Id Not find");
                    break;

                default:
                    if (dialog != null) dialog.cancel();
                    Snackbar.make(binding.getRoot(), "Something went wrong", Snackbar.LENGTH_LONG).show();
            }

        });

    }

    public void registerOnFireBase(UserModel.Register usermodel) {
        dialog = new ProgressDialog(RegisterUserActivity.this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(usermodel.getEmail(), usermodel.getPassword())
                .addOnSuccessListener(x -> {
                    Log.d("TAG_V", "createUserWithEmail:success");
                    Log.i("Token",x.getUser().getUid());
                    usermodel.setUserId(x.getUser().getUid());
                    updateUserData(usermodel);
                })
                .addOnFailureListener(y -> {

                    Snackbar.make(binding.getRoot(), y.getMessage() + "", Snackbar.LENGTH_LONG).show();
                    Log.w("TAG_V", "createUserWithEmail:failure" + y.getMessage());

                    dialog.cancel();
                });
    }

    public void updateUserData( UserModel.Register userModel) {
        FirebaseAuth.getInstance().getCurrentUser().updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(userModel.getUname()).build())
                .addOnSuccessListener(y -> {

                    addUserToServer(userModel);
//                                        dialog.cancel();
//                                        startActivity(new Intent(getBaseContext(),VerifyAccountActivity.class));
//                                        finish();
//                                        Snackbar.make(binding.getRoot(),"User Created",Snackbar.LENGTH_LONG).show();
                })
                .addOnFailureListener(f -> {
                    dialog.cancel();
                    Snackbar.make(binding.getRoot(), f.getMessage() + "", Snackbar.LENGTH_LONG).show();
                });
    }

    public void addUserToServer(UserModel.Register usermodel) {
        loginRepository.SignUp(usermodel, new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    dialog.cancel();
                    startActivity(new Intent(getBaseContext(), VerifyAccountActivity.class));
                    finish();
                } else {
                    dialog.cancel();
                    Snackbar.make(binding.getRoot(), response.message() + "", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                dialog.cancel();
                Snackbar.make(binding.getRoot(), t.getMessage() + "", Snackbar.LENGTH_LONG).show();
            }
        });
    }

    public class ClickHandler {
        public void OnSignup(View view, UserModel.Register userModel) {
//            UserModel.Register usermodel = mViewModel.userModel.getValue();
//            Snackbar.make(binding.getRoot(),"on sign up ",Snackbar.LENGTH_LONG).show();
            if (userModel.getUname().trim().isEmpty()) {
                // User Name is Empty
                Toast.makeText(getBaseContext(), "User Name is Empty" + userModel.getUname(), Toast.LENGTH_LONG).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(userModel.getEmail()).matches()) {
                // Invalid Email
                Toast.makeText(getBaseContext(), "Invalid Email", Toast.LENGTH_LONG).show();
            } else if (userModel.getPassword().length() < 8) {
                // Password Must Be grater than or equal to 8 characters
                Toast.makeText(getBaseContext(), "Password Must Be grater than or equal to 8 characters", Toast.LENGTH_LONG).show();
            }
//            else if(BaseClass.isNetworkConnected(RegisterUserActivity.this))
//                Toast.makeText(getBaseContext(),"please Check Your Internet connection",Toast.LENGTH_LONG).show();
            else {
                registerOnFireBase(userModel);
//                        .addOnCompleteListener(c->{
//                            dialog.cancel();
////                            Snackbar.make(binding.getRoot(),"Completed",Snackbar.LENGTH_LONG).show();
//                            if(c.isSuccessful())
//                                Snackbar.make(binding.getRoot(),"Successful",Snackbar.LENGTH_LONG).show();
//                            else {
//                                Snackbar.make(binding.getRoot(), "Failed", Snackbar.LENGTH_LONG).show();
//                                Log.w("TAG_V", "createUserWithEmail:failure", c.getException());
//
//                            }
//
//                        });
//                Log.i("USER_V",mAuth.getCurrentUser().getEmail()+"");
            }


        }

        public void OnSignin(View view) {
            finish();
        }
    }
}