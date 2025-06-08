package equipement;


import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;
import interactionUtilisateur.Affichage;

import java.util.ArrayList;

/**
 * La classe ListeEquipements gère une liste d'équipements disponibles et utilisés dans le jeu.
 */
public class ListeEquipements {

    /**
     * Liste des équipements disponibles.
     */
    private static ArrayList<Equipement> m_listeEquipements = new ArrayList<>() {{
        add(new Armure("Armure d'écailles", 9, Poids.LEGERE));
        add(new Armure("Demi-plate", 10, Poids.LEGERE));
        add(new Armure("Cotte de mailles", 11, Poids.LOURDE));
        add(new Armure("Harnois", 12, Poids.LOURDE));
        add(new Arme("Bâton", 1, 6, TypeCaC.COURANTE));
        add(new Arme("Masse d'armes", 1, 6, TypeCaC.COURANTE));
        add(new Arme("Épée longue", 1, 8, TypeCaC.GUERRE));
        add(new Arme("Rapière", 1, 8, TypeCaC.GUERRE));
        add(new Arme("Arbalète légère", 1, 8, 16));
        add(new Arme("Fronde", 1, 4, 6));
        add(new Arme("Arc court", 1, 6, 16));
        add(new Arme("Épée à deux mains", 2, 6, TypeCaC.GUERRE));
    }};

    /**
     * Liste des équipements déjà utilisés.
     */
    private static ArrayList<Equipement> m_listeEquipementsUtilises = new ArrayList<>() {};

    /**
     * Déplace un équipement de la liste des équipements disponibles à la liste des équipements utilisés.
     *
     * @param equip l'index de l'équipement à déplacer
     */
    public static void deDispoADejaUtilise(int equip) {
        m_listeEquipementsUtilises.add(m_listeEquipements.get(equip));
        m_listeEquipements.remove(equip);
    }

    /**
     * Utilise un équipement et le déplace de la liste des équipements disponibles à la liste des équipements utilisés.
     *
     * @param equip l'index de l'équipement à utiliser
     * @return l'équipement utilisé
     */
    public static Equipement utiliserEquipement(int equip) {
        Equipement returnEquip = m_listeEquipements.get(equip);
        deDispoADejaUtilise(equip);
        System.out.println("Équipement ajouté. \nÉquipements placés : \n");
        Affichage.afficherEquip(m_listeEquipementsUtilises);
        return returnEquip;
    }

    /**
     * Utilise un équipement automatiquement et le déplace de la liste des équipements disponibles à la liste des équipements utilisés.
     *
     * @param equip l'index de l'équipement à utiliser
     * @return l'équipement utilisé
     */
    public static Equipement utiliserEquipAuto(int equip) {
        Equipement equipementAReturn = m_listeEquipements.get(equip);
        deDispoADejaUtilise(equip);
        return equipementAReturn;
    }

    /**
     * Obtient le nombre d'équipements disponibles.
     *
     * @return le nombre d'équipements disponibles
     */
    public static int nbEquipDispo() {
        return m_listeEquipements.size();
    }

    /**
     * Obtient le nom d'un équipement disponible.
     *
     * @param equip l'index de l'équipement
     * @return le nom de l'équipement
     */
    public static String getNomEquip(int equip) {
        return m_listeEquipements.get(equip).getNom();
    }

    /**
     * Obtient la liste des équipements disponibles.
     *
     * @return la liste des équipements disponibles
     */
    public static ArrayList<Equipement> getListeEquips() {
        return m_listeEquipements;
    }

    /**
     * Réinitialise l'état des équipements en déplaçant tous les équipements utilisés vers la liste des équipements disponibles.
     */
    public static void retourEtatInitialEquipements() {
        m_listeEquipements.addAll(m_listeEquipementsUtilises);
        m_listeEquipementsUtilises.clear();
    }
}