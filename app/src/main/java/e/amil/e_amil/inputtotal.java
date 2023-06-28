package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class inputtotal extends AppCompatActivity {

    EditText totalfitrah, totalmal, totalinfak;

    Button simpantotal;
    ProgressBar progresstotal;

    DatabaseReference databaseReference;

    private String getTotalfitrah, getTotalmal, getTotalinfak;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputtotal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        
        databaseReference = FirebaseDatabase.getInstance().getReference();
        
        totalfitrah = findViewById(R.id.inputtotalfitrah);
        totalmal = findViewById(R.id.inputtotalmal);
        totalinfak = findViewById(R.id.inputtotalinfak);
        simpantotal = findViewById(R.id.simpan_totalsemua);
        
        simpantotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpantotalsemua();
            }
        });
    }
    
    private void simpantotalsemua(){
        getTotalfitrah = totalfitrah.getText().toString();
        getTotalmal = totalmal.getText().toString();
        getTotalinfak = totalinfak.getText().toString();

        databaseReference.child("Admin").child("Total").child("Zakat Fitrah").setValue(getTotalfitrah);
        databaseReference.child("Admin").child("Total").child("Zakat Mal").setValue(getTotalmal);
        databaseReference.child("Admin").child("Total").child("Infak").setValue(getTotalinfak);

        totalfitrah.setText("");
        totalmal.setText("");
        totalinfak.setText("");

        Toast.makeText(inputtotal.this, "Data Berhasil Disimpan", Toast.LENGTH_SHORT).show();
        progresstotal.setVisibility(View.GONE);
        startActivity(new Intent(inputtotal.this, MainActivity.class));
        finish();
    }
}