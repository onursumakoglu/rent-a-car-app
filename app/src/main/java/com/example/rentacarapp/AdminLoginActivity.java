package com.example.rentacarapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class AdminLoginActivity extends AppCompatActivity {

    EditText adminEmail;
    EditText adminPassword;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        setTitle("Bayi Girişi");

        adminEmail = findViewById(R.id.adminEmailtext);
        adminPassword= findViewById(R.id.adminPasswordText);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }


    public void adminLoginClicked(View view){

        final String email = adminEmail.getText().toString();
        final String password = adminPassword.getText().toString();

        CollectionReference collectionReference = firebaseFirestore.collection("Admin");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                for (DocumentSnapshot snapshot : value.getDocuments()){

                    Map<String, Object> adminData = snapshot.getData();
                    String emailData = (String) adminData.get("email");
                    String passwordData = (String) adminData.get("password");

                    if (email.equals(emailData) && password.equals(passwordData)){

                        Intent intent = new Intent(AdminLoginActivity.this, AdminPanel.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(AdminLoginActivity.this, "Yanlış giriş!!!", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }
}