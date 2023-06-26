package e.amil.e_amil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class profil_profil extends AppCompatActivity {

    private Button backprofil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_profil);

        backprofil = findViewById(R.id.kembaliprofil);
        backprofil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}