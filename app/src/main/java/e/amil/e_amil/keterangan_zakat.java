package e.amil.e_amil;

import static java.lang.Integer.parseInt;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class keterangan_zakat extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference jmlzakatfitrahhRef, jmlPenyzakatfitrahRef;
    int totalzakfit, totalzaktfitKeluar;
    TextView jmlzakfit, jmlzakfitKeluar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_zakat);

        totalzakfit= 0;
        totalzaktfitKeluar = 0;

        jmlzakatfitrahhRef = database.getReference("Admin").child("Zakat Fitrah");
        jmlPenyzakatfitrahRef = database.getReference("Admin").child("Peny Fitrah");
        jmlzakfit = findViewById(R.id.jumlahzakatfitrah);
        jmlzakfitKeluar = findViewById(R.id.julmahpengeluaranzakatfitrah);


        jmlPenyzakatfitrahRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {
                    String zakafitrah = String.valueOf(produkSnapshot.child("jumlahzakat_pen").getValue());
                    int jumlahzakfitpen = Integer.parseInt(zakafitrah);

                    totalzaktfitKeluar +=jumlahzakfitpen;
                }

                // Gunakan totalHarga sesuai kebutuhan Anda
                jmlzakfitKeluar.setText(String.valueOf(totalzaktfitKeluar)+".Kg");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle jika terjadi error
            }
        });
        jmlzakatfitrahhRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {
                    String zakatfitrah = String.valueOf(produkSnapshot.child("jumlahzakat").getValue());
                    int jumlahinfak = parseInt(zakatfitrah);

                    totalzakfit +=jumlahinfak;
                }

                // Gunakan totalHarga sesuai kebutuhan Anda
                int jmlKel = totalzakfit-totalzaktfitKeluar;
                jmlzakfit.setText(String.valueOf(jmlKel)+".Kg");
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