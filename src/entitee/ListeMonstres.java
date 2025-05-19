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

    private static ArrayList<ArrayList<Monstre>> m_listeMonstres = new ArrayList<>() {{

        // Donjon 1
        add(new ArrayList<>() {{
            // rajouter les Monstres communs armes et armures, 1 de chaque je pense ça fait 5 elements en tout
            add(new Monstre(1, "Gobelin", new int[] {25,15,14,0,0},
                    new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
            add(new Monstre(2, "Gobelin", new int[] {25,9,17,0,0},
                    new Equipement[]{new Arme("Bâton", 1, 4, 1), new Armure("Peau", 7, Poids.LEGERE)}));
            add(new Monstre(1, "Troll", new int[] {21,17,0,13,0},
                    new Equipement[]{new Arme("Catapulte", 1, 4, 6), new Armure("Peau", 7, Poids.LEGERE)}));
        }});

        // Donjon 2
        add(new ArrayList<>() {{
            // pareil pour ça
            add(new Monstre(1, "Dragon blanc", new int[] {45,15,0,12,0},
                    new Equipement[]{new Arme("Souffle flamboyant", 1, 8, 9), new Armure("Ecailles", 10, Poids.LOURDE)}));
            add(new Monstre(1, "Dragon noir", new int[] {45,12,0,16,0},
                    new Equipement[]{new Arme("Souffle grondant", 1, 8, 0), new Armure("Ecailles", 10, Poids.LOURDE)}));
            add(new Monstre(1, "Gorille", new int[] {35,14,14,0,0},
                    new Equipement[]{new Arme("Poings", 1, 6, 1), new Armure("Peau", 7, Poids.LEGERE)}));
        }});

        // Donjon 3
        add(new ArrayList<>() {{
            add(new Monstre(1, "Archer des abysses", new int[] {25,14,0,13,0},
                    new Equipement[]{new Arme("Arc d'Héraclès", 1, 8, 8), new Armure("Peau", 7, Poids.LEGERE)}));
            add(new Monstre(1, "Chevalier des abysses", new int[] {25,13,14,0,0},
                    new Equipement[]{new Arme("Excalibur", 1, 8, 1), new Armure("Peau", 12, Poids.LOURDE)}));
            add(new Monstre(1, "Demogorgon", new int[] {25,19,0,11,0},
                    new Equipement[]{new Arme("Bâton", 1, 10, 4), new Armure("???", 7, Poids.LEGERE)}));
        }});
    }};

    // Liste des monstres utilisés pour chaque donjon
    private static ArrayList<ArrayList<Monstre>> m_listeMonstresUtilises = new ArrayList<>() {{
        add(new ArrayList<>());
        add(new ArrayList<>());
        add(new ArrayList<>());
    }};


    public static void afficherMonstreDispo(){
        for(int i = 0; i < m_listeMonstres.size(); i++){
            String difficulte = "";
            switch(i){
                case 0:
                    difficulte = "facile";
                    break;
                case 1:
                    difficulte = "moyen";
                    break;
                case 2:
                    difficulte = "difficile";
                    break;

            }
            System.out.println("Liste n°" + i + " - Monstres de difficulté " + difficulte + " disponibles : \n");
            for (int j = 0; j < m_listeMonstres.get(i).size(); j++) {
                System.out.println("\t Monstre n°" + j + " : " + m_listeMonstres.get(i).get(j).getAppellation() + "\n");
            }
            System.out.println("\n\n");
        }
    }

    public static void monstresDispoParDiff(int numDonjon){
        String difficulte = "";
        switch(numDonjon){
            case 0:
                difficulte = "facile";
                break;
            case 1:
                difficulte = "moyen";
                break;
            case 2:
                difficulte = "difficile";
                break;
            default:
                difficulte = "difficile";
                break;
        }
        System.out.println("Liste n°" + (numDonjon) + " - Monstres de difficulté " + difficulte + " disponibles : \n");
        for (int j = 0; j < m_listeMonstres.get(numDonjon).size(); j++) {
            System.out.println("\t Monstre n°" + j + " : " + m_listeMonstres.get(numDonjon).get(j).getAppellation() + "\n");
        }
        System.out.println("\n\n");
    }

    public static int nbMonstresDispoParDiff(int difficulte){
        return m_listeMonstres.get(difficulte).size();
    }

    public static void afficherMonstreUtilis(){
        for(int i = 0; i < m_listeMonstresUtilises.size(); i++){
            String difficulte = "";
            switch(i){
                case 0:
                    difficulte = "facile";
                    break;
                case 1:
                    difficulte = "moyen";
                    break;
                case 2:
                    difficulte = "difficile";
                    break;

            }
            System.out.println("Liste n°" + i + " - Monstres de difficulté " + difficulte + " utilisés : \n");
            for (int j = 0; j < m_listeMonstresUtilises.get(i).size(); j++) {
                System.out.println("\t Monstre n°" + j + " : " + m_listeMonstresUtilises.get(i).get(j).getAppellation() + "\n");
            }
            System.out.println("\n\n");
        }
    }

    public static void deDispoADejaUtilise(int liste, int monstre){
        m_listeMonstresUtilises.get(liste).add(m_listeMonstres.get(liste).get(monstre));
        m_listeMonstres.get(liste).remove(monstre);
    }

    public static Monstre utiliserMonstre(int listeMonstre, int monstre){
        System.out.println("Quel monstre voulez-vous placer ?");
        //int numMonstre = Scanner.demandeInt();
        deDispoADejaUtilise(listeMonstre, monstre);
        System.out.println("Monstre ajouté. \n Monstres placés : \n\n");
        afficherMonstreUtilis();
        return m_listeMonstres.get(listeMonstre).get(monstre);
    }

    public static Monstre utiliserMonstreAuto(int listeMonstre, int monstre){
        Monstre monstreAReturn = m_listeMonstres.get(listeMonstre).get(monstre);
        deDispoADejaUtilise(listeMonstre, monstre);
        return monstreAReturn;
    }

    public static String getNomMonstre(int listeMonstre, int monstre){
        return m_listeMonstres.get(listeMonstre).get(monstre).getAppellation();
    }

}















