package donjon;

import entitee.personnage.Personnage;
import equipement.*;
import entitee.*;
import interactionUtilisateur.*;

import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;

/**
 * Représente un donjon dans le jeu, contenant des entités, des équipements et des obstacles.
 */
public class Donjon {
    private Hashtable<Entitee, int[]> m_positionEntitee;
    private Hashtable<Equipement, int[]> m_positionEquip;
    private ArrayList<int[]> m_obstacles = new ArrayList<>();
    private int[] m_tailleMap;

    /**
     * Construit un donjon avec une taille de carte spécifiée.
     *
     * @param tailleMap la taille de la carte du donjon
     */
    public Donjon(int[] tailleMap) {
        m_tailleMap = tailleMap;
        m_positionEntitee = new Hashtable<>();
        m_positionEquip = new Hashtable<>();
    }

    /**
     * Construit un donjon avec des positions d'entités, d'équipements, des obstacles et une taille de carte spécifiés.
     *
     * @param posEntitee les positions des entités
     * @param posEquip les positions des équipements
     * @param obstacles les obstacles du donjon
     * @param tailleMap la taille de la carte du donjon
     */
    public Donjon(Hashtable<Entitee, int[]> posEntitee,
                  Hashtable<Equipement, int[]> posEquip,
                  ArrayList<int[]> obstacles,
                  int[] tailleMap) {
        m_positionEntitee = posEntitee;
        m_positionEquip = posEquip;
        m_obstacles = obstacles;
        m_tailleMap = tailleMap;
    }

    /**
     * Définit les valeurs du donjon avec les positions des entités, des équipements et des obstacles.
     *
     * @param posEntitee les positions des entités
     * @param posEquip les positions des équipements
     * @param obstacles les obstacles du donjon
     */
    private void setValeurDonjon(Hashtable<Entitee, int[]> posEntitee,
                                 Hashtable<Equipement, int[]> posEquip,
                                 ArrayList<int[]> obstacles) {
        m_positionEntitee = new Hashtable<>();
        for (Entitee e : posEntitee.keySet()) {
            m_positionEntitee.put(e, posEntitee.get(e).clone());
        }

        m_positionEquip = new Hashtable<>();
        for (Equipement eq : posEquip.keySet()) {
            m_positionEquip.put(eq, posEquip.get(eq).clone());
        }

        m_obstacles = new ArrayList<>();
        for (int[] obs : obstacles) {
            m_obstacles.add(obs.clone());
        }
    }

    /**
     * Crée un donjon automatiquement avec des personnages spécifiés.
     *
     * @param personnages les personnages à placer dans le donjon
     * @return le donjon généré automatiquement
     */
    private static Donjon donjonAuto(ArrayList<Personnage> personnages) {
        Donjon d = tailleDonjonAuto();
        int nombreDobstacles = randomValue(5, 10);
        ArrayList<int[]> obstaclesDonjon = new ArrayList<>();
        poserObstaclesAuto(d, obstaclesDonjon, nombreDobstacles);
        Hashtable<Entitee, int[]> positionEntitee = new Hashtable<>();
        poserMonstresAuto(d, positionEntitee, randomValue(2, 4), obstaclesDonjon);
        poserPersonnagesAuto(d, positionEntitee, personnages, obstaclesDonjon);
        Hashtable<Equipement, int[]> positionEquip = new Hashtable<>();
        poserEquipementAuto(d, positionEquip, obstaclesDonjon, positionEntitee);
        d.setValeurDonjon(positionEntitee, positionEquip, obstaclesDonjon);
        ListeMonstres.retourEtatInitialMonstres();
        ListeEquipements.retourEtatInitialEquipements();
        return d;
    }

    /**
     * Définit une taille aléatoire pour le donjon.
     *
     * @return le donjon avec une taille aléatoire
     */
    private static Donjon tailleDonjonAuto() {
        int[] tailleTabl = new int[]{randomValue(15, 25), randomValue(15, 25)};
        return new Donjon(tailleTabl);
    }

