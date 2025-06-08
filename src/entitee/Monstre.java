package entitee;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;
import equipement.armure.Poids;
import interactionUtilisateur.*;

import java.util.ArrayList;
import java.lang.*;

public class Monstre extends Entitee{
    private String m_espece;
    private int m_numMonstre;
    public Monstre()
    {
        super();
        m_numMonstre = 0;
        m_espece = "MissingNo."; //pas mal la ref Favien
        m_type = TypeEntitee.MONSTRE;
    }
    public Monstre(int numMonstre, String espece, int[] stats, Equipement[] attaqueEtArmure)
    {
        super(stats);
        m_numMonstre = numMonstre;
        m_espece = espece;
        m_arme = (Arme)attaqueEtArmure[0];
        m_armure = (Armure)attaqueEtArmure[1];
        m_type = TypeEntitee.MONSTRE;
    }
    public String getAppellation(){
        return (m_espece + m_numMonstre);
    }
    public String getSymbole()
    {
        return m_espece.substring(0,2) + m_numMonstre;
    }
    public String getNom()
    {
        return m_espece;
    }
    public String getDescription()
    {
        return ""+m_numMonstre;
    }
    public String toString() {return m_espece+" n°"+m_numMonstre;}
    public static ArrayList<Monstre> creerMonstres(){
        Affichage.affiche("Combien de monstres souhaitez vous créer ?");
        int nbrMonstres = Scanner.demandeInt();
        if(nbrMonstres <= 0){Affichage.affiche("Arrêt de la création de monstre. Passage aux monstres par défaut."); return null;}
        ArrayList<Monstre> monstres = new ArrayList<>();
        for(int i = 0; i < nbrMonstres; i++){
            Affichage.affiche("Monstre n°"+(i+1));
            Monstre monstre = creerMonstre();
            if(!monstres.isEmpty()){monstre.m_numMonstre = compteNumMonstre(monstres , monstre);}
            monstres.add(monstre);
        }
        return monstres;
    }
    public static Monstre creerMonstre(){
        Monstre monstre = new Monstre();
        Affichage.affiche("Nom d'espèce de votre monstre ?");
        monstre.m_espece = Scanner.demandeString();
        Affichage.affiche("Points de vie ?");
        monstre.m_stats[0] = Scanner.demandeInt();
        Affichage.affiche("Vitesse ?");
        monstre.m_stats[1] = Scanner.demandeInt();
        Affichage.affiche("Force ?");
        monstre.m_stats[2] = Scanner.demandeInt();
        Affichage.affiche("Dextérité ?");
        monstre.m_stats[3] = Scanner.demandeInt();
        Affichage.affiche("Points de vie ?");
        monstre.m_stats[4] = Scanner.demandeInt();
        Equipement atqArmure[] = creerAtqDefMonstre(monstre.m_espece);
        monstre.m_arme = (Arme)atqArmure[0];
        monstre.m_armure = (Armure)atqArmure[1];

        return monstre;
    }
    public static Equipement[] creerAtqDefMonstre(String nomEspece){
        Equipement[] atqDefMonstre = new Equipement[2];
        int portee;
        do {
            Affichage.affiche("Portée de l'attaque (si 1, corps à corps. si +, à distance). Doit être supérieur à 0");
            portee = Scanner.demandeInt();
        }while(portee<1);

        String dgtsFaceAtq[] = new String[2];
        String tempDgtsFaceAtq;
        do {
            Affichage.affiche("Indiquez le lancer de dé attendu (au format xdy)");
            tempDgtsFaceAtq = Scanner.demandeString();
            dgtsFaceAtq = tempDgtsFaceAtq.split("d");
            if(!tempDgtsFaceAtq.contains("d")) Affichage.affiche("Veuillez respecter le format  xdy");
        }while(!tempDgtsFaceAtq.contains("d"));

        atqDefMonstre[0] = new Arme("attaque"+nomEspece , Integer.valueOf(dgtsFaceAtq[0]).intValue(),
                                                        Integer.valueOf(dgtsFaceAtq[1]).intValue(), portee);

        Affichage.affiche("Classe d'armure de votre monstre");
        int cArmure = Scanner.demandeInt();

        atqDefMonstre[1] = new Armure("classeArume"+nomEspece , cArmure, Poids.LEGERE);

        return atqDefMonstre;

    }
    public static int compteNumMonstre(ArrayList<Monstre> listeMonstres, Monstre monstre){
        int compteNumMonstre = -1;
        for(Monstre m : listeMonstres){
            if(m.m_espece == monstre.m_espece){compteNumMonstre++;}
        }
        return compteNumMonstre;
    }
    public static Monstre utiliserMonstre(int monstre, ArrayList<Monstre> monstres){
        //System.out.println("Quel monstre voulez-vous placer ?");
        //int numMonstre = Scanner.demandeInt();
        Monstre returnMonstre = monstres.get(monstre);
        monstres.remove(monstre);
        System.out.println("Monstre ajouté.\n");
        return returnMonstre;
    }
}
