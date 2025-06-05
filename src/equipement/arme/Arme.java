package equipement.arme;
import des.Des;
import equipement.Equipement;
import equipement.TypeEquipement;
import interactionUtilisateur.Affichage;

public class Arme extends Equipement {
    private final int m_nbDesDeg;
    private final int m_degats;
    private final int m_portee;
    private int m_bonusDegats;
    private int m_bonusAttaque;
    private final TypeCaC m_type;

    public Arme() {
        super("poing");
        m_nbDesDeg = 1;
        m_degats = 1;
        m_portee = 1;
        m_bonusAttaque = 1;
        m_bonusDegats = 1;
        m_type = TypeCaC.COURANTE;
        m_typeEquipement = TypeEquipement.ARME;
    }

    public Arme(String nom, int nbDes, int nbFaces, int portee){
        super(nom);
        m_nbDesDeg = nbDes;
        m_degats = nbFaces;
        m_portee = portee;
        m_bonusAttaque = 0;
        m_bonusDegats = 0;
        m_type = null;
        m_typeEquipement = TypeEquipement.ARME;
    }

    public Arme(String nom, int nbDes, int nbFaces, TypeCaC type){
        super(nom);
        m_nbDesDeg = nbDes;
        m_degats = nbFaces;
        m_portee = 1;
        m_bonusAttaque = 0;
        m_bonusDegats = 0;
        m_type = type;
        m_typeEquipement = TypeEquipement.ARME;
        if(m_type == TypeCaC.GUERRE){
            m_modifStat[3] = -2;
            m_modifStat[1] = 4;
        }
    }

    public int getPortee(){
        return m_portee;
    }
    public int infligerDegats() {
        int degats = 0;
        int degatsTotal = 0;
        String txt = "(";
        for(int i = 0; i<m_nbDesDeg;i++){
            degats = Des.lancerDes(m_degats);
            degatsTotal += degats;
            txt += degats+"+";
        }
        txt = degatsTotal + txt + +m_bonusDegats + ")";
        Affichage.affiche("Vous avez infliger "+txt+" dégâts");
        return degats+m_bonusDegats;
    }
    public void boost(int bonus)
    {
        m_bonusAttaque += bonus;
        m_bonusDegats += bonus;
    }
    public String getDegats()
    {
        return m_nbDesDeg+"d"+m_degats;
    }
    public int quelleStat(){
        return this.m_portee>1 ? 2 : 1;
        //Si portée sup à 1 donc arme à distance alors la statistique qui importe
        //c'est la dextérité, si c'est CàC alors ce serait force
    }
    public int getBonusAttaque()
    {
        return m_bonusAttaque;
    }
}
