package com.murat.gyk401_sqlite_firebase.model;

public class Book_Model {
String KitapAdi,YazarAdi,ISBN,BasimTarihi,Ozet,Path;

    public Book_Model(String kitapAdi, String yazarAdi, String ISBN, String basimTarihi, String ozet,String Path) {
        KitapAdi = kitapAdi;
        YazarAdi = yazarAdi;
        this.ISBN = ISBN;
        BasimTarihi = basimTarihi;
        Ozet = ozet;
        this.Path = Path;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getKitapAdi() {
        return KitapAdi;
    }

    public void setKitapAdi(String kitapAdi) {
        KitapAdi = kitapAdi;
    }

    public String getYazarAdi() {
        return YazarAdi;
    }

    public void setYazarAdi(String yazarAdi) {
        YazarAdi = yazarAdi;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getBasimTarihi() {
        return BasimTarihi;
    }

    public void setBasimTarihi(String basimTarihi) {
        BasimTarihi = basimTarihi;
    }

    public String getOzet() {
        return Ozet;
    }

    public void setOzet(String ozet) {
        Ozet = ozet;
    }
}
