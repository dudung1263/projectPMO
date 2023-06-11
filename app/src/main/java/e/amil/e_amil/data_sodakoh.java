package e.amil.e_amil;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
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

import e.amil.e_amil.MainActivity;
import e.amil.e_amil.R;
import e.amil.e_amil.data_amil;

public class data_sodakoh extends AppCompatActivity {

    RadioGroup rg;

    RadioButton rb;

    private ProgressBar progressodakoh;

    private EditText jumlahsodakoh, tglsodakoh, muzakisodakoh, penyaluransodakoh, ketsodakoh;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat simpleDateFormat;

    private ImageView image_sodakoh;

    private Button simpan_sodakoh, getfotosodakoh, kembalisodakoh;

    private String getrjenissodakoh, getJumlahsodakoh, getTglsodakoh, getMuzakisodakoh, getPenyaluransodakoh, getKeterangansodakoh;

    public Uri url;
    public Bitmap bitmap;
    DatabaseReference dbF;
    StorageReference dbS;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sodakoh);

        jumlahsodakoh = findViewById(R.id.jumlahsodakoh);
        tglsodakoh = findViewById(R.id.tglsodakoh);
        muzakisodakoh = findViewById(R.id.muzakisodakoh);
        penyaluransodakoh = findViewById(R.id.penyaluransodakoh);
        ketsodakoh = findViewById(R.id.keterangansodakoh);

        rg = findViewById(R.id.rjenissodakoh);

        image_sodakoh = findViewById(R.id.image_sodakoh);

        simpan_sodakoh = findViewById(R.id.simpan_sodakoh);
        kembalisodakoh = findViewById(R.id.kembalisodakoh);
        getfotosodakoh = findViewById(R.id.getfotosodakoh);

        progressodakoh = findViewById(R.id.progressodakoh);
        progressodakoh.setVisibility(View.GONE);

        simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        tglsodakoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_sodakoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb == null){
                    getrjenissodakoh = null;
                }else {
                    getrjenissodakoh = rb.getText().toString();
                }
                getJumlahsodakoh = jumlahsodakoh.getText().toString();
                getTglsodakoh = tglsodakoh.getText().toString();
                getMuzakisodakoh = muzakisodakoh.getText().toString();
                getPenyaluransodakoh = penyaluransodakoh.getText().toString();
                getKeterangansodakoh = ketsodakoh.getText().toString();

                checkUser();
            }
        });

        kembalisodakoh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotosodakoh.setOnClickListener(new View.OnClickListener() {
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
                tglsodakoh.setText(simpleDateFormat.format(newCalendar.getTime()));
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
                    image_sodakoh.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_sodakoh.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_sodakoh.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_sodakoh.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getrjenissodakoh) || isEmpty(getJumlahsodakoh) ||
                isEmpty(getTglsodakoh) || isEmpty(getMuzakisodakoh) || isEmpty(getPenyaluransodakoh) ||
                isEmpty(getKeterangansodakoh) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }else {
            image_sodakoh.setDrawingCacheEnabled(true);
            image_sodakoh.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_sodakoh.getDrawable()).getBitmap();
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
                            dbF.child("Admin").child("Zakat").push()
                                    .setValue(new data_amilsodakoh(getrjenissodakoh, getJumlahsodakoh, getTglsodakoh, getMuzakisodakoh, getPenyaluransodakoh, getKeterangansodakoh, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            jumlahsodakoh.setText("");
                                            tglsodakoh.setText("");
                                            muzakisodakoh.setText("");
                                            penyaluransodakoh.setText("");
                                            ketsodakoh.setText("");

                                            Toast.makeText(data_sodakoh.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progressodakoh.setVisibility(View.GONE);
                                            startActivity(new Intent(data_sodakoh.this, MainActivity.class));
                                            finish();
                                        }
                                    });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(data_sodakoh.this, "Upload Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progressodakoh.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progressodakoh.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
    }
}