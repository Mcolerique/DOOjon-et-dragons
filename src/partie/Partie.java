package partie;

import des.Des;
import donjon.Donjon;
import entitee.Entitee;
import entitee.TypeEntitee;
import entitee.personnage.Personnage;
import entitee.personnage.sort.Sort;
import equipement.Equipement;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * La classe Partie gère le déroulement d'une partie de jeu.
 */
public class Partie {
    private Donjon m_donjon;
    private final ArrayList<Personnage> m_perso;
    private int m_numDonjon;

    /**
     * Constructeur de la classe Partie.
     * Initialise les personnages et le donjon.
     */
    public Partie() {
        m_perso = miseEnPlacePerso();
        m_donjon = null;
        m_numDonjon = 0;
    }

    /**
     * Crée et retourne une liste de personnages pour la partie.
     *
     * @return la liste des personnages créés
     */
    public ArrayList<Personnage> miseEnPlacePerso() {
        ArrayList<Personnage> perso = new ArrayList<>();
        int choix;
        Affichage.affiche("Combien de personnages joueurs voulez-vous créer ?");
        choix = Scanner.demandeInt();
        for (int i = 0; i < choix; i++) {
            perso.add(Personnage.creePersonnage());
        }
        return perso;
    }

    /**
     * Lance la partie et gère les tours de jeu jusqu'à la victoire ou la défaite.
     */
    public void lancerPartie() {
        int numTour = 1;
        boolean defaite = false;
        while (m_numDonjon < 3) {
            m_donjon = Donjon.creerDonjon(m_perso);
            equiperPerso();
            ArrayList<Entitee> ordreEntite = m_donjon.lancerInitiative();
            while (!m_donjon.estVaincu() && !defaite) {
                for (int j = 0; j < ordreEntite.size(); j++) {
                    Affichage.afficheTour(ordreEntite, j, m_numDonjon + 1, numTour);
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
                    if (m_donjon.estVaincu()) break;
                }
                numTour++;
            }
            if (defaite) break;
            Affichage.victoireDonjon(m_numDonjon + 1);
            m_numDonjon++;
        }
    }

