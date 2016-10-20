package cdi.tp_androi_convertisseur;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cdi.tp_androi_convertisseur.modele.DeviseDAO;

/**
 * Created by Bonneau on 19/05/2015.
 */
public class Convert {

    private static Map<String, Double> conversionTable = new HashMap<String, Double>();

    // static fonctionne comme un constructeur dans une classe static
    static
    {
        conversionTable.put("Livre", Double.valueOf(0.6404));
        conversionTable.put("Euro", Double.valueOf(0.7697));
        conversionTable.put("Dirham", Double.valueOf(8.5656));
        conversionTable.put("Yen", Double.valueOf(76.6908));
        conversionTable.put("Franc", Double.valueOf(5.0317));
        conversionTable.put("Dollar", Double.valueOf(1.0));
    }

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
    public static ArrayList<String> getDevises(Context context) {

        ArrayList<String> devises = new ArrayList<String>();

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

}
