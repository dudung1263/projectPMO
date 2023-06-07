package e.amil.e_amil;


import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class data_zakat extends AppCompatActivity {


    private Button bakc_zakat, simpan_data ;

    private EditText jenis_zakat, jumlah_zakat, tanggal_zakat, muzaki_zakat, keterangan_zakat;

    private String getjenis, getjumlah, gettanggal, getmuzaki, getketerangan;

    SimpleDateFormat simpleDateFormat;
    DatePickerDialog datePickerDialog;

    DatabaseReference databaseReference;

    StorageReference storageReference;

    public Uri url;
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

         storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        simpan_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getjenis = jenis_zakat.getText().toString();
                getjumlah = jumlah_zakat.getText().toString();
                getmuzaki = muzaki_zakat.getText().toString();
                getketerangan = keterangan_zakat.getText().toString();
                gettanggal = tanggal_zakat.getText().toString();

                checkUser();

            }

            private void checkUser() {
                if (isEmpty(getjenis) || isEmpty(getjumlah) || isEmpty(getmuzaki) || isEmpty(gettanggal)
                       || isEmpty(getketerangan) || url == null) {
                    Toast.makeText(data_zakat.this, "Data Tidak Boleh Kososng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        bakc_zakat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viwe) {
                onBackPressed();
            }
        });
    }


}