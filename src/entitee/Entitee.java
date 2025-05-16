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
    protected Equipement[] m_equipement = new Equipement[2];
    protected int m_pvActuelle;
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
        int jetAttaque = Des.lancerDes(20) + this.m_stats[this.m_equipement[1].quelleStat()];
        if(ennemie.seFaireAttaquer(jetAttaque))
        {
            ennemie.sePrendreDegats(this.m_equipement[1].infligerDegats());
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
        return m_equipement[1].getPortee();
    }
    public boolean estVivant()
    {
        return m_pvActuelle <= 0;
    }
}
