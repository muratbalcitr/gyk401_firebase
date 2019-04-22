package com.murat.gyk401_sqlite_firebase.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.murat.gyk401_sqlite_firebase.R;
import com.murat.gyk401_sqlite_firebase.databases.Database;

import java.io.File;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddBook extends Fragment implements View.OnClickListener {

    EditText etYazarAdi, etKitapAdi, etISBN, etTarih, etOzet;
    Button btnEkle;
    Database database;
    SQLiteDatabase db;
    ImageButton imgSelect;
    private static final int CAMERA = 21;
    private static final int GALLERY = 22;
    String path;

    public AddBook() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = new Database(getContext());
        db =  Objects.requireNonNull(getActivity()).openOrCreateDatabase(database.TABLE_NAME,
                MODE_PRIVATE,
                null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_book, container, false);
        etKitapAdi = view.findViewById(R.id.etKitapAdi);
        etYazarAdi = view.findViewById(R.id.etYazarAdi);
        etOzet = view.findViewById(R.id.etOzet);
        etISBN = view.findViewById(R.id.etISBN);
        etTarih = view.findViewById(R.id.etBasimTarihi);
        btnEkle = view.findViewById(R.id.btnSave);
        imgSelect = view.findViewById(R.id.btnImgSelect);
        imgSelect.setOnClickListener(this);
        btnEkle.setOnClickListener(this);
        return view;


    }

    private void addTableContent() {
        int count = database.getRowCount();
        String kitap_Adi = etKitapAdi.getText().toString();
        String Yazar_Adi = etYazarAdi.getText().toString();
        String ISBN = etISBN.getText().toString();
        String Tarih = etTarih.getText().toString();
        String ozet = etOzet.getText().toString();
        database.kitapEkle(kitap_Adi, Yazar_Adi, ISBN, Tarih, ozet, path);
        Toast.makeText(getActivity(), "başarılı", Toast.LENGTH_SHORT).show();

        if (database.getRowCount() > count) {
            etKitapAdi.setText("");
            etYazarAdi.setText("");
            etTarih.setText("");
            etISBN.setText("");
            etOzet.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave:
                addTableContent();
                break;
            case R.id.btnImgSelect:
                showPictureDialog();
                break;
        }
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("İşlem Seçiniz");
        String[] pictureDialogItems = {
                "Galeriden Seç",
                "Fotoğrafını çek"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void takePhotoFromCamera() {
        Intent intentPick = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intentPick, CAMERA);
    }

    @SuppressLint("IntentReset")
    public void choosePhotoFromGallary() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, GALLERY);
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
             if (requestCode == GALLERY) {
                if (data != null) {
                    Uri selectedImageUri = data.getData();
                    path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                    }
                    imgSelect.setImageURI(selectedImageUri);
                }

            } else if (requestCode == CAMERA) {
                Bitmap thumbnail = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                imgSelect.setImageBitmap(thumbnail);
                path = String.valueOf(thumbnail);
                Toast.makeText(getContext(), "Image Saved!", Toast.LENGTH_SHORT).show();
            }
     }


}
