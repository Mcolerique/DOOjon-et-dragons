package entitee;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import equipement.armure.Poids;
import interactionUtilisateur.Affichage;

import java.util.ArrayList;

/**
 * La classe ListeMonstres gère une liste de monstres disponibles et utilisés dans le jeu.
 */
public class ListeMonstres {

    /**
     * Liste des monstres disponibles.
     */
    private static ArrayList<Monstre> m_listeMonstres = new ArrayList<>() {{
        add(new Monstre(1, "Gobelin", new int[]{25, 15, 0, 14, 1},
                new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(2, "Gobelin", new int[]{25, 9, 0, 17, 1},
                new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Troll", new int[]{21, 0, 17, 13, 1},
                new Equipement[]{new Arme("Catapulte", 1, 4, 6), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Dragon blanc", new int[]{45, 0, 15, 12, 1},
                new Equipement[]{new Arme("Souffle flamboyant", 1, 8, 9), new Armure("Écailles", 10, Poids.LOURDE)}));
        add(new Monstre(1, "Dragon noir", new int[]{45, 12, 0, 16, 1},
                new Equipement[]{new Arme("Souffle grondant", 1, 8, 0), new Armure("Écailles", 10, Poids.LOURDE)}));
        add(new Monstre(1, "Gorille", new int[]{35, 0, 14, 14, 1},
                new Equipement[]{new Arme("Poings", 1, 6, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Archer des abysses", new int[]{25, 0, 14, 13, 1},
                new Equipement[]{new Arme("Arc d'Héraclès", 1, 8, 8), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Chevalier des abysses", new int[]{25, 13, 0, 14, 1},
                new Equipement[]{new Arme("Excalibur", 1, 8, 1), new Armure("Peau", 12, Poids.LOURDE)}));
        add(new Monstre(1, "Demogorgon", new int[]{25, 0, 19, 11, 1},
                new Equipement[]{new Arme("!!!", 1, 10, 4), new Armure("???", 7, Poids.LEGERE)}));
    }};

    /**
     * Liste des monstres utilisés.
     */
    private static ArrayList<Monstre> m_listeMonstresUtilises = new ArrayList<>() {};

    /**
     * Obtient le nombre de monstres disponibles.
     *
     * @return le nombre de monstres disponibles
     */
    public static int nbMonstresDispo() {
        return m_listeMonstres.size();
    }

    /**
     * Déplace un monstre de la liste des monstres disponibles à la liste des monstres utilisés.
     *
     * @param monstre l'index du monstre à déplacer
     */
    public static void deDispoADejaUtilise(int monstre) {
        m_listeMonstresUtilises.add(m_listeMonstres.get(monstre));
        m_listeMonstres.remove(monstre);
    }

    /**
     * Utilise un monstre et le déplace de la liste des monstres disponibles à la liste des monstres utilisés.
     *
     * @param monstre l'index du monstre à utiliser
     * @return le monstre utilisé
     */
    public static Monstre utiliserMonstre(int monstre) {
        Monstre returnMonstre = m_listeMonstres.get(monstre);
        deDispoADejaUtilise(monstre);
        System.out.println("Monstre ajouté. \nMonstres placés : \n");
        Affichage.afficherMonstre(m_listeMonstresUtilises);
        return returnMonstre;
    }

    /**
     * Utilise un monstre automatiquement et le déplace de la liste des monstres disponibles à la liste des monstres utilisés.
     *
     * @param monstre l'index du monstre à utiliser
     * @return le monstre utilisé
     */
    public static Monstre utiliserMonstreAuto(int monstre) {
        Monstre monstreAReturn = m_listeMonstres.get(monstre);
        deDispoADejaUtilise(monstre);
        return monstreAReturn;
    }

    /**
     * Obtient le nom d'un monstre disponible.
     *
     * @param monstre l'index du monstre
     * @return le nom du monstre
     */
    public static String getNomMonstre(int monstre) {
        return m_listeMonstres.get(monstre).getAppellation();
    }

    /**
     * Obtient la liste des monstres disponibles.
     *
     * @return la liste des monstres disponibles
     */
    public static ArrayList<Monstre> getListeMonstres() {
        return m_listeMonstres;
    }

    /**
     * Réinitialise l'état des monstres en déplaçant tous les monstres utilisés vers la liste des monstres disponibles.
     */
    public static void retourEtatInitialMonstres() {
        m_listeMonstres.addAll(m_listeMonstresUtilises);
        m_listeMonstresUtilises.clear();
    }
}
