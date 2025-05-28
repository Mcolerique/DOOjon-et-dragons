package entitee.personnage;

import des.Des;
import entitee.Entitee;
import entitee.TypeEntitee;
import entitee.personnage.classe.*;
import entitee.personnage.race.*;
import equipement.Equipement;
import equipement.TypeEquipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;
import java.util.Arrays;

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
        m_type = TypeEntitee.PERSONNAGE;
        setPerso();
    }
    public Personnage(String nom, Race r, Classe c)
    {
        m_nom = nom;
        m_race = r;
        m_classe = c;
        m_type = TypeEntitee.PERSONNAGE;
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
            m_stats[i]+=3;
        }
        for(int i =0; i<5; i++)
        {
            m_stats[i] += m_race.getStats()[i];
        }
        m_inventaire = new ArrayList<>();
        m_inventaire.addAll(Arrays.asList(m_classe.getEquipement()));
        m_pvActuelle = m_stats[0];
    }
    public void equiper(int e)
    {
        TypeEquipement t = m_inventaire.get(e).getType();
        switch (t)
        {
            case ARME :
                Arme armeAEquiper = (Arme) m_inventaire.get(e);
                if(m_arme != null)
                {
                    for (int i = 0; i<5; i++)
                    {
                        m_stats[i] += m_arme.getModifStat()[i];
                    }
                    m_inventaire.add(m_arme);
                }
                m_arme = armeAEquiper;
                m_inventaire.remove(armeAEquiper);
                for (int i = 0; i<5; i++)
                {
                    m_stats[i] -= m_arme.getModifStat()[i];
                }
                break;
            case ARMURE:
                Armure aEquiper = (Armure) m_inventaire.get(e);
                if(m_armure!= null)
                {
                    for (int i = 0; i<5; i++)
                    {
                        m_stats[i] += m_armure.getModifStat()[i];
                    }
                    m_inventaire.add(m_armure);
                }
                m_armure = aEquiper;
                m_inventaire.remove(aEquiper);
                for (int i = 0; i<5; i++)
                {
                    m_stats[i] -= m_armure.getModifStat()[i];
                }
                break;
            default:
                Affichage.affiche("erreur");
                break;
        }
    }
    public void choisirArme()
    {
        int choix;
        ArrayList<Integer> index = new ArrayList<Integer>();
        ArrayList<Equipement> arme = new ArrayList<>();
        for(int i = 0; i < m_inventaire.size(); i++)
        {
            if(m_inventaire.get(i).getType() == TypeEquipement.ARME)
            {
                arme.add(m_inventaire.get(i));
                index.add(i);
            }
        }
        Affichage.listeEquipement(arme);
        choix = Scanner.demandeInt()-1;
        equiper(index.get(choix));
    }
    public void choisirArmure()
    {
        int choix;
        ArrayList<Integer> index = new ArrayList<Integer>();
        ArrayList<Equipement> armure = new ArrayList<>();
        for(int i = 0; i < m_inventaire.size(); i++)
        {
            if(m_inventaire.get(i).getType() == TypeEquipement.ARMURE)
            {
                armure.add(m_inventaire.get(i));
                index.add(i);
            }
        }
        Affichage.listeEquipement(armure);
        choix = Scanner.demandeInt()-1;
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

    public ArrayList<Equipement> getInventaire() {
        return m_inventaire;
    }
    public String getNom()
    {
        return m_nom;
    }
    public String getDescription()
    {
        return m_race.toString()+" "+m_classe.toString();
    }
    public String getInitiale()
    {
        if(m_nom.length() < 3) return m_nom;

        else return m_nom.substring(0,3);
    }

    @Override
    public String toString(){
        return m_nom;
    }
}
