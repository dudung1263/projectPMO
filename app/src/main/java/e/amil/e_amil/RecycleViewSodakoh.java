package e.amil.e_amil;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class RecycleViewSodakoh extends RecyclerView.Adapter<RecycleViewSodakoh.ViewHolder> implements Filterable {

    //Deklarasi Variabel
    ArrayList<data_amilsodakoh> listSodakoh;
    lisdata_sodakoh context;
    ArrayList<data_amilsodakoh> listSodakohSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Filter Data berdasarkan nama
    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<data_amilsodakoh> filterSodakoh = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterSodakoh.addAll(listSodakohSearch);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (data_amilsodakoh item : listSodakohSearch){
                    if (item.getRjenissodakoh().toLowerCase().contains(filterPattern)) {
                        filterSodakoh.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterSodakoh;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            listSodakoh.clear();
            listSodakoh.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//    private ViewHolder holder;
//    private int position;

    //Membuat konstruktor untuk menyimpan data
    public RecycleViewSodakoh(ArrayList<data_amilsodakoh> listSodakoh, lisdata_sodakoh context){
        this.listSodakoh = listSodakoh;
        this.context = context;
        this.listSodakohSearch = listSodakoh;
    }
    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public RecycleViewSodakoh.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdesign_sodakoh, parent, false);
        return new RecycleViewSodakoh.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewSodakoh.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        this.holder = holder;
//        this.position = position;

        final String JenisSodakoh = listSodakoh.get(position).getRjenissodakoh();
        final String JumlahSodakoh = listSodakoh.get(position).getJumlahsodakoh();
        final String TglSodakoh = listSodakoh.get(position).getTglsodakoh();
        final String MuzakiSodakoh = listSodakoh.get(position).getMuzakisodakoh();
        final String Penyaluransodakoh = listSodakoh.get(position).getPenyaluransodakoh();
        final String Keterangansodakoh = listSodakoh.get(position).getKeterangansodakoh();
        final String Gambar = listSodakoh.get(position).getGambarsodakoh();
        final data_amilsodakoh dataSodakoh = listSodakoh.get(position);

        //Memasukkan nilai kedalam view : NIM, Nama, Prodi
        holder.JenisSodakoh.setText(": "+JenisSodakoh);
        holder.JumlahSodakoh.setText(": "+JumlahSodakoh);
        holder.TglSodakoh.setText(": "+TglSodakoh);
        holder.MuzakiSodakoh.setText(": "+MuzakiSodakoh);
        holder.PenyaluranSodakoh.setText(": "+Penyaluransodakoh);
        holder.KetSodakoh.setText(": "+Keterangansodakoh);

        if (isEmpty(Gambar)){
            holder.GambarSodakoh.setImageResource(R.drawable.carigambar);
        }else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambar.trim())
                    .into(holder.GambarSodakoh);
        }

        holder.ListItemsodakoh.setOnLongClickListener(new View.OnLongClickListener(){

            @Override
            public boolean onLongClick(View view) {
                final String[] action = {"Update","Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setTitle("Apa yang akan anda pilih?");
                alert.setItems(action, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                //Berpindah activity pada halaman layout UpdateData dan mengambil data dari ListMahasiswa
                                Bundle bundle = new Bundle();
                                bundle.putString("dataJenisSodakoh",listSodakoh.get(position).getRjenissodakoh());
                                bundle.putString("dataJumlahSodakoh",listSodakoh.get(position).getJumlahsodakoh());
                                bundle.putString("dataTglSodakoh",listSodakoh.get(position).getTglsodakoh());
                                bundle.putString("dataMuzakiSodakoh",listSodakoh.get(position).getMuzakisodakoh());
                                bundle.putString("dataPenyaluranSodakoh",listSodakoh.get(position).getPenyaluransodakoh());
                                bundle.putString("dataKetSodakoh",listSodakoh.get(position).getKeterangansodakoh());
                                bundle.putString("dataPrimarySodakoh",listSodakoh.get(position).getKey());
                                bundle.putString("dataGambarSodakoh",listSodakoh.get(position).getGambarsodakoh());

                                //Intent intent = new Intent(view.getContext(), update_zakat.class);
                                //intent.putExtras(bundle);
                                //context.startActivity(intent);
                                break;

                            case 1:
                                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                                alert.setTitle("Apakah anda yakin akan menghapus Data ini?");
                                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Menjalankan Fungsi hapus disini
                                        database.child("Admin").child("sodakoh").child(dataSodakoh.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        return listSodakoh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView JenisSodakoh, JumlahSodakoh, TglSodakoh, MuzakiSodakoh, PenyaluranSodakoh, KetSodakoh;
        private ImageView GambarSodakoh;
        private CardView ListItemsodakoh;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            JenisSodakoh = itemView.findViewById(R.id.jenissodaqoh_ds);
            JumlahSodakoh = itemView.findViewById(R.id.jumlahsodaqoh_ds);
            TglSodakoh = itemView.findViewById(R.id.tglsodaqoh_ds);
            MuzakiSodakoh = itemView.findViewById(R.id.muzakisodaqoh_ds);
            PenyaluranSodakoh = itemView.findViewById(R.id.penyaluransodaqoh_ds);
            KetSodakoh = itemView.findViewById(R.id.ketsodaqoh_ds);
            GambarSodakoh = itemView.findViewById(R.id.gambarsodaqoh_ds);
            ListItemsodakoh = itemView.findViewById(R.id.list_item_sodaqoh);

        }
    }
}
