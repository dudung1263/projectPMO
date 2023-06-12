package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class profil_profil extends AppCompatActivity {

    private Button backprofil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_profil);

        backprofil = findViewById(R.id.kembaliprofil);

        backprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profil_profil.this, MainActivity.class));
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}