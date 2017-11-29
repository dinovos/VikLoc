package vikmax.vikloc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hrvoje on 21.11.2017..
 */

public class Database extends SQLiteOpenHelper {

    public static final String Database_Name = "baza.db";
    public static final String TableName = "korisnik";

    public Database(Context context) {
        super(context, Database_Name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE table korisnik (id integer primary key autoincrement, ime varchar(45), prezime varchar(45), email varchar(45), lozinka varchar(45))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP table if exists korisnik");
        onCreate(db);

    }
    public void insertData() {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("INSERT into korisnik (ime, prezime, email, lozinka)values ('Hrvoje', 'Cosic', 'hcosic2@foi.hr', 'adminadmin')");
        /*ContentValues podaci = new ContentValues();
        podaci.put("ime", "Dino");
        podaci.put("prezime", "Novosel");
        podaci.put("email", "dnovosel@foi.hr");
        podaci.put("lozinka", "adminadmin");
        long result = db.insert(TableName, null, podaci);
        if(result == -1)
            return false;
        else
            return true; */
    }
    public Cursor dohvacanjePodataka(String email, String lozinka){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor podaci = db.rawQuery("SELECT * FROM korisnik where email= '"+ email + "' and lozinka= '"+ lozinka +"'", null);
        return podaci;

    }
}
