package e.amil.e_amil;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class keterangan_infaq extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference jmlInfaqRef, jmlPenyRef;
    int totalInfaq, totalInfaqKeluar;
    TextView jmlInfaq, jmlInfaqKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_infaq);
        totalInfaq = 0;
        totalInfaqKeluar = 0;

        jmlInfaqRef = database.getReference("Admin").child("Infaq");
        jmlPenyRef = database.getReference("Admin").child("Peny Infak");
        jmlInfaq = findViewById(R.id.julmahinfaq_masuk);
        jmlInfaqKeluar = findViewById(R.id.julmahinfaq_keluar);

        jmlPenyRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {
                    String infaq = String.valueOf(produkSnapshot.child("jumlahinfak_pen").getValue());
                    int jumlahinfakpen = Integer.parseInt(infaq);

                    totalInfaqKeluar +=jumlahinfakpen;
                }

                // Gunakan totalHarga sesuai kebutuhan Anda
                jmlInfaqKeluar.setText("Rp."+String.valueOf(totalInfaqKeluar));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle jika terjadi error
            }
        });
        jmlInfaqRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {
                    String infaq = String.valueOf(produkSnapshot.child("jumlahinfak").getValue());
                    int jumlahinfak = Integer.parseInt(infaq);

                    totalInfaq +=jumlahinfak;
                }

                // Gunakan totalHarga sesuai kebutuhan Anda
                int jmlKel = totalInfaq-totalInfaqKeluar;
                jmlInfaq.setText("Rp."+String.valueOf(jmlKel));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle jika terjadi error
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}