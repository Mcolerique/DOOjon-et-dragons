package entitee.personnage.classe;

import entitee.personnage.sort.*;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;

/**
 * La classe Magicien représente une classe de personnage de type Magicien dans le jeu.
 * Elle hérite de la classe Classe.
 */
public class Magicien extends Classe {

    /**
     * Points de vie de base pour un Magicien.
     */
    private static final int m_pv = 12;

    /**
     * Sorts de base pour un Magicien.
     */
    private static final Sort[] m_sort = new Sort[] {new Guerison(), new BoogieWoogie(), new ArmeMagique()};

    /**
     * Équipement de base pour un Magicien.
     */
    private final Equipement[] m_equipementBase;

    /**
     * Construit un Magicien avec un équipement de base.
     */
    public Magicien() {
        m_equipementBase = new Equipement[] {new Arme("Bâton", 1, 6, TypeCaC.COURANTE), new Arme("Fronde", 1, 4, 6)};
    }

    /**
     * Obtient les points de vie de base associés à la classe Magicien.
     *
     * @return les points de vie du Magicien
     */
    @Override
    public int getPV() {
        return m_pv;
    }

    /**
     * Obtient l'équipement de base associé à la classe Magicien.
     *
     * @return l'équipement de base du Magicien
     */
    public Equipement[] getEquipement() {
        return m_equipementBase;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la classe Magicien.
     *
     * @return le nom de la classe
     */
    @Override
    public String toString() {
        return "Magicien";
    }

    /**
     * Obtient les sorts associés à la classe Magicien.
     *
     * @return les sorts du Magicien
     */
    @Override
    public Sort[] getSort() {
        return m_sort;
    }
}
