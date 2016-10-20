package cdi.tp_androi_convertisseur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> devises = null;
    private String devDep ;
    private String devArr;
    private String strMontant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // récupération des devises
        devises = Convert.getDevises(this);

        // Récupérer le fichier de préférences
        SharedPreferences mesPreferences = getSharedPreferences("ConvPref", MODE_PRIVATE);

        // Récupérer les devises (valeurs des clés) ou des devises par défaut
        devDep = mesPreferences.getString("devDepDef", "Dollar");
        devArr = mesPreferences.getString("devArrDef", "Euro");
        strMontant = mesPreferences.getString("montant", "");

        // Alimenter les Spinner avec une devise par défaut
        chargeSpinner(R.id.spinnerDevDep,devDep);

        chargeSpinner(R.id.spinnerDevArr, devArr);

        EditText edtMontant = (EditText)findViewById(R.id.editTextMont);
        edtMontant.setText(strMontant);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Récupérer le fichier de préférences
        SharedPreferences mesPreferences = getSharedPreferences("ConvPref", MODE_PRIVATE);

        // Récupérer un objet SharedPreferences.Editor avec la méthodes edit() du SharedPreferences
        SharedPreferences.Editor mesPreferencesEditor = mesPreferences.edit();

        // Assigner les clés/valeurs
        mesPreferencesEditor.putString("devDepDef", devDep);
        mesPreferencesEditor.putString("devArrDef", devArr);
        mesPreferencesEditor.putString("montant", strMontant);

        // Valider les modif
        mesPreferencesEditor.commit();

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
            devDep = ((Spinner)findViewById(R.id.spinnerDevDep)).getSelectedItem().toString();
            devArr = ((Spinner)findViewById(R.id.spinnerDevArr)).getSelectedItem().toString();
            strMontant = ((EditText)findViewById(R.id.editTextMont)).getText().toString();

            // On affecte à l'Intent les données à passer
            intent.putExtra("devDep", devDep);
            intent.putExtra("devArr", devArr);
            intent.putExtra("montant", Double.parseDouble(strMontant));

            // Démarrage de l'activité Resultat
            startActivity(intent);

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

    /**
     * Créer un adapter qui reçoit la liste des devise
     * Et l'associe au spinner dont l'id est passé en argument
     */
    private void chargeSpinner(int idView, String devDefaut) {

        final Spinner spinner = (Spinner)findViewById(idView);

        //Créer un adapter avec une liste de String
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, devises);

        //Définir le style des éléments de l'adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Assigner l'adapter au spinner
        spinner.setAdapter(adapter);

        Log.i("COUNT", Integer.toString(spinner.getCount()));

        // Récupérer la position de la devise dans l'ArrayList de l'Adapter
        int intDevDefaut = adapter.getPosition(devDefaut);

        // Bornage du paramètre
        if (intDevDefaut < 0 | intDevDefaut >= spinner.getCount() ) intDevDefaut = 0;

        // Affectation de la valeur par défaut
        spinner.setSelection(intDevDefaut);
    }
    public void toast(String message){
        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }

}
