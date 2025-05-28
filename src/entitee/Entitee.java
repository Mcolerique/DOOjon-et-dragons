package entitee;

import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import des.Des;

import java.util.ArrayList;

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
    public Entitee(int[] s, Equipement[] e)
    {
        m_stats = s;
        m_pvActuelle = m_stats[0];
    }
    public boolean seFaireAttaquer(int jetAttaque)
    {
        return m_armure.getCA() <= jetAttaque;
    }
    public void sePrendreDegats(int degats)
    {
        m_pvActuelle -= degats;
    }
    public boolean seDeplacer(int distance)
    {
        if(distance > m_stats[3])
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public void attaquer(Entitee ennemie)
    {
        int jetAttaque = Des.lancerDes(20) + this.m_stats[m_arme.quelleStat()];
        if(ennemie.seFaireAttaquer(jetAttaque))
        {
            ennemie.sePrendreDegats(m_arme.infligerDegats());
        }
    }
    public Entitee choisirCible(ArrayList<Entitee> list)
    {
        boolean f = false;
        int choix;
        while (f) {
            Affichage.afficheListeEntitee(list);
            choix = Scanner.demandeInt() - 1;
            if(choix > list.size())
            {
                Affichage.affiche("Index invalide, veillez sélectionnez un index valide");
            }
            else if(choix < 0)
            {
                return null;
            }
            else
            {
                return list.get(choix);
            }
        }
        return null;
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
    public TypeEntitee getType()
    {
        return m_type;
    }
    public abstract String getNom();
    public abstract String getDescription();

}
