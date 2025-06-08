package entitee.personnage.classe;

import entitee.personnage.sort.Sort;
import equipement.Equipement;

/**
 * La classe Classe représente une classe de personnage de base dans le jeu.
 * Elle est conçue pour être étendue par des classes spécifiques de personnages.
 */
public abstract class Classe {

    /**
     * Obtient les points de vie de base associés à la classe.
     *
     * @return les points de vie de la classe
     */
    public abstract int getPV();

    /**
     * Obtient l'équipement de base associé à la classe.
     *
     * @return l'équipement de base de la classe
     */
    public abstract Equipement[] getEquipement();

    /**
     * Obtient les sorts associés à la classe.
     *
     * @return les sorts de la classe
     */
    public abstract Sort[] getSort();
}
