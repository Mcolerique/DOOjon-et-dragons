package entitee.personnage.classe;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

public class Clerc extends Classe{
    private static final int m_pv = 16;
    private static final Equipement[] m_equipementBase = new Equipement[] {new Armure("Armure d'écaille", 9, Poids.LEGERE), new Arme("Masse d'armes", 1, 6, TypeCaC.COURANTE), new Arme("Arbalète légère", 1, 8, 16)};
    public Clerc()
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
