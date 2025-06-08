import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;
import partie.Partie;

public class Main {
    public static void main(String args[]){
        Partie p;
        String choix;
        do {
            p = new Partie();
            p.lancerPartie();
            Affichage.affiche("Lancer une nouvelle partie ? (y/n)");
            choix = Scanner.demandeString();
        }while (!choix.equals("n"));
    }
}