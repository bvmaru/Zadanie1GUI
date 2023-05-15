package app;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.awt.event.*;
import javax.swing.*;


public class MyWindow extends JFrame implements ActionListener, Runnable {
    private static final int WIDTH = 600;
    private static final int HEIGHT = 450;
    private JPanel conPane;

    //MyLogger logger = null;
    /**
     * Zmienne tworzace menu
     */
    JMenuBar menuBar;
    JMenu fileMenu, editMenu, viewMenu, calcMenu, helpMenu;
    JMenuItem fileExitMenuItem, helpContextMenuItem, helpAboutMenuItem, sumaMenuItem, sredniaMenuItem, minMenuitem, maxMenuitem;

    //JCheckBoxMenuItem viewStatusBarMenuItem, viewJToolBarMenuItem;

    /**
     * Zmienne tworz�ce pasek narzedziowy
     */
    JToolBar jToolBar;
    JButton jtbSave, jtbSuma, jtbSrednia, jtbMin, jtbMax, jtbExit, jtbHelp, jtbAbout;

    /**
     * definicja etykiet tekstowych
     */
    String[] labelMenu = {"Plik", "Edycja", "Widok", "Obliczenia", "Pomoc"};
    String[] labelFileMenuItem = {"Zamknij","Zapisz"};
    String[] labelCalcMenuItem = {"Suma","Srednia","Min","Max"};
    String[] labelViewMenuItem = {"Ukryj pasek statusu", "Ukryj pasek narz�dziowy"};
    String[] labelHelpMenuItem = {"Kontekst pomocy", "Informacje o programie"};

    /**
     * definicja etykiet opisuj�cych w tzw. dymkach
     */

    String[] tooltipFileMenu ={"Zamknij","Zapisz"};
            String[] tooltipCalcMenu = {"Dodaj wartość do tabeli", "Wyzeruj tabelę",
            "Zapisz zawartość tabeli do pliku", "Wyświetl sumę wszystkich elementów tablicy",
            "Wyświetl średnią z wszystkich elementów tablicy", "wartość min", "wartość Max"};

    //String[] tooltipFileMenu = {"Dodaj wartość do tabeli", "Wyzeruj tabelę",
      //      "Zapisz zawartość tabeli do pliku", "Wyświetl sumę wszystkich elementów tablicy",
         //   "Wyświetl średnią z wszystkich elementów tablicy", "wartość min", "wartość Max"};
    String[] tooltipHelpMenu = {"Uruchomienie pomocy", "Informacje o programie"};

    Icon iconSuma, iconSrednia, iconMin, iconMax, iconExit, iconHelpContext, iconAbout, iconSave;
    Icon miconSuma, miconSrednia, miconMin, miconMax, mIconExit, mIconHelpContext, mIconAbout, miconSave;

