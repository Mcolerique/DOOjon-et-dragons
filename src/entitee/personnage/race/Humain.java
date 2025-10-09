package entitee.personnage.race;

/**
 * La classe Humain représente une race de type Humain dans le jeu.
 * Elle hérite de la classe Race.
 */
public class Humain extends Race {

    /**
     * Tableau des statistiques de base pour un Humain.
     */
    private static final int[] m_stats = new int[]{2, 2, 2, 2, 2};

    /**
     * Construit un Humain avec des statistiques par défaut.
     */
    public Humain() {
    }

    /**
     * Obtient les statistiques de base associées à la race Humain.
     *
     * @return un tableau d'entiers représentant les statistiques de l'Humain
     */
    @Override
    public int[] getStats() {
        return m_stats;
    }

    /**
     * Obtient les points de vie de base associés à la race Humain.
     *
     * @return les points de vie de l'Humain
     */
    @Override
    public int getPv() {
        return m_stats[0];
    }

    /**
     * Obtient la force de base associée à la race Humain.
     *
     * @return la force de l'Humain
     */
    @Override
    public int getForce() {
        return m_stats[1];
    }

    /**
     * Obtient la dextérité de base associée à la race Humain.
     *
     * @return la dextérité de l'Humain
     */
    @Override
    public int getDex() {
        return m_stats[2];
    }

    /**
     * Obtient la vitesse de base associée à la race Humain.
     *
     * @return la vitesse de l'Humain
     */
    @Override
    public int getVitesse() {
        return m_stats[3];
    }

    /**
     * Obtient l'initiative de base associée à la race Humain.
     *
     * @return l'initiative de l'Humain
     */
    @Override
    public int getInitiative() {
        return m_stats[4];
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères de la race Humain.
     *
     * @return le nom de la race
     */
    @Override
    public String toString() {
        return "Humain";
    }
}
