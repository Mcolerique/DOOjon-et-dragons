package donjon;

import partie.Partie;
import entitee.personnage.Personnage;
import equipement.*;
import entitee.*;
import interactionUtilisateur.*;
import interactionUtilisateur.Scanner;

import java.lang.reflect.Array;
import java.util.*;

public class Donjon {
    private Hashtable<Entitee, int[]> m_positionEntitee;
    private Hashtable<Equipement, int[]> m_positionEquip;
    private ArrayList<int[]> m_obstacles = new ArrayList<>();
    private int[] m_tailleMap = new int[2];

    //jcrois bien qu'on va être obligés de mettre en paramtères liste Equipements vu que cest le 1er truc a instancier
    //et qu'il doit etre instancié HORS de donjon en tout premier vu que bah il reste pareil meme en changeant de donjon

    //Et je me dis qu'on aura peut être besoin dune valeur "donjon n°" que t'as peut etre créé déjà dans ta partie

    public Donjon(Hashtable<Entitee, int[]> posEntitee,
                  Hashtable<Equipement, int[]> posEquip,
                  ArrayList<int[]> obstacles,
                  int[] tailleMap) {

        m_positionEntitee = posEntitee;
        m_positionEquip = posEquip;
        m_obstacles = obstacles;
        m_tailleMap = tailleMap;

    }

    /*
    * Note à moi meme je devrais faire des méthodes placerObstacles avec les boucles et conditions pour les appeler
    * et genre un parametre autoOuManuel pour savoir si le truc fait tout seul ou demander a l'user mais le temps est compté
    * et dcp c pas la priorité.
    */



    public Donjon donjonAuto(ArrayList<Personnage> personnages, int numDonjon) {

        int[] tailleTabl = new int[] {randomValue(15, 25), randomValue(15,25)};

        int nombreDobstacles = randomValue(5, 10);
        ArrayList<int[]> obstaclesDonjon = new ArrayList<>();
        for(int i = 0; i < nombreDobstacles; i++) {
            boolean placementPossible;
            do {
                int[] positionObstacle = {randomValue(0, tailleTabl[0]), randomValue(0, tailleTabl[1])};
                placementPossible =  !existeAEmplacement(positionObstacle);
                if(placementPossible){
                    obstaclesDonjon.add(positionObstacle);
                }
            }while (!placementPossible);
        }

        Hashtable<Entitee, int[]> positionEntitee = new Hashtable<>();
        for(int i = 0; i < 3; i++) { //Par défaut, nous allons utiliser 3 monstres (le nombre de monstres créés par defaut)
            boolean placementPossible;
            do {
                int[] positionMonstre = {randomValue(0, tailleTabl[0]), randomValue(0, tailleTabl[1])};
                placementPossible =  !existeAEmplacement(positionMonstre);
                if(placementPossible){
                    positionEntitee.put(ListeMonstres.utiliserMonstreAuto(numDonjon, i), positionMonstre);
                }
            }while (!placementPossible);
        }

        for(Personnage personnage : personnages) {
            boolean placementPossible;
            do {
                int[] positionPerso = {randomValue(0, tailleTabl[0]), randomValue(0, tailleTabl[1])};
                placementPossible =  !existeAEmplacement(positionPerso);
                if(placementPossible){
                    positionEntitee.put(personnage, positionPerso);
                }
            }while (!placementPossible);
        }

        Hashtable<Equipement, int[]> positionEquip = new Hashtable<>();
        for(int i = 0; i < 5; i++) {
            boolean placementPossible;
            do {
                int[] positionEquipement = {randomValue(0, tailleTabl[0]), randomValue(0, tailleTabl[1])};
                placementPossible =  !existeAEmplacement(positionEquipement);
                if(placementPossible){
                    positionEntitee.put(ListeEquipements.utiliserEquipAuto(numDonjon, i), positionEquipement);
                }
            }while (!placementPossible);
        }


        Donjon donjon = new Donjon(positionEntitee, positionEquip, obstaclesDonjon, tailleTabl);

        return donjon;

    }

