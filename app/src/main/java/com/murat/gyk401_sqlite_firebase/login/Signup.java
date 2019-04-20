package com.murat.gyk401_sqlite_firebase.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.murat.gyk401_sqlite_firebase.R;

import java.util.Objects;

public class Signup extends AppCompatActivity implements View.OnClickListener {


    Button btnLogin;
    EditText etMail, etPassword, etRePassword;
    ConstraintLayout cnsMain;
    String email, password;
    ///ilk adım FirebaseAuth tanımlama
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize();
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    private void initialize() {
        cnsMain = findViewById(R.id.cnsMain);
        btnLogin = findViewById(R.id.btnSignup);
        etMail = findViewById(R.id.etMail_Signup);
        etPassword = findViewById(R.id.etPassword_Signup);
        etRePassword = findViewById(R.id.etRePassword);
        btnLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignup:
                signup();
        }
    }

    String re_password;

    private void signup() {
        email = etMail.getText().toString();
        password = etPassword.getText().toString();
        re_password = etRePassword.getText().toString();
        if (password.matches(re_password)) {
            if (!password.matches("") && !re_password.matches("") && !email.matches("")) {
                mFirebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            VerifySendMail();
                        }
                    }
                });
            }
        } else {
            Toast.makeText(this, "parolalar uyuşmuyor", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean VerifySendEmail() {
        final FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (!firebaseUser.isEmailVerified()) {
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Signup.this, "doğrulama maili gönderildi. Lütfen"
                                + firebaseUser.getEmail() + "isimli maili kontrol ediniz", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Signup.this, "işlem başarısız", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return false;
    }

    private boolean VerifySendMail() {
        final FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        if (!Objects.requireNonNull(firebaseUser).isEmailVerified()) {
            firebaseUser.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        "Verification email sent to " + firebaseUser.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                                Log.d("Verification", "Verification email sent to " + firebaseUser.getEmail());
                            } else {
                                Log.e("hata", "sendEmailVerification", task.getException());
                                Toast.makeText(getApplicationContext(),
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        return false;
    }

}
