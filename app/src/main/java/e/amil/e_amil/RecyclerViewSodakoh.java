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

public class RecyclerViewSodakoh extends RecyclerView.Adapter<RecyclerViewSodakoh.ViewHolder> implements Filterable {

    //Deklarasi Variabel
    ArrayList<datasodakoh> listsodakoh;
    listdata_sodakoh context;
    ArrayList<datasodakoh> listSodakohSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Filter Data berdasarkan nama
    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<datasodakoh> filterMahasiswa = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterMahasiswa.addAll(listsodakoh);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (datasodakoh item : listSodakohSearch){
                    if (item.get().toLowerCase().contains(filterPattern)){
                        filterMahasiswa.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterMahasiswa;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {

            listsodakoh.clear();
            listsodakoh.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
    private int fragment_viewdesign_sodakoh;
//    private ViewHolder holder;
//    private int position;

    //Membuat konstruktor untuk menyimpan data
    public  RecyclerViewSodakoh(ArrayList<datasodakoh> listsodakoh, listdata_sodakoh context){
        this.listsodakoh = listsodakoh;
        this.context = context;
        this.listSodakohSearch = listsodakoh;
    }
    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public RecyclerViewSodakoh.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(fragment_viewdesign_sodakoh, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        this.holder = holder;
//        this.position = position;

        final String jenis_sodakoh = listsodakoh.get(position).getJenis_sodakoh();
        final String jumlah_sodakoh = listsodakoh.get(position).getJumlah_sodakoh();
        final String tanggal_sodakoh = listsodakoh.get(position).getTanggal_sodakoh();
        final String muzaki = listsodakoh.get(position).getMuzaki_sodakoh();
        final String keterangan = listsodakoh.get(position).getKeterangan_sodakoh();
        final datasodakoh datasodakoh = listsodakoh.get(position);

        //Memasukkan nilai kedalam view : NIM, Nama, Prodi
        holder.jenis_sodakoh.setText(": "+jenis_sodakoh);
        holder.jenis_sodakoh.setText(": "+jumlah_sodakoh);
        holder.tanggal_sodakoh.setText(": "+tanggal_sodakoh);
        holder.muzaki_sodako.setText(": "+muzaki);
        holder.keterangan_sodakoh.setText(": "+keterangan);




        holder.ListItem.setOnLongClickListener(new View.OnLongClickListener(){

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
                                bundle.putString("datajenis",datasodakoh.get(position).getjenis_sodakoh);
                                bundle.putString("datajumlah",datasodakoh.get(position).getNama_crud());
                                bundle.putString("datatanggal",datasodakoh.get(position).getFakultas_crud());
                                bundle.putString("datamuzaki",datasodakoh.get(position).getProdi_crud());
                                bundle.putString("dataketerangan",datasodakoh.get(position).getHasil_crud());


                                Intent intent = new Intent(view.getContext(), update.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            case 1:
                                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                                alert.setTitle("Apakah anda yakin akan menghapus Data ini?");
                                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Menjalankan Fungsi hapus disini
                                        database.child("Admin").child("Mahasiswa").child(datasodakoh.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        return listsodakoh.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView jenis_sodakoh, jumlah_sodakoh, tanggal_sodakoh, keterangan_sodakoh, muzaki_sodako;
        private ImageView Gambar;
        private LinearLayout ListItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            jenis_sodakoh = itemView.findViewById(R.id.jenissodakoh_view);
            jumlah_sodakoh = itemView.findViewById(R.id.jumlahsodakoh_view);
            tanggal_sodakoh = itemView.findViewById(R.id.tanggalsodakoh_view);
            keterangan_sodakoh = itemView.findViewById(R.id.keterangansodakoh_view);
            muzaki_sodako = itemView.findViewById(R.id.muzaki_view);
            ListItem = itemView.findViewById(R.id.list_item);

        }
    }


}
