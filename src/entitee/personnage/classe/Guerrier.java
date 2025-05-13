package entitee.personnage.classe;

import equipement.Equipement;
import equipement.arme.Arme;
import equipement.arme.TypeCaC;
import equipement.armure.Armure;
import equipement.armure.Poids;

public class Guerrier extends Classe{
    private static final int m_pv = 20;
    private static final Equipement[] m_equipementBase = new Equipement[] {new Armure("Cotte de mailles", 11, Poids.LOURDE), new Arme("Epée longue", 1, 8, TypeCaC.GUERRE), new Arme("Arbalète légère", 1, 8, 16)};
    public Guerrier()
    {
    }

    @Override
    public int getPV() {
        return m_pv;
    }
    public Equipement[] equipementBase()
    {
        return m_equipementBase;
    }
}
