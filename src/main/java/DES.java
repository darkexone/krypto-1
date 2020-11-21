import java.util.ArrayList;

public class DES {

    byte [][] block64bit;
    byte [][][] blocks32bit;
    Subkeys podklucze;
    byte [] tekst;

    public DES(Subkeys podklucze, byte [] tekst) {
        this.podklucze = podklucze;
        this.tekst = tekst;

        int pom = (tekst.length / 8) + 1;
        block64bit = new byte[pom][8];
        blocks32bit = new byte[pom][2][4];

        for (int i = 0; i < pom; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i * 8) + j < tekst.length) {
                    block64bit[i][j] = tekst[(i * 8) + j];
                }
            }
        }

        for (int i = 0; i < pom; i++) {
            byte [] block64bit_pom = new byte[8];
            for (int j = 0; j < 64; j++) {
                Key.setBit(block64bit_pom, j, Key.getBit(block64bit[i], IP[i] - 1));
            }
            block64bit[i] = block64bit_pom;
        }

        //for (int = 0; )

        for (int i = 0; i < pom; i++) {
            byte [] r0_pom = new byte[6];
            for (int j = 0; j < 64; j++) {
                Key.setBit(r0_pom, j, Key.getBit(block64bit[i], IP[i] - 1));
            }
            block64bit[i] = block64bit_pom;
        }

    }

    void runda (byte [] l, byte [] r, int runda) {
        byte [] array48 = new byte[6];
        for (int j = 0; j < 48; j++) {
            Key.setBit(array48, j, Key.getBit(r, PC3[j] - 1));
            int ksor;
            if (Key.getBit(array48, j) != Key.getBit(podklucze.getSubKey(runda),j)) {
                ksor = 1;
            }
            else {
                ksor = 0;
            }
            Key.setBit(array48, j, ksor);
        }

    }

    byte[] IP = new byte[] {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9,  1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7};

    byte[] IPplus = new byte[] {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25};

    public byte[] PC3 = new byte[] {
            32, 1,   2,  3,  4,  5,
            4,  5,   6,  7,  8,  9,
            8,  9,  10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1};


}
