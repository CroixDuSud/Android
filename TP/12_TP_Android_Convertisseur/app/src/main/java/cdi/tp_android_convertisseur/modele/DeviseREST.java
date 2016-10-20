package cdi.tp_android_convertisseur.modele;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import cdi.tp_android_convertisseur.Convert;
import cdi.tp_android_convertisseur.MainActivity;
import cdi.tp_android_convertisseur.R;

/**
 * Created by Bonneau on 19/10/2016.
 */

public class DeviseREST extends AsyncTask<String, Void, Map<String, Double>> {

    private ProgressDialog progressDialog;
    private Context context;
    private MainActivity mainActivity;

    // Constructeur utilisé avec l'activité MainActivity
    public DeviseREST(Context context, MainActivity activity) {
        super();
        this.mainActivity = activity;
        this.context = context;
        Log.v("AsyncTask", "Contructeur MainAsyncTask ...");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        Log.v("AsyncTask", "onPreExecute ...");

        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage("Connexion au serveur,\nPatientez ...");
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setCancelable(true);

        this.progressDialog.show();
    }

    @Override
    protected Map<String, Double> doInBackground(String... params) {
        Map<String, Double> listeDevises = null;
        Log.v("REST_TAG", "doInBackground params : " + params[0] + " " + params[1]);
        try {
            listeDevises = Convert.getDevisesREST(params[0], params[1]);
        } catch (IOException e) {
            Log.v("REST_TAG", "Erreur : " + e.getStackTrace());
        }

        return listeDevises;
    }

    @Override
    protected void onPostExecute(Map<String, Double> listeDevises) {
        super.onPostExecute(listeDevises);

        Log.v("AsyncTask", "onPostExecute ..." + listeDevises);

        Convert.setConversionTable(listeDevises);

        mainActivity.setDevises(Convert.getDevises());

        // Alimenter les Spinner avec une devise par défaut
        mainActivity.chargeSpinner(R.id.spinnerDevDep, mainActivity.getStrDevDep());

        mainActivity.chargeSpinner(R.id.spinnerDevArr, mainActivity.getStrDevArr());

        // arrêt du progressDialog
        this.progressDialog.dismiss();

    }
}
