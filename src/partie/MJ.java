package partie;

import des.Des;
import entitee.Entitee;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

public class MJ {
    public static void attaquerEntitee(Entitee e)
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
