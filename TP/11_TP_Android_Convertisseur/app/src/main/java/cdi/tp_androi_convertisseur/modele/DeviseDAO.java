package cdi.tp_androi_convertisseur.modele;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Bonneau on 02/06/2015.
 */
public class DeviseDAO {

    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "conv_devises.db";

    public static final String TABLE_DEVISES = "devises";

    public static final String COL_MONNAIE = "monnaie";
    public static final String COL_TAUX = "taux";
    public static final int NUM_COL_MONNAIE = 0;
    public static final int NUM_COL_TAUX = 1;

    private SQLiteDatabase bdd;

    private DeviseSQLite maBaseSQLite;

    // Constructor
    public DeviseDAO(Context context) {
        // Créer la BDD et sa table
        maBaseSQLite = new DeviseSQLite(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        // Ouvrir la BDD en écriture
        // 	 (Pour les besoins de l'application, nous pourrions appeler la méthode getReadableDatabase())
        bdd = maBaseSQLite.getWritableDatabase();
    }

    public void close(){
        // Fermer l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }


    // Requêtes LMD (SELECT, INSERT, UPDATE, DELETE, ...)

    /**
     * @param devise : la devise à ajouter à la base
     */
    public long insertDevise(Devise devise){
        // Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        // on lui ajoute une valeur associée à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_MONNAIE, devise.getMonnaie());
        values.put(COL_TAUX, devise.getTaux());
        // on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_DEVISES, null, values);
    }

    /**
     * @param strDevise : le nom de la devise à supprimer
     */
    public int deleteDevise(String strDevise) {
        // Suppression d'une devise de la BDD grâce à l'ID
        return bdd.delete(TABLE_DEVISES, COL_MONNAIE + " = " + strDevise, null);
    }

    /**
     * @param devise : la devise modifiée
     */
    public void updateDevise(Devise devise) {
        ContentValues value = new ContentValues();
        value.put(COL_TAUX, devise.getTaux());
        bdd.update(TABLE_DEVISES, value, COL_MONNAIE  + " = " + devise.getMonnaie(), null);
    }
    /**
     * @param strDevise : le nom de la devise à récupérer
     */
    public Devise selectDevise(String strDevise) {
        // Récupère dans un Cursor les valeurs correspondant à la devise contenue dans la BDD
        // Cursor query (String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);
        Cursor c = bdd.query(TABLE_DEVISES, new String[] {COL_MONNAIE, COL_TAUX}, COL_MONNAIE + " LIKE \"" + strDevise +"\"", null, null, null, null);
        Devise devise = cursorToDevise(c);
        return devise;
    }

    //Cette méthode permet de convertir un cursor en une instance de Monnaie
    private Devise cursorToDevise(Cursor c){
        // Si aucun élément n'a été retourné dans la requête, on renvoie null
        if (c.getCount() == 0)
            return null;

        // Sinon on se place sur le premier élément
        c.moveToFirst();
        // On crée une Monnaie
        Devise devise = new Devise(c.getString(NUM_COL_MONNAIE), c.getInt(NUM_COL_TAUX));
        // puis on ferme le cursor
        c.close();

        // On retourne la monnaie
        return devise;
    }


    public Cursor getAll() {
        String sql = "SELECT " + COL_MONNAIE + " ," + COL_TAUX + " FROM " + TABLE_DEVISES + " ORDER BY " + COL_MONNAIE;
        return bdd.rawQuery(sql, null);
    }

}
