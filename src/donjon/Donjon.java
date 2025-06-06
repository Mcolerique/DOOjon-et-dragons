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
    public Donjon(int[] tailleMap)
    {
        m_tailleMap = tailleMap;
        m_positionEntitee = new Hashtable<>();
        m_positionEquip = new Hashtable<>();
    }
    public Donjon(Hashtable<Entitee, int[]> posEntitee,
                  Hashtable<Equipement, int[]> posEquip,
                  ArrayList<int[]> obstacles,
                  int[] tailleMap) {

        m_positionEntitee = posEntitee;
        m_positionEquip = posEquip;
        m_obstacles = obstacles;
        m_tailleMap = tailleMap;

    }
    private void setValeurDonjon(Hashtable<Entitee, int[]> posEntitee,
                                 Hashtable<Equipement, int[]> posEquip,
                                 ArrayList<int[]> obstacles)
    {
        m_positionEntitee = posEntitee;
        m_positionEquip = posEquip;
        m_obstacles = obstacles;
    }

    /*
    * Note à moi meme je devrais faire des méthodes placerObstacles avec les boucles et conditions pour les appeler
    * et genre un parametre autoOuManuel pour savoir si le truc fait tout seul ou demander a l'user mais le temps est compté
    * et dcp c pas la priorité.
    */



    private static Donjon donjonAuto(ArrayList<Personnage> personnages) {

        Donjon d = tailleDonjonAuto();
        int nombreDobstacles = randomValue(5, 10);
        ArrayList<int[]> obstaclesDonjon = new ArrayList<>();
        obstaclesDonjon = poserObstaclesAuto(d, obstaclesDonjon, nombreDobstacles);
        Hashtable<Entitee, int[]> positionEntitee = new Hashtable<>();
        positionEntitee = poserMonstresAuto(d, positionEntitee, randomValue(2, 4), obstaclesDonjon);
        positionEntitee = poserPersonnagesAuto(d, positionEntitee, personnages, obstaclesDonjon);
        Hashtable<Equipement, int[]> positionEquip = new Hashtable<>();
        positionEquip = poserEquipementAuto(d, positionEquip, obstaclesDonjon, positionEntitee);
        d.setValeurDonjon(positionEntitee, positionEquip, obstaclesDonjon);


        return d;
    }

    private static Donjon tailleDonjonAuto(){
        int[] tailleTabl = new int[] {randomValue(15, 25), randomValue(15,25)};
        Donjon d = new Donjon(tailleTabl);
        return d;
    }

    private static ArrayList<int[]> poserObstaclesAuto(Donjon d, ArrayList<int[]> obstaclesDonjon, int nbrObstacles){
        for(int i = 0; i < nbrObstacles; i++) {
            boolean placementPossible;
            do {
                int[] positionObstacle = {randomValue(0, d.m_tailleMap[0]), randomValue(0, d.m_tailleMap[1])};
                placementPossible = !d.existeAEmplacement(positionObstacle, obstaclesDonjon, null, null);
                if(placementPossible){
                    obstaclesDonjon.add(positionObstacle);
                }
            }while (!placementPossible);
        }

        return obstaclesDonjon;
    }

    private static Hashtable<Entitee, int[]> poserMonstresAuto(Donjon d, Hashtable<Entitee, int[]> positionEntitee, int nbrMonstres, ArrayList<int[]> obstaclesDonjon){
        for(int i = 0; i < nbrMonstres; i++) { //Par défaut, nous allons utiliser 3 monstres (le nombre de monstres créés par defaut)
            boolean placementPossible;
            do {
                int[] positionMonstre = {randomValue(0, d.m_tailleMap[0]), randomValue(0, d.m_tailleMap[1])};
                placementPossible = !d.existeAEmplacement(positionMonstre, obstaclesDonjon, positionEntitee, null);
                if(placementPossible){
                    positionEntitee.put(ListeMonstres.utiliserMonstreAuto(randomValue(0, ListeMonstres.nbMonstresDispo())), positionMonstre);
                }
            }while (!placementPossible);
        }

        return positionEntitee;
    }

    private static Hashtable<Entitee, int[]> poserPersonnagesAuto(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<Personnage> personnages, ArrayList<int[]> obstaclesDonjon){
        for(Personnage personnage : personnages) {
            boolean placementPossible;
            do {
                int[] positionPerso = {randomValue(0, d.m_tailleMap[0]), randomValue(0, d.m_tailleMap[1])};
                placementPossible = !d.existeAEmplacement(positionPerso, obstaclesDonjon, positionEntitee, null);
                if(placementPossible){
                    positionEntitee.put(personnage, positionPerso);
                }
            }while (!placementPossible);
        }
        return positionEntitee;
    }

    private static Hashtable<Equipement, int[]> poserEquipementAuto(Donjon d, Hashtable<Equipement, int[]> positionEquip, ArrayList<int[]> obstaclesDonjon, Hashtable<Entitee, int[]> positionEntitee){
        for(int i = 0; i < 5; i++) {
            boolean placementPossible;
            do {
                int[] positionEquipement = {randomValue(0, d.m_tailleMap[0]), randomValue(0, d.m_tailleMap[1])};
                placementPossible = !d.existeAEmplacement(positionEquipement, obstaclesDonjon, positionEntitee, positionEquip);
                if(placementPossible){
                    positionEquip.put(ListeEquipements.utiliserEquipAuto(i), positionEquipement);
                }
            }while (!placementPossible);
        }

        return positionEquip;
    }



    private static Donjon donjonManuel(ArrayList<Personnage> personnages) {

        Donjon d = tailleDonjonManuel();
        int nombreDobstacles = randomValue(5, 10);
        ArrayList<int[]> obstaclesDonjon = new ArrayList<>();
        obstaclesDonjon = poserObstaclesManuel(d, obstaclesDonjon);
        Hashtable<Entitee, int[]> positionEntitee = new Hashtable<>();
        Affichage.affiche("Souhaitez vous utiliser des monstres par défaut ou créer les vôtres ? \n0: Par défaut\t\t1: Créer les miens");
        int choixMonstre = Scanner.demandeInt();
        if(choixMonstre == 1){
            ArrayList<Monstre> mesMonstres = Monstre.creerMonstres();
            if(mesMonstres == null) positionEntitee = poserListeMonstresManuel(d, positionEntitee, obstaclesDonjon);
            positionEntitee = poserMesMonstresManuel(d, positionEntitee, mesMonstres, obstaclesDonjon);
        }
        else{
            if(choixMonstre != 0){Affichage.affiche("Choix invalide, monstres par défauts.");}
            positionEntitee = poserListeMonstresManuel(d, positionEntitee, obstaclesDonjon);
        }
        positionEntitee = poserPersonnagesManuel(d, positionEntitee, personnages, obstaclesDonjon);
        Hashtable<Equipement, int[]> positionEquip = new Hashtable<>();
        positionEquip = poserEquipementManuel(d, positionEquip, obstaclesDonjon, positionEntitee);
        d.setValeurDonjon(positionEntitee, positionEquip, obstaclesDonjon);

        return d;
    }

    private static Donjon tailleDonjonManuel(){

        int[] tailleTabl = new int[2];
        Affichage.affiche("Quelle taille de carte souhaitez-vous pour le donjon ? (Comprise entre 15 et 25)");

        do {
            Affichage.affiche("En longueur ?");
            tailleTabl[0] = Scanner.demandeInt();
        }while (tailleTabl[0] < 15 || tailleTabl[0] > 25);

        do {
            Affichage.affiche("En largeur ?");
            tailleTabl[1] = Scanner.demandeInt();
        }while (tailleTabl[1] < 15 || tailleTabl[1] > 25);

        Donjon d = new Donjon(tailleTabl);

        return d;
    }

    private static ArrayList<int[]> poserObstaclesManuel(Donjon d, ArrayList<int[]> obstaclesDonjon){
        int limiteObstacles = (d.m_tailleMap[0] * d.m_tailleMap[1] - (d.m_tailleMap[0] - 1) * d.m_tailleMap[1] - 1);
        int nombreDobstacles;
        do {
            Affichage.affiche(("Combien d'obstacles voulez vous ? Maximum : " + limiteObstacles));
            nombreDobstacles = Scanner.demandeInt();
        }while (!(nombreDobstacles <= limiteObstacles));

        for(int i = 0; i < nombreDobstacles; i++) {
            boolean placementPossible;
            do {
                Affichage.affiche(("Obstacle n°" + i + ". Où voulez-vous le placer ? (Format x;y)x\n"));
                String emplacementObstacle;
                int[] positionObstacle = {0,0};
                String[] coordonnees = {"",""};
                do {
                    emplacementObstacle = Scanner.demandeString();
                    coordonnees = emplacementObstacle.split(";");
                    if(!emplacementObstacle.contains(";")) Affichage.affiche("Veuillez respecter le format  x;y");
                    positionObstacle[0] = Integer.parseInt(coordonnees[0]);
                    positionObstacle[1] = Integer.parseInt(coordonnees[1]);
                    if((positionObstacle[0] >= d.m_tailleMap[0] || positionObstacle[0] < 0) || (positionObstacle[1] >= d.m_tailleMap[1] && positionObstacle[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                }while(!emplacementObstacle.contains(";") || (positionObstacle[0] >= d.m_tailleMap[0] || positionObstacle[0] < 0) || (positionObstacle[1] >= d.m_tailleMap[1] && positionObstacle[1] < 0));
                placementPossible = !d.existeAEmplacement(positionObstacle, obstaclesDonjon, null, null);
                if(placementPossible){
                    obstaclesDonjon.add(positionObstacle);
                }
                else{Affichage.affiche("Il y a déjà quelque chose à cet emplacement");}
            } while (!placementPossible);
        }

        return obstaclesDonjon;
    }

    private static Hashtable<Entitee, int[]> poserMesMonstresManuel(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<Monstre> mesMonstres, ArrayList<int[]> obstaclesDonjon){
        for(int i = 0; i < mesMonstres.size(); i++) {
            Affichage.affiche("Monstres disponibles : \n\n");
            Monstre.afficherMonstreDispo(mesMonstres);
            boolean placementPossible;
            int numMonstre = 0;
            do{
                Affichage.affiche("Quel monstre ? Entrez son numéro.");
                numMonstre = Scanner.demandeInt();
            }while(!(numMonstre < mesMonstres.size()));

            do {
                Affichage.affiche(mesMonstres.get(numMonstre).getAppellation() + ". Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementMonstre;
                int[] positionMonstre = {0,0};
                String[] coordonnees = {"",""};
                do {
                    emplacementMonstre = Scanner.demandeString();
                    coordonnees = emplacementMonstre.split(";");
                    if(!emplacementMonstre.contains(";")) Affichage.affiche("Veuillez respecter le format  x;y");
                    positionMonstre[0] = Integer.parseInt(coordonnees[0]);
                    positionMonstre[1] = Integer.parseInt(coordonnees[1]);
                    if((positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                }while(!emplacementMonstre.contains(";") || (positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0));
                placementPossible = !d.existeAEmplacement(positionMonstre, obstaclesDonjon, positionEntitee, null);
                if(placementPossible){
                    positionEntitee.put(Monstre.utiliserMonstre(numMonstre, mesMonstres), positionMonstre);
                }
                else{Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");}
            }while (!placementPossible);
        }

        return positionEntitee;
    }

    private static Hashtable<Entitee, int[]> poserListeMonstresManuel(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<int[]> obstaclesDonjon){
        Affichage.affiche("Il y a " + ListeMonstres.nbMonstresDispo() + " monstre(s) de disponible(s)\nCombien de monstres souhaitez-vous placer ?\n");
        int nbrMonstres;
        do{
            nbrMonstres = Scanner.demandeInt();
        }while(!(nbrMonstres <= ListeMonstres.nbMonstresDispo()));
        /*
         * Quand on aura refactor faudra ajouter la possibilité au MJ s'il le veut de créer des monstres
         * donc juste faire une fonction creerMonstre dans monstre et l'utiliser s'il dit oui.
         * Une série de Scanners pour ensuite intégrer le monstre avec ListeMonstres.add()
         */
        for(int i = 0; i < nbrMonstres; i++) {
            Affichage.affiche("Monstres disponibles : \n\n");
            ListeMonstres.afficherMonstreDispo();
            boolean placementPossible;

            int numMonstre = 0;
            do{
                Affichage.affiche("Quel monstre ? Entrez son numéro.");
                numMonstre = Scanner.demandeInt();
            }while(!(numMonstre < ListeMonstres.nbMonstresDispo()));

            do {
                Affichage.affiche(ListeMonstres.getNomMonstre(numMonstre) + ". Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementMonstre;
                int[] positionMonstre = {0,0};
                String[] coordonnees = {"",""};
                do {
                    emplacementMonstre = Scanner.demandeString();
                    coordonnees = emplacementMonstre.split(";");
                    if(!emplacementMonstre.contains(";")) Affichage.affiche("Veuillez respecter le format  x;y");
                    positionMonstre[0] = Integer.parseInt(coordonnees[0]);
                    positionMonstre[1] = Integer.parseInt(coordonnees[1]);
                    if((positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                }while(!emplacementMonstre.contains(";") || (positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0));
                placementPossible = !d.existeAEmplacement(positionMonstre, obstaclesDonjon, positionEntitee, null);
                if(placementPossible){
                    positionEntitee.put(ListeMonstres.utiliserMonstre(numMonstre), positionMonstre);
                }
                else{Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");}

            }while (!placementPossible);

        }

        return positionEntitee;
    }

    private static Hashtable<Entitee, int[]> poserPersonnagesManuel(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<Personnage> personnages, ArrayList<int[]> obstaclesDonjon){
        int compteur = 0;
        for(Personnage personnage : personnages) {
            boolean placementPossible = false;
            do {
                Affichage.affiche("Joueur : " + personnages.get(compteur).getNom() + " : Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementPerso;
                int[] positionPerso = {0,0};
                String[] coordonnees = {"",""};
                do {
                    emplacementPerso = Scanner.demandeString();
                    coordonnees = emplacementPerso.split(";");
                    if(!emplacementPerso.contains(";")) Affichage.affiche("Veuillez respecter le format  x;y");
                    positionPerso[0] = Integer.parseInt(coordonnees[0]);
                    positionPerso[1] = Integer.parseInt(coordonnees[1]);
                    if((positionPerso[0] >= d.m_tailleMap[0] || positionPerso[0] < 0) || (positionPerso[1] >= d.m_tailleMap[1] && positionPerso[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                }while(!emplacementPerso.contains(";") || (positionPerso[0] >= d.m_tailleMap[0] || positionPerso[0] < 0) || (positionPerso[1] >= d.m_tailleMap[1] && positionPerso[1] < 0));
                placementPossible =  !d.existeAEmplacement(positionPerso, obstaclesDonjon, positionEntitee, null);
                if(placementPossible){
                    positionEntitee.put(personnage, positionPerso);
                    compteur++;
                }
                else{Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");}
            }while (!placementPossible);
        }

        return positionEntitee;
    }

    private static Hashtable<Equipement, int[]> poserEquipementManuel(Donjon d, Hashtable<Equipement, int[]> positionEquip, ArrayList<int[]> obstaclesDonjon, Hashtable<Entitee, int[]> positionEntitee){
        Affichage.affiche("Il y a " + ListeEquipements.nbEquipDispo() + " équipement(s) de disponible(s)\nCombien d'équipements souhaitez-vous placer ?\n");
        int nbrEquips;
        do{
            nbrEquips = Scanner.demandeInt();
        }while(!(nbrEquips <= ListeEquipements.nbEquipDispo()));


        for(int i = 0; i < nbrEquips; i++) {
            Affichage.affiche("Equipements disponibles : \n\n");
            ListeEquipements.afficherEquipDispo();
            boolean placementPossible;

            int numEquip = 0;
            do{
                Affichage.affiche("Quel équipement ? Entrez son numéro.");
                numEquip = Scanner.demandeInt();
            }while(!(numEquip < ListeEquipements.nbEquipDispo()));

            do {
                Affichage.affiche(ListeEquipements.getNomEquip(numEquip) + ". Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementEquip;
                int[] posEquip = {0,0};
                String[] coordonnees = {"",""};
                do {
                    emplacementEquip = Scanner.demandeString();
                    coordonnees = emplacementEquip.split(";");
                    if(!emplacementEquip.contains(";")) Affichage.affiche("Veuillez respecter le format  x;y");
                    posEquip[0] = Integer.parseInt(coordonnees[0]);
                    posEquip[1] = Integer.parseInt(coordonnees[1]);
                    if((posEquip[0] >= d.m_tailleMap[0] || posEquip[0] < 0) || (posEquip[1] >= d.m_tailleMap[1] && posEquip[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                }while(!emplacementEquip.contains(";") || (posEquip[0] >= d.m_tailleMap[0] || posEquip[0] < 0) || (posEquip[1] >= d.m_tailleMap[1] && posEquip[1] < 0));
                placementPossible = !d.existeAEmplacement(posEquip, obstaclesDonjon, positionEntitee, positionEquip);
                if(placementPossible){
                    positionEquip.put(ListeEquipements.utiliserEquipement(numEquip), posEquip);
                }
                else{Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");}

            }while (!placementPossible);

        }

        return positionEquip;
    }


    public static Donjon creerDonjon(ArrayList<Personnage> personnages){
        int reponseCreation = -1;
        do {
            Affichage.affiche("Voulez-vous créer un donjon ou en utiliser un généré par défaut ?\n0: Par moi-même \t 1: Par défaut");
            reponseCreation = Scanner.demandeInt();
        }while (reponseCreation != 0 && reponseCreation != 1);


        if(reponseCreation == 0){

            return donjonManuel(personnages);
        }

        else{
            return donjonAuto(personnages);
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

    public static int randomValue(int borneInf, int borneSup){
        Random random = new Random();
        return borneInf + random.nextInt(borneSup - borneInf);
    }

    public boolean existeAEmplacement(int[] aVerifier){
        boolean existe = false;

        /*//Verification avec les obstacles
        //existe = m_obstacles.contains(aVerifier);
        for(int i = 0; i < m_obstacles.size() ;i++){
            existe = m_obstacles.get(i).equals(aVerifier);
        }

        //Verification avec les entitées (monstres et personnages)
        for(int[] positionEntitee : m_positionEntitee.values()){
            existe = positionEntitee[0] == aVerifier[0] && positionEntitee[1] == aVerifier[1];
        }*/

        existe = verifAEmplacement(aVerifier);
        if(existe){return existe;}

        //Verification avec les équipements
        for(int[] positionEquip : m_positionEquip.values()){
            existe = positionEquip[0] == aVerifier[0] && positionEquip[1] == aVerifier[1];
            if(existe){return existe;}
        }

        return existe;
    }

    public boolean existeAEmplacement(int[] aVerifier, ArrayList<int[]> obstacles,
                                      Hashtable<Entitee, int[]> posEntitee,
                                      Hashtable<Equipement, int[]> posEquip
                                      ){
        boolean existe = false;

        //Verification avec les obstacles
        //existe = m_obstacles.contains(aVerifier);
        for(int i = 0; i < obstacles.size() ;i++){
            existe = obstacles.get(i)[0] == aVerifier[0] && obstacles.get(i)[1] == aVerifier[1];
            if(existe){return existe;}
        }

        //Verification avec les entitées (monstres et personnages)
        if(posEntitee != null) {
            for (int[] positionEntitee : posEntitee.values()) {
                existe = positionEntitee[0] == aVerifier[0] && positionEntitee[1] == aVerifier[1];
                if(existe){return existe;}
            }
        }

        //Verification avec les équipements
        if(posEquip != null) {
            for (int[] positionEquip : posEquip.values()) {
                existe = positionEquip[0] == aVerifier[0] && positionEquip[1] == aVerifier[1];
                if(existe){return existe;}
            }
        }
        return existe;
    }

    public boolean verifAEmplacement(int[] aVerifier){
        boolean existe = false;

        //Verification avec les obstacles
        for(int i = 0; i<m_obstacles.size(); i++){
            existe = m_obstacles.get(i)[0] == aVerifier[0] && m_obstacles.get(i)[1] == aVerifier[1];
            if(existe){return existe;}
        }


        //Verification avec les entitées (monstres et personnages)
        for(int[] positionEntitee : m_positionEntitee.values()){
            existe = positionEntitee[0] == aVerifier[0] && positionEntitee[1] == aVerifier[1];
            if(existe){return existe;}
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

            if(m_positionEquip.get(equip)[0] == position[0] && m_positionEquip.get(equip)[1] == position[1]){
                equipAPos = true;
            }

        }
        return equipAPos;
    }

    public Equipement getEquipAPos(int[] position){
        for(Equipement equip : m_positionEquip.keySet()){
            if(m_positionEquip.get(equip)[0] == position[0] && m_positionEquip.get(equip)[1] == position[1]){
                return equip;
            }
        }
        return null;
    }

    public void supprEquip(Equipement equip){
        m_positionEquip.remove(equip);
    }
    public void supprEntite(Entitee e){m_positionEntitee.remove(e);}


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
                if(initiativeEntitees.get(i) < initiativeEntitees.get(j)){

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
    public ArrayList<int[]> getObstacle()
    {
        return m_obstacles;
    }

    public Hashtable<Entitee, int[]> getPositionEntitee() {
        return m_positionEntitee;
    }
    public Hashtable<Equipement, int[]> getPositionEquipement() {
        return m_positionEquip;
    }
    public boolean ajouterObstacle(int[] position)
    {
        if(!existeAEmplacement(position))
        {
            m_obstacles.add(position);
            return true;
        }
        else
        {
            return false;
        }
    }
    public void echangePosEntite(Entitee e1, Entitee e2)
    {
        int[] pos1 = m_positionEntitee.get(e1);
        int[] pos2 = m_positionEntitee.get(e2);

        deplacerEntitee(e1, pos2);
        deplacerEntitee(e2, pos1);
    }
    public ArrayList<Entitee> getListeEntite()
    {
        return new ArrayList<>(m_positionEntitee.keySet());
    }
    public ArrayList<Entitee> getListePersonnage()
    {
        ArrayList<Entitee> p = new ArrayList<>();
        for (Entitee e : m_positionEntitee.keySet())
        {
            if (e.getType() == TypeEntitee.PERSONNAGE)
            {
                p.add(e);
            }
        }
        return p;
    }
}