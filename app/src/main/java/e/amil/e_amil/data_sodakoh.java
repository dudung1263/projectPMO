package e.amil.e_amil;

import static android.text.TextUtils.isEmpty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class data_sodakoh extends AppCompatActivity {


    private EditText jenis_sodakoh, jumlah_sodakoh, tanggal_sodakoh, muzaki_sodakoh, keterangan_sodakoh;
    private String getjenis_sodakoh, getjumlah_sodakoh, gettanggal_sodakoh, getmuzaki_sodakoh, getketerangan_sodakoh;
    private Button simpan_datasodakoh;

    SimpleDateFormat simpleDateFormat;
    DatePickerDialog datePickerDialog;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    public  Uri uri;

    @SuppressLint("MissingInflatedID")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sodakoh);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        jenis_sodakoh = findViewById(R.id.jenis_sodakoh);

        jumlah_sodakoh = findViewById(R.id.jumlah_sodakoh);

        tanggal_sodakoh =findViewById(R.id.tanggal_sodakoh);
        keterangan_sodakoh =findViewById(R.id.keterangan_sodakoh);
        tanggal_sodakoh =findViewById(R.id.tanggal_sodakoh);
        muzaki_sodakoh =findViewById(R.id.muzaki_sodakoh);

        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");

        tanggal_sodakoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {showDateDialog();}

            private void showDateDialog() {
                Calendar calendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(data_sodakoh.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(year, month, dayOfMonth);
                        tanggal_sodakoh.setText(simpleDateFormat.format(newCalendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });

        private void checkUser(){
            if(isEmpty(getjenis_sodakoh)||isEmpty(getjumlah_sodakoh)||isEmpty(getketerangan_sodakoh)||isEmpty(getmuzaki_sodakoh)||
                    TextUtils.isEmpty(gettanggal_sodakoh)|| uri==null) {

                Toast.makeText(data_sodakoh.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }
            Task<UploadTask.TaskSnapshot> uploadTask = null;
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                DatabaseReference getReference = null;
                                getReference.child("Admin").child("Mahasiswa").push()
                                        .setValue(new data_sodakoh(getjenis_sodakoh, getjumlah_sodakoh, getketerangan_sodakoh, getmuzaki_sodakoh, gettanggal_sodakoh,uri.toString().trim()))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                jenis_sodakoh.setText("");
                                                jumlah_sodakoh.setText("");
                                                tanggal_sodakoh.setText("");
                                                keterangan_sodakoh.setText("");
                                                muzaki_sodakoh.setText("");

                                                Toast.makeText(data_sodakoh.this, "Data Berhasil Tersimpan", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(data_sodakoh.this, listdata_sodakoh.class);
                                                finish();
                                            }
                                        });
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(crud.this, "Uploading Gagal", Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>(){
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot){
                        progressBar.setVisibility(View.VISIBLE);
                        double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        progressBar.setProgress((int)progress);
                    }
                });
            }
        }


    }
}