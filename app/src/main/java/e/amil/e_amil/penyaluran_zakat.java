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

    RadioGroup rg_pz;

    RadioButton rb_pz;

    private ProgressBar progres_pz;

    private EditText namaamilzakat_pz, jumlahzakat_pz, tglzakat_pz,ketzakat_pz;

    DatePickerDialog datePickerDialog_pz;

    SimpleDateFormat simpleDateFormat_pz;

    private ImageView image_pz;

    private Button simpan_zakat_pz, getfotozakat_pz, kembalizakat_pz;

    private String getrjeniszakat_pz, getNamaamilzakat_pz, getJumlahzakat_pz, getTglzakat_pz,getKeterangan_pz;

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
        namaamilzakat_pz = findViewById(R.id.namaamil_zakat_penyaluran);
        jumlahzakat_pz = findViewById(R.id.jumlahzakat_penyaluran);
        tglzakat_pz = findViewById(R.id.tglzakat_penyaluran);
        ketzakat_pz = findViewById(R.id.keteranganzakat_penyaluran);

        rg_pz = findViewById(R.id.rjeniszakat_penyaluran);

        image_pz = findViewById(R.id.image_zakat_penyaluran);

        simpan_zakat_pz = findViewById(R.id.simpan_zakat_penyaluran);
        kembalizakat_pz = findViewById(R.id.kembalizakat_penyaluran);
        getfotozakat_pz = findViewById(R.id.getfotozakat_penyaluran);

        progres_pz = findViewById(R.id.progresszakat_penyaluran);
        progres_pz.setVisibility(View.GONE);

        simpleDateFormat_pz = new SimpleDateFormat("dd MM yyyy");
        tglzakat_pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_zakat_pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb_pz == null){
                    getrjeniszakat_pz = null;
                }else {
                    getrjeniszakat_pz = rb_pz.getText().toString();
                }
                getNamaamilzakat_pz = namaamilzakat_pz.getText().toString();
                getJumlahzakat_pz = jumlahzakat_pz.getText().toString();
                getTglzakat_pz = tglzakat_pz.getText().toString();
                getKeterangan_pz = ketzakat_pz.getText().toString();

                checkUser();
            }
        });

        kembalizakat_pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotozakat_pz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getimage();
            }
        });
    }
    private void showDateDialog(){
        Calendar calendar = Calendar.getInstance();

        datePickerDialog_pz= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newCalendar = Calendar.getInstance();
                newCalendar.set(year, month, dayOfMonth);
                tglzakat_pz.setText(simpleDateFormat_pz.format(newCalendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog_pz.show();
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
                    image_pz.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_pz.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_pz.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_pz.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getNamaamilzakat_pz) || isEmpty(getrjeniszakat_pz) || isEmpty(getJumlahzakat_pz) ||
                isEmpty(getTglzakat_pz) || isEmpty(getKeterangan_pz) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            image_pz.setDrawingCacheEnabled(true);
            image_pz.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_pz.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] bytes = stream.toByteArray();

            String namaFile = UUID.randomUUID() + ".jpg";
            final String pathImage = "gambar_peny_zakat/" + namaFile;
            UploadTask uploadTask = dbS.child(pathImage).putBytes(bytes);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot tasksnapshot) {
                    tasksnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            dbF.child("Admin").child("Peny Zakat").push()
                                    .setValue(new data_penyaluran_zakat(getNamaamilzakat_pz, getrjeniszakat_pz, getJumlahzakat_pz, getTglzakat_pz, getKeterangan_pz, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            namaamilzakat_pz.setText("");
                                            jumlahzakat_pz.setText("");
                                            tglzakat_pz.setText("");
                                            ketzakat_pz.setText("");

                                            Toast.makeText(penyaluran_zakat.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progres_pz.setVisibility(View.GONE);
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
                    progres_pz.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progres_pz.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg_pz.getCheckedRadioButtonId();
        rb_pz = (RadioButton) findViewById(radiobuttonid);
    }
}