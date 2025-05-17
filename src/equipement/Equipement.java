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
<<<<<<< HEAD

    public String getNom() {
        return m_nom;
    }

=======
>>>>>>> 71aeb895a0e1be7112e4fb8c34fba74e81f8f97d
}
