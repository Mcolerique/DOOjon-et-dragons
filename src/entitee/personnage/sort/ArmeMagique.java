package entitee.personnage.sort;

import entitee.Entitee;
import entitee.TypeEntitee;
import entitee.personnage.Personnage;
import equipement.Equipement;
import equipement.TypeEquipement;
import equipement.arme.Arme;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

public class ArmeMagique extends Sort{
    public ArmeMagique()
    {
        super("Arme magique", "Améliore une arme au choix. L'arme gagne alors un bonus de 1 lors des jets d'attaque et de 1 lors des jets de dégâts (les bonus peuvent se cumuler)");
    }

    @Override
    public void utiliserSort(ArrayList<Entitee> listEntite) {
        ArrayList<Equipement> armes = remplirListeArme(listEntite);
        Affichage.affiche("Selectionnez l'arme à amélioré :");
        Affichage.listeEquipement(armes);
        int choix = Scanner.demandeInt();
        ((Arme)armes.get(choix)).boostArme(1);
    }
    public ArrayList<Equipement> remplirListeArme(ArrayList<Entitee> list)
    {
        ArrayList<Equipement> armes = new ArrayList<>();
        Personnage p;
        for(Entitee e : list)
        {
            armes.add(e.getArme());
            if(e.getType() == TypeEntitee.PERSONNAGE)
            {
                p = (Personnage) e;
                for(int i = 0; i<p.getTailleInventaire(); i++)
                {
                    if (p.getInventaire(i).getType() == TypeEquipement.ARME)
                    {
                        armes.add(p.getInventaire(i));
                    }
                }
            }
        }
        return armes;
    }
}
