package entitee.personnage.race;

/**
 * La classe Elfe représente une race de type Elfe dans le jeu.
 * Elle hérite de la classe Race.
 */
public class Elfe extends Race {

    /**
     * Tableau des statistiques de base pour un Elfe.
     */
    private static final int[] m_stats = new int[]{0, 0, 6, 0, 0};

    /**
     * Construit un Elfe avec des statistiques par défaut.
     */
    public Elfe() {
    }

    /**
     * Obtient les statistiques de base associées à la race Elfe.
     *
     * @return un tableau d'entiers représentant les statistiques de l'Elfe
     */
    @Override
    public int[] getStats() {
        return m_stats;
    }

    /**
     * Obtient les points de vie de base associés à la race Elfe.
     *
     * @return les points de vie de l'Elfe
     */
    @Override
    public int getPv() {
        return m_stats[0];
    }

    /**
     * Obtient la force de base associée à la race Elfe.
     *
     * @return la force de l'Elfe
     */
    @Override
    public int getForce() {
        return m_stats[1];
    }

    /**
     * Obtient la dextérité de base associée à la race Elfe.
     *
     * @return la dextérité de l'Elfe
     */
    @Override
    public int getDex() {
        return m_stats[2];
    }

    /**
     * Obtient la vitesse de base associée à la race Elfe.
     *
     * @return la vitesse de l'Elfe
     */
    @Override
    public int getVitesse() {
        return m_stats[3];
    }

    /**
     * Obtient l'initiative de base associée à la race Elfe.
     *
     * @return l'initiative de l'Elfe
     */
    @Override
    public int getInitiative() {
        return m_stats[4];
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la race Elfe.
     *
     * @return le nom de la race
     */
    @Override
    public String toString() {
        return "Elfe";
    }
}
