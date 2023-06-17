package com.hom.myrealestate.classes;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.akexorcist.localizationactivity.ui.LocalizationApplication;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class RootApplication extends LocalizationApplication {

    private static RootApplication instance;
    private SharedPManger sharedPManger;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        sharedPManger = new SharedPManger(instance);
    }

    public static RootApplication getInstance() {
        return instance;
    }

    public SharedPManger getSharedPManger() {
        return sharedPManger;
    }

    @NotNull
    @Override
    public Locale getDefaultLanguage() {
        return new Locale("ar");
    }
}
