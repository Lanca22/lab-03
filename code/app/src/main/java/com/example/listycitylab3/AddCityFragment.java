package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.listycitylab3.City;

public class AddCityFragment extends DialogFragment {
    public interface AddCityDialogListener {
        void addCity(City city);
        void editCity(City cityToEdit, City updatedCity);
    }

    private AddCityDialogListener listener;
    private City cityToEdit;


    public AddCityFragment() {
    }

    public AddCityFragment(City cityToEdit) {
        this.cityToEdit = cityToEdit;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        if (cityToEdit != null) {
            editCityName.setText(cityToEdit.getName());
            editProvinceName.setText(cityToEdit.getProvince());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        String title = (cityToEdit != null) ? "Edit City" : "Add a City";
        String buttonText = (cityToEdit != null) ? "Save" : "Add";

        return builder
                .setView(view)
                .setTitle(title)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(buttonText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City newCity = new City(cityName, provinceName);

                    if (cityToEdit != null) {
                        // Editing an existing city
                        listener.editCity(cityToEdit, newCity);
                    } else {
                        // Adding a new city
                        listener.addCity(newCity);
                    }
                })
                .create();
    }
}