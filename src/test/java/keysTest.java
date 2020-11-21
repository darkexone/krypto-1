import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.Charset;

public class keysTest {

    private void printByteArray(byte[] bytes) {
        for (byte b1 : bytes) {
            String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
            System.out.print(s1 + " ");
        }
        System.out.println();
    }



    @Test
    public void keysTest() {

        String kluczString = "☺☻FF";
        byte [] klucz = new byte[8];
        for (int i = 0; i < 64; i++) {
            if (i % 2 == 0) {
                Key.setBit(klucz, i, 1);
            } else {
                Key.setBit(klucz, i, 1);
            }
        }

        //klucz = "☺☻♥♦♣♠•◘".getBytes(Charset.defaultCharset());
        String kluczHexString = String.format("%x", new BigInteger(1, kluczString.getBytes(Charset.defaultCharset())));
        System.out.println("Charset: " + Charset.defaultCharset());
        //klucz = hexToBytes(("7F7F7F7F7F7F7F7F"));
        klucz = GUI.hexToBytes((kluczHexString));

        Key kluczyk = new Key(klucz);
        Subkeys podklucze = new Subkeys(kluczyk);
        //kluczyk.set56Key(podklucz);

        System.out.println("\nWczytany klucz z getBytes():   " + new String(klucz));
        System.out.println("HEX klucz:                     " + GUI.bytesToHex(klucz));
        System.out.print("BIN klucz:                     ");
        printByteArray(klucz);
        System.out.print("BIN key64 po pobraniu:         ");
        printByteArray(kluczyk.get64Key());
        System.out.print("BIN key56 po set56Key():       ");
        printByteArray(kluczyk.get56Key());

        //System.out.println("\npobrany key: " + new String(klucz));
        System.out.println("\nHEX key64:        " + GUI.bytesToHex(kluczyk.get64Key()));
        System.out.println("HEX key56:        " + GUI.bytesToHex(kluczyk.get56Key()));
        System.out.println("key64 ma dlugosc: " + kluczyk.get64Key().length);
        System.out.println("key56 ma dlugosc: " + kluczyk.get56Key().length + "\n");
        for (int i = 0; i < 9; i++) {
            System.out.print("Podklucz  " + (i + 1) + ": " + GUI.bytesToHex(podklucze.getSubKey(i)) + " ");
            printByteArray(podklucze.getSubKey(i));
        }
        for (int i = 9; i < 16; i++) {
            System.out.print("Podklucz " + (i + 1) + ": " + GUI.bytesToHex(podklucze.getSubKey(i)) + " ");
            printByteArray(podklucze.getSubKey(i));
        }
    }
}