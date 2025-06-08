package entitee.personnage.race;

/**
 * La classe Halfelin représente une race de type Halfelin dans le jeu.
 * Elle hérite de la classe Race.
 */
public class Halfelin extends Race {

    /**
     * Tableau des statistiques de base pour un Halfelin.
     */
    private static final int[] m_stats = new int[]{0, 0, 4, 2, 0};

    /**
     * Construit un Halfelin avec des statistiques par défaut.
     */
    public Halfelin() {
    }

    /**
     * Obtient les statistiques de base associées à la race Halfelin.
     *
     * @return un tableau d'entiers représentant les statistiques du Halfelin
     */
    @Override
    public int[] getStats() {
        return m_stats;
    }

    /**
     * Obtient les points de vie de base associés à la race Halfelin.
     *
     * @return les points de vie du Halfelin
     */
    @Override
    public int getPv() {
        return m_stats[0];
    }

    /**
     * Obtient la force de base associée à la race Halfelin.
     *
     * @return la force du Halfelin
     */
    @Override
    public int getForce() {
        return m_stats[1];
    }

    /**
     * Obtient la dextérité de base associée à la race Halfelin.
     *
     * @return la dextérité du Halfelin
     */
    @Override
    public int getDex() {
        return m_stats[2];
    }

    /**
     * Obtient la vitesse de base associée à la race Halfelin.
     *
     * @return la vitesse du Halfelin
     */
    @Override
    public int getVitesse() {
        return m_stats[3];
    }

    /**
     * Obtient l'initiative de base associée à la race Halfelin.
     *
     * @return l'initiative du Halfelin
     */
    @Override
    public int getInitiative() {
        return m_stats[4];
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la race Halfelin.
     *
     * @return le nom de la race
     */
    @Override
    public String toString() {
        return "Halfelin";
    }
}
