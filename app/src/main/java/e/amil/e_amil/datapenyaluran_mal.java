package e.amil.e_amil;

public class datapenyaluran_mal {

    private String namaamilzakatmal_pen;
    private String rjeniszakatmal_pen;
    private String jumlahzakatmal_pen;
    private String tglzakatmal_pen;
    private String ketzakatmal_pen;

    private String gambar;
    private static String key;

    public datapenyaluran_mal() {
    }

    public datapenyaluran_mal(String namaamilzakatmal_pen, String rjeniszakatmal_pen, String jumlahzakatmal_pen, String tglzakatmal_pen, String ketzakatmal_pen, String gambar) {
        this.namaamilzakatmal_pen = namaamilzakatmal_pen;
        this.rjeniszakatmal_pen = rjeniszakatmal_pen;
        this.jumlahzakatmal_pen = jumlahzakatmal_pen;
        this.tglzakatmal_pen = tglzakatmal_pen;
        this.ketzakatmal_pen = ketzakatmal_pen;
        this.gambar = gambar;
    }

    public String getNamaamilzakatmal_pen() {
        return namaamilzakatmal_pen;
    }

    public void setNamaamilzakatmal_pen(String namaamilzakatmal_pen) {
        this.namaamilzakatmal_pen = namaamilzakatmal_pen;
    }

    public String getRjeniszakatmal_pen() {
        return rjeniszakatmal_pen;
    }

    public void setRjeniszakatmal_pen(String rjeniszakatmal_pen) {
        this.rjeniszakatmal_pen = rjeniszakatmal_pen;
    }

    public String getJumlahzakatmal_pen() {
        return jumlahzakatmal_pen;
    }

    public void setJumlahzakatmal_pen(String jumlahzakatmal_pen) {
        this.jumlahzakatmal_pen = jumlahzakatmal_pen;
    }

    public String getTglzakatmal_pen() {
        return tglzakatmal_pen;
    }

    public void setTglzakatmal_pen(String tglzakatmal_pen) {
        this.tglzakatmal_pen = tglzakatmal_pen;
    }

    public String getKetzakatmal_pen() {
        return ketzakatmal_pen;
    }

    public void setKetzakatmal_pen(String ketzakatmal_pen) {
        this.ketzakatmal_pen = ketzakatmal_pen;
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
        datapenyaluran_mal.key = key;
    }
}
