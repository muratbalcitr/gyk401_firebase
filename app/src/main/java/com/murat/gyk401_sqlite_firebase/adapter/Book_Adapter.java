package com.murat.gyk401_sqlite_firebase.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.murat.gyk401_sqlite_firebase.R;
import com.murat.gyk401_sqlite_firebase.model.Book_Model;

import java.util.List;

public class Book_Adapter extends RecyclerView.Adapter<Book_Adapter.KitapHolder> {
    Context context;
    List<Book_Model> kitapList;

    public Book_Adapter(Context context, List<Book_Model> list) {
        this.context = context;
        this.kitapList = list;
    }

    @NonNull
    @Override
    public KitapHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.book_item, viewGroup, false);
        return new KitapHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KitapHolder kitapHolder, int i) {
        Book_Model book_model = kitapList.get(i);
        Glide.with(context).load(book_model.getPath()).into(kitapHolder.imgBook);
        kitapHolder.tvBookName.setText(book_model.getKitapAdi());
        kitapHolder.tvContent.setText(book_model.getOzet());
    }

    @Override
    public int getItemCount() {
        return kitapList.size();
    }

    public class KitapHolder extends RecyclerView.ViewHolder {
        ImageView imgBook;
        TextView tvBookName;
        TextView tvContent;

        public KitapHolder(@NonNull View itemView) {
            super(itemView);
            imgBook = itemView.findViewById(R.id.imgBook);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}
