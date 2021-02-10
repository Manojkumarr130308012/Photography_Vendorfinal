package com.shashank.platform.loginui.Fragment;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shashank.platform.loginui.Activity.Image;
import com.shashank.platform.loginui.Activity.Imagepick;
import com.shashank.platform.loginui.Adapter.CustomGalleryAdapter;
import com.shashank.platform.loginui.R;
import com.shashank.platform.loginui.Util.VideoAdapter;
import com.shashank.platform.loginui.Util.YouTubeVideos;

import java.util.ArrayList;
import java.util.Vector;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;


public class Home extends Fragment {
View view;
    GridView simpleGrid;
    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;
    RelativeLayout lin1;
    RelativeLayout lin2;
    TextView Accesriesinfobtn,digitalsbtn;
    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();
    Button addphoto;
    Button upload;
    // array of images
    int[] images = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
            R.drawable.ic_launcher_background, R.drawable.ic_launcher_background};

    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    static final int OPEN_MEDIA_PICKER = 1;
    
    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    private ViewGroup mSelectedImagesContainer;
    TextView editText;
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
        addphoto = view.findViewById(R.id.addphoto);
        upload = view.findViewById(R.id.upload);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(getActivity(), Imagepick.class);
               startActivity(i);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an alert builder
                AlertDialog.Builder builder
                        = new AlertDialog.Builder(getActivity());
                builder.setTitle("Paste Video Url");


                // set the custom layout
                final View customLayout
                        = getLayoutInflater()
                        .inflate(
                                R.layout.custom_layout,
                                null);
                builder.setView(customLayout);

                editText = customLayout.findViewById(R.id.tvPaste);
                registerForContextMenu(editText);

                // add a button
                builder.setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        // send data from the
                                        // AlertDialog to the Activity


                                    }
                                });

                // create and show
                // the alert dialog
                AlertDialog dialog
                        = builder.create();
                dialog.show();
            }
        });

        Accesriesinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin1.setVisibility(View.VISIBLE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.primary_dark));
                digitalsbtn.setTextColor(getResources().getColor(R.color.white));


            }
        });

        digitalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin2.setVisibility(View.VISIBLE);
                lin1.setVisibility(View.GONE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.white));
                digitalsbtn.setTextColor(getResources().getColor(R.color.primary_dark));


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


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager( new GridLayoutManager(getActivity(),3));

        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/SMKPKGW083c\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/DGQwd1_dpuc\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/Bd3ifQo9PBI\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/YTJg8q9Q940\" frameborder=\"0\" allowfullscreen></iframe>") );
        youtubeVideos.add( new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/IUN664s7N-c\" frameborder=\"0\" allowfullscreen></iframe>") );

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == OPEN_MEDIA_PICKER) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> selectionResult=data.getStringArrayListExtra("result");
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Options");
        switch (v.getId()) {

            case R.id.tvPaste:
                menu.add(0, v.getId(), 0, "Paste");
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.tvPaste:
                ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);
                if (manager != null && manager.getPrimaryClip() != null && manager.getPrimaryClip().getItemCount() > 0) {
                    editText.setText(manager.getPrimaryClip().getItemAt(0).getText().toString());
                }

                break;
        }


        return super.onContextItemSelected(item);


    }
}