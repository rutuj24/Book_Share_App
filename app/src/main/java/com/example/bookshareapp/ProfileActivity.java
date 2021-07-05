package com.example.bookshareapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class ProfileActivity extends AppCompatActivity {

    TextView fullName,email,phone,location,books,verified;
    EditText searchTxt;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    Button editUser,searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile);
        phone = findViewById(R.id.profilePhone);
        verified = findViewById(R.id.verification);
        fullName = findViewById(R.id.profileName);
        email    = findViewById(R.id.profileEmail);
        location = findViewById(R.id.profileLoc);
        editUser = findViewById(R.id.changeProfile);
        books = findViewById(R.id.profileBooks);
        searchButton = findViewById(R.id.search);
        searchTxt = findViewById(R.id.searchText);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        FirebaseUser user = fAuth.getCurrentUser();
        userId = fAuth.getCurrentUser().getUid();

        if(!user.isEmailVerified()){
            verified.setVisibility(View.VISIBLE);
            searchButton.setVisibility(View.INVISIBLE);
            editUser.setVisibility(View.INVISIBLE);

            verified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "onFailure: Email not sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()){
                    phone.setText(documentSnapshot.getString("phone"));
                    fullName.setText(documentSnapshot.getString("fullName"));
                    email.setText(documentSnapshot.getString("email"));
                    location.setText(documentSnapshot.getString("location"));
                    books.setText(documentSnapshot.getString("books"));

                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });

        editUser.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(v.getContext(),EditProfileActivity.class);
               i.putExtra("fullName",fullName.getText().toString());
               i.putExtra("email",email.getText().toString());
               i.putExtra("phone",phone.getText().toString());
               i.putExtra("location",location.getText().toString());
               i.putExtra("books",books.getText().toString());
               startActivity(i);
           }
       });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(),MainActivity.class);
                i.putExtra("search",searchTxt.getText().toString());
                startActivity(i);
                finish();
            }
        });
    }

    public void logout(View view) {
        startActivity(new Intent(getApplicationContext(),EmptyActivity.class));
        finish();
    }
}