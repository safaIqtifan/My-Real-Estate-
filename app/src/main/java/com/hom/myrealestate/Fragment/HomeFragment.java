package com.hom.myrealestate.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hom.myrealestate.Activitys.PropertyDetailActivity;
import com.hom.myrealestate.Adapter.PropertyAdapter;
import com.hom.myrealestate.Models.PropertyModel;
import com.hom.myrealestate.Models.UserModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.Constants;
import com.hom.myrealestate.classes.DataCallBack;
import com.hom.myrealestate.classes.UtilityApp;
import com.hom.myrealestate.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    FirebaseFirestore fireStoreDB;

    UserModel userModel;
//    PropertyModel propertyModel;
    PropertyAdapter adapter;
    ArrayList<PropertyModel> propertyModelArrayList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        userModel = UtilityApp.getUserData();
        fireStoreDB = FirebaseFirestore.getInstance();
//        propertyModel = new PropertyModel();
        propertyModelArrayList = new ArrayList<>();

        binding.propertyRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        binding.swipeToRefreshLY.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                binding.swipeToRefreshLY.setRefreshing(false);
                fetchPropertyData();
            }
        });

        fetchPropertyData();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initAdapter() {

        adapter = new PropertyAdapter(requireActivity(),
                propertyModelArrayList, new DataCallBack() {
            @Override
            public void Result(Object obj, String type, Object otherData) {
                PropertyModel propertyModel = (PropertyModel) obj;
                Intent intent = new Intent(requireActivity(), PropertyDetailActivity.class);
                intent.putExtra(Constants.KEY_PROPERTY_DETAILS, propertyModel);
                startActivity(intent);
            }
        });
        binding.propertyRv.setAdapter(adapter);

    }

    public void fetchPropertyData() {

        binding.progressBar.setVisibility(View.VISIBLE);
        propertyModelArrayList.clear();

        fireStoreDB.collection(Constants.KEY_USER).document(userModel.getUserId())
                .collection(Constants.KEY_PROPERTY)
                .orderBy("propertyCreatedAt", Query.Direction.ASCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (isVisible()) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult().getDocuments()) {
                                    PropertyModel propertyModel = document.toObject(PropertyModel.class);
//                                    if (propertyModel.getUserId().equals(userModel.getUserId())){
                                    propertyModelArrayList.add(propertyModel);
//                                }
//                                    Toast.makeText(getActivity(), propertyModel.getPropertyId(), Toast.LENGTH_SHORT).show();
                                }
                                initAdapter();
                            } else {
                                Toast.makeText(getActivity(), getString(R.string.fail_get_data), Toast.LENGTH_SHORT).show();
                            }
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

}