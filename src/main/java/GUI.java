import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

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

    public byte [] klucz;
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



    @Override
    public void actionPerformed(ActionEvent e) {

        String s = e.getActionCommand();

        if (s == "DESZYFRUJ" || s == "SZYFRUJ") {
            if (kluczText.getText().length()!=16) {
                JOptionPane.showMessageDialog(null,
                        "Podano niepoprawny klucz, wpisana wartość musi składać się z dwóch kluczy o długości 8 bajtów.\nObecnie ma długość: "
                                + kluczText.getText().length(),"Ostrzeżenie",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            else {
                try {
                    klucz = kluczText.getText().getBytes("UTF-8");
                    System.out.println(new String(klucz));
                } catch (UnsupportedEncodingException unsupportedEncodingException) {
                    unsupportedEncodingException.printStackTrace();
                }
            }
        }

        switch (s) {
            case "DESZYFRUJ": {
                if (mode == 1) {
                    return;
                }
                else {
                    try {
                        szyfr = szyfrogramTextArea.getText().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                    }
                }
                //System.out.println(new String(szyfr));
                return;
            }
            case "SZYFRUJ": {
                if (mode != 0) {
                    //System.out.println("true " + new String(tekst));
                    return;
                }
                else {
                    try {
                        tekst = tekstTextArea.getText().getBytes("UTF-8");
                    } catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                    }
                }

                //System.out.println("false " + new String(tekst));
                return;
            }

            case "Wybierz plik z tekstem":
            {
                tekst = wczytajPlik(tekstTextArea);
                System.out.println(new String(tekst));
                return;
            }
            case "Wybierz plik z szyfrem":
            {
                szyfr = wczytajPlik(szyfrogramTextArea);
                System.out.println(new String(szyfr));
                return;
            }
        }
    }
}