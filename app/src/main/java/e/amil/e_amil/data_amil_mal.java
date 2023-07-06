package e.amil.e_amil;

public class data_amil_mal {

    private String namaamilzakatmal;
    private String rjeniszakatmal;
    private String jumlahzakatmal;
    private String tglzakatmal;
    private String muzakizakatmal;
    private String penyaluranzakatmal;
    private String ketzakatmal;

    private String gambar;
    private static String key;

    public data_amil_mal() {
    }

    public data_amil_mal(String namaamilzakatmal, String rjeniszakatmal, String jumlahzakatmal, String tglzakatmal, String muzakizakatmal, String penyaluranzakatmal, String ketzakatmal, String gambar) {
        this.namaamilzakatmal = namaamilzakatmal;
        this.rjeniszakatmal = rjeniszakatmal;
        this.jumlahzakatmal = jumlahzakatmal;
        this.tglzakatmal = tglzakatmal;
        this.muzakizakatmal = muzakizakatmal;
        this.penyaluranzakatmal = penyaluranzakatmal;
        this.ketzakatmal = ketzakatmal;
        this.gambar = gambar;
    }

    public String getNamaamilzakatmal() {
        return namaamilzakatmal;
    }

    public void setNamaamilzakatmal(String namaamilzakatmal) {
        this.namaamilzakatmal = namaamilzakatmal;
    }

    public String getRjeniszakatmal() {
        return rjeniszakatmal;
    }

    public void setRjeniszakatmal(String rjeniszakatmal) {
        this.rjeniszakatmal = rjeniszakatmal;
    }

    public String getJumlahzakatmal() {
        return jumlahzakatmal;
    }

    public void setJumlahzakatmal(String jumlahzakatmal) {
        this.jumlahzakatmal = jumlahzakatmal;
    }

    public String getTglzakatmal() {
        return tglzakatmal;
    }

    public void setTglzakatmal(String tglzakatmal) {
        this.tglzakatmal = tglzakatmal;
    }

    public String getMuzakizakatmal() {
        return muzakizakatmal;
    }

    public void setMuzakizakatmal(String muzakizakatmal) {
        this.muzakizakatmal = muzakizakatmal;
    }

    public String getPenyaluranzakatmal() {
        return penyaluranzakatmal;
    }

    public void setPenyaluranzakatmal(String penyaluranzakatmal) {
        this.penyaluranzakatmal = penyaluranzakatmal;
    }

    public String getKetzakatmal() {
        return ketzakatmal;
    }

    public void setKetzakatmal(String ketzakatmal) {
        this.ketzakatmal = ketzakatmal;
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
        data_amil_mal.key = key;
    }
}
