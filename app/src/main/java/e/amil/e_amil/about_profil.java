package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class about_profil extends AppCompatActivity {

    private Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_profil);

        kembali = findViewById(R.id.kembaliabout);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(about_profil.this, MainActivity.class));
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}