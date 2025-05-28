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
    //creer des listes dmonstre
    //get element de la liste
    //add dans une liste
    //creer une liste dejaUtilise et y mettre les elements deja utilises



    private static ArrayList<Monstre> m_listeMonstres = new ArrayList<>() {{

        add(new Monstre(1, "Gobelin", new int[] {25,15,14,0,0},
                new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(2, "Gobelin", new int[] {25,9,17,0,0},
                new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Troll", new int[] {21,17,0,13,0},
                new Equipement[]{new Arme("Catapulte", 1, 4, 6), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Dragon blanc", new int[] {45,15,0,12,0},
                new Equipement[]{new Arme("Souffle flamboyant", 1, 8, 9), new Armure("Ecailles", 10, Poids.LOURDE)}));
        add(new Monstre(1, "Dragon noir", new int[] {45,12,0,16,0},
                new Equipement[]{new Arme("Souffle grondant", 1, 8, 0), new Armure("Ecailles", 10, Poids.LOURDE)}));
        add(new Monstre(1, "Gorille", new int[] {35,14,14,0,0},
                new Equipement[]{new Arme("Poings", 1, 6, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Archer des abysses", new int[] {25,14,0,13,0},
                new Equipement[]{new Arme("Arc d'Héraclès", 1, 8, 8), new Armure("Peau", 7, Poids.LEGERE)}));
        add(new Monstre(1, "Chevalier des abysses", new int[] {25,13,14,0,0},
                new Equipement[]{new Arme("Excalibur", 1, 8, 1), new Armure("Peau", 12, Poids.LOURDE)}));
        add(new Monstre(1, "Demogorgon", new int[] {25,19,0,11,0},
                new Equipement[]{new Arme("!!!", 1, 10, 4), new Armure("???", 7, Poids.LEGERE)}));

    }};

    // Liste des monstres utilisés pour chaque donjon
    private static ArrayList<Monstre> m_listeMonstresUtilises = new ArrayList<>() {{}};


    public static void afficherMonstreDispo(){
        for(int i = 0; i < m_listeMonstres.size(); i++){
            System.out.println("\t Monstre n°" + i + " : " + m_listeMonstres.get(i).getAppellation() + "\n");
        }
        System.out.println("\n\n");
    }

    public static int nbMonstresDispo(){
        return m_listeMonstres.size();
    }

    public static void afficherMonstreUtilis(){
        for(int i = 0; i < m_listeMonstresUtilises.size(); i++){
            System.out.println("\t Monstre n°" + i + " : " + m_listeMonstresUtilises.get(i).getAppellation() + "\n");
        }
        System.out.println("\n");
    }

    public static boolean addMonstre (Monstre Monstre){
        int difficulte;
        boolean aPuEtreCree;

        aPuEtreCree = (m_listeMonstres.contains(Monstre)) ? false : true;
        if (!aPuEtreCree) {
            return aPuEtreCree;
        }

        m_listeMonstres.add(Monstre);
        aPuEtreCree = true;
        System.out.println(Monstre.getAppellation() + "a pu être ajouté avec succès à la liste.");


        return aPuEtreCree;

    }

    public static void deDispoADejaUtilise(int monstre) {
        m_listeMonstresUtilises.add(m_listeMonstres.get(monstre));
        m_listeMonstres.remove(monstre);
    }

    public static void deDispoADejaUtilise(int liste, int monstre){
        m_listeMonstresUtilises.add(m_listeMonstres.get(monstre));
        m_listeMonstres.remove(monstre);
    }

    public static Monstre utiliserMonstre(int monstre){
        //System.out.println("Quel monstre voulez-vous placer ?");
        //int numMonstre = Scanner.demandeInt();
        Monstre returnMonstre = m_listeMonstres.get(monstre);
        deDispoADejaUtilise(monstre);
        System.out.println("Monstre ajouté. \nMonstres placés : \n");
        afficherMonstreUtilis();
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

}















