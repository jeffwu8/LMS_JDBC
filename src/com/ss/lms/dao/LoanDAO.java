/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Loan;


public class LoanDAO extends BaseDAO<Loan> {

	public LoanDAO(Connection conn) {
		super(conn);
	}

	public void saveLoan(Loan loan) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values(?, ?, ?, ?, ?)", 
				new Object[] { loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDueDate() });
	}
	
	public Integer saveLoanWithId(Loan loan) throws ClassNotFoundException, SQLException {
		return saveWithID("INSERT INTO tbl_book_loans (bookId, branchId, cardNo, dateOut, dueDate) values(?, ?, ?, ?, ?)", 
				new Object[] { loan.getBookId(), loan.getBranchId(), loan.getCardNo(), loan.getDateOut(), loan.getDueDate() });
	}
	
	public void editLoan(Loan loan) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_loans set dueDate = ?, dateIn = ? where bookId = ? AND branchId = ? AND cardNo = ?", 
				new Object[] { loan.getDueDate(), loan.getDateIn(), loan.getBookId(), loan.getBranchId(), loan.getCardNo() });
	}

	public void deleteLoan(Loan loan) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_loans where bookId = ? AND branchId = ? AND cardNo = ?", 
				new Object[] { loan.getBookId(), loan.getBranchId(), loan.getCardNo() });
	}

	public List<Loan> readLoan() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_loans", null);
	}

	@Override
	public List<Loan> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Loan> loans = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		BookDAO bodao = new BookDAO(conn);
		BranchDAO brdao = new BranchDAO(conn);
		while (rs.next()) {
			Loan loan = new Loan();
			loan.setBookId(rs.getInt("bookId"));
			loan.setBranchId(rs.getInt("branchId"));
			loan.setCardNo(rs.getInt("cardNo"));
			loan.setDateOut(rs.getDate("dateOut").toLocalDate());
			loan.setDueDate(rs.getDate("dueDate").toLocalDate());	
			loan.setDateIn(rs.getDate("dateIn").toLocalDate());
			loan.setBookTitle(bodao.readFirstLevel( "SELECT * FROM tbl_book where bookId = ?)",
					new Object[] { loan.getBookId() }).get(0).getTitle());
			loan.setBranchName(brdao.readFirstLevel( "SELECT * FROM tbl_library_branch where branchId = ?)",
					new Object[] { loan.getBranchId() }).get(0).getBranchName());
			loan.setAuthors(adao.readFirstLevel(
					"SELECT * FROM tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)",
					new Object[] { loan.getBookId() }));
			loans.add(loan);
		}
		return loans;
	}

	@Override
	public List<Loan> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Loan> loans = new ArrayList<>();
		while (rs.next()) {
			Loan loan = new Loan();
			loan.setBookId(rs.getInt("bookId"));
			loan.setBranchId(rs.getInt("branchId"));
			loan.setCardNo(rs.getInt("cardNo"));
			loan.setDateOut(rs.getDate("dateOut").toLocalDate());
			loan.setDueDate(rs.getDate("dueDate").toLocalDate());	
			loan.setDateIn(rs.getDate("dateIn").toLocalDate());
			loans.add(loan);
		}
		return loans;
	}

}
