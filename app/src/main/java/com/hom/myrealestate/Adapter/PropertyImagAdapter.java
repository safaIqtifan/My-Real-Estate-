package com.hom.myrealestate.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hom.myrealestate.Models.PropertyModel;
import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.DataCallBack;
import com.hom.myrealestate.databinding.ItemPropertyBinding;
import com.hom.myrealestate.databinding.ItemPropertyImagBinding;

import java.util.ArrayList;
import java.util.List;


public class PropertyImagAdapter extends RecyclerView.Adapter<PropertyImagAdapter.MyViewHolder> {

    public ArrayList<Uri> myArray;
    Activity context;
    DataCallBack callBack;

    public PropertyImagAdapter(Activity context, ArrayList<Uri> myArray, DataCallBack callBack) {
        this.context = context;
        this.myArray = myArray;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemPropertyImagBinding binding = ItemPropertyImagBinding.inflate(LayoutInflater.from(context), parent,
                false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Uri propertyImagList = myArray.get(position);

        Glide.with(context).asBitmap().load(propertyImagList)
                .placeholder(R.drawable.property).into(holder.binding.propertyImage);
    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemPropertyImagBinding binding;

        public MyViewHolder(@NonNull ItemPropertyImagBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.closeFab.setOnClickListener(v -> {

            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri propertyImagList = myArray.get(getAdapterPosition());
                    callBack.Result(propertyImagList, "", getAdapterPosition());
                }
            });

//            itemView.setOnLongClickListener(view -> {
//                CategoryModel categoryModel = myArray.get(getBindingAdapterPosition());
//                choseDialog = new ChoseDialog(context, categoryModel, new DataCallBack() {
//                    @Override
//                    public void Result(Object obj, String type, Object otherData) {
//                        callBack.Result(categoryModel, type, getBindingAdapterPosition());
//                    }
//                });
//                return false;
//            });
        }
    }
}
