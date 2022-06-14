package com.ayoapp.cryptocurrency;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FavoriteFragment extends Fragment {
    TextView favoriteText;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_favorite, null);
        favoriteText = fragmentView.findViewById(R.id.favoriteText);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String name = preferences.getString("Name", "");
        if(!name.equalsIgnoreCase(""))
        {
            name = "Favorites: "+name ;  /* Edit the value here*/
        }
        favoriteText.setText(name);





        return fragmentView;
    }
}
