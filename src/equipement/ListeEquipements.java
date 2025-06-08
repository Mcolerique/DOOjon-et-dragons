package equipement;


import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;
import interactionUtilisateur.Affichage;

import java.util.ArrayList;

public class ListeEquipements {


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
    private static ArrayList<Equipement> m_listeEquipementsUtilises = new ArrayList<>() {};

    public static void deDispoADejaUtilise(int equip){
        m_listeEquipementsUtilises.add(m_listeEquipements.get(equip));
        m_listeEquipements.remove(equip);
    }
    public static Equipement utiliserEquipement(int equip){

        Equipement returnEquip = m_listeEquipements.get(equip);
        deDispoADejaUtilise(equip);
        System.out.println("Equipement ajouté. \nEquipements placés : \n");
        Affichage.afficherEquip(m_listeEquipementsUtilises);
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
    public static ArrayList<Equipement> getListeEquips(){
        return m_listeEquipements;
    }
    public static void retourEtatInitialEquipements(){
        m_listeEquipements.addAll(m_listeEquipementsUtilises);
        m_listeEquipementsUtilises.clear();
    }

}

