package com.astr.hc.celebrations.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.astr.hc.celebrations.Util.MultipleSelectionSpinner;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.astr.hc.celebrations.Api.Api;

import com.astr.hc.celebrations.R;

import com.shuhart.stepview.StepView;
import com.tiper.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Register extends AppCompatActivity implements Validator.ValidationListener{
    private int currentStep = 0;
    LinearLayout lin1,lin2,lin3,lin4;
    List<String> locationname = new ArrayList<>();
    List<String> locationid = new ArrayList<>();
    List<String> statename = new ArrayList<>();
    List<String> stateid = new ArrayList<>();
    List<String> catgoryid = new ArrayList<>();
    List<String> catgoryname = new ArrayList<>();
    List<String> servicename = new ArrayList<>();
    MaterialSpinner loc,category,state;
    String location="";
    String catg="";
    ChipGroup services;
    String imagestring="",imagestring1="",imagestring2="",Document_img1;
    ImageView IDProf, IDProf1, IDProf2;
    public  static final int RequestPermissionCode  = 1 ;
    String s = null;
    DatePickerDialog datepicker;
    @NotEmpty
    @Length(min = 3, max = 10)
    TextInputEditText dob;
    @NotEmpty
    @Length(min = 3, max = 50)
    TextInputEditText firstname;
    @NotEmpty
    @Length(min = 3, max = 50)
    TextInputEditText lastname;
    @NotEmpty
    @Length(min = 1, max = 50)
    TextInputEditText amount;
    @NotEmpty
    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    TextInputEditText mobile;
    @NotEmpty
    @Pattern(regex =  "^\\(?([0-9]{3})\\)?[-.\\s]?([0-9]{3})[-.\\s]?([0-9]{4})$")
    TextInputEditText watsapp;
    @NotEmpty
    @Email
    TextInputEditText email;
    @NotEmpty
    @Length(min = 3, max = 50)
    TextInputEditText companyname;
    @Length(min = 3, max = 50)
    TextInputEditText Experience;

    @Length(min = 3, max = 50)
    TextInputEditText Achivement;
    @NotEmpty
    @Length(min = 3, max = 100)
    TextInputEditText address;
    @NotEmpty
    @Length(min = 3, max = 100)
    TextInputEditText description;
    @NotEmpty
    @Length(min = 3, max = 100)
    TextInputEditText password;
    private Validator validator;
    Bitmap bitmap1,bitmap2;
    boolean check = true;
    public static String oneSpace =" ";
    public static int tikMark =0X2714;
    public static int crossMark =0X2715;
    public static int tikMarkAroundBox =0X2611;
    public static int crossMarkAroundBox =0X274E;
    public static String dash ="-";
    private Spinner mySpinner;

    MultipleSelectionSpinner mSpinner;
    List<String> list = new ArrayList<String>();
    String dobstring,stateidstr,firstnamestring,Achivementsring,Experiencestring,lastnamestring,passwordstring,mobilestring,watsappstring,emailstring,amountstring,companynamestring,dobsting,addressstring,descriptionstring;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        validator = new Validator(this);
        validator.setValidationListener(this);

        lin1=findViewById(R.id.lin1);
        lin2=findViewById(R.id.lin2);
        lin3=findViewById(R.id.lin3);
        lin4=findViewById(R.id.lin4);
        IDProf1 = (ImageView) findViewById(R.id.IdProf1);
        IDProf2 = (ImageView) findViewById(R.id.IdProf2);
        password = findViewById(R.id.password);



        loc=findViewById(R.id.location);
        state=findViewById(R.id.state);
        category=findViewById(R.id.category);
        mSpinner = findViewById(R.id.mSpinner);
        services=findViewById(R.id.chipGroup);
        dob=findViewById(R.id.dob);




        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        mobile=findViewById(R.id.mobile);
        watsapp=findViewById(R.id.watsapp);
        email=findViewById(R.id.email);
        companyname=findViewById(R.id.companyname);
        Experience=findViewById(R.id.Experience);
        Achivement=findViewById(R.id.Achivement);
        address=findViewById(R.id.address);
        description=findViewById(R.id.description);
        amount=findViewById(R.id.amount);









        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datepicker = new DatePickerDialog(Register.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                dobsting=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });





        getstate();
        getservice();
        getcatgory();



        state.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    stateidstr = stateid.get(i).toString();
                    getitem(stateidstr);
                } else {
                    stateidstr = "";
                }
            }
        });

        loc.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    location = locationid.get(i).toString();

                } else {
                    location = "";
                }
            }
        });


        category.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    catg = catgoryid.get(i).toString();
                } else {
                    catg = "";
                }
            }
        });

        if (currentStep == 0){
            lin2.setVisibility(View.GONE);
            lin3.setVisibility(View.GONE);
            lin4.setVisibility(View.GONE);
        }

        final StepView stepView = findViewById(R.id.step_view);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
