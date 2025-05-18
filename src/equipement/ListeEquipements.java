package equipement;

import entitee.Entitee;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;
import interactionUtilisateur.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ListeEquipements {
    //creer des listes dequip
    //get element de la liste
    //add dans une liste
    //creer une liste dejaUtilise et y mettre les elements deja utilises



    // Liste des équipements disponibles (par rareté)
    private static ArrayList<ArrayList<Equipement>> m_listeEquipements = new ArrayList<>() {{

        // rajouter les equipements communs armes et armures, 1 de chaque je pense ça fait 5 elements en tout
        add(new ArrayList<>(Arrays.asList(
                new Arme("ArmeDistance1", 2, 4, 5),
                new Arme("ArmeCourante1", 2, 4, TypeCaC.COURANTE),
                new Arme("ArmeGuerre1", 2, 4, TypeCaC.GUERRE),
                new Armure("ArmureLegere1", 9, Poids.LEGERE),
                new Armure("ArmureLourde1", 9, Poids.LOURDE)
        )));

        // pareil pour ça
        add(new ArrayList<>(Arrays.asList(
                new Arme("ArmeDistance2", 2, 4, 5),
                new Arme("ArmeCourant2", 2, 4, TypeCaC.COURANTE),
                new Arme("ArmeGuerre2", 2, 4, TypeCaC.GUERRE),
                new Armure("ArmureLegere2", 9, Poids.LEGERE),
                new Armure("ArmureLourde2", 9, Poids.LOURDE)
        )));

        add(new ArrayList<>(Arrays.asList(
                new Arme("ArmeDistance3", 2, 4, 5),
                new Arme("ArmeCourante3", 2, 4, TypeCaC.COURANTE),
                new Arme("ArmeGuerre3", 2, 4, TypeCaC.GUERRE),
                new Armure("ArmureLegere3", 9, Poids.LEGERE),
                new Armure("ArmureLourde3", 9, Poids.LOURDE)
        )));
    }};

    // Liste des équipements utilisés (initialement vide)
    private static ArrayList<ArrayList<Equipement>> m_listeEquipementsUtilises = new ArrayList<>() {{
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
    }};


    public static void afficherEquipDispo(){
        for(int i = 0; i < m_listeEquipements.size(); i++){
            String rarete = "";
            switch(i){
                case 0:
                    rarete = "commune";
                    break;
                case 1:
                    rarete = "peu commune";
                    break;
                case 2:
                    rarete = "rare";
                    break;

            }
            System.out.println("Liste n°" + i + " - Equipements de rareté " + rarete + " disponibles : \n");
            for (int j = 0; j < m_listeEquipements.get(i).size(); j++) {
                System.out.println("\t Equipement n°" + j + " : " + m_listeEquipements.get(i).get(j).getNom() + "\n");
            }
            System.out.println("\n\n");
        }
    }

    public static void afficherEquipUtilis(){
        for(int i = 0; i < m_listeEquipementsUtilises.size(); i++){
            String rarete = "";
            switch(i){
                case 0:
                    rarete = "commune";
                    break;
                case 1:
                    rarete = "peu commune";
                    break;
                case 2:
                    rarete = "rare";
                    break;

            }
            System.out.println("Liste n°" + i + " - Equipements de rareté " + rarete + " utilisés : \n");
            for (int j = 0; j < m_listeEquipementsUtilises.get(i).size(); j++) {
                System.out.println("\t Equipement n°" + j + " : " + m_listeEquipementsUtilises.get(i).get(j).getNom() + "\n");
            }
            System.out.println("\n\n");
        }
    }

    public static boolean addEquipement (Equipement equipement){
        int rarete;
        boolean aPuEtreCree;

        aPuEtreCree = (m_listeEquipements.contains(equipement)) ? false : true;
        if (!aPuEtreCree) {
            return aPuEtreCree;
        }

        System.out.println("A quelle rareté voulez-vous assigner l'objet ? : \n 0 - Commune \n 1 - Peu Commune \n 2 - Rare ");
        rarete = Scanner.demandeInt();

        if(rarete >= 0 && rarete < m_listeEquipements.size()){
            m_listeEquipements.get(rarete).add(equipement);
            aPuEtreCree = true;
            System.out.println(equipement.getNom() + "a pu être ajouté avec succès à la liste.");
        }
        else{
            System.out.println("La liste choisie n'existe pas.");
        }

        return aPuEtreCree;

        //et dcp on pourra faire en sorte de redemander à l'utilisateur sil veut ou non retenter la création d'un equipement
        //dans le cas où aPuEtreCree est retourné comme false.
    }

    public static void deDispoADejaUtilise(int liste, int equip){
        m_listeEquipementsUtilises.get(liste).add(m_listeEquipements.get(liste).get(equip));
        m_listeEquipements.get(liste).remove(equip);
    }

    public static void utiliserEquipement(){
        afficherEquipDispo();
        System.out.println("De quelle liste voulez-vous utiliser un équipement ?");
        int listeEquip = Scanner.demandeInt();
        System.out.println("Quel équipement voulez-vous utiliser ?");
        int numEquip = Scanner.demandeInt();
        deDispoADejaUtilise(listeEquip, numEquip);
        System.out.println("Equipements utilisés : \n\n");
        afficherEquipUtilis();
    }

    public static Entitee utiliserEquipAuto(int listeEquip, int equip){
        Equipement equipementAReturn = m_listeEquipements.get(listeEquip).get(equip);
        deDispoADejaUtilise(listeEquip, equip);
        return equipementAReturn;
    }

}

