package com.hom.myrealestate.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hom.myrealestate.Models.PropertyModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.DataCallBack;
import com.hom.myrealestate.databinding.ItemPropertyBinding;
import com.hom.myrealestate.databinding.ItemPropertyFeaturesBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PropertyFeaturesAdapter extends RecyclerView.Adapter<PropertyFeaturesAdapter.MyViewHolder> {

    private String[] myArray;
    private List myArrayList;
    Activity context;
    boolean clicked;
    DataCallBack callBack;

    public PropertyFeaturesAdapter(Activity context, String[] myArray, boolean clicked, DataCallBack callBack) {
        this.context = context;
        this.myArray = myArray;
        this.clicked = clicked;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemPropertyFeaturesBinding binding = ItemPropertyFeaturesBinding.inflate(LayoutInflater.from(context), parent,
                false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

//        if (clicked) {
//            holder.binding.propertyImage.setVisibility(View.GONE);
//        } else {
//            holder.binding.propertyImage.setVisibility(View.VISIBLE);
//        }
        holder.binding.propertyFeaturesName.setText(myArray[position]);

    }

    @Override
    public int getItemCount() {
        return myArray.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemPropertyFeaturesBinding binding;

        public MyViewHolder(@NonNull ItemPropertyFeaturesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.Result(myArray[getAdapterPosition()], "", getAdapterPosition());
                    if (clicked) {
                        binding.propertyFeaturesCard.setBackground(context.getDrawable(R.drawable.background_shap));
//                        myArrayList = Arrays.asList(myArrayList);
//                        myArrayList.remove(getAdapterPosition());
                    }else {
                        binding.propertyFeaturesCard.setBackground(context.getDrawable(R.drawable.button_round));

                    }
                }
            });

            itemView.setOnLongClickListener(view -> {
//                CategoryModel categoryModel = myArray.get(getBindingAdapterPosition());
//                choseDialog = new ChoseDialog(context, categoryModel, new DataCallBack() {
//                    @Override
//                    public void Result(Object obj, String type, Object otherData) {
//                        callBack.Result(categoryModel, type, getBindingAdapterPosition());
//                    }
//                });
                return false;
            });
        }
    }
}