    public Donjon donjonManuel(ArrayList<Personnage> personnages, int numDonjon) {

        int[] tailleTabl = new int[2];

        Affichage.affiche("Quelle taille de carte souhaitez-vous pour le donjon ? (Comprise entre 15 et 25)");

        do {
            Affichage.affiche("En longueur ?");
            tailleTabl[0] = Scanner.demandeInt();
        }while (tailleTabl[0] < 15 && tailleTabl[0] > 25);

        do {
            Affichage.affiche("En largeur ?");
            tailleTabl[1] = Scanner.demandeInt();
        }while (tailleTabl[1] < 15 && tailleTabl[1] > 25);


        //Limiter le nbr d'obstacles à placer à longueur*largeurCarte - (longueur-1)*(largeur-1)Carte
        //Minimum (Carte 15*15) 29 ; Maximum (Carte 25*25) 49

        int limiteObstacles = (tailleTabl[0] * tailleTabl[1] - (tailleTabl[0] - 1) * tailleTabl[1] - 1);
        ArrayList<int[]> obstaclesDonjon = new ArrayList<>();
        int nombreDobstacles;
        do {
            Affichage.affiche(("Combien d'obstacles voulez vous ? Maximum : " + limiteObstacles));
            nombreDobstacles = Scanner.demandeInt();
        }while (!(nombreDobstacles <= limiteObstacles));

        for(int i = 0; i < nombreDobstacles; i++) {
            boolean placementPossible;
            do {
                Affichage.affiche(("Obstacle n°" + i + ". Où voulez-vous le placer ? (Format x;y)x\n"));
                String emplacementObstacle = Scanner.demandeString();
                String[] coordonnees = emplacementObstacle.split("[;]");
                int[] positionObstacle = {Integer.parseInt(coordonnees[0]), Integer.parseInt(coordonnees[1])};
                placementPossible =  !existeAEmplacement(positionObstacle);
                if(placementPossible){
                    obstaclesDonjon.add(positionObstacle);
                }
            } while (!placementPossible);
        }

        Hashtable<Entitee, int[]> positionEntitee = new Hashtable<>();
        ListeMonstres.afficherMonstreDispo();
        System.out.println("De quelle liste voulez-vous placer un monstre ?");
        int listeMonstre = Scanner.demandeInt();
        Affichage.affiche("Combien ?");
        int numMonstre;
        do{
            numMonstre = Scanner.demandeInt();
        }while(numMonstre <= ListeMonstres.nbMonstresDispoParDiff(listeMonstre));

        for(int i = 0; i < numMonstre; i++) {
            boolean placementPossible;
            do {
                Affichage.affiche(ListeMonstres.getNomMonstre(listeMonstre, numMonstre) + ". Où voulez-vous le placer ? (Format x;y)x\n");
                String emplacementMonstre = Scanner.demandeString();
                String[] coordonnees = emplacementMonstre.split("[;]");
                int[] positionMonstre = {Integer.parseInt(coordonnees[0]), Integer.parseInt(coordonnees[1])};
                placementPossible =  !existeAEmplacement(positionMonstre);
                if(placementPossible){
                    positionEntitee.put(ListeMonstres.utiliserMonstre(listeMonstre, i),positionMonstre);
                }
            }while (!placementPossible);
        }


        int compteur = 0;
        for(Personnage personnage : personnages) {
            boolean placementPossible;
            do {
                Affichage.affiche(personnages.get(compteur) + ". Où voulez-vous le placer ? (Format x;y)x\n");
                String emplacementPerso = Scanner.demandeString();
                String[] coordonnees = emplacementPerso.split("[;]");
                int[] positionPerso = {Integer.parseInt(coordonnees[0]), Integer.parseInt(coordonnees[1])};
                placementPossible =  !existeAEmplacement(positionPerso);
                if(placementPossible){
                    positionEntitee.put(personnage, positionPerso);
                }
                compteur++;
            }while (!placementPossible);
        }

        Hashtable<Equipement, int[]> positionEquip = new Hashtable<>();
        for(int i = 0; i < 5; i++) {
            boolean placementPossible;
            do {
                Affichage.affiche(personnages.get(compteur) + ". Où voulez-vous le placer ? (Format x;y)x\n");
                String emplacementEquip = Scanner.demandeString();
                String[] coordonnees = emplacementEquip.split("[;]");
                int[] positionEquipement = {Integer.parseInt(coordonnees[0]), Integer.parseInt(coordonnees[1])};
                placementPossible =  !existeAEmplacement(positionEquipement);
                if(placementPossible){
                    positionEntitee.put(ListeEquipements.utiliserEquipAuto(numDonjon, i), positionEquipement);
                }
            }while (!placementPossible);
        }


        Donjon donjon = new Donjon(positionEntitee, positionEquip, obstaclesDonjon, tailleTabl);

        return donjon;

    }

    public  Donjon creerDonjon(ArrayList<Personnage> personnages, int numDonjon){
        int reponseCreation = -1;
        do {
            Affichage.affiche("Voulez-vous créer un donjon ou en utiliser un généré par défaut ?\n0: Par moi-même \t 1: Par défaut");
            reponseCreation = Scanner.demandeInt();
        }while (reponseCreation != 0 || reponseCreation != 1);


        if(reponseCreation == 0){

            return donjonManuel(personnages, numDonjon);
        }

        else{
            return donjonAuto(personnages, numDonjon);
        }
    }

    public ArrayList<Entitee> listeCible(Entitee attaquant)
    {
        int distanceX, distanceY;
        int[] posEntitee;
        int[] maPosition = m_positionEntitee.get(attaquant);
        ArrayList<Entitee> listCible = new ArrayList<>();
        Set<Entitee> setKey = m_positionEntitee.keySet();

        for(Entitee i : setKey) {
            posEntitee = m_positionEntitee.get(i);
            distanceX = Math.abs(maPosition[0] - posEntitee[0]);
            distanceY = Math.abs(maPosition[1] - posEntitee[1]);
            if (distanceX <= attaquant.getPorteeArme() && distanceY <= attaquant.getPorteeArme()) {
                listCible.add(i);
            }
        }
        return listCible;
    }

