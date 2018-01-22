package vikmax.vikloc;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import dinovos.database.DatabaseAccess;

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
        osvjeziListu();

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

        listaKategorija.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                final String nazivKategorije = listaKategorija.getItemAtPosition(i).toString();
                final Integer idKategorije = dohvatiIdKategorije(nazivKategorije);

                final AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                builder.setTitle("Brisanje odabrane kategorije?")
                        .setCancelable(false)
                        .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        })
                        .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                int broj = brojArtikalaUKategoriji(idKategorije);
                                if (broj != 0)
                                {
                                    final AlertDialog.Builder builder1 = new AlertDialog.Builder(MenuActivity.this);
                                    builder1.setTitle("Obavijest")
                                            .setCancelable(true)
                                            .setMessage("Nije moguće obrisati! Kategorija '"+ nazivKategorije +"' sadrži artikle!");
                                    AlertDialog dialog1 = builder1.create();
                                    dialog1.show();
                                }
                                else
                                {
                                    brisanjeKategorije(nazivKategorije);
                                    Toast.makeText(MenuActivity.this, "Kategorija obrisana", Toast.LENGTH_SHORT).show();
                                    osvjeziListu();
                                }
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();

                return true;

            }


        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        osvjeziListu();
    }

    private void osvjeziListu(){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        List<String> kategorije = databaseAccess.dohvatiKategorije();
        databaseAccess.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, kategorije);
        this.listaKategorija.setAdapter(adapter);
    }

    private int brojArtikalaUKategoriji(int idKategorije) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Integer brojArtikalaUKategoriji = databaseAccess.dohvatiBroj("SELECT COUNT(kategorija) FROM ARTIKL WHERE kategorija = '" + idKategorije + "'");
        databaseAccess.close();
        return brojArtikalaUKategoriji;
    }

    private void brisanjeKategorije(String nazivKategorije) {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        databaseAccess.obrisiKategoriju(nazivKategorije);
        databaseAccess.close();
    }

    private int dohvatiIdKategorije(String Kategorija)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Integer idKategorije = databaseAccess.dohvatiBroj("SELECT id FROM KATEGORIJA WHERE naziv = '" + Kategorija + "'");
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
            listaKategorija.clearTextFilter();
        }
        else {
            listaKategorija.setFilterText(s.toString());
        }
        return  true;
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder  = new AlertDialog.Builder(this);
        builder.setMessage("Odjava iz aplikacije?")
                .setCancelable(false)
                .setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        MenuActivity.this.onSuperBackPressed();
                    }
                })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    RelativeLayout relativeLayout;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        relativeLayout = (RelativeLayout) findViewById(R.id.primary_relativeLayout);

        if(id == R.id.action_settings){
            return true;
        }
        else{
            return false;
        }
    }

    public void onSuperBackPressed(){
        super.onBackPressed();
    }

}
