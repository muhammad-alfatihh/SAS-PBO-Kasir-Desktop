package KasirDesktopSAS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

// ================= CLASS INDUK (ENKAPSULASI) =================
class Barang {

    // Atribut private (Enkapsulasi)
    private String namaBarang;
    private double hargaBarang;
    private int jumlahBeli;

    // Constructor
    public Barang(String namaBarang, double hargaBarang, int jumlahBeli) {
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.jumlahBeli = jumlahBeli;
    }

    // Getter dan Setter (Enkapsulasi)
    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(double hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public int getJumlahBeli() {
        return jumlahBeli;
    }

    public void setJumlahBeli(int jumlahBeli) {
        this.jumlahBeli = jumlahBeli;
    }

    // Method hitungDiskon (akan di-override oleh class turunan - Polimorfisme)
    public double hitungDiskon() {
        return 0;
    }

    // Method totalBayar
    public double totalBayar() {
        return (jumlahBeli * hargaBarang) - hitungDiskon();
    }
}

// ================= CLASS TURUNAN (INHERITANCE & POLIMORFISME) =================

// Makanan: diskon 10%
class Makanan extends Barang {

    public Makanan(String namaBarang, double hargaBarang, int jumlahBeli) {
        super(namaBarang, hargaBarang, jumlahBeli);
    }

    @Override
    public double hitungDiskon() {
        return (getJumlahBeli() * getHargaBarang()) * 0.10; // Diskon 10%
    }
}

// Minuman: diskon 5%
class Minuman extends Barang {

    public Minuman(String namaBarang, double hargaBarang, int jumlahBeli) {
        super(namaBarang, hargaBarang, jumlahBeli);
    }

    @Override
    public double hitungDiskon() {
        return (getJumlahBeli() * getHargaBarang()) * 0.05; // Diskon 5%
    }
}

// Barang Biasa: tidak ada diskon (0%)
class BarangBiasa extends Barang {

    public BarangBiasa(String namaBarang, double hargaBarang, int jumlahBeli) {
        super(namaBarang, hargaBarang, jumlahBeli);
    }

    @Override
    public double hitungDiskon() {
        return 0; // Tidak ada diskon
    }
}

// ================= FORM APLIKASI KASIR =================
public class KasirDesktopSAS extends JFrame {

    // Label
    JLabel lblNama   = new JLabel("Nama Barang");
    JLabel lblHarga  = new JLabel("Harga Satuan (Rp)");
    JLabel lblJumlah = new JLabel("Jumlah Beli");
    JLabel lblJenis  = new JLabel("Jenis Barang");

    // Input
    JTextField txtNama   = new JTextField();
    JTextField txtHarga  = new JTextField();
    JTextField txtJumlah = new JTextField();

    String[] jenis = {"Makanan", "Minuman", "Barang Biasa"};
    JComboBox<String> cmbJenis = new JComboBox<>(jenis);

    // Tombol
    JButton btnTambah      = new JButton("Tambah");
    JButton btnHitungTotal = new JButton("Hitung Total");
    JButton btnStruk       = new JButton("Cetak Struk");
    JButton btnHapus       = new JButton("Hapus Semua");

    // Tabel
    JTable table;
    DefaultTableModel model;

    // Label total
    JLabel lblTotal = new JLabel("Total Pembayaran : Rp 0");

    // Penyimpanan data menggunakan ArrayList
    ArrayList<Barang> daftarBarang = new ArrayList<>();

    double grandTotal = 0;

    public KasirDesktopSAS() {

        setTitle("Aplikasi Kasir Desktop - Toko Modern");
        setSize(800, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // ---- Header ----
        JLabel lblHeader = new JLabel("APLIKASI KASIR DESKTOP");
        lblHeader.setBounds(250, 10, 300, 30);
        lblHeader.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));

        // ---- Input Area ----
        lblNama.setBounds(20, 55, 130, 25);
        txtNama.setBounds(160, 55, 200, 25);

        lblHarga.setBounds(20, 90, 130, 25);
        txtHarga.setBounds(160, 90, 200, 25);

        lblJumlah.setBounds(20, 125, 130, 25);
        txtJumlah.setBounds(160, 125, 200, 25);

        lblJenis.setBounds(20, 160, 130, 25);
        cmbJenis.setBounds(160, 160, 200, 25);

        // ---- Tombol ----
        btnTambah.setBounds(20, 205, 130, 30);
        btnHitungTotal.setBounds(165, 205, 130, 30);
        btnStruk.setBounds(310, 205, 130, 30);
        btnHapus.setBounds(455, 205, 130, 30);

