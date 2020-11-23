import org.junit.jupiter.api.Test;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

public class DESTest {

    private void printByteArray(byte[] bytes) {
        for (byte b1 : bytes) {
            String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
            System.out.print(s1 + " ");
        }
        System.out.println();
    }

    @Test
    public void DESTest() {

        String kluczString = "FFFFFFFFFFFFFFFF";
        byte [] klucz;
        klucz = GUI.hexToBytes((kluczString));
        System.out.println("klucz:");
        printByteArray(klucz);

        String klucz2String = "FFFFFFFFFFFFFFFF";
        byte [] klucz2;
        klucz2 = GUI.hexToBytes((klucz2String));


        String tekst = "12345678";
        String tekstHexString = String.format("%x", new BigInteger(1, tekst.getBytes(Charset.defaultCharset())));

        Key kluczyk = new Key(klucz);
        Subkeys podklucze = new Subkeys(kluczyk);
        DES DES = new DES(podklucze, GUI.hexToBytes(tekstHexString), 0);

        Key kluczyk2 = new Key(klucz2);
        Subkeys podklucze2 = new Subkeys(kluczyk2);
        DES DES2 = new DES(podklucze2, DES.getResult(), 1);

        System.out.println("dlugosc getResult: " + DES.getResult().length);
        printByteArray(GUI.hexToBytes(tekstHexString));
        printByteArray(DES.getResult());
        printByteArray(DES2.getResult());
    }

    private static final byte[] HEX_ARRAY = "0123456789ABCDEF".getBytes(StandardCharsets.US_ASCII);
    public static String bytesToHex(byte[] bytes) {
        byte[] hexChars = new byte[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars, StandardCharsets.UTF_8);
    }

