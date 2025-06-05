package entitee;

import equipement.arme.Arme;
import equipement.armure.Armure;
import des.Des;
import interactionUtilisateur.Affichage;

public abstract class Entitee {
    protected int[] m_stats = new int[5];
    protected Arme m_arme;
    protected Armure m_armure;
    protected int m_pvActuelle;
    protected TypeEntitee m_type;
    public Entitee()
    {
        for (int i = 0; i<5; i++)
        {
            m_stats[i] = 10;
        }
        m_pvActuelle =m_stats[0] ;
    }
    public Entitee (int[] s)
    {
        m_stats = s;
        m_pvActuelle = m_stats[0];
    }
    public boolean seFaireAttaquer(int jetAttaque)
    {
        if (m_armure != null){
            return m_armure.getCA() <= jetAttaque;
        }
        return true;
    }
    public void sePrendreDegats(int degats)
    {
        m_pvActuelle -= degats;
    }
    public boolean seDeplacer(int distance)
    {
        return distance <= m_stats[3];
    }
    public void attaquer(Entitee ennemie)
    {
        int jetAttaque = Des.lancerDes(20) + this.m_stats[m_arme.quelleStat()] + m_arme.getBonusAttaque();
        Affichage.affiche("Vous avec fait un "+ jetAttaque+" a votre jet d'attaque");
        if(ennemie.seFaireAttaquer(jetAttaque))
        {
            Affichage.affiche("Votre attaque touche");
            ennemie.sePrendreDegats(m_arme.infligerDegats());
        }
        else
        {
            Affichage.affiche("Votre attaque rate");
        }
    }
    public int getPorteeArme()
    {
        return m_arme.getPortee();
    }
    public boolean estVivant()
    {
        return m_pvActuelle > 0;
    }
    public int lancerInitiative()
    {
        return Des.lancerDes(20) + m_stats[4];
    }
    public void soin(int soin){
        m_pvActuelle += soin;
        if(m_pvActuelle > m_stats[0])
        {
            m_pvActuelle = m_stats[0];
        }
    }
    public int getForce()
    {
        return m_stats[1];
    }
    public int getDexterite()
    {
        return m_stats[2];
    }
    public int getVitesse()
    {
        return m_stats[3];
    }
    public int getPv()
    {
        return m_stats[0];
    }
    public int getPvActuelle()
    {
        return m_pvActuelle;
    }
    public String getNomArmure()
    {
        return m_armure.getNom();
    }
    public Arme getArme()
    {
        return m_arme;
    }
    public Armure getArmure(){ return m_armure; }
    public TypeEntitee getType()
    {
        return m_type;
    }
    public abstract String getNom();
    public abstract String getDescription();

}