//                Toast.makeText(Register.this, "Step " + step, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(Register.this, ""+currentStep, Toast.LENGTH_SHORT).show();

                if (currentStep == 0){
                    firstnamestring=firstname.getText().toString();
                    lastnamestring=lastname.getText().toString();
                    mobilestring=mobile.getText().toString();
                    watsappstring=watsapp.getText().toString();
                    emailstring=email.getText().toString();


                    if (!firstnamestring.equals("")&!lastnamestring.equals("")&!mobilestring.equals("")&!watsappstring.equals("")&!emailstring.equals(""))
                    {
                        validator.validate();
                        lin1.setVisibility(View.GONE);
                        lin2.setVisibility(View.VISIBLE);
                        lin3.setVisibility(View.GONE);
                        lin4.setVisibility(View.GONE);
                        if (currentStep < stepView.getStepCount() - 1) {
                            currentStep++;
                            stepView.go(currentStep, true);

                        } else {
                            stepView.done(true);
                        }
                    }else{
                        validator.validate();
                    }


                }else  if (currentStep == 1){

                    companynamestring=companyname.getText().toString();
                    amountstring=amount.getText().toString();
                    dobstring=dob.getText().toString();
                    addressstring=address.getText().toString();
                    descriptionstring=description.getText().toString();


                    if (!companynamestring.equals("")&!amountstring.equals("")&!dobstring.equals("")&!addressstring.equals("")&!descriptionstring.equals(""))
                    {
                        validator.validate();
                        lin1.setVisibility(View.GONE);
                        lin2.setVisibility(View.GONE);
                        lin3.setVisibility(View.VISIBLE);
                        lin4.setVisibility(View.GONE);
                        showSelections();
                        if (currentStep < stepView.getStepCount() - 1) {
                            currentStep++;
                            stepView.go(currentStep, true);

                        } else {
                            stepView.done(true);
                        }
                    }else{
                        validator.validate();
                    }


                }else  if (currentStep == 2){
                    passwordstring=password.getText().toString();
                    if (imagestring1.equals("")&imagestring2.equals("")&location.equals(""))
                    {

                        validator.validate();
                        if (imagestring1 == ""){
                            Toast.makeText(Register.this, "Please Select the Profile", Toast.LENGTH_SHORT).show();
                        }
                        if (imagestring2 == ""){
                            Toast.makeText(Register.this, "Please Select the Prof", Toast.LENGTH_SHORT).show();
                        }
//                        if (s.equals(null)){
//                            Toast.makeText(Register.this, "Please Select the Catgory", Toast.LENGTH_SHORT).show();
//                        }
                        if (location.equals("")){
                            Toast.makeText(Register.this, "Please Select the Location", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        validator.validate();
                        lin1.setVisibility(View.GONE);
                        lin2.setVisibility(View.GONE);
                        lin3.setVisibility(View.GONE);
                        lin4.setVisibility(View.VISIBLE);
                        showSelections();
                        if (currentStep < stepView.getStepCount() - 1) {
                            currentStep++;
                            stepView.go(currentStep, true);


                        } else {
                            stepView.done(true);

                        }
//                        RegisterData();
//                        SendDetail();
                    }
                }else  if (currentStep == 3){
                    passwordstring=password.getText().toString();
                    if (imagestring1.equals("")&imagestring2.equals("")&location.equals(""))
                    {

                        validator.validate();
                        if (imagestring1 == ""){
                            Toast.makeText(Register.this, "Please Select the Profile", Toast.LENGTH_SHORT).show();
                        }
                        if (imagestring2 == ""){
                            Toast.makeText(Register.this, "Please Select the Prof", Toast.LENGTH_SHORT).show();
                        }
//                        if (s.equals(null)){
//                            Toast.makeText(Register.this, "Please Select the Catgory", Toast.LENGTH_SHORT).show();
//                        }
                        if (location.equals("")){
                            Toast.makeText(Register.this, "Please Select the Location", Toast.LENGTH_SHORT).show();
                        }

                    }else{
//                        validator.validate();
                        lin1.setVisibility(View.GONE);
                        lin2.setVisibility(View.GONE);
                        lin3.setVisibility(View.GONE);
                        lin4.setVisibility(View.VISIBLE);
//                        showSelections();

                        Achivementsring=Achivement.getText().toString();
                        Experiencestring=Experience.getText().toString();
                        if (currentStep < stepView.getStepCount() - 1) {
                            currentStep++;
                            stepView.go(currentStep, true);


                        } else {
                            stepView.done(true);

                        }
//                        RegisterData();
                        ImageUploadToServerFunction();
                    }
                }
            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(Register.this, ""+currentStep, Toast.LENGTH_SHORT).show();

                if (currentStep == 0){
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.GONE);
                    lin4.setVisibility(View.GONE);
                    if (currentStep > 0) {
                        currentStep--;
                    }
                    stepView.done(false);
                    stepView.go(currentStep, true);
                }else  if (currentStep == 1){
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.GONE);
                    lin4.setVisibility(View.GONE);
                    if (currentStep > 0) {
                        currentStep--;
                    }
                    stepView.done(false);
                    stepView.go(currentStep, true);
                }else  if (currentStep == 2){
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.VISIBLE);
                    lin3.setVisibility(View.GONE);
                    lin4.setVisibility(View.GONE);
                    if (currentStep > 0) {
                        currentStep--;
                    }
                    stepView.done(false);
                    stepView.go(currentStep, true);
                }else  if (currentStep == 3){
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.VISIBLE);
                    lin4.setVisibility(View.GONE);
                    if (currentStep > 0) {
                        currentStep--;
                    }
                    stepView.done(false);
                    stepView.go(currentStep, true);
                }
            }
        });
        List<String> steps = new ArrayList<>();
        steps.add("Basic Details");
        steps.add("Business Details");
        steps.add("Documents");
        steps.add("Final");
        steps.set(steps.size() - 1, steps.get(steps.size() - 1));
        stepView.setSteps(steps);
        IDProf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();

            }
        });
        IDProf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestPermission1();


            }
        });
    }







    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
    AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 1);//zero can be replaced with any action code

                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void selectImage1() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 3);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 4);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                    IDProf1.setImageBitmap(imageBitmap);
                bitmap1=imageBitmap;
                BitMapToString(imageBitmap);
            } else if (requestCode == 2) {
                Uri selectedImage = data.getData();
                IDProf1.setImageURI(selectedImage);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    bitmap1=bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                BitMapToString(bitmap);
            } else if (requestCode == 3) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                IDProf2.setImageBitmap(imageBitmap);
                bitmap2=imageBitmap;
                BitMapToString1(imageBitmap);
            } else if (requestCode == 4) {
                Uri selectedImage = data.getData();
                IDProf2.setImageURI(selectedImage);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    bitmap2=bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ;
                BitMapToString1(bitmap);
            }
        }
    }
    public void BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        imagestring1 = Base64.encodeToString(b, Base64.DEFAULT);
    }
    public void BitMapToString1(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 60, baos);
        byte[] b = baos.toByteArray();
        imagestring2 = Base64.encodeToString(b, Base64.DEFAULT);
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }



    public void getstate() {



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();


        String url = Api.statenurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd", "" + response);

                            JSONArray jObject1 = response.getJSONArray("state");

                            for (int i = 0; i < jObject1.length(); i++) {
                                JSONObject jsonObject = jObject1.getJSONObject(i);
                                statename.add(jsonObject.getString("state_name"));
                                stateid.add(jsonObject.getString("state_id"));
                            }
                            Log.e("locationname", "" + locationname.toString());
                            ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, statename);
                            _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            state.setAdapter(_Adapter);