    /**
     * Konstruktor bezparametrowy klasy <code>MyWindow</code>
     */
    public MyWindow() {
        this.setTitle("Moje Okno");
        // definicja zdarzenia zamkniecia okna
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //MyLogger.writeLog("INFO", "Zamkniecie aplikacji");
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
        //logger = new MyLogger();

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
            //MyLogger.writeLog("ERROR", "Blad podczas tworzenia GUI aplikacji");
            System.out.println("ERROR - Blad podczas tworzenia GUI aplikacji");
        }
        // utworzenie watku uruchamiajacgo okno logowania
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        try {
            Thread.sleep(1000); // opoznienie 1 sekunda
        }
        catch(InterruptedException e) {}
        // uruchomienie okna logowania
    }

    private void createIcons() {
        try {
            // Utworzenie ikon 24 * 24 px dla paska toolbar
            iconSuma = createMyIcon("suma.jpg");
            iconSrednia = createMyIcon("srednia.jpg");
            iconMin = createMyIcon("min.jpg");
            iconMax = createMyIcon("max.jpg");
            iconExit = createMyIcon("close.jpg");
            iconHelpContext = createMyIcon("help_context.jpg");
            iconAbout = createMyIcon("about.jpg");
            iconSave = createMyIcon("save.jpg");

            // Utworzenie ikon 16 * 16 px dla MenuItem
            miconSuma = createMyIcon("min_suma.jpg");
            miconSrednia = createMyIcon("min_srednia.jpg");
            miconMin = createMyIcon("min_min.jpg");
            miconMax = createMyIcon("min_max.jpg");
            mIconExit = createMyIcon("min_close.jpg");
            mIconHelpContext = createMyIcon("min_help_context.jpg");
            mIconAbout = createMyIcon("min_about.jpg");
            miconSave = createMyIcon("min_save.jpg");
        }
        catch(Exception e) {
            //MyLogger.writeLog("ERROR","Blad tworzenia ikon");
            System.out.println("ERROR - Blad tworzenia ikon");
        }
    }

    private void createMenu() {
        // Utworzenie paska menu
        menuBar = new JMenuBar();
        // Utworzenie pol menu glownego
        fileMenu = createJMenu(labelMenu[0], KeyEvent.VK_P, true);
        editMenu = createJMenu(labelMenu[1], KeyEvent.VK_E, false);
        viewMenu = createJMenu(labelMenu[2], KeyEvent.VK_W, false);
        calcMenu = createJMenu(labelMenu[3], KeyEvent.VK_W, true);
        helpMenu = createJMenu(labelMenu[4], KeyEvent.VK_O, true);

        // Utworzenie pol podmenu

        fileExitMenuItem = createJMenuItem(labelFileMenuItem[0],mIconExit,
                KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.ALT_MASK),true);

        // utworzenie MenuItem dla calc
        sumaMenuItem = createJMenuItem(labelCalcMenuItem[0],miconSuma,
                KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.ALT_MASK),true);
        sredniaMenuItem = createJMenuItem(labelCalcMenuItem[1],miconSrednia,
                KeyStroke.getKeyStroke(KeyEvent.VK_W,ActionEvent.ALT_MASK),false);
        minMenuitem = createJMenuItem(labelCalcMenuItem[2],miconMin,
                KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.ALT_MASK),false);
        maxMenuitem = createJMenuItem(labelCalcMenuItem[3],miconMax,
                KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.ALT_MASK),false);

        // utworzenie obiektow MenuItem dla helpMenu
        helpContextMenuItem = createJMenuItem(labelHelpMenuItem[0],
                mIconHelpContext,KeyStroke.getKeyStroke(KeyEvent.VK_H,
                        ActionEvent.ALT_MASK),true);
        helpAboutMenuItem = createJMenuItem(labelHelpMenuItem[1],mIconAbout,
                KeyStroke.getKeyStroke(KeyEvent.VK_I,ActionEvent.ALT_MASK),true);

        // dodanie utworzonych elementow menu dopaska menu JMenuBar
        fileMenu.add(fileExitMenuItem);
        calcMenu.add(sumaMenuItem);
        calcMenu.add(sredniaMenuItem);
        calcMenu.add(minMenuitem);
        calcMenu.add(maxMenuitem);
        helpMenu.add(helpContextMenuItem);
        helpMenu.add(helpAboutMenuItem);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        menuBar.add(calcMenu);
        menuBar.add(helpMenu);
        this.setJMenuBar(menuBar);
    }

    private void createGUI() {

        // Utworzenie paska narzedziowego
        jToolBar = new JToolBar();
        createJToolBar(jToolBar);
        // Utworzenie panelu informacyjnego umieszczonego na dole okna
        InfoBottomPanel infoPanel = new InfoBottomPanel();
        // Utworzenie panelu centralnego
        CenterPanel centerPanel = new CenterPanel();
        centerPanel.setVisible(true);

        conPane.add(jToolBar, BorderLayout.NORTH);
        conPane.add(infoPanel, BorderLayout.SOUTH);
        conPane.add(centerPanel, BorderLayout.CENTER);
    }

    public void createJToolBar(JToolBar cjtb) {
        cjtb.setFloatable(false);
        cjtb.add(Box.createHorizontalStrut(5));

        // Utworzenie przycisk�w paska narz�dziowego
        jtbExit = createJButtonToolBar(tooltipFileMenu[0],iconExit,true);
        jtbSave = createJButtonToolBar(tooltipFileMenu[1],iconSave,true);

        jtbSuma = createJButtonToolBar(tooltipCalcMenu[0],iconSuma,true);
        jtbSrednia = createJButtonToolBar(tooltipCalcMenu[1],iconSrednia,true);
        jtbMin = createJButtonToolBar(tooltipCalcMenu[2],iconMin,true);
        jtbMax = createJButtonToolBar(tooltipCalcMenu[3],iconMax,true);

        jtbHelp = createJButtonToolBar(tooltipHelpMenu[0],iconHelpContext,true);
        jtbAbout = createJButtonToolBar(tooltipHelpMenu[1],iconAbout,true);

        // dodanie przycisk�w do paska narz�dziowego
        cjtb.add(jtbExit);
        cjtb.add(jtbSave);
        cjtb.addSeparator();
        cjtb.add(jtbSuma);
        cjtb.add(jtbSrednia);
        cjtb.add(jtbMin);
        cjtb.add(jtbMax);
        cjtb.addSeparator();
        cjtb.add(jtbHelp);
        cjtb.add(jtbAbout);
    }

    public Icon createMyIcon(String file) {
        String name = "/grafika/"+file;
        Icon icon = new ImageIcon(getClass().getResource(name));
        return icon;
    }

    public JMenu createJMenu(String name, int keyEvent, boolean enable) {
        JMenu jMenu = new JMenu(name);
        jMenu.setMnemonic(keyEvent);
        jMenu.setEnabled(enable);
        return jMenu;
    }

    public JMenuItem createJMenuItem(String name, Icon icon, KeyStroke key,
                                     boolean enable) {
        JMenuItem jMI;
        if(icon != null)
            jMI = new JMenuItem(name,icon);
        else jMI = new JMenuItem(name);
        jMI.setAccelerator(key);
        jMI.addActionListener(this);
        jMI.setEnabled(enable);
        return jMI;
    }

    public JButton createJButtonToolBar(String tooltip,Icon icon,boolean enable) {
        JButton jb = new JButton("",icon);
        jb.setToolTipText(tooltip);
        jb.addActionListener(this);
        jb.setEnabled(enable);
        return jb;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        CenterPanel.changeData();
    }

    public static void main(String args[])
    {
        //MyLogger.writeLog("INFO","Start Aplikacji");
        System.out.println("Start Aplikacji");
        MyWindow f = new MyWindow();
        f.setVisible(true);
        //CenterPanel.changeData();//Do testów
    }
}