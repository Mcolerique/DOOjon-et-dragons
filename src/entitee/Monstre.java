package entitee;
import affichage.Affichage;
import equipement.arme.Arme;

public class Monstre extends Entitee{
    private String m_espece;
    private int m_numMonstre;
    public Monstre()
    {
        super();
        m_numMonstre = 0;
        m_espece = "missigno";
    }
}
