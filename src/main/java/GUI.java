import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class GUI implements ActionListener, DocumentListener {
    JFrame frame;
    JPanel panel;

    JLabel kluczLabel;
    JTextField kluczTextField;

    JLabel tekstLabel;
    JScrollPane tekstScrollPane;
    JTextArea tekstTextArea;
    JButton selectTekstFileButton;

    JLabel szyfrogramLabel;
    JScrollPane szyfrogramScrollPane;
    JTextArea szyfrogramTextArea;
    JButton selectSzyfrFileButton;

    public byte [] klucz;
    public byte [] tekst;
    public byte [] szyfr;
    String pathToFile;

    JButton szyfrujButton;
    JButton deszyfrujButton;
    int mode;


    JFrame fileSelect = new JFrame();
    FileDialog fd = new FileDialog(fileSelect, "Choose a file", FileDialog.LOAD);


    public GUI () {

        frame = new JFrame();
        panel = new JPanel();

        frame.setSize(666,720);
        frame.setTitle("3-DES Encryption by 229836 229863 229928");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        frame.add(panel);
        panel.setLayout(null);

        // klucz
        kluczLabel = new JLabel("Klucz: ");
        kluczLabel.setBounds(23, 20, 80, 25);
        panel.add(kluczLabel);
        //TODO wczytanie klucza

        kluczTextField = new JTextField(20);
        kluczTextField.setBounds(103,20,500,25);
        panel.add(kluczTextField);

        // tekst
        tekstLabel = new JLabel("Tekst: ");
        tekstLabel.setBounds(23, 45, 80, 25);
        panel.add(tekstLabel);

        tekstScrollPane = new JScrollPane();
        tekstTextArea = new JTextArea();
        tekstTextArea.setBounds(103,50,500,250);
        tekstScrollPane.setBounds(103,50,500,250);
        tekstScrollPane.getViewport().setBackground(Color.white);
        tekstScrollPane.getViewport().add(tekstTextArea);
        panel.add(tekstScrollPane);

        selectTekstFileButton = new JButton("Wybierz plik z tekstem");
        selectTekstFileButton.setBounds(103, 310, 180, 30);
        panel.add(selectTekstFileButton);

        // szyfrogram
        szyfrogramLabel = new JLabel("Szyfrogram: ");
        szyfrogramLabel.setBounds(23, 355, 80, 25);
        panel.add(szyfrogramLabel);

        szyfrogramScrollPane = new JScrollPane();
        szyfrogramTextArea = new JTextArea();
        szyfrogramTextArea.setBounds(103,360,500,250);
        szyfrogramScrollPane.setBounds(103,360,500,250);
        szyfrogramScrollPane.getViewport().setBackground(Color.white);
        szyfrogramScrollPane.getViewport().add(szyfrogramTextArea);
        panel.add(szyfrogramScrollPane);

        selectSzyfrFileButton = new JButton("Wybierz plik z szyfrem");
        selectSzyfrFileButton.setBounds(103, 620, 180, 30);
        panel.add(selectSzyfrFileButton);


        // SZYFRUJ / DESZYFRUJ
        szyfrujButton = new JButton("SZYFRUJ");
        szyfrujButton.setBounds(363, 310, 180, 30);
        panel.add(szyfrujButton);

        deszyfrujButton = new JButton("DESZYFRUJ");
        deszyfrujButton.setBounds(363, 620, 180, 30);
        panel.add(deszyfrujButton);


        tekstTextArea.getDocument().addDocumentListener(this);
        szyfrogramTextArea.getDocument().addDocumentListener(this);
        szyfrujButton.addActionListener(this);
        deszyfrujButton.addActionListener(this);
        selectTekstFileButton.addActionListener(this);
        selectSzyfrFileButton.addActionListener(this);
        frame.setVisible(true);
    }


    public static void main(String [] args) {
        //GUI gui = new GUI();
        new GUI();
    }

    public byte [] wczytajPlik (JTextArea area) {

        fd.setVisible(true);
        String filesource = fd.getDirectory() + fd.getFile();
        pathToFile = filesource;
        FileReader reader = null;
        try {
            reader = new FileReader(filesource);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        byte [] text = null;
        try {

            FileInputStream is = new FileInputStream(filesource);
            text = new byte[is.available()];
            is.read(text);
            is.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        area.requestFocus();
        mode = 1;
        return text;
    }

    public void insertUpdate(DocumentEvent e)
    {

        //System.out.println("insertUpdate");
        mode = 0;
    }
    public void removeUpdate(DocumentEvent e)
    {
        //System.out.println("removeUpdate");
        mode = 0;
    }
    public void changedUpdate(DocumentEvent e)
    {
        //System.out.println("changedUpdate");
        mode = 0;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }

    public static byte[] hexToBytes(String tekst)
    {
        if (tekst == null) { return null;}
        else if (tekst.length() < 2) { return null;}
        else { if (tekst.length()%2!=0)tekst+='0';
            int dl = tekst.length() / 2;
            System.out.println("Dlugosc tekstu: " + tekst.length());
            byte[] wynik = new byte[dl];
            for (int i = 0; i < dl; i++)
            { try {
                wynik[i] = (byte) Integer.parseInt(tekst.substring(i * 2, i * 2 + 2), 16);
            } catch(NumberFormatException e){
                System.out.println("Problem z przekonwertowaniem HEX->BYTE.\n Sprawdź wprowadzone dane."); }
            }
            return wynik;
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();

        if (s == "DESZYFRUJ" || s == "SZYFRUJ") {
            String kluczHexString = String.format("%x", new BigInteger(1, kluczTextField.getText().getBytes(Charset.defaultCharset())));
            if (kluczHexString.length() != 16) {//TODO ZMIENIC NA 32 JAK BEDA DWA KLUCZE
                JOptionPane.showMessageDialog(null,
                        "Podano niepoprawny klucz, wpisana wartość musi składać się z dwóch kluczy o długości 8 bajtów każdy.\nObecnie ma długość: "
                                + kluczTextField.getText().length(),"Ostrzeżenie",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else {
                klucz = hexToBytes((kluczHexString));
            }
        }


        switch (s) {

            case "DESZYFRUJ": {
                if (mode == 1) {
                    break;
                }
                else {
                    szyfr = String.format("%x", new BigInteger(1, szyfrogramTextArea.getText().getBytes(Charset.defaultCharset()))).getBytes(Charset.defaultCharset());
                }
                break;
            }

            case "SZYFRUJ": {

                if (mode == 1) {
                    break;
                }
                else {
                    tekst = String.format("%x", new BigInteger(1, tekstTextArea.getText().getBytes(Charset.defaultCharset()))).getBytes(Charset.defaultCharset());

                }

                Key kluczyk = new Key(klucz);
                Subkeys podklucze = new Subkeys(kluczyk); //TODO przekazywać klucz jako poprawną tablicę bajtów
                break;
            }

            case "Wybierz plik z tekstem":
            {
                tekst = String.format("%x", new BigInteger(1, wczytajPlik(tekstTextArea))).getBytes(Charset.defaultCharset());
                break;
            }

            case "Wybierz plik z szyfrem":
            {
                szyfr = String.format("%x", new BigInteger(1, wczytajPlik(szyfrogramTextArea))).getBytes(Charset.defaultCharset());
                break;
            }
        }
    }
}