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

public class RecycleViewpenyzakatfitrah extends RecyclerView.Adapter<RecycleViewpenyzakatfitrah.ViewHolder> implements Filterable {
    //Deklarasi Variabel
    ArrayList<datapenyaluran_fitrah> listzakatfitrah;
    listdata_penyzakat_fitrah context;
    ArrayList<datapenyaluran_fitrah> listzakatfitrahSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<datapenyaluran_fitrah> filterpenyzakatfitrah = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterpenyzakatfitrah.addAll(listzakatfitrahSearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (datapenyaluran_fitrah item : listzakatfitrahSearch) {
                    if (item.getNamaamilzakat_pen().toLowerCase().contains(filterPattern)) {
                        filterpenyzakatfitrah.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterpenyzakatfitrah;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listzakatfitrah.clear();
            listzakatfitrah.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public RecycleViewpenyzakatfitrah(ArrayList<datapenyaluran_fitrah> listzakatfitrah, listdata_penyzakat_fitrah context) {
        this.listzakatfitrah = listzakatfitrah;
        this.context = context;
        this.listzakatfitrahSearch = listzakatfitrah;
    }

    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public RecycleViewpenyzakatfitrah.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewdesign_penyzakatfitrah, parent, false);
        return new RecycleViewpenyzakatfitrah.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewpenyzakatfitrah.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String Jeniszakatfitrah = listzakatfitrah.get(position).getRjeniszakat_pen();
        final String Jumlahzakatfitrah_pen = listzakatfitrah.get(position).getJumlahzakat_pen();
        final String Tglzakatfitrah_pen = listzakatfitrah.get(position).getTglinfakzakat_pen();
        final String Namazakatfitrah_pen = listzakatfitrah.get(position).getNamaamilzakat_pen();
        final String Keteranganzakatfitrah_pen = listzakatfitrah.get(position).getKetinfakzakat_pen();
        final String Gambarzakatfitrah_pen = listzakatfitrah.get(position).getGambar();
        final datapenyaluran_fitrah datapenyaluran_fitrah = listzakatfitrah.get(position);


        holder.Jeniszakatfitrah_pen.setText(" " + Jeniszakatfitrah);
        holder.Jumlahzakatfitrah_pen.setText(Jumlahzakatfitrah_pen + ".Kg");
        holder.Tglzakatfitrah_pen.setText(" " + Tglzakatfitrah_pen);
        holder.Namazakatfitrah_pen.setText(" " + Namazakatfitrah_pen);
        holder.Ketzakatfitrah_pen.setText(" " + Keteranganzakatfitrah_pen);

        if (isEmpty(Gambarzakatfitrah_pen)){
            holder.Gambarzakatfitrah_pen.setImageResource(R.drawable.carigambar);
        }else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambarzakatfitrah_pen.trim())
                    .into(holder.Gambarzakatfitrah_pen);
        }
        holder.list_item_penyzakatfitrah.setOnLongClickListener(new View.OnLongClickListener(){
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
                                        database.child("Admin").child("Peny Fitrah").child(datapenyaluran_fitrah.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        return listzakatfitrah.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Jeniszakatfitrah_pen, Jumlahzakatfitrah_pen, Tglzakatfitrah_pen, Namazakatfitrah_pen, Ketzakatfitrah_pen;
        private ImageView Gambarzakatfitrah_pen;
        private CardView list_item_penyzakatfitrah;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            Jeniszakatfitrah_pen = itemView.findViewById(R.id.jenispenyzakatfitrah_ds);
            Jumlahzakatfitrah_pen = itemView.findViewById(R.id.jumlahpenyzakatfitrah_ds);
            Tglzakatfitrah_pen = itemView.findViewById(R.id.tglpenyzakatfitrah_ds);
            Namazakatfitrah_pen = itemView.findViewById(R.id.namaamilzakatfitrah_ds);
            Ketzakatfitrah_pen = itemView.findViewById(R.id.ketpenyzakatfitrah_ds);
            Gambarzakatfitrah_pen = itemView.findViewById(R.id.gambarpenyzakatfitrah_ds);
            list_item_penyzakatfitrah = itemView.findViewById(R.id.list_item_penyzakatfitrah);

        }
    }
}
