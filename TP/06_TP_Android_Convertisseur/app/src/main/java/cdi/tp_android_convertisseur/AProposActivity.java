package cdi.tp_android_convertisseur;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AProposActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apropos);
    }

    // Méthode appelée sur l'évènement OnClick du bouton "buttonQuit"
    public void onClickQuitter(View v){

        finish();
    }

}
