package e.amil.e_amil;

public class datapenyaluran_fitrah {

    private String namaamilzakat_pen;
    private String rjeniszakat_pen;
    private String jumlahzakat_pen;
    private String tglinfakzakat_pen;
    private String ketinfakzakat_pen;

    private String gambar;
    private static String key;

    public datapenyaluran_fitrah() {
    }

    public datapenyaluran_fitrah(String namaamilzakat_pen, String rjeniszakat_pen, String jumlahzakat_pen, String tglinfakzakat_pen, String ketinfakzakat_pen, String gambar) {
        this.namaamilzakat_pen = namaamilzakat_pen;
        this.rjeniszakat_pen = rjeniszakat_pen;
        this.jumlahzakat_pen = jumlahzakat_pen;
        this.tglinfakzakat_pen = tglinfakzakat_pen;
        this.ketinfakzakat_pen = ketinfakzakat_pen;
        this.gambar = gambar;
    }

    public String getNamaamilzakat_pen() {
        return namaamilzakat_pen;
    }

    public void setNamaamilzakat_pen(String namaamilzakat_pen) {
        this.namaamilzakat_pen = namaamilzakat_pen;
    }

    public String getRjeniszakat_pen() {
        return rjeniszakat_pen;
    }

    public void setRjeniszakat_pen(String rjeniszakat_pen) {
        this.rjeniszakat_pen = rjeniszakat_pen;
    }

    public String getJumlahzakat_pen() {
        return jumlahzakat_pen;
    }

    public void setJumlahzakat_pen(String jumlahzakat_pen) {
        this.jumlahzakat_pen = jumlahzakat_pen;
    }

    public String getTglinfakzakat_pen() {
        return tglinfakzakat_pen;
    }

    public void setTglinfakzakat_pen(String tglinfakzakat_pen) {
        this.tglinfakzakat_pen = tglinfakzakat_pen;
    }

    public String getKetinfakzakat_pen() {
        return ketinfakzakat_pen;
    }

    public void setKetinfakzakat_pen(String ketinfakzakat_pen) {
        this.ketinfakzakat_pen = ketinfakzakat_pen;
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
        datapenyaluran_fitrah.key = key;
    }
}
