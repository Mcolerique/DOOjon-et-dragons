package entitee;

import equipement.arme.Arme;
import equipement.armure.Armure;
import des.Des;
import interactionUtilisateur.Affichage;

/**
 * La classe Entitee représente une entité de base dans le jeu.
 * Elle est conçue pour être étendue par des classes spécifiques d'entités.
 */
public abstract class Entitee {
    protected int[] m_stats = new int[5];
    protected Arme m_arme;
    protected Armure m_armure;
    protected int m_pvActuelle;
    protected TypeEntitee m_type;

    /**
     * Construit une entité avec des statistiques par défaut.
     */
    public Entitee() {
        for (int i = 0; i < 5; i++) {
            m_stats[i] = 10;
        }
        m_pvActuelle = m_stats[0];
    }

    /**
     * Construit une entité avec des statistiques spécifiées.
     *
     * @param s les statistiques de l'entité
     */
    public Entitee(int[] s) {
        m_stats = s;
        m_pvActuelle = m_stats[0];
    }

    /**
     * Détermine si l'entité est touchée par une attaque.
     *
     * @param jetAttaque le jet d'attaque
     * @return vrai si l'entité est touchée, faux sinon
     */
    public boolean seFaireAttaquer(int jetAttaque) {
        if (m_armure != null) {
            return m_armure.getCA() <= jetAttaque;
        }
        return true;
    }

    /**
     * Applique des dégâts à l'entité.
     *
     * @param degats les dégâts à appliquer
     */
    public void sePrendreDegats(int degats) {
        m_pvActuelle -= degats;
    }

    /**
     * Détermine si l'entité peut se déplacer d'une certaine distance.
     *
     * @param distance la distance à parcourir
     * @return vrai si l'entité peut se déplacer, faux sinon
     */
    public boolean seDeplacer(int distance) {
        return distance <= m_stats[3];
    }

    /**
     * Fait attaquer l'entité sur une autre entité.
     *
     * @param ennemie l'entité à attaquer
     */
    public void attaquer(Entitee ennemie) {
        if (m_arme != null) {
            int jetAttaque = Des.lancerDes(20) + this.m_stats[m_arme.quelleStat()] + m_arme.getBonusAttaque();
            Affichage.affiche("Vous avez fait un " + jetAttaque + " à votre jet d'attaque");
            if (ennemie.seFaireAttaquer(jetAttaque)) {
                Affichage.affiche("\u001B[32mVotre attaque touche\u001B[0m");
                ennemie.sePrendreDegats(m_arme.infligerDegats());
            } else {
                Affichage.affiche("\u001B[31mVotre attaque rate\u001B[0m");
            }
        } else {
            Affichage.affiche("\u001B[31mVous n'avez pas d'arme équipée.\u001B[0m");
        }
    }

    /**
     * Obtient la portée de l'arme de l'entité.
     *
     * @return la portée de l'arme
     */
    public int getPorteeArme() {
        if (m_arme == null) {
            return 0;
        }
        return m_arme.getPortee();
    }

    /**
     * Vérifie si l'entité est vivante.
     *
     * @return vrai si l'entité est vivante, faux sinon
     */
    public boolean estVivant() {
        return m_pvActuelle > 0;
    }

    /**
     * Lance un jet d'initiative pour l'entité.
     *
     * @return le résultat du jet d'initiative
     */
    public int lancerInitiative() {
        return Des.lancerDes(20) + m_stats[4];
    }

    /**
     * Soigne l'entité d'un certain nombre de points de vie.
     *
     * @param soin le nombre de points de vie à soigner
     */
    public void soin(int soin) {
        m_pvActuelle += soin;
        if (m_pvActuelle > m_stats[0]) {
            m_pvActuelle = m_stats[0];
        }
    }

    /**
     * Obtient la force de l'entité.
     *
     * @return la force de l'entité
     */
    public int getForce() {
        return m_stats[1];
    }

    /**
     * Obtient la dextérité de l'entité.
     *
     * @return la dextérité de l'entité
     */
    public int getDexterite() {
        return m_stats[2];
    }

    /**
     * Obtient la vitesse de l'entité.
     *
     * @return la vitesse de l'entité
     */
    public int getVitesse() {
        return m_stats[3];
    }

    /**
     * Obtient les points de vie maximum de l'entité.
     *
     * @return les points de vie maximum de l'entité
     */
    public int getPv() {
        return m_stats[0];
    }

    /**
     * Obtient les points de vie actuels de l'entité.
     *
     * @return les points de vie actuels de l'entité
     */
    public int getPvActuelle() {
        return m_pvActuelle;
    }

    /**
     * Obtient le nom de l'armure de l'entité.
     *
     * @return le nom de l'armure de l'entité
     */
    public String getNomArmure() {
        return m_armure.getNom();
    }

    /**
     * Obtient l'arme de l'entité.
     *
     * @return l'arme de l'entité
     */
    public Arme getArme() {
        return m_arme;
    }

    /**
     * Obtient l'armure de l'entité.
     *
     * @return l'armure de l'entité
     */
    public Armure getArmure() {
        return m_armure;
    }

    /**
     * Obtient le type de l'entité.
     *
     * @return le type de l'entité
     */
    public TypeEntitee getType() {
        return m_type;
    }

    /**
     * Obtient le nom de l'entité.
     *
     * @return le nom de l'entité
     */
    public abstract String getNom();

    /**
     * Obtient la description de l'entité.
     *
     * @return la description de l'entité
     */
    public abstract String getDescription();
}
