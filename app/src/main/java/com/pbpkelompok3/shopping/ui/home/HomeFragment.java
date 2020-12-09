package com.pbpkelompok3.shopping.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.pbpkelompok3.shopping.R;

public class HomeFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        ImageButton imgBtnMan = root.findViewById(R.id.imageButtonMan);
        ImageButton imgBtnWoman = root.findViewById(R.id.imageButtonWoman);

        imgBtnMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_nav_home_to_nav_man);
            }
        });

        imgBtnWoman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(root).navigate(R.id.action_nav_home_to_nav_woman);
            }
        });
        return root;
    }
}