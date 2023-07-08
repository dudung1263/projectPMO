package e.amil.e_amil;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class coba_user extends AppCompatActivity {

    ImageView btnBack, ivProfil;
    TextView btnSimpan;
    EditText etNama, etUsername, etEmail, etPhone;
    TextInputEditText etAlamat;
    CardView btnChooseFoto, progresLay;
    String getNama, getUsername, getEmail, getPhone, getAlamat, getFoto;
    String ID;
    FirebaseAuth userAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;
    Uri imageUri;
    Boolean gantiFoto = false;

    FirebaseAuth mAuth;
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba_user);

        // Inisialisasi
        progresLay = findViewById(R.id.progres_layout);
        btnBack = findViewById(R.id.btn_back);
        ivProfil = findViewById(R.id.iv_profil);
        btnSimpan = findViewById(R.id.btn_simpan);
        etNama = findViewById(R.id.et_nama_profil);
        etUsername = findViewById(R.id.et_username_profil);
        etPhone = findViewById(R.id.et_phone_profil);
        etEmail = findViewById(R.id.et_email_profil);
        etAlamat = findViewById(R.id.et_alamat_profil);
        btnChooseFoto = findViewById(R.id.btn_choose_foto);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        ID = mUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Pengguna").child("userAuth");

        GetSetDataUser(ID);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progresLay.setVisibility(View.VISIBLE);
                updateUser();
            }
        });

    }
    private void GetSetDataUser(String ID){
        databaseReference.child(ID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().exists()){
                        //Get Data
                        DataSnapshot dataSnapshot = task.getResult();
                        getNama = String.valueOf(dataSnapshot.child("dataNama").getValue());
                        getUsername = String.valueOf(dataSnapshot.child("dataUsername").getValue());
                        getEmail = String.valueOf(dataSnapshot.child("dataEmail").getValue());
                        getPhone = String.valueOf(dataSnapshot.child("dataPhone").getValue());
                        getAlamat = String.valueOf(dataSnapshot.child("dataAlamat").getValue());
                        getFoto = String.valueOf(dataSnapshot.child("dataFoto").getValue());
                        //Set data
                        etNama.setText(getNama);
                        etUsername.setText(getUsername);
                        etEmail.setText(getEmail);
                        etPhone.setText(getPhone);
                        etAlamat.setText(getAlamat);
                        if (getFoto.isBlank()){
                            // ekspresi jika foto masih kosong
                            ivProfil.setImageResource(R.drawable.ikonamil);
                        }else {
                            Glide.with(coba_user.this).load(getFoto.trim()).into(ivProfil);

                        }
                    }else {
                        // Lakukan jika data tidak ketemu
                        Toast.makeText(coba_user.this, "Load data gagal", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }

    private void updateUser(){
        String nama = etNama.getText().toString();
        String alamat = etAlamat.getText().toString();
        String username = etUsername.getText().toString();
        String phone = etPhone.getText().toString();
        String email = etEmail.getText().toString();

        DataUser user = new DataUser(nama, username, email, phone, alamat, "");
        databaseReference.child(ID).setValue(user).addOnCompleteListener(task -> {
            new AlertDialog.Builder(coba_user.this)
                    .setMessage("Data Berhasil Dismpan")
                    .setPositiveButton("Ok", (dialog, which) -> onBackPressed())
                    .show();
        });
    }

}