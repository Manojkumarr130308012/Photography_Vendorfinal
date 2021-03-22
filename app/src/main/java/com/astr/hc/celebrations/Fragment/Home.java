package com.astr.hc.celebrations.Fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.astr.hc.celebrations.Activity.Planupgrade;
import com.google.android.material.snackbar.Snackbar;
import com.astr.hc.celebrations.Activity.Image;
import com.astr.hc.celebrations.Activity.Imagepick;
import com.astr.hc.celebrations.Adapter.CustomGalleryAdapter;
import com.astr.hc.celebrations.Adapter.YoutubeRecyclerAdapter;
import com.astr.hc.celebrations.Api.Api;
import com.astr.hc.celebrations.Config.DBHelper;
import com.astr.hc.celebrations.Model.YoutubeVideo;
import com.astr.hc.celebrations.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.CLIPBOARD_SERVICE;


public class Home extends Fragment {
View view;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    SharedPreferences sh;
    GridView simpleGrid;
    Gallery simpleGallery;
    CustomGalleryAdapter customGalleryAdapter;
    ImageView selectedImageView;
    RelativeLayout lin1;
    RelativeLayout lin2;
    TextView Accesriesinfobtn,digitalsbtn;
    RecyclerView recyclerView;
    List<YoutubeVideo> youtubeVideos;
    Button addphoto;
    Button upload;
    // array of images
    String[] images=new String[0];
    String[] imagesid=new String[0];
    DBHelper dbHelper;
    String id,na,pa;
    private static final int INTENT_REQUEST_GET_IMAGES = 13;
    static final int OPEN_MEDIA_PICKER = 1;
    
    ArrayList<Uri> image_uris = new ArrayList<Uri>();
    private ViewGroup mSelectedImagesContainer;
    EditText editText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerViewFeed;
    YoutubeRecyclerAdapter mRecyclerAdapter;
    String image,videos;
    int getvideocount;
    ArrayList<YoutubeVideo> videoArrayList;
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
        dbHelper=new DBHelper(getActivity());
        Cursor res = dbHelper.getAllData();
        videoArrayList=new ArrayList<>();
        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
            image = res.getString(3);
            videos = res.getString(4);

        }

        Log.e("na",""+na);
        Log.e("pa",""+pa);
        Log.e("image",""+image);
        Log.e("videos",""+videos);
        getvideocount= Integer.parseInt(videos);

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {



            } else {



                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);


            }
        }

        addphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent i=new Intent(getActivity(), Imagepick.class);
               i.putExtra("imagecount",""+images.length);
               startActivity(i);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (getvideocount > videoArrayList.size()){
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
                    editText.setText("");
                    ClipboardManager manager = (ClipboardManager)getActivity().getSystemService(CLIPBOARD_SERVICE);
//                ClipData pasteData = manager.getPrimaryClip();
//                ClipData.Item item = pasteData.getItemAt(0);
//                String paste = item.getText().toString();
//                editText.setText(""+paste);
                    // add a button
                    builder.setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    manager.setText("");
                                    // send data from the
                                    // AlertDialog to the Activity
                                    String pattern = "^(http(s)?:\\/\\/)?((w){3}.)?youtu(be|.be)?(\\.com)?\\/.+";
                                    String youTubeURl=editText.getText().toString();
                                    if (!youTubeURl.isEmpty() && youTubeURl.matches(pattern))
                                    {
                                        postvideos(editText.getText().toString());
                                    }
                                    else
                                    {
                                        // Not Valid youtube URL
                                        Snackbar.make(view, "Only Upload Youtube links", Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                    }

//                                    new Home.ReadJSON().execute(Api.videosurl+""+na);
                                }
                            });

                    // create and show
                    // the alert dialog
                    AlertDialog dialog
                            = builder.create();
                    dialog.show();
                }else{
                    Snackbar.make(view, "Your Limit is Over.Upgrade your Plan!!!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        Accesriesinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin1.setVisibility(View.VISIBLE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.primary_dark2));
                digitalsbtn.setTextColor(getResources().getColor(R.color.txt_medium_gray));


            }
        });

        digitalsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lin2.setVisibility(View.VISIBLE);
                lin1.setVisibility(View.GONE);
