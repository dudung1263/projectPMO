package e.amil.e_amil;

public class datapenyaluran_infak {

    private String namaamilinfak_pen;
    private String rjenisinfak_pen;
    private String jumlahinfak_pen;
    private String tglinfak_pen;
    private String ketinfak_pen;

    private String gambar;
    private static String key;

    public datapenyaluran_infak() {
    }

    public datapenyaluran_infak(String namaamilinfak_pen, String rjenisinfak_pen, String jumlahinfak_pen, String tglinfak_pen, String ketinfak_pen, String gambar) {
        this.namaamilinfak_pen = namaamilinfak_pen;
        this.rjenisinfak_pen = rjenisinfak_pen;
        this.jumlahinfak_pen = jumlahinfak_pen;
        this.tglinfak_pen = tglinfak_pen;
        this.ketinfak_pen = ketinfak_pen;
        this.gambar = gambar;
    }

    public String getNamaamilinfak_pen() {
        return namaamilinfak_pen;
    }

    public void setNamaamilinfak_pen(String namaamilinfak_pen) {
        this.namaamilinfak_pen = namaamilinfak_pen;
    }

    public String getRjenisinfak_pen() {
        return rjenisinfak_pen;
    }

    public void setRjenisinfak_pen(String rjenisinfak_pen) {
        this.rjenisinfak_pen = rjenisinfak_pen;
    }

    public String getJumlahinfak_pen() {
        return jumlahinfak_pen;
    }

    public void setJumlahinfak_pen(String jumlahinfak_pen) {
        this.jumlahinfak_pen = jumlahinfak_pen;
    }

    public String getTglinfak_pen() {
        return tglinfak_pen;
    }

    public void setTglinfak_pen(String tglinfak_pen) {
        this.tglinfak_pen = tglinfak_pen;
    }

    public String getKetinfak_pen() {
        return ketinfak_pen;
    }

    public void setKetinfak_pen(String ketinfak_pen) {
        this.ketinfak_pen = ketinfak_pen;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        datapenyaluran_infak.key = key;
    }
}
