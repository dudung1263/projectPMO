package e.amil.e_amil;

public class data_amil {

    private String jenis_zakat;
    private String jumlah_zakat;
    private String tanggal_zakat;
    private String keterangan_zakat;

    public data_amil(String getketerangan, String getmuzaki, String gettanggal, String getjumlah, String getjenis) {

    }

    public data_amil(String jenis_zakat, String jumlah_zakat, String tanggal_zakat, String keterangan_zakat) {
        this.jenis_zakat = jenis_zakat;
        this.jumlah_zakat = jumlah_zakat;
        this.tanggal_zakat = tanggal_zakat;
        this.keterangan_zakat = keterangan_zakat;
    }

    public String getJenis_zakat() {
        return jenis_zakat;
    }

    public void setJenis_zakat(String jenis_zakat) {
        this.jenis_zakat = jenis_zakat;
    }

    public String getJumlah_zakat() {
        return jumlah_zakat;
    }

    public void setJumlah_zakat(String jumlah_zakat) {
        this.jumlah_zakat = jumlah_zakat;
    }

    public String getTanggal_zakat() {
        return tanggal_zakat;
    }

    public void setTanggal_zakat(String tanggal_zakat) {
        this.tanggal_zakat = tanggal_zakat;
    }

    public String getKeterangan_zakat() {
        return keterangan_zakat;
    }

    public void setKeterangan_zakat(String keterangan_zakat) {
        this.keterangan_zakat = keterangan_zakat;
    }
}

