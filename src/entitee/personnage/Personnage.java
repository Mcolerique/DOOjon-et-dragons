package entitee.personnage;
import des.Des;
import entitee.Entitee;
import entitee.personnage.classe.Classe;
import entitee.personnage.classe.Guerrier;
import entitee.personnage.race.Halfelin;
import entitee.personnage.race.Race;
import equipement.Equipement;
import equipement.arme.Arme;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

public class Personnage extends Entitee{
    private String m_nom;
    private Race m_race;
    private Classe m_classe;
    private ArrayList<Equipement> m_inventaire;
    public Personnage()
    {
        super();
        m_nom = "Saral Porcattache";
        m_race = new Halfelin();
        m_classe = new Guerrier();
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
    public void equiper()
    {
        Equipement aEquiper = choixEquipement();
        if( aEquiper.getClass() == Arme.class)
        {
            if(m_equipement[1] != null)
            {
                for (int i = 0; i<5; i++)
                {
                    m_stats += m_equipement[1].getModifStat()[i];
                }
                m_inventaire.add(m_equipement[1]);
            }
            m_equipement[1] = aEquiper;
            m_inventaire.remove(aEquiper);
            for (int i = 0; i<5; i++)
            {
                m_stats -= m_equipement[1].getModifStat()[i];
            }
        }
        else
        {
            if(m_equipement[0] != null)
            {
                for (int i = 0; i<5; i++)
                {
                    m_stats += m_equipement[1].getModifStat()[i];
                }
                m_inventaire.add(m_equipement[1]);
            }
            m_equipement[0] = aEquiper;
            m_inventaire.remove(aEquiper);
            for (int i = 0; i<5; i++)
            {
                m_stats -= m_equipement[0].getModifStat()[i];
            }
        }
    }
    public Equipement choixEquipement()
    {
        {
            boolean f = false;
            int choix;
            while (f) {
                Affichage.afficheInventaire(m_inventaire);
                choix = Scanner.demandeInt() - 1;
                if(choix > m_inventaire.size())
                {
                    Affichage.affiche("Index invalide, veillez sélectionnez un index valide");
                }
                else if(choix < 0)
                {
                    return null;
                }
                else
                {
                    return m_inventaire.get(choix);
                }
            }
            return null;
        }
    }
    public void ramasserObjet(Equipement objet)
    {
        m_inventaire.add(objet);
    }
}
