package e.amil.e_amil;

public class data_penyaluran_zakat {

    private String namaamilzakat_pz;
    private String rjeniszakat_pz;
    private String jumlahzakat_pz;
    private String tglzakat_pz;
    private String ketzakat_pz;

    private String gambar;
    private static String key;

    public String getRjeniszakat_pz() {
        return rjeniszakat_pz;
    }

    public void setRjeniszakat_pz(String rjeniszakat_pz) {
        this.rjeniszakat_pz = rjeniszakat_pz;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public data_penyaluran_zakat() {
    }

    public data_penyaluran_zakat(String namaamilzakat_pz, String rjeniszakat_pz, String jumlahzakat_pz, String tglzakat_pz, String ketzakat_pz, String gambar) {
        this.namaamilzakat_pz = namaamilzakat_pz;
        this.rjeniszakat_pz = rjeniszakat_pz;
        this.jumlahzakat_pz = jumlahzakat_pz;
        this.tglzakat_pz = tglzakat_pz;
        this.ketzakat_pz = ketzakat_pz;
        this.gambar = gambar;
    }

    public String getNamaamilzakat_pz() {
        return namaamilzakat_pz;
    }

    public void setNamaamilzakat_pz(String namaamilzakat_pz) {
        this.namaamilzakat_pz = namaamilzakat_pz;
    }

    public String getJumlahzakat_pz() {
        return jumlahzakat_pz;
    }

    public void setJumlahzakat_pz(String jumlahzakat_pz) {
        this.jumlahzakat_pz = jumlahzakat_pz;
    }

    public String getTglzakat_pz() {
        return tglzakat_pz;
    }

    public void setTglzakat_pz(String tglzakat_pz) {
        this.tglzakat_pz = tglzakat_pz;
    }

    public String getKetzakat_pz() {
        return ketzakat_pz;
    }

    public void setKetzakat_pz(String ketzakat_pz) {
        this.ketzakat_pz = ketzakat_pz;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        data_penyaluran_zakat.key = key;
    }
}
