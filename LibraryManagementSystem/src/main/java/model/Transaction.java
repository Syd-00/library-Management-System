package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Transaction {
    private int id;
    private String transactionCode;
    private int bookId;
    private int memberId;
    private Date borrowDate;
    private Date dueDate;
    private Date returnDate;
    private String status;
    private BigDecimal fineAmount;

    // Constructor, Getter, dan Setter
    public Transaction() {}

    public Transaction(String transactionCode, int bookId, int memberId, Date borrowDate, Date dueDate) {
        this.transactionCode = transactionCode;
        this.bookId = bookId;
        this.memberId = memberId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.status = "BORROWED";
        this.fineAmount = BigDecimal.ZERO;
    }

    // Getter & Setter untuk semua field (Sesuaikan seperti kelas Book)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getTransactionCode() { return transactionCode; }
    public void setTransactionCode(String transactionCode) { this.transactionCode = transactionCode; }
    
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
    
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public Date getBorrowDate() { return borrowDate; }
    public void setBorrowDate(Date borrowDate) { this.borrowDate = borrowDate; }

    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public BigDecimal getFineAmount() { return fineAmount; }
    public void setFineAmount(BigDecimal fineAmount) { this.fineAmount = fineAmount; }
}