/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package javaapplication13;

/**
 *
 * @author Acer
 */
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class BankingSystem {
    private ArrayList<BankAccount> accounts;
    private String csvFilePath = "data.csv";

    // Constructor
    public BankingSystem() {
        this.accounts = loadAccountsFromCSV();
    }

    // Menampilkan menu utama
    public void tampilkanMenu() {
        System.out.println("\nSelamat datang di MyBanking System!");
        System.out.println("1. Buat akun baru");
        System.out.println("2. Transfer uang");
        System.out.println("3. Simpan uang");
        System.out.println("4. Mengecek informasi rekening pribadi");
        System.out.println("5. Keluar dari program");
        System.out.print("Silakan pilih opsi: ");
    }

    //  registrasi akun baru
    public void registrasiAkun(Scanner scanner) {
        System.out.println("\n=== Registrasi Akun Baru ===");
        System.out.print("Masukkan nama: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan alamat: ");
        String alamat = scanner.nextLine();
        System.out.print("Masukkan nomor telepon: ");
        String nomorTelepon = scanner.nextLine();
        int saldoAwal;
        do {
            System.out.print("Masukkan saldo awal (bilangan bulat): ");
            while (!scanner.hasNextInt()) {
                System.out.println("Input harus berupa bilangan bulat.");
                scanner.next(); 
            }
            saldoAwal = scanner.nextInt();
        } while (saldoAwal <= 0);
        scanner.nextLine(); 

        BankAccount akunBaru = new BankAccount(nama, alamat, nomorTelepon, saldoAwal);
        accounts.add(akunBaru);
        saveAccountsToCSV(); 

        // Menampilkan informasi akun yang baru dibuat
        System.out.println("\nAkun berhasil dibuat! Berikut informasinya:");
        System.out.println("Nama: " + akunBaru.getNama());
        System.out.println("Alamat: " + akunBaru.getAlamat());
        System.out.println("Nomor Telepon: " + akunBaru.getNomorTelepon());
        System.out.println("Nomor Akun: " + akunBaru.getNomorAkun());
        System.out.println("Saldo Awal: " + akunBaru.getSaldo());
        System.out.println("Tanggal Registrasi: " + akunBaru.getTanggalRegistrasi());
    }

    // transfer uang antar akun
    public void transferUang(Scanner scanner) {
        System.out.println("\n=== Transfer Uang ===");
        System.out.print("Masukkan nomor akun pengirim: ");
        String nomorAkunPengirim = scanner.nextLine();
        System.out.print("Masukkan nomor akun penerima: ");
        String nomorAkunPenerima = scanner.nextLine();
        System.out.print("Masukkan jumlah uang yang akan ditransfer: ");
        int jumlahTransfer;
        while (!scanner.hasNextInt()) {
            System.out.println("Input harus berupa bilangan bulat.");
            scanner.next(); 
        }
        jumlahTransfer = scanner.nextInt();
        scanner.nextLine(); 

        BankAccount pengirim = findAccountByNomorAkun(nomorAkunPengirim);
        BankAccount penerima = findAccountByNomorAkun(nomorAkunPenerima);

        if (pengirim != null && penerima != null) {
            if (pengirim.transfer(jumlahTransfer)) {
                penerima.topUp(jumlahTransfer);
                saveAccountsToCSV(); 
            }
        } else {
            System.out.println("Nomor akun pengirim atau penerima tidak valid.");
        }
    }

    //menyimpan uang ke dalam akun
    public void simpanUang(Scanner scanner) {
        System.out.println("\n=== Simpan Uang ===");
        System.out.print("Masukkan nomor akun: ");
        String nomorAkun = scanner.nextLine();
        System.out.print("Masukkan jumlah uang yang akan disimpan: ");
        int jumlahSimpan;
        while (!scanner.hasNextInt()) {
            System.out.println("Input harus berupa bilangan bulat.");
            scanner.next(); 
        }
        jumlahSimpan = scanner.nextInt();
        scanner.nextLine(); 

        BankAccount akun = findAccountByNomorAkun(nomorAkun);
        if (akun != null) {
            akun.topUp(jumlahSimpan);
            saveAccountsToCSV(); 
        } else {
            System.out.println("Nomor akun tidak valid.");
        }
    }

    //mengecek informasi rekening pribadi
    public void cekInformasiRekening(Scanner scanner) {
        System.out.println("\n=== Cek Informasi Rekening ===");
        System.out.print("Masukkan nomor akun: ");
        String nomorAkun = scanner.nextLine();

        BankAccount akun = findAccountByNomorAkun(nomorAkun);
        if (akun != null) {
            akun.showDescription();
        } else {
            System.out.println("Nomor akun tidak valid.");
        }
    }

    //mencari akun berdasarkan nomor akun
    private BankAccount findAccountByNomorAkun(String nomorAkun) {
        for (BankAccount account : accounts) {
            if (account.getNomorAkun().equals(nomorAkun)) {
                return account;
            }
        }
        return null; 
    }

    
    private ArrayList<BankAccount> loadAccountsFromCSV() {
        ArrayList<BankAccount> loadedAccounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String nama = data[0];
                String alamat = data[1];
                String nomorTelepon = data[2];
                int saldo = Integer.parseInt(data[3]);
                BankAccount account = new BankAccount(nama, alamat, nomorTelepon, saldo);
                loadedAccounts.add(account);
            }
        } catch (IOException e) {
            System.out.println("Gagal memuat data dari file CSV: " + e.getMessage());
        }
        return loadedAccounts;
    }

    //  menyimpan data 
    private void saveAccountsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
            for (BankAccount account : accounts) {
                writer.write(String.format("%s,%s,%s,%d\n",
                        account.getNama(), account.getAlamat(), account.getNomorTelepon(), account.getSaldo()));
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data ke file CSV: " + e.getMessage());
        }
    }

   //MAIN 
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = new BankingSystem();

        boolean keluar = false;
        while (!keluar) {
            bankingSystem.tampilkanMenu();
            int pilihan;
            while (true) {
                try {
                    System.out.print("Silakan pilih opsi (1-5): ");
                    pilihan = Integer.parseInt(scanner.nextLine());
                    if (pilihan < 1 || pilihan > 5) {
                        System.out.println("Masukkan angka antara 1 hingga 5.");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Masukkan angka antara 1 hingga 5.");
                }
            }

            switch (pilihan) {
                case 1:
                    bankingSystem.registrasiAkun(scanner);
                    break;
                case 2:
                    bankingSystem.transferUang(scanner);
                    break;
                case 3:
                    bankingSystem.simpanUang(scanner);
                    break;
                case 4:
                    bankingSystem.cekInformasiRekening(scanner);
                    break;
                case 5:
                    keluar = true;
                    System.out.println("Keluar dari MyBanking System. Selamat tinggal!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        }
        scanner.close();
    }
}


