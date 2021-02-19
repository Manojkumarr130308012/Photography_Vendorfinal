package com.shashank.platform.loginui.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.material.snackbar.Snackbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.shashank.platform.loginui.Api.Api;
import com.shashank.platform.loginui.Api.ApiService;

import com.shashank.platform.loginui.Api.Apic;
import com.shashank.platform.loginui.Api.Client;
import com.shashank.platform.loginui.Config.DBHelper;
import com.shashank.platform.loginui.Util.FileUtils;
import com.shashank.platform.loginui.Util.InternetConnection;
import com.shashank.platform.loginui.Util.PhtotAdapter;
import com.shashank.platform.loginui.R;
import com.shashank.platform.loginui.rest.RealPathUtils;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Imagepick extends AppCompatActivity {
    private List<Uri> arrayList;
    Button btnSelectImage,btnUpload;
    RecyclerView rcvPhoto;
    PhtotAdapter photoAdapter;
    private ProgressBar mProgressBar;
    private static final String TAG = Imagepick.class.getSimpleName();
    boolean check = true;
    String ConvertImage;
    DBHelper dbHelper;
    String id,na,pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepick);
        mProgressBar = findViewById(R.id.progressBar);
        btnSelectImage=findViewById(R.id.sel_image);
        rcvPhoto=findViewById(R.id.rcv_photo);
        photoAdapter=new PhtotAdapter(this);
        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }


        Log.e("dddddddddddd",""+na);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,2);
        rcvPhoto.setLayoutManager(gridLayoutManager);
        rcvPhoto.setAdapter(photoAdapter);


        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            requestPermission();
            }
        });

        btnUpload = findViewById(R.id.upload);
        btnUpload.setOnClickListener(v -> uploadImagesToServer()  );
        arrayList = new ArrayList<>();

    }
 private void requestPermission(){
     PermissionListener permissionlistener = new PermissionListener() {
         @Override
         public void onPermissionGranted() {
            selectImagesFromGallery();
         }

         @Override
         public void onPermissionDenied(List<String> deniedPermissions) {
             Toast.makeText(Imagepick.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
         }
     };
     TedPermission.with(this)
             .setPermissionListener(permissionlistener)
             .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
             .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
             .check();
 }

 private void selectImagesFromGallery(){
     TedBottomPicker.with(Imagepick.this)
             .setPeekHeight(1600)
             .showTitle(false)
             .setCompleteButtonText("Done")
             .setEmptySelectionText("No Select")
             .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                 @Override
                 public void onImagesSelected(List<Uri> uriList) {

                     // here is selected image uri list
                     if (uriList != null && !uriList.isEmpty()){
                         photoAdapter.setData(uriList);
                         arrayList=uriList;
                     }
                 }
             });
 }


    private void uploadImagesToServer() {
        if (InternetConnection.checkConnection(Imagepick.this)) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            showProgress();

            // create list of file parts (photo, video, ...)
            List<MultipartBody.Part> parts = new ArrayList<>();

            // create upload service client
            ApiService service = retrofit.create(ApiService.class);

            if (arrayList != null) {
                // create part for file (photo, video, ...)
                for (int i = 0; i < arrayList.size(); i++) {
                    parts.add(prepareFilePart("image"+i, arrayList.get(i)));
                }
            }
Log.e("ffff",""+na);
            // create a map of data to pass along
            RequestBody description = createPartFromString(""+na);
            RequestBody size = createPartFromString(""+parts.size());

            // finally, execute the request
            Call<ResponseBody> call = service.uploadMultiple(description, size, parts);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    hideProgress();
                    if(response.isSuccessful()) {
                        Toast.makeText(Imagepick.this,
                                "Images successfully uploaded!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Imagepick.this, Bottommenu.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content),
                                R.string.string_some_thing_wrong, Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    hideProgress();
                    Log.e(TAG, "Image upload failed!", t);
                    Snackbar.make(findViewById(android.R.id.content),
                            "Image upload failed!", Snackbar.LENGTH_LONG).show();
                }
            });

        } else {
            hideProgress();
            Toast.makeText(Imagepick.this,
                    R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }

    private void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
//        btnChoose.setEnabled(false);
//        btnUpload.setVisibility(View.GONE);
    }

    private void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
//        btnChoose.setEnabled(true);
//        btnUpload.setVisibility(View.VISIBLE);
    }

    @NonNull
    private RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(MediaType.parse(FileUtils.MIME_TYPE_TEXT), descriptionString);
    }

    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(this, fileUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create (MediaType.parse(FileUtils.MIME_TYPE_TEXT), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }



    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Imagepick.this);
        final AlertDialog dialog = builder.setMessage("You need to grant access to Read External Storage")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(arg0 -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(Imagepick.this, android.R.color.holo_blue_light));
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(Imagepick.this, android.R.color.holo_red_light));
        });

        dialog.show();
    }


/*
    public void uploadMultipart() {
        //getting name for the image

        ArrayList<String> files = new ArrayList<>();
        //getting the actual path of the image
        for(int i=0;i<arrayList.size();i++){
            String filePath = getAbsolutePath(arrayList.get(i));
            files.add(filePath);
        }

        Log.e("files",""+files.toString());
        //Uploading code
        try {
//            String uploadId = UUID.randomUUID().toString();
            //Creating a multi part request
            MultipartBody.Builder builder = new MultipartBody.Builder();
            builder.setType(MultipartBody.FORM);
            builder.addFormDataPart("vid", "13");
            builder.addFormDataPart("images", files.toString(), RequestBody.create(MediaType.parse("multipart/form-data"),files.toString()));

//            for (int i = 0; i < files.size(); i++) {
//                File file = new File(files.get(i));
//                Log.e("file",""+file);
//            }

            MultipartBody requestBody = builder.build();
            Apic api = Client.api();
            Call<ResponseBody> call = api.uploadImages(requestBody);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    System.out.println("response received");
                    System.out.println(response.body());
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    System.out.println("error received");
                    System.out.println(t.getMessage());
                    System.out.println(t.toString());
                }
            });

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/

/*
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }
*/










 /*   private String getAbsolutePath(Uri uri) {
        String path = null;
        path = RealPathUtils.getRealPath(this, uri);
        return path;
    }

*/




/*
    public void ImageUploadToServerFunction() {




        class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {

                super.onPreExecute();

//                progressDialog = ProgressDialog.show(AddFancyActivity.this, "Image is Uploading", "Please Wait", false, false);
            }

            @Override
            protected void onPostExecute(String string1) {

                super.onPostExecute(string1);

                Toast.makeText(Imagepick.this, string1, Toast.LENGTH_LONG).show();



            }

            @Override
            protected String doInBackground(Void... params) {

                ImageProcessClass imageProcessClass = new ImageProcessClass();

                HashMap<String, String> HashMapParams = new HashMap<String, String>();

                 for (int i=0;i<arrayList.size();i++){
                     Bitmap bitmap = null;
                     try {
                         bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), arrayList.get(i));
                         ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                         bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
                         byte [] arr=baos.toByteArray();
                         ConvertImage=Base64.encodeToString(arr, Base64.DEFAULT);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                     HashMapParams.put("vid","13");
                     HashMapParams.put("images",""+ ConvertImage);
                 }

                String FinalData = imageProcessClass.ImageHttpRequest(Api.postimageurl, HashMapParams);


                Log.e("xxxxxx",""+HashMapParams);

                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();

        AsyncTaskUploadClassOBJ.execute();
    }
*/

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

}