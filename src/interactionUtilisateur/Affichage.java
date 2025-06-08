package interactionUtilisateur;

import donjon.Donjon;
import entitee.Entitee;
import entitee.Monstre;
import entitee.TypeEntitee;
import entitee.personnage.Personnage;
import entitee.personnage.sort.Sort;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;

import java.util.*;

/**
 * La classe Affichage fournit des méthodes pour afficher des informations à l'utilisateur.
 */
public class Affichage {

    /**
     * Affiche un texte donné.
     *
     * @param texte le texte à afficher
     */
    public static void affiche(String texte) {
        System.out.println(texte);
    }

    /**
     * Affiche les informations relatives à un tour de jeu.
     *
     * @param e la liste des entités
     * @param i l'index de l'entité active
     * @param numDonjon le numéro du donjon
     * @param numTour le numéro du tour
     */
    public static void afficheTour(ArrayList<Entitee> e, int i, int numDonjon, int numTour) {
        StringBuilder sb = new StringBuilder();
        String separator = "*".repeat(80);

        Entitee entiteActive = e.get(i);

        sb.append(separator).append("\n");
        sb.append("Donjon ").append(numDonjon).append(":\n");

        String nomEntite = entiteActive.getNom() + " (" + entiteActive.getDescription() + ")";
        int padding = (80 - nomEntite.length()) / 2;
        sb.append(" ".repeat(Math.max(0, padding))).append(nomEntite).append("\n");

        sb.append("\n").append(separator).append("\n");

        sb.append("Tour ").append(numTour).append(":\n");

        for (int j = 0; j < e.size(); j++) {
            Entitee ent = e.get(j);

            boolean estActif = (j == i);
            boolean estMonstre = ent instanceof Monstre;

            String prefixe = estActif ? "-> " : "   ";

            String identifiant = estMonstre ? " " + ((Monstre) ent).getSymbole() + " " : ((Personnage) ent).getInitiale();
            String nomEtDesc = ent.getNom() + " (" + ent.getDescription();
            int pvActuels = ent.getPvActuelle();
            int pvMax = ent.getPv();

            sb.append(prefixe)
                    .append(String.format("%-5s", identifiant)).append(" ")
                    .append(nomEtDesc).append(", ")
                    .append(pvActuels).append("/").append(pvMax).append(")")
                    .append("\n");
        }

        affiche(sb.toString());
    }

