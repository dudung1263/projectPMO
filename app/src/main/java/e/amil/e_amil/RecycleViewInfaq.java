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

public class RecycleViewInfaq extends RecyclerView.Adapter<RecycleViewInfaq.ViewHolder> implements Filterable {

    //Deklarasi Variabel
    ArrayList<data_amilinfaqq> listInfaq;
    listdata_infaq context;
    ArrayList<data_amilinfaqq> listInfaqSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Filter Data berdasarkan nama
    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<data_amilinfaqq> filterInfaq = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterInfaq.addAll(listInfaqSearch);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (data_amilinfaqq item : listInfaqSearch){
                    if (item.getNamaamilinfak().toLowerCase().contains(filterPattern)) {
                        filterInfaq.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterInfaq;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            listInfaq.clear();
            listInfaq.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//    private ViewHolder holder;
//    private int position;

    //Membuat konstruktor untuk menyimpan data
    public RecycleViewInfaq(ArrayList<data_amilinfaqq> listInfaq, listdata_infaq context){
        this.listInfaq = listInfaq;
        this.context = context;
        this.listInfaqSearch = listInfaq;
    }
    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public RecycleViewInfaq.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdesign_infaq, parent, false);
        return new RecycleViewInfaq.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        this.holder = holder;
//        this.position = position;

        final String NamaamilInfaq = listInfaq.get(position).getNamaamilinfak();
        final String JenisInfaq = listInfaq.get(position).getRjenisinfak();
        final String Jumlahinfaq = listInfaq.get(position).getJumlahinfak();
        final String TglInfaq = listInfaq.get(position).getTglinfak();
        final String MuzakiInfaq = listInfaq.get(position).getMuzakiinfak();
        final String Penyaluraninfaq = listInfaq.get(position).getPenyaluraninfak();
        final String Keteranganinfaq = listInfaq.get(position).getKetinfak();
        final String Gambar = listInfaq.get(position).getGambarinfak();
        final data_amilinfaqq dataInfaq = listInfaq.get(position);


        holder.NamaamilInfaq.setText(" "+NamaamilInfaq);
        holder.JenisInfaq.setText(" "+JenisInfaq);
        holder.JumlahInfaq.setText("Rp. "+Jumlahinfaq);
        holder.TglInfaq.setText(" "+TglInfaq);
        holder.MuzakiInfaq.setText(" "+MuzakiInfaq);
        holder.PenyaluranInfaq.setText(" "+Penyaluraninfaq);
        holder.KetInfaq.setText(" "+Keteranganinfaq);

        if (isEmpty(Gambar)){
            holder.Gambarinfaq.setImageResource(R.drawable.carigambar);
        }else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambar.trim())
                    .into(holder.Gambarinfaq);
        }

        holder.ListIteminfaq.setOnLongClickListener(new View.OnLongClickListener(){

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
                                        database.child("Admin").child("Infaq").child(dataInfaq.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        return listInfaq.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView NamaamilInfaq, JenisInfaq, JumlahInfaq, TglInfaq, MuzakiInfaq, PenyaluranInfaq, KetInfaq;
        private ImageView Gambarinfaq;
        private CardView ListIteminfaq;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            NamaamilInfaq = itemView.findViewById(R.id.namaamilinfaq_ds);
            JenisInfaq = itemView.findViewById(R.id.jenisinfaq_ds);
            JumlahInfaq = itemView.findViewById(R.id.jumlahinfaq_ds);
            TglInfaq = itemView.findViewById(R.id.tglinfaq_ds);
            MuzakiInfaq = itemView.findViewById(R.id.muzakinfaq_ds);
            PenyaluranInfaq = itemView.findViewById(R.id.penyaluraninfaq_ds);
            KetInfaq = itemView.findViewById(R.id.ketinfaq_ds);
            Gambarinfaq = itemView.findViewById(R.id.gambarinfaq_ds);
            ListIteminfaq = itemView.findViewById(R.id.list_item_infaq);

        }
    }
}
