package e.amil.e_amil;

import static android.text.TextUtils.isEmpty;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

public class data_zakatmal extends AppCompatActivity {

    RadioGroup rg;

    RadioButton rb;

    private ProgressBar progreszakatmal;

    private EditText namaamilzakatmal, jumlahzakatmal, tglzakatmal, muzakizakatmal, penyaluranzakatmal, ketzakatmal;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat simpleDateFormat;

    private ImageView image_zakatmal;

    private Button simpan_zakatmal, getfotozakatmal, kembalizakatmal;

    private String getrjeniszakatmal, getNamaamilzakatmal, getJumlahzakatmal, getTglzakatmal, getMuzakizakatmal, getPenyaluranmal, getKeteranganmal;

    public Uri url;
    public Bitmap bitmap;
    DatabaseReference dbF;
    StorageReference dbS;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_zakatmal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        namaamilzakatmal = findViewById(R.id.namaamil_zakatmal);
        jumlahzakatmal = findViewById(R.id.jumlahzakatmal);
        tglzakatmal = findViewById(R.id.tglzakatmal);
        muzakizakatmal = findViewById(R.id.muzakizakatmal);
        penyaluranzakatmal = findViewById(R.id.penyaluranzakatmal);
        ketzakatmal = findViewById(R.id.keteranganzakatmal);

        rg = findViewById(R.id.rjeniszakatmal);

        image_zakatmal = findViewById(R.id.image_zakatmal);

        simpan_zakatmal = findViewById(R.id.simpan_zakatmal);
        kembalizakatmal = findViewById(R.id.kembalizakat_mal);
        getfotozakatmal = findViewById(R.id.getfotozakatmal);

        progreszakatmal = findViewById(R.id.progresszakatmal);
        progreszakatmal.setVisibility(View.GONE);

        simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        tglzakatmal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_zakatmal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb == null){
                    getrjeniszakatmal = null;
                } else {
                    getrjeniszakatmal = rb.getText().toString();
                }
                getNamaamilzakatmal = namaamilzakatmal.getText().toString();
                getJumlahzakatmal = jumlahzakatmal.getText().toString();
                getTglzakatmal = tglzakatmal.getText().toString();
                getMuzakizakatmal = muzakizakatmal.getText().toString();
                getPenyaluranmal = penyaluranzakatmal.getText().toString();
                getKeteranganmal = ketzakatmal.getText().toString();

                checkUser();
            }
        });

        kembalizakatmal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotozakatmal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getimage();
            }
        });
    }
    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();

        datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, month, dayOfMonth);
                tglzakatmal.setText(simpleDateFormat.format(newCalendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getimage(){
        Intent imageIntentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(imageIntentGallery, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_CAMERA:
                if (resultCode == RESULT_OK){
                    image_zakatmal.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_zakatmal.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_zakatmal.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_zakatmal.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getNamaamilzakatmal) || isEmpty(getrjeniszakatmal) || isEmpty(getJumlahzakatmal) ||
                isEmpty(getTglzakatmal) || isEmpty(getMuzakizakatmal) || isEmpty(getPenyaluranmal) ||
                isEmpty(getKeteranganmal) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            image_zakatmal.setDrawingCacheEnabled(true);
            image_zakatmal.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_zakatmal.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();

            String namaFile = UUID.randomUUID() + ".jpg";
            final String pathImage = "gambar/" + namaFile;
            UploadTask uploadTask = dbS.child(pathImage).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot tasksnapshot) {
                    tasksnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dbF.child("Admin").child("Zakat Mal").push()
                                    .setValue(new data_amil_mal(getNamaamilzakatmal, getrjeniszakatmal, getJumlahzakatmal, getTglzakatmal, getMuzakizakatmal, getPenyaluranmal, getKeteranganmal, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            namaamilzakatmal.setText("");
                                            jumlahzakatmal.setText("");
                                            tglzakatmal.setText("");
                                            muzakizakatmal.setText("");
                                            penyaluranzakatmal.setText("");
                                            ketzakatmal.setText("");

                                            Toast.makeText(data_zakatmal.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progreszakatmal.setVisibility(View.GONE);
                                            startActivity(new Intent(data_zakatmal.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(data_zakatmal.this, "Upload Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progreszakatmal.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progreszakatmal.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
    }
}