package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class help_profil extends AppCompatActivity {

    private Button panduan, backhelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_profil);

        panduan = findViewById(R.id.bukapanduan);
        backhelp = findViewById(R.id.kembalihelp);

        panduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(help_profil.this, PDF_panduan.class));
            }
        });

        backhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(help_profil.this, MainActivity.class));
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}