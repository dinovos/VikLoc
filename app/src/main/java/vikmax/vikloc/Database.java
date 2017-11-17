package vikmax.vikloc;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hrvoje on 17.11.2017..
 */
public class Database extends SQLiteOpenHelper {

    public static final String DatabaseName = "VikLoc.db";

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DatabaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
