package cdi.tp_android_convertisseur;

import android.content.Context;
import android.database.Cursor;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cdi.tp_android_convertisseur.modele.DeviseDAO;


/**
 * Created by Bonneau on 19/05/2015.
 */
public class Convert {

    private static Map<String, Double> conversionTable = new HashMap<String, Double>();

    // static fonctionne comme un constructeur dans une classe static
/*    static
    {
        conversionTable.put("Livre", Double.valueOf(0.6404));
        conversionTable.put("Euro", Double.valueOf(0.7697));
        conversionTable.put("Dirham", Double.valueOf(8.5656));
        conversionTable.put("Yen", Double.valueOf(76.6908));
        conversionTable.put("Franc", Double.valueOf(5.0317));
        conversionTable.put("Dollar", Double.valueOf(1.0));
    }
*/
    /**
     * Retourne un Double correspondant au <b>montant</b> en devise <b>source</b> converti en devise <b>cible</b>
     * @param source String : représentant la devise source
     * @param cible String : représentant la devise cible
     * @param montant Double : montant à convertir
     * @return Double : le montant en devise cible
     */
    public static double convertir(String source, String cible, double montant) {
        double tauxSource = conversionTable.get(source);
        double tauxCible = conversionTable.get(cible);
        double tauxConversion = tauxCible/tauxSource;
        return (montant * tauxConversion) ;
    }

    /**
     * Chargement de la liste des devises présente dans la classe métier Convert
     */
    public static ArrayList<String> getDevisesSQlite(Context context) {

        //ArrayList<String> devises = new ArrayList<String>();

        //Création d'une instance de ma classe DeviseDAO
        DeviseDAO deviseDao = new DeviseDAO(context);

        //On ouvre la base de données pour écrire dedans
        deviseDao.open();

        //On récupère la liste des devises présentes dans la table DEVISES
        Cursor c = deviseDao.getAll();

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String monnaie = c.getString(c.getColumnIndex(DeviseDAO.COL_MONNAIE));
                    double taux = c.getDouble(c.getColumnIndex(DeviseDAO.COL_TAUX));
                    conversionTable.put(monnaie, taux);
                } while (c.moveToNext());
            }
        }

        c.close();
        deviseDao.close();

        //for (Iterator<String> it = conversionTable.keySet().iterator(); it.hasNext();){
        //    devises.add((String)it.next());
        //}
        return getDevises();
    }

    public static Map<String, Double> getDevisesREST(String adrServeur, String req) throws IOException {

        Map<String, Double> devises = new HashMap<String, Double>();

        InputStream is = null;
        int len = 500;

        try {
            //HttpClient httpClient = new DefaultHttpClient();
            //String url = adrServeur + "?" + req;
            //HttpGet httpGet = new HttpGet(url);
            //HttpResponse response = null;
            //response = httpClient.execute(httpGet);
            //HttpEntity entity = response.getEntity();
            //is = entity.getContent();

            URL url = new URL(adrServeur + "?" + req);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //    conn.setReadTimeout(10000 /* milliseconds */);
            //    conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");

            // test du code retour de la requête http : juste pour info, non testeée dans la suite
            int codeResponse = conn.getResponseCode();
            Log.d("REST_TAG", "The response is: " + codeResponse);

            // récupération de la réponse sous forme InputStream
            is = conn.getInputStream();

            // Converssion de l'InputStream en une string
            String jsonString = readIt(is, len);
            Log.d("REST_TAG", "String Json : " + jsonString);
            // { "devises": [{"monnaie":"Dirham","taux":"8.5656"},{"monnaie":"Dollars CA","taux":"1.1"}]}

            // utilisation de l'api Json : converssion en objet Json
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray array = new JSONArray(jsonObject.getString("devises"));
            Log.d("REST_TAG", "Longueur array json : " + array.length());

            for (int i = 0; i < array.length(); i++) {
                // On récupère un objet JSON du tableau
                JSONObject dev = new JSONObject(array.getString(i));
                // On fait le lien devise - Objet JSON
                String monnaie = dev.getString("monnaie");
                Double taux = dev.getDouble("taux");
                devises.put(monnaie, taux);
            }

        } catch (JSONException e){
            Log.v("REST_TAG", "Erreur : " + e.getStackTrace());

        } finally {
            if (is != null) {
                is.close();
            }
        }
        return devises;

    }

    // Reads an InputStream and converts it to a String.
    private static String readIt(InputStream stream, int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    public static ArrayList<String> getDevises() {
        ArrayList<String> devises = new ArrayList<String>();

        for (Iterator<String> it = conversionTable.keySet().iterator(); it.hasNext();){
            devises.add((String)it.next());
        }
        return devises;

    }

        /**
         * Accesseur du tableau associatif des devises
         * @return une référence sur la table des devises
         */
    public static Map<String, Double> getConversionTable() {
        return conversionTable;
    }

    public static void setConversionTable(Map<String, Double> convTab) {
        conversionTable = convTab;
    }

}