    /**
     * Affiche la carte du donjon.
     *
     * @param d le donjon à afficher
     */
    public static void afficherDonjon(Donjon d) {
        int lignes = d.getLongueur();
        int colonnes = d.getLargeur();
        String[][] grille = new String[lignes][colonnes];
        ArrayList<int[]> obstacle = d.getObstacle();
        Hashtable<Entitee, int[]> positionEntitee = d.getPositionEntitee();
        Hashtable<Equipement, int[]> positionEquipement = d.getPositionEquipement();

        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = " . ";
            }
        }

        for (int[] pos : obstacle) {
            int x = pos[0];
            int y = pos[1];
            if (y >= 0 && y < lignes && x >= 0 && x < colonnes)
                grille[y][x] = "[ ]";
        }

        for (Map.Entry<Equipement, int[]> entry : positionEquipement.entrySet()) {
            int x = entry.getValue()[0];
            int y = entry.getValue()[1];
            if (y >= 0 && y < lignes && x >= 0 && x < colonnes)
                grille[y][x] = "\u001B[34m * \u001B[0m";
        }

        for (Map.Entry<Entitee, int[]> entry : positionEntitee.entrySet()) {
            Entitee entite = entry.getKey();
            int x = entry.getValue()[0];
            int y = entry.getValue()[1];

            String symbole;

            if (entite.getType() == TypeEntitee.PERSONNAGE) {
                String nom = "\u001B[32m" + ((Personnage) entite).getInitiale() + "\u001B[0m";
                symbole = centerTextAnsi(nom, 3);
            } else if (entite.getType() == TypeEntitee.MONSTRE) {
                String s = "\u001B[31m" + ((Monstre) entite).getSymbole() + "\u001B[0m";
                symbole = centerTextAnsi(s, 3);
            } else {
                symbole = " ? ";
            }

            if (y >= 0 && y < lignes && x >= 0 && x < colonnes)
                grille[y][x] = symbole;
        }

        StringBuilder sb = new StringBuilder();

        sb.append("    ");
        for (int j = 0; j < colonnes; j++) {
            char col = (char) ('A' + j);
            sb.append(" ").append(col).append(" ");
        }
        sb.append("\n");

        sb.append("   *");
        for (int j = 0; j < colonnes; j++) {
            sb.append("---");
        }
        sb.append("-*\n");

        for (int i = 0; i < lignes; i++) {
            sb.append(String.format("%-3d|", i + 1));
            for (int j = 0; j < colonnes; j++) {
                sb.append(grille[i][j]);
            }
            sb.append("|\n");
        }

        sb.append("   *");
        for (int j = 0; j < colonnes; j++) {
            sb.append("---");
        }
        sb.append("-*\n");

        affiche(sb.toString());
    }

    /**
     * Centre un texte en tenant compte des codes ANSI pour les couleurs.
     *
     * @param text le texte à centrer
     * @param width la largeur totale
     * @return le texte centré
     */
    private static String centerTextAnsi(String text, int width) {
        String textVisible = text.replaceAll("\u001B\\[[;\\d]*m", "");
        int padding = width - textVisible.length();
        int padStart = padding / 2;
        int padEnd = padding - padStart;
        return " ".repeat(padStart) + text + " ".repeat(padEnd);
    }

    /**
     * Affiche les actions possibles pour une entité.
     *
     * @param e l'entité
     * @param numAction le numéro de l'action
     * @param objetARecup indique s'il y a un objet à récupérer
     */
    public static void afficheAction(Entitee e, int numAction, boolean objetARecup) {
        if (e.getType() == TypeEntitee.PERSONNAGE) {
            afficheActionPerso((Personnage) e, numAction, objetARecup);
        } else {
            afficheActionMonstre((Monstre) e, numAction);
        }
    }

    /**
     * Affiche les actions possibles pour un personnage.
     *
     * @param e le personnage
     * @param numAction le numéro de l'action
     * @param objetARecup indique s'il y a un objet à récupérer
     */
    public static void afficheActionPerso(Personnage e, int numAction, boolean objetARecup) {
        StringBuilder sb = new StringBuilder();

        sb.append(e.getNom()).append("\n");
        sb.append("  Vie : ").append(e.getPvActuelle()).append("/").append(e.getPv()).append("\n");

        Armure armure = e.getArmure();
        if (armure != null) {
            sb.append("  Armure: ").append(armure.getNom()).append("\n");
        } else {
            sb.append("  Armure: aucune\n");
        }

        Arme arme = e.getArme();
        if (arme != null) {
            sb.append("  Arme: ").append(arme.getNom() != null ? arme.getNom() : "inconnue")
                    .append(" (dégât: ").append(arme.getDegats())
                    .append(", portée: ").append(arme.getPortee()).append(")\n");
        } else {
            sb.append("  Arme: aucune\n");
        }

        ArrayList<Equipement> inventaire = e.getInventaire();
        if (inventaire != null && !inventaire.isEmpty()) {
            sb.append("  Inventaire:");
            for (int i = 0; i < inventaire.size(); i++) {
                Equipement equip = inventaire.get(i);
                sb.append(" [").append(i + 1).append("] ").append(equip != null ? equip.getNom() : "inconnu");
            }
            sb.append("\n");
        } else {
            sb.append("  Inventaire: vide\n");
        }

        ArrayList<Sort> sort = e.getSort();
        if (sort != null && !sort.isEmpty()) {
            sb.append("  Sort:");
            for (int i = 0; i < sort.size(); i++) {
                Sort equip = sort.get(i);
                sb.append(" [").append(i + 1).append("] ").append(equip != null ? equip.getNom() : "inconnu");
            }
            sb.append("\n");
        } else {
            sb.append("  Sort: aucun\n");
        }

        sb.append("  Force: ").append(e.getForce()).append("\n");
        sb.append("  Dextérité: ").append(e.getDexterite()).append("\n");
        sb.append("  Vitesse: ").append(e.getVitesse()).append("\n");

        sb.append("\n");

        int actionsRestantes = 3 - numAction;
        sb.append(e.getNom()).append(", il vous reste ").append(actionsRestantes)
                .append(actionsRestantes == 1 ? " action" : " actions")
                .append(", que souhaitez-vous faire ?\n");

        sb.append("  - laisser le maître du jeu commenter l'action précédente (mj <texte>)\n");
        sb.append("  - commenter l'action précédente (com <texte>)\n");
        sb.append("  - attaquer (att <Case>)\n");
        sb.append("  - se déplacer (dep <Case>)\n");
        sb.append("  - s'équiper (equ <numero equipement>)\n");
        if (sort != null && !sort.isEmpty()) {
            sb.append("  - lancer un sort (sor <numero sort>)\n");
        }
        if (objetARecup) {
            sb.append("  - ramasser un objet (ram)\n");
        }
        sb.append("  - passer l'action (pas)\n");

        affiche(sb.toString());
    }

    /**
     * Affiche les actions possibles pour un monstre.
     *
     * @param e le monstre
     * @param numAction le numéro de l'action
     */
    public static void afficheActionMonstre(Monstre e, int numAction) {
        StringBuilder sb = new StringBuilder();

        sb.append(e.getNom()).append("\n");
        sb.append("  Vie : ").append(e.getPvActuelle()).append("/").append(e.getPv()).append("\n");

        String armure = e.getNomArmure();
        sb.append("  Armure: ").append(armure != null ? armure : "aucune").append("\n");

        Arme arme = e.getArme();
        if (arme != null) {
            sb.append("  Arme: ").append(arme.getNom())
                    .append(" (dégâts: ").append(arme.getDegats())
                    .append(", portée: ").append(arme.getPortee()).append(")\n");
        } else {
            sb.append("  Arme: aucune\n");
        }

        sb.append("  Force: ").append(e.getForce()).append("\n");
        sb.append("  Dextérité: ").append(e.getDexterite()).append("\n");
        sb.append("  Vitesse: ").append(e.getVitesse()).append("\n");

        sb.append("\n");

        int actionsRestantes = 3 - numAction;
        sb.append(e.getNom()).append(", il vous reste ").append(actionsRestantes)
                .append(actionsRestantes == 1 ? " action" : " actions")
                .append(", que souhaitez vous faire ?\n");

        sb.append("  - laisser le maître du jeu commenter l'action précédente (mj <texte>)\n");
        sb.append("  - attaquer (att <Case>)\n");
        sb.append("  - se déplacer (dep <Case>)\n");
        sb.append("  - passer l'action (pas)\n");

        affiche(sb.toString());
    }

    /**
     * Affiche les actions possibles pour le maître du jeu.
     */
    public static void afficheActionMJ() {
        affiche("Que souhaitez-vous faire ?");
        affiche("  - attaquer une entité (att <Case>)");
        affiche("  - déplacer une entité (dep <CaseEntité> <CaseDestination>)");
        affiche("  - ajouter un obstacle (obs <Case>)");
    }

    /**
     * Affiche une liste d'entités.
     *
     * @param list la liste des entités à afficher
     */
    public static void afficheListeEntitee(ArrayList<Entitee> list) {
        String txt = "0.Rien sélectionner   ";
        for (int j = 0; j < list.size(); j++) {
            txt += j + 1 + "." + list.get(j).toString() + "   ";
        }
        affiche(txt);
    }

    /**
     * Affiche une liste d'équipements.
     *
     * @param list la liste des équipements à afficher
     */
    public static void listeEquipement(ArrayList<Equipement> list) {
        String txt = "0.Rien sélectionner   ";
        for (int j = 0; j < list.size(); j++) {
            txt += j + 1 + "." + list.get(j).toString() + "   ";
        }
        affiche(txt);
    }

    /**
     * Affiche un tableau d'objets.
     *
     * @param tab le tableau d'objets à afficher
     */
    public static void selectionTableau(Object[] tab) {
        String txt = "";
        for (int j = 0; j < tab.length; j++) {
            txt += j + 1 + "." + tab[j].toString() + "   ";
        }
        affiche(txt);
    }

    /**
     * Affiche une liste de monstres.
     *
     * @param monstres la liste des monstres à afficher
     */
    public static void afficherMonstre(ArrayList<Monstre> monstres) {
        for (int i = 0; i < monstres.size(); i++) {
            System.out.println("\t Monstre n°" + i + " : " + monstres.get(i).getAppellation() + "\n");
        }
        System.out.println("\n\n");
    }

    /**
     * Affiche une liste d'équipements.
     *
     * @param equipements la liste des équipements à afficher
     */
    public static void afficherEquip(ArrayList<Equipement> equipements) {
        for (int i = 0; i < equipements.size(); i++) {
            System.out.println("\t Équipement n°" + i + " : " + equipements.get(i).getNom() + "\n");
        }
        System.out.println("\n\n");
    }

    /**
     * Affiche un message de défaite.
     *
     * @param e l'entité qui a causé la défaite
     */
    public static void defaite(Entitee e) {
        affiche("\u001B[31mVous avez perdu car " + e.toString() + " est mort\u001B[0m");
    }

    /**
     * Affiche un message de victoire pour un donjon.
     *
     * @param numDonjon le numéro du donjon
     */
    public static void victoireDonjon(int numDonjon) {
        affiche("\u001B[32mFélicitations, vous avez triomphé du " + numDonjon + " donjon\u001B[0m");
    }
}
