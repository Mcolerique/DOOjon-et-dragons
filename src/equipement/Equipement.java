package equipement;

/**
 * La classe Equipement représente un équipement de base dans le jeu.
 * Elle est conçue pour être étendue par des classes spécifiques d'équipement.
 */
public abstract class Equipement {
    private final String m_nom;
    protected int[] m_modifStat = new int[5];
    protected TypeEquipement m_typeEquipement;

    /**
     * Construit un nouvel équipement avec un nom spécifié.
     *
     * @param nom le nom de l'équipement
     */
    public Equipement(String nom) {
        m_nom = nom;
        m_modifStat[0] = 0;
        m_modifStat[1] = 0;
        m_modifStat[2] = 0;
        m_modifStat[3] = 0;
        m_modifStat[4] = 0;
    }

    /**
     * Obtient les modifications de statistiques apportées par cet équipement.
     *
     * @return un tableau d'entiers représentant les modifications de statistiques
     */
    public int[] getModifStat() {
        return m_modifStat;
    }

    /**
     * Obtient le nom de l'équipement.
     *
     * @return le nom de l'équipement
     */
    public String getNom() {
        return m_nom;
    }

    /**
     * Obtient le type de l'équipement.
     *
     * @return le type de l'équipement
     */
    public TypeEquipement getType() {
        return m_typeEquipement;
    }

    /**
     * Applique un bonus à l'équipement.
     *
     * @param bonus le bonus à appliquer
     */
    public abstract void boost(int bonus);

    /**
     * Retourne une représentation sous forme de chaîne de caractères de l'équipement.
     *
     * @return le nom de l'équipement
     */
    @Override
    public String toString() {
        return m_nom;
    }
}
