package vikmax.vikloc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dino on 5.12.2017..
 */

public class DatabaseAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /* Dodati funkcije za vracanje trazenih podataka iz baze - po potrebi */

    public List<String> dohvatiKategorije() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT naziv FROM KATEGORIJA", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public List<String> dohvatiArtikle(Integer kategorija) {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT naziv FROM ARTIKL WHERE kategorija='" + kategorija + "'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public boolean loginProvjera(String user, String lozinka) {
        Cursor podaci = database.rawQuery("SELECT * FROM KORISNIK where korisnicko_ime= '" + user + "' and lozinka= '" + lozinka + "'", null);
        Integer brojac = podaci.getCount();
        if (brojac == 0) {
            return false;
        }
        return true;
    }

    public int dohvatiBroj(String upit) {
        Cursor podaci = database.rawQuery(upit, null);
        podaci.moveToFirst();
        Integer broj;
        if (podaci != null && podaci.moveToFirst())
            broj = podaci.getInt(0);
        else
            broj = 0;
        return broj;
    }

    public boolean unesiKategoriju(String naziv, String opis, Integer izradio) {
        ContentValues unos = new ContentValues();
        unos.put("naziv", naziv);
        unos.put("opis", opis);
        unos.put("izradio", izradio);
        long rezultat = database.insert("KATEGORIJA", null, unos);
        if (rezultat == -1)
            return false;
        else
            return true;
    }

    public boolean unesiArtikl(String naziv, String opis, Integer kategorija, Integer izradio) {
        ContentValues unos = new ContentValues();
        unos.put("naziv", naziv);
        unos.put("opis", opis);
        unos.put("kategorija", kategorija);
        unos.put("izradio", izradio);
        long rezultat = database.insert("ARTIKL", null, unos);
        if (rezultat == -1)
            return false;
        else
            return true;
    }

    public void obrisiKategoriju(String nazivKategorije) {
        database.execSQL("DELETE FROM KATEGORIJA WHERE naziv = '" + nazivKategorije + "'");
    }

    public void obrisiArtikl(String nazivArtikla) {
        database.execSQL("DELETE FROM ARTIKL WHERE naziv = '"+nazivArtikla+"'");
    }

    public ArrayList<String> dohvatiDetaljeAtrikla(String nazivArtikla){
        ArrayList<String> listaDetalja = new ArrayList<>();
        Cursor podaci = database.rawQuery("SELECT s.adresa, s.opis, s.naziv, p.oznaka FROM SKLADISTE s JOIN POZICIJA p on s.id = p.SKLADISTE JOIN ARTIKL a on p.ARTIKL = a.id WHERE a.naziv = '"+nazivArtikla+"'", null);
        podaci.moveToFirst();
        while(!podaci.isAfterLast()){
            listaDetalja.add(podaci.getString(0));
            listaDetalja.add(podaci.getString(1));
            listaDetalja.add(podaci.getString(2));
            listaDetalja.add(podaci.getString(3));
            podaci.moveToNext();
        }
        podaci.close();
        return  listaDetalja;
    }

}
