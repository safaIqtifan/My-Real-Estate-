package com.hom.myrealestate.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hom.myrealestate.databinding.FragmentOrderBinding;

public class OrderFragment extends Fragment {

private FragmentOrderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentOrderBinding.inflate(inflater, container, false);
    View root = binding.getRoot();


        return root;
    }

@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}