package entitee.personnage.race;

public class Halfelin extends Race{
    private static final int[] m_stats = new int[]{0,0,4,2,0};
    public Halfelin()
    {
    }
    @Override
    public int getPv() {
        return m_stats[0];
    }

    @Override
    public int getForce() {
        return m_stats[1];
    }

    @Override
    public int getDex() {
        return m_stats[2];
    }

    @Override
    public int getVitesse() {
        return m_stats[3];
    }

    @Override
    public int getInitiative() {
        return m_stats[4];
    }
}
