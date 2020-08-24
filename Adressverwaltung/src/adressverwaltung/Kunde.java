package adressverwaltung;

/**
 * Eine Klasse welche Kundenobjekte instanziiert
 * 
 * @author PeterB
 */
public class Kunde implements Adressant{
    private String id,type,vorname,nachname,strasse,plz,ort,email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }
    
    @Override
    public String getVorname() {
        return vorname;
    }
    @Override
    public void setVorname(String vorname) {
        this.vorname = vorname;
    }
    @Override
    public String getNachname() {
        return nachname;
    }
    @Override
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    @Override
    public String getStrasse() {
        return strasse;
    }
    @Override
    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }
    @Override
    public String getPlz() {
        return plz;
    }
    @Override
    public void setPlz(String plz) {
        this.plz = plz;
    }
    @Override
    public String getOrt() {
        return ort;
    }
    @Override
    public void setOrt(String ort) {
        this.ort = ort;
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public Kunde(String id, String type, String vorname, String nachname, String strasse, String plz, String ort, String email) {
        this.id = id;
        this.type = type;
        this.vorname = vorname;
        this.nachname = nachname;
        this.strasse = strasse;
        this.plz = plz;
        this.ort = ort;
        this.email = email;
    }
    
    public Kunde(){};
    
    @Override
    public String toString(){
        return vorname + " " + nachname;
        //return id + " " + type + " " + vorname + " " + nachname + " " + strasse + " " + plz + " " + ort + " " + email;
    }
}
