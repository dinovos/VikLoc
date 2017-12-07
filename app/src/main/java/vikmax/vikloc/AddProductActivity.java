package vikmax.vikloc;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;

public class AddProductActivity extends AppCompatActivity {

    private Button spremi;
    private TextInputEditText naziv_artikla;
    private TextInputEditText opis_artikla;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_addproduct);

        spremi = (Button) findViewById(R.id.button_spremiartikl);
        naziv_artikla = (TextInputEditText) findViewById(R.id.textInput_ProductName);
        opis_artikla = (TextInputEditText) findViewById(R.id.textInput_ProductDescription);

    }
}
