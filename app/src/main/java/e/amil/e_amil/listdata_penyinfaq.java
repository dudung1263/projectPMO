package e.amil.e_amil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listdata_penyinfaq extends AppCompatActivity {

    private RecyclerView recyclerView;

    RecycleViewPenyInfaq adapter;

    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;

    private ArrayList<datapenyaluran_infak> dataPenyinfaq;

    private FloatingActionButton adddataPenyinfaq, homePenyinfaq;

    private EditText Search_liasdata_Penyinfaq;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata_penyinfaq);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        recyclerView = findViewById(R.id.datalist_Penyinfaq);

        GetData(" ");

        Search_liasdata_Penyinfaq = findViewById(R.id.Search_listPenyinfaq);
        Search_liasdata_Penyinfaq.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().isEmpty()) {
                    GetData(s.toString());
                } else {
                    adapter.getFilter().filter(s);
                }

            }
        });

        MyRecycleView();


    }

    private void GetData(String data) {

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Admin").child("Peny Infak")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataPenyinfaq = new ArrayList<datapenyaluran_infak>();
                        dataPenyinfaq.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            datapenyaluran_infak Penyinfaq = snapshot.getValue(datapenyaluran_infak.class);

                            Penyinfaq.setKey(snapshot.getKey());
                            dataPenyinfaq.add(Penyinfaq);
                        }
                        adapter = new RecycleViewPenyInfaq(dataPenyinfaq, listdata_penyinfaq.this);
                        adapter.notifyDataSetChanged();

                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                        Toast.makeText(getApplicationContext(), "Data Gagal Menampilkan", Toast.LENGTH_SHORT).show();
                        Log.e("MyListDataInfaq", error.getDetails() + " " + error.getMessage());
                    }
                });
    }

    private void MyRecycleView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        DividerItemDecoration ItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        ItemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.shap1));
        recyclerView.addItemDecoration(ItemDecoration);

    }
}