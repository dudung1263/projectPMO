package e.amil.e_amil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class pilih_penyaluranzakat extends AppCompatActivity {

    Button kembali_pilpenzakat, fitrah_pilpenzakat, mal_pilpenzakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilih_penyaluranzakat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        kembali_pilpenzakat = findViewById(R.id.backpilih_penzakat);
        fitrah_pilpenzakat = findViewById(R.id.datapenzakatfitrah);
        mal_pilpenzakat = findViewById(R.id.datapenzakatmal);

        kembali_pilpenzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fitrah_pilpenzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pilih_penyaluranzakat.this, penyaluran_zakat.class));
            }
        });

        mal_pilpenzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pilih_penyaluranzakat.this, penyaluran_zakatmal.class));
            }
        });
    }
}