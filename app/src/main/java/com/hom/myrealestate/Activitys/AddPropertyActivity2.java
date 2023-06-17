package com.hom.myrealestate.Activitys;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hom.myrealestate.Adapter.PropertyAddedFeaturesAdapter;
import com.hom.myrealestate.Adapter.PropertyFeaturesAdapter;
import com.hom.myrealestate.Adapter.PropertyImagAdapter;
import com.hom.myrealestate.Models.PropertyModel;
import com.hom.myrealestate.Models.UserModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.BaseActivity;
import com.hom.myrealestate.classes.Constants;
import com.hom.myrealestate.classes.DataCallBack;
import com.hom.myrealestate.classes.UtilityApp;
import com.hom.myrealestate.databinding.ActivityAddProperty2Binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AddPropertyActivity2 extends BaseActivity {

    ActivityAddProperty2Binding binding;
    FirebaseFirestore fireStoreDB;
    StorageReference storageRef;

    UserModel userModel;
    PropertyModel propertyModel;
    FlexboxLayoutManager layoutManager;
    FlexboxLayoutManager layoutManagerAddedFeatures;

    String imageEncoded;
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    ArrayList<String> propertyImageList = new ArrayList<String>();
    ArrayList<String> propertyaddedFeaturesList = new ArrayList<String>();
//    String[] addedFeatures;
    PropertyImagAdapter adapter;
    PropertyFeaturesAdapter adapterFeatures;
    PropertyAddedFeaturesAdapter adapterAddedFeatures;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    String propertyStatusStr, propertyPaymentStr, leaseTermStr, propertyCategoryStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_property2);
        binding = ActivityAddProperty2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bar.title.setVisibility(View.VISIBLE);
        binding.bar.title.setText("السابق");
        fireStoreDB = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();
        userModel = UtilityApp.getUserData();

        Intent intent = getIntent();
        propertyModel = (PropertyModel) intent.getSerializableExtra(Constants.KEY_PROPERTY_ADDED);

        binding.propertyImgRv.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, true));

        binding.propertyStatus.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.propertyNew) {
                propertyStatusStr = binding.propertyNew.getText().toString();
            } else if (i == R.id.propertyMiddle) {
                propertyStatusStr = binding.propertyMiddle.getText().toString();
            } else if (i == R.id.propertyOld) {
                propertyStatusStr = binding.propertyOld.getText().toString();
            }
        });

        binding.propertyCategory.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.propertyRent) {
                propertyCategoryStr = binding.propertyRent.getText().toString();
            } else if (i == R.id.propertySale) {
                propertyCategoryStr = binding.propertySale.getText().toString();
            }
        });

        binding.leaseTerm.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.propertyYearly) {
                leaseTermStr = binding.propertyYearly.getText().toString();
            } else if (i == R.id.propertyMonthly) {
                leaseTermStr = binding.propertyMonthly.getText().toString();
            } else if (i == R.id.propertyWeekly) {
                leaseTermStr = binding.propertyWeekly.getText().toString();
            }
        });

        binding.propertyPayment.setOnCheckedChangeListener((radioGroup1, i) -> {
            if (i == R.id.propertyHavePayment) {
                propertyPaymentStr = binding.propertyHavePayment.getText().toString();
            } else if (i == R.id.propertyNoPayment) {
                propertyPaymentStr = binding.propertyNoPayment.getText().toString();
            }
        });

        binding.pickImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (getApplicationContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);

                    } else {
                        pickImageFromGallery();
                    }
                } else {
                    pickImageFromGallery();
                }
            }
        });

        binding.addPropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        binding.addNewFeatures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });

//        binding.propertyFeaturesRv.setLayoutManager(new GridLayoutManager(AddPropertyActivity2.this, 3,
//                GridLayoutManager.VERTICAL, false));
//        binding.propertyFeaturesRv.setNestedScrollingEnabled(false);

//        FlowLayoutManager flowLayoutManager = new FlowLayoutManager();
//        flowLayoutManager.setAutoMeasureEnabled(true);
//        binding.propertyFeaturesRv.setLayoutManager(flowLayoutManager);

        layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        binding.propertyFeaturesRv.setLayoutManager(layoutManager);

        layoutManagerAddedFeatures = new FlexboxLayoutManager(this);
        layoutManagerAddedFeatures.setFlexWrap(FlexWrap.WRAP);
        layoutManagerAddedFeatures.setFlexDirection(FlexDirection.ROW);
        binding.propertyAddedFeaturesRv.setLayoutManager(layoutManagerAddedFeatures);

        initFeaturesAdapter();

    }

     private void checkData() {
        if (propertyStatusStr.isEmpty()) {
            binding.propertyStatusTv.setError("مطلوب");
            return;
        }
        if (propertyPaymentStr.isEmpty()) {
            binding.propertyPaymentTv.setError("مطلوب");
            return;
        }
        if (leaseTermStr.isEmpty()) {
            binding.leaseTermTv.setError("مطلوب");
            return;
        }
        if (propertyCategoryStr.isEmpty()) {
            binding.propertyCategoryTv.setError("مطلوب");
            return;
        }
        if (propertyImageList.isEmpty() || propertyImageList.size() < 8) {
            binding.pickPhotos.setError(getString(R.string.pick_photos));
            return;
        }

        propertyModel.setPropertyStatus(propertyStatusStr);
        propertyModel.setPropertyPayment(propertyPaymentStr);
        propertyModel.setLeaseTerm(leaseTermStr);
        propertyModel.setPropertyCategory(propertyCategoryStr);
        propertyModel.setPropertyImage(propertyImageList);

        sendPropertyToFireBase();
    }

    private void pickImageFromGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE);
