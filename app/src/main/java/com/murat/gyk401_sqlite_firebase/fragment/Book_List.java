package com.murat.gyk401_sqlite_firebase.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.murat.gyk401_sqlite_firebase.R;
import com.murat.gyk401_sqlite_firebase.adapter.Book_Adapter;
import com.murat.gyk401_sqlite_firebase.model.Book_Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Book_List extends Fragment {

    private List<Book_Model> sehirler = new ArrayList<>();
    private RecyclerView rvSehirler;
    private Book_Adapter BookAdapter;

    public Book_List() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_kitap_list, container, false);

        return  view;
    }

}
