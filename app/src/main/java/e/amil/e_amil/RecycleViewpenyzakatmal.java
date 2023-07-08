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

public class RecycleViewpenyzakatmal extends RecyclerView.Adapter<RecycleViewpenyzakatmal.ViewHolder> implements Filterable {
    //Deklarasi Variabel
    ArrayList<datapenyaluran_mal> listzakatmal;
    listdatapenyzakat_mal context;
    ArrayList<datapenyaluran_mal> listzakatmalSearch;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    Filter setSearch = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<datapenyaluran_mal> filterpenyzakatmal = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filterpenyzakatmal.addAll(listzakatmalSearch);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (datapenyaluran_mal item : listzakatmalSearch) {
                    if (item.getNamaamilzakatmal_pen().toLowerCase().contains(filterPattern)) {
                        filterpenyzakatmal.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterpenyzakatmal;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listzakatmal.clear();
            listzakatmal.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public RecycleViewpenyzakatmal(ArrayList<datapenyaluran_mal> listzakatmal, listdatapenyzakat_mal context) {
        this.listzakatmal = listzakatmal;
        this.context = context;
        this.listzakatmalSearch = listzakatmal;
    }

    @Override
    public Filter getFilter() {

        return setSearch;
    }

    @NonNull
    @Override
    public RecycleViewpenyzakatmal.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewdesign_penyzakatmal, parent, false);
        return new RecycleViewpenyzakatmal.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewpenyzakatmal.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final String Jeniszakatmal = listzakatmal.get(position).getRjeniszakatmal_pen();
        final String Jumlahzakatmal_pen = listzakatmal.get(position).getJumlahzakatmal_pen();
        final String Tglzakatmal_pen = listzakatmal.get(position).getTglzakatmal_pen();
        final String Namazakatmal_pen = listzakatmal.get(position).getNamaamilzakatmal_pen();
        final String Keteranganzakat_pen = listzakatmal.get(position).getKetzakatmal_pen();
        final String Gambarzakatmal_pen = listzakatmal.get(position).getGambar();
        final datapenyaluran_mal datapenyaluran_mal = listzakatmal.get(position);


        holder.Jeniszakatmal_pen.setText(" " + Jeniszakatmal);
        holder.Jumlahzakatmal_pen.setText("Rp. " + Jumlahzakatmal_pen);
        holder.Tglzakatmal_pen.setText(" " + Tglzakatmal_pen);
        holder.Namazakatmal_pen.setText(" " + Namazakatmal_pen);
        holder.Ketzakatmal_pen.setText(" " + Keteranganzakat_pen);

        if (isEmpty(Gambarzakatmal_pen)){
            holder.Gambarzakatmal_pen.setImageResource(R.drawable.carigambar);
        }else {
            Glide.with(holder.itemView.getContext())
                    .load(Gambarzakatmal_pen.trim())
                    .into(holder.Gambarzakatmal_pen);
        }
        holder.list_item_penyzakatmal.setOnLongClickListener(new View.OnLongClickListener(){
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
                                        database.child("Admin").child("Peny Mal").child(datapenyaluran_mal.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
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

        return listzakatmal.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Jeniszakatmal_pen, Jumlahzakatmal_pen, Tglzakatmal_pen, Namazakatmal_pen, Ketzakatmal_pen;
        private ImageView Gambarzakatmal_pen;
        private CardView list_item_penyzakatmal;

        public ViewHolder (@NonNull View itemView) {
            super(itemView);
            //Menginisialisasi view view yang terpasang pada layout ini
            Jeniszakatmal_pen = itemView.findViewById(R.id.jenispenyzakatmal_ds);
            Jumlahzakatmal_pen = itemView.findViewById(R.id.jumlahpenyzakatmal_ds);
            Tglzakatmal_pen = itemView.findViewById(R.id.tglpenyzakatmal_ds);
            Namazakatmal_pen = itemView.findViewById(R.id.namaamilzakatmal_ds);
            Ketzakatmal_pen = itemView.findViewById(R.id.ketpenyzakatmal_ds);
            Gambarzakatmal_pen = itemView.findViewById(R.id.gambarpenyzakatmal_ds);
            list_item_penyzakatmal = itemView.findViewById(R.id.list_item_penyzakatmal);

        }
    }
}
