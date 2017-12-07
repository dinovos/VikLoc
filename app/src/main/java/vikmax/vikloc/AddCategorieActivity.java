package vikmax.vikloc;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;


public class AddCategorieActivity extends AppCompatActivity {

    private Button button_spremi;
    private TextInputEditText naziv_kategorije;
    private TextInputEditText opis_kategorije;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_addcategorie);

        button_spremi = (Button) findViewById(R.id.button_spremikategoriju);
        naziv_kategorije = (TextInputEditText) findViewById(R.id.textInput_CategorieName);
        opis_kategorije = (TextInputEditText) findViewById(R.id.textInput_CategorieDescription);

    }


}
