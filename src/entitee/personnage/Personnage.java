package entitee.personnage;

import des.Des;
import entitee.Entitee;
import entitee.personnage.classe.*;
import entitee.personnage.race.*;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

public class Personnage extends Entitee{
    private String m_nom;
    private final Race m_race;
    private final Classe m_classe;
    private ArrayList<Equipement> m_inventaire;
    public Personnage()
    {
        super();
        m_nom = "Saral Porcattache";
        m_race = new Halfelin();
        m_classe = new Guerrier();
        setPerso();
    }
    public Personnage(String nom, Race r, Classe c)
    {
        m_nom = nom;
        m_race = r;
        m_classe = c;
        setPerso();
    }
    public void setPerso()
    {
        m_stats[0] = m_classe.getPV();
        for(int i=1; i<5; i++)
        {
            for (int j = 0; i<4; i++)
            {
                m_stats[i] = Des.lancerDes(4);
            }
        }
        for(int i =0; i<5; i++)
        {
            m_stats[i] += m_race.getStats()[i];
        }
        m_inventaire = new ArrayList<>();
        for(int i = 0; i<m_classe.getEquipement().length; i++)
        {
            m_inventaire.add(m_equipement[i]);
        }
    }
    public void equiper(int e)
    {
        if( m_inventaire.get(e).getClass() == Arme.class)
        {
            Arme aEquiper = (Arme) m_inventaire.get(e);
            if(m_equipement[1] != null)
            {
                for (int i = 0; i<5; i++)
                {
                    m_stats[i] += m_equipement[1].getModifStat()[i];
                }
                m_inventaire.add(m_equipement[1]);
            }
            m_equipement[1] = aEquiper;
            m_inventaire.remove(aEquiper);
            for (int i = 0; i<5; i++)
            {
                m_stats[i] -= m_equipement[1].getModifStat()[i];
            }
        }
        else
        {
            Armure aEquiper = (Armure) m_inventaire.get(e);
            if(m_equipement[0] != null)
            {
                for (int i = 0; i<5; i++)
                {
                    m_stats[i] += m_equipement[1].getModifStat()[i];
                }
                m_inventaire.add(m_equipement[1]);
            }
            m_equipement[0] = aEquiper;
            m_inventaire.remove(aEquiper);
            for (int i = 0; i<5; i++)
            {
                m_stats[i] -= m_equipement[0].getModifStat()[i];
            }
        }
    }
    public void equiperArme()
    {
        int choix;
        ArrayList<Integer> index = new ArrayList<Integer>();
        ArrayList<Equipement> arme = new ArrayList<>();
        for(int i = 0; i < m_inventaire.size(); i++)
        {
            if(m_inventaire.get(i).getClass() == Arme.class)
            {
                arme.add(m_inventaire.get(i));
                index.add(i);
            }
        }
        Affichage.listeEquipement(arme);
        choix = Scanner.demandeInt();
        equiper(index.get(choix));
    }
    public void equiperArmure()
    {
        int choix;
        ArrayList<Integer> index = new ArrayList<Integer>();
        ArrayList<Equipement> armure = new ArrayList<>();
        for(int i = 0; i < m_inventaire.size(); i++)
        {
            if(m_inventaire.get(i).getClass() == Armure.class)
            {
                armure.add(m_inventaire.get(i));
                index.add(i);
            }
        }
        Affichage.listeEquipement(armure);
        choix = Scanner.demandeInt();
        equiper(index.get(choix));
    }
    public void ramasserObjet(Equipement objet)
    {
        m_inventaire.add(objet);
    }
    public int getTailleInventaire()
    {
        return m_inventaire.size();
    }
    public static Personnage creePersonnage()
    {
        int choix;
        Race[] raceDispo = {new Humain(), new Halfelin(), new Elfe(), new Nain()};
        Classe[] classeDispo = {new Guerrier(), new Magicien(), new Clerc(), new Roublard()};

        Affichage.affiche("Nom du personnage : ");
        String nom = Scanner.demandeString();
        Affichage.selectionTableau(raceDispo);
        choix = Scanner.demandeInt() -1 ;
        Race r = raceDispo[choix];
        Affichage.selectionTableau(classeDispo);
        choix = Scanner.demandeInt() -1 ;
        Classe c  = classeDispo[choix];
        return new Personnage(nom, r, c);
    }
}
