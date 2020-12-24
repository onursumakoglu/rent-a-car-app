package com.example.rentacarapp;

import androidx.annotation.NonNull;
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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    EditText emailText, passwordText, passwordTextAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setTitle("Kullanıcı Kaydı");

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.emailtext);
        passwordText = findViewById(R.id.passwordtext);
        passwordTextAgain = findViewById(R.id.passwordtextagain);

    }

    public void signupClicked(View view){

        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        String passwordAgain = passwordTextAgain.getText().toString();

        if(password.matches(passwordAgain)){
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(SignUpActivity.this, "Kullanıcı Oluşturuldu", Toast.LENGTH_LONG).show();

                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            Intent intent = new Intent(SignUpActivity.this, PersonalInformation.class);
                            intent.putExtra("email", email);
                            startActivity(intent);
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                        }
                    });


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SignUpActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_LONG).show();
                }
            });

        }else{
            Toast.makeText(SignUpActivity.this, "Parolalar eşleşmiyor.", Toast.LENGTH_LONG).show();
        }

    }
}