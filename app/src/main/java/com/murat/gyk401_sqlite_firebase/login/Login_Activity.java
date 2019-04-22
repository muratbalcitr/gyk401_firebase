package com.murat.gyk401_sqlite_firebase.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.murat.gyk401_sqlite_firebase.R;
import com.murat.gyk401_sqlite_firebase.activity.KitapActivity;

import java.util.Objects;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {


    Button btnLogin;
    TextView tvSignup, tvForgotPassword;
    EditText etUsername, etPassword;
    ConstraintLayout cnsMain;
    String email, password;
    ///ilk adım FirebaseAuth tanımlama
    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseApp.initializeApp(getApplicationContext());

        mFirebaseAuth = FirebaseAuth.getInstance();

        initialize();

    }

    private void initialize() {
        cnsMain = findViewById(R.id.cnsMain);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        tvSignup = findViewById(R.id.tvSignup);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        etUsername = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, Signup.class));
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this, ForgotAccount.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                login();
                break;
        }
    }

    private void login() {
        email = etUsername.getText().toString();
        password = etPassword.getText().toString();
        if (email.isEmpty() || password.isEmpty()) {
            Snackbar.make(cnsMain, "e-mail yada parola içeriği boş", 1000);
        } else {
            mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                            if (!verifyEmal()){
                                startActivity(new Intent(Login_Activity.this, KitapActivity.class));
                            }
                    } else {
                        Snackbar.make(cnsMain,"Kullanıcı doğrulanmamıştır lütfen  maili kontrol ediniz",1000).show();
                        signupDialog();
                    }
                }
            });

        }
    }

    private boolean verifyEmal() {

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
                                Log.e("gyk401", "sendEmailVerification", task.getException());
                                Toast.makeText(getApplicationContext(),
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        return false;
    }

    public void signupDialog() {
        AlertDialog.Builder bookDialog = new AlertDialog.Builder(this);
        bookDialog.setIcon(R.drawable.common_google_signin_btn_icon_dark);
        bookDialog.setMessage("Kayıt Olmak ister misiniz ?");
        bookDialog.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Login_Activity.this, Signup.class));

            }
        });
        bookDialog.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = bookDialog.create();
        alertDialog.show();

    }
}
