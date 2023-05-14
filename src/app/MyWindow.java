import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.event.*;
import javax.swing.*;


public class MyWindow extends JFrame implements ActionListener, Runnable {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private JPanel conPane;

    MyLogger logger = null;
    /**
     * Zmienne tworzace menu
     */
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, viewMenu, calcMenu, helpMenu;
    JMenuItem fileExitMenuItem, helpContextMenutem, helpAboutMenuItem;

    //JCheckBoxMenuItem viewStatusBarMenuItem, viewJToolBarMenuItem;

    /**
     * Zmienne tworz�ce pasek narzedziowy
     */
    JToolBar jToolBar;
    JButton jtbPrint, jtbExit, jtbHelp, jtbAbout;

    /**
     * definicja etykiet tekstowych
     */
    String[] labelMenu = {"Plik", "Edycja", "Widok", "Obliczenia", "Pomoc"};
    String[] labelFileMenuItem = {"Logowanie", "Wylogowanie", "Drukuj", "Zamknij"};
    String[] labelViewMenuItem = {"Ukryj pasek statusu", "Ukryj pasek narz�dziowy"};
    String[] labelHelpMenuItem = {"Kontekst pomocy", "Informacje o programie"};

    /**
     * definicja etykiet opisuj�cych w tzw. dymkach
     */
    String[] tooltipFileMenu = {"Dodaj wartość do tabeli", "Wyzeruj tabelę",
            "Zapisz zawartość tabeli do pliku", "Wyświetl sumę wszystkich elementów tablicy",
            "Wyświetl średnią z wszystkich elementów tablicy", "wartość min", "wartość Max"};
    String[] tooltipHelpMenu = {"Uruchomienie pomocy", "Informacje o programie"};

    Icon iconLogin, iconLogout, iconPrint, iconExit, iconHelpContext, iconAbout;
    Icon mIconLogin, mIconLogout, mIconPrint, mIconExit, mIconHelpContext, mIconAbout;

    /**
     * Konstruktor bezparametrowy klasy <code>MyWindow</code>
     */
    public MyWindow() {
        this.setTitle("Moje Okno");
        // definicja zdarzenia zamkniecia okna
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                MyLogger.writeLog("INFO", "Zamkniecie aplikacji");
                dispose();
                System.exit(0);
            }
        });
        // Rozmieszczenie okna na srodku ekranu
        Dimension frameSize = new Dimension(WIDTH, HEIGHT);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//pobranie rozdzielczosci pulpitu
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;
        if (frameSize.width > screenSize.width) frameSize.width = screenSize.width;
        setSize(frameSize);
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

        // Utworzenie glownego kontekstu (ContentPane)
        conPane = (JPanel) this.getContentPane();
        conPane.setLayout(new BorderLayout());
        // Utworzenie dziennika zdarzen
        logger = new MyLogger();

        // utworzenie GUI w watku zdarzeniowym
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createIcons();
                    createMenu();
                    createGUI();
                }
            });
        } catch (Exception e) {
            MyLogger.writeLog("ERROR", "Blad podczas tworzenia GUI aplikacji");
            System.out.println("ERROR - Blad podczas tworzenia GUI aplikacji");
        }
        // utworzenie watku uruchamiajacgo okno logowania
        Thread thread = new Thread(this);
        thread.start();
    }

    private void createIcons() {
        try {
            // Utworzenie ikon 24 * 24 px dla paska toolbar
            iconLogin = createMyIcon("login.jpg");
            iconLogout = createMyIcon("logout.jpg");
            iconPrint = createMyIcon("print.jpg");
            iconExit = createMyIcon("close.jpg");
            iconHelpContext = createMyIcon("help_context.jpg");
            iconAbout = createMyIcon("about.jpg");

            // Utworzenie ikon 16 * 16 px dla MenuItem
            mIconLogin = createMyIcon("min_login.jpg");
            mIconLogout = createMyIcon("min_logout.jpg");
            mIconPrint = createMyIcon("min_print.jpg");
            mIconExit = createMyIcon("min_close.jpg");
            mIconHelpContext = createMyIcon("min_help_context.jpg");
            mIconAbout = createMyIcon("min_about.jpg");
        }
        catch(Exception e) {
            MyLogger.writeLog("ERROR","Blad tworzenia ikon");
            System.out.println("ERROR - Blad tworzenia ikon");
        }
    }

    public Icon createMyIcon(String file) {
        String name = "/grafika/"+file;
        Icon icon = new ImageIcon(getClass().getResource(name));
        return icon;
    }
}