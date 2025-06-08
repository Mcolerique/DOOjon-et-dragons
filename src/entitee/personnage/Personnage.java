package entitee.personnage;

import des.Des;
import entitee.Entitee;
import entitee.TypeEntitee;
import entitee.personnage.classe.*;
import entitee.personnage.race.*;
import entitee.personnage.sort.Sort;
import equipement.Equipement;
import equipement.TypeEquipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import interactionUtilisateur.Affichage;
import interactionUtilisateur.Scanner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * La classe Personnage représente un personnage jouable dans le jeu.
 * Elle hérite de la classe Entitee.
 */
public class Personnage extends Entitee {
    private final String m_nom;
    private final Race m_race;
    private final Classe m_classe;
    private ArrayList<Sort> m_sort;
    private ArrayList<Equipement> m_inventaire;

    /**
     * Construit un personnage par défaut.
     */
    public Personnage() {
        super();
        m_nom = "Saral Porcattache";
        m_race = new Halfelin();
        m_classe = new Guerrier();
        m_type = TypeEntitee.PERSONNAGE;
        setPerso();
    }

    /**
     * Construit un personnage avec un nom, une race et une classe spécifiés.
     *
     * @param nom le nom du personnage
     * @param r la race du personnage
     * @param c la classe du personnage
     */
    public Personnage(String nom, Race r, Classe c) {
        m_nom = nom;
        m_race = r;
        m_classe = c;
        m_type = TypeEntitee.PERSONNAGE;
        setPerso();
    }

