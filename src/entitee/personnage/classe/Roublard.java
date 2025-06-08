package entitee.personnage.classe;

import entitee.personnage.sort.Sort;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

/**
 * La classe Roublard représente une classe de personnage de type Roublard dans le jeu.
 * Elle hérite de la classe Classe.
 */
public class Roublard extends Classe {

    /**
     * Points de vie de base pour un Roublard.
     */
    private static final int m_pv = 16;

    /**
     * Équipement de base pour un Roublard.
     */
    private static Equipement[] m_equipementBase;

    /**
     * Construit un Roublard avec un équipement de base.
     */
    public Roublard() {
        m_equipementBase = new Equipement[]{new Arme("Rapière", 1, 8, TypeCaC.GUERRE), new Arme("Arc court", 1, 6, 16)};
    }

    /**
     * Obtient les points de vie de base associés à la classe Roublard.
     *
     * @return les points de vie du Roublard
     */
    @Override
    public int getPV() {
        return m_pv;
    }

    /**
     * Obtient l'équipement de base associé à la classe Roublard.
     *
     * @return l'équipement de base du Roublard
     */
    public Equipement[] getEquipement() {
        return m_equipementBase;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la classe Roublard.
     *
     * @return le nom de la classe
     */
    @Override
    public String toString() {
        return "Roublard";
    }

    /**
     * Obtient les sorts associés à la classe Roublard.
     *
     * @return null, car le Roublard n'a pas de sorts
     */
    @Override
    public Sort[] getSort() {
        return null;
    }
}
