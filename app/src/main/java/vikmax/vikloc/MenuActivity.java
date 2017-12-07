package vikmax.vikloc;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private ListView listaKategorija;
    private FloatingActionButton fab;
    private Integer idKorisnika;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_menu);

        this.listaKategorija =  (ListView) findViewById(R.id.listKategorije);
        napuniListu();

        listaKategorija.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MenuActivity.this, ItemsActivity.class);
                intent.putExtra("kategorija", listaKategorija.getItemAtPosition(i).toString());
                intent.putExtra("izradio", idKorisnika);
                startActivity(intent);
            }
        });

        Bundle dohvaceniId = getIntent().getExtras();
        idKorisnika = dohvaceniId.getInt("idKorisnik");

        fab = (FloatingActionButton) findViewById(R.id.floatingDodajKategoriju);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuActivity.this, AddCategorieActivity.class);
                i.putExtra("idKorisnik", idKorisnika);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        napuniListu();
    }

    private void napuniListu(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> kategorije = databaseAccess.dohvatiKategorije();
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kategorije);
        this.listaKategorija.setAdapter(adapter);
    }

}
