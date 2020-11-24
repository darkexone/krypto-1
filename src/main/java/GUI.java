import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

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

    String klucz1;
    String klucz2;
    public byte [] tekst;
    public byte [] szyfr;
    byte [] dlugoscTekstu;
    byte [] dlugoscSzyfrogramu;
    String pathToFile;

    JButton szyfrujButton;
    JButton deszyfrujButton;
    int tekstMode;
    int szyfrMode;

    JFrame fileSelect = new JFrame();
    FileDialog fd = new FileDialog(fileSelect, "Choose a file", FileDialog.LOAD);

    JFrame fileSave = new JFrame();
    //JFileChooser

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

    public byte [] wczytajPlik () {

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
        //area.requestFocus();
        return text;
    }

    public void zapiszPlik(byte [] outputBinary) {
        fd.setVisible(true);
        String fileSource = fd.getDirectory() + fd.getFile();
        File outputBinaryFile = new File(fileSource);
        FileOutputStream outputFosBinary = null;
        try {
            outputFosBinary = new FileOutputStream(outputBinaryFile);
            outputFosBinary.write(outputBinary);
            outputFosBinary.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void insertUpdate(DocumentEvent e)  {
        Document s = e.getDocument();

        if (s == tekstTextArea.getDocument()) {
            //System.out.println("insertUpdate tekst");
            tekstMode = 0;
        }
        else if (s == szyfrogramTextArea.getDocument()) {
            //System.out.println("insertUpdate szyfr");
            szyfrMode = 0;
        }
    }

    public void removeUpdate(DocumentEvent e) {
        insertUpdate(e);
    }

    public void changedUpdate(DocumentEvent e) {
        insertUpdate(e);
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexToBytes(String tekst)
    {
        if (tekst == null) { return null;}
        else if (tekst.length() < 2) { return null;}
        else { if (tekst.length()%2!=0)tekst+='0';
            int dl = tekst.length() / 2;
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

    private void printByteArray(byte[] bytes) {
        for (byte b1 : bytes) {
            String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
            System.out.print(s1 + " ");
        }
        System.out.println();
    }

    public static byte[] hexStringToByteArray(String hex) {
        int l = hex.length();
        byte[] data = new byte[l / 2];
        for (int i = 0; i < l; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] asBytes (String s) {
        String tmp;
        byte[] b = new byte[s.length() / 2];
        int i;
        for (i = 0; i < s.length() / 2; i++) {
            tmp = s.substring(i * 2, i * 2 + 2);
            b[i] = (byte)(Integer.parseInt(tmp, 16) & 0xff);
        }
        return b;                                            //return bytes
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();

        if (s == "DESZYFRUJ" || s == "SZYFRUJ") { //check key
            String kluczString = String.format("%x", new BigInteger(1, kluczTextField.getText().getBytes(Charset.defaultCharset())));
            if (kluczString.length() != 32) {
                JOptionPane.showMessageDialog(null,
                        "Podano niepoprawny klucz, wpisana wartość musi składać się z dwóch kluczy o długości 8 bajtów każdy (po 64 BITY).\nObecnie ma długość: "
                                + kluczString.length()*2,"Ostrzeżenie",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else {
                klucz1 = kluczString.substring(0,16);
                klucz2 = kluczString.substring(16,32);
            }
        }


        switch (s) {

            case "DESZYFRUJ": {
                int liczbaBajtow = 0;
                if (szyfrMode == 0) {
                    szyfr = hexToBytes(szyfrogramTextArea.getText().substring(1));
                    liczbaBajtow = Integer.valueOf(szyfrogramTextArea.getText().substring(0,1));
                }
                else {
                    liczbaBajtow = (((Byte)dlugoscSzyfrogramu[0]).intValue() - 48);
                }

                if (szyfr == null || szyfr.length == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Nie podano szyfru","Ostrzeżenie",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                //dlugoscSzyfrogramu = hexToBytes(String.format("%x", new BigInteger(1, szyfr)).substring(0, 2));


                System.out.println(liczbaBajtow);

                Key kluczyk = new Key(hexToBytes(klucz1));
                Subkeys podklucze = new Subkeys(kluczyk);

                Key kluczyk2 = new Key(hexToBytes(klucz2));
                Subkeys podklucze2 = new Subkeys(kluczyk2);

                DES3 decryptor = new DES3(podklucze,podklucze2);
                byte [] decryptionResult = decryptor.decryption(szyfr);

                if (szyfrMode == 0) {
                    String result = new String(decryptionResult);
                    tekstTextArea.setText(result);
                    break;
                }
                byte [] output = Arrays.copyOfRange(decryptionResult,0, decryptionResult.length-liczbaBajtow);
                zapiszPlik(output);
                break;
            }

            case "SZYFRUJ": {

                if (tekstMode == 0) {
                    tekst = hexToBytes(String.format("%x", new BigInteger(1, tekstTextArea.getText().getBytes(Charset.defaultCharset()))));
                    }

                if (tekst == null || tekst.length == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Nie podano tekstu jawnego","Ostrzeżenie",
                            JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                Key kluczyk = new Key(hexToBytes(klucz1));
                Subkeys podklucze = new Subkeys(kluczyk);

                Key kluczyk2 = new Key(hexToBytes(klucz2));
                Subkeys podklucze2 = new Subkeys(kluczyk2);

                DES3 encryptor = new DES3(podklucze,podklucze2);
                byte [] encryptionResult = encryptor.encryption(tekst);

                String bytesToDeleteString = String.valueOf(8 - (tekst.length % 8));

                if (tekstMode == 0) {
                    String result = bytesToHex(encryptionResult);
                    szyfrogramTextArea.setText(bytesToDeleteString+result);
                    break;
                }

                byte [] bytesToDeleteByte = hexToBytes(String.format("%x", new BigInteger(1, bytesToDeleteString.getBytes())));
                fd.setVisible(true);
                String fileSource = fd.getDirectory() + fd.getFile();

                File outputFile = new File(fileSource);
                FileOutputStream outputFos = null;
                try {
                    outputFos = new FileOutputStream(outputFile);
                    outputFos.write(bytesToDeleteByte);
                    outputFos.write(encryptionResult);
                    outputFos.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                break;
            }

            case "Wybierz plik z tekstem":
            {
                tekst = wczytajPlik();
                tekstMode = 1;
                break;
            }

            case "Wybierz plik z szyfrem":
            {
                //szyfr = String.format("%x", new BigInteger(1, wczytajPlik())).getBytes(Charset.defaultCharset());
                byte [] szyfrFile = wczytajPlik();
                dlugoscSzyfrogramu = GUI.hexToBytes(String.format("%x", new BigInteger(1, szyfrFile)).substring(0, 2));
                szyfr = hexToBytes(String.format("%x", new BigInteger(1, szyfrFile)).substring(2));
                szyfrMode = 1;
                break;
            }
        }
    }
}