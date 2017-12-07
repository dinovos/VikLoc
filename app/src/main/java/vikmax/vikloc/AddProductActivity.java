package vikmax.vikloc;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {

    private Button spremi;
    private TextInputEditText naziv_artikla;
    private TextInputEditText opis_artikla;
    private Integer idKorisnika;
    private Integer kategorija;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_addproduct);

        spremi = (Button) findViewById(R.id.button_spremiartikl);
        naziv_artikla = (TextInputEditText) findViewById(R.id.textInput_ProductName);
        opis_artikla = (TextInputEditText) findViewById(R.id.textInput_ProductDescription);

        final Bundle dohvaceno = getIntent().getExtras();
        kategorija = dohvaceno.getInt("kategorija");
        idKorisnika = dohvaceno.getInt("izradio");
        unesiArtikl();
    }

    public void unesiArtikl(){
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        spremi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseAccess.open();
                        boolean uneseno = databaseAccess.unesiArtikl(naziv_artikla.getText().toString(), opis_artikla.getText().toString(), kategorija, idKorisnika);
                        databaseAccess.close();
                        if(uneseno = true)
                            Toast.makeText(AddProductActivity.this, "Artikl dodan", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddProductActivity.this, "Artikl nije dodan", Toast.LENGTH_LONG).show();
                        naziv_artikla.getText().clear();
                        opis_artikla.getText().clear();
                        naziv_artikla.requestFocus();
                    }
                }
        );
    }
}
