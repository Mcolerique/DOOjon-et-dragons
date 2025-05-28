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
        int numTour = 1;
        boolean defaite = false;
        while (m_numDonjon < 3 && !defaite) {
            m_donjon = Donjon.creerDonjon(m_perso);
            equiperPerso();
            ArrayList<Entitee> ordreEntite = m_donjon.lancerInitiative();
            while (!m_donjon.estVaincu() && !defaite) {
                for (int j = 0; j < ordreEntite.size(); j++) {
                    Affichage.afficheTour(ordreEntite, j, m_donjon, m_numDonjon + 1, numTour);
                    Entitee e = ordreEntite.get(j);
                    if (e.estVivant()) {
                        executerTour(e);
                    }

                    ArrayList<Entitee> morts = new ArrayList<>();
                    for (Entitee ent : new ArrayList<>(ordreEntite)) {
                        if (!ent.estVivant()) {
                            m_donjon.supprEntite(ent);
                            if (ent.getType() == TypeEntitee.PERSONNAGE) {
                                defaite(ent);
                                defaite = true;
                                break;
                            }
                            morts.add(ent);
                        }
                    }
                    ordreEntite.removeAll(morts);
                    if (defaite) break;
                }
                numTour++;
            }
            if (defaite) break;
            Affichage.victoireDonjon(m_numDonjon + 1);
            m_numDonjon++;
        }
    }
    public void executerTour(Entitee e)
    {
        String choix;
        boolean objetARecup;
        boolean finAction;
        int i = 0;
        while (i < 3 && e.estVivant()) {
            finAction = false;
            while (!finAction) {
                objetARecup = m_donjon.equipAPos(m_donjon.getPosEntitee(e));
                Affichage.afficherDonjon(m_donjon);
                Affichage.afficheAction(e, i, objetARecup);
                choix = Scanner.demandeString();
                if (choix.isEmpty()) continue;
                finAction = traiterTour(e, choix.split(" "), objetARecup);
            }
            Affichage.affiche("mj voulez vous faire une action ? y/n");
            choix = Scanner.demandeString();
            finAction = false;
            if(choix.equals("y"))
            {
                while (!finAction) {
                    Affichage.afficheActionMJ();
                    choix = Scanner.demandeString();
                    finAction = actionMJ(choix.split(" "));
                }
            }
            i++;
        }
    }
    public boolean traiterTour(Entitee e, String[] choix, boolean objetARecup)
    {
        if (choix.length < 2) {
            Affichage.affiche("Commande incomplète.");
            return false;
        }
        int[] pos = extrairePosition(choix[1]);
        switch (choix[0]) {
            case "att":
                if (pos == null )
                {
                    Affichage.affiche("Emplacement non valide.");
                    return false;
                }
                else if (!attaquePossible(e, pos)) {
                    Affichage.affiche("Attaque impossible, sélectionnez un emplacement a porté");
                    return false;
                }
                Entitee ennemi = m_donjon.getEntiteeAPos(pos);
                if (ennemi == null) {
                    Affichage.affiche("Attaque impossible, sélectionnez une entité valide");
                    return false;
                } else if (e.getType() == ennemi.getType()) {
                    Affichage.affiche("Friendly fire off, sélectionnez un ennemi valide");
                    return false;
                }
                e.attaquer(ennemi);
                return true;
            case "mj ":
                Affichage.affiche(choix[1]);
                return false;
            case "dep":
                if (pos == null )
                {
                    Affichage.affiche("Emplacement non valide.");
                    return false;
                }
                else if (!deplacementPossible(e, pos)) {
                    Affichage.affiche("Déplacement impossible, sélectionnez un emplacement a porté");
                    return false;
                }
                m_donjon.deplacerEntitee(e, pos);
                return true;
            default:
                if (e.getType() == TypeEntitee.PERSONNAGE) {
                    return tourPerso((Personnage) e, choix, objetARecup);
                } else {
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
                if(ie > p.getTailleInventaire())
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
    public boolean actionMJ(String[] choix)
    {
        if (choix.length < 2) {
            Affichage.affiche("Commande MJ incomplète");
            return false;
        }
        int[] pos = extrairePosition(choix[1]);

        Entitee e;
        switch (choix[0]) {
            case "att":
                if (pos == null )
                {
                    Affichage.affiche("Emplacement non valide.");
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
                if (choix.length < 3) {
                    Affichage.affiche("Commande déplacement MJ incomplète");
                    return false;
                }
                else if (pos == null )
                {
                    Affichage.affiche("Emplacement départ non valide.");
                    return false;
                }
                e = m_donjon.getEntiteeAPos(pos);
                if (e == null) {
                    Affichage.affiche("Déplacement impossible, séléctionnez une entité");
                    return false;
                }
                int[] newPos = extrairePosition(choix[2]);
                if (newPos == null ) {
                    Affichage.affiche("Emplacement d'arriver non valide.");
                    return false;
                }
                m_donjon.deplacerEntitee(e, newPos);
                return true;
            case "obs":
                return m_donjon.ajouterObstacle(pos);
            default:
                Affichage.affiche("Sélectionner une action valide");
                return false;
        }
    }
    public int[] extrairePosition(String s) {
        try {
            if (s.length() < 2) return null;
            char col = s.toUpperCase().charAt(0);
            if (!verifCharValide(col)) return null;
            int row = Integer.parseInt(s.substring(1)) - 1;
            if (!verifEntierValide(row)) return null;
            return new int[]{col - 'A', row};
        } catch (Exception e) {
            return null;
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
        return e < m_donjon.getLargeur() && e >= 0;
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
