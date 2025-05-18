package equipement;

public abstract class Equipement {
    private String m_nom;
    protected int[] m_modifStat = new int[5];

    public Equipement(String nom) {
        m_nom = nom;
        m_modifStat[0] = 0;
        m_modifStat[1] = 0;
        m_modifStat[2] = 0;
        m_modifStat[3] = 0;
        m_modifStat[4] = 0;
    }

    public int[] getModifStat() {
        return m_modifStat;
    }

    public String getNom() {
        return m_nom;
    }
}
