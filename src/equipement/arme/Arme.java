package equipement.arme;

import des.Des;
import equipement.Equipement;
import equipement.TypeEquipement;
import interactionUtilisateur.Affichage;

/**
 * La classe Arme représente une arme dans le jeu.
 * Elle hérite de la classe Equipement.
 */
public class Arme extends Equipement {
    private final int m_nbDesDeg;
    private final int m_degats;
    private final int m_portee;
    private int m_bonusDegats;
    private int m_bonusAttaque;
    private final TypeCaC m_type;

    /**
     * Construit une arme par défaut.
     */
    public Arme() {
        super("poing");
        m_nbDesDeg = 1;
        m_degats = 1;
        m_portee = 1;
        m_bonusAttaque = 1;
        m_bonusDegats = 1;
        m_type = TypeCaC.COURANTE;
        m_typeEquipement = TypeEquipement.ARME;
    }

    /**
     * Construit une arme avec un nom, un nombre de dés, un nombre de faces et une portée spécifiés.
     *
     * @param nom le nom de l'arme
     * @param nbDes le nombre de dés
     * @param nbFaces le nombre de faces des dés
     * @param portee la portée de l'arme
     */
    public Arme(String nom, int nbDes, int nbFaces, int portee) {
        super(nom);
        m_nbDesDeg = nbDes;
        m_degats = nbFaces;
        m_portee = portee;
        m_bonusAttaque = 0;
        m_bonusDegats = 0;
        m_type = null;
        m_typeEquipement = TypeEquipement.ARME;
    }

    /**
     * Construit une arme avec un nom, un nombre de dés, un nombre de faces et un type spécifiés.
     *
     * @param nom le nom de l'arme
     * @param nbDes le nombre de dés
     * @param nbFaces le nombre de faces des dés
     * @param type le type de l'arme
     */
    public Arme(String nom, int nbDes, int nbFaces, TypeCaC type) {
        super(nom);
        m_nbDesDeg = nbDes;
        m_degats = nbFaces;
        m_portee = 1;
        m_bonusAttaque = 0;
        m_bonusDegats = 0;
        m_type = type;
        m_typeEquipement = TypeEquipement.ARME;
        if (m_type == TypeCaC.GUERRE) {
            m_modifStat[3] = -2;
            m_modifStat[1] = 4;
        }
    }

    /**
     * Obtient la portée de l'arme.
     *
     * @return la portée de l'arme
     */
    public int getPortee() {
        return m_portee;
    }

    /**
     * Inflige des dégâts en fonction des caractéristiques de l'arme.
     *
     * @return les dégâts infligés
     */
    public int infligerDegats() {
        int degats = 0;
        int degatsTotal = 0;
        String txt = "(";
        for (int i = 0; i < m_nbDesDeg; i++) {
            degats = Des.lancerDes(m_degats);
            degatsTotal += degats;
            txt += degats + "+";
        }
        txt = "\u001B[31m" + degatsTotal + "\u001B[0m" + txt + m_bonusDegats + ")";
        Affichage.affiche("Vous avez infligé " + txt + " \u001B[31mdégâts\u001B[0m");
        return degats + m_bonusDegats;
    }

    /**
     * Applique un bonus à l'arme.
     *
     * @param bonus le bonus à appliquer
     */
    @Override
    public void boost(int bonus) {
        m_bonusAttaque += bonus;
        m_bonusDegats += bonus;
    }

    /**
     * Obtient une représentation des dégâts de l'arme sous forme de chaîne de caractères.
     *
     * @return une chaîne de caractères représentant les dégâts de l'arme
     */
    public String getDegats() {
        return m_nbDesDeg + "d" + m_degats;
    }

    /**
     * Détermine quelle statistique utiliser pour l'arme.
     *
     * @return l'index de la statistique à utiliser
     */
    public int quelleStat() {
        return this.m_portee > 1 ? 2 : 1;
        // Si la portée est supérieure à 1, donc une arme à distance, alors la statistique qui importe est la dextérité.
        // Si c'est une arme de corps à corps, alors c'est la force.
    }

    /**
     * Obtient le bonus d'attaque de l'arme.
     *
     * @return le bonus d'attaque de l'arme
     */
    public int getBonusAttaque() {
        return m_bonusAttaque;
    }
}
