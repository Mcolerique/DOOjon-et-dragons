package entitee.personnage.race;

/**
 * La classe Race représente une race de personnage dans le jeu.
 * Elle est conçue pour être étendue par des classes spécifiques de races.
 */
public abstract class Race {

    /**
     * Obtient les statistiques de base associées à la race.
     *
     * @return un tableau d'entiers représentant les statistiques de la race
     */
    public abstract int[] getStats();

    /**
     * Obtient les points de vie de base associés à la race.
     *
     * @return les points de vie de la race
     */
    public abstract int getPv();

    /**
     * Obtient la force de base associée à la race.
     *
     * @return la force de la race
     */
    public abstract int getForce();

    /**
     * Obtient la dextérité de base associée à la race.
     *
     * @return la dextérité de la race
     */
    public abstract int getDex();

    /**
     * Obtient la vitesse de base associée à la race.
     *
     * @return la vitesse de la race
     */
    public abstract int getVitesse();

    /**
     * Obtient l'initiative de base associée à la race.
     *
     * @return l'initiative de la race
     */
    public abstract int getInitiative();
}
