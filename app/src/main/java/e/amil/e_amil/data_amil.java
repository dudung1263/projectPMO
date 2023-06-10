package e.amil.e_amil;

public class data_amil {

    private String rjeniszakat;
    private String jumlahzakat;
    private String tglzakat;
    private String muzakizakat;
    private String penyaluranzakat;
    private String ketzakat;

    private String gambar;
    private String key;

    public String getRjeniszakat() {
        return rjeniszakat;
    }

    public void setRjeniszakat(String rjeniszakat) {
        this.rjeniszakat = rjeniszakat;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public data_amil() {
    }

    public data_amil(String rjeniszakat, String jumlahzakat, String tglzakat, String muzakizakat, String penyaluranzakat, String ketzakat, String gambar) {
        this.rjeniszakat = rjeniszakat;
        this.jumlahzakat = jumlahzakat;
        this.tglzakat = tglzakat;
        this.muzakizakat = muzakizakat;
        this.penyaluranzakat = penyaluranzakat;
        this.ketzakat = ketzakat;
        this.gambar = gambar;
    }

    public String getJumlahzakat() {
        return jumlahzakat;
    }

    public void setJumlahzakat(String jumlahzakat) {
        this.jumlahzakat = jumlahzakat;
    }

    public String getTglzakat() {
        return tglzakat;
    }

    public void setTglzakat(String tglzakat) {
        this.tglzakat = tglzakat;
    }

    public String getMuzakizakat() {
        return muzakizakat;
    }

    public void setMuzakizakat(String muzakizakat) {
        this.muzakizakat = muzakizakat;
    }

    public String getPenyaluranzakat() {
        return penyaluranzakat;
    }

    public void setPenyaluranzakat(String penyaluranzakat) {
        this.penyaluranzakat = penyaluranzakat;
    }

    public String getKetzakat() {
        return ketzakat;
    }

    public void setKetzakat(String ketzakat) {
        this.ketzakat = ketzakat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}