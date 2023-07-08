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

public class RecycleViewPenyInfaq extends RecyclerView.Adapter<RecycleViewPenyInfaq.ViewHolder> implements Filterable {

    ArrayList<datapenyaluran_infak> listPenyInfaq;
    listdata_penyinfaq context;
    ArrayList<datapenyaluran_infak> listPenyInfaqSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    //Filter Data berdasarkan nama
    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<datapenyaluran_infak> filterInfaq = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                filterInfaq.addAll(listPenyInfaqSearch);
            }else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (datapenyaluran_infak item : listPenyInfaqSearch){
                    if (item.getNamaamilinfak_pen().toLowerCase().contains(filterPattern)) {
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

            listPenyInfaq.clear();
            listPenyInfaq.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };
//    private ViewHolder holder;
//    private int position;

    //Membuat konstruktor untuk menyimpan data
    public RecycleViewPenyInfaq(ArrayList<datapenyaluran_infak> listPenyInfaq, listdata_penyinfaq context){
        this.listPenyInfaq = listPenyInfaq;
        this.context = context;
        this.listPenyInfaqSearch = listPenyInfaq;
    }
    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public RecycleViewPenyInfaq.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewdesign_penyinfaq, parent, false);
        return new RecycleViewPenyInfaq.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewPenyInfaq.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        this.holder = holder;
//        this.position = position;

        final String JenisPenyInfaq = listPenyInfaq.get(position).getRjenisinfak_pen();
        final String JumlahPenyinfaq = listPenyInfaq.get(position).getJumlahinfak_pen();
        final String TglPenyInfaq = listPenyInfaq.get(position).getTglinfak_pen();
        final String KeteranganPenyinfaq = listPenyInfaq.get(position).getKetinfak_pen();
        final String Gambar = listPenyInfaq.get(position).getGambar();
        final datapenyaluran_infak dataPenyInfaq = listPenyInfaq.get(position);


        holder.JenisPenyInfaq.setText(" "+JenisPenyInfaq);
        holder.JumlahPenyInfaq.setText("Rp. "+JumlahPenyinfaq);
        holder.TglPenyInfaq.setText(" "+TglPenyInfaq);
        holder.KetPenyInfaq.setText(" "+KeteranganPenyinfaq);

        if (isEmpty(Gambar)){
            holder.GambarPenyinfaq.setImageResource(R.drawable.carigambar);
        }else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambar.trim())
                    .into(holder.GambarPenyinfaq);
        }

        holder.ListItemPenyinfaq.setOnLongClickListener(new View.OnLongClickListener(){

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
                                bundle.putString("dataJenisPenyInfaq",listPenyInfaq.get(position).getRjenisinfak_pen());
                                bundle.putString("dataJumlahPenyInfaq",listPenyInfaq.get(position).getJumlahinfak_pen());
                                bundle.putString("dataTglPenyInfaq",listPenyInfaq.get(position).getTglinfak_pen());
                                bundle.putString("dataKetPenyInfaq",listPenyInfaq.get(position).getKetinfak_pen());
                                bundle.putString("dataPrimaryPenyInfaq",listPenyInfaq.get(position).getKey());
                                bundle.putString("dataGambarPenyInfaq",listPenyInfaq.get(position).getGambar());

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
                                        database.child("Admin").child("Peny Infak").child(dataPenyInfaq.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        return listPenyInfaq.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView JenisPenyInfaq, JumlahPenyInfaq, TglPenyInfaq, KetPenyInfaq;
        private ImageView GambarPenyinfaq;
        private CardView ListItemPenyinfaq;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            JenisPenyInfaq = itemView.findViewById(R.id.jenisPenyinfaq_ds);
            JumlahPenyInfaq = itemView.findViewById(R.id.jumlahPenyinfaq_ds);
            TglPenyInfaq = itemView.findViewById(R.id.tglPenyinfaq_ds);
            KetPenyInfaq = itemView.findViewById(R.id.ketPenyinfaq_ds);
            GambarPenyinfaq = itemView.findViewById(R.id.gambarPenyinfaq_ds);
            ListItemPenyinfaq = itemView.findViewById(R.id.list_item_Penyinfaq);

        }
    }
}