    /**
     * Exécute un tour pour une entité donnée.
     *
     * @param e l'entité pour laquelle exécuter le tour
     */
    public void executerTour(Entitee e) {
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
                try {
                    choix = Scanner.demandeString();
                    if (choix.isEmpty()) continue;
                    finAction = traiterTour(e, choix.split(" "), objetARecup);
                } catch (Exception ex) {
                    Affichage.affiche("Erreur de saisie. Réessayez.");
                }
            }
            Affichage.affiche("MJ, voulez-vous faire une action ? y/n");
            try {
                choix = Scanner.demandeString();
                finAction = false;
                if (choix.equals("y")) {
                    while (!finAction) {
                        Affichage.afficheActionMJ();
                        try {
                            choix = Scanner.demandeString();
                            finAction = actionMJ(choix.split(" "));
                        } catch (Exception ex) {
                            Affichage.affiche("Erreur de saisie MJ. Réessayez.");
                        }
                    }
                }
            } catch (Exception ex) {
                Affichage.affiche("Erreur de saisie. Tour MJ ignoré.");
            }
            i++;
        }
    }

    /**
     * Traite le tour d'une entité en fonction de son choix d'action.
     *
     * @param e l'entité qui effectue l'action
     * @param choix le choix d'action de l'entité
     * @param objetARecup indique s'il y a un objet à récupérer
     * @return vrai si l'action est terminée, faux sinon
     */
    public boolean traiterTour(Entitee e, String[] choix, boolean objetARecup) {
        int[] pos = new int[2];
        if (choix.length > 1) {
            pos = extrairePosition(choix[1]);
        }
        try {
            switch (choix[0]) {
                case "att":
                    if (pos == null) {
                        Affichage.affiche("\u001B[31m Emplacement non valide.\u001B[0m");
                        return false;
                    } else if (!attaquePossible(e, pos)) {
                        Affichage.affiche("\u001B[31m Attaque impossible, sélectionnez un emplacement à portée\u001B[0m");
                        return false;
                    }
                    Entitee ennemi = m_donjon.getEntiteeAPos(pos);
                    if (ennemi == null) {
                        Affichage.affiche("\u001B[31m Attaque impossible, sélectionnez une entité valide\u001B[0m");
                        return false;
                    } else if (e.getType() == ennemi.getType()) {
                        Affichage.affiche("\u001B[31m Friendly fire is off, sélectionnez un ennemi valide\u001B[0m");
                        return false;
                    }
                    e.attaquer(ennemi);
                    return true;
                case "mj":
                    Affichage.affiche(concatString(choix));
                    return false;
                case "dep":
                    if (pos == null) {
                        Affichage.affiche("\u001B[31m Emplacement non valide.\u001B[0m");
                        return false;
                    } else if (!deplacementPossible(e, pos)) {
                        Affichage.affiche("\u001B[31m Déplacement impossible, sélectionnez un emplacement à portée\u001B[0m");
                        return false;
                    }
                    m_donjon.deplacerEntitee(e, pos);
                    return true;
                case "pas":
                    return true;
                default:
                    if (e.getType() == TypeEntitee.PERSONNAGE) {
                        return tourPerso((Personnage) e, choix, objetARecup);
                    } else {
                        Affichage.affiche("\u001B[31m Sélectionnez une action valide\u001B[0m");
                        return false;
                    }
            }
        } catch (Exception ex) {
            Affichage.affiche("\u001B[31m Erreur lors du traitement de l'action. Réessayez.\u001B[0m");
            return false;
        }
    }

    /**
     * Traite le tour d'un personnage en fonction de son choix d'action.
     *
     * @param p le personnage qui effectue l'action
     * @param choix le choix d'action du personnage
     * @param objetARecup indique s'il y a un objet à récupérer
     * @return vrai si l'action est terminée, faux sinon
     */
    public boolean tourPerso(Personnage p, String[] choix, boolean objetARecup) {
        try {
            switch (choix[0]) {
                case "com":
                    Affichage.affiche(concatString(choix));
                    return false;
                case "equ":
                    int ie = Integer.parseInt(choix[1]) - 1;
                    if (ie > p.getTailleInventaire()) {
                        Affichage.affiche("\u001B[31m Équipement sélectionné invalide \u001B[0m");
                        return false;
                    }
                    p.equiper(ie);
                    return true;
                case "ram":
                    if (objetARecup) {
                        Equipement equipRam = m_donjon.getEquipAPos(m_donjon.getPosEntitee(p));
                        p.ramasserObjet(equipRam);
                        m_donjon.supprEquip(equipRam);
                        return true;
                    }
                    Affichage.affiche("\u001B[31m Aucun objet à ramasser à cette position \u001B[0m");
                    return false;
                case "sor":
                    int is = Integer.parseInt(choix[1]) - 1;
                    if (is > p.getTailleSort()) {
                        Affichage.affiche("\u001B[31m Sort sélectionné invalide \u001B[0m");
                        return false;
                    }
                    return utiliserSort(p.getSort(is));
                default:
                    Affichage.affiche("\u001B[31m Sélectionnez une action valide \u001B[0m");
                    return false;
            }
        } catch (Exception ex) {
            Affichage.affiche("\u001B[31m Erreur dans le tour du personnage. Réessayez. \u001B[0m");
            return false;
        }
    }

    /**
     * Traite une action du maître du jeu.
     *
     * @param choix le choix d'action du maître du jeu
     * @return vrai si l'action est terminée, faux sinon
     */
    public boolean actionMJ(String[] choix) {
        if (choix.length < 2) {
            Affichage.affiche("\u001B[31m Commande MJ incomplète \u001B[0m");
            return false;
        }
        int[] pos = extrairePosition(choix[1]);

        Entitee e;
        switch (choix[0]) {
            case "att":
                if (pos == null) {
                    Affichage.affiche("\u001B[31m Emplacement non valide. \u001B[0m");
                    return false;
                }
                e = m_donjon.getEntiteeAPos(pos);
                if (e == null) {
                    Affichage.affiche("\u001B[31m Attaque impossible, sélectionnez une entité valide \u001B[0m");
                    return false;
                }
                attaqueEntitee(e);
                return true;
            case "dep":
                if (choix.length < 3) {
                    Affichage.affiche("\u001B[31m Commande déplacement MJ incomplète \u001B[0m");
                    return false;
                } else if (pos == null) {
                    Affichage.affiche("\u001B[31m Emplacement départ non valide.\u001B[0m");
                    return false;
                }
                e = m_donjon.getEntiteeAPos(pos);
                if (e == null) {
                    Affichage.affiche("\u001B[31m Déplacement impossible, sélectionnez une entité\u001B[0m");
                    return false;
                }
                int[] newPos = extrairePosition(choix[2]);
                if (newPos == null) {
                    Affichage.affiche("\u001B[31m Emplacement d'arrivée non valide.\u001B[0m");
                    return false;
                }
                m_donjon.deplacerEntitee(e, newPos);
                return true;
            case "obs":
                return m_donjon.ajouterObstacle(pos);
            default:
                Affichage.affiche("\u001B[31m Sélectionnez une action valide\u001B[0m");
                return false;
        }
    }

    /**
     * Extrait une position à partir d'une chaîne de caractères.
     *
     * @param s la chaîne de caractères représentant la position
     * @return un tableau d'entiers représentant la position, ou null si la position est invalide
     */
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

    /**
     * Vérifie si un déplacement est possible pour une entité.
     *
     * @param entitee l'entité à déplacer
     * @param pos la position de destination
     * @return vrai si le déplacement est possible, faux sinon
     */
    public boolean deplacementPossible(Entitee entitee, int[] pos) {
        int[] posEntitee = m_donjon.getPosEntitee(entitee);
        int distance = (int) Math.sqrt(Math.pow(pos[0] - posEntitee[0], 2) + Math.pow(pos[1] - posEntitee[1], 2));
        if (distance < 1) {
            distance = 1;
        }
        return pos[0] < m_donjon.getLongueur() && pos[1] < m_donjon.getLargeur() && entitee.seDeplacer(abs(distance)) && !m_donjon.verifAEmplacement(pos);
    }

    /**
     * Vérifie si une attaque est possible pour une entité.
     *
     * @param entitee l'entité qui attaque
     * @param pos la position de la cible
     * @return vrai si l'attaque est possible, faux sinon
     */
    public boolean attaquePossible(Entitee entitee, int[] pos) {
        int[] posEntitee = m_donjon.getPosEntitee(entitee);
        int distance = (int) Math.sqrt(Math.pow(pos[0] - posEntitee[0], 2) + Math.pow(pos[1] - posEntitee[1], 2));
        return pos[0] < m_donjon.getLongueur() && pos[1] < m_donjon.getLargeur() && entitee.getPorteeArme() >= distance;
    }

    /**
     * Vérifie si un entier est valide pour une position dans le donjon.
     *
     * @param e l'entier à vérifier
     * @return vrai si l'entier est valide, faux sinon
     */
    public boolean verifEntierValide(int e) {
        return e < m_donjon.getLargeur() && e >= 0;
    }

    /**
     * Vérifie si un caractère est valide pour une position dans le donjon.
     *
     * @param c le caractère à vérifier
     * @return vrai si le caractère est valide, faux sinon
     */
    public boolean verifCharValide(char c) {
        return c >= 'A' && c <= 'Z';
    }

    /**
     * Équip les personnages avec une armure et une arme.
     */
    public void equiperPerso() {
        for (Personnage personnage : m_perso) {
            personnage.choisirArmure();
            personnage.choisirArme();
        }
    }

    /**
     * Affiche un message de défaite.
     *
     * @param e l'entité qui a causé la défaite
     */
    public void defaite(Entitee e) {
        Affichage.defaite(e);
    }

    /**
     * Attaque une entité avec des dégâts spécifiés par le maître du jeu.
     *
     * @param e l'entité à attaquer
     */
    public static void attaqueEntitee(Entitee e) {
        try {
            int nbDes, degats, resultDes;
            int somme = 0;
            String txt = "(";
            String choix;
            String[] split;

            Affichage.affiche("Entrez les dégâts que vous voulez faire (sous la forme <nbDes>d<nbFace>)");
            choix = Scanner.demandeString();
            split = choix.split("d");

            if (split.length != 2) {
                Affichage.affiche("Format invalide. Utilisez le format <nbDes>d<nbFace>, par exemple 2d6.");
                return;
            }

            nbDes = Integer.parseInt(split[0].trim());
            degats = Integer.parseInt(split[1].trim());

            if (nbDes <= 0 || degats <= 0) {
                Affichage.affiche("Le nombre de dés et de faces doit être supérieur à 0.");
                return;
            }

            for (int i = 0; i < nbDes; i++) {
                resultDes = Des.lancerDes(degats);
                somme += resultDes;
                txt += resultDes + "+";
            }

            txt = "\u001B[31m" + somme + "\u001B[0m" + txt.substring(0, txt.length() - 1) + ")";
            e.sePrendreDegats(somme);
            Affichage.affiche("Vous avez infligé " + txt + "\u001B[31m dégâts\u001B[0m");

        } catch (NumberFormatException nfe) {
            Affichage.affiche("Erreur : veuillez entrer uniquement des nombres valides dans le format <nbDes>d<nbFace>.");
        } catch (Exception ex) {
            Affichage.affiche("Une erreur s'est produite : " + ex.getMessage());
        }
    }

    /**
     * Utilise un sort spécifié.
     *
     * @param s le sort à utiliser
     * @return vrai si le sort a été utilisé avec succès, faux sinon
     */
    public boolean utiliserSort(Sort s) {
        ArrayList<Entitee> listEntite;
        String nom = s.getNom();
        switch (nom) {
            case "Guérison":
                listEntite = m_donjon.getListePersonnage();
                return s.utiliserSort(listEntite);
            case "Boogie Woogie":
                listEntite = m_donjon.getListeEntite();
                if (s.utiliserSort(listEntite)) {
                    m_donjon.echangePosEntite(listEntite.get(0), listEntite.get(1));
                    return true;
                }
                return false;
            case "Arme magique":
                listEntite = m_donjon.getListeEntite();
                return s.utiliserSort(listEntite);
            default:
                Affichage.affiche("erreur");
        }
        return false;
    }

    /**
     * Concatène les éléments d'un tableau de chaînes de caractères.
     *
     * @param tab le tableau de chaînes de caractères
     * @return la chaîne de caractères concaténée
     */
    String concatString(String[] tab) {
        String txt = "\u001B[33m";
        for (int i = 1; i < tab.length; i++) {
            txt += tab[i] + " ";
        }
        return txt + "\u001B[0m";
    }
}
