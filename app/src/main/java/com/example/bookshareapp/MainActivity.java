package com.example.bookshareapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    TextView data;
    Button profile;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        final String search = intent.getStringExtra("search");

        data = findViewById(R.id.name);
        profile = findViewById(R.id.profile);
        db = FirebaseFirestore.getInstance();

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });

        fetchData(search);

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final String filter = search.toString();
//                db.collection("users").get()
//                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                            @Override
//                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                                if (filter.isEmpty()){
//                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots){
//                                        String allBooks = document.getString("books");
//                                        if (allBooks.isEmpty())
//                                            allBooks = "*No Books*";
//                                        data.append(document.getString("fullName") + "\n" + document.getString("location")
//                                                + "\n" + document.getString("email") + "\n" + document.getString("phone")
//                                                + "\n" + allBooks + "\n\n");
//                                    }
//                                }
//                                else {
//                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots){
//                                        String allBooks = document.getString("books");
//                                        if (allBooks.isEmpty())
//                                            allBooks = "*No Books*";
//                                        if (allBooks.contains(filter)){
//                                            data.append(document.getString("fullName") + "\n" + document.getString("location")
//                                                    + "\n" + document.getString("email") + "\n" + document.getString("phone")
//                                                    + "\n" + allBooks + "\n\n");
//                                        }
//                                    }
//                                }
//                            }
//                        });
//            }
//        });

    }

    private void fetchData(String a) {
        db.collection("users").orderBy("location").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots){
                            if(a.isEmpty()){
                                String allBooks = document.getString("books");
                                if (allBooks.isEmpty())
                                    allBooks = "*No Books*";
                                data.append("Name : " + document.getString("fullName") + "\n"
                                        + "Location : " + document.getString("location") + "\n"
                                        + "Email : " +  document.getString("email") + "\n"
                                        + "Books : " +  allBooks + "\n" +
                                        "_________________________________" +  "\n\n");
                            }
                            else{
                                String allBooks = document.getString("books");
                                if (allBooks.toUpperCase().contains(a.toUpperCase())){
                                    if (allBooks.isEmpty())
                                        allBooks = "*No Books*";
                                    data.append("Name : " + document.getString("fullName") + "\n"
                                            + "Location : " + document.getString("location") + "\n"
                                            + "Email : " +  document.getString("email") + "\n"
                                            + "Books : " +  allBooks + "\n" +
                                            "_________________________________" + "\n\n");
                                }
                            }
                        }
                    }
                });
    }

    public void logout(View view) {
        startActivity(new Intent(getApplicationContext(),EmptyActivity.class));
        finish();
    }
}