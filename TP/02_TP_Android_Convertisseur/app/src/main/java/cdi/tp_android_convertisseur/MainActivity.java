package cdi.tp_android_convertisseur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Méthode appelée sur l'évènement OnClick du bouton "buttonConv"
    public void onClickConvertir(View v){
        // Si champs non valides, affichage du message d'anomalie
        String message = validation();
        if (!message.equals("ok")){
            toast(message);
        } else {
            toast("Montant : " + doConversion());
        };
    }

    // Méthode appelée sur l'évènement OnClick du bouton "buttonQuit"
    public void onClickQuitter(View v){
        toast("Fin de l'application");
        finish();
    }

    /**
     * Validation des champs de saisie
     * @return : message d'erreur, "ok" si pas d'erreur
     */
    public String validation(){
         // Vérification montant numérique
            try {
                Double.valueOf(((EditText)findViewById(R.id.editTextMont)).getText().toString());
            } catch (NumberFormatException e) {

                String message = "Saisissez un montant numérique !";
                return message;
            }

        // Pas d'anomalies détectées, validate() renvoie vrai
        return "ok";
    }

    private double doConversion(){

        double montant = Double.parseDouble(((EditText)findViewById(R.id.editTextMont)).getText().toString());

        return Convert.convertir("Franc", "Euro", montant);

    }

    public void toast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

}
