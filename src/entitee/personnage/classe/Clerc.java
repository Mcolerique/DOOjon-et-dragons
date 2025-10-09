package entitee.personnage.classe;

import entitee.personnage.sort.Guerison;
import entitee.personnage.sort.Sort;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

/**
 * La classe Clerc représente une classe de personnage de type Clerc dans le jeu.
 * Elle hérite de la classe Classe.
 */
public class Clerc extends Classe {

    /**
     * Points de vie de base pour un Clerc.
     */
    private static final int m_pv = 16;

    /**
     * Sorts de base pour un Clerc.
     */
    private static final Sort[] m_sort = new Sort[] {new Guerison()};

    /**
     * Équipement de base pour un Clerc.
     */
    private final Equipement[] m_equipementBase;

    /**
     * Construit un Clerc avec un équipement de base.
     */
    public Clerc() {
        m_equipementBase = new Equipement[] {
                new Armure("Armure d'écaille", 9, Poids.LEGERE),
                new Arme("Masse d'armes", 1, 6, TypeCaC.COURANTE),
                new Arme("Arbalète légère", 1, 8, 16)
        };
    }

    /**
     * Obtient les points de vie de base associés à la classe Clerc.
     *
     * @return les points de vie du Clerc
     */
    @Override
    public int getPV() {
        return m_pv;
    }

    /**
     * Obtient l'équipement de base associé à la classe Clerc.
     *
     * @return l'équipement de base du Clerc
     */
    public Equipement[] getEquipement() {
        return m_equipementBase;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la classe Clerc.
     *
     * @return le nom de la classe
     */
    @Override
    public String toString() {
        return "Clerc";
    }

    /**
     * Obtient les sorts associés à la classe Clerc.
     *
     * @return les sorts du Clerc
     */
    @Override
    public Sort[] getSort() {
        return m_sort;
    }
}
