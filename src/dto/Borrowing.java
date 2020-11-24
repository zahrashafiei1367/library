package dto;

import java.time.LocalDate;

public class Borrowing {
    private int id;
    private LocalDate dateOfBorrowing;
    private LocalDate dateOfReturning;
    private int bookId;
    private int userId;

    public Borrowing() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateOfBorrowing() {
        return dateOfBorrowing;
    }

    public void setDateOfBorrowing(LocalDate dateOfBorrowing) {
        this.dateOfBorrowing = dateOfBorrowing;
    }

    public LocalDate getDateOfReturning() {
        return dateOfReturning;
    }

    public void setDateOfReturning(LocalDate dateOfReturning) {
        this.dateOfReturning = dateOfReturning;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
