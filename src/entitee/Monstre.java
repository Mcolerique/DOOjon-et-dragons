package entitee;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import equipement.armure.Poids;
import interactionUtilisateur.*;

import java.util.ArrayList;
import java.lang.*;

/**
 * Représente une entité Monstre dans le jeu.
 */
public class Monstre extends Entitee {

    private String m_espece;
    private int m_numMonstre;

    /**
     * Construit un Monstre avec des valeurs par défaut.
     */
    public Monstre() {
        super();
        m_numMonstre = 0;
        m_espece = "MissingNo.";
        m_type = TypeEntitee.MONSTRE;
    }

    /**
     * Construit un Monstre avec des attributs spécifiés.
     *
     * @param numMonstre le numéro unique du monstre
     * @param espece l'espèce du monstre
     * @param stats les statistiques du monstre
     * @param attaqueEtArmure l'équipement du monstre, incluant l'attaque et l'armure
     */
    public Monstre(int numMonstre, String espece, int[] stats, Equipement[] attaqueEtArmure) {
        super(stats);
        m_numMonstre = numMonstre;
        m_espece = espece;
        m_arme = (Arme) attaqueEtArmure[0];
        m_armure = (Armure) attaqueEtArmure[1];
        m_type = TypeEntitee.MONSTRE;
    }

    /**
     * Obtient l'appellation du monstre.
     *
     * @return l'appellation combinant l'espèce et le numéro
     */
    public String getAppellation() {
        return (m_espece + m_numMonstre);
    }

    /**
     * Obtient le symbole représentant le monstre.
     *
     * @return le symbole combinant les deux premières lettres de l'espèce et le numéro du monstre
     */
    public String getSymbole() {
        return m_espece.substring(0, 2) + m_numMonstre;
    }

    /**
     * Obtient le nom de l'espèce du monstre.
     *
     * @return le nom de l'espèce
     */
    public String getNom() {
        return m_espece;
    }

    /**
     * Obtient la description du monstre.
     *
     * @return la description qui est le numéro du monstre
     */
    public String getDescription() {
        return "" + m_numMonstre;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du monstre.
     *
     * @return la représentation sous forme de chaîne de caractères
     */
    public String toString() {
        return m_espece + " n°" + m_numMonstre;
    }

    /**
     * Crée une liste de monstres basée sur l'entrée de l'utilisateur.
     *
     * @return la liste des monstres créés
     */
    public static ArrayList<Monstre> creerMonstres() {
        Affichage.affiche("Combien de monstres souhaitez vous créer ?");
        int nbrMonstres = Scanner.demandeInt();
        if (nbrMonstres <= 0) {
            Affichage.affiche("Arrêt de la création de monstre. Passage aux monstres par défaut.");
            return null;
        }
        ArrayList<Monstre> monstres = new ArrayList<>();
        for (int i = 0; i < nbrMonstres; i++) {
            Affichage.affiche("Monstre n°" + (i + 1));
            Monstre monstre = creerMonstre();
            if (!monstres.isEmpty()) {
                monstre.m_numMonstre = compteNumMonstre(monstres, monstre);
            }
            monstres.add(monstre);
        }
        return monstres;
    }

    /**
     * Crée un seul monstre basé sur l'entrée de l'utilisateur.
     *
     * @return le monstre créé
     */
    public static Monstre creerMonstre() {
        Monstre monstre = new Monstre();
        Affichage.affiche("Nom d'espèce de votre monstre ?");
        monstre.m_espece = Scanner.demandeString();
        Affichage.affiche("Points de vie ?");
        monstre.m_stats[0] = Scanner.demandeInt();
        Affichage.affiche("Vitesse ?");
        monstre.m_stats[1] = Scanner.demandeInt();
        Affichage.affiche("Force ?");
        monstre.m_stats[2] = Scanner.demandeInt();
        Affichage.affiche("Dextérité ?");
        monstre.m_stats[3] = Scanner.demandeInt();
        Affichage.affiche("Points de vie ?");
        monstre.m_stats[4] = Scanner.demandeInt();
        Equipement atqArmure[] = creerAtqDefMonstre(monstre.m_espece);
        monstre.m_arme = (Arme) atqArmure[0];
        monstre.m_armure = (Armure) atqArmure[1];

        return monstre;
    }

    /**
     * Crée l'équipement d'attaque et de défense pour un monstre.
     *
     * @param nomEspece le nom de l'espèce du monstre
     * @return le tableau contenant l'équipement d'attaque et d'armure
     */
    public static Equipement[] creerAtqDefMonstre(String nomEspece) {
        Equipement[] atqDefMonstre = new Equipement[2];
        int portee;
        do {
            Affichage.affiche("Portée de l'attaque (si 1, corps à corps. si +, à distance). Doit être supérieur à 0");
            portee = Scanner.demandeInt();
        } while (portee < 1);

        String dgtsFaceAtq[] = new String[2];
        String tempDgtsFaceAtq;
        do {
            Affichage.affiche("Indiquez le lancer de dé attendu (au format xdy)");
            tempDgtsFaceAtq = Scanner.demandeString();
            dgtsFaceAtq = tempDgtsFaceAtq.split("d");
            if (!tempDgtsFaceAtq.contains("d")) Affichage.affiche("Veuillez respecter le format xdy");
        } while (!tempDgtsFaceAtq.contains("d"));

        atqDefMonstre[0] = new Arme("attaque" + nomEspece, Integer.valueOf(dgtsFaceAtq[0]).intValue(),
                Integer.valueOf(dgtsFaceAtq[1]).intValue(), portee);

        Affichage.affiche("Classe d'armure de votre monstre");
        int cArmure = Scanner.demandeInt();

        atqDefMonstre[1] = new Armure("classeArume" + nomEspece, cArmure, Poids.LEGERE);

        return atqDefMonstre;
    }

    /**
     * Compte le nombre de monstres de la même espèce dans une liste.
     *
     * @param listeMonstres la liste des monstres
     * @param monstre le monstre à comparer
     * @return le nombre de monstres de la même espèce
     */
    public static int compteNumMonstre(ArrayList<Monstre> listeMonstres, Monstre monstre) {
        int compteNumMonstre = -1;
        for (Monstre m : listeMonstres) {
            if (m.m_espece == monstre.m_espece) {
                compteNumMonstre++;
            }
        }
        return compteNumMonstre;
    }

    /**
     * Utilise un monstre de la liste et le retire de la liste.
     *
     * @param monstre l'index du monstre à utiliser
     * @param monstres la liste des monstres
     * @return le monstre qui a été utilisé
     */
    public static Monstre utiliserMonstre(int monstre, ArrayList<Monstre> monstres) {
        Monstre returnMonstre = monstres.get(monstre);
        monstres.remove(monstre);
        System.out.println("Monstre ajouté.\n");
        return returnMonstre;
    }
}