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

public class data_infaqq extends AppCompatActivity {

    RadioGroup rg;

    RadioButton rb;

    private ProgressBar progresinfak;

    private EditText jumlahinfak, tglinfak, muzakiinfak, penyaluraninfak, ketinfak;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat simpleDateFormat;

    private ImageView image_infak;

    private Button simpan_infak, getfotoinfak, kembaliinfak;

    private String getrjenisinfak, getJumlahinfak, getTglinfak, getMuzakiinfak, getPenyaluraninfak, getketeranganinfak;

    public Uri url;
    public Bitmap bitmap;
    DatabaseReference dbF;
    StorageReference dbS;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    public static void clear() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_infaqq);

        jumlahinfak = findViewById(R.id.jumlahinfak);
        tglinfak = findViewById(R.id.tglinfak);
        muzakiinfak = findViewById(R.id.muzakiinfak);
        penyaluraninfak = findViewById(R.id.penyaluraninfak);
        ketinfak = findViewById(R.id.keteranganinfak);

        rg = findViewById(R.id.rjenisinfak);

        image_infak = findViewById(R.id.image_infak);

        simpan_infak = findViewById(R.id.simpan_infak);
        kembaliinfak = findViewById(R.id.kembaliinfak);
        getfotoinfak = findViewById(R.id.getfotoinfak);

        progresinfak = findViewById(R.id.progressinfak);
        progresinfak.setVisibility(View.GONE);

        simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        tglinfak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_infak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb == null){
                    getrjenisinfak = null;
                }else {
                    getrjenisinfak = rb.getText().toString();
                }
                getJumlahinfak = jumlahinfak.getText().toString();
                getTglinfak = tglinfak.getText().toString();
                getMuzakiinfak = muzakiinfak.getText().toString();
                getPenyaluraninfak = penyaluraninfak.getText().toString();
                getketeranganinfak = ketinfak.getText().toString();

                checkUser();
            }
        });

        kembaliinfak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotoinfak.setOnClickListener(new View.OnClickListener() {
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
                tglinfak.setText(simpleDateFormat.format(newCalendar.getTime()));
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
                    image_infak.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_infak.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_infak.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_infak.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getrjenisinfak) || isEmpty(getJumlahinfak) ||
                isEmpty(getTglinfak) || isEmpty(getMuzakiinfak) || isEmpty(getPenyaluraninfak) ||
                isEmpty(getketeranganinfak) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }else {
            image_infak.setDrawingCacheEnabled(true);
            image_infak.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_infak.getDrawable()).getBitmap();
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
                                    .setValue(new data_amilinfaqq(getrjenisinfak, getJumlahinfak, getTglinfak, getMuzakiinfak, getPenyaluraninfak, getketeranganinfak, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            jumlahinfak.setText("");
                                            tglinfak.setText("");
                                            muzakiinfak.setText("");
                                            penyaluraninfak.setText("");
                                            ketinfak.setText("");

                                            Toast.makeText(data_infaqq.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progresinfak.setVisibility(View.GONE);
                                            startActivity(new Intent(data_infaqq.this, MainActivity.class));
                                            finish();
                                        }
                                    });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(data_infaqq.this, "Upload Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progresinfak.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progresinfak.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
    }
}