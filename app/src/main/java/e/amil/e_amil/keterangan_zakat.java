package e.amil.e_amil;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class keterangan_zakat extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference jmlzakatfitrahhRef, jmlPenyzakatfitrahRef, jmlzakatmalRef, jmlPenzakatmalRef;
    int totalzakfit, totalzaktfitKeluar, totalzakmal, totalzakatmalKeluar;
    TextView jmlzakfit, jmlzakfitKeluar, jmlzakatmal, jmlzakatmalKeluar;
    private RecyclerView recyclerView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keterangan_zakat);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        totalzakfit= 0;
        totalzaktfitKeluar = 0;

        //zakat Mal
        totalzakmal= 0;
        totalzakatmalKeluar = 0;


        jmlzakatfitrahhRef = database.getReference("Admin").child("Zakat Fitrah");
        jmlPenyzakatfitrahRef = database.getReference("Admin").child("Peny Fitrah");
        jmlzakfit = findViewById(R.id.jumlahzakatfitrah);
        jmlzakfitKeluar = findViewById(R.id.julmahpengeluaranzakatfitrah);

        //zaktMall
        jmlzakatmalRef = database.getReference("Admin").child("Zakat Mal");
        jmlPenzakatmalRef = database.getReference("Admin").child("Peny Mal");
        jmlzakatmal = findViewById(R.id.jumlahzakatmall);
        jmlzakatmalKeluar = findViewById(R.id.julmahpengeluaranzakatmall);

//ZakatFitrah Keluar
        jmlPenyzakatfitrahRef.addListenerForSingleValueEvent(new ValueEventListener()  {
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
        //Zakatfitrah masuk
        jmlzakatfitrahhRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {
                    String zakatfitrah = String.valueOf(produkSnapshot.child("jumlahzakat").getValue());
                    int jumlahzakat = parseInt(zakatfitrah);

                    totalzakfit +=jumlahzakat;
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


        // Zakat Mal
        jmlPenzakatmalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {
                    String zakamal = String.valueOf(produkSnapshot.child("jumlahzakatmal_pen").getValue());
                    int jumlahzakatmalpen = Integer.parseInt(zakamal);

                    totalzakatmalKeluar +=jumlahzakatmalpen;
                }

                // Gunakan totalHarga sesuai kebutuhan Anda
                jmlzakatmalKeluar.setText("Rp. "+String.valueOf(totalzakatmalKeluar));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle jika terjadi error
            }
        });
        //masukzakat mal
        jmlzakatmalRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot produkSnapshot : dataSnapshot.getChildren()) {
                    String zakatmal = String.valueOf(produkSnapshot.child("jumlahzakatmal").getValue());
                    int jumlahzakatmal = parseInt(zakatmal);

                    totalzakmal +=jumlahzakatmal;
                }

                // Gunakan totalHarga sesuai kebutuhan Anda
                int jmlKeluarmal = totalzakmal-totalzakatmalKeluar;
                jmlzakatmal.setText("Rp. "+String.valueOf(jmlKeluarmal));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle jika terjadi error
            }
        });

    }


    public void datazakat(View view) {
        Intent intent = new Intent( keterangan_zakat.this, listdatapenyzakat_mal.class);
        startActivity(intent);
    }
}