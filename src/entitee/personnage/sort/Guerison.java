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
    public void utiliserSort(ArrayList<Entitee> listEntite) {
        Affichage.affiche("Sélectionnez l'entité a soigner :");
        Affichage.afficheListeEntitee(listEntite);
        int choix = Scanner.demandeInt();
        int soin = Des.lancerDes(10);
        listEntite.get(choix).soin(soin);
    }
}
