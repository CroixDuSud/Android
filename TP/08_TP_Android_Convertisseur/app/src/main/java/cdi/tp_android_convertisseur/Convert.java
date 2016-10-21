package cdi.tp_android_convertisseur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
     * @param String : représentant la devise source
     * @param String : représentant la devise cible
     * @param Double : montant à convertir
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
    public static ArrayList<String> getDevises() {

        ArrayList<String> devises = new ArrayList<String>();

        //Map<String, Double> conversionTable = Convert.getConversionTable();

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
