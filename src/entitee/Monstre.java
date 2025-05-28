package entitee;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.armure.Armure;

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
        super(stats, attaqueEtArmure);
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
    public  String getDescription()
    {
        return ""+m_numMonstre;
    }
}
