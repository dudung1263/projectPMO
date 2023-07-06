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

public class penyaluran_infaq extends AppCompatActivity {

    RadioGroup rg;

    RadioButton rb;

    private ProgressBar progresinfaq_penyaluran;

    private EditText namaamilinfak_pen, jumlahinfak_pen, tglinfak_pen, ketinfak_pen;

    DatePickerDialog datePickerDialog;

    SimpleDateFormat simpleDateFormat;

    private ImageView image_infak_pen;

    private Button simpan_infak_pen, getfotoinfak_pen, kembaliinfak_pen;

    private String getrjenisinfak_pen, getNamaamilinfak_pen, getJumlahinfak_pen, getTglinfak_pen, getKeteranganinfak_pen;

    public Uri url;
    public Bitmap bitmap;
    DatabaseReference dbF;
    StorageReference dbS;

    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_GALLERY = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penyaluran_infaq);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        namaamilinfak_pen = findViewById(R.id.namaamil_infaq_penyaluran);
        jumlahinfak_pen = findViewById(R.id.jumlahinfaq_penyaluran);
        tglinfak_pen = findViewById(R.id.tglinfaq_penyaluran);
        ketinfak_pen = findViewById(R.id.keteranganinfaq_penyaluran);

        rg = findViewById(R.id.rjenisinfaq_penyaluran);

        image_infak_pen = findViewById(R.id.image_infaq_penyaluran);

        simpan_infak_pen = findViewById(R.id.simpan_infaq_penyaluran);
        kembaliinfak_pen = findViewById(R.id.kembaliinfak_penyaluran);
        getfotoinfak_pen = findViewById(R.id.getfotoinfaq_penyaluran);

        progresinfaq_penyaluran = findViewById(R.id.progressinfaq_penyaluran);
        progresinfaq_penyaluran.setVisibility(View.GONE);

        simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        tglinfak_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        dbS = FirebaseStorage.getInstance().getReference();

        dbF = FirebaseDatabase.getInstance().getReference();

        simpan_infak_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rb == null){
                    getrjenisinfak_pen = null;
                } else {
                    getrjenisinfak_pen = rb.getText().toString();
                }
                getNamaamilinfak_pen = namaamilinfak_pen.getText().toString();
                getJumlahinfak_pen = jumlahinfak_pen.getText().toString();
                getTglinfak_pen = tglinfak_pen.getText().toString();
                getKeteranganinfak_pen = ketinfak_pen.getText().toString();

                checkUser();
            }
        });

        kembaliinfak_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        getfotoinfak_pen.setOnClickListener(new View.OnClickListener() {
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
                tglinfak_pen.setText(simpleDateFormat.format(newCalendar.getTime()));
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
                    image_infak_pen.setVisibility(View.VISIBLE);
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_infak_pen.setImageBitmap(bitmap);
                }
                break;

            case REQUEST_CODE_GALLERY:
                if (resultCode == RESULT_OK){
                    image_infak_pen.setVisibility(View.VISIBLE);
                    url = data.getData();
                    image_infak_pen.setImageURI(url);
                }
                break;
        }
    }
    private void checkUser(){
        if (isEmpty(getNamaamilinfak_pen) || isEmpty(getrjenisinfak_pen) || isEmpty(getJumlahinfak_pen) ||
                isEmpty(getTglinfak_pen) || isEmpty(getKeteranganinfak_pen) || url == null){

            Toast.makeText(this, "Data Tidak Boleh Ada Yang Kosong", Toast.LENGTH_SHORT).show();
        }
        else {
            image_infak_pen.setDrawingCacheEnabled(true);
            image_infak_pen.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) image_infak_pen.getDrawable()).getBitmap();
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
                            dbF.child("Admin").child("Peny Infak").push()
                                    .setValue(new datapenyaluran_infak(getNamaamilinfak_pen, getrjenisinfak_pen, getJumlahinfak_pen, getTglinfak_pen, getKeteranganinfak_pen, uri.toString().trim()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            namaamilinfak_pen.setText("");
                                            jumlahinfak_pen.setText("");
                                            tglinfak_pen.setText("");
                                            ketinfak_pen.setText("");

                                            Toast.makeText(penyaluran_infaq.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                                            progresinfaq_penyaluran.setVisibility(View.GONE);
                                            startActivity(new Intent(penyaluran_infaq.this, MainActivity.class));
                                            finish();
                                        }
                                    });

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(penyaluran_infaq.this, "Upload Gagal", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    progresinfaq_penyaluran.setVisibility(View.VISIBLE);
                    double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                    progresinfaq_penyaluran.setProgress((int) progress);
                }
            });

        }
    }
    public void rbclick(View view){
        int radiobuttonid = rg.getCheckedRadioButtonId();
        rb = (RadioButton) findViewById(radiobuttonid);
    }
}