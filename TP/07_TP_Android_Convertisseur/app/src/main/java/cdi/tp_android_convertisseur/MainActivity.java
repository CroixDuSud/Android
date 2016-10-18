package cdi.tp_android_convertisseur;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<String> listeDevises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listeDevises = Convert.getDevises();

        chargerSpinner(R.id.spinnerDevArr);
        chargerSpinner(R.id.spinnerDevDep);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (item.getItemId()) {
            case R.id.action_apropos:
                Intent intent = new Intent(getApplicationContext(), AProposActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_langage:
                Intent changerLangue = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(changerLangue);
                return true;
            case R.id.action_date:
                Intent changerDate = new Intent(Settings.ACTION_DATE_SETTINGS);
                startActivity(changerDate);
                return true;
            case R.id.action_display:
                Intent changerAffichage = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                startActivity(changerAffichage);
                return true;
            case R.id.action_quitter:
                onClickQuitter(null);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Méthode appelée sur l'évènement OnClick du bouton "buttonConv"
    public void onClickConvertir(View v){
        // Si champs non valides, affichage du message d'anomalie
        String message = validation();
        if (!message.equals("ok")){
            toast(message);
        } else {
            //toast("Montant : " + doConversion());
            // Création de l'Intent permettant d'afficher l'activité Resultat
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);

            // récupération des valeurs
            String source = ((Spinner)findViewById(R.id.spinnerDevDep)).getSelectedItem().toString();
            String cible = ((Spinner)findViewById(R.id.spinnerDevArr)).getSelectedItem().toString();
            double montant = Double.parseDouble(((EditText)findViewById(R.id.editTextMont)).getText().toString());

            // On affecte à l'Intent les données à passer
            intent.putExtra("devDep", source);
            intent.putExtra("devArr", cible);
            intent.putExtra("montant", montant);

            // Démarrage de l'activité Resultat
            //startActivity(intent);
            startActivityForResult(intent, 1);

        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if(resultCode == RESULT_OK){
                toast("le résultat : " + data.getDoubleExtra("resultat", 0.0));
            }
        }
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

        String message = "ok";
        // Devise départ, il faut récupérer la valeur sélectionnée du spinner spiDevDep
        //
        // Création d'une référence sur le spinner de l'activité
        Spinner spiDevDep = (Spinner)findViewById(R.id.spinnerDevDep);

        // Récupération de la devise sous forme de chaine de caractères
        String strDevDep = spiDevDep.getSelectedItem().toString();

        // Contrôle que la chaine n'est pas vide
        if (strDevDep.equals("")){
            message = "Sélectionnez la devise de départ !";

            // Devise d'arrivée, la même chose en une ligne
        } else if (((Spinner)findViewById(R.id.spinnerDevArr)).getSelectedItem().toString().equals("")) {
            message = "Sélectionnez la devise d'arrivée !";

            // Vérification du montant non-nul
        } else if (((EditText)findViewById(R.id.editTextMont)).getText().toString().equals("")) {
            message = "Saisissez un montant !";

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
/*
    private double doConversion(){

        String source = ((Spinner)findViewById(R.id.spinnerDevDep)).getSelectedItem().toString();
        String cible = ((Spinner)findViewById(R.id.spinnerDevArr)).getSelectedItem().toString();
        double montant = Double.parseDouble(((EditText)findViewById(R.id.editTextMont)).getText().toString());

        return Convert.convertir(source, cible, montant);

    }
*/

    public void chargerSpinner(int idView)
    {
        Spinner spinner = (Spinner) findViewById(idView);

        // Création d'un adapter alimenté par une liste de String, qui sera la liste des devises

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listeDevises);
        // On assigne l'adapter au spinner
        spinner.setAdapter(adapter);
    }



    public void toast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

}
