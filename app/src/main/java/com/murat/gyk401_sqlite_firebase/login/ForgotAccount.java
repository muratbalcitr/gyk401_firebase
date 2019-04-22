package com.murat.gyk401_sqlite_firebase.login;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.murat.gyk401_sqlite_firebase.R;

public class ForgotAccount extends AppCompatActivity {

    Button btnMewPassword;
    TextInputEditText etMailForgot;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_account);
        btnMewPassword = findViewById(R.id.btnNewPassword);
        etMailForgot = findViewById(R.id.etMailForgot);

        mAuth = FirebaseAuth.getInstance();


        btnMewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword();
            }
        });
    }

    private void newPassword() {
        String email = etMailForgot.getText().toString();
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(ForgotAccount.this, "mail kontrol ediniz", Toast.LENGTH_SHORT).show();
                }else if (task.isCanceled()){
                    Toast.makeText(ForgotAccount.this, "işlem iptal edildi", Toast.LENGTH_SHORT).show();
                }else{
                     Toast.makeText(ForgotAccount.this, "işlem başarısız", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
