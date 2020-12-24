package com.example.rentacarapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;


public class AdminPanel extends AppCompatActivity {

    Bitmap selectedCar;
    ImageView carImageView;
    EditText modelText;
    EditText markaText;
    EditText vitesText;
    EditText yilText;
    EditText plakaText;
    EditText ucretText;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;

    // FOTOGRAF VERISINI 2 FONKSIYONDA DA KULLANACAGIMIZ ICIN GLOBAL
    Uri imageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        setTitle("Bayi Paneli");

        carImageView = findViewById(R.id.carImage);
        modelText = findViewById(R.id.model);
        markaText = findViewById(R.id.marka);
        vitesText = findViewById(R.id.vites);
        yilText = findViewById(R.id.yil);
        plakaText = findViewById(R.id.plaka);
        ucretText = findViewById(R.id.ucret);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
    }


    public void saveCar(View view){

        if (imageData != null){

            //BENZERSIZ BIR ID OLUSTURDUM
            UUID uuid = UUID.randomUUID();
            final String imageName = "cars/" + uuid + ".jpg";

            storageReference.child(imageName).putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    StorageReference newReference = FirebaseStorage.getInstance().getReference(imageName);
                    newReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            String downloadUrl = uri.toString();

                            String marka = markaText.getText().toString();
                            String model = modelText.getText().toString();
                            String vites = vitesText.getText().toString();
                            String yil = yilText.getText().toString();
                            String plaka = plakaText.getText().toString();
                            String ucret = ucretText.getText().toString();

                            HashMap<String, Object> carData = new HashMap<>();
                            carData.put("carImageUrl", downloadUrl);
                            carData.put("marka", marka);
                            carData.put("model", model);
                            carData.put("vites", vites);
                            carData.put("yil", yil);
                            carData.put("plaka", plaka);
                            carData.put("ucret", ucret);
                            carData.put("date", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Cars").document(plaka).set(carData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {



                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminPanel.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void selectCarImage(View view){

        // GALERIYE ERISMEK ICIN IZIN ALIYORUZ
        // API23 VE ONCESI IZIN ALMAMIZA GEREK YOK
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else{ // IZIN VERILMISSE
            Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intentToGallery, 2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentToGallery, 2);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            // DATA INTENT OLD ICIN URI'A CEVIRDIM
            imageData = data.getData();
            try {

                if (Build.VERSION.SDK_INT >= 28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), imageData);
                    selectedCar = ImageDecoder.decodeBitmap(source);
                    carImageView.setImageBitmap(selectedCar);
                }else
                {
                    selectedCar = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageData);
                    carImageView.setImageBitmap(selectedCar);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}