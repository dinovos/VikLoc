package vikmax.vikloc;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dino on 3.1.2018..
 */

public class DetailsActivity extends AppCompatActivity {

    private String nazivArtikla;

    private TextView adresaSkladistaText;
    private TextView opisSkladistaText;
    private TextView nazivSkladistaText;
    private TextView oznakaSkladistaText;
    private TextView nazivArtiklaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_details);

        Bundle dohvaceniArtikl = getIntent().getExtras();
        nazivArtikla = dohvaceniArtikl.getString("artikl");

        Integer brojSkladista = provjeriArtikl(nazivArtikla); //provjera da li artikl postoji u tablici POZICIJA


        if(dohvaceniArtikl != null && brojSkladista != 0){

            nazivArtiklaText = findViewById(R.id.textViewDetaljiArtikla);
            nazivArtiklaText.setText(nazivArtikla);

            napuniListu(nazivArtikla);

            adresaSkladistaText = findViewById(R.id.textAdresaSkladista);
            adresaSkladistaText.setText(adresaSkladista);
            opisSkladistaText = findViewById(R.id.textOpisSkladista);
            opisSkladistaText.setText(opisSkladista);
            nazivSkladistaText = findViewById(R.id.textNazivSkladista);
            nazivSkladistaText.setText(nazivSkladista);
            oznakaSkladistaText = findViewById(R.id.textOznakaSkladista);
            oznakaSkladistaText.setText(oznakaSkladista);

        }
        else
        {
            nazivArtiklaText = findViewById(R.id.textViewDetaljiArtikla);
            nazivArtiklaText.setText(nazivArtikla);

            adresaSkladistaText = findViewById(R.id.textAdresaSkladista);
            adresaSkladistaText.setText("Nepoznato");
            opisSkladistaText = findViewById(R.id.textOpisSkladista);
            opisSkladistaText.setText("Nepoznato");
            nazivSkladistaText = findViewById(R.id.textNazivSkladista);
            nazivSkladistaText.setText("Nepoznato");
            oznakaSkladistaText = findViewById(R.id.textOznakaSkladista);
            oznakaSkladistaText.setText("Nepoznato");
        }


    }

    private String adresaSkladista;
    private String opisSkladista;
    private String oznakaSkladista;
    private String nazivSkladista;


    private void napuniListu(String nazivArtikla){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        ArrayList<String> detalji = databaseAccess.dohvatiDetaljeAtrikla(nazivArtikla);
        databaseAccess.close();

        adresaSkladista = detalji.get(0);
        opisSkladista = detalji.get(1);
        nazivSkladista = detalji.get(2);
        oznakaSkladista = detalji.get(3);
    }

    private Integer provjeriArtikl(String nazivArtikla){
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        Integer broj = databaseAccess.dohvatiBroj("SELECT id FROM ARTIKL WHERE naziv = '"+nazivArtikla+"' ");
        Integer pozicija = databaseAccess.dohvatiBroj("SELECT SKLADISTE FROM POZICIJA WHERE ARTIKL = '"+broj+"'");
        databaseAccess.close();
        return pozicija;
    }


}
