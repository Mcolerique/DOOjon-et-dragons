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
    public boolean utiliserSort(ArrayList<Entitee> listEntite) {
        try {
            ArrayList<Equipement> armes = remplirListeArme(listEntite);

            if (armes.isEmpty()) {
                Affichage.affiche("Aucune arme disponible à améliorer.");
                return false;
            }

            Affichage.affiche("Sélectionnez l'arme à améliorer :");
            Affichage.listeEquipement(armes);
            int choix = Scanner.demandeInt() -1;

            if (choix < 0 || choix >= armes.size()) {
                Affichage.affiche("Erreur : choix invalide.");
                return false;
            }

            Equipement equipement = armes.get(choix);
            if (equipement.getType() == TypeEquipement.ARME) {
                equipement.boost(1);
                Affichage.affiche(equipement.getNom() + " a été améliorée !");
            } else {
                Affichage.affiche("Erreur : l'équipement sélectionné n'est pas une arme.");
                return false;
            }

            return true;

        } catch (NumberFormatException e) {
            Affichage.affiche("Erreur : entrée non valide, veuillez saisir un nombre.");
        } catch (IndexOutOfBoundsException e) {
            Affichage.affiche("Erreur : sélection en dehors des limites.");
        } catch (Exception e) {
            Affichage.affiche("Une erreur s'est produite : " + e.getMessage());
        }

        return false;
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
