package equipement;

import entitee.Entitee;
import entitee.Monstre;
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



    private static ArrayList<Equipement> m_listeEquipements = new ArrayList<>(){{
        add(new Arme("ArmeDistance1", 2, 4, 5));
        add(new Arme("ArmeCourante1", 2, 4, TypeCaC.COURANTE));
        add(new Arme("ArmeGuerre1", 2, 4, TypeCaC.GUERRE));
        add(new Armure("ArmureLegere1", 9, Poids.LEGERE));
        add(new Armure("ArmureLourde1", 9, Poids.LOURDE));
        add(new Arme("ArmeDistance2", 2, 4, 5));
        add(new Arme("ArmeCourante2", 2, 4, TypeCaC.COURANTE));
        add(new Arme("ArmeGuerre2", 2, 4, TypeCaC.GUERRE));
        add(new Armure("ArmureLegere2", 9, Poids.LEGERE));
        add(new Armure("ArmureLourde2", 9, Poids.LOURDE));
        add(new Arme("ArmeDistance3", 2, 4, 5));
        add(new Arme("ArmeCourante3", 2, 4, TypeCaC.COURANTE));
        add(new Arme("ArmeGuerre3", 2, 4, TypeCaC.GUERRE));
        add(new Armure("ArmureLegere3", 9, Poids.LEGERE));
        add(new Armure("ArmureLourde3", 9, Poids.LOURDE));
    }};

    private static ArrayList<Equipement> m_listeEquipementsUtilises = new ArrayList<>() {{}};


    public static void afficherEquipDispo(){
        for(int i = 0; i < m_listeEquipements.size(); i++){
            System.out.println("\t Equipement n°" + i + " : " + m_listeEquipements.get(i).getNom() + "\n");
        }
        System.out.println("\n\n");
    }

    public static void afficherEquipUtilis(){
        for(int i = 0; i < m_listeEquipementsUtilises.size(); i++){
            System.out.println("\t Monstre n°" + i + " : " + m_listeEquipementsUtilises.get(i).getNom() + "\n");
        }
        System.out.println("\n\n");
    }

    public static boolean ajtEquipement (Equipement equipement){
        int difficulte;
        boolean aPuEtreCree;

        aPuEtreCree = (m_listeEquipements.contains(equipement)) ? false : true;
        if (!aPuEtreCree) {
            return aPuEtreCree;
        }

        m_listeEquipements.add(equipement);
        aPuEtreCree = true;
        System.out.println(equipement.getNom() + "a pu être ajouté avec succès à la liste.");


        return aPuEtreCree;
    }

    public static void deDispoADejaUtilise(int equip){
        m_listeEquipementsUtilises.add(m_listeEquipements.get(equip));
        m_listeEquipements.remove(equip);
    }

    public static Equipement utiliserEquipement(int equip){
        /*afficherEquipDispo();
        System.out.println("Quel équipement voulez-vous utiliser ?");
        int numEquip = Scanner.demandeInt();
        deDispoADejaUtilise(numEquip);
        System.out.println("Equipements utilisés : \n\n");
        afficherEquipUtilis();*/


        Equipement returnEquip = m_listeEquipements.get(equip);
        deDispoADejaUtilise(equip);
        System.out.println("Equipement ajouté. \nEquipements placés : \n");
        afficherEquipUtilis();
        return returnEquip;

    }

    public static Equipement utiliserEquipAuto(int equip){
        Equipement equipementAReturn = m_listeEquipements.get(equip);
        deDispoADejaUtilise(equip);
        return equipementAReturn;
    }

    public static int nbEquipDispo(){
        return m_listeEquipements.size();
    }

    public static String getNomEquip(int equip){
        return m_listeEquipements.get(equip).getNom();
    }

}

