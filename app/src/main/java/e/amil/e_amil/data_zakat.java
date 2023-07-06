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

public class data_zakat extends AppCompatActivity {

    RadioGroup rg;

    RadioButton rb;

    private ProgressBar progreszakat;

    private EditText namaamilzakat, jumlahzakat, tglzakat, muzakizakat, penyaluranzakat, ketzakat;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat simpleDateFormat;

    private ImageView image_zakat;

    private Button simpan_zakat, getfotozakat, kembalizakat;

    private String getrjeniszakat, getNamaamilzakat, getJumlahzakat, getTglzakat, getMuzakizakat, getPenyaluran, getKeterangan;

    public Uri url;
    public Bitmap bitmap;
    DatabaseReference dbF;
    StorageReference dbS;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;
//    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_zakat);



        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        namaamilzakat = findViewById(R.id.namaamil_zakat);
        jumlahzakat = findViewById(R.id.jumlahzakat);
        tglzakat = findViewById(R.id.tglzakat);
        muzakizakat = findViewById(R.id.muzakizakat);
        penyaluranzakat = findViewById(R.id.penyaluranzakat);
        ketzakat = findViewById(R.id.keteranganzakat);

        rg = findViewById(R.id.rjeniszakat);

        image_zakat = findViewById(R.id.image_zakat);

        simpan_zakat = findViewById(R.id.simpan_zakat);
        kembalizakat = findViewById(R.id.kembalizakat);
        getfotozakat = findViewById(R.id.getfotozakat);

        progreszakat = findViewById(R.id.progresszakat);
        progreszakat.setVisibility(View.GONE);

        simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        tglzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_zakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (rb == null){
                    getrjeniszakat = null;
                } else {
                    getrjeniszakat = rb.getText().toString();
                }
                getNamaamilzakat = namaamilzakat.getText().toString();
                getJumlahzakat = jumlahzakat.getText().toString();
                getTglzakat = tglzakat.getText().toString();
                getMuzakizakat = muzakizakat.getText().toString();
                getPenyaluran = penyaluranzakat.getText().toString();
                getKeterangan = ketzakat.getText().toString();

                checkUser();
            }
        });

        kembalizakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotozakat.setOnClickListener(new View.OnClickListener() {
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
                tglzakat.setText(simpleDateFormat.format(newCalendar.getTime()));
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
                    image_zakat.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_zakat.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_zakat.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_zakat.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getNamaamilzakat) || isEmpty(getrjeniszakat) || isEmpty(getJumlahzakat) ||
                isEmpty(getTglzakat) || isEmpty(getMuzakizakat) || isEmpty(getPenyaluran) ||
                isEmpty(getKeterangan) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            image_zakat.setDrawingCacheEnabled(true);
            image_zakat.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_zakat.getDrawable()).getBitmap();
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
                            dbF.child("Admin").child("Zakat Fitrah").push()
                                    .setValue(new data_amil(getNamaamilzakat, getrjeniszakat, getJumlahzakat, getTglzakat, getMuzakizakat, getPenyaluran, getKeterangan, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            namaamilzakat.setText("");
                                            jumlahzakat.setText("");
                                            tglzakat.setText("");
                                            muzakizakat.setText("");
                                            penyaluranzakat.setText("");
                                            ketzakat.setText("");

                                            Toast.makeText(data_zakat.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progreszakat.setVisibility(View.GONE);
                                            startActivity(new Intent(data_zakat.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(data_zakat.this, "Upload Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progreszakat.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progreszakat.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
    }
}