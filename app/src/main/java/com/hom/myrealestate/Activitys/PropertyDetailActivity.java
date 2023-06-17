package com.hom.myrealestate.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hom.myrealestate.Adapter.PropertyAdapter;
import com.hom.myrealestate.Models.PropertyModel;
import com.hom.myrealestate.Models.UserModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.Constants;
import com.hom.myrealestate.classes.UtilityApp;
import com.hom.myrealestate.databinding.ActivityAddPropertyBinding;
import com.hom.myrealestate.databinding.ActivityPropertyDetailBinding;

import java.util.ArrayList;

public class PropertyDetailActivity extends AppCompatActivity {

    ActivityPropertyDetailBinding binding;
    PropertyModel propertyModel = new PropertyModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_property_detail);
        binding = ActivityPropertyDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            propertyModel = (PropertyModel) bundle.getSerializable(Constants.KEY_PROPERTY_DETAILS);
            binding.propertyType.setText(propertyModel.getPropertyType());
            binding.propertyPrice.setText(propertyModel.getPropertyPrice());
            binding.propertyAddress.setText(propertyModel.getPropertyLocation());
            binding.propertyCodeNum.setText(propertyModel.getPropertyCodeNum());
            binding.propertyArea.setText(propertyModel.getPropertyArea());
            binding.bedroomsNumber.setText(propertyModel.getRoomsNum());
            binding.bathroomsNumber.setText(propertyModel.getBathroomsNum());
            binding.propertyStatus.setText(propertyModel.getPropertyStatus());
            binding.serviceFees.setText(propertyModel.getServiceFees());
            binding.propertyCategory.setText(propertyModel.getPropertyCategory());
            binding.propertyPayment.setText(propertyModel.getPropertyPayment());
        }


    }

}