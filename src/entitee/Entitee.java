package entitee;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import des.Des;

public abstract class Entitee {
    protected int[] m_stats = new int[5];
    protected Equipement[] m_equipement = new Equipement[2];
    protected int m_pvActuelle;
    public Entitee()
    {
        m_equipement[0] = new Armure();
        m_equipement[1] = new Arme();
        for (int i = 0; i<5; i++) {
            m_stats[i] = Des.lancerDes(20);
        }
        m_pvActuelle = m_stats[0];
    }
    public Entitee(int[] s, Equipement[] e)
    {
        m_stats = s;
        m_equipement = e;
        m_pvActuelle = m_stats[0];
    }
    public boolean seFaireAttaquer(int jetAttaque)
    {
        if(jetAttaque >= m_equipement[0].getCA())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void sePrendreDegats(int degats)
    {
        m_pvActuelle -= degats;
    }
    public boolean seDeplacer(int distance)
    {
        if(distance > m_stats[4])
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public void attaquer()
    {
        Entitee ennemie = choisirCible();
        int jetAttaque = Des.lancerDes(20) + this.m_stats[this.m_equipement[1].quelleStat()];
        if(ennemie.seFaireAttaquer(jetAttaque))
        {
            ennemie.sePrendreDegats(this.m_equipement[1].infligerDegats());
        }
    }
    public abstract Entitee choisirCible();
}
