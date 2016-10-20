package cdi.tp_androi_convertisseur;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //On récupère l'Intent reçu
        Intent intent = getIntent();

        // On récupère les paramètres passés
//        String strDevDep = intent.getExtras().getString("devDep");
        String strDevDep = intent.getStringExtra("devDep");
        String strDevArr = intent.getExtras().getString("devArr");
        Double montant = intent.getExtras().getDouble("montant");

        // Affichage du résultat
        NumberFormat nf = NumberFormat.getInstance(); //Instanciation d'un Objet de type 'format nombre' paramètré avec les valeurs locales
        nf.setMinimumFractionDigits(2); // pour avoir 2 chiffres après la virgule

        // Conversion du montant en devise d'arrivée et application du format (nf)
        String strResult = nf.format(Convert.convertir(strDevDep, strDevArr, montant));
        String strMontant = nf.format(montant);

        TextView textViewResultat = (TextView) findViewById(R.id.textViewResultat2);
        textViewResultat.setText(strMontant + " " + strDevDep + " = " + strResult + " " + strDevArr);
    }

    // appelée sur le clic du bouton retour
    public void onClicRetour(View v){
        finish();
    }
}