//                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);


    }


    public void getitem(String stateidstr) {



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();


        String url =Api.locationurl+"?sid="+stateidstr;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd", "" + response);

                            JSONArray jObject1 = response.getJSONArray("location");

                            for (int i = 0; i < jObject1.length(); i++) {
                                JSONObject jsonObject = jObject1.getJSONObject(i);
                                locationname.add(jsonObject.getString("location_name"));
                                locationid.add(jsonObject.getString("location_id"));
                            }
                            Log.e("locationname", "" + locationname.toString());
                            ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, locationname);
                            _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            loc.setAdapter(_Adapter);
//                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);


    }



    public void getcatgory() {



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();


        String url = Api.catgorurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd", "" + response);

                            JSONArray jObject1 = response.getJSONArray("category");

                            for (int i = 0; i < jObject1.length(); i++) {
                                JSONObject jsonObject = jObject1.getJSONObject(i);
                                catgoryname.add(jsonObject.getString("category_name"));
                                catgoryid.add(jsonObject.getString("category_id"));
                            }
                            mSpinner.setItems(catgoryname,catgoryid);
//                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);


    }
    public void getservice() {



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();


        String url = Api.serviceurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
//                            message = (String) response.get("status");
                            Log.e("xdddddddddddd", "" + response);

                            JSONArray jObject1 = response.getJSONArray("services");

                            for (int i = 0; i < jObject1.length(); i++) {
                                JSONObject jsonObject = jObject1.getJSONObject(i);
                                servicename.add(jsonObject.getString("services_name"));



                                try {
                                    LayoutInflater inflater = LayoutInflater.from(Register.this);

                                    // Create a Chip from Layout.
                                    Chip newChip = (Chip) inflater.inflate(R.layout.layout_chip_entry, services, false);
                                    newChip.setText(jsonObject.getString("services_name"));

                                    //
                                    // Other methods:
                                    //
                                    // newChip.setCloseIconVisible(true);
                                    // newChip.setCloseIconResource(R.drawable.your_icon);
                                    // newChip.setChipIconResource(R.drawable.your_icon);
                                    // newChip.setChipBackgroundColorResource(R.color.red);
                                    // newChip.setTextAppearanceResource(R.style.ChipTextStyle);
                                    // newChip.setElevation(15);

                                    services.addView(newChip);

                                    // Set Listener for the Chip:
                                    newChip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                            handleChipCheckChanged((Chip) buttonView, isChecked);
                                        }
                                    });

                                    newChip.setOnCloseIconClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            handleChipCloseIconClicked((Chip) v);
                                        }
                                    });


