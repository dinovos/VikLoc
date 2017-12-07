package vikmax.vikloc;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


public class AddCategorieActivity extends AppCompatActivity {

    private Button button_spremi;
    private TextInputEditText naziv_kategorije;
    private TextInputEditText opis_kategorije;
    private Integer idKorisnika;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_addcategorie);

        button_spremi = (Button) findViewById(R.id.button_spremikategoriju);
        naziv_kategorije = (TextInputEditText) findViewById(R.id.textInput_CategorieName);
        opis_kategorije = (TextInputEditText) findViewById(R.id.textInput_CategorieDescription);

        Bundle dohvaceniId = getIntent().getExtras();
        idKorisnika = dohvaceniId.getInt("idKorisnik");

        unesiKategoriju();

    }

    public void unesiKategoriju(){
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        button_spremi.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseAccess.open();
                        boolean uneseno = databaseAccess.unesiKategoriju(naziv_kategorije.getText().toString(), opis_kategorije.getText().toString(), idKorisnika);
                        databaseAccess.close();
                        if(uneseno = true)
                            Toast.makeText(AddCategorieActivity.this, "Kategorija dodana", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddCategorieActivity.this, "Kategorija nije dodana", Toast.LENGTH_LONG).show();
                            naziv_kategorije.getText().clear();
                            opis_kategorije.getText().clear();
                            naziv_kategorije.requestFocus();
                    }
                }
        );
    }
}