//                Accesriesinfobtn.setVisibility(View.VISIBLE);

                Accesriesinfobtn.setTextColor(getResources().getColor(R.color.txt_medium_gray));
                digitalsbtn.setTextColor(getResources().getColor(R.color.primary_dark2));


            }
        });



        simpleGrid =view.findViewById(R.id.simpleGridView); // init GridView
        // Create an object of CustomAdapter and set Adapter to GirdView

        // implement setOnItemClickListener event on GridView
        simpleGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(getActivity(), Image.class);
                intent.putExtra("image", imagesid[position]); // put image data in Intent
                intent.putExtra("imageid", images[position]); // put image data in Intent
                startActivity(intent); // start Intent
            }
        });


        recyclerView = view.findViewById(R.id.recyclerView);
        ButterKnife.bind(getActivity());
        // prepare data for list


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Home.ReadJSON().execute(Api.videosurl+""+na);
                new Home.Readplan().execute(Api.homeplanurl+""+na);
                new Home.ReadJSON1().execute(Api.imagsurl+""+na);
            }
        });
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




    class Readplan extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONObject obj = jsonObject.getJSONObject("reg");
                String msg = obj.getString("msg");

                if (getActivity()!=null) {
                    //set layout manager and adapter for "GridView"

if (msg.equals("Failed")){
Intent i=new Intent(getActivity(), Planupgrade.class);
startActivity(i);
}

             }

            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }




        }
    }
    class ReadJSON extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("videos");
                videoArrayList.clear();
                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    YoutubeVideo video1 = new YoutubeVideo();
                    video1.setId(""+productObject.getString("video_id"));
                    String ytrl=""+productObject.getString("video_src");
                    String regExp = "/.*(?:youtu.be\\/|v\\/|u/\\w/|embed\\/|watch\\?.*&?v=)";
                    Pattern compiledPattern = Pattern.compile(regExp);
                    Matcher matcher = compiledPattern.matcher(ytrl);
                    if(matcher.find()){
                        int start = matcher.end();
                        System.out.println("ID : " + ytrl.substring(start, start+11));

                        video1.setVideoId(""+ytrl.substring(start, start+11));
                        videoArrayList.add(video1);
                    }



                }
                if (getActivity()!=null) {
                    //set layout manager and adapter for "GridView"

                    mRecyclerAdapter = new YoutubeRecyclerAdapter(videoArrayList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(mRecyclerAdapter);



                }

            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }




        }
    }
    class ReadJSON1 extends AsyncTask<String, Integer, String> {
        ArrayList<YoutubeVideo> videoArrayList=new ArrayList<>();
        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray =  jsonObject.getJSONArray("images");
                images=new String[jsonArray.length()];
                imagesid=new String[jsonArray.length()];

                for(int i =0;i<jsonArray.length(); i++){
                    JSONObject productObject = jsonArray.getJSONObject(i);

                    YoutubeVideo video1 = new YoutubeVideo();
                   String imgid=""+productObject.getString("image_id");
                    String imagtrl=""+productObject.getString("image_src");

                    images[i]=productObject.getString("image_id");
                    imagesid[i]=productObject.getString("image_src");

                }

                if (getActivity()!=null) {
                    //set layout manager and adapter for "GridView"

                    CustomGalleryAdapter customAdapter = new CustomGalleryAdapter(getActivity(), images,imagesid);
                    simpleGrid.setAdapter(customAdapter);



                }
            } catch (JSONException e) {
                e.printStackTrace();
//                custPrograssbar.closePrograssBar();
            }
            Log.e("images",""+images);
            Log.e("image_id",""+imagesid);




        }
    }

    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);
            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();
            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void postvideos(String videourl) {

        String url = Api.postvideourl;
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
                "Please wait",
                "Loading...");

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //let's parse json data

                Log.e("gfgfgfghf",""+response);
                progressDialog.dismiss();
                new Home.ReadJSON().execute(Api.videosurl+""+na);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Post Data : Response Failed", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<String, String>();
                params.put("vid", ""+na);
                params.put("video", ""+videourl);
                return params;
            }

            @Override
            public Map<String,String> getHeaders() throws AuthFailureError {
                Map<String,String> params=new HashMap<String, String>();
//                params.put("Content-Type","application/json;charset=utf-8");
                params.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
//                params.put("Authorization","Bearer "+sToken);
//                params.put("X-Requested-With", "XMLHttpRequest");
                return params;
            }
        };

        requestQueue.add(stringRequest);


    }

}