    public byte [] wczytajPlik (String filesource) {

        String pathToFile = filesource;
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
        return text;
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

    @Test
    public void DES3Test () {
        String kluczString = "欥ｆ씶秛ᖐx";
        String kluczHexString = String.format("%x", new BigInteger(1, kluczString.getBytes(Charset.defaultCharset())));
        System.out.println("kluczHexString: " +  kluczHexString + "\ndlugosc: " + kluczHexString.length());
        String klucz1String = kluczHexString.substring(0,16);
        String klucz2String = kluczHexString.substring(16,32);
        System.out.println("klucz1String: " + klucz1String);
        System.out.println("klucz2String: " + klucz2String);
        String tekst = "欥ｆ씶秛ᖐꙭ�檛꺬舘xD";
        String tekstHexString = String.format("%x", new BigInteger(1, tekst.getBytes(Charset.defaultCharset())));

        //String klucz1HexString = String.format("%x", new BigInteger(1, klucz1String.getBytes(Charset.defaultCharset())));
        //String klucz2HexString = String.format("%x", new BigInteger(1, klucz2String.getBytes(Charset.defaultCharset())));
        System.out.print("klucz1StringToBytes: ");printByteArray(GUI.hexToBytes(klucz1String));
        System.out.print("klucz2StringToBytes: ");printByteArray(GUI.hexToBytes(klucz2String));
        System.out.println();

        Key key1 = new Key(GUI.hexToBytes(klucz1String));
        Subkeys subkeys1 = new Subkeys(key1);

        Key key2 = new Key(GUI.hexToBytes(klucz2String));
        Subkeys subkeys2 = new Subkeys(key2);

        DES DES1 = new DES(subkeys1, GUI.hexToBytes(tekstHexString), 0);
        DES DES2 = new DES(subkeys2, DES1.getResult(), 1);
        DES DES3 = new DES(subkeys1, DES2.getResult(), 0);

        String result = new String(bytesToHex(DES3.getResult()));
        byte [] byteArray = hexStringToByteArray(result);
        result = new String(byteArray);

        DES DES4 = new DES(subkeys1, byteArray, 1);
        DES DES5 = new DES(subkeys2, DES4.getResult(), 0);
        DES DES6 = new DES(subkeys1, DES5.getResult(), 1);


        System.out.print("TEKST: ");printByteArray(GUI.hexToBytes(tekstHexString));

        System.out.print("\nDES1: ");printByteArray(DES1.getResult());
        System.out.print("DES2: ");printByteArray(DES2.getResult());
        System.out.print("DES3: ");printByteArray(DES3.getResult());


        System.out.print("DES4: ");printByteArray(DES4.getResult());
        System.out.print("DES5: ");printByteArray(DES5.getResult());
        System.out.print("DES6: ");printByteArray(DES6.getResult());


        String result2 = new String (DES6.getResult());
        System.out.print("\nTEKST JAWNY: " + tekst + "  BIN: ");printByteArray(GUI.hexToBytes(tekstHexString));
        System.out.print("WYNIK:       " + result2 + "  BIN: ");printByteArray(DES6.getResult());
        System.out.println("RESULT:    " + result);
    }


    @Test
    public void saveToFileTest () throws IOException {

        String kluczHexString = String.format("%x", new BigInteger(1, "欥ｆ씶秛ᖐx".getBytes(Charset.defaultCharset())));
        String tekstHexString = String.format("%x", new BigInteger(1, "葻zu㹩ⱂX֙蘺\uD802\uDC70".getBytes(Charset.defaultCharset())));

        String klucz1String = kluczHexString.substring(0,16);
        String klucz2String = kluczHexString.substring(16,32);

        Key key1 = new Key(GUI.hexToBytes(klucz1String));
        Subkeys subkeys1 = new Subkeys(key1);

        Key key2 = new Key(GUI.hexToBytes(klucz2String));
        Subkeys subkeys2 = new Subkeys(key2);

        System.out.println("dlugosckluczy:  " + kluczHexString.length());
        System.out.println("kluczHexString: " +  kluczHexString);
        System.out.println("\nklucz1String: " + klucz1String);
        System.out.println("klucz2String: " + klucz2String);
        System.out.print("\nklucz1StringToBytes: ");printByteArray(GUI.hexToBytes(klucz1String));
        System.out.print("klucz2StringToBytes: ");printByteArray(GUI.hexToBytes(klucz2String));System.out.println();



        //byte [] input = GUI.hexToBytes(tekstHexString);
        //byte [] input = wczytajPlik("./compare/delfina.jpg");
        byte [] input = wczytajPlik("./compare/is-5.pdf");
        DES DES1 = new DES(subkeys1, input, 0);
        DES DES2 = new DES(subkeys2, DES1.getResult(), 1);
        DES DES3 = new DES(subkeys1, DES2.getResult(), 0);

        String bytesToDelete = String.valueOf(8 - (input.length % 8));
        byte [] jajco = GUI.hexToBytes(String.format("%x", new BigInteger(1, bytesToDelete.getBytes())));


        File outputFile = new File("./files/outputEncryption");
        FileOutputStream outputFos = null;
        try {
            outputFos = new FileOutputStream(outputFile);
            outputFos.write(jajco);
            outputFos.write(DES3.getResult());
            outputFos.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        System.out.println("bytesToDelete: " + (8 - input.length % 8));
        printByteArray(jajco);

        byte [] plik1 = wczytajPlik("./files/outputEncryption");
        byte [] plik2 = GUI.hexToBytes(String.format("%x", new BigInteger(1, plik1)).substring(2));
        byte [] dlugoscByte = GUI.hexToBytes(String.format("%x", new BigInteger(1, plik1)).substring(0, 2));

        printByteArray(jajco);
        printByteArray(dlugoscByte);

        System.out.print("dlugoscByte[0]: ");System.out.println(((Byte)dlugoscByte[0]).intValue()-48);
        int liczbaBajtow = (((Byte)dlugoscByte[0]).intValue()-48);
        System.out.println("liczba bajtow:"+liczbaBajtow);


        DES DES4 = new DES(subkeys1, plik2, 1);
        DES DES5 = new DES(subkeys2, DES4.getResult(), 0);
        DES DES6 = new DES(subkeys1, DES5.getResult(), 1);


        //System.out.print("TEKST: ");printByteArray(GUI.hexToBytes(tekstHexString));

        //System.out.print("\nDES1: ");printByteArray(DES1.getResult());
        //System.out.print("DES2: ");printByteArray(DES2.getResult());
        //System.out.print("DES3: ");printByteArray(DES3.getResult());
//
//
        //System.out.print("DES4: ");printByteArray(DES4.getResult());
        //System.out.print("DES5: ");printByteArray(DES5.getResult());
        //System.out.print("DES6: ");printByteArray(DES6.getResult());

        byte [] result = DES6.getResult();
        System.out.println("input.length: " + input.length);

        byte [] output = Arrays.copyOfRange(DES6.getResult(),0, result.length-liczbaBajtow);
        System.out.println("output.length: " + output.length);

        //System.out.print("\nTEKST JAWNY: " + tekst + "  BIN: ");printByteArray(GUI.hexToBytes(tekstHexString));
        //System.out.print("WYNIK:       " + result2 + "  BIN: ");printByteArray(DES6.getResult());

        File outputBinary = new File("./compare/outputEncryption");
        File outputJPG = new File("./compare/outputEncryption.pdf");
        FileOutputStream outputFosBinary = null;
        FileOutputStream outputFosJPG = null;
        try {
            outputFosBinary = new FileOutputStream(outputBinary);
            outputFosBinary.write(output);
            outputFosBinary.close();
            outputFosJPG = new FileOutputStream(outputJPG);
            outputFosJPG.write(output);
            outputFosJPG.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}