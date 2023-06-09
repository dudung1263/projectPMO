package e.amil.e_amil;


import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class data_zakat extends AppCompatActivity {


    private Button bakc_zakat, simpan_data ;

    private EditText jenis_zakat, jumlah_zakat, tanggal_zakat, muzaki_zakat, keterangan_zakat;

    private String getjenis, getjumlah, gettanggal, getmuzaki, getketerangan;

    SimpleDateFormat simpleDateFormat;
    DatePickerDialog datePickerDialog;

    DatabaseReference database = FirebaseDatabase.getInstance().getReference();




    public Uri uri;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate( final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_zakat);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        //deklarasi button
        bakc_zakat = findViewById(R.id.bakc_zakat);

        simpan_data = findViewById(R.id.simpanzakat);

        jenis_zakat =findViewById(R.id.jenis_zakat);
        jumlah_zakat =findViewById(R.id.jumlah_zakat);
        tanggal_zakat =findViewById(R.id.tanggal_zakat);
        muzaki_zakat =findViewById(R.id.muzaki_zakat);
        keterangan_zakat =findViewById(R.id.keterangan_zakat);

        simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
        tanggal_zakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }

            private void showDateDialog() {
                Calendar calendar = Calendar.getInstance();

                datePickerDialog = new DatePickerDialog(data_zakat.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar newCalendar = Calendar.getInstance();
                        newCalendar.set(year, month, dayOfMonth);
                        tanggal_zakat.setText(simpleDateFormat.format(newCalendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        simpan_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getjenis = jenis_zakat.getText().toString();
                getjumlah = jumlah_zakat.getText().toString();
                getmuzaki = muzaki_zakat.getText().toString();
                getketerangan = keterangan_zakat.getText().toString();
                gettanggal = tanggal_zakat.getText().toString();

                if(getjenis.isEmpty()){
                    jenis_zakat.setError("Isikan jenis zakat");
                }else if (getjumlah.isEmpty()) {
                    jumlah_zakat.setError("Jumlah zakat");
                } else if (getmuzaki.isEmpty()) {
                    muzaki_zakat.setError("Masukan muzaki");
                } else if (gettanggal.isEmpty()) {
                    tanggal_zakat.setError("Tanggal Kosong");
                } else if(getketerangan.isEmpty()) {
                    keterangan_zakat.setError("jika perlu diisi");
                } else {
                    database.child("Admin").child("user").push().
                            setValue(new data_amil (getketerangan,getmuzaki,gettanggal,getjumlah,getjenis))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(data_zakat.this,"Data berhasil di simpan",Toast
                                            .LENGTH_SHORT).show();
                                    startActivity(new Intent(data_zakat.this,MainActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(data_zakat.this,"Data gagal di simpan",Toast
                                            .LENGTH_SHORT).show();
                                }
                            });
                }

            }

            });
                bakc_zakat.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View viwe) {
                            onBackPressed();
                        }
                });

    }}