    /**
     * Place des obstacles automatiquement dans le donjon.
     *
     * @param d le donjon
     * @param obstaclesDonjon la liste des obstacles du donjon
     * @param nbrObstacles le nombre d'obstacles à placer
     */
    private static void poserObstaclesAuto(Donjon d, ArrayList<int[]> obstaclesDonjon, int nbrObstacles) {
        int[] positionObstacle = new int[2];
        for (int i = 0; i < nbrObstacles; i++) {
            do {
                positionObstacle[0] = randomValue(0, d.m_tailleMap[0]);
                positionObstacle[1] = randomValue(0, d.m_tailleMap[1]);
            } while (d.existeAEmplacement(positionObstacle, obstaclesDonjon, null, null));
            obstaclesDonjon.add(positionObstacle.clone());
        }
    }

    /**
     * Place des monstres automatiquement dans le donjon.
     *
     * @param d le donjon
     * @param positionEntitee les positions des entités
     * @param nbrMonstres le nombre de monstres à placer
     * @param obstaclesDonjon la liste des obstacles du donjon
     */
    private static void poserMonstresAuto(Donjon d, Hashtable<Entitee, int[]> positionEntitee, int nbrMonstres, ArrayList<int[]> obstaclesDonjon) {
        int[] positionMonstre = new int[2];
        for (int i = 0; i < nbrMonstres; i++) {
            do {
                positionMonstre[0] = randomValue(0, d.m_tailleMap[0]);
                positionMonstre[1] = randomValue(0, d.m_tailleMap[1]);
            } while (d.existeAEmplacement(positionMonstre, obstaclesDonjon, positionEntitee, null));
            positionEntitee.put(ListeMonstres.utiliserMonstreAuto(randomValue(0, ListeMonstres.nbMonstresDispo())), positionMonstre.clone());
        }
    }

    /**
     * Place des personnages automatiquement dans le donjon.
     *
     * @param d le donjon
     * @param positionEntitee les positions des entités
     * @param personnages les personnages à placer
     * @param obstaclesDonjon la liste des obstacles du donjon
     */
    private static void poserPersonnagesAuto(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<Personnage> personnages, ArrayList<int[]> obstaclesDonjon) {
        int[] positionPerso = new int[2];
        for (Personnage personnage : personnages) {
            do {
                positionPerso[0] = randomValue(0, d.m_tailleMap[0]);
                positionPerso[1] = randomValue(0, d.m_tailleMap[1]);
            } while (d.existeAEmplacement(positionPerso, obstaclesDonjon, positionEntitee, null));
            positionEntitee.put(personnage, positionPerso.clone());
        }
    }

    /**
     * Place des équipements automatiquement dans le donjon.
     *
     * @param d le donjon
     * @param positionEquip les positions des équipements
     * @param obstaclesDonjon la liste des obstacles du donjon
     * @param positionEntitee les positions des entités
     */
    private static void poserEquipementAuto(Donjon d, Hashtable<Equipement, int[]> positionEquip, ArrayList<int[]> obstaclesDonjon, Hashtable<Entitee, int[]> positionEntitee) {
        int[] positionEquipement = new int[2];
        for (int i = 0; i < 5; i++) {
            do {
                positionEquipement[0] = randomValue(0, d.m_tailleMap[0]);
                positionEquipement[1] = randomValue(0, d.m_tailleMap[1]);
            } while (d.existeAEmplacement(positionEquipement, obstaclesDonjon, positionEntitee, positionEquip));
            positionEquip.put(ListeEquipements.utiliserEquipAuto(i), positionEquipement.clone());
        }
    }