//                                    this.editTextKeyword.setText("");

                                } catch (Exception e) {
                                    e.printStackTrace();
//                                    Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                            Log.e("category_name", "" + catgoryname.toString());

//                    mTxtExpense.setText(objResDetails.getTodayPayments()+" Rs");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd", "" + error);

            }
        });
        requestQueue.add(jsonObjectRequest);


    }

    private void showSelections()  {
        int count = services.getChildCount();


        for(int i=0;i< count; i++) {
            Chip child = (Chip) services.getChildAt(i);

            if(!child.isChecked()) {
                continue;
            }

            if(s == null)  {
                s = child.getText().toString();
            } else {
                s += ", " + child.getText().toString();
            }
        }
//        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }

    // User close a Chip.
    private void handleChipCloseIconClicked(Chip chip) {
        ChipGroup parent = (ChipGroup) chip.getParent();
        parent.removeView(chip);
    }

    // Chip Checked Changed
    private void handleChipCheckChanged(Chip chip, boolean isChecked) {
    }

    @Override
    public void onValidationSucceeded() {
//        Toast.makeText(this, "We got it right!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);
            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        }
    }





    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

   /* private void uploadBitmap() {

        //getting the tag from the edittext


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Api.registerurl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            *//*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * *//*
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("fname",""+firstnamestring);
                params.put("lname",""+lastnamestring);
                params.put("mobile",""+mobilestring);
                params.put("whatsapp   ",""+watsappstring);
                params.put("email",""+emailstring);
                params.put("cname",""+companynamestring);
                params.put("amount",""+amountstring);
                params.put("dob",""+dobsting);
                params.put("address",""+addressstring);
                params.put("desc",""+descriptionstring);
                params.put("location",""+location);
                params.put("category",""+catg);
                params.put("service",""+s);
                params.put("password",""+passwordstring);
                return params;
            }

            *//*
             * Here we are passing image by renaming it with a unique name
             * *//*
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                long imagename1 = System.currentTimeMillis();
                params.put("image",new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap1)));
                params.put("proof",new DataPart(imagename1 + ".png", getFileDataFromDrawable(bitmap2)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }*/


    public void ImageUploadToServerFunction() {

        final ProgressDialog progressDialog = ProgressDialog.show(Register.this,
                "Please wait",
                "Loading...");

        ByteArrayOutputStream byteArrayOutputStreamObject;
        ByteArrayOutputStream byteArrayOutputStreamObject1;

        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        byteArrayOutputStreamObject1 = new ByteArrayOutputStream();

        bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject1);

        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        byte[] byteArrayVar1 = byteArrayOutputStreamObject1.toByteArray();

        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        final String ConvertImage1 = Base64.encodeToString(byteArrayVar1, Base64.DEFAULT);

        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

