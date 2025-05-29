package entitee.personnage.sort;

import des.Des;
import entitee.Entitee;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

public class Guerison extends Sort{
    public Guerison()
    {
        super("Guérison", "Soigne un personnage de 1d10");
    }
    @Override
    public boolean utiliserSort(ArrayList<Entitee> listEntite) {
        try {
            if (listEntite == null || listEntite.isEmpty()) {
                Affichage.affiche("Aucune entité disponible à soigner.");
                return false;
            }

            Affichage.affiche("Sélectionnez l'entité à soigner :");
            Affichage.afficheListeEntitee(listEntite);
            int choix = Scanner.demandeInt();

            if (choix < 0 || choix >= listEntite.size()) {
                Affichage.affiche("Erreur : sélection invalide.");
                return false;
            }

            int soin = Des.lancerDes(10);
            listEntite.get(choix).soin(soin);
            Affichage.affiche(listEntite.get(choix).getNom() + " a été soigné de " + soin + " points.");
            return true;
        } catch (NumberFormatException e) {
            Affichage.affiche("Erreur : entrée non valide, veuillez saisir un nombre.");
        } catch (IndexOutOfBoundsException e) {
            Affichage.affiche("Erreur : l'entité sélectionnée n'existe pas.");
        } catch (Exception e) {
            Affichage.affiche("Une erreur s'est produite lors de l'utilisation du sort : " + e.getMessage());
        }
        return false;
    }
}
