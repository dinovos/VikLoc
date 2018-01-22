package vikmax.vikloc;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import dinovos.database.DatabaseAccess;


public class LoginActivity extends AppCompatActivity {

    private Button button_login;
    private TextInputEditText user;
    private TextInputEditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

        Toast.makeText(this, "Dobrodošli", Toast.LENGTH_SHORT).show();

        button_login = (Button)findViewById(R.id.button_login);
        user = (TextInputEditText)findViewById(R.id.textInput_user);
        password = (TextInputEditText)findViewById(R.id.textInput_password);

        loginProvjera();
    }

    @Override
    public void onResume(){
        super.onResume();

        user.getText().clear();
        password.getText().clear();
        user.requestFocus();
    }

    public void loginProvjera(){
        final DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        button_login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        databaseAccess.open();
                        boolean provjera = databaseAccess.loginProvjera(user.getText().toString(), password.getText().toString());
                        databaseAccess.close();

                        EditText editTextPassword = (EditText) findViewById(R.id.textInput_password);
                        EditText editTextUsername = (EditText) findViewById(R.id.textInput_user);

                        if(provjera == false) {
                            prikaziPoruku("Greška kod prijave", "Neispravni podaci za prijavu. Molim pokušajte ponovo");

                            editTextUsername.getText().clear();
                            editTextUsername.requestFocus();
                            editTextPassword.getText().clear();

                            return;
                        }
                        databaseAccess.open();
                        Integer idKorisnik = databaseAccess.dohvatiBroj("SELECT id FROM KORISNIK WHERE korisnicko_ime='" + user.getText().toString() + "'");
                        databaseAccess.close();
                        Toast.makeText(LoginActivity.this, "Dobrodošao "+ user.getText().toString(), Toast.LENGTH_LONG).show();
                        if(v.getId() == R.id.button_login) {
                            Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                            i.putExtra("idKorisnik", idKorisnik);
                            startActivity(i);
                        }
                    }
                });
    }

    public void prikaziPoruku(String naziv, String poruka){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(naziv);
        builder.setMessage(poruka);
        builder.show();
    }

}