    /**
     * Crée un donjon manuellement avec des personnages spécifiés.
     *
     * @param personnages les personnages à placer dans le donjon
     * @return le donjon créé manuellement
     */
    private static Donjon donjonManuel(ArrayList<Personnage> personnages) {
        Donjon d = tailleDonjonManuel();
        ArrayList<int[]> obstaclesDonjon = new ArrayList<>();
        poserObstaclesManuel(d, obstaclesDonjon);
        Hashtable<Entitee, int[]> positionEntitee = new Hashtable<>();
        Affichage.affiche("Souhaitez vous utiliser des monstres par défaut ou créer les vôtres ? \n0: Par défaut\t\t1: Créer les miens");
        int choixMonstre = Scanner.demandeInt();
        if (choixMonstre == 1) {
            ArrayList<Monstre> mesMonstres = Monstre.creerMonstres();
            if (mesMonstres == null) poserListeMonstresManuel(d, positionEntitee, obstaclesDonjon);
            else poserMesMonstresManuel(d, positionEntitee, mesMonstres, obstaclesDonjon);
        } else {
            if (choixMonstre != 0) {
                Affichage.affiche("Choix invalide, monstres par défauts.");
            }
            poserListeMonstresManuel(d, positionEntitee, obstaclesDonjon);
        }
        poserPersonnagesManuel(d, positionEntitee, personnages, obstaclesDonjon);
        Hashtable<Equipement, int[]> positionEquip = new Hashtable<>();
        poserEquipementManuel(d, positionEquip, obstaclesDonjon, positionEntitee);
        d.setValeurDonjon(positionEntitee, positionEquip, obstaclesDonjon);
        ListeMonstres.retourEtatInitialMonstres();
        ListeEquipements.retourEtatInitialEquipements();
        return d;
    }

    /**
     * Définit une taille manuellement pour le donjon.
     *
     * @return le donjon avec une taille définie manuellement
     */
    private static Donjon tailleDonjonManuel() {
        int[] tailleTabl = new int[2];
        Affichage.affiche("Quelle taille de carte souhaitez-vous pour le donjon ? (Comprise entre 15 et 25)");
        do {
            Affichage.affiche("En longueur ?");
            tailleTabl[0] = Scanner.demandeInt();
        } while (tailleTabl[0] < 15 || tailleTabl[0] > 25);
        do {
            Affichage.affiche("En largeur ?");
            tailleTabl[1] = Scanner.demandeInt();
        } while (tailleTabl[1] < 15 || tailleTabl[1] > 25);
        return new Donjon(tailleTabl);
    }

