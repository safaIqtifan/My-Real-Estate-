package com.hom.myrealestate.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hom.myrealestate.Models.PropertyModel;
import com.hom.myrealestate.Models.UserModel;
import com.hom.myrealestate.classes.BaseActivity;
import com.hom.myrealestate.classes.Constants;
import com.hom.myrealestate.classes.UtilityApp;
import com.hom.myrealestate.databinding.ActivityAddPropertyBinding;
import java.util.UUID;

public class AddPropertyActivity extends BaseActivity {

    ActivityAddPropertyBinding binding;
    FirebaseFirestore fireStoreDB;

    UserModel userModel;
    PropertyModel propertyModel;

    String propertyTypeStr, developmentStatusStr, propertyLocationStr,
            propertyPriceStr, propertyAreaStr, serviceFeesStr, roomsNumStr,
            bathroomsNumStr, uniqueID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_property);
        binding = ActivityAddPropertyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bar.title.setVisibility(View.VISIBLE);
        binding.bar.title.setText("إضافة عقار");
        fireStoreDB = FirebaseFirestore.getInstance();
        propertyModel = new PropertyModel();
        userModel = UtilityApp.getUserData();

        binding.propertyTypeArray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    propertyTypeStr = binding.propertyTypeArray.getSelectedItem().toString();
                else
                    propertyTypeStr = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.developmentStatusArray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i > 0)
                    developmentStatusStr = binding.propertyTypeArray.getSelectedItem().toString();
                else
                    developmentStatusStr = "";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.addPropertyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                propertyLocationStr = binding.propertyLocation.getText().toString();
                propertyPriceStr = binding.propertyPrice.getText().toString();
                propertyAreaStr = binding.propertyArea.getText().toString();
                serviceFeesStr = binding.serviceFees.getText().toString();
                roomsNumStr = binding.roomsNum.getText().toString();
                bathroomsNumStr = binding.bathroomsNum.getText().toString();
                uniqueID = UUID.randomUUID().toString();

                checkData();
            }
        });

    }

    private void checkData() {

        if (propertyTypeStr.isEmpty()) {
            binding.propertyTypeTv.setError("مطلوب");
            return;
        }
        if (developmentStatusStr.isEmpty()) {
            binding.developmentStatusTv.setError("مطلوب");
            return;
        }
        if (propertyLocationStr.isEmpty()) {
            binding.propertyLocation.setError("مطلوب");
            return;
        }
        if (propertyPriceStr.isEmpty()) {
            binding.propertyPrice.setError("مطلوب");
            return;
        }
        if (propertyAreaStr.isEmpty()) {
            binding.propertyArea.setError("مطلوب");
            return;
        }
        if (serviceFeesStr.isEmpty()) {
            binding.serviceFees.setError("مطلوب");
            return;
        }
        if (roomsNumStr.isEmpty()) {
            binding.roomsNum.setError("مطلوب");
            return;
        }
        if (bathroomsNumStr.isEmpty()) {
            binding.bathroomsNum.setError("مطلوب");
            return;
        }

        propertyModel.setPropertyType(propertyTypeStr);
        propertyModel.setDevelopmentStatus(developmentStatusStr);
        propertyModel.setPropertyLocation(propertyLocationStr);
        propertyModel.setPropertyPrice(propertyPriceStr);
        propertyModel.setPropertyArea(propertyAreaStr);
        propertyModel.setServiceFees(serviceFeesStr);
        propertyModel.setRoomsNum(roomsNumStr);
        propertyModel.setBathroomsNum(bathroomsNumStr);
//        propertyModel.setPropertyCodeNum(uniqueID);

//        Bundle bundle = new Bundle();
//        bundle.putSerializable(Constants.KEY_PROPERTY_ADDED, propertyModel);

        Intent intent = new Intent(AddPropertyActivity.this, AddPropertyActivity2.class);
        intent.putExtra(Constants.KEY_PROPERTY_ADDED, propertyModel);
        startActivity(intent);

    }

}