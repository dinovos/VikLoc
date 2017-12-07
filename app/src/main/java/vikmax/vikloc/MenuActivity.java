package vikmax.vikloc;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

public class MenuActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

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

        listaKategorija.setTextFilterEnabled(true);

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
            listaKategorija.clearTextFilter();
        }
        else {
            listaKategorija.setFilterText(s.toString());
        }
        return  true;
    }

}
