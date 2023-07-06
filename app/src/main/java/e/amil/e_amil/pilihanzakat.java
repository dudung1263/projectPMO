package e.amil.e_amil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class pilihanzakat extends AppCompatActivity {

    Button kembali_pilzakat, fitrah_pilzakat, mal_pilzakat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihanzakat);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        kembali_pilzakat = findViewById(R.id.backpilih_zakat);
        fitrah_pilzakat = findViewById(R.id.datazakatfitrah);
        mal_pilzakat = findViewById(R.id.datazakatmal);

        kembali_pilzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fitrah_pilzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pilihanzakat.this, data_zakat.class));
            }
        });

        mal_pilzakat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(pilihanzakat.this, data_zakatmal.class));
            }
        });
    }

}