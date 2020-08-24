package adressverwaltung;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.DefaultListModel;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;


/**
 * Klasse zur Erzeugung der Grafischen Oberfläche 
 * @author PBerger
 */
public class GUI extends JFrame implements ActionListener {
    
    private static GUI instance;
    private final JFrame adressverwaltung;
    private JPanel login_jp, administration_jp, current_jp;
    private final File anwenderFile = new File("anwender.txt");
    private File klFile;
    
    private ArrayList<Anwender> anwenderListe;
        
// Login
    private JLabel lbl_login,lbl_userName,lbl_password,lbl_loginFail;
    private JTextField tf_userName;
    private JPasswordField tf_password;
    private JButton btn_login;
    
// Menue-Items
    private JMenuItem kunde_neu,kunde_s_l,lieferant_neu,lieferant_s_l,anwender_neu,ueber,farbe,klCsvNeuWaehlen;
    
// Willkommenscreen
    private JLabel lbl_willkommen;
    private Font willkommen;
    
// Kunde neu anlegen
    private JLabel lbl_kunde_vorname,lbl_kunde_nachname,lbl_kunde_strasse,lbl_kunde_plz,lbl_kunde_ort,lbl_kunde_email,addKundeDialogText,addKundeEmailDialogText;
    private JTextField tf_kunde_vorname,tf_kunde_nachname,tf_kunde_strasse,tf_kunde_plz,tf_kunde_ort,tf_kunde_email;
    private JList list_kunden;
    private JButton btn_kunden_hinzufügen, addKundeDialogButton,addKundeEmailDialogButton;
    private JDialog addKundeDialog,addKundeEmailDialog;
    
// Kunde suchen/löschen    
    private JButton btn_kunden_loeschen,btn_kunden_suchen,btn_alle_kunden_anzeigen,btn_kunde_anzeigen;
    private DefaultListModel<Kunde> listModel_kunde;
    private ArrayList<Kunde> kunden, foundKunden;
    private JLabel lbl_kunde_notfound = new JLabel();
    
// Lieferant neu anlegen
    private JLabel lbl_lieferant_firmenname,lbl_lieferant_vorname,lbl_lieferant_nachname,lbl_lieferant_strasse,lbl_lieferant_plz,lbl_lieferant_ort,lbl_lieferant_email,addLieferantDialogText,addLieferantEmailDialogText;
    private JTextField tf_lieferant_firmenname,tf_lieferant_vorname,tf_lieferant_nachname,tf_lieferant_strasse,tf_lieferant_plz,tf_lieferant_ort,tf_lieferant_email;
    private JList list_lieferanten;
    private JButton btn_lieferanten_hinzufügen,addLieferantDialogButton,addLieferantEmailDialogButton;
    private JDialog addLieferantDialog,addLieferantEmailDialog;
    
    
// Lieferant suchen/löschen    
    private JButton btn_lieferanten_loeschen,btn_lieferanten_suchen,btn_alle_lieferanten_anzeigen,btn_lieferant_anzeigen;
    private DefaultListModel<Lieferant> listModel_lieferant;
    private ArrayList<Lieferant> lieferanten, foundLieferanten;
    private JLabel lbl_lieferant_notfound = new JLabel();
    
// Anwender neu anlegen
    private JLabel lbl_anwender_username,lbl_anwender_password;
    private JTextField tf_anwender_username,tf_anwender_password;
    private JButton btn_anwender_hinzufügen;
    private int anwenderListeLength;
    
// FileChooser
   private JButton csvNeuAuswahl,cancelCsvAuswahl;
   private JDialog fcDialog;
   private JLabel fcDialogText1,fcDialogText2;
   private int csvSelect = -1;
  
// KL-Liste
   private ArrayList<Adressant> klListe;
   private int klListeLength;
   
// Extras
   private Color farbauswahl;
   
// Hilfe
   private JDialog ueberDialog;
   private JLabel lbl_ueberVersionText;
   private JLabel lbl_ueberAuthorText;
   
// CSV-Auswahl_Dialog
   private JDialog csvSelectDialog;
   private JLabel lbl_csvSelectDialogText1;
   private JLabel lbl_csvSelectDialogText2;
   private JButton csvNeuAuswahl2;
    

    private GUI() {
        adressverwaltung = new JFrame("Adressverwaltung");
        adressverwaltung.setSize(680, 450);
        adressverwaltung.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        adressverwaltung.add(getLoginContainer());
        adressverwaltung.addWindowListener(exitListener);
        adressverwaltung.setLocationRelativeTo(null);
        adressverwaltung.setVisible(true);
    }
    
// WindowListener um alles zu Speichern bevor das Fenster geschlossen wird    
    WindowListener exitListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {  
            int confirm = JOptionPane.showOptionDialog(
                 null, "Wollen Sie die Anwendung wirklich schließen?", 
                 "Abbrechen", JOptionPane.YES_NO_OPTION , 
                 JOptionPane.WARNING_MESSAGE, null, null, null);
            if (confirm == JOptionPane.YES_OPTION) {
                if(csvSelect == 0){
                    putAllFromArrayListInCsv();
                    putAllAnwenderInTextFile();
                }
                    System.exit(0);
            }
        }
    };
    
    /**
     * Prüfung, Objekt der Klasse GUI bereits existiert
     *
     * @return Einzelobjekt
     */
    public static GUI getInstance() {
        // Abfrage, ob eine Instanz existiert
        if (GUI.instance == null) {
            GUI.instance = new GUI();
        }
        return GUI.instance;
    }
    
// Menue_Methoden
    /**
     * Eine Methode welche die JMenus der JMenuBar hinzufügt
     * 
     * @return JMenuBar - gibt die Menüleiste zurück
     */
    public JMenuBar getMenuBar2(){
        JMenuBar bar = new JMenuBar();
        bar.add(getMenu_kunden());
        bar.add(getMenu_lieferanten());
        bar.add(getMenu_anwender());
        bar.add(getMenu_Extras());
        bar.add(getMenu_hilfe());
        return bar;
    }
    /**
     * Eine Methode welche das Menü "Kunden" erzeugt
     * 
     * @return JMenu - gibt das Menü "Kunden" zurück
     */
    public JMenu getMenu_kunden(){
        JMenu kunden = new JMenu("Kunden");
        kunde_neu = new JMenuItem("Neu anlegen");
        kunde_s_l = new JMenuItem("Suchen/Löschen");
        kunden.add(kunde_neu);
        kunden.add(kunde_s_l);
        kunde_neu.addActionListener(this);
        kunde_s_l.addActionListener(this);
        return kunden ;
    }
    /**
     * Eine Methode welche das Menü "Lieferanten" erzeugt
     * 
     * @return JMenu - gibt das Menü "Lieferanten" zurück
     */
    public JMenu getMenu_lieferanten(){
        JMenu lieferanten = new JMenu("Lieferanten");
        lieferant_neu = new JMenuItem("Neu anlegen");
        lieferant_s_l = new JMenuItem("Suchen/Löschen");
        lieferanten.add(lieferant_neu);
        lieferanten.add(lieferant_s_l);
        lieferant_neu.addActionListener(this);
        lieferant_s_l.addActionListener(this);
        return lieferanten ;
    }
    /**
     * Eine Methode welche das Menü "Mitarbeiter" erzeugt
     * 
     * @return JMenu - gibt das Menü "Mitarbeiter" zurück
     */
    public JMenu getMenu_anwender(){
        JMenu anwender = new JMenu("Mitarbeiter");
        anwender_neu = new JMenuItem("Neu anlegen");
        anwender.add(anwender_neu);
        anwender_neu.addActionListener(this);
        return anwender ;
    }
    /**
     * Eine Methode welche das Menü "Hilfe" erzeugt
     * 
     * @return JMenu - gibt das Menü "Hilfe" zurück
     */
    public JMenu getMenu_hilfe(){
        JMenu hilfe = new JMenu("Hilfe");
        ueber = new JMenuItem("Über");
        hilfe.add(ueber);
        ueber.addActionListener(this);
        return hilfe;
    }
    /**
     * Eine Methode welche das Menü "Extras" erzeugt
     * 
     * @return JMenu - gibt das Menü "Extras" zurück
     */
    public JMenu getMenu_Extras(){
        JMenu extras = new JMenu("Extras");
        farbe = new JMenuItem("Hintergrundfarbe ändern");
        extras.add(farbe);
        farbe.addActionListener(this);
        klCsvNeuWaehlen = new JMenuItem("Kunden/Lieferanten-CSV auswählen");
        extras.add(klCsvNeuWaehlen);
        klCsvNeuWaehlen.addActionListener(this);
        return extras;
    }
    
