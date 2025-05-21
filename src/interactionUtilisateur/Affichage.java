package interactionUtilisateur;

import donjon.Donjon;
import entitee.Entitee;
import entitee.Monstre;
import entitee.personnage.Personnage;
import equipement.Equipement;
import equipement.arme.Arme;

import java.util.*;

public class Affichage {
    public static void affiche(String texte)
    {
        System.out.println(texte);
    }
    public static void afficheTour(ArrayList<Entitee> e, int i, Donjon d, int numDonjon, int numTour)
    {
        StringBuilder sb = new StringBuilder();
        String separator = "*".repeat(80);

        Entitee entiteActive = e.get(i);

        // Ligne 1 : Donjon X
        sb.append(separator).append("\n");
        sb.append("Donjon ").append(numDonjon).append(":\n");

        // Ligne 2 : Nom complet centré
        String nomEntite = entiteActive.getNom() + " (" + entiteActive.getDescription() + ")";
        int padding = (80 - nomEntite.length()) / 2;
        sb.append(" ".repeat(Math.max(0, padding))).append(nomEntite).append("\n");

        sb.append("\n").append(separator).append("\n");

        // Ligne 3 : Tour X
        sb.append("Tour ").append(numTour).append(":\n");

        // Liste des entités
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

        // Affichage final
        affiche(sb.toString());
    }
    public static void afficherDonjon(Donjon d) {
        int lignes = d.getLongueur();
        int colonnes = d.getLargeur();
        String[][] grille = new String[lignes][colonnes];
        ArrayList<int[]> obstacle = d.getObstacle();
        Hashtable<Entitee, int[]> positionEntitee = d.getPositionEntitee();
        Hashtable<Equipement, int[]> positionEquipement = d.getPositionEquipement();

        // Initialisation de la grille vide
        for (int i = 0; i < lignes; i++) {
            for (int j = 0; j < colonnes; j++) {
                grille[i][j] = " . ";
            }
        }

        // Obstacles : [ ]
        for (int[] pos : obstacle) {
            int x = pos[0];
            int y = pos[1];
            grille[x][y] = "[ ]";
        }

        // Équipements : *
        for (Map.Entry<Equipement, int[]> entry : positionEquipement.entrySet()) {
            int x = entry.getValue()[0];
            int y = entry.getValue()[1];
            grille[x][y] = " * ";
        }

        // Entités : initiales pour Personnage, symbole pour Monstre
        for (Map.Entry<Entitee, int[]> entry : positionEntitee.entrySet()) {
            Entitee entite = entry.getKey();
            int x = entry.getValue()[0];
            int y = entry.getValue()[1];

            String symbole = " ? ";

            if (entite instanceof Personnage) {
                String nom = ((Personnage) entite).getInitiale(); // Ex: "Alt"
                symbole = " " + nom.charAt(0) + " ";
            } else if (entite instanceof Monstre) {
                symbole = " " + ((Monstre) entite).getSymbole() + " ";
            }

            grille[x][y] = symbole;
        }

        // Construction de l'affichage
        StringBuilder sb = new StringBuilder();

        // En-tête colonnes (A B C ...)
        sb.append("     ");
        for (char col = 'A'; col < 'A' + colonnes; col++) {
            sb.append(" ").append(col).append(" ");
        }
        sb.append("\n");

        sb.append("   *");
        for (int j = 0; j < colonnes; j++) {
            sb.append("---");
        }
        sb.append("-*\n");

        // Corps du donjon
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

        // Affichage final
        affiche(sb.toString());
    }
    public static void afficheAction(Entitee e, int numAction, boolean objetARecup)
    {
        if (e.getClass() == Personnage.class)
        {
            afficheActionPerso((Personnage) e, numAction, objetARecup);
        }
        else
        {
            afficheActionMonstre((Monstre) e, numAction);
        }
    }
    public static void afficheActionPerso(Personnage e, int numAction, boolean objetARecup)
    {
        StringBuilder sb = new StringBuilder();

        // Nom et stats
        sb.append(e.getNom()).append("\n");
        sb.append("  Vie : ").append(e.getPvActuelle()).append("/").append(e.getPv()).append("\n");

        // Armure
        String armure = e.getNomArmure();
        sb.append("  Armure: ").append(armure != null ? armure : "aucune").append("\n");

        // Arme
        Arme arme = e.getArme();
        if (arme != null) {
            sb.append("  Arme: ").append(arme.getNom())
                    .append(" (dégât: ").append(arme.getDegats())
                    .append(", portée: ").append(arme.getPortee()).append(")\n");
        } else {
            sb.append("  Arme: aucune\n");
        }

        // Inventaire
        ArrayList<Equipement> inventaire = e.getInventaire();
        if (inventaire != null && !inventaire.isEmpty()) {
            sb.append("  Inventaire:");
            for (int i = 0; i < inventaire.size(); i++) {
                sb.append(" [").append(i + 1).append("] ").append(inventaire.get(i).getNom());
            }
            sb.append("\n");
        } else {
            sb.append("  Inventaire: vide\n");
        }

        // Caractéristiques
        sb.append("  Force: ").append(e.getForce()).append("\n");
        sb.append("  Dextérité: ").append(e.getDexterite()).append("\n");
        sb.append("  Vitesse: ").append(e.getVitesse()).append("\n");

        sb.append("\n");

        // Actions restantes
        int actionsRestantes = 3 - numAction;
        sb.append(e.getNom()).append(", il vous reste ").append(actionsRestantes)
                .append(actionsRestantes == 1 ? " action" : " actions")
                .append(", que souhaitez vous faire ?\n");

        // Menu d’actions
        sb.append("  - laisser le maître du jeu commenter l'action précédente (mj <texte>)\n");
        sb.append("  - commenter action précédente (com <texte>)\n");
        sb.append("  - attaquer (att <Case>)\n");
        sb.append("  - se déplacer (dep <Case>)\n");
        sb.append("  - s'équiper (equ <numero equipement>)\n");
        if(objetARecup)
        {
            sb.append("  - ramasser objet (ram)\n");
        }

        // Affichage final
        affiche(sb.toString());
    }
    public static void afficheActionMonstre(Monstre e, int numAction)
    {
        StringBuilder sb = new StringBuilder();

        // Nom et stats
        sb.append(e.getNom()).append("\n");
        sb.append("  Vie : ").append(e.getPvActuelle()).append("/").append(e.getPv()).append("\n");

        // Armure
        String armure = e.getNomArmure();
        sb.append("  Armure: ").append(armure != null ? armure : "aucune").append("\n");

        // Arme
        Arme arme = e.getArme();
        if (arme != null) {
            sb.append("  Arme: ").append(arme.getNom())
                    .append(" (dégâts: ").append(arme.getDegats())
                    .append(", portée: ").append(arme.getPortee()).append(")\n");
        } else {
            sb.append("  Arme: aucune\n");
        }

        // Caractéristiques
        sb.append("  Force: ").append(e.getForce()).append("\n");
        sb.append("  Dextérité: ").append(e.getDexterite()).append("\n");
        sb.append("  Vitesse: ").append(e.getVitesse()).append("\n");

        sb.append("\n");

        // Actions restantes
        int actionsRestantes = 3 - numAction;
        sb.append(e.getNom()).append(", il vous reste ").append(actionsRestantes)
                .append(actionsRestantes == 1 ? " action" : " actions")
                .append(", que souhaitez vous faire ?\n");

        // Menu d’actions
        sb.append("  - commenter action précédente (com <texte>)\n");
        sb.append("  - attaquer (att <Case>)\n");
        sb.append("  - se déplacer (dep <Case>)\n");

        // Affichage final
        affiche(sb.toString());
    }
    public static void afficheListeEntitee(ArrayList<Entitee> list){
        String txt = "0.Rien sélectionner   ";
        for (int j = 0; j<list.size();j++)
        {
            txt += j+1+"."+list.get(j).toString() + "   ";
        }
        affiche(txt);
    }
    public static void listeEquipement(ArrayList<Equipement> list)
    {
        String txt = "0.Rien sélectionner   ";
        for (int j = 0; j<list.size();j++)
        {
            txt += j+1+"."+list.get(j).toString() + "   ";
        }
        affiche(txt);
    }
    public static void selectionTableau(Object[] tab)
    {
        String txt = "0.Rien sélectionner   ";
        for (int j = 0; j<tab.length;j++)
        {
            txt += j+1+"."+tab[j].toString() + "   ";
        }
        affiche(txt);
    }
    public static void defaite(Entitee e)
    {
        affiche("Vous avez perdu car "+e.toString()+" est mort");
    }
    public static void victoireDonjon(int numDonjon)
    {
        affiche("Félicitation, vous avez triomphé du "+numDonjon+" donjon");
    }
}
