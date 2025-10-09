package entitee.personnage.sort;

import entitee.Entitee;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

/**
 * La classe BoogieWoogie représente un sort permettant d'échanger les positions de deux entités dans le jeu.
 * Elle hérite de la classe Sort.
 */
public class BoogieWoogie extends Sort {

    /**
     * Construit un sort BoogieWoogie avec un nom et une description par défaut.
     */
    public BoogieWoogie() {
        super("Boogie Woogie", "Échangez la place de deux entités au choix");
    }

    /**
     * Utilise le sort BoogieWoogie pour échanger les positions de deux entités sélectionnées.
     *
     * @param listEntite la liste des entités disponibles pour l'échange
     * @return vrai si le sort a été utilisé avec succès, faux sinon
     */
    @Override
    public boolean utiliserSort(ArrayList<Entitee> listEntite) {
        if (listEntite.size() < 2) {
            Affichage.affiche("Pas assez d'entités pour utiliser ce sort.");
            return false;
        }

        try {
            Affichage.afficheListeEntitee(listEntite);
            Affichage.affiche("Sélectionnez deux entités à échanger (format : x;y)");

            String[] choix = Scanner.demandeString().split(";");
            if (choix.length != 2) {
                Affichage.affiche("Erreur : vous devez sélectionner exactement deux entités séparées par ';'.");
                return false;
            }

            int idx1 = Integer.parseInt(choix[0].trim()) - 1;
            int idx2 = Integer.parseInt(choix[1].trim()) - 1;

            if (idx1 < 0 || idx1 >= listEntite.size() || idx2 < 0 || idx2 >= listEntite.size()) {
                Affichage.affiche("Erreur : index(s) invalide(s).");
                return false;
            }

            Entitee e1 = listEntite.get(idx1);
            Entitee e2 = listEntite.get(idx2);

            // On peut soit modifier listEntite (comme tu fais), soit exécuter l’effet ici
            listEntite.clear();
            listEntite.add(e1);
            listEntite.add(e2);

            Affichage.affiche(e1.getNom() + " et " + e2.getNom() + " échangent leur position.");
            return true;
        } catch (NumberFormatException e) {
            Affichage.affiche("Erreur : entrée non valide, veuillez entrer deux entiers séparés par ';'.");
        } catch (Exception e) {
            Affichage.affiche("Une erreur s'est produite lors de l'utilisation du sort : " + e.getMessage());
        }
        return false;
    }
}
