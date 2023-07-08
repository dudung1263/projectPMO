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

public class penyaluran_zakat extends AppCompatActivity {

    RadioGroup rg;

    RadioButton rb;

    private ProgressBar progreszakat_penyaluran;

    private EditText namaamilzakat_pen, jumlahzakat_pen, tglzakat_pen, ketzakat_pen;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat simpleDateFormat;

    private ImageView image_zakat_pen;

    private Button simpan_zakat_pen, getfotozakat_pen, kembalizakat_pen;

    private String getrjeniszakat_pen, getNamaamilzakat_pen, getJumlahzakat_pen, getTglzakat_pen, getKeteranganzakat_pen;

    public Uri url;
    public Bitmap bitmap;
    DatabaseReference dbF;
    StorageReference dbS;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyaluran_zakat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        namaamilzakat_pen = findViewById(R.id.namaamil_zakat_penyaluran);
        jumlahzakat_pen = findViewById(R.id.jumlahzakat_penyaluran);
        tglzakat_pen = findViewById(R.id.tglzakat_penyaluran);
        ketzakat_pen = findViewById(R.id.keteranganzakat_penyaluran);

        rg = findViewById(R.id.rjeniszakat_penyaluran);

        image_zakat_pen = findViewById(R.id.image_zakat_penyaluran);

        simpan_zakat_pen = findViewById(R.id.simpan_zakat_penyaluran);
        kembalizakat_pen= findViewById(R.id.kembalizakat_penyaluran);
        getfotozakat_pen = findViewById(R.id.getfotozakat_penyaluran);

        progreszakat_penyaluran = findViewById(R.id.progresszakat_penyaluran);
        progreszakat_penyaluran.setVisibility(View.GONE);

        simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        tglzakat_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_zakat_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb == null){
                    getrjeniszakat_pen = null;
                } else {
                    getrjeniszakat_pen = rb.getText().toString();
                }
                getNamaamilzakat_pen = namaamilzakat_pen.getText().toString();
                getJumlahzakat_pen = jumlahzakat_pen.getText().toString();
                getTglzakat_pen = tglzakat_pen.getText().toString();
                getKeteranganzakat_pen = ketzakat_pen.getText().toString();

                checkUser();
            }
        });

        kembalizakat_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotozakat_pen.setOnClickListener(new View.OnClickListener() {
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
                tglzakat_pen.setText(simpleDateFormat.format(newCalendar.getTime()));
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
                    image_zakat_pen.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_zakat_pen.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_zakat_pen.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_zakat_pen.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getNamaamilzakat_pen) || isEmpty(getrjeniszakat_pen) || isEmpty(getJumlahzakat_pen) ||
                isEmpty(getTglzakat_pen) || isEmpty(getKeteranganzakat_pen) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            image_zakat_pen.setDrawingCacheEnabled(true);
            image_zakat_pen.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_zakat_pen.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();

            String namaFile = UUID.randomUUID() + ".jpg";
            final String pathImage = "gambar_penyfitrah/" + namaFile;
            UploadTask uploadTask = dbS.child(pathImage).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot tasksnapshot) {
                    tasksnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dbF.child("Admin").child("Peny Fitrah").push()
                                    .setValue(new datapenyaluran_mal(getNamaamilzakat_pen, getrjeniszakat_pen, getJumlahzakat_pen, getTglzakat_pen, getKeteranganzakat_pen, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            namaamilzakat_pen.setText("");
                                            jumlahzakat_pen.setText("");
                                            tglzakat_pen.setText("");
                                            ketzakat_pen.setText("");

                                            Toast.makeText(penyaluran_zakat.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progreszakat_penyaluran.setVisibility(View.GONE);
                                            startActivity(new Intent(penyaluran_zakat.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(penyaluran_zakat.this, "Upload Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progreszakat_penyaluran.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progreszakat_penyaluran.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
    }

}