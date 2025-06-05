package equipement.armure;
import equipement.Equipement;
import equipement.TypeEquipement;

public class Armure extends Equipement {
    private final int m_cA;
    private final Poids m_poids;

    public Armure() {
        super("nomArmure");
        m_cA = 1;
        m_poids = Poids.LEGERE;
        m_typeEquipement = TypeEquipement.ARMURE;
    }

    public Armure(String nom, int cA, Poids poids) {
        super(nom);
        m_cA = cA;
        m_poids = poids;
        if(m_poids == Poids.LOURDE){
            m_modifStat[3] = -4;
        }
        m_typeEquipement = TypeEquipement.ARMURE;
    }
    public int getCA() {
        return m_cA;
    }
    public void boost(int bonus)
    {

    }
}
