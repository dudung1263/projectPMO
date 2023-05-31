package e.amil.e_amil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {

    private EditText email, password;
    private Button btnlogin;
    public static FirebaseAuth auth;
    public static FirebaseAuth.AuthStateListener Listener;
    private String getEmail, getPassword;
    //private ProgressBar progesbar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

            email = findViewById(R.id.email);
            password = findViewById(R.id.password);
            btnlogin = findViewById(R.id.btnlogin);
            //progesbar_login = findViewById(R.id.progesbar_login);
            //rogesbar_login.setVisibility(View.GONE);

            auth = FirebaseAuth.getInstance();

            Listener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (user != null && user.isEmailVerified()) {
                        startActivity(new Intent(login.this, MainActivity.class));
                        finish();
                    }
                }
            };
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //progesbar_login.setVisibility(View.VISIBLE);
                    getEmail = email.getText().toString();
                    getPassword = password.getText().toString();

                    if (TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)) {
                        Toast.makeText(login.this, "Email dan Password Tidak Boleh Kosong",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        loginUserAccount();
                    }
                }
            });
        }

        @Override
        protected void onStart() {
            login.super.onStart();
            auth.addAuthStateListener(Listener);
        }

        @Override
        protected void onStop() {
            login.super.onStop();
            if  (Listener != null) {
                auth.removeAuthStateListener(Listener);
            }
        }

        private void loginUserAccount() {
            auth.signInWithEmailAndPassword(getEmail, getPassword)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //progesbar_login.setVisibility(View.GONE);
                                if (auth.getCurrentUser().isEmailVerified()) {
                                    Toast.makeText(login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder alert = new AlertDialog.Builder(login.this);
                                    alert.setTitle("Periksa Email anda, verifikasi!!");
                                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            return;
                                        }
                                    });
                                    alert.create();
                                    alert.show();
                                }
                            } else {
                                //progesbar_login.setVisibility(View.GONE);
                                Toast.makeText(login.this, task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    public void daftarakun(View view) {
        Intent intent = new Intent( login.this, daftar.class);
        startActivity(intent);
    }

}