package cdi.tp_android_convertisseur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
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

        String message = getString(R.string.message_ok);
        // Devise départ, il faut récupérer la valeur sélectionnée du spinner spiDevDep
        //
        // Création d'une référence sur le spinner de l'activité
        Spinner spiDevDep = (Spinner)findViewById(R.id.spinnerDevDep);

        // Récupération de la devise sous forme de chaine de caractères
        String strDevDep = spiDevDep.getSelectedItem().toString();

        // Contrôle que la chaine n'est pas vide
        if (strDevDep.equals("")){
            message = getString(R.string.message_err_dev_dep);

            // Devise d'arrivée, la même chose en une ligne
        } else if (((Spinner)findViewById(R.id.spinnerDevArr)).getSelectedItem().toString().equals("")) {
            message = getString(R.string.message_err_dev_arr);

            // Vérification du montant non-nul
        } else if (((EditText)findViewById(R.id.editTextMont)).getText().toString().equals("")) {
            message = getString(R.string.message_err_mont);

            // Vérification montant numérique
        } else {

            try {
                Double.valueOf(((EditText)findViewById(R.id.editTextMont)).getText().toString());
            } catch (NumberFormatException e) {

                message = "Saisissez un montant numérique !";
            }
        }

        // Pas d'anomalies détectées, validate() renvoie vrai
        return message;
    }

    private double doConversion(){

        String source = ((Spinner)findViewById(R.id.spinnerDevDep)).getSelectedItem().toString();
        String cible = ((Spinner)findViewById(R.id.spinnerDevArr)).getSelectedItem().toString();
        double montant = Double.parseDouble(((EditText)findViewById(R.id.editTextMont)).getText().toString());

        return Convert.convertir(source, cible, montant);

    }

    public void toast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

}
