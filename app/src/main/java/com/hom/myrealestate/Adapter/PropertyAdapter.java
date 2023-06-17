package com.hom.myrealestate.Adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hom.myrealestate.Models.PropertyModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.DataCallBack;
import com.hom.myrealestate.databinding.ItemPropertyBinding;

import java.util.ArrayList;


public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.MyViewHolder> {

    public ArrayList<PropertyModel> myArray;
    Activity context;
    DataCallBack callBack;

    public PropertyAdapter(Activity context, ArrayList<PropertyModel> myArray, DataCallBack callBack) {
        this.context = context;
        this.myArray = myArray;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemPropertyBinding binding = ItemPropertyBinding.inflate(LayoutInflater.from(context), parent,
                false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        PropertyModel propertyModel = myArray.get(position);

        holder.binding.propertyType.setText(propertyModel.getPropertyType());
        holder.binding.propertyPrice.setText(propertyModel.getPropertyPrice());
        holder.binding.propertyAddress.setText(propertyModel.getPropertyLocation());
        holder.binding.propertyCodeNum.setText(propertyModel.getPropertyCodeNum());
        holder.binding.propertyArea.setText(propertyModel.getPropertyArea());
        holder.binding.bedroomsNumber.setText(propertyModel.getRoomsNum());

        Glide.with(context).asBitmap().load(propertyModel.getPropertyImage())
                .placeholder(R.drawable.property).into(holder.binding.propertyImage);
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemPropertyBinding binding;

        public MyViewHolder(@NonNull ItemPropertyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PropertyModel propertyModel = myArray.get(getAdapterPosition());
                    callBack.Result(propertyModel, "", getAdapterPosition());
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
