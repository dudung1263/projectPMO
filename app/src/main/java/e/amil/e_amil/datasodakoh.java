package e.amil.e_amil;



public class datasodakoh {
    private String jenis_sodakoh;
    private String jumlah_sodakoh;
    private String tanggal_sodakoh;
    private String muzaki_sodakoh;
    private String keterangan_sodakoh;


    public String getJenis_sodakoh() {
        return jenis_sodakoh;
    }

    public void setJenis_sodakoh(String jenis_sodakoh) {
        this.jenis_sodakoh = jenis_sodakoh;
    }

    public String getJumlah_sodakoh() {
        return jumlah_sodakoh;
    }

    public void setJumlah_sodakoh(String jumlah_sodakoh) {
        this.jumlah_sodakoh = jumlah_sodakoh;
    }

    public String getTanggal_sodakoh() {
        return tanggal_sodakoh;
    }

    public void setTanggal_sodakoh(String tanggal_sodakoh) {
        this.tanggal_sodakoh = tanggal_sodakoh;
    }

    public String getMuzaki_sodakoh() {
        return muzaki_sodakoh;
    }

    public void setMuzaki_sodakoh(String muzaki_sodakoh) {
        this.muzaki_sodakoh = muzaki_sodakoh;
    }

    public String getKeterangan_sodakoh() {
        return keterangan_sodakoh;
    }

    public void setKeterangan_sodakoh(String keterangan_sodakoh) {
        this.keterangan_sodakoh = keterangan_sodakoh;
    }


    public datasodakoh(String jenis_sodakoh, String jumlah_sodakoh, String tanggal_sodakoh, String muzaki_sodakoh, String keterangan_sodakoh, String trim) {
        this.jenis_sodakoh = jenis_sodakoh;
        this.jumlah_sodakoh = jumlah_sodakoh;
        this.tanggal_sodakoh = tanggal_sodakoh;
        this.muzaki_sodakoh = muzaki_sodakoh;
        this.keterangan_sodakoh = keterangan_sodakoh;

    }


}
