package equipement.arme;
import des.Des;
import equipement.Equipement;

public class Arme extends Equipement {
    private int m_nbDesDeg;
    private int m_degats;
    private int m_portee;
    private TypeCaC m_type;

    public Arme() {
        super("nomArme");
        m_nbDesDeg = 1;
        m_degats = 1;
        m_portee = 1;
        m_type = TypeCaC.COURANTE;
    }

    public Arme(String nom, int nbDes, int nbFaces, int portee){
        super(nom);
        m_nbDesDeg = nbDes;
        m_degats = nbFaces;
        m_portee = portee;
        m_type = null;
    }

    public Arme(String nom, int nbDes, int nbFaces, TypeCaC type){
        super(nom);
        m_nbDesDeg = nbDes;
        m_degats = nbFaces;
        m_portee = 1;
        m_type = type;
        if(type == TypeCaC.GUERRE){
            m_modifStat[3] = -2;
            m_modifStat[1] = 4;
        }
    }

    public int getPortee(){
        return m_portee;
    }


    public int infligerDegats() {
        int degats = 0;
        for(int i = 0; i<m_nbDesDeg;i++){
            degats += Des.lancerDes(m_degats);
        }
        return degats;
    }

    public int quelleStat(){
        return this.m_portee>1 ? 2 : 1;
        //Si portée sup à 1 donc arme à distance alors la statistique qui importe
        //c'est la dextérité, si c'est CàC alors ce serait force
    }
}
