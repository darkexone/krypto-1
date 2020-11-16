public class Subkeys {

    byte [][] subKeys = new byte[16][6];
    byte [] c = new byte[4];
    byte [] d = new byte[4];

    public Subkeys(Key mainKey) {

        for (int i = 0; i < 28; i++) {
            Key.setBit(this.c, i, Key.getBit(mainKey.get56Key(), i));
            Key.setBit(this.d, i, Key.getBit(mainKey.get56Key(), 28 + i));
        }
<<<<<<< Updated upstream
        System.out.println("wartosci kolejnych podkluczy:");
=======
        //System.out.println("wartosci kolejnych podkluczy:");
>>>>>>> Stashed changes
        for (int i = 0; i < 16; i++) {
            setSubKey(getShiftedAndJoinedSubKey56(i),i);
        }
    }

    public static byte[] rotateLeft(byte[] in, int len, int step)
    {
        byte[] out = new byte[(len - 1) / 8 + 1];
        for (int i = 0; i < len; i++)
        {
            int val = Key.getBit(in, (i + step) % len);
            Key.setBit(out, i, val);
        }
        return out;
    }

    private void printByteArray(byte[] bytes) {
        for (byte b1 : bytes) {
            String s1 = String.format("%8s", Integer.toBinaryString(b1 & 0xFF)).replace(' ', '0');
            System.out.print(s1 + " ");
        }
        System.out.println();
    }

    private byte[] joinBlocks(byte[] a, int aLen, byte[] b, int bLen)
    {
        int numOfBytes = (aLen + bLen - 1) / 8 + 1;
        byte[] out = new byte[numOfBytes];
        int j = 0;
        for (int i = 0; i < aLen; i++)
        {
            int val = Key.getBit(a, i);
            Key.setBit(out, j, val);
            j++;
        }
        for (int i = 0; i < bLen; i++)
        {
            int val = Key.getBit(b, i);
            Key.setBit(out, j, val);
            j++;
        }
        return out;
    }

    private byte [] getShiftedAndJoinedSubKey56(int round) {
        byte[] out;// = new byte[7];
        byte[] cShifted = rotateLeft(c,28,1);
        byte[] dShifted = rotateLeft(d,28,1);

        //for (int i = 0; i < 28; i++) {
            //Key.setBit(out, i, Key.getBit(cShifted, i));
            //Key.setBit(out,i + 28, Key.getBit(dShifted, i));
        //}

        out = joinBlocks(cShifted,28,dShifted,28);

        this.c = cShifted;
        this.d = dShifted;
        //System.out.println("podklucz po rundzie " + (round + 1) + ":");
<<<<<<< Updated upstream
        printByteArray(out);
=======
        //printByteArray(out);
>>>>>>> Stashed changes
        return out;
    }

    private void setSubKey(byte [] subKey56, int i) {
        for (int j = 0; j < 48; j++) {
            Key.setBit(subKeys[i], j, Key.getBit(subKey56, PC2[j] - 1));
        }
    }

    public byte[] getSubKey(int i) {
        return subKeys[i];
    }

    final byte[] PC2 = {14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2, 41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
    final byte[] SHIFTS = {1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
}
