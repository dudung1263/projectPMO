package e.amil.e_amil;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listdata_infaq extends AppCompatActivity {

    private RecyclerView recyclerView;

    RecycleViewInfaq adapter;

    private RecyclerView.LayoutManager layoutManager;

    private DatabaseReference reference;

    private ArrayList<data_amilinfaqq> datainfaq;

    private FloatingActionButton adddatainfaq, homeinfaq;

    private EditText Search_liasdata_infaq;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listdata_infaqq);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }


        recyclerView = findViewById(R.id.datalist_infaq);

        GetData(" ");

        Search_liasdata_infaq = findViewById(R.id.Search_listinfaq);
        Search_liasdata_infaq.addTextChangedListener(new TextWatcher() {
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
        reference.child("Admin").child("Infaq")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        datainfaq = new ArrayList<data_amilinfaqq>();
                        datainfaq.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            data_amilinfaqq infaq = snapshot.getValue(data_amilinfaqq.class);

                            infaq.setKey(snapshot.getKey());
                            datainfaq.add(infaq);
                        }
                        adapter = new RecycleViewInfaq(datainfaq, listdata_infaq.this);
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

    public void backcrudinfaq(View view) {onBackPressed();
    }
}