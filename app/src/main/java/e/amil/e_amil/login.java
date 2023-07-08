package e.amil.e_amil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    private EditText etusername, etpassword;
    private Button btnlogin;
    public static FirebaseAuth auth;
    public static FirebaseAuth.AuthStateListener userAuthlistener, adminAuthlistener;
    //ProgressBar progresslgn;
    FirebaseUser firebaseUser;
    String admintoken, usertoken;
    private String getUsername, getEmail, getPassword;
    String Username, Password;
    SharedPreferences sharedPreferences;
    //private ProgressBar progesbar_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

            etusername = findViewById(R.id.username);
            etpassword = findViewById(R.id.password);
            btnlogin = findViewById(R.id.btnlogin);
            //progresslgn = findViewById(R.id.progresslogin);

            auth = FirebaseAuth.getInstance();
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

            sharedPreferences = getSharedPreferences("userAuth", MODE_PRIVATE);

            userAuthlistener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if ( !user.isEmailVerified()) {
                        new android.app.AlertDialog.Builder(login.this)
                                .setTitle("Email belum terverifikasi")
                                .setMessage("Periksa email anda untuk \nverifikasi !")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                        //progresslgn.setVisibility(View.GONE);
                                    }
                                }).show();
                    }else{
                        //progresslgn.setVisibility(View.GONE);
                        Toast.makeText(login.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(login.this, MainActivity_user.class);
                        intent.putExtra("username",getUsername);
                        intent.putExtra("email",getEmail);
                        startActivity(intent);
                        finish();

                    }
                }
            };
       adminAuthlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (!user.isEmailVerified()){
                    new android.app.AlertDialog.Builder(login.this)
                            .setTitle("Email belum terverifikasi")
                            .setMessage("Periksa email anda untuk \nverifikasi !")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    //progresslgn.setVisibility(View.GONE);
                                }
                            }).show();
                }else{
                    //progresslgn.setVisibility(View.GONE);
                    Toast.makeText(login.this, "Login Sebagai Admin", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(login.this, MainActivity.class));
                    finish();
                }
            }
        };
            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //progesbar_login.setVisibility(View.VISIBLE);
                    Username = etusername.getText().toString().toLowerCase();
                    Password = etpassword.getText().toString();

                    //progresslgn.setVisibility(View.VISIBLE);
                    if (TextUtils.isEmpty(Username)){
                        etusername.setError("Tidak boleh kosong");
                        etusername.requestFocus();
                        //progresslgn.setVisibility(View.GONE);
                    } else if (TextUtils.isEmpty(Password)) {
                        etpassword.setError("Tidak boleh kosong");
                        etpassword.requestFocus();
                        //progresslgn.setVisibility(View.GONE);
                    }
                    else {
                        getAdmin(Username);
                    }
                }
            });
        }

    private void getUsers(String username){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Pengguna")
                .child("userAuth");
        databaseReference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        getUsername = String.valueOf(dataSnapshot.child("username").getValue());
                        getEmail = String.valueOf(dataSnapshot.child("email").getValue());
                        UserLogin();
                    }else {
                        etusername.setError("Username tidak ada");
                        etusername.requestFocus();
                        //progresslgn.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
    private void UserLogin() {
        auth.signInWithEmailAndPassword(getEmail, etpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent = new Intent(login.this, MainActivity_user.class);
                    intent.putExtra("username",getUsername);
                    intent.putExtra("email",getEmail);
                    // Mendapatkan token login
                    auth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                usertoken = task.getResult().getToken();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("userToken", usertoken);
                                editor.apply();
                            } else {
                                Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    auth.addAuthStateListener(userAuthlistener);
                }else {
                    new android.app.AlertDialog.Builder(login.this)
                            .setTitle("Login Gagal")
                            .setMessage("Periksa password anda!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    //progresslgn.setVisibility(View.GONE);
                                    etpassword.requestFocus();
                                }
                            }).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                //progresslgn.setVisibility(View.GONE);
            }
        });
    }
    private void AdminLogin() {
        auth.signInWithEmailAndPassword(getEmail, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Mendapatkan token login
                    auth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                        @Override
                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                            if (task.isSuccessful()) {
                                admintoken = task.getResult().getToken();
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("adminToken", admintoken);
                                editor.apply();
                            } else {
                                Toast.makeText(login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    auth.addAuthStateListener(adminAuthlistener);
                }else {
                    new AlertDialog.Builder(login.this)
                            .setTitle("Login Gagal")
                            .setMessage("Periksa password anda!")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                    //progresslgn.setVisibility(View.GONE);
                                    etpassword.requestFocus();
                                }
                            }).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                //progresslgn.setVisibility(View.GONE);
            }
        });
    }
    private void getAdmin(String username){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference.child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                        getEmail = String.valueOf(dataSnapshot.child("dataEmail").getValue());
                        AdminLogin();
                    }else {
                        getUsers(Username);
                    }
                }
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            if (sharedPreferences.getString("adminToken", null) != null){
                auth.addAuthStateListener(adminAuthlistener);
            } else if (sharedPreferences.getString("userToken", null) != null) {
                auth.addAuthStateListener(userAuthlistener);
            }
        }else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (sharedPreferences.getString("adminToken", null) != null){
            auth.removeAuthStateListener(adminAuthlistener);
        } else if (sharedPreferences.getString("userToken", null) != null) {
            auth.removeAuthStateListener(userAuthlistener);
        }
    }
    public void onBackPressed(){

        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ikonamil)
                .setTitle(R.string.app_name)
                .setMessage("Apakah Anda Yakin Keluar?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();

    }



    public void daftarakun(View view) {
        Intent intent = new Intent( login.this, daftar.class);
        startActivity(intent);
    }

}