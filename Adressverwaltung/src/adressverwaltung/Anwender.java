package adressverwaltung;

/**
 * Eine Klasse welche Anwenderobjekte instanziiert
 * 
 * @author PBerger
 */
public class Anwender {
    private String username;
    private String pwHash;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    public Anwender(String username, String pwHash) {
        this.username = username;
        this.pwHash = pwHash;
    }
    
    public Anwender() {
    }
    
    @Override
    public String toString() {
        return username + " " + pwHash + "\n";
    }
}
