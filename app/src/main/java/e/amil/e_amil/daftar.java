package e.amil.e_amil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class daftar extends AppCompatActivity {

    private EditText  nama_daftar, email_daftr, password2_dftr;
    private Button btndaftar;
    //private ProgressBar progesbar_daftar;
    private FirebaseAuth auth;
    private String getUsername, getEmail, getPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        nama_daftar = findViewById(R.id.nama_daftar);
        email_daftr = findViewById(R.id.email_daftr);
        password2_dftr = findViewById(R.id.password2_dftr);
        btndaftar = findViewById(R.id.btndaftar);
        //progesbar_daftar = findViewById(R.id.progesbar_daftar);
        //progesbar_daftar.setVisibility(View.GONE);
        auth = FirebaseAuth.getInstance();

        btndaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //progesbar_daftar.setVisibility(View.VISIBLE);
                cekDataUser();
            }

            private void cekDataUser() {
                //mendapatkan data inputan user
                getUsername = nama_daftar.getText().toString();
                getEmail = email_daftr.getText().toString();
                getPassword = password2_dftr.getText().toString();

                if (TextUtils.isEmpty(getUsername) || TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPassword)) {
                    Toast.makeText(daftar.this, "Username, Email, dan Password tidak boleh kosong",
                            Toast.LENGTH_SHORT).show();
                } else {
                    createUserAccount();
                }
            }

            private void createUserAccount() {
                auth.createUserWithEmailAndPassword(getEmail, getPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                Pengguna user = new Pengguna(getEmail, getPassword);
                                FirebaseDatabase.getInstance().getReference("Pengguna")
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                //cek status keberhasilan daftar email dan password baru
                                                if (task.isSuccessful()) {
                                                    //progesbar_daftar.setVisibility(View.GONE);
                                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(
                                                            new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        Toast.makeText(daftar.this, "Registrasi Berhasil !!, Silakan Cek email untuk verfication",
                                                                                Toast.LENGTH_SHORT).show();
                                                                        startActivity(new Intent(daftar.this, login.class));
                                                                        finish();
                                                                    } else {
                                                                        Toast.makeText(daftar.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                } else {
                                                    //progesbar_daftar.setVisibility(View.GONE);
                                                    Toast.makeText(daftar.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                //progesbar_daftar.setVisibility(View.GONE);
                                                Toast.makeText(daftar.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //progesbar_daftar.setVisibility(View.GONE);
                                Toast.makeText(daftar.this, e.getMessage(),
                                        Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            }
        });

    }



    public void dft_back(View view) {
        Intent intent = new Intent( daftar.this, login.class);
        startActivity(intent);
    }
}