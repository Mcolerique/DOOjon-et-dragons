package entitee.personnage.classe;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

public class Roublard extends Classe{
    private static final int m_pv = 16;
    private static  final Equipement[] m_equipementBase = new Equipement[] {new Arme("Rapière", 1, 8, TypeCaC.GUERRE), new Arme("Arc court", 1, 6, 16)};
    public Roublard()
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
