package com.hom.myrealestate.Activitys;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.hom.myrealestate.Models.UserModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.BaseActivity;
import com.hom.myrealestate.classes.Constants;
import com.hom.myrealestate.classes.UtilityApp;
import com.hom.myrealestate.databinding.ActivitySingUpBinding;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;

import java.util.HashMap;
import java.util.Map;

public class SingUpActivity extends BaseActivity {

    ActivitySingUpBinding binding;
    FirebaseFirestore fireStoreDB;
    private FirebaseAuth fAuth;
    FirebaseUser firebaseUser;

    GoogleSignInOptions gso;
    GoogleSignInClient googleSignInClient;
    public static final int REQUEST_CODE = 100;

    UserModel userModel;
    String nameStr, phoneNumberStr, ccpPhoneStr, addressStr, 
            emailStr, passwordStr, confirmPasswordStr, fullMobileNum = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_sing_up);
        binding = ActivitySingUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fireStoreDB = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        firebaseUser = fAuth.getCurrentUser();
        userModel = new UserModel();

//        CountryCodePicker cpp = (CountryCodePicker) findViewById(R.id.ccp_phone);
//        cpp.registerCarrierNumberEditText(binding.mobileNumberEd);
//        binding.ccpPhone.registerCarrierNumberEditText

        // Initialize sign in options the client-id is copied form google-services.json file
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // Initialize sign in client
        googleSignInClient = GoogleSignIn.getClient(SingUpActivity.this, gso);
        // Check condition
