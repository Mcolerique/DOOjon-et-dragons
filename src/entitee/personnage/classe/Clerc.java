package entitee.personnage.classe;

import entitee.personnage.sort.Guerison;
import entitee.personnage.sort.Sort;
import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

public class Clerc extends Classe{
    private static final int m_pv = 16;
    private static final Sort[] m_sort = new Sort[] {new Guerison()};
    private final Equipement[] m_equipementBase;
    public Clerc()
    {
        m_equipementBase = new Equipement[] {new Armure("Armure d'écaille", 9, Poids.LEGERE), new Arme("Masse d'armes", 1, 6, TypeCaC.COURANTE), new Arme("Arbalète légère", 1, 8, 16)};
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
        return "Clerc";
    }

    @Override
    public Sort[] getSort() {
        return m_sort;
    }
}
