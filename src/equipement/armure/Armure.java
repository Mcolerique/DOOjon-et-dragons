package equipement.armure;

import equipement.Equipement;
import equipement.TypeEquipement;

/**
 * La classe Armure représente une armure dans le jeu.
 * Elle hérite de la classe Equipement.
 */
public class Armure extends Equipement {
    private final int m_cA;
    private final Poids m_poids;

    /**
     * Construit une armure par défaut.
     */
    public Armure() {
        super("nomArmure");
        m_cA = 1;
        m_poids = Poids.LEGERE;
        m_typeEquipement = TypeEquipement.ARMURE;
    }

    /**
     * Construit une armure avec un nom, une classe d'armure et un poids spécifiés.
     *
     * @param nom le nom de l'armure
     * @param cA la classe d'armure
     * @param poids le poids de l'armure
     */
    public Armure(String nom, int cA, Poids poids) {
        super(nom);
        m_cA = cA;
        m_poids = poids;
        if (m_poids == Poids.LOURDE) {
            m_modifStat[3] = -4;
        }
        m_typeEquipement = TypeEquipement.ARMURE;
    }

    /**
     * Obtient la classe d'armure.
     *
     * @return la classe d'armure
     */
    public int getCA() {
        return m_cA;
    }

    /**
     * Applique un bonus à l'armure.
     *
     * @param bonus le bonus à appliquer
     */
    @Override
    public void boost(int bonus) {
        // Cette méthode est vide car les armures ne reçoivent pas de bonus dans cette implémentation.
    }
}