//        if (firebaseUser != null) {
//            // When user already sign in redirect to profile activity
//            startActivity(new Intent(SingUpActivity.this, HomePageActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        }

        binding.haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SingUpActivity.this, SingInActivity.class));
                finish();
            }
        });

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameStr = binding.nameEd.getText().toString();
                phoneNumberStr = binding.mobileNumberEd.getText().toString();
                addressStr = binding.addressEd.getText().toString();
                ccpPhoneStr = binding.ccpPhone.selectedCountryCode();
                emailStr = binding.emailEd.getText().toString();
                passwordStr = binding.passwordEd.getText().toString();
                confirmPasswordStr = binding.confirmPassword.getText().toString();
                fullMobileNum = ccpPhoneStr + phoneNumberStr;

                checkData();
            }
        });

        binding.googleSingUpBtn.setOnClickListener(v -> {
            // Initialize sign in intent
            Intent intent = googleSignInClient.getSignInIntent();
            // Start activity for result
            startActivityForResult(intent, REQUEST_CODE);
        });
    }

    private void checkData() {

        if (nameStr.isEmpty()) {
            binding.nameEd.setError("الاسم مطلوب");
            return;
        }
        if (phoneNumberStr.isEmpty()) {
            binding.mobileNumberEd.setError("رقم الهاتف مطلوب");
            return;
        }
        if (addressStr.isEmpty()) {
            binding.addressEd.setError("العنوان مطلوب");
            return;
        }
        if (emailStr.isEmpty()) {
            binding.emailEd.setError("الايميل مطلوب");
            return;
        }
        if (passwordStr.isEmpty()) {
            binding.passwordEd.setError("كلمة المرور مطلوبة");
            return;
        }
        if (confirmPasswordStr.isEmpty()) {
            binding.confirmPassword.setError("كلمة المرور مطلوبة");
            return;
        }
        if (!passwordStr.equals(confirmPasswordStr)) {
            binding.passwordEd.setError("كلمة المرور غير متطابقة");
            binding.confirmPassword.setError("كلمة المرور غير متطابقة");
            return;
        }

        userModel.setName(nameStr);
        userModel.setCcpPhone(ccpPhoneStr);
        userModel.setPhoneNumber(phoneNumberStr);
        userModel.setFullMobileNum(fullMobileNum);
        userModel.setAddress(addressStr);
        userModel.setEmail(emailStr);
        userModel.setPassword(passwordStr);

        firebaseAuth(emailStr, passwordStr);

    }

    private void firebaseAuth(String email, String password) {

        binding.progressBar.setVisibility(View.VISIBLE);
        fAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                sendToFireBase();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                mackToast(SingUpActivity.this, e.getMessage());
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void sendToFireBase() {

        FirebaseUser firebaseUser = fAuth.getCurrentUser();
        assert firebaseUser != null;
        String userid = firebaseUser.getUid();
        userModel.setUserId(userid);

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("userId", userModel.getUserId());
        userMap.put("name", userModel.getName());
        userMap.put("phoneNumber", userModel.getPhoneNumber());
        userMap.put("ccpPhone", userModel.getCcpPhone());
        userMap.put("address", userModel.getAddress());
        userMap.put("email", userModel.getEmail());
        userMap.put("password", userModel.getPassword());
        userMap.put("fullMobileNum", userModel.getFullMobileNum());
        userMap.put("profileCompleted", userModel.isProfileCompleted());

        fireStoreDB.collection(Constants.KEY_USER).document(userid).set(userMap, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    UtilityApp.setUserData(userModel);
                    Toast.makeText(SingUpActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SingUpActivity.this, HomePageActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
//                            Toast.makeText(SignUpActivity.this, getString(R.string.fail_add_user), Toast.LENGTH_SHORT).show();
                    mackToast(SingUpActivity.this, getString(R.string.fail_add_data));
                }
            }
        });
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        binding.progressBar.setVisibility(View.VISIBLE);
        // Check condition
        if (requestCode == REQUEST_CODE) {
            // When request code is equal to 100 initialize task
            Task<GoogleSignInAccount> signInAccountTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            // check condition
            System.out.println("Log signInAccountTask isSuccessful "+signInAccountTask.isSuccessful());
            if (signInAccountTask.isSuccessful()) {
                // When google sign in successful initialize string
                String s = "Google sign in successful";
                // Display Toast
                displayToast(s);
                // Initialize sign in account
                try {
                    // Initialize sign in account
                    GoogleSignInAccount googleSignInAccount = signInAccountTask.getResult(ApiException.class);
                    // Check condition
                    if (googleSignInAccount != null) {
                        // When sign in account is not equal to null initialize auth credential
                        AuthCredential authCredential = GoogleAuthProvider.getCredential(googleSignInAccount.getIdToken(), null);
                        // Check credential
                        fAuth.signInWithCredential(authCredential).addOnCompleteListener(this,
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        // Check condition
                                        if (task.isSuccessful()) {
                                            userModel.setName(firebaseUser.getDisplayName());
                                            userModel.setFullMobileNum(firebaseUser.getPhoneNumber());
                                            userModel.setEmail(firebaseUser.getEmail());
                                            userModel.setCcpPhone(ccpPhoneStr);
                                            userModel.setPhoneNumber(phoneNumberStr);
                                            userModel.setAddress(addressStr);
                                            userModel.setPassword(passwordStr);

                                            displayToast("Firebase authentication successful");
                                            sendToFireBase();
                                            // When task is successful redirect to profile activity display Toast
//                                            startActivity(new Intent(SingUpActivity.this, HomePageActivity.class)
//                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//                                            Toast.makeText(SingUpActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                                        } else {
                                            binding.progressBar.setVisibility(View.GONE);
                                            // When task is unsuccessful display Toast
                                            displayToast("فشل بإضافة البيانات : 3" + task.getException().getMessage());
                                        }
                                    }
                                });
                    }
//                    else {
//                        binding.progressBar.setVisibility(View.GONE);
//                        // When task is unsuccessful display Toast
//                        displayToast("فشل بإضافة البيانات : 3");
//                    }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, "signInAccountTask fail", Toast.LENGTH_SHORT).show();
            }
//            else {
//                binding.progressBar.setVisibility(View.GONE);
//                // When task is unsuccessful display Toast
//                displayToast("فشل بإضافة البيانات : 2" + signInAccountTask.getException().getMessage());
//            }
        }
//        else {
//            binding.progressBar.setVisibility(View.GONE);
//            // When task is unsuccessful display Toast
//            displayToast("فشل بإضافة البيانات : 1");
        }


    private void displayToast(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
    
}