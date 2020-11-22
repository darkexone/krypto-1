public class DES3 {

    Subkeys subkeys1, subkeys2;

    public DES3(Subkeys subkeys1, Subkeys subkeys2) {
        this.subkeys1 = subkeys1;
        this.subkeys2 = subkeys2;
    }

    public byte [] encryption (byte [] bytesFromHexTekstJawny) {
        DES DES1 = new DES(subkeys1, bytesFromHexTekstJawny, 0);
        DES DES2 = new DES(subkeys2, DES1.getResult(), 1);
        DES DES3 = new DES(subkeys1, DES2.getResult(), 0);

        return DES3.getResult();
    }

    public byte []  decryption (byte [] bytesFromHexSzyfrogram) {
        DES DES1 = new DES(subkeys1, bytesFromHexSzyfrogram, 1);
        DES DES2 = new DES(subkeys2, DES1.getResult(), 0);
        DES DES3 = new DES(subkeys1, DES2.getResult(), 1);

        return DES3.getResult();
    }

}
