package e.amil.e_amil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class jadwalsholat extends AppCompatActivity {

    private ListView listView;

    private String[] prayerTimes = {
            "Subuh - 04:30",
            "Dzuhur - 12:30",
            "Ashar - 15:30",
            "Maghrib - 18:30",
            "Isya - 20:30"
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwalsholat);

        listView = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, prayerTimes);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPrayerTime = prayerTimes[position];
                Toast.makeText(jadwalsholat.this, "Waktu Sholat: " + selectedPrayerTime, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
