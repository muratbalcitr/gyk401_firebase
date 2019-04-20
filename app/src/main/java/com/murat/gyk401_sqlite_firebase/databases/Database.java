package com.murat.gyk401_sqlite_firebase.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Database extends SQLiteOpenHelper {

    private static final String DatabaseName = "kitaplik.db";
    public static final String TABLE_NAME = "kitaplar";
    private String KITAP_ADI = "kitap_adi";
    private String YAZAR_ADI = "yazar_adi";
    private String ISBN = "isbn";
    private String BASIM_TARIHI = "basim_tarihi";
    private String OZET="ozet";
    private String PATH="path";
    private static final int DB_VERSION = 1;

    private String query = "CREATE TABLE " + TABLE_NAME + " (" + KITAP_ADI + " TEXT," + " " + YAZAR_ADI + " TEXT, " + " " + ISBN + " TEXT, " + " " + BASIM_TARIHI + " TEXT,"+" "+OZET +" TEXT,"+" "+PATH+" TEXT)";


    public Database(Context context) {
        super(context, DatabaseName, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    public void kitapEkle(String kitap_Adi, String yazar_Adi, String ISBN_, String basim_Tarihi,String ozet,String path) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KITAP_ADI,kitap_Adi);
        contentValues.put(YAZAR_ADI,yazar_Adi);
        contentValues.put(ISBN,ISBN_);
        contentValues.put(BASIM_TARIHI,basim_Tarihi);
        contentValues.put(OZET,ozet);
        contentValues.put(PATH,path);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<HashMap<String, String>> kitaplar() {

        //Bu methodda ise tablodaki tüm değerleri alıyoruz
        //ArrayList adı üstünde Array lerin listelendiği bir Array.Burda hashmapleri listeleyeceğiz
        //Herbir satırı değer ve value ile hashmap a atıyoruz. Her bir satır 1 tane hashmap arrayı demek.
        //olusturdugumuz tüm hashmapleri ArrayList e atıp learnwordsbg dönüyoruz(return).

        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM  "+TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<HashMap<String, String>> kitaplarList = new ArrayList<HashMap<String, String>>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    map.put(cursor.getColumnName(i), cursor.getString(i));
                }
                kitaplarList.add(map);
            } while (cursor.moveToNext());
        }
        db.close();

        return kitaplarList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }



    public int getRowCount() {
        // Bu method bu uygulamada kullanılmıyor ama her zaman lazım olabilir.Tablodaki row sayısını learnwordsbg döner.
        String countQuery = "SELECT  * FROM  "+TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void resetTables() {
        //Bunuda uygulamada kullanmıyoruz. Tüm verileri siler. tabloyu resetler.
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
