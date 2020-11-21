import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.Charset;

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


        String tekst = "123456781";
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
}