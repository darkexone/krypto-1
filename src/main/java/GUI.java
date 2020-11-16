import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
<<<<<<< Updated upstream
=======
import java.nio.charset.StandardCharsets;
>>>>>>> Stashed changes

public class GUI implements ActionListener, DocumentListener {
    JFrame frame;
    JPanel panel;

    JLabel kluczLabel;
    JTextField kluczText;

    JLabel tekstLabel;
    JScrollPane tekstScrollPane;
    JTextArea tekstTextArea;
    JButton selectTekstFileButton;

    JLabel szyfrogramLabel;
    JScrollPane szyfrogramScrollPane;
    JTextArea szyfrogramTextArea;
    JButton selectSzyfrFileButton;

    public byte [] klucz;// = new byte[8];
    public byte [] tekst;// = null;
    public byte [] szyfr;// = null;
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

        kluczText = new JTextField(20);
        kluczText.setBounds(103,20,500,25);
        panel.add(kluczText);

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
            //System.out.println(new String(text));
            //area.read( br, null );
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
<<<<<<< Updated upstream
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
=======

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 3];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 3] = HEX_ARRAY[v >>> 4];
            hexChars[j * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[j * 3 + 2] = ' ';
>>>>>>> Stashed changes
        }
        return new String(hexChars);
    }

<<<<<<< Updated upstream
    public static byte[] hexToBytes(String tekst)
    {
        if (tekst == null) { return null;}
        else if (tekst.length() < 2) { return null;}
        else { if (tekst.length()%2!=0)tekst+='0';
            int dl = tekst.length() / 2;
            byte[] wynik = new byte[dl];
            for (int i = 0; i < dl; i++)
            { try{
                wynik[i] = (byte) Integer.parseInt(tekst.substring(i * 2, i * 2 + 2), 16);
            }catch(NumberFormatException e){JOptionPane.showMessageDialog(null, "Problem z przekonwertowaniem HEX->BYTE.\n Sprawdź wprowadzone dane.", "Problem z przekonwertowaniem HEX->BYTE", JOptionPane.ERROR_MESSAGE); }
            }
            return wynik;
        }
    }

=======
    private void printByteArray(byte[] bytes) {
        for (byte b1 : bytes) {
            String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
            System.out.print(s1 + " ");
        }
        System.out.println();
    }
>>>>>>> Stashed changes


    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();

        if (s == "DESZYFRUJ" || s == "SZYFRUJ") {
            if (kluczText.getText().length()!=8) {//TODO POPRAWIC ZNOWU NA ROZNY
                JOptionPane.showMessageDialog(null,
                        "Podano niepoprawny klucz, wpisana wartość musi składać się z dwóch kluczy o długości 8 bajtów każdy.\nObecnie ma długość: "
                                + kluczText.getText().length(),"Ostrzeżenie",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else {
<<<<<<< Updated upstream
                try {
                    klucz = kluczText.getText().getBytes("UTF-8");
=======
                //try {
                    klucz = kluczText.getText().getBytes(StandardCharsets.UTF_8);
>>>>>>> Stashed changes
                    //klucz = hexToBytes(kluczText.getText().toString());
                    //for (int i = 0; i < 16; i++) {
                        //System.out.println("klucz["+i+"]="+klucz[i]);
                    //}
<<<<<<< Updated upstream
                    System.out.println(new String(klucz));
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                }
=======
                    //System.out.println(new String(klucz));
                //} catch (UnsupportedEncodingException unsupportedEncodingException) {
                //    unsupportedEncodingException.printStackTrace();
                //}
>>>>>>> Stashed changes
            }
        }



        switch (s) {

            case "DESZYFRUJ": {
                if (mode == 1) {
                    break;
                }
                else {
                        szyfr = szyfrogramTextArea.getText().getBytes(Charset.defaultCharset());
                }
                break;
            }

            case "SZYFRUJ": {
                if (mode == 1) {
                    break;
                }
                else {
                    tekst = tekstTextArea.getText().getBytes(Charset.defaultCharset());
                }
                for (int i = 0; i < 64; i++) {
                    if (i%2 == 0) {
                        //Key.setBit(klucz, i,1);
                    }
                    else {
                        //Key.setBit(klucz, i,1);
                    }
                }
                //klucz = hexToBytes(kluczText.getText());//TODO to powinno działać
                Key kluczyk = new Key(klucz);
                Subkeys podklucze = new Subkeys(kluczyk);
                System.out.println("pobrany key: " + new String(klucz));
                System.out.println("podklucz 64: " + bytesToHex(kluczyk.get64Key()));
                System.out.println("podklucz 56: " + bytesToHex(kluczyk.get56Key()));
                System.out.println("podklucz 64 ma dlugosc: " + kluczyk.get64Key().length);
                System.out.println("podklucz 56 ma dlugosc: " + kluczyk.get56Key().length);
                for (int i = 0; i < 9; i++) {
                    System.out.println("podklucz  " + (i + 1) + ": " + bytesToHex(podklucze.getSubKey(i)));
                }
                for (int i = 9; i < 16; i++) {
                    System.out.println("podklucz " + (i + 1) + ": " + bytesToHex(podklucze.getSubKey(i)));
                }

<<<<<<< Updated upstream
=======
                Key kluczyk = new Key(klucz);
                Subkeys podklucze = new Subkeys(kluczyk);

                System.out.println("\n\nwpisany tekst klucza z JText:  " + new String(klucz));
                //klucz = hexToBytes(kluczText.getText());//TODO to powinno działać
                System.out.println("HEX klucza z JText:            " + bytesToHex(klucz));
                System.out.print("BIN klucza z JText:            ");
                printByteArray(klucz);
                System.out.print("BIN key64 po pobraniu:         ");
                printByteArray(kluczyk.get64Key());
                System.out.print("BIN key56 po set56Key():       ");
                printByteArray(kluczyk.get56Key());

                //System.out.println("\npobrany key: " + new String(klucz));
                System.out.println("\nHEX key64:        " + bytesToHex(kluczyk.get64Key()));
                System.out.println("HEX key56:        " + bytesToHex(kluczyk.get56Key()));
                System.out.println("key64 ma dlugosc: " + kluczyk.get64Key().length);
                System.out.println("key56 ma dlugosc: " + kluczyk.get56Key().length + "\n");
                for (int i = 0; i < 9; i++) {
                    System.out.print("podklucz  " + (i + 1) + ": " + bytesToHex(podklucze.getSubKey(i)) + " ");
                    printByteArray(podklucze.getSubKey(i));
                }
                for (int i = 9; i < 16; i++) {
                    System.out.print("podklucz " + (i + 1) + ": " + bytesToHex(podklucze.getSubKey(i)) + " ");
                    printByteArray(podklucze.getSubKey(i));
                }

>>>>>>> Stashed changes
                break;
            }

            case "Wybierz plik z tekstem":
            {
                tekst = wczytajPlik(tekstTextArea);
                break;
            }

            case "Wybierz plik z szyfrem":
            {
                szyfr = wczytajPlik(szyfrogramTextArea);
                break;
            }
        }
    }
}