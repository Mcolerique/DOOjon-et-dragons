package entitee;


import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import equipement.armure.Poids;
import interactionUtilisateur.*;
import entitee.Monstre;

import java.util.ArrayList;
import java.util.Arrays;

public class ListeMonstres{


    private static ArrayList<Monstre> m_listeMonstres = new ArrayList<>() {{

        add(new Monstre(1, "Gobelin", new int[] {25,15,0,14,1},
                new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(2, "Gobelin", new int[] {25,9,0,17,1},
                new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Troll", new int[] {21,0,17,13,1},
                new Equipement[]{new Arme("Catapulte", 1, 4, 6), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Dragon blanc", new int[] {45,0,15,12,1},
                new Equipement[]{new Arme("Souffle flamboyant", 1, 8, 9), new Armure("Ecailles", 10, Poids.LOURDE)}));
        add(new Monstre(1, "Dragon noir", new int[] {45,12,0,16,1},
                new Equipement[]{new Arme("Souffle grondant", 1, 8, 0), new Armure("Ecailles", 10, Poids.LOURDE)}));
        add(new Monstre(1, "Gorille", new int[] {35,0,14,14,1},
                new Equipement[]{new Arme("Poings", 1, 6, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Archer des abysses", new int[] {25,0,14,13,1},
                new Equipement[]{new Arme("Arc d'Héraclès", 1, 8, 8), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Chevalier des abysses", new int[] {25,13,0,14,1},
                new Equipement[]{new Arme("Excalibur", 1, 8, 1), new Armure("Peau", 12, Poids.LOURDE)}));
        add(new Monstre(1, "Demogorgon", new int[] {25,0,19,11,1},
                new Equipement[]{new Arme("!!!", 1, 10, 4), new Armure("???", 7, Poids.LEGERE)}));

    }};

    // Liste des monstres utilisés pour chaque donjon
    private static ArrayList<Monstre> m_listeMonstresUtilises = new ArrayList<>() {};

    public static int nbMonstresDispo(){
        return m_listeMonstres.size();
    }
    public static void deDispoADejaUtilise(int monstre) {
        m_listeMonstresUtilises.add(m_listeMonstres.get(monstre));
        m_listeMonstres.remove(monstre);
    }
    public static Monstre utiliserMonstre(int monstre){
        Monstre returnMonstre = m_listeMonstres.get(monstre);
        deDispoADejaUtilise(monstre);
        System.out.println("Monstre ajouté. \nMonstres placés : \n");
        Affichage.afficherMonstre(m_listeMonstresUtilises);
        return returnMonstre;
    }
    public static Monstre utiliserMonstreAuto(int monstre){
        Monstre monstreAReturn = m_listeMonstres.get(monstre);
        deDispoADejaUtilise(monstre);
        return monstreAReturn;
    }
    public static String getNomMonstre(int monstre){
        return m_listeMonstres.get(monstre).getAppellation();
    }
    public static ArrayList<Monstre> getListeMonstres() {
        return m_listeMonstres;
    }
    public static void retourEtatInitialMonstres(){
        m_listeMonstres.addAll(m_listeMonstresUtilises);
        m_listeMonstresUtilises.clear();
    }

}















