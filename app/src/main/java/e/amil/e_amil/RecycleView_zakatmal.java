package e.amil.e_amil;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecycleView_zakatmal extends RecyclerView.Adapter<RecycleView_zakatmal.ViewHolder> implements Filterable {

    ArrayList<data_amil_mal> listZakatmal;
    listdata_zakatmal context;
    ArrayList<data_amil_mal> listZakatmalSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Filter Data berdasarkan nama
    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<data_amil_mal> filterZakat = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterZakat.addAll(listZakatmalSearch);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (data_amil_mal item : listZakatmalSearch){
                    if (item.getRjeniszakatmal().toLowerCase().contains(filterPattern)) {
                        filterZakat.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterZakat;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            listZakatmal.clear();
            listZakatmal.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//    private ViewHolder holder;
//    private int position;

    //Membuat konstruktor untuk menyimpan data
    public RecycleView_zakatmal(ArrayList<data_amil_mal> listZakatmal, listdata_zakatmal context){
        this.listZakatmal = listZakatmal;
        this.context = context;
        this.listZakatmalSearch = listZakatmal;
    }
    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public RecycleView_zakatmal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdesign_zakatmal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleView_zakatmal.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        this.holder = holder;
//        this.position = position;

        final String NamaamilZakatmal = listZakatmal.get(position).getNamaamilzakatmal();
        final String JenisZakatmal = listZakatmal.get(position).getRjeniszakatmal();
        final String JumlahZakatmal = listZakatmal.get(position).getJumlahzakatmal();
        final String TglZakatmal = listZakatmal.get(position).getTglzakatmal();
        final String MuzakiZakatmal = listZakatmal.get(position).getMuzakizakatmal();
        final String PenyaluranZakatmal = listZakatmal.get(position).getPenyaluranzakatmal();
        final String KeteranganZakatmal = listZakatmal.get(position).getKetzakatmal();
        final String Gambar = listZakatmal.get(position).getGambar();
        final data_amil_mal dataAmilmal = listZakatmal.get(position);

        holder.NamaamilZakatmal.setText(": "+NamaamilZakatmal);
        holder.JenisZakatmal.setText(": "+JenisZakatmal);
        holder.JumlahZakatmal.setText(": "+JumlahZakatmal);
        holder.TglZakatmal.setText(": "+TglZakatmal);
        holder.MuzakiZakatmal.setText(": "+MuzakiZakatmal);
        holder.PenyaluranZakatmal.setText(": "+PenyaluranZakatmal);
        holder.KetZakatmal.setText(": "+KeteranganZakatmal);

        if (isEmpty(Gambar)){
            holder.Gambarzakatmal.setImageResource(R.drawable.carigambar);
        }else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambar.trim())
                    .into(holder.Gambarzakatmal);
        }

        holder.ListItemZakatmal.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                final String[] action = {"Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Apa yang akan anda pilih?");
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){

                            case 0:
                                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                                alert.setTitle("Apakah anda yakin akan menghapus Data ini?");
                                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Menjalankan Fungsi hapus disini
                                        database.child("Admin").child("Zakat Mal").child(dataAmilmal.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(context, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Menutup dialog hapus data
                                        return;
                                    }
                                });

                                alert.create();
                                alert.show();
//                            return;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {

        return listZakatmal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView NamaamilZakatmal, JenisZakatmal, JumlahZakatmal, TglZakatmal, MuzakiZakatmal, PenyaluranZakatmal, KetZakatmal;
        private ImageView Gambarzakatmal;
        private CardView ListItemZakatmal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            NamaamilZakatmal = itemView.findViewById(R.id.namaamilzakatmal_ds);
            JenisZakatmal = itemView.findViewById(R.id.jeniszakatmal_ds);
            JumlahZakatmal = itemView.findViewById(R.id.jumlahzakatmal_ds);
            TglZakatmal = itemView.findViewById(R.id.tglzakatmal_ds);
            MuzakiZakatmal = itemView.findViewById(R.id.muzakizakatmal_ds);
            PenyaluranZakatmal = itemView.findViewById(R.id.penyaluranzakatmal_ds);
            KetZakatmal = itemView.findViewById(R.id.ketzakatmal_ds);
            Gambarzakatmal = itemView.findViewById(R.id.gambarzakatmal_ds);
            ListItemZakatmal = itemView.findViewById(R.id.list_item_zakatmal);

        }
    }
}
