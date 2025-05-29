package entitee.personnage.sort;

import donjon.Donjon;
import entitee.Entitee;

import java.util.ArrayList;

public abstract class Sort {
    private final String m_nom;
    private final String m_description;
    public Sort(String n, String desc)
    {
        m_nom = n;
        m_description = desc;
    }
    public abstract boolean utiliserSort(ArrayList<Entitee> listEntite);
    public String getNom()
    {
        return m_nom;
    }
}
