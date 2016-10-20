package cdi.tp_androi_convertisseur.modele;

/**
 * Created by Bonneau on 02/06/2015.
 */
public class Devise {

    private String monnaie;
    private float taux;

    // Constructeur
    public Devise(String monnaie, float taux) {
        super();
        this.setMonnaie(monnaie);
        this.setTaux(taux);
    }

    // Getters and Setters
    public String getMonnaie() {
        return monnaie;
    }
    public void setMonnaie(String monnaie) {
        this.monnaie = monnaie;
    }
    public float getTaux() {
        return taux;
    }
    public void setTaux(float taux) {
        this.taux = taux;
    }

    // Surcharge de la méthode toString()
    public String toString(){
        return "Monnaie : " + this.monnaie + "\n  Taux : " + this.taux;
    }

}
