package e.amil.e_amil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class about_profil extends AppCompatActivity {

    private Button kembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_profil);

        kembali = findViewById(R.id.kembaliabout);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {onBackPressed();}
            });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}