// JPanel-Container 
    /**
     * Eine Methode welche das Login-Jpanel erzeugt
     * 
     * @return JPanel - gibt das Login-JPanel zurück
     */
    private JPanel getLoginContainer() {
        login_jp = new JPanel();
        login_jp.setLayout(null);
        
        lbl_login = new JLabel("Login");
        lbl_userName = new JLabel("Anmeldename");
        lbl_password = new JLabel("Passwort");
        lbl_loginFail = new JLabel("");  
        lbl_loginFail.setForeground(Color.RED);
        lbl_login.setBounds(40, 40, 100, 30);
        lbl_userName.setBounds(40, 100, 90, 30);
        lbl_password.setBounds(310, 100, 60, 30);
        lbl_loginFail.setBounds(40, 150, 250, 30);
        login_jp.add(lbl_login);
        login_jp.add(lbl_userName);
        login_jp.add(lbl_password);
        login_jp.add(lbl_loginFail);
        
        tf_userName = new JTextField();
        tf_password = new JPasswordField();
        tf_userName.setBounds(140, 100, 150, 30);
        tf_password.setBounds(380, 100, 150, 30);
        login_jp.add(tf_userName);
        login_jp.add(tf_password);
        
        btn_login = new JButton("Anmelden");
        btn_login.setBounds(380, 150, 150, 30);
        login_jp.add(btn_login);
        btn_login.addActionListener(this);
        
        putAllUsersInArrayList();
                
        return login_jp;
    }
    /**
     * Eine Methode welche den WillkommenBildschirm erzeugt
     * 
     * @return JPanel - gibt das WillkommenBildschirm-JPanel zurück
     */
    private JPanel getAdministrationContainer_empty(){
        administration_jp = new JPanel();
        administration_jp.setLayout(null);
        if(farbauswahl != null){
            administration_jp.setBackground(farbauswahl);
        }    
        lbl_willkommen = new JLabel("Willkommen in der Adressverwaltung 3000");
        lbl_willkommen.setBounds(30, 30, 450, 60);
        willkommen = new Font("Arial", Font.BOLD + Font.ITALIC, 20);
        lbl_willkommen.setFont(willkommen);
        administration_jp.add(lbl_willkommen);
           
        return administration_jp;
    }
    
    /**
     * Eine Methode welche den Kunde-neu-anlegen-Bildschirm erzeugt
     * 
     * @return JPanel - gibt das Kunde-neu-anlegen-JPanel zurück
     */
    private JPanel getAdministrationContainer_kunde_neu(){
        administration_jp = new JPanel();
        administration_jp.setLayout(null);
        
        if(farbauswahl != null){
            administration_jp.setBackground(farbauswahl);
        }

        lbl_kunde_vorname = new JLabel("Vorname");
        lbl_kunde_nachname = new JLabel("Nachname");
        lbl_kunde_strasse = new JLabel("Strasse");
        lbl_kunde_plz = new JLabel("PLZ");
        lbl_kunde_ort = new JLabel("Ort");
        lbl_kunde_email = new JLabel("E-Mail");
        lbl_kunde_vorname.setBounds(20, 20, 120, 30);
        lbl_kunde_nachname.setBounds(20, 60, 120, 30);
        lbl_kunde_strasse.setBounds(20, 100, 120, 30);
        lbl_kunde_plz.setBounds(20, 140, 120, 30);
        lbl_kunde_ort.setBounds(20, 180, 120, 30);
        lbl_kunde_email.setBounds(20, 220, 120, 30);
        lbl_kunde_notfound.setBounds(20, 280, 290, 30);
        administration_jp.add(lbl_kunde_vorname);
        administration_jp.add(lbl_kunde_nachname);
        administration_jp.add(lbl_kunde_strasse);
        administration_jp.add(lbl_kunde_plz);
        administration_jp.add(lbl_kunde_ort);
        administration_jp.add(lbl_kunde_email);
        administration_jp.add(lbl_kunde_notfound);
        
        tf_kunde_vorname = new JTextField();
        tf_kunde_nachname = new JTextField();
        tf_kunde_strasse = new JTextField();
        tf_kunde_plz = new JTextField();
        tf_kunde_ort = new JTextField();
        tf_kunde_email = new JTextField();
        tf_kunde_vorname.setBounds(180, 20, 150, 30);
        tf_kunde_nachname.setBounds(180, 60, 150, 30);
        tf_kunde_strasse.setBounds(180, 100, 150, 30);
        tf_kunde_plz.setBounds(180, 140, 150, 30);
        tf_kunde_ort.setBounds(180, 180, 150, 30);
        tf_kunde_email.setBounds(180, 220, 150, 30);
        administration_jp.add(tf_kunde_vorname);
        administration_jp.add(tf_kunde_nachname);
        administration_jp.add(tf_kunde_strasse);
        administration_jp.add(tf_kunde_plz);
        administration_jp.add(tf_kunde_ort);
        administration_jp.add(tf_kunde_email);
        
        btn_kunden_hinzufügen = new JButton("Kunde hinzufügen");
        btn_kunden_hinzufügen.setBounds(20, 330, 150, 30);
        administration_jp.add(btn_kunden_hinzufügen);
        btn_kunden_hinzufügen.addActionListener(this);
             
        return administration_jp;
    }
    /**
     * Eine Methode welche den Kunde-suchen-löschen-Bildschirm erzeugt
     * 
     * @return JPanel - gibt das Kunde-suchen-löschen-JPanel zurück
     */
    private JPanel getAdministrationContainer_kunde_suchen_loeschen(){
        administration_jp = new JPanel();
        administration_jp.setLayout(null);
        kunden = new ArrayList<>();
        
        if(farbauswahl != null){
            administration_jp.setBackground(farbauswahl);
        }
        
        lbl_kunde_vorname = new JLabel("Vorname");
        lbl_kunde_nachname = new JLabel("Nachname");
        lbl_kunde_strasse = new JLabel("Strasse");
        lbl_kunde_plz = new JLabel("PLZ");
        lbl_kunde_ort = new JLabel("Ort");
        lbl_kunde_email = new JLabel("E-Mail");
        lbl_kunde_vorname.setBounds(20, 20, 120, 30);
        lbl_kunde_nachname.setBounds(20, 60, 120, 30);
        lbl_kunde_strasse.setBounds(20, 100, 120, 30);
        lbl_kunde_plz.setBounds(20, 140, 120, 30);
        lbl_kunde_ort.setBounds(20, 180, 120, 30);
        lbl_kunde_email.setBounds(20, 220, 120, 30);
        lbl_kunde_notfound.setBounds(20, 290, 290, 30);
        administration_jp.add(lbl_kunde_vorname);
        administration_jp.add(lbl_kunde_nachname);
        administration_jp.add(lbl_kunde_strasse);
        administration_jp.add(lbl_kunde_plz);
        administration_jp.add(lbl_kunde_ort);
        administration_jp.add(lbl_kunde_email);
        administration_jp.add(lbl_kunde_notfound);
        
        tf_kunde_vorname = new JTextField();
        tf_kunde_nachname = new JTextField();
        tf_kunde_strasse = new JTextField();
        tf_kunde_plz = new JTextField();
        tf_kunde_ort = new JTextField();
        tf_kunde_email = new JTextField();
        tf_kunde_vorname.setBounds(180, 20, 150, 30);
        tf_kunde_nachname.setBounds(180, 60, 150, 30);
        tf_kunde_strasse.setBounds(180, 100, 150, 30);
        tf_kunde_plz.setBounds(180, 140, 150, 30);
        tf_kunde_ort.setBounds(180, 180, 150, 30);
        tf_kunde_email.setBounds(180, 220, 150, 30);
        administration_jp.add(tf_kunde_vorname);
        administration_jp.add(tf_kunde_nachname);
        administration_jp.add(tf_kunde_strasse);
        administration_jp.add(tf_kunde_plz);
        administration_jp.add(tf_kunde_ort);
        administration_jp.add(tf_kunde_email);

        for (Adressant kunde : klListe) {
            if(kunde.getType().equals("0")){
                kunden.add((Kunde)kunde);
            }
        }
        
        listModel_kunde = new DefaultListModel<>();
        for (Kunde kunde : kunden) {
            listModel_kunde.addElement(kunde);
        }
        list_kunden = new JList(listModel_kunde);
        list_kunden.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list_kunden.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(list_kunden);
        listScroller.setBounds(350, 20, 290, 270);        
        administration_jp.add(listScroller);
        
        btn_kunden_loeschen = new JButton("Kunde löschen");
        btn_kunden_loeschen.setBounds(350, 330, 140, 30);
        administration_jp.add(btn_kunden_loeschen);
        btn_kunden_loeschen.addActionListener(this);
        
        btn_kunde_anzeigen = new JButton("Kunde anzeigen");
        btn_kunde_anzeigen.setBounds(500, 330, 140, 30);
        administration_jp.add(btn_kunde_anzeigen);
        btn_kunde_anzeigen.addActionListener(this);
        
        btn_kunden_suchen = new JButton("Kunde suchen");
        btn_kunden_suchen.setBounds(20, 330, 150, 30);
        administration_jp.add(btn_kunden_suchen);
        btn_kunden_suchen.addActionListener(this);
        
        btn_alle_kunden_anzeigen = new JButton("Alle anzeigen");
        btn_alle_kunden_anzeigen.setBounds(180, 330, 150, 30);
        administration_jp.add(btn_alle_kunden_anzeigen);
        btn_alle_kunden_anzeigen.addActionListener(this);
        
        return administration_jp;
    }
    /**
     * Eine Methode welche den Lieferant-neu-anlegen-Bildschirm erzeugt
     * 
     * @return JPanel - gibt das Lieferant-neu-anlegen-JPanel zurück
     */
    private JPanel getAdministrationContainer_lieferant_neu(){
        administration_jp = new JPanel();
        administration_jp.setLayout(null);
        
        if(farbauswahl != null){
            administration_jp.setBackground(farbauswahl);
        }
        
        lbl_lieferant_firmenname = new JLabel("Firmenname");
        lbl_lieferant_vorname = new JLabel("Vorname");
        lbl_lieferant_nachname = new JLabel("Nachname");
        lbl_lieferant_strasse = new JLabel("Strasse");
        lbl_lieferant_plz = new JLabel("PLZ");
        lbl_lieferant_ort = new JLabel("Ort");
        lbl_lieferant_email = new JLabel("E-Mail");
        lbl_lieferant_firmenname.setBounds(20, 20, 120, 30);
        lbl_lieferant_vorname.setBounds(20, 60, 120, 30);
        lbl_lieferant_nachname.setBounds(20, 100, 120, 30);
        lbl_lieferant_strasse.setBounds(20, 140, 120, 30);
        lbl_lieferant_plz.setBounds(20, 180, 120, 30);
        lbl_lieferant_ort.setBounds(20, 220, 120, 30);
        lbl_lieferant_email.setBounds(20, 260, 120, 30);
        lbl_lieferant_notfound.setBounds(20, 280, 290, 30);
        administration_jp.add(lbl_lieferant_firmenname);
        administration_jp.add(lbl_lieferant_vorname);
        administration_jp.add(lbl_lieferant_nachname);
        administration_jp.add(lbl_lieferant_strasse);
        administration_jp.add(lbl_lieferant_plz);
        administration_jp.add(lbl_lieferant_ort);
        administration_jp.add(lbl_lieferant_email);
        administration_jp.add(lbl_lieferant_notfound);
        
        tf_lieferant_firmenname = new JTextField();
        tf_lieferant_vorname = new JTextField();
        tf_lieferant_nachname = new JTextField();
        tf_lieferant_strasse = new JTextField();
        tf_lieferant_plz = new JTextField();
        tf_lieferant_ort = new JTextField();
        tf_lieferant_email = new JTextField();
        tf_lieferant_firmenname.setBounds(180, 20, 150, 30);
        tf_lieferant_vorname.setBounds(180, 60, 150, 30);
        tf_lieferant_nachname.setBounds(180, 100, 150, 30);
        tf_lieferant_strasse.setBounds(180, 140, 150, 30);
        tf_lieferant_plz.setBounds(180, 180, 150, 30);
        tf_lieferant_ort.setBounds(180, 220, 150, 30);
        tf_lieferant_email.setBounds(180, 260, 150, 30);
        administration_jp.add(tf_lieferant_firmenname);
        administration_jp.add(tf_lieferant_vorname);
        administration_jp.add(tf_lieferant_nachname);
        administration_jp.add(tf_lieferant_strasse);
        administration_jp.add(tf_lieferant_plz);
        administration_jp.add(tf_lieferant_ort);
        administration_jp.add(tf_lieferant_email);
        
        btn_lieferanten_hinzufügen = new JButton("Lieferant hinzufügen");
        btn_lieferanten_hinzufügen.setBounds(20, 330, 150, 30);
        administration_jp.add(btn_lieferanten_hinzufügen);
        btn_lieferanten_hinzufügen.addActionListener(this);
             
        return administration_jp;
    }
    /**
     * Eine Methode welche den Lieferant-suchen-löschen-Bildschirm erzeugt
     * 
     * @return JPanel - gibt das Lieferant-suchen-löschen-JPanel zurück
     */
    private JPanel getAdministrationContainer_lieferant_suchen_loeschen(){
        administration_jp = new JPanel();
        administration_jp.setLayout(null);
        lieferanten = new ArrayList<>();
        
        if(farbauswahl != null){
            administration_jp.setBackground(farbauswahl);
        }
        
        lbl_lieferant_firmenname = new JLabel("Firmenname");
        lbl_lieferant_vorname = new JLabel("Vorname");
        lbl_lieferant_nachname = new JLabel("Nachname");
        lbl_lieferant_strasse = new JLabel("Strasse");
        lbl_lieferant_plz = new JLabel("PLZ");
        lbl_lieferant_ort = new JLabel("Ort");
        lbl_lieferant_email = new JLabel("E-Mail");
        lbl_lieferant_firmenname.setBounds(20, 20, 120, 30);
        lbl_lieferant_vorname.setBounds(20, 60, 120, 30);
        lbl_lieferant_nachname.setBounds(20, 100, 120, 30);
        lbl_lieferant_strasse.setBounds(20, 140, 120, 30);
        lbl_lieferant_plz.setBounds(20, 180, 120, 30);
        lbl_lieferant_ort.setBounds(20, 220, 120, 30);
        lbl_lieferant_email.setBounds(20, 260, 120, 30);
        lbl_lieferant_notfound.setBounds(20, 290, 290, 30);
        administration_jp.add(lbl_lieferant_firmenname);
        administration_jp.add(lbl_lieferant_vorname);
        administration_jp.add(lbl_lieferant_nachname);
        administration_jp.add(lbl_lieferant_strasse);
        administration_jp.add(lbl_lieferant_plz);
        administration_jp.add(lbl_lieferant_ort);
        administration_jp.add(lbl_lieferant_email);
        administration_jp.add(lbl_lieferant_notfound);
        
        tf_lieferant_firmenname = new JTextField();
        tf_lieferant_vorname = new JTextField();
        tf_lieferant_nachname = new JTextField();
        tf_lieferant_strasse = new JTextField();
        tf_lieferant_plz = new JTextField();
        tf_lieferant_ort = new JTextField();
        tf_lieferant_email = new JTextField();
        tf_lieferant_firmenname.setBounds(180, 20, 150, 30);
        tf_lieferant_vorname.setBounds(180, 60, 150, 30);
        tf_lieferant_nachname.setBounds(180, 100, 150, 30);
        tf_lieferant_strasse.setBounds(180, 140, 150, 30);
        tf_lieferant_plz.setBounds(180, 180, 150, 30);
        tf_lieferant_ort.setBounds(180, 220, 150, 30);
        tf_lieferant_email.setBounds(180, 260, 150, 30);
        administration_jp.add(tf_lieferant_firmenname);
        administration_jp.add(tf_lieferant_vorname);
        administration_jp.add(tf_lieferant_nachname);
        administration_jp.add(tf_lieferant_strasse);
        administration_jp.add(tf_lieferant_plz);
        administration_jp.add(tf_lieferant_ort);
        administration_jp.add(tf_lieferant_email);
       
        for (Adressant lieferant : klListe) {
            if(lieferant.getType().equals("1")){
                lieferanten.add((Lieferant)lieferant);
            }
        }
        
        listModel_lieferant = new DefaultListModel<>();
        for (Lieferant lieferant : lieferanten) {
            listModel_lieferant.addElement(lieferant);
        }
        list_lieferanten = new JList(listModel_lieferant);
        list_lieferanten.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list_lieferanten.setLayoutOrientation(JList.VERTICAL);
        JScrollPane listScroller = new JScrollPane(list_lieferanten);
        listScroller.setBounds(350, 20, 290, 270);        
        administration_jp.add(listScroller);
        
        btn_lieferanten_loeschen = new JButton("Lief. löschen");
        btn_lieferanten_loeschen.setBounds(350, 330, 140, 30);
        administration_jp.add(btn_lieferanten_loeschen);
        btn_lieferanten_loeschen.addActionListener(this);
        
        btn_lieferant_anzeigen = new JButton("Lief. anzeigen");
        btn_lieferant_anzeigen.setBounds(500, 330, 140, 30);
        administration_jp.add(btn_lieferant_anzeigen);
        btn_lieferant_anzeigen.addActionListener(this);
        
        btn_lieferanten_suchen = new JButton("Lieferant suchen");
        btn_lieferanten_suchen.setBounds(20, 330, 150, 30);
        administration_jp.add(btn_lieferanten_suchen);
        btn_lieferanten_suchen.addActionListener(this);
        
        btn_alle_lieferanten_anzeigen = new JButton("Alle anzeigen");
        btn_alle_lieferanten_anzeigen.setBounds(180, 330, 150, 30);
        administration_jp.add(btn_alle_lieferanten_anzeigen);
        btn_alle_lieferanten_anzeigen.addActionListener(this);
        
        return administration_jp;
    }
    /**
     * Eine Methode welche den Anwender-neu-anlegen-Bildschirm erzeugt
     * 
     * @return JPanel - gibt das LAnwender-neu-anlegen-JPanel zurück
     */
    private JPanel getAdministrationContainer_anwender_neu(){
        administration_jp = new JPanel();
        administration_jp.setLayout(null);
        
        if(farbauswahl != null){
            administration_jp.setBackground(farbauswahl);
        }
        
        lbl_anwender_username = new JLabel("Benutzername");
        lbl_anwender_password = new JLabel("Passwort");
        lbl_anwender_username.setBounds(20, 40, 120, 30);
        lbl_anwender_password.setBounds(20, 80, 120, 30);
        administration_jp.add(lbl_anwender_username);
        administration_jp.add(lbl_anwender_password);
        
        tf_anwender_username = new JTextField();
        tf_anwender_password = new JTextField();
        tf_anwender_username.setBounds(140, 40, 120, 30);
        tf_anwender_password.setBounds(140, 80, 120, 30);
        administration_jp.add(tf_anwender_username);
        administration_jp.add(tf_anwender_password);
        
        btn_anwender_hinzufügen = new JButton("Mitarbeiter hinzufügen");
        btn_anwender_hinzufügen.setBounds(20, 120, 180, 30);
        administration_jp.add(btn_anwender_hinzufügen);
        btn_anwender_hinzufügen.addActionListener(this);

        return administration_jp;
    }
    
    /**
     * Eine Methode welche das anwenderFile ausließt und passende
     * Anwenderobjekte dazu in einer ArrayList speichert
     */
    private void putAllUsersInArrayList(){
        anwenderListe = new ArrayList<>();
        try {
            BufferedReader zeile = new BufferedReader(new FileReader(anwenderFile));
            String str;
            int i = 1;        
            // aa ==> aktueller Anwender
            String[] aa = new String[2];
            while ((str = zeile.readLine()) != null) {               
                if(i%2 == 1){
                    aa[0] = str;
                }else{
                    aa[1] = str;
                    Anwender anwender = new Anwender();
                    anwender.setUsername(aa[0]);
                    anwender.setPwHash(aa[1]);
                    anwenderListe.add(anwender);
                }
                i++;
            }
            zeile.close();
        } catch (IOException e) {
            System.out.println("Fehler in Methode putAllUsersInArrayList()");
        }
    }
    /**
     * Eine Methode die prüft ob der eingegebene Benutzername zu dem 
     * eingegebenen Passwort passt
     * 
     * @return boolean - gibt true zurück wenn Benutzername und Passwort 
     * zusammenpassen
     */
    private boolean checkLogin(){
        String pw = String.valueOf(tf_password.getPassword());
        String name = tf_userName.getText();
        try{
            pw = EncryptPassword.SHA512(pw);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }       
        for (Anwender anwender : anwenderListe) {
            if(name.equals(anwender.getUsername()) && pw.equals(anwender.getPwHash())){
                return true;
            }
        }       
        return false;
    }
    
    /**
     * Eine Methode die den FileChooser für die Kunden-Liferanten-CSV-Datei
     * erzeugt und ein Warnungsdialog bei Nichtauswahl einer Datei ausgibt
     * 
     */
    private void csvDateiAuswahl(){
        klListe = new ArrayList<>();
        JFileChooser fcb = new JFileChooser();
        csvSelect = fcb.showOpenDialog(null);
        if(csvSelect == JFileChooser.APPROVE_OPTION){
            
            klFile = new File(fcb.getSelectedFile().getPath());
            putAllKlFromCsvInArrayList();          
        }
        else if (csvSelect != 0){
            fcDialog = new JDialog();
            fcDialog.setLayout(null);
            if(farbauswahl != null){
                fcDialog.setBackground(farbauswahl);
            }
            fcDialog.setTitle("Warnung");
            fcDialog.setSize(400,200);
            fcDialog.setModal(true);
            fcDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            fcDialogText1 = new JLabel("Sie sollten eine Kunden-Lieferanten-CSV-Datei auswählen,");
            fcDialogText2 = new JLabel("um Kunden und Lieferanten verwalten zu können!");
            fcDialogText1.setBounds(20, 20, 350, 20);
            fcDialog.add(fcDialogText1);
            fcDialogText2.setBounds(20, 40, 350, 20);
            fcDialog.add(fcDialogText2);
            csvNeuAuswahl = new JButton("CSV auswählen");
            csvNeuAuswahl.setBounds(20, 100, 160, 30);
            fcDialog.add(csvNeuAuswahl);
            csvNeuAuswahl.addActionListener(this);
            cancelCsvAuswahl = new JButton("Abbrechen");
            cancelCsvAuswahl.setBounds(200, 100, 160, 30);
            fcDialog.add(cancelCsvAuswahl);
            cancelCsvAuswahl.addActionListener(this);
            fcDialog.setLocationRelativeTo(adressverwaltung);     
            fcDialog.setVisible(true);    
        }
    }
    
    /**
     * Eine Methode die alle Kunden und Lieferanten aus der CSV-Datei ausließt
     * und dazu passende Objekte in einer ArrayList speichert
     */
    private void putAllKlFromCsvInArrayList(){
        try{
            BufferedReader zeile = new BufferedReader(new FileReader(klFile));
            String str;
            // aA ==> aktueller Adressant
            String[] aA;
            while((str = zeile.readLine())!= null){
                aA = str.split(";");               
                if(aA != null && aA.length == 9){
                    if(aA[1].equals("0")){
                        Kunde kunde = new Kunde(aA[0],aA[1],aA[3],aA[4],aA[5],aA[6],aA[7],aA[8]);
                        klListe.add(kunde);
                    }
                    else {
                        Lieferant lieferant = new Lieferant(aA[0],aA[1],aA[2],aA[3],aA[4],aA[5],aA[6],aA[7],aA[8]);
                        klListe.add(lieferant);
                    }
                }
            }
            zeile.close();
        }catch(IOException e){
            System.out.println("Fehler in Methode putAllKLInArrayList()");
        }
    }
    
    /**
     * Eine Methode die prüft ob alle Textfelder beim Neuanlegen eines
     * Kunden befüllt sind
     * 
     * @param kunde - ein Kundenobjekt
     * @return boolean - gibt true zurück wenn alle Textfelder befüllt sind
     */
    private boolean isKundeValid(Kunde kunde){
        if(kunde.getVorname().equals("")){
            return false;
        }
        if(kunde.getNachname().equals("")){
            return false;
        }
        if(kunde.getStrasse().equals("")){
            return false;
        }
        if(kunde.getPlz().equals("")){
            return false;
        }
        if(kunde.getOrt().equals("")){
            return false;
        }
        if(kunde.getEmail().equals("")){
            return false;
        }
        return true;
    }
    /**
     * Eine Methode die prüft ob alle Textfelder beim Neuanlegen eines
     * Lieferanten befüllt sind
     * 
     * @param lieferant - ein Lieferantenobjektobjekt
     * @return boolean - gibt true zurück wenn alle Textfelder befüllt sind
     */
    private boolean isLieferantValid(Lieferant lieferant){
        if(lieferant.getFirmenname().equals("")){
            return false;
        }
        if(lieferant.getVorname().equals("")){
            return false;
        }
        if(lieferant.getNachname().equals("")){
            return false;
        }
        if(lieferant.getStrasse().equals("")){
            return false;
        }
        if(lieferant.getPlz().equals("")){
            return false;
        }
        if(lieferant.getOrt().equals("")){
            return false;
        }
        if(lieferant.getEmail().equals("")){
            return false;
        }
        return true;
    }
    
    /**
     * Eine Methode die prüft, ob eine eingegebene E-Mail-Adresse schon vorkommt
     * 
     * @param adressant - ein Objekt vom Typ Adressant
     * @return boolean - gibt zurück ob eine eingegebene E-mail-Adresse schon 
     * vorkommt
     */
    private boolean isEmailUnique(Adressant adressant){
        for (Adressant a : klListe) {
            if(a.getEmail().equals(adressant.getEmail())){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Eine Methode die die Informatioen in den Textfeldern beim Neuanlegen
     * eines Kunden in ein Kunden-Objekt speichert und es anschließend
     * einer ArrayList hinzufügt
     */
    private void addKundeToArrayList(){
        Kunde kunde = new Kunde();
        kunde.setId(String.valueOf(Integer.parseInt((klListe.get(klListe.size()-1)).getId())+1));
        kunde.setType("0");
        kunde.setVorname(tf_kunde_vorname.getText());
        kunde.setNachname(tf_kunde_nachname.getText());
        kunde.setStrasse(tf_kunde_strasse.getText());
        kunde.setPlz(tf_kunde_plz.getText());
        kunde.setOrt(tf_kunde_ort.getText());
        kunde.setEmail(tf_kunde_email.getText());
        if(isKundeValid(kunde)){
            if(isEmailUnique(kunde)){
                klListe.add(kunde);
            }
            else{
                addKundeEmailDialog = new JDialog();
                addKundeEmailDialog.setLayout(null);
                addKundeEmailDialog.setTitle("Hinweis");
                addKundeEmailDialog.setSize(260,200);
                addKundeEmailDialog.setModal(true);
                addKundeEmailDialogText = new JLabel("Diese E-Mail-Adresse existiert schon.");
                addKundeEmailDialogText.setBounds(20, 20, 350, 20);
                addKundeEmailDialog.add(addKundeEmailDialogText);
                addKundeEmailDialogButton = new JButton("OK");
                addKundeEmailDialogButton.setBounds(20, 100, 70, 30);
                addKundeEmailDialog.add(addKundeEmailDialogButton);
                addKundeEmailDialogButton.addActionListener(this);
                addKundeEmailDialog.setLocationRelativeTo(adressverwaltung);     
                addKundeEmailDialog.setVisible(true);
            }
        }
        else{
            addKundeDialog = new JDialog();
            addKundeDialog.setLayout(null);
            addKundeDialog.setTitle("Hinweis");
            addKundeDialog.setSize(260,200);
            addKundeDialog.setModal(true);
            addKundeDialogText = new JLabel("Bitte füllen Sie alle Felder aus.");
            addKundeDialogText.setBounds(20, 20, 350, 20);
            addKundeDialog.add(addKundeDialogText);
            addKundeDialogButton = new JButton("OK");
            addKundeDialogButton.setBounds(20, 100, 70, 30);
            addKundeDialog.add(addKundeDialogButton);
            addKundeDialogButton.addActionListener(this);
            addKundeDialog.setLocationRelativeTo(adressverwaltung);     
            addKundeDialog.setVisible(true);
        } 
    }
    /**
     * Eine Methode die die Informatioen in den Textfeldern beim Neuanlegen
     * eines Liefernaten in ein Lieferanten-Objekt speichert und es anschließend
     * einer ArrayList hinzufügt
     */
    private void addLieferantToArrayList(){
        Lieferant lieferant = new Lieferant();
        lieferant.setId(String.valueOf(Integer.parseInt((klListe.get(klListe.size()-1)).getId())+1));
        lieferant.setType("1");
        lieferant.setFirmenname(tf_lieferant_firmenname.getText());
        lieferant.setVorname(tf_lieferant_vorname.getText());
        lieferant.setNachname(tf_lieferant_nachname.getText());
        lieferant.setStrasse(tf_lieferant_strasse.getText());
        lieferant.setPlz(tf_lieferant_plz.getText());
        lieferant.setOrt(tf_lieferant_ort.getText());
        lieferant.setEmail(tf_lieferant_email.getText());
        if(isLieferantValid(lieferant)){
            if(isEmailUnique(lieferant)){
                klListe.add(lieferant);
            }
            else{
                addLieferantEmailDialog = new JDialog();
                addLieferantEmailDialog.setLayout(null);
                if(farbauswahl != null){
                    addLieferantEmailDialog.setBackground(farbauswahl);
                }
                addLieferantEmailDialog.setTitle("Hinweis");
                addLieferantEmailDialog.setSize(260,200);
                addLieferantEmailDialog.setModal(true);
                addLieferantEmailDialogText = new JLabel("Diese E-Mail-Adresse existiert schon.");
                addLieferantEmailDialogText.setBounds(20, 20, 350, 20);
                addLieferantEmailDialog.add(addLieferantEmailDialogText);
                addLieferantEmailDialogButton = new JButton("OK");
                addLieferantEmailDialogButton.setBounds(20, 100, 70, 30);
                addLieferantEmailDialog.add(addLieferantEmailDialogButton);
                addLieferantEmailDialogButton.addActionListener(this);
                addLieferantEmailDialog.setLocationRelativeTo(adressverwaltung);     
                addLieferantEmailDialog.setVisible(true);
            }
        }
        else{
            addLieferantDialog = new JDialog();
            addLieferantDialog.setLayout(null);
            if(farbauswahl != null){
                addLieferantDialog.setBackground(farbauswahl);
            }
            addLieferantDialog.setTitle("Hinweis");
            addLieferantDialog.setSize(260,200);
            addLieferantDialog.setModal(true);
            addLieferantDialogText = new JLabel("Bitte füllen Sie alle Felder aus.");
            addLieferantDialogText.setBounds(20, 20, 350, 20);
            addLieferantDialog.add(addLieferantDialogText);
            addLieferantDialogButton = new JButton("OK");
            addLieferantDialogButton.setBounds(20, 100, 70, 30);
            addLieferantDialog.add(addLieferantDialogButton);
            addLieferantDialogButton.addActionListener(this);
            addLieferantDialog.setLocationRelativeTo(adressverwaltung);     
            addLieferantDialog.setVisible(true);
        } 
    }
    /**
     * Eine Methode die einen ausgewählten Kunden von der ArrayList löscht
     */
    private void deleteKundeFromArrayList(){
        Kunde selectedKunde = (Kunde)list_kunden.getSelectedValue();
        klListe.remove(selectedKunde);
        kunden.remove(selectedKunde);
        listModel_kunde.removeElement(selectedKunde);
    }
    /**
     * Eine Methode die einen ausgewählten Lieferanten von der ArrayList löscht
     */
    private void deleteLieferantFromArrayList(){
        Lieferant selectedLieferant = (Lieferant)list_lieferanten.getSelectedValue();
        klListe.remove(selectedLieferant);
        kunden.remove(selectedLieferant);
        listModel_kunde.removeElement(selectedLieferant);
    }
    /**
     * Eine Methode die alle Einträge der KL-ArrayList in das kl-csv-File 
     * überträgt
     */
    private void putAllFromArrayListInCsv(){
        String newKunde;
        String ending = "\n";
        try {
            PrintWriter writer = new PrintWriter(klFile);
            writer.print("");
            writer.close();
            }
        catch(IOException e){
            System.out.println("Fehler 1 in putAllFromArrayListInCsv()");
        }
        for (Adressant a : klListe) {
            if(a.getType().equals("1")){
                if(klListe.indexOf(a) == (klListe.size()-1)){
                    ending = "";
                }
                Lieferant l = new Lieferant();
                l = (Lieferant)a;
                newKunde = l.getId() + ";" + l.getType() + ";" + l.getFirmenname() + ";" + l.getVorname() 
                + ";" + l.getNachname() + ";" + l.getStrasse() + ";" + l.getPlz() + ";" + l.getOrt() 
                + ";" + l.getEmail() + ending;
                try{
                    FileWriter fw = new FileWriter(klFile, true);
                    if (!klFile.exists()){
                        fw.write(newKunde);
                    }
                    else{
                        fw.append(newKunde);
                    }
                    fw.close();
                }catch(IOException e){
                        System.out.println("Fehler 2 in putAllFromArrayListInCsv()");
                }
            }
            else{
                if(klListe.indexOf(a) == (klListe.size()-1)){
                    ending = "";
                }
                Kunde k = new Kunde();
                k = (Kunde)a;
                newKunde = k.getId() + ";" + k.getType() + ";;" + k.getVorname() 
                + ";" + k.getNachname() + ";" + k.getStrasse() + ";" + k.getPlz() + ";" + k.getOrt() 
                + ";" + k.getEmail() + ending;
                try{
                    FileWriter fw = new FileWriter(klFile, true);
                    if (!klFile.exists()){
                        fw.write(newKunde);
                    }
                    else{
                        fw.append(newKunde);
                    }
                    fw.close();

                }catch(IOException e){
                        System.out.println("Fehler 3 in putAllFromArrayListInCsv()");
                }
            }  
        }
    }
    /**
     * Eine Methode die zwei Objekte vom Typ Kunde vergleicht und Leereinträge
     * von k1 dabei ignoriert
     * 
     * @param k1 - ein Objekt vom Typ Kunde
     * @param k2 - ein Objekt vom Typ Kunde
     * @return boolean - gibt true zurück wenn alle gesetzten Attribute 
     * von k1 auch in k2 vorkommen
     */
    private boolean compareKunden(Kunde k1, Kunde k2){
        boolean erg = false;
        if(!(k1.getVorname() == null)){
            if (k1.getVorname().equals(k2.getVorname())){
                erg = true;
            }else{return false;}
        }
        if(!(k1.getNachname() == null)){
            if (k1.getNachname().equals(k2.getNachname())){
                erg = true;
            }else{return false;}
        }
        if(!(k1.getStrasse() == null)){
            if (k1.getStrasse().equals(k2.getStrasse())){
                erg = true;
            }else{return false;}
        }
        if(!(k1.getPlz() == null)){
            if (k1.getPlz().equals(k2.getPlz())){
                erg = true;
            }else{return false;}
        }
        if(!(k1.getOrt() == null)){
            if (k1.getOrt().equals(k2.getOrt())){
                erg = true;
            }else{return false;}
        }
        if(!(k1.getEmail() == null)){
            if (k1.getEmail().equals(k2.getEmail())){
                erg = true;
            }else{return false;}
        }
        return erg;
    }
    /**
     * Eine Methode die alle Kunden mit übereinstimmenden Attributwerten
     * (Leereinträge werden vernachlässigt) in eine ArrayList packt
     */
    private void searchKunde(){
        foundKunden = new ArrayList<>();
        Kunde compareDummy = new Kunde();
        String vn = tf_kunde_vorname.getText();
        String nn = tf_kunde_nachname.getText();
        String st = tf_kunde_strasse.getText();
        String plz = tf_kunde_plz.getText();
        String ort = tf_kunde_ort.getText();
        String em = tf_kunde_email.getText();
       
        if(!vn.equals("")){compareDummy.setVorname(vn);}
        if(!nn.equals("")){compareDummy.setNachname(nn);}
        if(!st.equals("")){compareDummy.setStrasse(st);}
        if(!plz.equals("")){compareDummy.setPlz(plz);}
        if(!ort.equals("")){compareDummy.setOrt(ort);}
        if(!em.equals("")){compareDummy.setEmail(em);}
               
        for (Kunde k : kunden) {
           if(compareKunden(compareDummy,k)){
               foundKunden.add(k);
           } 
        }
    }
    /**
     * Eine Methode die zwei Objekte vom Typ Lieferant vergleicht und Leereinträge
     * von l1 dabei ignoriert
     * 
     * @param l1 - ein Objekt vom Typ Lieferant
     * @param l2 - ein Objekt vom Typ Lieferant
     * @return boolean - gibt true zurück wenn alle gesetzten Attribute 
     * von l1 auch in l2 vorkommen
     */
    private boolean compareLieferanten(Lieferant l1, Lieferant l2){
        boolean erg = false;
        if(!(l1.getFirmenname() == null)){
            if (l1.getFirmenname().equals(l2.getFirmenname())){
                erg = true;
            }else{return false;}
        }
        if(!(l1.getVorname() == null)){
            if (l1.getVorname().equals(l2.getVorname())){
                erg = true;
            }else{return false;}
        }
        if(!(l1.getNachname() == null)){
            if (l1.getNachname().equals(l2.getNachname())){
                erg = true;
            }else{return false;}
        }
        if(!(l1.getStrasse() == null)){
            if (l1.getStrasse().equals(l2.getStrasse())){
                erg = true;
            }else{return false;}
        }
        if(!(l1.getPlz() == null)){
            if (l1.getPlz().equals(l2.getPlz())){
                erg = true;
            }else{return false;}
        }
        if(!(l1.getOrt() == null)){
            if (l1.getOrt().equals(l2.getOrt())){
                erg = true;
            }else{return false;}
        }
        if(!(l1.getEmail() == null)){
            if (l1.getEmail().equals(l2.getEmail())){
                erg = true;
            }else{return false;}
        }
        return erg;
    }
    /**
     * Eine Methode die alle Lieferanten mit übereinstimmenden Attributwerten
     * (Leereinträge werden vernachlässigt) in eine ArrayList packt
     */
    private void searchLieferant(){
        foundLieferanten = new ArrayList<>();
        Lieferant compareDummy = new Lieferant();
        String fn = tf_lieferant_firmenname.getText();
        String vn = tf_lieferant_vorname.getText();
        String nn = tf_lieferant_nachname.getText();
        String st = tf_lieferant_strasse.getText();
        String plz = tf_lieferant_plz.getText();
        String ort = tf_lieferant_ort.getText();
        String em = tf_lieferant_email.getText();
        
        if(!fn.equals("")){compareDummy.setFirmenname(fn);}
        if(!vn.equals("")){compareDummy.setVorname(vn);}
        if(!nn.equals("")){compareDummy.setNachname(nn);}
        if(!st.equals("")){compareDummy.setStrasse(st);}
        if(!plz.equals("")){compareDummy.setPlz(plz);}
        if(!ort.equals("")){compareDummy.setOrt(ort);}
        if(!em.equals("")){compareDummy.setEmail(em);}
           
        for (Lieferant l : lieferanten) {
           if(compareLieferanten(compareDummy,l)){
               foundLieferanten.add(l);
           } 
        }
    }
    /**
     * Eine Methode die aus den Informationen der Textfelder beim Neuanlegen
     * eines Anwenders ein neues Objekt vom Typ Anwender erzeugt und dieses
     * in eine ArrayList packt
     */
    private void addAnwenderToArrayList(){
        String username = tf_anwender_username.getText();
        String password = tf_anwender_password.getText();

        try{
            password = EncryptPassword.SHA512(password);
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        Anwender a = new Anwender(username,password);
        anwenderListe.add(a);
    }
    /**
     * Eine Methode die alle Anwender aus der ArrayList in das Textfile packt
     */
    private void putAllAnwenderInTextFile(){
        String newAnwender;
        String ending = "\n";
        try {
            PrintWriter writer = new PrintWriter(anwenderFile);
            writer.print("");
            writer.close();
            }
        catch(IOException e){
            System.out.println("Fehler 1 in putAllFromArrayListInCsv()");
        }
        for (Anwender a : anwenderListe) {
            if(anwenderListe.indexOf(a) == (anwenderListe.size()-1)){
                ending = "";
            }
            newAnwender = a.getUsername() + "\n" + a.getPwHash() + ending;
            try{
                FileWriter fw = new FileWriter(anwenderFile, true);
                if (!anwenderFile.exists()){
                    fw.write(newAnwender);
                }
                else{
                    fw.append(newAnwender);
                }
                fw.close();
            }catch(IOException e){
                    System.out.println("Fehler 2 in putAllFromArrayListInCsv()");
            }
        }
    }
    /**
     * Eine Methode die den Ueber-Dialog im Menü Hilfe erzeugt
     */
    private void showUeberDialog(){
        ueberDialog = new JDialog();
        ueberDialog.setLayout(null);
        if(farbauswahl != null){
            ueberDialog.setBackground(farbauswahl);
        }
        ueberDialog.setTitle("Über");
        ueberDialog.setSize(280,150);
        ueberDialog.setModal(true);
        
        lbl_ueberVersionText = new JLabel("Adressverwaltung Version 1.00");
        lbl_ueberAuthorText = new JLabel("Author: Peter Berger");
        lbl_ueberVersionText.setBounds(20, 20, 220, 20);
        lbl_ueberAuthorText.setBounds(20, 50, 220, 20);
        ueberDialog.add(lbl_ueberVersionText);
        ueberDialog.add(lbl_ueberAuthorText);
        
        ueberDialog.setLocationRelativeTo(adressverwaltung);     
        ueberDialog.setVisible(true);  
    }
    
    /**
     * Eine Methode welche die Informationen eines ausgewählten Kunden in den
     * dazu passenden Textfeldern anzeigt
     */
    private void showKunde(){
        Kunde selectedKunde = (Kunde)list_kunden.getSelectedValue();
        tf_kunde_vorname.setText(selectedKunde.getVorname());
        tf_kunde_nachname.setText(selectedKunde.getNachname());
        tf_kunde_strasse.setText(selectedKunde.getStrasse());
        tf_kunde_plz.setText(selectedKunde.getPlz());
        tf_kunde_ort.setText(selectedKunde.getOrt());
        tf_kunde_email.setText(selectedKunde.getEmail());
    }
    /**
     * Eine Methode welche die Informationen eines ausgewählten Lieferanten 
     * in den dazu passenden Textfeldern anzeigt
     */
    private void showLieferant(){
        Lieferant selectedLieferant = (Lieferant)list_lieferanten.getSelectedValue();
        tf_lieferant_firmenname.setText(selectedLieferant.getFirmenname());
        tf_lieferant_vorname.setText(selectedLieferant.getVorname());
        tf_lieferant_nachname.setText(selectedLieferant.getNachname());
        tf_lieferant_strasse.setText(selectedLieferant.getStrasse());
        tf_lieferant_plz.setText(selectedLieferant.getPlz());
        tf_lieferant_ort.setText(selectedLieferant.getOrt());
        tf_lieferant_email.setText(selectedLieferant.getEmail());
    }
    
    private void showCsvSelectDialog(){
        csvSelectDialog = new JDialog();
        csvSelectDialog.setLayout(null);
        if(farbauswahl != null){
            csvSelectDialog.setBackground(farbauswahl);
        }
        csvSelectDialog.setTitle("CSV Auswahl");
        csvSelectDialog.setSize(350,200);
        csvSelectDialog.setModal(true);
        
        lbl_csvSelectDialogText1 = new JLabel("Bevor Sie das tuen können, müssen Sie eine");
        lbl_csvSelectDialogText2 = new JLabel("Kunden-Lieferanten-CSV-Datei auswählen");
        lbl_csvSelectDialogText1.setBounds(20, 20, 280, 20);
        lbl_csvSelectDialogText2.setBounds(20, 50, 280, 20);
        csvSelectDialog.add(lbl_csvSelectDialogText1);
        csvSelectDialog.add(lbl_csvSelectDialogText2);
        csvNeuAuswahl2 = new JButton("CSV auswählen");
        csvNeuAuswahl2.setBounds(20, 100, 160, 30);
        csvNeuAuswahl2.addActionListener(this);
        csvSelectDialog.add(csvNeuAuswahl2);
        

        csvSelectDialog.setLocationRelativeTo(adressverwaltung);     
        csvSelectDialog.setVisible(true);
    }
    
    /**
     * Eine ActionListener Methode die festlegt was bei welchem Event 
     * (zB Buttonklick) passieren soll
     * 
     * @param e - ein Objekt vom Typ ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(lbl_lieferant_notfound.getText().equals("Es wurde kein passender Lieferant gefunden.")){
            lbl_lieferant_notfound.setText("");
        }
        if(lbl_kunde_notfound.getText().equals("Es wurde kein passender Kunde gefunden.")){
            lbl_kunde_notfound.setText("");
        }
        if(e.getSource() == this.btn_login){
            if(checkLogin()){
                login_jp.setVisible(false);
                adressverwaltung.remove(login_jp);
                adressverwaltung.setJMenuBar(getMenuBar2());
                current_jp = getAdministrationContainer_empty();
                adressverwaltung.add(current_jp);
                csvDateiAuswahl();
                klListeLength = klListe.size();
                
            }else{
                lbl_loginFail.setText("Diese Logindaten sind nicht bekannt.");
            }
        }
        if(e.getSource() == this.kunde_neu){
            if(csvSelect == 0){
                if(klListe.size() != klListeLength){
                    putAllFromArrayListInCsv();
                }
                if(anwenderListe.size() != anwenderListeLength){
                    putAllAnwenderInTextFile();
                }
                current_jp.setVisible(false);
                adressverwaltung.remove(current_jp);
                current_jp = getAdministrationContainer_kunde_neu();
                adressverwaltung.add(current_jp);
                klListeLength = klListe.size();
                anwenderListeLength = anwenderListe.size();
            }else{
                showCsvSelectDialog();
            }
            System.out.println(csvSelect);
        }
        
        
        if(e.getSource() == this.kunde_s_l){
            if(klListe.size() != klListeLength){
                putAllFromArrayListInCsv();
            }
            if(anwenderListe.size() != anwenderListeLength){
                putAllAnwenderInTextFile();
            }
            current_jp.setVisible(false);
            adressverwaltung.remove(current_jp);
            current_jp = getAdministrationContainer_kunde_suchen_loeschen();
            adressverwaltung.add(current_jp);
            klListeLength = klListe.size();
            anwenderListeLength = anwenderListe.size();
        }

        if(e.getSource() == this.btn_kunden_loeschen){
            if(csvSelect == 0){
                deleteKundeFromArrayList();
            }else{
                showCsvSelectDialog();
            }
        }
        
        if(e.getSource() == this.lieferant_neu){
            if(csvSelect == 0){
                if(klListe.size() != klListeLength){
                    putAllFromArrayListInCsv();
                }
                if(anwenderListe.size() != anwenderListeLength){
                    putAllAnwenderInTextFile();
                }
                current_jp.setVisible(false);
                adressverwaltung.remove(current_jp);
                current_jp = getAdministrationContainer_lieferant_neu();
                adressverwaltung.add(current_jp);
                klListeLength = klListe.size();
                anwenderListeLength = anwenderListe.size();
            }else{
                showCsvSelectDialog();
            }
        }
        
        if(e.getSource() == this.lieferant_s_l){
            if(klListe.size() != klListeLength){
                putAllFromArrayListInCsv();
            }
            if(anwenderListe.size() != anwenderListeLength){
                putAllAnwenderInTextFile();
            }
            current_jp.setVisible(false);
            adressverwaltung.remove(current_jp);
            current_jp = getAdministrationContainer_lieferant_suchen_loeschen();
            adressverwaltung.add(current_jp);
            klListeLength = klListe.size();
            anwenderListeLength = anwenderListe.size();
        }

        if(e.getSource() == this.btn_lieferanten_loeschen){
            if(csvSelect == 0){
                deleteLieferantFromArrayList();
            }else{
                showCsvSelectDialog();
            }
        }
        if(e.getSource() == this.anwender_neu){
            if(klListe.size() != klListeLength){
                putAllFromArrayListInCsv();
            }
            if(anwenderListe.size() != anwenderListeLength){
                putAllAnwenderInTextFile();
            }
            current_jp.setVisible(false);
            adressverwaltung.remove(current_jp);
            current_jp = getAdministrationContainer_anwender_neu();
            adressverwaltung.add(current_jp);
            klListeLength = klListe.size();
            anwenderListeLength = anwenderListe.size();
        }
        if(e.getSource() == this.csvNeuAuswahl){
            fcDialog.setVisible(false);
            csvDateiAuswahl();
        }
        
        if(e.getSource() == this.csvNeuAuswahl2){
            csvSelectDialog.setVisible(false);
            csvDateiAuswahl();
        }
        
        if(e.getSource() == this.cancelCsvAuswahl){
            fcDialog.setVisible(false);
        }
        
        if(e.getSource() == this.btn_kunden_hinzufügen){
            if(csvSelect == 0){
                addKundeToArrayList();
            }else{
                showCsvSelectDialog();
            }   
        }
        
        if(e.getSource() == this.addKundeDialogButton){
            addKundeDialog.setVisible(false);
        }
        
        if(e.getSource() == this.addKundeEmailDialogButton){
            addKundeEmailDialog.setVisible(false);
        }
        
        if(e.getSource() == this.btn_lieferanten_hinzufügen){
            if(csvSelect == 0){
                addLieferantToArrayList();
            }else{
                showCsvSelectDialog();
            }
        }
        
        if(e.getSource() == this.addLieferantDialogButton){
            addLieferantDialog.setVisible(false);
        }
        
        if(e.getSource() == this.addLieferantEmailDialogButton){
            addLieferantEmailDialog.setVisible(false);
        }
        
        if(e.getSource() == this.btn_kunden_suchen){
            if(csvSelect == 0){
                searchKunde();
                if(!foundKunden.isEmpty()){
                    listModel_kunde.clear();
                    for (Kunde kunde : foundKunden) {
                        listModel_kunde.addElement(kunde);
                }
                }else{
                    lbl_kunde_notfound.setText("Es wurde kein passender Kunde gefunden.");
                }
            }else{
                showCsvSelectDialog();
            }
            
        }
        
        if(e.getSource() == this.btn_alle_kunden_anzeigen){
            if(csvSelect == 0){
                listModel_kunde.clear();
                for (Kunde kunde : kunden) {
                    listModel_kunde.addElement(kunde);
                }
            }else{
                showCsvSelectDialog();
            }
        }
        
        if (e.getSource() == this.btn_lieferanten_suchen) {
            if (csvSelect == 0) {
                searchLieferant();
                if(!foundLieferanten.isEmpty()) {
                    listModel_lieferant.clear();
                    for (Lieferant lieferant : foundLieferanten) {
                        listModel_lieferant.addElement(lieferant);
                    }
                } else {
                    lbl_lieferant_notfound.setText("Es wurde kein passender Lieferant gefunden.");
                }
            } else {
                showCsvSelectDialog();
            }           
        }
        
        if (e.getSource() == this.btn_alle_lieferanten_anzeigen) {
            if (csvSelect == 0) {
                listModel_lieferant.clear();
                for (Lieferant lieferant : lieferanten) {
                    listModel_lieferant.addElement(lieferant);
                }
            } else {
                showCsvSelectDialog();
            }
        }
        
        if(e.getSource() == this.btn_anwender_hinzufügen){
            addAnwenderToArrayList();
        }
        
        if(e.getSource() == this.farbe){
            farbauswahl = JColorChooser.showDialog(null, "Farbauswahl", null);
            current_jp.setBackground(farbauswahl);
        }
        
        if(e.getSource() == this.ueber){
            showUeberDialog();
        }
        
        if(e.getSource() == this.klCsvNeuWaehlen){
            csvDateiAuswahl();
        }
        
        if(e.getSource() == this.btn_kunde_anzeigen){
            if(csvSelect == 0){
                showKunde();
            }else{
                showCsvSelectDialog();
            }
        }
        
        if(e.getSource() == this.btn_lieferant_anzeigen){
            if(csvSelect == 0){
                showLieferant();
            }else{
                showCsvSelectDialog();
            }
        }
    }
}