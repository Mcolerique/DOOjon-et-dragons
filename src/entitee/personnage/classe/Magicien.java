package entitee.personnage.classe;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

public class Magicien extends Classe{
    private static final int m_pv = 12;
    private static final Equipement[] m_equipementBase = new Equipement[] {new Arme("Bâton", 1, 6, TypeCaC.COURANTE), new Arme("Fronde", 1, 4, 6)};
    public Magicien()
    {
    }

    @Override
    public int getPV() {
        return m_pv;
    }
    public Equipement[] getEquipement()
    {
        return m_equipementBase;
    }
}