//                progressDialog = ProgressDialog.show(AddFancyActivity.this, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                // Dismiss the progress dialog after done uploading.
//                progressDialog.dismiss();
                progressDialog.dismiss();
                // Printing uploading success message coming from server on android app.
                Toast.makeText(Register.this, "Your Registration Success!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);

                // Setting image as transparent after done uploading.
//                buttonChoose.setImageResource(android.R.color.transparent);


            }

            @Override
            protected String doInBackground(Void... params) {

                Register.ImageProcessClass imageProcessClass = new Register.ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

String categorysteid=mSpinner.getSelectedItemsAsString();
String categorystename=mSpinner.buildSelectedItemString();
Log.e("dddddddd",""+categorysteid);
                HashMapParams.put("fname",""+firstnamestring);
                HashMapParams.put("lname",""+lastnamestring);
                HashMapParams.put("mobile",""+mobilestring);
                HashMapParams.put("wnumber",""+watsapp.getText().toString());
                HashMapParams.put("email",""+emailstring);
                HashMapParams.put("cname",""+companynamestring);
                HashMapParams.put("amount",""+amount.getText().toString());
                HashMapParams.put("dob",""+dobsting);
                HashMapParams.put("address",""+addressstring);
                HashMapParams.put("desc",""+descriptionstring);
                HashMapParams.put("state",""+stateidstr);
                HashMapParams.put("location",""+location);
                HashMapParams.put("category",""+categorysteid);
                HashMapParams.put("ncategory",""+categorystename);
                HashMapParams.put("service",""+s);
                HashMapParams.put("password",""+passwordstring);
                HashMapParams.put("image",""+ConvertImage);
                HashMapParams.put("proof",""+ConvertImage1);
                HashMapParams.put("exp",""+Experiencestring);
                HashMapParams.put("ach",""+Achivementsring);

                String FinalData = imageProcessClass.ImageHttpRequest(Api.registerurl, HashMapParams);


                Log.e("xxxxxx",""+HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }

    public class ImageProcessClass {

        public String ImageHttpRequest(String requestURL, HashMap<String, String> PData) {

            StringBuilder stringBuilder = new StringBuilder();

            try {

                URL url;
                HttpURLConnection httpURLConnectionObject;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject;
                BufferedReader bufferedReaderObject;
                int RC;

                url = new URL(requestURL);

                httpURLConnectionObject = (HttpURLConnection) url.openConnection();

                httpURLConnectionObject.setReadTimeout(19000);

                httpURLConnectionObject.setConnectTimeout(19000);

                httpURLConnectionObject.setRequestMethod("POST");

                httpURLConnectionObject.setDoInput(true);

                httpURLConnectionObject.setDoOutput(true);

                OutPutStream = httpURLConnectionObject.getOutputStream();

                bufferedWriterObject = new BufferedWriter(

                        new OutputStreamWriter(OutPutStream, "UTF-8"));

                bufferedWriterObject.write(bufferedWriterDataFN(PData));

                bufferedWriterObject.flush();

                bufferedWriterObject.close();

                OutPutStream.close();

                RC = httpURLConnectionObject.getResponseCode();

                if (RC == HttpsURLConnection.HTTP_OK) {

                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));

                    stringBuilder = new StringBuilder();

                    String RC2;

                    while ((RC2 = bufferedReaderObject.readLine()) != null) {

                        stringBuilder.append(RC2);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {

            StringBuilder stringBuilderObject;

            stringBuilderObject = new StringBuilder();

            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {

                if (check)

                    check = false;
                else
                    stringBuilderObject.append("&");

                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));

                stringBuilderObject.append("=");

                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }

            return stringBuilderObject.toString();
        }

    }




//    public void postdata() {
//
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JSONObject object = new JSONObject();
//
//        try {
//            object.put("fname",""+firstnamestring);
//            object.put("lname",""+lastnamestring);
//            object.put("mobile",""+mobilestring);
//            object.put("whatsapp   ",""+watsappstring);
//            object.put("email",""+emailstring);
//            object.put("cname",""+companynamestring);
//            object.put("amount",""+amountstring);
//            object.put("dob",""+dobsting);
//            object.put("address",""+addressstring);
//            object.put("desc",""+descriptionstring);
//            object.put("location",""+location);
//            object.put("category",""+catg);
//            object.put("service",""+s);
//            object.put("password",""+passwordstring);
//            object.put("image",""+imagestring1);
//            object.put("proof",""+imagestring2);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        String url = Api.registerurl;
//
//        // Enter the correct url for your api service site
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, object,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//
////                            message = (String) response.get("status");
//                            Log.e("xdddddddddddd", "" + response);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e("xddddd", "" + error);
//
//            }
//        });
//        requestQueue.add(jsonObjectRequest);
//
//
//    }


    private void requestPermission(){
        com.gun0912.tedpermission.PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                selectImagesFromGallery();
                selectImage();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Register.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
    private void requestPermission1(){
        com.gun0912.tedpermission.PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                selectImagesFromGallery();
                selectImage1();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Register.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
}