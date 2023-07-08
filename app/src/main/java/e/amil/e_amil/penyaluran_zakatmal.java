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

public class penyaluran_zakatmal extends AppCompatActivity {

    RadioGroup rg;

    RadioButton rb;

    private ProgressBar progreszakatmal_penyaluran;

    private EditText namaamilzakatmal_pen, jumlahzakatmal_pen, tglzakatmal_pen, ketzakatmal_pen;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat simpleDateFormat;

    private ImageView image_zakatmal_pen;

    private Button simpan_zakatmal_pen, getfotozakatmal_pen, kembalizakatmal_pen;

    private String getrjeniszakatmal_pen, getNamaamilzakatmal_pen, getJumlahzakatmal_pen, getTglzakatmal_pen, getKeteranganzakatmal_pen;

    public Uri url;
    public Bitmap bitmap;
    DatabaseReference dbF;
    StorageReference dbS;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyaluran_zakatmal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        namaamilzakatmal_pen = findViewById(R.id.namaamil_zakatmal_penyaluran);
        jumlahzakatmal_pen = findViewById(R.id.jumlahzakatmal_penyaluran);
        tglzakatmal_pen = findViewById(R.id.tglzakatmal_penyaluran);
        ketzakatmal_pen = findViewById(R.id.keteranganzakatmal_penyaluran);

        rg = findViewById(R.id.rjeniszakatmal_penyaluran);

        image_zakatmal_pen = findViewById(R.id.image_zakatmal_penyaluran);

        simpan_zakatmal_pen = findViewById(R.id.simpan_zakatmal_penyaluran);
        kembalizakatmal_pen= findViewById(R.id.kembalizakatmal_penyaluran);
        getfotozakatmal_pen = findViewById(R.id.getfotozakatmal_penyaluran);

        progreszakatmal_penyaluran = findViewById(R.id.progresszakatmal_penyaluran);
        progreszakatmal_penyaluran.setVisibility(View.GONE);

        simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        tglzakatmal_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_zakatmal_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb == null){
                    getrjeniszakatmal_pen = null;
                } else {
                    getrjeniszakatmal_pen = rb.getText().toString();
                }
                getNamaamilzakatmal_pen = namaamilzakatmal_pen.getText().toString();
                getJumlahzakatmal_pen = jumlahzakatmal_pen.getText().toString();
                getTglzakatmal_pen = tglzakatmal_pen.getText().toString();
                getKeteranganzakatmal_pen = ketzakatmal_pen.getText().toString();

                checkUser();
            }
        });

        kembalizakatmal_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotozakatmal_pen.setOnClickListener(new View.OnClickListener() {
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
                tglzakatmal_pen.setText(simpleDateFormat.format(newCalendar.getTime()));
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
                    image_zakatmal_pen.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_zakatmal_pen.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_zakatmal_pen.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_zakatmal_pen.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getNamaamilzakatmal_pen) || isEmpty(getrjeniszakatmal_pen) || isEmpty(getJumlahzakatmal_pen) ||
                isEmpty(getTglzakatmal_pen) || isEmpty(getKeteranganzakatmal_pen) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            image_zakatmal_pen.setDrawingCacheEnabled(true);
            image_zakatmal_pen.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_zakatmal_pen.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();

            String namaFile = UUID.randomUUID() + ".jpg";
            final String pathImage = "gambar_penyinfak/" + namaFile;
            UploadTask uploadTask = dbS.child(pathImage).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot tasksnapshot) {
                    tasksnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dbF.child("Admin").child("Peny Mal").push()
                                    .setValue(new datapenyaluran_mal(getNamaamilzakatmal_pen, getrjeniszakatmal_pen, getJumlahzakatmal_pen, getTglzakatmal_pen, getKeteranganzakatmal_pen, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            namaamilzakatmal_pen.setText("");
                                            jumlahzakatmal_pen.setText("");
                                            tglzakatmal_pen.setText("");
                                            ketzakatmal_pen.setText("");

                                            Toast.makeText(penyaluran_zakatmal.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progreszakatmal_penyaluran.setVisibility(View.GONE);
                                            startActivity(new Intent(penyaluran_zakatmal.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(penyaluran_zakatmal.this, "Upload Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progreszakatmal_penyaluran.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progreszakatmal_penyaluran.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
    }
}