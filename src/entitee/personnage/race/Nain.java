package entitee.personnage.race;

/**
 * La classe Nain représente une race de type Nain dans le jeu.
 * Elle hérite de la classe Race.
 */
public class Nain extends Race {

    /**
     * Tableau des statistiques de base pour un Nain.
     */
    private static final int[] m_stats = new int[]{0, 6, 0, 0, 0};

    /**
     * Construit un Nain avec des statistiques par défaut.
     */
    public Nain() {
    }

    /**
     * Obtient les statistiques de base associées à la race Nain.
     *
     * @return un tableau d'entiers représentant les statistiques du Nain
     */
    @Override
    public int[] getStats() {
        return m_stats;
    }

    /**
     * Obtient les points de vie de base associés à la race Nain.
     *
     * @return les points de vie du Nain
     */
    @Override
    public int getPv() {
        return m_stats[0];
    }

    /**
     * Obtient la force de base associée à la race Nain.
     *
     * @return la force du Nain
     */
    @Override
    public int getForce() {
        return m_stats[1];
    }

    /**
     * Obtient la dextérité de base associée à la race Nain.
     *
     * @return la dextérité du Nain
     */
    @Override
    public int getDex() {
        return m_stats[2];
    }

    /**
     * Obtient la vitesse de base associée à la race Nain.
     *
     * @return la vitesse du Nain
     */
    @Override
    public int getVitesse() {
        return m_stats[3];
    }

    /**
     * Obtient l'initiative de base associée à la race Nain.
     *
     * @return l'initiative du Nain
     */
    @Override
    public int getInitiative() {
        return m_stats[4];
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la race Nain.
     *
     * @return le nom de la race
     */
    @Override
    public String toString() {
        return "Nain";
    }
}
