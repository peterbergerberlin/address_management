package adressverwaltung;

/**
 * Ein Interface welches von den Klassen Kunde und Lieferant implementiert wird
 * 
 * @author PeterB
 */
public interface Adressant {
    public abstract String getId();
    public abstract String getType();
    public abstract String getVorname();
    public abstract String getNachname();
    public abstract String getStrasse();
    public abstract String getPlz();
    public abstract String getOrt();
    public abstract String getEmail();
    
    public abstract void setId(String id);
    public abstract void setType(String type);
    public abstract void setVorname(String vorname);
    public abstract void setNachname(String nachname);
    public abstract void setStrasse(String strasse);
    public abstract void setPlz(String plz);
    public abstract void setOrt(String ort);
    public abstract void setEmail(String email);
    
    @Override
    public abstract String toString();
}
