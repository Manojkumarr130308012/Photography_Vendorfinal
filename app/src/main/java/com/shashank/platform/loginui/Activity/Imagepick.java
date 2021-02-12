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
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.material.snackbar.Snackbar;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.shashank.platform.loginui.Api.ApiService;

import com.shashank.platform.loginui.Util.FileUtils;
import com.shashank.platform.loginui.Util.InternetConnection;
import com.shashank.platform.loginui.Util.PhtotAdapter;
import com.shashank.platform.loginui.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepick);
        mProgressBar = findViewById(R.id.progressBar);
        btnSelectImage=findViewById(R.id.sel_image);
        rcvPhoto=findViewById(R.id.rcv_photo);
        photoAdapter=new PhtotAdapter(this);

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
        btnUpload.setOnClickListener(v -> uploadImagesToServer());
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

            // create a map of data to pass along
            RequestBody description = createPartFromString("www.androidlearning.com");
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

}