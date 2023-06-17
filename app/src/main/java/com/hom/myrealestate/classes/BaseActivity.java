package com.hom.myrealestate.classes;

import android.app.Activity;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationActivity;

public class BaseActivity extends LocalizationActivity {

    Activity getActivity(){
        return  this;
    }

    public static void mackToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

}