    /**
     * Place des obstacles manuellement dans le donjon.
     *
     * @param d le donjon
     * @param obstaclesDonjon la liste des obstacles du donjon
     */
    private static void poserObstaclesManuel(Donjon d, ArrayList<int[]> obstaclesDonjon) {
        int limiteObstacles = (d.m_tailleMap[0] * d.m_tailleMap[1] - (d.m_tailleMap[0] - 1) * d.m_tailleMap[1] - 1);
        int nombreDobstacles;
        do {
            Affichage.affiche(("Combien d'obstacles voulez vous ? Maximum : " + limiteObstacles));
            nombreDobstacles = Scanner.demandeInt();
        } while (!(nombreDobstacles <= limiteObstacles));

        for (int i = 0; i < nombreDobstacles; i++) {
            boolean placementPossible;
            do {
                Affichage.affiche(("Obstacle n°" + i + ". Où voulez-vous le placer ? (Format x;y)\n"));
                String emplacementObstacle;
                int[] positionObstacle = {0, 0};
                String[] coordonnees;
                do {
                    emplacementObstacle = Scanner.demandeString();
                    coordonnees = emplacementObstacle.split(";");
                    if (!emplacementObstacle.contains(";"))
                        Affichage.affiche("Veuillez respecter le format x;y");
                    if (coordonnees.length == 2) {
                        positionObstacle[0] = Integer.parseInt(coordonnees[0]);
                        positionObstacle[1] = Integer.parseInt(coordonnees[1]);
                    }
                    if ((positionObstacle[0] >= d.m_tailleMap[0] || positionObstacle[0] < 0) || (positionObstacle[1] >= d.m_tailleMap[1] && positionObstacle[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                } while (!emplacementObstacle.contains(";") || (positionObstacle[0] >= d.m_tailleMap[0] || positionObstacle[0] < 0) || (positionObstacle[1] >= d.m_tailleMap[1] && positionObstacle[1] < 0));
                placementPossible = !d.existeAEmplacement(positionObstacle, obstaclesDonjon, null, null);
                if (placementPossible) {
                    obstaclesDonjon.add(positionObstacle.clone());
                } else {
                    Affichage.affiche("Il y a déjà quelque chose à cet emplacement");
                }
            } while (!placementPossible);
        }
    }

    /**
     * Place les monstres créés manuellement dans le donjon.
     *
     * @param d le donjon
     * @param positionEntitee les positions des entités
     * @param mesMonstres les monstres à placer
     * @param obstaclesDonjon la liste des obstacles du donjon
     */
    private static void poserMesMonstresManuel(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<Monstre> mesMonstres, ArrayList<int[]> obstaclesDonjon) {
        for (int i = 0; i < mesMonstres.size(); i++) {
            Affichage.affiche("Monstres disponibles : \n\n");
            Affichage.afficherMonstre(mesMonstres);
            boolean placementPossible;
            int numMonstre;
            do {
                Affichage.affiche("Quel monstre ? Entrez son numéro.");
                numMonstre = Scanner.demandeInt();
            } while (!(numMonstre < mesMonstres.size()));

            do {
                Affichage.affiche(mesMonstres.get(numMonstre).getAppellation() + ". Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementMonstre;
                int[] positionMonstre = {0, 0};
                String[] coordonnees;
                do {
                    emplacementMonstre = Scanner.demandeString();
                    coordonnees = emplacementMonstre.split(";");
                    if (!emplacementMonstre.contains(";"))
                        Affichage.affiche("Veuillez respecter le format x;y");
                    if (coordonnees.length == 2) {
                        positionMonstre[0] = Integer.parseInt(coordonnees[0]);
                        positionMonstre[1] = Integer.parseInt(coordonnees[1]);
                    }
                    if ((positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                } while (!emplacementMonstre.contains(";") || (positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0));
                placementPossible = !d.existeAEmplacement(positionMonstre, obstaclesDonjon, positionEntitee, null);
                if (placementPossible) {
                    positionEntitee.put(Monstre.utiliserMonstre(numMonstre, mesMonstres), positionMonstre.clone());
                } else {
                    Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");
                }
            } while (!placementPossible);
        }
    }

    /**
     * Place une liste de monstres par défaut manuellement dans le donjon.
     *
     * @param d le donjon
     * @param positionEntitee les positions des entités
     * @param obstaclesDonjon la liste des obstacles du donjon
     */
    private static void poserListeMonstresManuel(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<int[]> obstaclesDonjon) {
        Affichage.affiche("Il y a " + ListeMonstres.nbMonstresDispo() + " monstre(s) de disponible(s)\nCombien de monstres souhaitez-vous placer ?\n");
        int nbrMonstres;
        do {
            nbrMonstres = Scanner.demandeInt();
        } while (!(nbrMonstres <= ListeMonstres.nbMonstresDispo()));

        for (int i = 0; i < nbrMonstres; i++) {
            Affichage.affiche("Monstres disponibles : \n\n");
            Affichage.afficherMonstre(ListeMonstres.getListeMonstres());
            boolean placementPossible;

            int numMonstre;
            do {
                Affichage.affiche("Quel monstre ? Entrez son numéro.");
                numMonstre = Scanner.demandeInt();
            } while (!(numMonstre < ListeMonstres.nbMonstresDispo()));

            do {
                Affichage.affiche(ListeMonstres.getNomMonstre(numMonstre) + ". Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementMonstre;
                int[] positionMonstre = {0, 0};
                String[] coordonnees;
                do {
                    emplacementMonstre = Scanner.demandeString();
                    coordonnees = emplacementMonstre.split(";");
                    if (!emplacementMonstre.contains(";"))
                        Affichage.affiche("Veuillez respecter le format x;y");
                    if (coordonnees.length == 2) {
                        positionMonstre[0] = Integer.parseInt(coordonnees[0]);
                        positionMonstre[1] = Integer.parseInt(coordonnees[1]);
                    }
                    if ((positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                } while (!emplacementMonstre.contains(";") || (positionMonstre[0] >= d.m_tailleMap[0] || positionMonstre[0] < 0) || (positionMonstre[1] >= d.m_tailleMap[1] && positionMonstre[1] < 0));
                placementPossible = !d.existeAEmplacement(positionMonstre, obstaclesDonjon, positionEntitee, null);
                if (placementPossible) {
                    positionEntitee.put(ListeMonstres.utiliserMonstre(numMonstre), positionMonstre.clone());
                } else {
                    Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");
                }
            } while (!placementPossible);
        }
    }

    /**
     * Place des personnages manuellement dans le donjon.
     *
     * @param d le donjon
     * @param positionEntitee les positions des entités
     * @param personnages les personnages à placer
     * @param obstaclesDonjon la liste des obstacles du donjon
     */
    private static void poserPersonnagesManuel(Donjon d, Hashtable<Entitee, int[]> positionEntitee, ArrayList<Personnage> personnages, ArrayList<int[]> obstaclesDonjon) {
        int compteur = 0;
        for (Personnage personnage : personnages) {
            boolean placementPossible;
            do {
                Affichage.affiche("Joueur : " + personnages.get(compteur).getNom() + " : Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementPerso;
                int[] positionPerso = {0, 0};
                String[] coordonnees;
                do {
                    emplacementPerso = Scanner.demandeString();
                    coordonnees = emplacementPerso.split(";");
                    if (!emplacementPerso.contains(";"))
                        Affichage.affiche("Veuillez respecter le format x;y");
                    if (coordonnees.length == 2) {
                        positionPerso[0] = Integer.parseInt(coordonnees[0]);
                        positionPerso[1] = Integer.parseInt(coordonnees[1]);
                    }
                    if ((positionPerso[0] >= d.m_tailleMap[0] || positionPerso[0] < 0) || (positionPerso[1] >= d.m_tailleMap[1] && positionPerso[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                } while (!emplacementPerso.contains(";") || (positionPerso[0] >= d.m_tailleMap[0] || positionPerso[0] < 0) || (positionPerso[1] >= d.m_tailleMap[1] && positionPerso[1] < 0));
                placementPossible = !d.existeAEmplacement(positionPerso, obstaclesDonjon, positionEntitee, null);
                if (placementPossible) {
                    positionEntitee.put(personnage, positionPerso.clone());
                    compteur++;
                } else {
                    Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");
                }
            } while (!placementPossible);
        }
    }

    /**
     * Place des équipements manuellement dans le donjon.
     *
     * @param d le donjon
     * @param positionEquip les positions des équipements
     * @param obstaclesDonjon la liste des obstacles du donjon
     * @param positionEntitee les positions des entités
     */
    private static void poserEquipementManuel(Donjon d, Hashtable<Equipement, int[]> positionEquip, ArrayList<int[]> obstaclesDonjon, Hashtable<Entitee, int[]> positionEntitee) {
        Affichage.affiche("Il y a " + ListeEquipements.nbEquipDispo() + " équipement(s) de disponible(s)\nCombien d'équipements souhaitez-vous placer ?\n");
        int nbrEquips;
        do {
            nbrEquips = Scanner.demandeInt();
        } while (!(nbrEquips <= ListeEquipements.nbEquipDispo()));

        for (int i = 0; i < nbrEquips; i++) {
            Affichage.affiche("Equipements disponibles : \n\n");
            Affichage.afficherEquip(ListeEquipements.getListeEquips());
            boolean placementPossible;

            int numEquip;
            do {
                Affichage.affiche("Quel équipement ? Entrez son numéro.");
                numEquip = Scanner.demandeInt();
            } while (!(numEquip < ListeEquipements.nbEquipDispo()));

            do {
                Affichage.affiche(ListeEquipements.getNomEquip(numEquip) + ". Où voulez-vous le placer ? (Format x;y)\n");
                String emplacementEquip;
                int[] posEquip = {0, 0};
                String[] coordonnees;
                do {
                    emplacementEquip = Scanner.demandeString();
                    coordonnees = emplacementEquip.split(";");
                    if (!emplacementEquip.contains(";"))
                        Affichage.affiche("Veuillez respecter le format x;y");
                    if (coordonnees.length == 2) {
                        posEquip[0] = Integer.parseInt(coordonnees[0]);
                        posEquip[1] = Integer.parseInt(coordonnees[1]);
                    }
                    if ((posEquip[0] >= d.m_tailleMap[0] || posEquip[0] < 0) || (posEquip[1] >= d.m_tailleMap[1] && posEquip[1] < 0))
                        Affichage.affiche("Veuillez rester dans le périmètre de la carte.");
                } while (!emplacementEquip.contains(";") || (posEquip[0] >= d.m_tailleMap[0] || posEquip[0] < 0) || (posEquip[1] >= d.m_tailleMap[1] && posEquip[1] < 0));
                placementPossible = !d.existeAEmplacement(posEquip, obstaclesDonjon, positionEntitee, positionEquip);
                if (placementPossible) {
                    positionEquip.put(ListeEquipements.utiliserEquipement(numEquip), posEquip.clone());
                } else {
                    Affichage.affiche("Il y a déjà quelque chose à cet emplacement.");
                }
            } while (!placementPossible);
        }
    }

    /**
     * Crée un donjon en fonction de la réponse de l'utilisateur.
     *
     * @param personnages les personnages à placer dans le donjon
     * @return le donjon créé
     */
    public static Donjon creerDonjon(ArrayList<Personnage> personnages) {
        int reponseCreation;
        do {
            Affichage.affiche("Voulez-vous créer un donjon ou en utiliser un généré par défaut ?\n0: Par moi-même \t 1: Par défaut");
            reponseCreation = Scanner.demandeInt();
        } while (reponseCreation != 0 && reponseCreation != 1);

        if (reponseCreation == 0) {
            return donjonManuel(personnages);
        } else {
            return donjonAuto(personnages);
        }
    }

    /**
     * Génère une valeur aléatoire entre deux bornes.
     *
     * @param borneInf la borne inférieure
     * @param borneSup la borne supérieure
     * @return une valeur aléatoire entre les deux bornes
     */
    public static int randomValue(int borneInf, int borneSup) {
        Random random = new Random();
        return borneInf + random.nextInt(borneSup - borneInf);
    }

    /**
     * Vérifie si une position est occupée dans le donjon.
     *
     * @param aVerifier la position à vérifier
     * @return vrai si la position est occupée, faux sinon
     */
    public boolean existeAEmplacement(int[] aVerifier) {
        if (verifAEmplacement(aVerifier)) {
            return true;
        }

        for (int[] positionEquip : m_positionEquip.values()) {
            if (positionEquip[0] == aVerifier[0] && positionEquip[1] == aVerifier[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si une position est occupée dans le donjon en tenant compte des obstacles, entités et équipements.
     *
     * @param aVerifier la position à vérifier
     * @param obstacles les obstacles du donjon
     * @param posEntitee les positions des entités
     * @param posEquip les positions des équipements
     * @return vrai si la position est occupée, faux sinon
     */
    public boolean existeAEmplacement(int[] aVerifier, ArrayList<int[]> obstacles,
                                      Hashtable<Entitee, int[]> posEntitee,
                                      Hashtable<Equipement, int[]> posEquip) {
        for (int[] posObs : obstacles) {
            if (Arrays.equals(posObs, aVerifier)) {
                return true;
            }
        }

        if (posEntitee != null) {
            for (int[] posEnt : posEntitee.values()) {
                if (Arrays.equals(posEnt, aVerifier)) {
                    return true;
                }
            }
        }

        if (posEquip != null) {
            for (int[] posEq : posEquip.values()) {
                if (Arrays.equals(posEq, aVerifier)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Vérifie si une position est occupée par un obstacle ou une entité.
     *
     * @param aVerifier la position à vérifier
     * @return vrai si la position est occupée, faux sinon
     */
    public boolean verifAEmplacement(int[] aVerifier) {
        for (int[] mObstacle : m_obstacles) {
            if (mObstacle[0] == aVerifier[0] && mObstacle[1] == aVerifier[1]) {
                return true;
            }
        }

        for (int[] positionEntitee : m_positionEntitee.values()) {
            if (positionEntitee[0] == aVerifier[0] && positionEntitee[1] == aVerifier[1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtient la longueur de la carte du donjon.
     *
     * @return la longueur de la carte
     */
    public int getLongueur() {
        return m_tailleMap[0];
    }

    /**
     * Obtient la largeur de la carte du donjon.
     *
     * @return la largeur de la carte
     */
    public int getLargeur() {
        return m_tailleMap[1];
    }

    /**
     * Vérifie si tous les monstres du donjon sont vaincus.
     *
     * @return vrai si tous les monstres sont vaincus, faux sinon
     */
    public boolean estVaincu() {
        boolean termine = true;
        for (Entitee entitee : m_positionEntitee.keySet()) {
            if (entitee instanceof Monstre) {
                if (entitee.estVivant()) {
                    termine = false;
                }
            }
        }
        return termine;
    }

    /**
     * Obtient la position d'une entité dans le donjon.
     *
     * @param entitee l'entité dont on veut obtenir la position
     * @return la position de l'entité
     */
    public int[] getPosEntitee(Entitee entitee) {
        return m_positionEntitee.get(entitee);
    }

    /**
     * Déplace une entité à une nouvelle position dans le donjon.
     *
     * @param entitee l'entité à déplacer
     * @param position la nouvelle position de l'entité
     */
    public void deplacerEntitee(Entitee entitee, int[] position) {
        m_positionEntitee.replace(entitee, position);
    }

    /**
     * Obtient l'entité située à une position donnée dans le donjon.
     *
     * @param position la position à vérifier
     * @return l'entité située à la position donnée, ou null si aucune entité n'est présente
     */
    public Entitee getEntiteeAPos(int[] position) {
        for (Entitee entitee : m_positionEntitee.keySet()) {
            if (m_positionEntitee.get(entitee)[0] == position[0] && m_positionEntitee.get(entitee)[1] == position[1]) {
                return entitee;
            }
        }
        return null;
    }

    /**
     * Vérifie s'il y a un équipement à une position donnée dans le donjon.
     *
     * @param position la position à vérifier
     * @return vrai s'il y a un équipement à la position donnée, faux sinon
     */
    public boolean equipAPos(int[] position) {
        boolean equipAPos = false;
        for (Equipement equip : m_positionEquip.keySet()) {
            if (m_positionEquip.get(equip)[0] == position[0] && m_positionEquip.get(equip)[1] == position[1]) {
                equipAPos = true;
            }
        }
        return equipAPos;
    }

    /**
     * Obtient l'équipement situé à une position donnée dans le donjon.
     *
     * @param position la position à vérifier
     * @return l'équipement situé à la position donnée, ou null si aucun équipement n'est présent
     */
    public Equipement getEquipAPos(int[] position) {
        for (Equipement equip : m_positionEquip.keySet()) {
            if (m_positionEquip.get(equip)[0] == position[0] && m_positionEquip.get(equip)[1] == position[1]) {
                return equip;
            }
        }
        return null;
    }

    /**
     * Supprime un équipement du donjon.
     *
     * @param equip l'équipement à supprimer
     */
    public void supprEquip(Equipement equip) {
        m_positionEquip.remove(equip);
    }

    /**
     * Supprime une entité du donjon.
     *
     * @param e l'entité à supprimer
     */
    public void supprEntite(Entitee e) {
        m_positionEntitee.remove(e);
    }

    /**
     * Lance l'initiative pour déterminer l'ordre des entités dans le donjon.
     *
     * @return la liste des entités triées par ordre d'initiative
     */
    public ArrayList<Entitee> lancerInitiative() {
        ArrayList<Entitee> listeEntitee = new ArrayList<>();
        ArrayList<Integer> listeInitiative = new ArrayList<>();
        for (Entitee entitee : m_positionEntitee.keySet()) {
            int initiative = entitee.lancerInitiative();
            listeEntitee.add(entitee);
            listeInitiative.add(initiative);
        }

        triEntitees(listeEntitee, listeInitiative);
        return listeEntitee;
    }

    /**
     * Trie les entités en fonction de leur initiative.
     *
     * @param entitees la liste des entités à trier
     * @param initiativeEntitees la liste des initiatives des entités
     */
    public void triEntitees(ArrayList<Entitee> entitees, ArrayList<Integer> initiativeEntitees) {
        for (int i = 0; i <= entitees.size() - 2; i++) {
            for (int j = i + 1; j <= entitees.size() - 1; j++) {
                if (initiativeEntitees.get(i) < initiativeEntitees.get(j)) {
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

    /**
     * Obtient la liste des obstacles du donjon.
     *
     * @return la liste des obstacles
     */
    public ArrayList<int[]> getObstacle() {
        return m_obstacles;
    }

    /**
     * Obtient les positions des entités dans le donjon.
     *
     * @return les positions des entités
     */
    public Hashtable<Entitee, int[]> getPositionEntitee() {
        return m_positionEntitee;
    }

    /**
     * Obtient les positions des équipements dans le donjon.
     *
     * @return les positions des équipements
     */
    public Hashtable<Equipement, int[]> getPositionEquipement() {
        return m_positionEquip;
    }

    /**
     * Ajoute un obstacle à une position donnée dans le donjon.
     *
     * @param position la position de l'obstacle à ajouter
     * @return vrai si l'obstacle a été ajouté avec succès, faux sinon
     */
    public boolean ajouterObstacle(int[] position) {
        if (!existeAEmplacement(position)) {
            m_obstacles.add(position);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Échange les positions de deux entités dans le donjon.
     *
     * @param e1 la première entité
     * @param e2 la deuxième entité
     */
    public void echangePosEntite(Entitee e1, Entitee e2) {
        int[] pos1 = m_positionEntitee.get(e1);
        int[] pos2 = m_positionEntitee.get(e2);

        deplacerEntitee(e1, pos2);
        deplacerEntitee(e2, pos1);
    }

    /**
     * Obtient la liste des entités dans le donjon.
     *
     * @return la liste des entités
     */
    public ArrayList<Entitee> getListeEntite() {
        return new ArrayList<>(m_positionEntitee.keySet());
    }

    /**
     * Obtient la liste des personnages dans le donjon.
     *
     * @return la liste des personnages
     */
    public ArrayList<Entitee> getListePersonnage() {
        ArrayList<Entitee> p = new ArrayList<>();
        for (Entitee e : m_positionEntitee.keySet()) {
            if (e.getType() == TypeEntitee.PERSONNAGE) {
                p.add(e);
            }
        }
        return p;
    }
}
