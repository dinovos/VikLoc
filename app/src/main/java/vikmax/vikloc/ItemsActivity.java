package vikmax.vikloc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import java.util.List;

/**
 * Created by Hrvoje on 6.12.2017..
 */

public class ItemsActivity  extends AppCompatActivity {

    private ListView listaArtikala;
    private FloatingActionButton fab;
    private Integer idKorisnika;

    android.support.v7.widget.Toolbar kategorija;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_items);

        kategorija = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_kategorija);
        final Bundle dohvaceno = getIntent().getExtras();
        String dohvacenaKategorija;
        if(dohvaceno != null) {
            dohvacenaKategorija = dohvaceno.getString("kategorija");
            idKorisnika = dohvaceno.getInt("izradio");
            kategorija.setTitle(dohvacenaKategorija);
            Integer idKategorije = dohvatiIdKategorije(dohvacenaKategorija);

            this.listaArtikala = (ListView) findViewById(R.id.listArtikli);

            DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
            databaseAccess.open();
            List<String> artikli = databaseAccess.dohvatiArtikle(idKategorije);
            databaseAccess.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, artikli);
            this.listaArtikala.setAdapter(adapter);
        }

        fab = (FloatingActionButton) findViewById(R.id.floatingDodajArtikl);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ItemsActivity.this, AddProductActivity.class);
                i.putExtra("kategorija", dohvatiIdKategorije(dohvaceno.getString("kategorija")));
                i.putExtra("izradio", idKorisnika);
                startActivity(i);
            }
        });

    }

    public Integer dohvatiIdKategorije(String kategorija){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Integer idKategorije = databaseAccess.dohvatiBroj("SELECT id FROM KATEGORIJA WHERE naziv='" + kategorija + "'");
        databaseAccess.close();
        return idKategorije;
    }
}