//        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void initAdapter() {

        adapter = new PropertyImagAdapter(this,
                mArrayUri, new DataCallBack() {
            @Override
            public void Result(Object obj, String type, Object otherData) {

            }
        });
        binding.propertyImgRv.setAdapter(adapter);

    }

    private void initFeaturesAdapter() {

        String[] m = this.getResources().getStringArray(R.array.chalet_features);//key_features

        adapterFeatures = new PropertyFeaturesAdapter(AddPropertyActivity2.this,
                m, false, new DataCallBack() {
            @Override
            public void Result(Object obj, String type, Object otherData) {

                binding.propertyFeaturesAdded.setVisibility(View.GONE);

                for (int i = 0; i > propertyaddedFeaturesList.size(); i++) {
                    if ((String) obj == (propertyaddedFeaturesList.get(i))) {
                        propertyaddedFeaturesList.remove(obj);
                        initAddedFeaturesAdapter();
                        break;
                    } else {
                        propertyaddedFeaturesList.add((String) obj);
                        binding.propertyAddedFeaturesRv.setVisibility(View.VISIBLE);
                        initAddedFeaturesAdapter();
                        break;
                    }
                }
            }
        });
        binding.propertyFeaturesRv.setAdapter(adapterFeatures);
    }

    private void initAddedFeaturesAdapter() {

        adapterAddedFeatures = new PropertyAddedFeaturesAdapter(AddPropertyActivity2.this,
                propertyaddedFeaturesList, true, new DataCallBack() {
            @Override
            public void Result(Object obj, String type, Object otherData) {
            }
        });
        binding.propertyAddedFeaturesRv.setAdapter(adapterAddedFeatures);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK
                    && null != data) {
                // Get the Image from data
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                List<String> imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    // Get the cursor
                    Cursor cursor = getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();
                    mArrayUri.add(mImageUri);
                    uploadPhoto(mImageUri);
//                    propertyImageList.add(mImageUri)

                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {

                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(uri);
                            // Get the cursor
                            Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imageEncoded = cursor.getString(columnIndex);
                            imagesEncodedList.add(imageEncoded);
                            cursor.close();
                            uploadPhoto(uri);
                        }
                        Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                    }
                }
                initAdapter();
            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadPhoto(Uri photoUri) {

        StorageReference imgRef = storageRef.child("propertsImages" + "/" + UUID.randomUUID().toString());
        UploadTask uploadTask = imgRef.putFile(photoUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
            }
        }).addOnSuccessListener(taskSnapshot -> {
            imgRef.getDownloadUrl().addOnCompleteListener(task -> {
//                propertyModel.setPropertyImage();
                propertyImageList.add(task.getResult().toString());
            });
        });
    }

    private void sendPropertyToFireBase() {

        binding.progressBar.setVisibility(View.VISIBLE);
        String propertyId = fireStoreDB.collection(Constants.KEY_PROPERTY).document().getId(); // this is auto genrat

        Map<String, Object> propertyModelMap = new HashMap<>();
        propertyModelMap.put("userId", userModel.getUserId());
        propertyModelMap.put("propertyId", propertyId);
        propertyModelMap.put("propertyStatus", propertyModel.getPropertyStatus());
        propertyModelMap.put("propertyCodeNum", propertyId);
        propertyModelMap.put("propertyImage", propertyModel.getPropertyImage());
        propertyModelMap.put("propertyPayment", propertyModel.getPropertyPayment());
        propertyModelMap.put("leaseTerm", propertyModel.getLeaseTerm());
        propertyModelMap.put("propertyCategory", propertyModel.getPropertyCategory());
        propertyModelMap.put("propertyType", propertyModel.getPropertyType());
        propertyModelMap.put("developmentStatus", propertyModel.getDevelopmentStatus());
        propertyModelMap.put("propertyLocation", propertyModel.getPropertyLocation());
        propertyModelMap.put("propertyPrice", propertyModel.getPropertyPrice());
        propertyModelMap.put("propertyArea", propertyModel.getPropertyArea());
        propertyModelMap.put("serviceFees", propertyModel.getServiceFees());
        propertyModelMap.put("roomsNum", propertyModel.getRoomsNum());
        propertyModelMap.put("bathroomsNum", propertyModel.getBathroomsNum());
        propertyModelMap.put("propertyCreatedAt", FieldValue.serverTimestamp());

        fireStoreDB.collection(Constants.KEY_USER).document(userModel.getUserId())
                .collection(Constants.KEY_PROPERTY).document(propertyId)
                .set(propertyModelMap, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Toast.makeText(AddTransferDeedActivity.this, getString(R.string.success_add), Toast.LENGTH_SHORT).show();
                            mackToast(AddPropertyActivity2.this, getString(R.string.success_add));
                            startActivity(new Intent(AddPropertyActivity2.this, HomePageActivity.class));
                        } else {
//                            Toast.makeText(AddTransferDeedActivity.this, getString(R.string.fail_add_data), Toast.LENGTH_SHORT).show();
                            mackToast(AddPropertyActivity2.this, getString(R.string.fail_add_data));
                        }
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
    }

    private void showAddDialog() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_add_new_features);

        EditText newPropertyFeature = dialog.findViewById(R.id.newPropertyFeature);
        Button addFeatureBtn = dialog.findViewById(R.id.addFeatureBtn);

        addFeatureBtn.setOnClickListener(view -> {
            String newPropertyFeatureStr = newPropertyFeature.getText().toString();
            if (newPropertyFeatureStr.isEmpty()){
                newPropertyFeature.setError("مطلوب");
            }else {
                propertyaddedFeaturesList.add(newPropertyFeatureStr);
                initAddedFeaturesAdapter();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}