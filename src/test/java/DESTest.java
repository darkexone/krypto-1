import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.nio.charset.Charset;

public class DESTest {

    @Test
    public void DESTest() {

        String kluczString = "☺☻FF";
        byte [] klucz;

        System.out.println("Charset: " + Charset.defaultCharset());
        //klucz = hexToBytes(("7F7F7F7F7F7F7F7F"));
        //klucz = "☺☻♥♦♣♠•◘".getBytes(Charset.defaultCharset());

        String kluczHexString = String.format("%x", new BigInteger(1, kluczString.getBytes(Charset.defaultCharset())));
        klucz = GUI.hexToBytes((kluczHexString));

        String tekst = "12345678";
        String tekstHexString = String.format("%x", new BigInteger(1, tekst.getBytes(Charset.defaultCharset())));

        Key kluczyk = new Key(klucz);
        Subkeys podklucze = new Subkeys(kluczyk);
        DES DES = new DES(podklucze, GUI.hexToBytes(tekstHexString));


    }
}