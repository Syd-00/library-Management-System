package gui;

import dao.BookDao;
import model.Book;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BookForm extends JFrame {
    private JTextField txtISBN, txtTitle, txtAuthor, txtPublisher, txtYear, txtCategory, txtTotal, txtAvailable, txtLocation, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private BookDao bookDao;

    public BookForm() {
        bookDao = new BookDao();
        initComponents();
        loadTableData();
    }

    private void initComponents() {
        setTitle("Manajemen Data Buku - Perpustakaan");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Panel Input (Atas)
        JPanel inputPanel = new JPanel(new GridLayout(5, 4, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Form Buku"));

        inputPanel.add(new JLabel("ISBN:"));
        txtISBN = new JTextField();
        inputPanel.add(txtISBN);

        inputPanel.add(new JLabel("Judul:"));
        txtTitle = new JTextField();
        inputPanel.add(txtTitle);

        inputPanel.add(new JLabel("Pengarang:"));
        txtAuthor = new JTextField();
        inputPanel.add(txtAuthor);

        inputPanel.add(new JLabel("Penerbit:"));
        txtPublisher = new JTextField();
        inputPanel.add(txtPublisher);

        inputPanel.add(new JLabel("Tahun:"));
        txtYear = new JTextField();
        inputPanel.add(txtYear);

        inputPanel.add(new JLabel("Kategori:"));
        txtCategory = new JTextField();
        inputPanel.add(txtCategory);

        inputPanel.add(new JLabel("Total Stok:"));
        txtTotal = new JTextField();
        inputPanel.add(txtTotal);

        inputPanel.add(new JLabel("Tersedia:"));
        txtAvailable = new JTextField();
        inputPanel.add(txtAvailable);

        inputPanel.add(new JLabel("Lokasi Rak:"));
        txtLocation = new JTextField();
        inputPanel.add(txtLocation);
        
        // Kosong untuk grid layout
        inputPanel.add(new JLabel("")); 
        inputPanel.add(new JLabel(""));

        // Panel Tombol (Tengah)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Simpan");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Hapus");
        btnClear = new JButton("Clear");
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Panel Tabel (Bawah)
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Data Buku"));
        
        // Panel Pencarian di atas tabel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari Judul:"));
        txtSearch = new JTextField(30);
        btnSearch = new JButton("Cari");
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);
        
        tableModel = new DefaultTableModel(
            new String[]{"ID", "ISBN", "Judul", "Pengarang", "Penerbit", "Tahun", "Kategori", "Total", "Tersedia", "Lokasi"}, 0
        );
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        tablePanel.add(searchPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Menambahkan panel ke frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.SOUTH);

        // Event Listeners
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });

        // Mouse Listener pada Tabel untuk mengambil data ke form
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    // Mengambil ID dari kolom 0 (hidden or visible)
                    int id = (int) tableModel.getValueAt(row, 0);
                    fillForm(id); // Implementasi fillForm dibawah
                }
            }
        });
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        List<Book> books = bookDao.getAllBooks();
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                b.getId(), b.getIsbn(), b.getTitle(), b.getAuthor(),
                b.getPublisher(), b.getPublicationYear(), b.getCategory(),
                b.getTotalCopies(), b.getAvailableCopies(), b.getShelfLocation()
            });
        }
    }
    
    private void fillForm(int id) {
        // Kita ambil data dari tabel langsung untuk simplifikasi, atau query ulang by ID
        int row = table.getSelectedRow();
        txtISBN.setText(tableModel.getValueAt(row, 1).toString());
        txtTitle.setText(tableModel.getValueAt(row, 2).toString());
        txtAuthor.setText(tableModel.getValueAt(row, 3).toString());
        txtPublisher.setText(tableModel.getValueAt(row, 4).toString());
        txtYear.setText(tableModel.getValueAt(row, 5).toString());
        txtCategory.setText(tableModel.getValueAt(row, 6).toString());
        txtTotal.setText(tableModel.getValueAt(row, 7).toString());
        txtAvailable.setText(tableModel.getValueAt(row, 8).toString());
        txtLocation.setText(tableModel.getValueAt(row, 9).toString());
    }

    private void addBook() {
        Book book = new Book();
        book.setIsbn(txtISBN.getText());
        book.setTitle(txtTitle.getText());
        book.setAuthor(txtAuthor.getText());
        book.setPublisher(txtPublisher.getText());
        book.setPublicationYear(Integer.parseInt(txtYear.getText()));
        book.setCategory(txtCategory.getText());
        book.setTotalCopies(Integer.parseInt(txtTotal.getText()));
        book.setAvailableCopies(Integer.parseInt(txtAvailable.getText()));
        book.setShelfLocation(txtLocation.getText());

        if (bookDao.addBook(book)) {
            JOptionPane.showMessageDialog(this, "Data buku berhasil ditambahkan!");
            loadTableData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal menambahkan data.");
        }
    }

    private void updateBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data buku yang akan diupdate di tabel!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        
        Book book = new Book();
        book.setId(id);
        book.setIsbn(txtISBN.getText());
        book.setTitle(txtTitle.getText());
        book.setAuthor(txtAuthor.getText());
        book.setPublisher(txtPublisher.getText());
        book.setPublicationYear(Integer.parseInt(txtYear.getText()));
        book.setCategory(txtCategory.getText());
        book.setTotalCopies(Integer.parseInt(txtTotal.getText()));
        book.setAvailableCopies(Integer.parseInt(txtAvailable.getText()));
        book.setShelfLocation(txtLocation.getText());

        if (bookDao.updateBook(book)) {
            JOptionPane.showMessageDialog(this, "Data buku berhasil diupdate!");
            loadTableData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Gagal mengupdate data.");
        }
    }

    private void deleteBook() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data buku yang akan dihapus di tabel!");
            return;
        }
        int id = (int) tableModel.getValueAt(row, 0);
        
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus buku ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookDao.deleteBook(id)) {
                JOptionPane.showMessageDialog(this, "Data buku berhasil dihapus!");
                loadTableData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menghapus data.");
            }
        }
    }
    
    private void searchBook() {
        String keyword = txtSearch.getText().toLowerCase();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String title = tableModel.getValueAt(i, 2).toString().toLowerCase();
            if (title.contains(keyword)) {
                table.setRowSelectionInterval(i, i);
                return; // Hanya select yang pertama ketemu
            }
        }
        JOptionPane.showMessageDialog(this, "Data tidak ditemukan!");
    }

    private void clearForm() {
        txtISBN.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtPublisher.setText("");
        txtYear.setText("");
        txtCategory.setText("");
        txtTotal.setText("");
        txtAvailable.setText("");
        txtLocation.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BookForm().setVisible(true);
            }
        });
    }
}