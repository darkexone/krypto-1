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

    @Test
    public void DES3Test () {
        String kluczString = "欥ｆ씶秛ᖐꙭ�檛꺬舘鏆킱欥ｆ씶秛ᖐꙭ�檛꺬舘鏆킱";
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

        String result = bytesToHex(DES3.getResult());

        DES DES4 = new DES(subkeys1, GUI.hexToBytes(result), 1);
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
    }
}