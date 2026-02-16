package dao;

import database.DatabaseConnection;
import model.Transaction;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    // Proses Peminjaman
    public boolean borrowBook(Transaction transaction) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Mulai Transaksi

            // 1. Cek Stok Buku
            String checkStock = "SELECT available_copies FROM books WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(checkStock)) {
                ps.setInt(1, transaction.getBookId());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    int stock = rs.getInt("available_copies");
                    if (stock <= 0) {
                        throw new Exception("Stok buku habis!");
                    }
                }
            }

            // 2. Insert Transaksi
            String insertTrans = "INSERT INTO transactions (transaction_code, book_id, member_id, borrow_date, due_date, status) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(insertTrans)) {
                ps.setString(1, transaction.getTransactionCode());
                ps.setInt(2, transaction.getBookId());
                ps.setInt(3, transaction.getMemberId());
                ps.setDate(4, new java.sql.Date(transaction.getBorrowDate().getTime()));
                ps.setDate(5, new java.sql.Date(transaction.getDueDate().getTime()));
                ps.setString(6, "BORROWED");
                ps.executeUpdate();
            }

            // 3. Update Stok Buku (Kurangi)
            String updateStock = "UPDATE books SET available_copies = available_copies - 1 WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateStock)) {
                ps.setInt(1, transaction.getBookId());
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
        }
    }

    // Proses Pengembalian
    public boolean returnBook(int transactionId, Date returnDate) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Ambil data transaksi
            String selectTrans = "SELECT * FROM transactions WHERE id = ?";
            int bookId = 0;
            Date dueDate = null;
            
            try (PreparedStatement ps = conn.prepareStatement(selectTrans)) {
                ps.setInt(1, transactionId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bookId = rs.getInt("book_id");
                    dueDate = rs.getDate("due_date");
                }
            }

            // 2. Hitung Denda (Logic: 2000 per hari terlambat)
            BigDecimal fine = BigDecimal.ZERO;
            if (returnDate.after(dueDate)) {
                long daysLate = ChronoUnit.DAYS.between(dueDate.toLocalDate(), returnDate.toLocalDate());
                fine = BigDecimal.valueOf(daysLate * 2000);
            }

            // 3. Update Transaksi
            String updateTrans = "UPDATE transactions SET return_date = ?, status = 'RETURNED', fine_amount = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateTrans)) {
                ps.setDate(1, new java.sql.Date(returnDate.getTime()));
                ps.setBigDecimal(2, fine);
                ps.setInt(3, transactionId);
                ps.executeUpdate();
            }

            // 4. Update Stok Buku (Tambah)
            String updateStock = "UPDATE books SET available_copies = available_copies + 1 WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(updateStock)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (Exception e) {
            try { if(conn!=null) conn.rollback(); } catch(Exception ex){}
            e.printStackTrace();
            return false;
        } finally {
             if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException e) {}
        }
    }
    
    // Method untuk mendapatkan semua transaksi agar bisa ditampilkan di tabel
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setTransactionCode(rs.getString("transaction_code"));
                t.setBookId(rs.getInt("book_id"));
                t.setMemberId(rs.getInt("member_id"));
                t.setBorrowDate(rs.getDate("borrow_date"));
                t.setDueDate(rs.getDate("due_date"));
                t.setReturnDate(rs.getDate("return_date"));
                t.setStatus(rs.getString("status"));
                t.setFineAmount(rs.getBigDecimal("fine_amount"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}