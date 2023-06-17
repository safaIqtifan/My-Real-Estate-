package com.hom.myrealestate.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hom.myrealestate.R;
import com.hom.myrealestate.classes.DataCallBack;
import com.hom.myrealestate.databinding.ItemPropertyFeaturesBinding;

import java.util.ArrayList;
import java.util.List;


public class PropertyAddedFeaturesAdapter extends RecyclerView.Adapter<PropertyAddedFeaturesAdapter.MyViewHolder> {

    private ArrayList<String> myArray;
    private List myArrayList;
    Activity context;
    boolean clicked;
    DataCallBack callBack;

    public PropertyAddedFeaturesAdapter(Activity context, ArrayList<String> myArray, boolean clicked, DataCallBack callBack) {
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
            holder.binding.propertyImage.setVisibility(View.GONE);
            holder.binding.propertyFeaturesCard.setBackground(context.getDrawable(R.drawable.add_imag_border));
//        }
        holder.binding.propertyFeaturesName.setText(myArray.get(position));

    }

    @Override
    public int getItemCount() {
        return myArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ItemPropertyFeaturesBinding binding;

        public MyViewHolder(@NonNull ItemPropertyFeaturesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.Result(myArray.get(getAdapterPosition()), "", getAdapterPosition());
                    myArray.remove(getAdapterPosition());
                }
            });
        }
    }
}
