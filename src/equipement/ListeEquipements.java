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
        add(new Armure("Armure d'écailles", 9, Poids.LEGERE));
        add(new Armure("Demi-plate", 10, Poids.LEGERE));
        add(new Armure("Cotte de mailles", 11, Poids.LOURDE));
        add(new Armure("Harnois", 12, Poids.LOURDE));
        add(new Arme("Bâton", 1, 6, TypeCaC.COURANTE));
        add(new Arme("Masse d'armes", 1, 6, TypeCaC.COURANTE));
        add(new Arme("Epée longue", 1,  8, TypeCaC.GUERRE));
        add(new Arme("Rapière", 1,  8, TypeCaC.GUERRE));
        add(new Arme("Arbalète légère", 1, 8, 16));
        add(new Arme("Fronde", 1, 4, 6));
        add(new Arme("Arc court", 1, 6, 16));
        add(new Arme("Epée à deux mains", 2,  6, TypeCaC.GUERRE));
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

