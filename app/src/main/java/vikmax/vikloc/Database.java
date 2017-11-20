package vikmax.vikloc;

<<<<<<< HEAD
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
=======
import android.content.Context;
>>>>>>> origin/master
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hrvoje on 17.11.2017..
 */
public class Database extends SQLiteOpenHelper {

    public static final String DatabaseName = "VikLoc.db";
<<<<<<< HEAD
    public static final String TableName = "korisnik";

    public Database(Context context) {
=======

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
>>>>>>> origin/master
        super(context, DatabaseName, null, 1);
    }

    @Override
<<<<<<< HEAD
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE korisnik (id integer primary key autoincrement, ime varchar(45), prezime varchar(45), korime varchar(45), lozinka varchar(45))");
        db.execSQL("CREATE TABLE artikl (id integer primary key autoincrement, naziv varchar(45), opis varchar(80), kategorija integer, izradio integer)");
        db.execSQL("CREATE TABLE kategorija (id integer primary key autoincrement, naziv varchar(45), opis varchar(80), izradio integer)");
        db.execSQL("CREATE TABLE pozicija (oznaka varchar(45), mapa integer, skladiste integer, artikl integer)");
        db.execSQL("CREATE TABLE skladiste (id integer primary key autoincrement, naziv varchar(45), adresa varchar(45), opis varchar(80))");
=======
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
>>>>>>> origin/master

    }

    @Override
<<<<<<< HEAD
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists korisnik");
        onCreate(db);
    }

    public boolean insertData(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues podaci = new ContentValues();
        podaci.put("ime", "Dino");
        podaci.put("prezime", "Novosel");
        podaci.put("korime", "dnovosel");
        podaci.put("lozinka", "admin");
        long rezultat = db.insert(TableName, null, podaci);
        if (rezultat == -1)
            return false;
        else
            return true;
    }

    public Cursor dohvatiPodatke(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor podaci = db.rawQuery("select * from "+TableName, null);
        return podaci;
=======
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

>>>>>>> origin/master
    }
}