    public int randomValue(int borneInf, int borneSup){
        Random random = new Random();
        return borneInf + random.nextInt(borneSup - borneInf);
    }

    public boolean existeAEmplacement(int[] aVerifier){
        boolean existe = false;

        //Verification avec les obstacles
        for(int i = 0; i<m_obstacles.size(); i++){
            existe = (m_obstacles.get(i)[0] == aVerifier[0] && m_obstacles.get(i)[1] == aVerifier[1]) ? true : false;
        }


        //Verification avec les entitées (monstres et personnages)
        for(int[] positionEntitee : m_positionEntitee.values()){
            existe = (positionEntitee[0] == aVerifier[0] && positionEntitee[1] == aVerifier[1]) ? true : false;
        }

        //Verification avec les équipements
        for(int[] positionEquip : m_positionEquip.values()){
            existe = (positionEquip[0] == aVerifier[0] && positionEquip[1] == aVerifier[1]) ? true : false;
        }


        return existe;
    }

    public boolean verifAEmplacement(int[] aVerifier){
        boolean existe = false;

        //Verification avec les obstacles
        for(int i = 0; i<m_obstacles.size(); i++){
            existe = (m_obstacles.get(i)[0] == aVerifier[0] && m_obstacles.get(i)[1] == aVerifier[1]) ? true : false;
        }


        //Verification avec les entitées (monstres et personnages)
        for(int[] positionEntitee : m_positionEntitee.values()){
            existe = (positionEntitee[0] == aVerifier[0] && positionEntitee[1] == aVerifier[1]) ? true : false;
        }

        return existe;
    }

    public int getLongueur(){
        return m_tailleMap[0];
    }

    public int getLargeur(){
        return m_tailleMap[1];
    }

    public boolean estVaincu() {
        //return m_estVaincu;
        boolean termine = true;
        for(Entitee entitee : m_positionEntitee.keySet()){
            if(entitee instanceof Monstre){
                if(entitee.estVivant()){
                    termine = false;
                }
            }
        }
        return termine;
    }

    public int[] getPosEntitee(Entitee entitee){
        return m_positionEntitee.get(entitee);
    }

    public void deplacerEntitee(Entitee entitee, int[] position){
        m_positionEntitee.replace(entitee, position);
    }

    public Entitee getEntiteeAPos(int[] position){
        for(Entitee entitee : m_positionEntitee.keySet()){
            if(m_positionEntitee.get(entitee)[0] == position[0] && m_positionEntitee.get(entitee)[1] == position[1]){
                return entitee;
            }
        }
        return null;
    }

    public boolean equipAPos(int[] position){
        boolean equipAPos = false;
        for(Equipement equip : m_positionEquip.keySet()){
            if(m_positionEntitee.get(equip)[0] == position[0] && m_positionEntitee.get(equip)[1] == position[1]){
                equipAPos = true;
            }
        }
        return equipAPos;
    }

    public Equipement getEquipAPos(int[] position){
        for(Equipement equip : m_positionEquip.keySet()){
            if(m_positionEntitee.get(equip)[0] == position[0] && m_positionEntitee.get(equip)[1] == position[1]){
                return equip;
            }
        }
        return null;
    }

    public void supprEquip(Equipement equip){
        m_positionEquip.remove(equip);
    }


    public ArrayList<Entitee> lancerInitiative(){
        ArrayList<Entitee> listeEntitee = new ArrayList<>();
        ArrayList<Integer> listeInitiative = new ArrayList<>();
        for(Entitee entitee : m_positionEntitee.keySet()){
            int initiative = entitee.lancerInitiative();
            listeEntitee.add(entitee);
            listeInitiative.add(initiative);
        }

        triEntitees(listeEntitee, listeInitiative);
        return listeEntitee;
    }

    public void triEntitees(ArrayList<Entitee> entitees, ArrayList<Integer> initiativeEntitees){

        for(int i = 0 ; i <= entitees.size()-2 ; i++){
            for(int j = i+1 ; j <= entitees.size()-1 ; j++){
                if(initiativeEntitees.get(i).intValue() < initiativeEntitees.get(j).intValue()){

                    Entitee entiteeTemp = entitees.get(j);
                    Integer initiativeTemp = initiativeEntitees.get(j);


                    entitees.remove(j);
                    initiativeEntitees.remove(j);
                    entitees.add(j, entitees.get(i));
                    initiativeEntitees.add(j, initiativeEntitees.get(i));


                    entitees.remove(i);
                    initiativeEntitees.remove(i);
                    entitees.add(i, entiteeTemp);
                    initiativeEntitees.add(i, initiativeTemp);

                }
            }
        }
    }


}