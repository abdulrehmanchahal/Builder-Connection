package com.example.builderconnection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

public class RegisterUser extends AppCompatActivity {




    EditText name, pass, phone_no, CNIC_no, user_type, work_det;
    ImageView img, work_img1, work_img2;
    Button register, browse,btn_img_1, btn_img_2;
    Uri filepath;
    Bitmap bitmap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().hide();

        pass = findViewById(R.id.Password);
        phone_no = findViewById(R.id.user_contact);
        name = findViewById(R.id.PERSON);
        CNIC_no = findViewById(R.id.user_CNIC);
        user_type = findViewById(R.id.user_type);
        work_det = findViewById(R.id.uer_info);




        img = findViewById(R.id.user_image);
        work_img1 = findViewById(R.id.builder_work_img1);
        work_img2 = findViewById(R.id.builder_work_img2);
        register = findViewById(R.id.REG);
        browse = findViewById(R.id.brs_image);
        btn_img_1 = findViewById(R.id.btn_wrk_img_1);
        btn_img_2 = findViewById(R.id.btn_wrk_img_2);

        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter .withActivity(RegisterUser.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image File"),1);

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });




        register.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String name_text = name.getText().toString();
                String pass_text = pass.getText().toString();
                String phone_text = phone_no.getText().toString();
                String CNIC_text = CNIC_no.getText().toString();

                if(name_text.isEmpty() || pass_text.isEmpty() || phone_text.isEmpty() || CNIC_text.isEmpty()){
                    Toast.makeText(RegisterUser.this,"Please fill all fields",Toast.LENGTH_SHORT).show();
                }
                else {

                    uploadonfirebase();
                    RecyclerFragment recyclerFragment = new RecyclerFragment();
                    replacefragment(recyclerFragment);


                    // openRecycler();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK){
            filepath=data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                img.setImageBitmap(bitmap);
            }catch (Exception ex){

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadonfirebase(){


        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setTitle("File Uploader");
        dialog.show();

        name = findViewById(R.id.PERSON);
        pass = findViewById(R.id.Password);
        phone_no = findViewById(R.id.user_contact);
        CNIC_no = findViewById(R.id.user_CNIC);



        FirebaseStorage storage=FirebaseStorage.getInstance();
        StorageReference uploader=storage.getReference("Image1" + new Random().nextInt(50));

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                dialog.dismiss();
                                FirebaseDatabase db=FirebaseDatabase.getInstance();
                                DatabaseReference root=db.getReference("users");

                                Dataholder obj=new Dataholder(name.getText().toString(),pass.getText().toString(),phone_no.getText().toString(),CNIC_no.getText().toString(),uri.toString());
                                root.child(phone_no.getText().toString()).setValue(obj);

                                name.setText("");
                                pass.setText("");
                                phone_no.setText("");
                                CNIC_no.setText("");
                                img.setImageResource(R.drawable.ic_iamge1);
                                Toast.makeText(getApplicationContext(),"Uploaded", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        float percent=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                        dialog.setMessage("uploaded :"+(int)percent+" %");

                    }
                });
    }


    public void openRecycler(){
        Intent intent = new Intent(this, MainRecycler.class);
        startActivity(intent);
    }

    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.rec_fragment,fragment);
        fragmentTransaction.commit();
    }
}