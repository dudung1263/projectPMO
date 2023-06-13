package e.amil.e_amil;

import static android.text.TextUtils.isEmpty;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewZakat extends RecyclerView.Adapter<RecycleViewZakat.ViewHolder> implements Filterable {

    //Deklarasi Variabel
    ArrayList<data_amil> listZakat;
    listdata_zakat context;
    ArrayList<data_amil> listZakatSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Filter Data berdasarkan nama
    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<data_amil> filterZakat = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterZakat.addAll(listZakatSearch);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (data_amil item : listZakatSearch){
                    if (item.getRjeniszakat().toLowerCase().contains(filterPattern)) {
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

            listZakat.clear();
            listZakat.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//    private ViewHolder holder;
//    private int position;

    //Membuat konstruktor untuk menyimpan data
    public RecycleViewZakat(ArrayList<data_amil> listZakat, listdata_zakat context){
        this.listZakat = listZakat;
        this.context = context;
        this.listZakatSearch = listZakat;
    }
    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdesign_zakat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        this.holder = holder;
//        this.position = position;

        final String JenisZakat = listZakat.get(position).getRjeniszakat();
        final String JumlahZakat = listZakat.get(position).getJumlahzakat();
        final String TglZakat = listZakat.get(position).getTglzakat();
        final String MuzakiZakat = listZakat.get(position).getMuzakizakat();
        final String PenyaluranZakat = listZakat.get(position).getPenyaluranzakat();
        final String KeteranganZakat = listZakat.get(position).getKetzakat();
        final String Gambar = listZakat.get(position).getGambar();
        final data_amil dataAmil = listZakat.get(position);

        holder.JenisZakat.setText(": "+JenisZakat);
        holder.JumlahZakat.setText(": "+JumlahZakat);
        holder.TglZakat.setText(": "+TglZakat);
        holder.MuzakiZakat.setText(": "+MuzakiZakat);
        holder.PenyaluranZakat.setText(": "+PenyaluranZakat);
        holder.KetZakat.setText(": "+KeteranganZakat);

        if (isEmpty(Gambar)){
            holder.Gambarzakat.setImageResource(R.drawable.carigambar);
        }else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambar.trim())
                    .into(holder.Gambarzakat);
        }

        holder.ListItemZakat.setOnLongClickListener(new View.OnLongClickListener(){

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
                                bundle.putString("dataJenisZakat",listZakat.get(position).getRjeniszakat());
                                bundle.putString("dataJumlahZakat",listZakat.get(position).getJumlahzakat());
                                bundle.putString("dataTglZakat",listZakat.get(position).getTglzakat());
                                bundle.putString("dataMuzakiZakat",listZakat.get(position).getMuzakizakat());
                                bundle.putString("dataPenyaluranZakat",listZakat.get(position).getPenyaluranzakat());
                                bundle.putString("dataKetZakat",listZakat.get(position).getKetzakat());
                                bundle.putString("dataPrimaryKey",listZakat.get(position).getKey());
                                bundle.putString("dataGambarZakat",listZakat.get(position).getGambar());

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
                                        database.child("Admin").child("Zakat").child(dataAmil.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        return listZakat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView JenisZakat, JumlahZakat, TglZakat, MuzakiZakat, PenyaluranZakat, KetZakat;
        private ImageView Gambarzakat;
        private LinearLayout ListItemZakat;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            JenisZakat = itemView.findViewById(R.id.jeniszakat_ds);
            JumlahZakat = itemView.findViewById(R.id.jumlahzakat_ds);
            TglZakat = itemView.findViewById(R.id.tglzakat_ds);
            MuzakiZakat = itemView.findViewById(R.id.muzakizakat_ds);
            PenyaluranZakat = itemView.findViewById(R.id.penyaluranzakat_ds);
            KetZakat = itemView.findViewById(R.id.ketzakat_ds);
            Gambarzakat = itemView.findViewById(R.id.gambarzakat_ds);
            ListItemZakat = itemView.findViewById(R.id.list_item_zakat);

        }
    }
}
