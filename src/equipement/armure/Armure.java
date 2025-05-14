package equipement.armure;
import equipement.Equipement;
import equipement.arme.TypeCaC;

public class Armure extends Equipement {
    private int m_cA;
    private Poids m_poids;

    public Armure() {
        super("nomArmure");
        m_cA = 1;
        m_poids = Poids.LEGERE;
    }

    public Armure(String nom, int cA, Poids poids) {
        super(nom);
        m_cA = cA;
        m_poids = poids;
        if(poids == Poids.LOURDE){
            m_modifStat[3] = -4;
        }
    }
    public int getCA() {
        return m_cA;
    }

    @Override
    public int quelleStat() {
        return  -1;
    }

    @Override
    public int getPortee() {
        return -1;
    }

    @Override
    public int infligerDegats() {
        return 0;
    }
}
