package partie;

import donjon.Donjon;
import entitee.Entitee;
import entitee.personnage.Personnage;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

public class Partie {
    private Donjon m_donjon;
    private ArrayList<Personnage> m_perso;
    private int m_numDonjon;

    public Partie()
    {
        m_perso = miseEnPlacePerso();
        m_donjon = null;
        m_numDonjon = 0;
    }
    public ArrayList<Personnage> miseEnPlacePerso()
    {
        ArrayList<Personnage> perso = new ArrayList<>();
        int choix;
        Affichage.affiche("Combien de personnage joueur voulez vous crée ?");
        choix = Scanner.demandeInt();
        for(int i = 0; i<choix; i++)
        {
            perso.add(Personnage.creePersonnage());
        }
        return perso;
    }
    public void lancerPartie()
    {
        ArrayList<Entitee> ordreEntite;
        Entitee e;
        boolean defaite = false;
        for (int i = 0; i<3 ; i++)
        {
            m_donjon = Donjon.creerDonjon(m_perso);
            ordreEntite = m_donjon.lancerInitiative();
            while (!m_donjon.estVaincu() || !defaite)
            {
                for (int j = 0; j<ordreEntite.size(); j++)
                {
                    e = ordreEntite.get(j);
                    if(e.estVivant())
                    {
                        nouveauTour(e);
                    }
                    else
                    {
                        if(e.equals(Personnage.class))
                        {
                            defaite(e);
                            defaite = true;
                            break;
                        }
                        else
                        {
                            ordreEntite.remove(e);
                        }
                    }
                }
            }
            if(defaite)
            {
                break;
            }
            Affichage.victoireDonjon(i+1);
            m_numDonjon ++;
        }
    }
    public void nouveauTour(Entitee e)
    {
        String choix;
        boolean finAction;
        for(int i=0; i<3; i++)
        {
            finAction = false;
            while (!finAction)
            {
                Affichage.afficheTour(e);
                choix = Scanner.demandeString();
                finAction = tourMonstre(e, choix);
            }
        }
    }
    public boolean tourMonstre(Entitee e, String choix)
    {
        char a = 'A';
        char x;
        int[] pos = new int[2];
        switch (choix.substring(0,2)) {
            case "att":
                x = choix.substring(4, 5).toUpperCase().charAt(0);
                pos[0] = (a + x) % 26;
                pos[1] = Integer.parseInt(choix.substring(5));
                if (!attaquePossible(e, pos)) {
                    Affichage.affiche("Attaque impossible, séléctionnez un emplacement valide");
                    return false;
                    break;
                }
                e.attaquer(m_donjon.getEntiteeAPos(pos));
                break;
            case "mj ":
                Affichage.affiche(choix.substring(4));
                return true;
                break;
            case "dep":
                x = choix.substring(4, 5).toUpperCase().charAt(0);
                if(!verifCharValide(x))
                {
                    Affichage.affiche("Déplacement impossible, séléctionnez un emplacement valide");
                    return false;
                    break;
                }
                pos[0] = (a + x) % 26;
                pos[1] = Integer.parseInt(choix.substring(5));
                if (!verifEntierValide(pos[1]) || !deplacementPossible(e, pos)) ;
                {
                    Affichage.affiche("Déplacement impossible, séléctionnez un emplacement valide");
                    return false;
                    break;
                }
                m_donjon.deplacerEntitee(e, pos);
                return true;
                break;
            default:
                if(e instanceof Personnage)
                {
                    Personnage p = (Personnage) e;
                    return tourPerso(p, choix);
                    break;
                }
                else {
                    Affichage.affiche("Sélectionner une action valide");
                    return false;
                }
                break;
        }
        return false;
    }
    public boolean tourPerso(Personnage p, String choix)
    {
        switch (choix)
        {
            case "com":
                Affichage.affiche(choix.substring(4));
                break;
            case "equ":
                int ie = Integer.parseInt(choix.substring(4));
                if(ie >= p.getTailleInventaire())
                {
                    Affichage.affiche("Equipement sélectionnez invalide");
                    return false;
                    break;
                }
                p.equiper(ie);
                return true;
                break;
            default:
                Affichage.affiche("Sélectionner une action valide");
                return false;
                break;
        }
        return false;
    }
    public boolean deplacementPossible(Entitee entitee, int[] pos)
    {
        int[] posEntitee = m_donjon.getPosEntitee(entitee);
        int distance = (int)Math.sqrt(Math.pow(pos[0] - posEntitee[0], 2) + Math.pow(pos[1] - posEntitee[1], 2)); ;
        return pos[0] < m_donjon.getLongueur() && pos[1] < m_donjon.getLargeur() && entitee.seDeplacer(distance);
    }
    public boolean attaquePossible(Entitee entitee, int[] pos)
    {
        int[] posEntitee = m_donjon.getPosEntitee(entitee);
        int distance = (int)Math.sqrt(Math.pow(pos[0] - posEntitee[0], 2) + Math.pow(pos[1] - posEntitee[1], 2)); ;
        return pos[0] < m_donjon.getLongueur() && pos[1] < m_donjon.getLargeur() && entitee.getPorteeArme()<distance;
    }
    public boolean verifEntierValide(int e)
    {
        return e <= m_donjon.getLargeur() && e > 0;
    }
    public boolean verifCharValide(char c)
    {
        return c >= 'A' && c <= 'Z';
    }

    public void defaite(Entitee e){
        Affichage.defaite(e);
    }
}
