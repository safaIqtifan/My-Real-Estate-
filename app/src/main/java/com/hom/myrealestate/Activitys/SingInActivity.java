package com.hom.myrealestate.Activitys;

import static com.hom.myrealestate.classes.BaseActivity.mackToast;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hom.myrealestate.Models.UserModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.BaseActivity;
import com.hom.myrealestate.classes.Constants;
import com.hom.myrealestate.classes.UtilityApp;
import com.hom.myrealestate.databinding.ActivitySingInBinding;

public class SingInActivity extends BaseActivity {

    ActivitySingInBinding binding;
    FirebaseAuth fAuth;
    FirebaseFirestore fireStoreDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sing_in);
        binding = ActivitySingInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fAuth = FirebaseAuth.getInstance();
        fireStoreDB = FirebaseFirestore.getInstance();

        binding.signUp.setOnClickListener(v -> {
            startActivity(new Intent(SingInActivity.this, SingUpActivity.class));
        });

        binding.signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidData();
            }
        });

        binding.forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passWordResetDialog = new AlertDialog.Builder(v.getContext());
                passWordResetDialog.setTitle("Reset Password ?");
                passWordResetDialog.setMessage("Enter your E-mail to Reset Link ");
                passWordResetDialog.setView(resetMail);
                passWordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(SignInActivity.this, "Reset Link Sent to Your Email .", Toast.LENGTH_SHORT).show();
                                mackToast(SingInActivity.this, "Reset Link Sent to Your Email .");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(SignInActivity.this, "Error ! Reset Link is Not Sent ." + e.getMessage(), Toast.LENGTH_SHORT).show();
                                mackToast(SingInActivity.this, e.getMessage());
                            }
                        });
                    }
                });

                passWordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                passWordResetDialog.create().show();
            }
        });

    }

    public void ValidData() {

        String emailStr = binding.emailEd.getText().toString();
        String passwordStr = binding.passwordEd.getText().toString();

        if (emailStr.isEmpty()) {
            binding.emailEd.setError("الايميل مطلوب");
            return;
        }
        if (passwordStr.isEmpty()) {
            binding.passwordEd.setError("كلمة السر مطلوبة");
            return;
        }

        LoginAth(emailStr, passwordStr);
    }

    public void LoginAth(String email, String password) {

        binding.progressBar.setVisibility(View.VISIBLE);
        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            getData();
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
//                            Toast.makeText(SignInActivity.this, "fail_to_login", Toast.LENGTH_SHORT).show();
                            mackToast(SingInActivity.this, getString(R.string.fail_to_login));
                        }
                    }
                });

    }

    public void getData() {

        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();

        fireStoreDB.collection(Constants.KEY_USER).document(userid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            UserModel user = task.getResult().toObject(UserModel.class);
                            UtilityApp.setUserData(user);
                            startActivity(new Intent(SingInActivity.this, HomePageActivity.class)
                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            finish();
                        } else {
//                            Toast.makeText(SignInActivity.this, getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                            mackToast(SingInActivity.this, getString(R.string.fail_get_data));
                        }
                    }
                });
        binding.progressBar.setVisibility(View.GONE);
    }
}