package entitee.personnage.classe;


import entitee.personnage.sort.*;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;

public class Magicien extends Classe{
    private static final int m_pv = 12;
    private static final Sort[] m_sort = new Sort[] {new Guerison(), new BoogieWoogie(), new ArmeMagique()};
    private final Equipement[] m_equipementBase;
    public Magicien()
    {
       m_equipementBase = new Equipement[] {new Arme("Bâton", 1, 6, TypeCaC.COURANTE), new Arme("Fronde", 1, 4, 6)};
    }

    @Override
    public int getPV() {
        return m_pv;
    }
    public Equipement[] getEquipement()
    {
        return m_equipementBase;
    }

    @Override
    public String toString() {
        return "Magicien";
    }
    @Override
    public Sort[] getSort() {
        return m_sort;
    }
}
