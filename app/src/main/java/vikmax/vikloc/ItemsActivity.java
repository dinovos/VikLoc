package vikmax.vikloc;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toolbar;

import java.util.List;

/**
 * Created by Hrvoje on 6.12.2017..
 */

public class ItemsActivity  extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private ListView listaArtikala;
    private FloatingActionButton fab;
    private Integer idKorisnika;
    private Integer idKategorije;

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
            idKategorije = dohvatiIdKategorije(dohvacenaKategorija);

            this.listaArtikala = (ListView) findViewById(R.id.listArtikli);

            napuniListu(idKategorije);

        }

        listaArtikala.setTextFilterEnabled(true);

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

    @Override
    protected void onResume() {
        super.onResume();

        napuniListu(idKategorije);
    }

    private void napuniListu(Integer idKategorije){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> artikli = databaseAccess.dohvatiArtikle(idKategorije);
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, artikli);
        this.listaArtikala.setAdapter(adapter);
    }

    public Integer dohvatiIdKategorije(String kategorija){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Integer idKategorije = databaseAccess.dohvatiBroj("SELECT id FROM KATEGORIJA WHERE naziv='" + kategorija + "'");
        databaseAccess.close();
        return idKategorije;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(this);

        return  true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty((s))) {
            listaArtikala.clearTextFilter();
        }
        else {
            listaArtikala.setFilterText(s.toString());
        }
        return  true;
    }


}
