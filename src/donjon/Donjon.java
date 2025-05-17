package donjon;

import equipement.*;
import entitee.*;
import interactionUtilisateur.*;
import interactionUtilisateur.Scanner;

import java.lang.reflect.Array;
import java.util.*;

public class Donjon {
    private Hashtable<Entitee, int[]> m_positionEntitee = new Hashtable<>();
    private Hashtable<Equipement, int[]> m_positionEquip = new Hashtable<>();
    private ArrayList<int[]> m_obstacles = new ArrayList<>();
    private int[] m_tailleMap = new int[2];
    private boolean m_estVaincu;

    //jcrois bien qu'on va être obligés de mettre en paramtères liste Equipements vu que cest le 1er truc a instancier
    //et qu'il doit etre instancié HORS de donjon en tout premier vu que bah il reste pareil meme en changeant de donjon

    //Et je me dis qu'on aura peut être besoin dune valeur "donjon n°" que t'as peut etre créé déjà dans ta partie

    public Donjon() {
        m_estVaincu = false;
        creerDonjons();
    }


    public Donjon(ListeEquipements listeEquipements) {
        int tailleTabl = randomValue(15, 25);
        m_tailleMap[0] = tailleTabl;
        m_tailleMap[1] = tailleTabl;

        int nombreDobstacles = randomValue(5, 10);
        for(int i = 0; i < nombreDobstacles; i++) {
            boolean placementPossible;
            do {
                int[] positionObstacle = {randomValue(0, 25), randomValue(0, 25)};
                placementPossible =  !existeAEmplacement(positionObstacle);
                if(placementPossible);
            }while (!placementPossible);
        }




    }

    public Donjon(int[] tailleMap) {
        m_tailleMap[0] = tailleMap[0];
        m_tailleMap[1] = tailleMap[1];
        //Limiter le nbr d'obstacles à placer à longueur*largeurCarte - (longueur-1)*(largeur-1)Carte
        //Minimum (Carte 15*15) 29 ; Maximum (Carte 25*25) 49

        int limiteObstacles = (m_tailleMap[0] * m_tailleMap[1] - (m_tailleMap[0] - 1) * m_tailleMap[1] - 1);
        int nombreDobstacles;
        do {
            Affichage.Affiche(("Combien d'obstacles voulez vous ? Maximum : " + limiteObstacles));
            nombreDobstacles = Scanner.demandeInt();
        }while (!(nombreDobstacles <= limiteObstacles));

        for(int i = 0; i < nombreDobstacles; i++) {
            boolean placementPossible;
            do {
                Affichage.Affiche(("Obstacle n°" + i + ". Où voulez-vous le placer ? (Format x;y)x\n"));
                String emplacementObstacle = Scanner.demandeString();
                int[] positionObstacle = {randomValue(0, 25), randomValue(0, 25)};
                placementPossible = !existeAEmplacement(positionObstacle);
            } while (!placementPossible);
        }


    }

    public static void creerDonjons(){
        int reponseCreation = -1;
        do {
            Affichage.affiche("Voulez-vous créer un donjon ou en utiliser un généré par défaut ?\n0: Par moi-même \t 1: Par défaut");
            reponseCreation = Scanner.demandeInt();
        }while (reponseCreation != 0 || reponseCreation != 1);


        if(reponseCreation == 0){
            Donjon donjon = new Donjon();
        }

        else{
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


            Donjon donjon = new Donjon(tailleTabl);
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
        for(int i = 0; i<m_obstacles.length; i++){
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

    public int getLongueur(){
        return m_tailleMap[0];
    }

    public int getLargeur(){
        return m_tailleMap[1];
    }

    public int[] getPosEntitee(Entitee entitee){
        return m_positionEntitee.get(entitee);
    }

    public void
}