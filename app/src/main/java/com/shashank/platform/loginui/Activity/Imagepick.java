package com.shashank.platform.loginui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.shashank.platform.loginui.Util.PhtotAdapter;
import com.shashank.platform.loginui.R;

import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class Imagepick extends AppCompatActivity {

    Button btnSelectImage;
    RecyclerView rcvPhoto;
    PhtotAdapter photoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagepick);

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
                     }
                 }
             });
 }

}