package entitee.personnage.classe;

import entitee.personnage.sort.Sort;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

/**
 * La classe Guerrier représente une classe de personnage de type Guerrier dans le jeu.
 * Elle hérite de la classe Classe.
 */
public class Guerrier extends Classe {

    /**
     * Points de vie de base pour un Guerrier.
     */
    private static final int m_pv = 20;

    /**
     * Équipement de base pour un Guerrier.
     */
    private final Equipement[] m_equipementBase;

    /**
     * Construit un Guerrier avec un équipement de base.
     */
    public Guerrier() {
        m_equipementBase = new Equipement[] {
                new Armure("Cotte de mailles", 11, Poids.LOURDE),
                new Arme("Épée longue", 1, 8, TypeCaC.GUERRE),
                new Arme("Arbalète légère", 1, 8, 16)
        };
    }

    /**
     * Obtient les points de vie de base associés à la classe Guerrier.
     *
     * @return les points de vie du Guerrier
     */
    @Override
    public int getPV() {
        return m_pv;
    }

    /**
     * Obtient l'équipement de base associé à la classe Guerrier.
     *
     * @return l'équipement de base du Guerrier
     */
    @Override
    public Equipement[] getEquipement() {
        return m_equipementBase;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la classe Guerrier.
     *
     * @return le nom de la classe
     */
    @Override
    public String toString() {
        return "Guerrier";
    }

    /**
     * Obtient les sorts associés à la classe Guerrier.
     *
     * @return null, car le Guerrier n'a pas de sorts
     */
    @Override
    public Sort[] getSort() {
        return null;
    }
}
