package partie;

import des.Des;
import donjon.Donjon;
import entitee.Entitee;
import entitee.TypeEntitee;
import entitee.personnage.Personnage;
import equipement.Equipement;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Partie {
    private Donjon m_donjon;
    private final ArrayList<Personnage> m_perso;
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
        Affichage.affiche("Combien de personnages joueurs voulez vous créer ?");
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
        int numTour = 1;
        boolean defaite = false;
        while (m_numDonjon < 3 && !defaite)
        {
            m_donjon = Donjon.creerDonjon(m_perso);
            equiperPerso();
            ordreEntite = m_donjon.lancerInitiative();
            while (!m_donjon.estVaincu() && !defaite)
            {
                for (int j = 0; j<ordreEntite.size(); j++)
                {
                    Affichage.afficheTour(ordreEntite, j,m_donjon, m_numDonjon+1, numTour);
                    e = ordreEntite.get(j);
                    if(e.estVivant())
                    {
                        nouveauTour(e);
                    }
                    // Liste des morts à supprimer
                    ArrayList<Entitee> morts = new ArrayList<>();

                    for (Entitee ent : ordreEntite)
                    {
                        if (!ent.estVivant())
                        {
                            m_donjon.supprEntite(ent);
                            if (ent.getType() == TypeEntitee.PERSONNAGE) {
                                defaite(ent); // Affichage de défaite
                                defaite = true;
                                break;
                            }
                            morts.add(ent);
                        }
                    }
                    ordreEntite.removeAll(morts);
                    if (defaite) break;
                }
                numTour ++;
            }
            if(defaite) break;
            Affichage.victoireDonjon(m_numDonjon+1);
            m_numDonjon ++;
        }
    }
    public void nouveauTour(Entitee e)
    {
        boolean objetARecup;
        String choix;
        boolean finAction;
        int i = 0;
        while(i<3 && e.estVivant())
        {
            finAction = false;
            while (!finAction)
            {
                objetARecup = m_donjon.equipAPos(m_donjon.getPosEntitee(e));
                Affichage.afficherDonjon(m_donjon);
                Affichage.afficheAction(e, i, objetARecup);
                choix = Scanner.demandeString();
                finAction = tour(e, choix.split(" "), objetARecup);
            }
            Affichage.affiche("mj voulez vous faire une action ? y/n");
            choix = Scanner.demandeString();
            finAction = false;
            if(choix.equals("y"))
            {
                while (!finAction) {
                    Affichage.afficheActionMJ();
                    choix = Scanner.demandeString();
                    finAction = tourMJ(choix.split(" "));
                }
            }
            i++;
        }
    }
    public boolean tour(Entitee e, String[] choix, boolean objetARecup)
    {
        char a = 'A';
        char x;
        int[] pos = new int[2];
        switch (choix[0]) {
            case "att":
                x = choix[1].toUpperCase().charAt(0);
                if(!verifCharValide(x))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                pos[0] = ((a + x) % 26);
                pos[1] = Integer.parseInt(choix[1].substring(1))-1;
                if (!verifEntierValide(pos[1]) || !attaquePossible(e, pos)) {
                    Affichage.affiche("Attaque impossible, sélectionnez un emplacement valide");
                    return false;
                }
                Entitee ennemi = m_donjon.getEntiteeAPos(pos);
                if (ennemi == null)
                {
                    Affichage.affiche("Attaque impossible, séléctionnez une entité valide");
                    return false;
                }
                else if(e.getType() == ennemi.getType())
                {
                    Affichage.affiche("Friendly fire off, selectionner un ennemi valide");
                    return false;
                }
                e.attaquer(ennemi);
                return true;
            case "mj ":
                Affichage.affiche(choix[1]);
                return false;
            case "dep":
                x = choix[1].toUpperCase().charAt(0);
                if(!verifCharValide(x))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                pos[0] = ((a + x) % 26);
                pos[1] = Integer.parseInt(choix[1].substring(1))-1;
                if(!verifEntierValide(pos[1]) || !deplacementPossible(e, pos))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                m_donjon.deplacerEntitee(e, pos);
                return true;
            default:
                if(e.getType() == TypeEntitee.PERSONNAGE)
                {
                    Personnage p = (Personnage) e;
                    return tourPerso(p, choix, objetARecup);
                }
                else {
                    Affichage.affiche("Sélectionnez une action valide");
                    return false;
                }
        }
    }
    public boolean tourPerso(Personnage p, String[] choix, boolean objetARecup)
    {
        switch (choix[0])
        {
            case "com":
                Affichage.affiche(choix[1]);
                return false;
            case "equ":
                int ie = Integer.parseInt(choix[1]) -1;
                if(ie >= p.getTailleInventaire())
                {
                    Affichage.affiche("Equipement sélectionné invalide");
                    return false;
                }
                p.equiper(ie);
                return true;
            case "ram":
                if(objetARecup)
                {
                    Equipement equipRam = m_donjon.getEquipAPos(m_donjon.getPosEntitee(p));
                    p.ramasserObjet(equipRam);
                    m_donjon.supprEquip(equipRam);
                    return true;
                }
                Affichage.affiche("Sélectionnez une action valide");
                return false;
            default:
                Affichage.affiche("Sélectionnez une action valide");
                return false;
        }
    }
    public boolean tourMJ(String[] choix)
    {
        char a = 'A';
        char x;
        int[] pos = new int[2];
        Entitee e;
        switch (choix[0]) {
            case "att":
                x = choix[1].toUpperCase().charAt(0);
                if(!verifCharValide(x))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                pos[0] = ((a + x) % 26);
                pos[1] = Integer.parseInt(choix[1].substring(1))-1;
                if (!verifEntierValide(pos[1])) {
                    Affichage.affiche("Attaque impossible, sélectionnez un emplacement valide");
                    return false;
                }

                e = m_donjon.getEntiteeAPos(pos);
                if (e == null) {
                    Affichage.affiche("Attaque impossible, séléctionnez une entité valide");
                    return false;
                }
                attaqueEntitee(e);
                return true;
            case "dep":
                x = choix[1].toUpperCase().charAt(0);
                if(!verifCharValide(x))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                pos[0] = ((a + x) % 26);
                pos[1] = Integer.parseInt(choix[1].substring(1))-1;
                if(!verifEntierValide(pos[1]))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                e = m_donjon.getEntiteeAPos(pos);
                x = choix[2].toUpperCase().charAt(0);
                if(!verifCharValide(x))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                pos[0] = ((a + x) % 26);
                pos[1] = Integer.parseInt(choix[1].substring(1))-1;
                if(!verifEntierValide(pos[1]) || !deplacementPossible(e, pos))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                m_donjon.deplacerEntitee(e, pos);
                return true;
            case "obs":
                x = choix[1].toUpperCase().charAt(0);
                if(!verifCharValide(x))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                pos[0] = ((a + x) % 26);
                pos[1] = Integer.parseInt(choix[1].substring(1))-1;
                if(!verifEntierValide(pos[1]))
                {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement valide");
                    return false;
                }
                if (!m_donjon.ajouterObstacle(pos))
                {
                    Affichage.affiche("Séléctionnez un emplacement valide");
                    return false;
                }
                else
                {
                    return  true;
                }
            default:
                Affichage.affiche("Sélectionner une action valide");
                return false;
        }
    }
    public boolean deplacementPossible(Entitee entitee, int[] pos)
    {
        int[] posEntitee = m_donjon.getPosEntitee(entitee);
        int distance = (int)Math.sqrt(Math.pow(pos[0] - posEntitee[0], 2) + Math.pow(pos[1] - posEntitee[1], 2));
        return pos[0] < m_donjon.getLongueur() && pos[1] < m_donjon.getLargeur() && entitee.seDeplacer(abs(distance)) && !m_donjon.verifAEmplacement(pos);
    }
    public boolean attaquePossible(Entitee entitee, int[] pos)
    {
        int[] posEntitee = m_donjon.getPosEntitee(entitee);
        int distance = (int)Math.sqrt(Math.pow(pos[0] - posEntitee[0], 2) + Math.pow(pos[1] - posEntitee[1], 2));
        return pos[0] < m_donjon.getLongueur() && pos[1] < m_donjon.getLargeur() && entitee.getPorteeArme()>=distance;
    }
    public boolean verifEntierValide(int e)
    {
        return e < m_donjon.getLargeur() && e > 0;
    }
    public boolean verifCharValide(char c)
    {
        return c >= 'A' && c <= 'Z';
    }
    public void equiperPerso()
    {
        for (Personnage personnage : m_perso) {
            personnage.choisirArmure();
            personnage.choisirArme();
        }
    }
    public void defaite(Entitee e){
        Affichage.defaite(e);
    }
    public static void attaqueEntitee(Entitee e)
    {
        int nbDes, degats, resultDes;
        int somme = 0;
        String txt = "(";
        Affichage.affiche("Combien de dès voulez vous lancer ?");
        nbDes = Scanner.demandeInt();
        Affichage.affiche("Combien de faces ont les dès ?");
        degats = Scanner.demandeInt();
        for (int i = 0; i<nbDes; i++)
        {
            resultDes = Des.lancerDes(degats);
            somme += resultDes;
            txt += resultDes+"+";
        }
        txt = somme+txt.substring(0, txt.length()-1)+")";
        e.sePrendreDegats(somme);
        Affichage.affiche("vous avez infliger " + txt +" degats");
    }
}
