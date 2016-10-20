package cdi.tp_androi_convertisseur.modele;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Bonneau on 02/06/2015.
 */
public class DeviseSQLite extends SQLiteOpenHelper {

    // Requêtes LDD (CREATE, DROP table, ...)
    public static final String CREATE_TABLE_DEVISES =
            "CREATE TABLE " + DeviseDAO.TABLE_DEVISES + " (" +
                    // DEVISE_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    DeviseDAO.COL_MONNAIE + " TEXT PRIMARY KEY, " +
                    DeviseDAO.COL_TAUX + " REAL);";

    public static final String DROP_TABLE_DEVISES = "DROP TABLE IF EXISTS " + DeviseDAO.TABLE_DEVISES + ";";

    // Requêtes de peuplement
    public static final String INSERT_LIVRE =
            "INSERT INTO " + DeviseDAO.TABLE_DEVISES + " (monnaie, taux) VALUES ('Livre',0.6405);";
    public static final String INSERT_EURO =
            "INSERT INTO " + DeviseDAO.TABLE_DEVISES + " (monnaie, taux) VALUES ('Euro',0.7697);";
    public static final String INSERT_DIRHAM =
            "INSERT INTO " + DeviseDAO.TABLE_DEVISES + " (monnaie, taux) VALUES ('Dirham',8.5656);";
    public static final String INSERT_YEN =
            "INSERT INTO " + DeviseDAO.TABLE_DEVISES + " (monnaie, taux) VALUES ('Yen',76.6908);";
    public static final String INSERT_FRANC_CFA =
            "INSERT INTO " + DeviseDAO.TABLE_DEVISES + " (monnaie, taux) VALUES ('Franc CFA',503.17);";
    public static final String INSERT_DOLLARS_US =
            "INSERT INTO " + DeviseDAO.TABLE_DEVISES + " (monnaie, taux) VALUES ('Dollar',1);";
    public static final String INSERT_FRANC =
            "INSERT INTO " + DeviseDAO.TABLE_DEVISES + " (monnaie, taux) VALUES ('Franc',5.049);";

    // Constructeur
    public DeviseSQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Méthode callBack appelée par le système si la base de données n'existe pas.
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("BASE", "onCreate() Création BDD");
        db.execSQL(CREATE_TABLE_DEVISES);

        // Peuplement de la base
        Log.i("BASE", "Peuplement de la base");
        db.execSQL(INSERT_LIVRE);  //db.insertDevise(new Devise("LIVRE", 0.6405));
        db.execSQL(INSERT_EURO);
        db.execSQL(INSERT_DIRHAM);
        db.execSQL(INSERT_YEN);
        db.execSQL(INSERT_FRANC_CFA);
        db.execSQL(INSERT_DOLLARS_US);
        db.execSQL(INSERT_FRANC);

    }

    // Méthode callBack appelée par le système si la base change de VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_DEVISES);
        onCreate(db);
    }



}
