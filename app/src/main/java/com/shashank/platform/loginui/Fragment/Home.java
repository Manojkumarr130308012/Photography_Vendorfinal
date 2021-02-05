package com.shashank.platform.loginui.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shashank.platform.loginui.Activity.Image;
import com.shashank.platform.loginui.Adapter.CustomGalleryAdapter;
import com.shashank.platform.loginui.R;


public class Home extends Fragment {
View view;
    GridView simpleGrid;
    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;
    LinearLayout lin1,lin2;
    TextView Accesriesinfobtn,digitalsbtn;
    // array of images
    int[] images = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};
    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_home, container, false);

        lin1 = view.findViewById(R.id.lin1);
        lin2 = view.findViewById(R.id.lin2);
        Accesriesinfobtn = view.findViewById(R.id.personalinfobtn);
        digitalsbtn = view.findViewById(R.id.Bussinessbtn);

        Accesriesinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin1.setVisibility(View.VISIBLE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.white));
                digitalsbtn.setTextColor(getResources().getColor(R.color.primary_dark));


            }
        });

        digitalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin2.setVisibility(View.VISIBLE);
                lin1.setVisibility(View.GONE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.primary_dark));
                digitalsbtn.setTextColor(getResources().getColor(R.color.white));


            }
        });



        simpleGrid =view.findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView
        CustomGalleryAdapter customAdapter = new CustomGalleryAdapter(getActivity(), images);
        simpleGrid.setAdapter(customAdapter);
        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(getActivity(), Image.class);
                intent.putExtra("image", images[position]); // put image data in Intent
                startActivity(intent); // start Intent
            }
        });
        return view;
    }
}