package entitee.personnage.sort;

import entitee.Entitee;

import java.util.ArrayList;

/**
 * La classe Sort représente un sort de base dans le jeu.
 * Elle est conçue pour être étendue par des classes spécifiques de sorts.
 */
public abstract class Sort {
    private final String m_nom;
    private final String m_description;

    /**
     * Construit un sort avec un nom et une description spécifiés.
     *
     * @param n le nom du sort
     * @param desc la description du sort
     */
    public Sort(String n, String desc) {
        m_nom = n;
        m_description = desc;
    }

    /**
     * Utilise le sort sur une liste d'entités.
     *
     * @param listEntite la liste des entités sur lesquelles utiliser le sort
     * @return vrai si le sort a été utilisé avec succès, faux sinon
     */
    public abstract boolean utiliserSort(ArrayList<Entitee> listEntite);

    /**
     * Obtient le nom du sort.
     *
     * @return le nom du sort
     */
    public String getNom() {
        return m_nom;
    }
}
