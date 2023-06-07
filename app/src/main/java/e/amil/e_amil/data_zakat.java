package e.amil.e_amil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class data_zakat extends AppCompatActivity {


    private Button bakc_zakat ;

    @Override
    protected void onCreate( final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_zakat);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bakc_zakat = findViewById(R.id.bakc_zakat);


        bakc_zakat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View viwe) {
                onBackPressed();
            }
        });
    }
}