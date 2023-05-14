package app;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import org.freixas.jcalendar.JCalendarCombo;
/**
 * Program <code>MyWindow</code>
 * Klasa <code>CenterPanel</code> definiujaca centralny panel
 * aplikacji zawierajacy glowna funkcjonalnosc aplikacji
 * @author
 * @version 1.0	15/12/2010
 */
public class CenterPanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel northPanel, southPanel;
    private JTextField paramTextField;
    private JTextArea resultTextArea;
    private JLabel paramLabel;
    private JButton submitButton;
    // deklaracja zmiennej typu JCalendarCombo o nazwie jccData
    private JCalendarCombo jccData;
    private TitledBorder titledBorder;
    private Border blackLine;
    /**
     * Konstruktor bezparametrowy klasy <CODE>InfoBottomPanel<CODE>
     */
    public CenterPanel() {
        createGUI();
    }
    /**
     * Metoda tworzacaca graficzny interfejs uzyytkownika
     */
    public void createGUI() {
        this.setLayout(new GridLayout(2,1,5,5));

        // Utworzenie panelu z paramtrami i wynikiem
        northPanel = createNorthPanel();
        southPanel = createSouthPanel();

        // Utworzenie obiektow TextField
        this.add(northPanel);
        this.add(southPanel);
    }
    /**
     * Metoda tworzaca panel z parametrami
     */
    public JPanel createNorthPanel() {
        JPanel jp = new JPanel();
        blackLine = BorderFactory.createLineBorder(Color.gray);
        titledBorder = BorderFactory.createTitledBorder(blackLine,
                "Parmetry wejsciowe");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        jp.setBorder(titledBorder);
        jp.setLayout(new FlowLayout());

        paramLabel = new JLabel("Parametr 1");
        paramTextField = new JTextField(10);
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);

        // utworzenie instacji obiektu JCalendar
        jccData = new JCalendarCombo(
                Calendar.getInstance(),
                Locale.getDefault(),
                JCalendarCombo.DISPLAY_DATE,
                false
        );
        // ustawienie formatu daty
        jccData.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        jp.add(paramLabel);
        jp.add(paramTextField);
        jp.add(jccData);
        jp.add(submitButton);
        return jp;
    }
    /**
     * Metoda tworzaca panel z wynikami
     */
    public JPanel createSouthPanel() {
        JPanel jp = new JPanel();
        blackLine = BorderFactory.createLineBorder(Color.gray);
        titledBorder = BorderFactory.createTitledBorder(blackLine,
                "Uzyskany rezultat");
        titledBorder.setTitleJustification(TitledBorder.CENTER);
        jp.setBorder(titledBorder);
        jp.setLayout(new BorderLayout());

        resultTextArea = new JTextArea();
        // zawijanie wierszy
        resultTextArea.setLineWrap(true);
        // edycja pola TextArea
        // resulTextAreat.setEditable(false);
        resultTextArea.append("Start aplikacji\n");
        jp.add(new JScrollPane(resultTextArea),BorderLayout.CENTER);
        return jp;
    }
    /**
     * Metoda obsługujaca zdarzenie akcji
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == submitButton) {
            String param = paramTextField.getText();
            // Pobranie daty do obiektu typu String
            // Miesiace liczone sa od 0 wiec trzeba dodac 1
            Calendar cal = jccData.getCalendar();
            String data = ""+cal.get(Calendar.YEAR)+"-";
            int miesiac = cal.get(Calendar.MONTH)+1;
            if(miesiac <= 9) data = data+"0"+String.valueOf(miesiac)+"-";
            else data = data+String.valueOf(miesiac)+"-";
            int dzien = cal.get(Calendar.DAY_OF_MONTH);
            if(dzien <= 9) data = data+"0"+String.valueOf(dzien);
            else data = data+String.valueOf(dzien);

            // zapisanie danych w polu TextArea
            resultTextArea.append("Parametr: "+param+"\n");
            resultTextArea.append("Data: "+data+"\n");
        }
    }
    /**
     * Metoda okreslajaca wartosci odstepow od krawedzi panelu
     * (top,left,bottom,right)
     */
    public Insets getInsets() {
        return new Insets(5,10,10,10);
    }
}
