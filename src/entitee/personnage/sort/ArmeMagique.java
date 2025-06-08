package entitee.personnage.sort;

import entitee.Entitee;
import entitee.TypeEntitee;
import entitee.personnage.Personnage;
import equipement.Equipement;
import equipement.TypeEquipement;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;

/**
 * La classe ArmeMagique représente un sort permettant d'améliorer une arme dans le jeu.
 * Elle hérite de la classe Sort.
 */
public class ArmeMagique extends Sort {

    /**
     * Construit un sort ArmeMagique avec un nom et une description par défaut.
     */
    public ArmeMagique() {
        super("Arme magique", "Améliore une arme au choix. L'arme gagne alors un bonus de 1 lors des jets d'attaque et de 1 lors des jets de dégâts (les bonus peuvent se cumuler)");
    }

    /**
     * Utilise le sort ArmeMagique pour améliorer une arme sélectionnée.
     *
     * @param listEntite la liste des entités disponibles pour l'amélioration d'une arme
     * @return vrai si le sort a été utilisé avec succès, faux sinon
     */
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
            int choix = Scanner.demandeInt() - 1;

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

    /**
     * Remplit une liste d'armes à partir d'une liste d'entités.
     *
     * @param list la liste des entités
     * @return la liste des armes
     */
    public ArrayList<Equipement> remplirListeArme(ArrayList<Entitee> list) {
        ArrayList<Equipement> armes = new ArrayList<>();
        Personnage p;
        for (Entitee e : list) {
            if (e.getType() == TypeEntitee.PERSONNAGE) {
                armes.add(e.getArme());
                p = (Personnage) e;
                for (int i = 0; i < p.getTailleInventaire(); i++) {
                    if (p.getInventaire(i).getType() == TypeEquipement.ARME) {
                        armes.add(p.getInventaire(i));
                    }
                }
            }
        }
        return armes;
    }
}
