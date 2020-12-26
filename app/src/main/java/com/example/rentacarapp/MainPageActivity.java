package com.example.rentacarapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainPageActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> downloadUrlFromDB;
    ArrayList<String> markaFromDB;
    ArrayList<String> modelFromDB;
    ArrayList<String> yilFromDB;
    ArrayList<String> vitesFromDB;
    ArrayList<String> ucretFromDB;
    MainPageRecyclerAdapter mainPageRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        setTitle("Mevcut Araçlar");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        downloadUrlFromDB = new ArrayList<>();
        markaFromDB = new ArrayList<>();
        modelFromDB = new ArrayList<>();
        yilFromDB = new ArrayList<>();
        vitesFromDB = new ArrayList<>();
        ucretFromDB = new ArrayList<>();

        getDataFromFirestore();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String bakalim = markaFromDB.get(position);
                        System.out.println(bakalim);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainPageRecyclerAdapter = new MainPageRecyclerAdapter(downloadUrlFromDB, markaFromDB, modelFromDB, yilFromDB, vitesFromDB, ucretFromDB);
        recyclerView.setAdapter(mainPageRecyclerAdapter);
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
            Intent intent = new Intent(MainPageActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    // VERITABANINA ATTIGIM VERILERI KULLANICIYA GOSTERMEK ICIN ALIYORUM
    public void getDataFromFirestore() {

        CollectionReference collectionReference = firebaseFirestore.collection("Cars");

        // collectionReference.whereEqualTo("vites", "Otomatik").addSnapshotListener(new EventListener<QuerySnapshot>() {
        // ÜSTTEKİ GİBİ OLSAYDI SADECE VİTESİ OTOMATİK OLAN ARAÇLARIN VERİLERİ ALINACAKTI
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){
                    Toast.makeText(getApplicationContext(), error.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }

                if (value != null){
                    // KOLEKSIYON ICERISINDEKI BELGELERI LOOP İLE ALIYORUM
                    // value, DocumentSnapshot döndüğü için bu şekilde aldım
                    for (DocumentSnapshot snapshot : value.getDocuments()){

                        // snapshot.getData() Map döndürdüğü için Map kullandım
                        Map<String, Object> data = snapshot.getData();

                        String downloadUrl = (String) data.get("carImageUrl");
                        String marka = (String) data.get("marka");
                        String model = (String) data.get("model");
                        String yil = (String) data.get("yil");
                        String vites = (String) data.get("vites");
                        String ucret = (String) data.get("ucret");

                        downloadUrlFromDB.add(downloadUrl);
                        markaFromDB.add(marka);
                        modelFromDB.add(model);
                        yilFromDB.add(yil);
                        vitesFromDB.add(vites);
                        ucretFromDB.add(ucret);

                        mainPageRecyclerAdapter.notifyDataSetChanged();
                    }
                }


            }
        });


    }





}