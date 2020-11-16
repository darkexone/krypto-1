public class Key {

    private byte [] key64 = new byte [8];
    private byte [] key56 = new byte [7];

    public Key(byte [] key) {
        set64Key(key);
        set56Key();
    }

    private void printByteArray(byte[] bytes) {
        for (byte b1 : bytes) {
            String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
            System.out.print(s1 + " ");
        }
        System.out.println();
    }

    private void set56Key() {
        for (int i = 0; i < 56; i++) {
                setBit(key56, i, getBit(key64, PC1[i]));
        }
    }

    public static int getBit(byte[] data, int pos) {
        int posByte = pos / 8;
        int posBit = pos % 8;
        byte valByte = data[posByte];
        int valInt = valByte >> ( 7 - posBit) & 1;
        return valInt;
    }

    public static void setBit(byte[] data, int pos, int oneOrZero) {
        int posByte = pos / 8;
        int posBit = pos % 8;
        if (oneOrZero == 1) {               // set 1
            data[posByte] |= (1 << posBit); //TODO XDDDDDDDDDDDDDDD
        }
        else {                              // set 0
            data[posByte] &= ~(1 << posBit);
        }
    }


    public byte[] get56Key() {
        return key56;
    }

    public byte[] get64Key() {
        return key64;
    }

    public void set64Key(byte[] key) {
        key64 = key;
    }



    final byte[] PC1 = {57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36, 63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
}
