package vikmax.vikloc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hrvoje on 17.11.2017..
 */
public class Database extends SQLiteOpenHelper {

    public static final String DatabaseName = "VikLoc.db";
    public static final String TableName = "korisnik";
    public static final String prviId = "default";

    public Database(Context context) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE korisnik (id integer primary key autoincrement, ime varchar(45), prezime varchar(45), korime varchar(45), lozinka varchar(45))");
        db.execSQL("CREATE TABLE artikl (id integer primary key autoincrement, naziv varchar(45), opis varchar(80), kategorija integer, izradio integer)");
        db.execSQL("CREATE TABLE kategorija (id integer primary key autoincrement, naziv varchar(45), opis varchar(80), izradio integer)");
        db.execSQL("CREATE TABLE pozicija (oznaka varchar(45), mapa integer, skladiste integer, artikl integer)");
        db.execSQL("CREATE TABLE skladiste (id integer primary key autoincrement, naziv varchar(45), adresa varchar(45), opis varchar(80))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists korisnik");
        db.execSQL("drop table if exists artikl");
        db.execSQL("drop table if exists kategorija");
        db.execSQL("drop table if exists pozicija");
        db.execSQL("drop table if exists skladiste");
        onCreate(db);
    }

    public void insertData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DELETE from korisnik where id in (9, 10, 11)");
        //db.execSQL("INSERT into korisnik (ime, prezime, korime, lozinka)values ('Milan', 'Ivic', 'iivic', 'iivic')");
        /*ContentValues podaci = new ContentValues();
        podaci.put("ime", "Pero");
        podaci.put("prezime", "Novosel");
        podaci.put("korime", "dnovosel");
        podaci.put("lozinka", "admin");
        long rezultat = db.insert(TableName, null, podaci);
        if (rezultat == -1)
            return false;
        else
            return true; */
    }

    public Cursor dohvatiPodatke(String korime, String lozinka){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor podaci = db.rawQuery("select * from korisnik where korime ='" + korime + "' and lozinka ='" +lozinka +"'" , null);
        return podaci;
    }
}
