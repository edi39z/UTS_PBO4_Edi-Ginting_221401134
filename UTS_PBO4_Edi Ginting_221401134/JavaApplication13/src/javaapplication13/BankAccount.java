/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication13;

/**
 *
 * @author Acer
 */
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Random;

public class BankAccount implements Serializable {
    private String nama;
    private String alamat;
    private String nomorTelepon;
    private String nomorAkun;
    private int saldo;
    private LocalDateTime tanggalRegistrasi;

    // Constructor
    public BankAccount(String nama, String alamat, String nomorTelepon, int saldo) {
        this.nama = nama;
        this.alamat = alamat;
        this.nomorTelepon = nomorTelepon;
        this.saldo = saldo;
        this.nomorAkun = generateNomorAkun();
        this.tanggalRegistrasi = LocalDateTime.now();
    }

    //  menambah saldo
    public void topUp(int jumlahTopUp) {
        saldo += jumlahTopUp;
        System.out.println("Top-up berhasil. Saldo sekarang: " + saldo);
    }

    //  transfer saldo 
    public boolean transfer(int jumlahTransfer) {
        if (saldo >= jumlahTransfer) {
            saldo -= jumlahTransfer;
            System.out.println("Transfer berhasil. Saldo tersisa: " + saldo);
            return true;
        } else {
            System.out.println("Saldo tidak mencukupi untuk transfer.");
            return false;
        }
    }

    // menampilkan informasi 
    public void showDescription() {
        System.out.println("Informasi Rekening:");
        System.out.println("Nama: " + nama);
        System.out.println("Alamat: " + alamat);
        System.out.println("Nomor Telepon: " + nomorTelepon);
        System.out.println("Nomor Akun: " + nomorAkun);
        System.out.println("Saldo: " + saldo);
        System.out.println("Tanggal Registrasi: " + tanggalRegistrasi);
    }

    // nomor akun secara acak
    private String generateNomorAkun() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            sb.append(rand.nextInt(10)); 
        }
        return sb.toString();
    }

   
    public String getNomorAkun() {
        return nomorAkun;
    }

    public String getNama() {
        return nama;
    }
  
    public String getAlamat() {
        return alamat;
    }
    
    public String getNomorTelepon() {
        return nomorTelepon;
    }
   
    public int getSaldo() {
        return saldo;
    }

    public LocalDateTime getTanggalRegistrasi() {
        return tanggalRegistrasi;
    }
}