    /**
     * Initialise les statistiques et l'inventaire du personnage.
     */
    public void setPerso() {
        m_stats[0] = m_classe.getPV();
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                m_stats[i] += Des.lancerDes(4);
            }
            m_stats[i] += 3;
        }
        for (int i = 0; i < 5; i++) {
            m_stats[i] += m_race.getStats()[i];
        }
        m_inventaire = new ArrayList<>();
        m_inventaire.addAll(Arrays.asList(m_classe.getEquipement()));
        if (m_classe.getSort() != null) {
            m_sort = new ArrayList<>();
            m_sort.addAll(Arrays.asList(m_classe.getSort()));
        } else {
            m_sort = null;
        }
        m_pvActuelle = m_stats[0];
    }

    /**
     * Équipe le personnage avec un équipement de l'inventaire.
     *
     * @param e l'index de l'équipement dans l'inventaire
     */
    public void equiper(int e) {
        TypeEquipement t = m_inventaire.get(e).getType();
        switch (t) {
            case ARME:
                Arme armeAEquiper = (Arme) m_inventaire.get(e);
                if (m_arme != null) {
                    for (int i = 0; i < 5; i++) {
                        m_stats[i] += m_arme.getModifStat()[i];
                    }
                    m_inventaire.add(m_arme);
                }
                m_arme = armeAEquiper;
                m_inventaire.remove(armeAEquiper);
                for (int i = 0; i < 5; i++) {
                    m_stats[i] -= m_arme.getModifStat()[i];
                }
                break;
            case ARMURE:
                Armure aEquiper = (Armure) m_inventaire.get(e);
                if (m_armure != null) {
                    for (int i = 0; i < 5; i++) {
                        m_stats[i] += m_armure.getModifStat()[i];
                    }
                    m_inventaire.add(m_armure);
                }
                m_armure = aEquiper;
                m_inventaire.remove(aEquiper);
                for (int i = 0; i < 5; i++) {
                    m_stats[i] -= m_armure.getModifStat()[i];
                }
                break;
            default:
                Affichage.affiche("Erreur");
                break;
        }
    }

    /**
     * Permet au joueur de choisir une arme dans l'inventaire.
     */
    public void choisirArme() {
        ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Equipement> arme = new ArrayList<>();
        for (int i = 0; i < m_inventaire.size(); i++) {
            if (m_inventaire.get(i).getType() == TypeEquipement.ARME) {
                arme.add(m_inventaire.get(i));
                index.add(i);
            }
        }

        if (arme.isEmpty()) {
            Affichage.affiche("Aucune arme disponible dans l'inventaire.");
            return;
        }

        Affichage.listeEquipement(arme);

        try {
            int choix = Scanner.demandeInt() - 1;
            if (choix == -1) {
                return;
            } else if (choix < -1 || choix >= index.size()) {
                Affichage.affiche("Choix invalide. Aucun équipement sélectionné.");
                return;
            }
            equiper(index.get(choix));
        } catch (Exception e) {
            Affichage.affiche("Erreur de saisie. Veuillez entrer un entier valide.");
        }
    }

    /**
     * Permet au joueur de choisir une armure dans l'inventaire.
     */
    public void choisirArmure() {
        ArrayList<Integer> index = new ArrayList<>();
        ArrayList<Equipement> armure = new ArrayList<>();
        for (int i = 0; i < m_inventaire.size(); i++) {
            if (m_inventaire.get(i).getType() == TypeEquipement.ARMURE) {
                armure.add(m_inventaire.get(i));
                index.add(i);
            }
        }

        if (armure.isEmpty()) {
            Affichage.affiche("Aucune armure disponible dans l'inventaire.");
            return;
        }

        Affichage.listeEquipement(armure);

        try {
            int choix = Scanner.demandeInt() - 1;
            if (choix == -1) {
                return;
            } else if (choix < -1 || choix >= index.size()) {
                Affichage.affiche("Choix invalide. Aucun équipement sélectionné.");
                return;
            }
            equiper(index.get(choix));
        } catch (Exception e) {
            Affichage.affiche("Erreur de saisie. Veuillez entrer un entier valide.");
        }
    }

    /**
     * Permet au personnage de ramasser un objet et de l'ajouter à son inventaire.
     *
     * @param objet l'objet à ramasser
     */
    public void ramasserObjet(Equipement objet) {
        m_inventaire.add(objet);
    }

    /**
     * Obtient la taille de l'inventaire du personnage.
     *
     * @return la taille de l'inventaire
     */
    public int getTailleInventaire() {
        return m_inventaire.size();
    }

    /**
     * Crée un personnage en fonction des choix de l'utilisateur.
     *
     * @return le personnage créé
     */
    public static Personnage creePersonnage() {
        Race[] raceDispo = {new Humain(), new Halfelin(), new Elfe(), new Nain()};
        Classe[] classeDispo = {new Guerrier(), new Magicien(), new Clerc(), new Roublard()};

        Affichage.affiche("Nom du personnage : ");
        String nom = Scanner.demandeString();

        Race r;
        Classe c;

        try {
            Affichage.selectionTableau(raceDispo);
            int choixRace = Scanner.demandeInt() - 1;
            if (choixRace < 0 || choixRace >= raceDispo.length) {
                Affichage.affiche("Choix de race invalide. Race par défaut (Humain) sélectionnée.");
                r = new Humain();
            } else {
                r = raceDispo[choixRace];
            }

            Affichage.selectionTableau(classeDispo);
            int choixClasse = Scanner.demandeInt() - 1;
            if (choixClasse < 0 || choixClasse >= classeDispo.length) {
                Affichage.affiche("Choix de classe invalide. Classe par défaut (Guerrier) sélectionnée.");
                c = new Guerrier();
            } else {
                c = classeDispo[choixClasse];
            }

        } catch (Exception e) {
            Affichage.affiche("Erreur de saisie. Personnage par défaut (Humain / Guerrier) utilisé.");
            r = new Humain();
            c = new Guerrier();
        }

        return new Personnage(nom, r, c);
    }

    /**
     * Obtient l'inventaire du personnage.
     *
     * @return l'inventaire du personnage
     */
    public ArrayList<Equipement> getInventaire() {
        return m_inventaire;
    }

    /**
     * Obtient un équipement spécifique de l'inventaire du personnage.
     *
     * @param i l'index de l'équipement dans l'inventaire
     * @return l'équipement à l'index spécifié
     */
    public Equipement getInventaire(int i) {
        return m_inventaire.get(i);
    }

    /**
     * Obtient les sorts du personnage.
     *
     * @return les sorts du personnage
     */
    public ArrayList<Sort> getSort() {
        return m_sort;
    }

    /**
     * Obtient un sort spécifique du personnage.
     *
     * @param i l'index du sort
     * @return le sort à l'index spécifié
     */
    public Sort getSort(int i) {
        return m_sort.get(i);
    }

    /**
     * Obtient la taille de la liste des sorts du personnage.
     *
     * @return la taille de la liste des sorts
     */
    public int getTailleSort() {
        return m_sort.size();
    }

    /**
     * Obtient le nom du personnage.
     *
     * @return le nom du personnage
     */
    @Override
    public String getNom() {
        return m_nom;
    }

    /**
     * Obtient la description du personnage.
     *
     * @return la description du personnage
     */
    @Override
    public String getDescription() {
        return m_race.toString() + " " + m_classe.toString();
    }

    /**
     * Obtient les initiales du nom du personnage.
     *
     * @return les initiales du nom du personnage
     */
    public String getInitiale() {
        if (m_nom.length() < 3) return m_nom;
        else return m_nom.substring(0, 3);
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du personnage.
     *
     * @return le nom du personnage
     */
    @Override
    public String toString() {
        return m_nom;
    }
}
