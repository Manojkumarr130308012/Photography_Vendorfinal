package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.single.PermissionListener;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Pattern;
import com.shashank.platform.loginui.Api.Api;
import com.shashank.platform.loginui.R;
import com.shuhart.stepview.StepView;
import com.tiper.MaterialSpinner;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import gun0912.tedimagepicker.builder.TedImagePicker;
import gun0912.tedimagepicker.builder.listener.OnMultiSelectedListener;

public class Register extends AppCompatActivity implements Validator.ValidationListener{
    private int currentStep = 0;
    LinearLayout lin1,lin2,lin3;
    List<String> locationname = new ArrayList<>();
    List<String> catgoryname = new ArrayList<>();
    List<String> servicename = new ArrayList<>();
    MaterialSpinner loc,category;
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
    String dobstring,firstnamestring,lastnamestring,passwordstring,mobilestring,watsappstring,emailstring,amountstring,companynamestring,dobsting,addressstring,descriptionstring;
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
        IDProf1 = (ImageView) findViewById(R.id.IdProf1);
        IDProf2 = (ImageView) findViewById(R.id.IdProf2);
        password = findViewById(R.id.password);



        loc=findViewById(R.id.location);
        category=findViewById(R.id.category);
        services=findViewById(R.id.chipGroup);
        dob=findViewById(R.id.dob);




        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        mobile=findViewById(R.id.mobile);
        watsapp=findViewById(R.id.watsapp);
        email=findViewById(R.id.email);
        companyname=findViewById(R.id.companyname);
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
                            }
                        }, year, month, day);
                datepicker.show();
            }
        });





        getitem();
        getservice();
        getcatgory();
        loc.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(MaterialSpinner materialSpinner) {

            }

            @Override
            public void onItemSelected(MaterialSpinner materialSpinner, View view, int i, long l) {
                if (i > -1) {
                    location = locationname.get(i).toString();
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
                    catg = catgoryname.get(i).toString();
                } else {
                    catg = "";
                }
            }
        });

        if (currentStep == 0){
            lin2.setVisibility(View.GONE);
            lin3.setVisibility(View.GONE);
        }

        final StepView stepView = findViewById(R.id.step_view);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
                Toast.makeText(Register.this, "Step " + step, Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(Register.this, ""+currentStep, Toast.LENGTH_SHORT).show();

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
                        lin3.setVisibility(View.VISIBLE);
                        showSelections();
                        if (currentStep < stepView.getStepCount() - 1) {
                            currentStep++;
                            stepView.go(currentStep, true);


                        } else {
                            stepView.done(true);

                        }
                        RegisterData();

                    }




                }

            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Register.this, ""+currentStep, Toast.LENGTH_SHORT).show();

                if (currentStep == 0){
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.GONE);
                    if (currentStep > 0) {
                        currentStep--;
                    }
                    stepView.done(false);
                    stepView.go(currentStep, true);
                }else  if (currentStep == 1){
                    lin1.setVisibility(View.VISIBLE);
                    lin2.setVisibility(View.GONE);
                    lin3.setVisibility(View.GONE);
                    if (currentStep > 0) {
                        currentStep--;
                    }
                    stepView.done(false);
                    stepView.go(currentStep, true);
                }else  if (currentStep == 2){
                    lin1.setVisibility(View.GONE);
                    lin2.setVisibility(View.VISIBLE);
                    lin3.setVisibility(View.GONE);
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
        steps.set(steps.size() - 1, steps.get(steps.size() - 1));
        stepView.setSteps(steps);
        IDProf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult( intent,1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }


                        }).check();
            }
        });
        IDProf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.CAMERA)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult( intent,2);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(com.karumi.dexter.listener.PermissionRequest permissionRequest, PermissionToken permissionToken) {

                            }


                        }).check();

            }
        });
    }




    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==0 && resultCode==RESULT_OK) {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            IDProf.setImageBitmap(bitmap);
            imagestring=BitMapToString(bitmap);
            Log.e("imagestring",""+imagestring);

        }else if (requestCode == 1 && resultCode == RESULT_OK ) {
            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            IDProf1.setImageBitmap(bitmap);
            imagestring1=BitMapToString(bitmap);
            Log.e("imagestring1",""+imagestring1);
        } else if (requestCode == 2 && resultCode == RESULT_OK ) {

            Bitmap bitmap=(Bitmap)data.getExtras().get("data");
            IDProf2.setImageBitmap(bitmap);
            imagestring2=BitMapToString(bitmap);
            Log.e("imagestring2",""+imagestring2);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    public void EnableRuntimePermissionToAccessCamera(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(Register.this,
                Manifest.permission.CAMERA))
        {

            // Printing toast message after enabling runtime permission.
//            Toast.makeText(Addimage.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Register.this,new String[]{Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }
    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

//                    Toast.makeText(Addimage.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(Register.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
    public String BitMapToString(Bitmap userImage1) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userImage1.compress(Bitmap.CompressFormat.PNG, 40, baos);
        byte[] b = baos.toByteArray();
        Document_img1 = Base64.encodeToString(b, Base64.DEFAULT);
        return Document_img1;
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



    public void getitem() {



        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject object = new JSONObject();


        String url = Api.locationurl;

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
                            }
                            Log.e("catgoryname", "" + catgoryname.toString());
                            ArrayAdapter<String> _Adapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_spinner_item, catgoryname);
                            _Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            category.setAdapter(_Adapter);
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
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "We got it right!", Toast.LENGTH_SHORT).show();
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


    private void RegisterData() {

        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject object = new JSONObject();
        try {
//            SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy");
//            Date todayDate = new Date();
//            String thisDate = currentDate.format(todayDate);
            object.put("fname",""+firstnamestring);
            object.put("lname",""+lastnamestring);
            object.put("mobile",""+mobilestring);
            object.put("whatsapp   ",""+watsappstring);
            object.put("email",""+emailstring);
            object.put("cname",""+companynamestring);
            object.put("amount",""+amountstring);
            object.put("dob",""+dobsting);
            object.put("address",""+addressstring);
            object.put("desc",""+descriptionstring);
            object.put("location",""+location);
            object.put("category",""+catg);
            object.put("service",""+s);
            object.put("image   ",""+imagestring1);
            object.put("proof",""+imagestring2);
            object.put("password",""+passwordstring);



        } catch (JSONException e) {
            e.printStackTrace();
        }


        String url =Api.registerurl;

        // Enter the correct url for your api service site

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,url,object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(Register.this, "Sucessfully Registered", Toast.LENGTH_SHORT).show();
                        Intent i=new Intent(Register.this,Plans.class);
                        startActivity(i);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("xddddd",""+error);

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}