        // ---- Tabel ----
        model = new DefaultTableModel();
        model.addColumn("Nama Barang");
        model.addColumn("Jenis");
        model.addColumn("Jumlah");
        model.addColumn("Harga/Satuan");
        model.addColumn("Diskon");
        model.addColumn("Total");

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(20, 250, 740, 220);

        // ---- Label Total ----
        lblTotal.setBounds(20, 485, 400, 30);
        lblTotal.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));

        // Tambahkan komponen ke frame
        add(lblHeader);
        add(lblNama);    add(txtNama);
        add(lblHarga);   add(txtHarga);
        add(lblJumlah);  add(txtJumlah);
        add(lblJenis);   add(cmbJenis);
        add(btnTambah);
        add(btnHitungTotal);
        add(btnStruk);
        add(btnHapus);
        add(pane);
        add(lblTotal);

        // ---- Event Listener ----
        btnTambah.addActionListener(e -> tambahBarang());

        btnHitungTotal.addActionListener(e -> {
            lblTotal.setText("Total Pembayaran : Rp " + String.format("%,.0f", grandTotal));
        });

        btnStruk.addActionListener(e -> cetakStruk());

        btnHapus.addActionListener(e -> hapusSemua());

        setVisible(true);
    }

    // Method: tambah barang ke tabel dan ArrayList
    private void tambahBarang() {
        try {
            String nama     = txtNama.getText().trim();
            double harga    = Double.parseDouble(txtHarga.getText().trim());
            int jumlah      = Integer.parseInt(txtJumlah.getText().trim());
            String selectedJenis = cmbJenis.getSelectedItem().toString();

            if (nama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nama barang tidak boleh kosong!");
                return;
            }

            // Polimorfisme: objek yang dibuat sesuai jenis barang
            Barang barang;
            switch (selectedJenis) {
                case "Makanan":
                    barang = new Makanan(nama, harga, jumlah);
                    break;
                case "Minuman":
                    barang = new Minuman(nama, harga, jumlah);
                    break;
                default:
                    barang = new BarangBiasa(nama, harga, jumlah);
                    break;
            }

            double diskon = barang.hitungDiskon();
            double total  = barang.totalBayar();

            grandTotal += total;

            // Simpan ke ArrayList
            daftarBarang.add(barang);

            // Tampilkan ke tabel
            model.addRow(new Object[]{
                barang.getNamaBarang(),
                selectedJenis,
                barang.getJumlahBeli(),
                String.format("Rp %,.0f", barang.getHargaBarang()),
                String.format("Rp %,.0f", diskon),
                String.format("Rp %,.0f", total)
            });

            // Kosongkan input
            txtNama.setText("");
            txtHarga.setText("");
            txtJumlah.setText("");
            txtNama.requestFocus();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan Jumlah harus berupa angka!");
        }
    }

    // Method: cetak struk pembayaran
    private void cetakStruk() {
        if (daftarBarang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Belum ada barang yang ditambahkan!");
            return;
        }

        StringBuilder struk = new StringBuilder();
        struk.append("============================================\n");
        struk.append("          STRUK PEMBAYARAN TOKO MODERN     \n");
        struk.append("============================================\n");
        struk.append(String.format("%-20s %-5s %-10s %-10s%n", "Nama Barang", "Jml", "Diskon", "Total"));
        struk.append("--------------------------------------------\n");

        for (Barang b : daftarBarang) {
            struk.append(String.format("%-20s %-5d Rp%-8.0f Rp%.0f%n",
                b.getNamaBarang(),
                b.getJumlahBeli(),
                b.hitungDiskon(),
                b.totalBayar()
            ));
        }

        struk.append("============================================\n");
        struk.append(String.format("TOTAL PEMBAYARAN : Rp %,.0f%n", grandTotal));
        struk.append("============================================\n");
        struk.append("        Terima kasih telah berbelanja!      \n");
        struk.append("============================================\n");

        JTextArea area = new JTextArea(struk.toString());
        area.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 12));
        area.setEditable(false);
        JScrollPane sp = new JScrollPane(area);
        sp.setPreferredSize(new java.awt.Dimension(480, 300));

        JOptionPane.showMessageDialog(this, sp, "Struk Pembayaran", JOptionPane.INFORMATION_MESSAGE);
    }

    // Method: hapus semua data
    private void hapusSemua() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Yakin ingin menghapus semua data transaksi?",
            "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.setRowCount(0);
            daftarBarang.clear();
            grandTotal = 0;
            lblTotal.setText("Total Pembayaran : Rp 0");
        }
    }

    public static void main(String[] args) {
        new KasirDesktopSAS();
    }
}