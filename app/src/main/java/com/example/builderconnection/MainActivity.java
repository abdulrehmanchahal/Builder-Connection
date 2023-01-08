package com.example.builderconnection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {



    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://builder-connection-c3d73-default-rtdb.firebaseio.com/");


    EditText editText_phone, editText_pass;
    Button btnlogin, btnregister;
    Switch aSwitch;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();



        btnlogin = findViewById(R.id.login);
        btnregister = findViewById(R.id.register);
        editText_phone = findViewById(R.id.user_phone);
        editText_pass = findViewById(R.id.Password);
        aSwitch = findViewById(R.id.print_switch);





        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone_text = editText_phone.getText().toString();
                String pass_text = editText_pass.getText().toString();


                if(phone_text.isEmpty() || pass_text.isEmpty()){

                    Toast.makeText(MainActivity.this,"Please enter phone no. or password",Toast.LENGTH_LONG).show();


                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(phone_text)){
                                String get_pass = snapshot.child(phone_text).child("pass").getValue(String.class);
                                if(get_pass.equals(pass_text)){
                                    Toast.makeText(MainActivity.this,"Successfully logged in",Toast.LENGTH_LONG).show();


                                    RecyclerFragment recyclerFragment = new RecyclerFragment();
                                    replacefragment(recyclerFragment);
                                    //openRecycler();


                                }
                                else {
                                    Toast.makeText(MainActivity.this,"Wrong password",Toast.LENGTH_LONG).show();

                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this,"Wrong Phone no.",Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }






            }
        });

        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openSIGNUPActivity();
            }
        });


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(aSwitch.isChecked()){

                    ///finger
                    BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Verify")
                            .setDescription("Finger Print Required")
                            .setNegativeButtonText("cancel")
                            .build();
                    getPrompt().authenticate(promptInfo);
                    ///finger

                }

                else {

                }
            }
        });



    }


    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_layout,fragment);
        fragmentTransaction.commit();
    }




    private BiometricPrompt getPrompt(){
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt.AuthenticationCallback callback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                notifyUser(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                notifyUser("Succeeded");

                RecyclerFragment recyclerFragment = new RecyclerFragment();
                replacefragment(recyclerFragment);

                //openRecycler();

            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                notifyUser("Failed!!!");
            }
        };
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, callback);
        return biometricPrompt;
    }

    private void notifyUser(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }





    public void openSIGNUPActivity(){
        Intent intent = new Intent(this, RegisterUser.class);
        startActivity(intent);
    }




    public void openRecycler(){
        Intent intent = new Intent(this, MainRecycler.class);
        startActivity(intent);
    }




}