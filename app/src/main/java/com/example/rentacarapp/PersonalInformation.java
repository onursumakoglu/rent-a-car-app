package com.example.rentacarapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class PersonalInformation extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    String email;
    EditText isimText;
    EditText soyisimText;
    EditText tcText;
    EditText telText;
    EditText dogumTarihiText;
    EditText sehirText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        setTitle("Kişisel Bilgiler");

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        isimText = findViewById(R.id.isim_text);
        soyisimText = findViewById(R.id.soyisim_text);
        tcText = findViewById(R.id.tc_text);
        telText = findViewById(R.id.tel_text);
        dogumTarihiText = findViewById(R.id.dogumtarihi_text);
        sehirText = findViewById(R.id.sehir_text);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout){
            firebaseAuth.signOut();
            Intent intent = new Intent(PersonalInformation.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void bilgileriKaydet(View view){


        String isim = isimText.getText().toString();
        String soyisim = soyisimText.getText().toString();
        String tc = tcText.getText().toString();
        String tel = telText.getText().toString();
        String dogumTarihi = dogumTarihiText.getText().toString();
        String sehir = sehirText.getText().toString();

        Users user = new Users(isim, soyisim, tc, tel, dogumTarihi, sehir, email);

        Intent intent = new Intent(PersonalInformation.this, MainPageActivity.class);
        Toast.makeText(PersonalInformation.this, "Hoşgeldin! " + isim, Toast.LENGTH_LONG).show();
        startActivity(intent);
        finish();
    }



}