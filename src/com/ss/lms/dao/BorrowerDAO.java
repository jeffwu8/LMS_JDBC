/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Borrower;

public class BorrowerDAO extends BaseDAO<Borrower> {

	public BorrowerDAO(Connection conn) {
		super(conn);
	}

	public void saveBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_borrower (cardNo, name, address, phone) values(?, ?, ?, ?)", new Object[] { 
				borrower.getCardNo(), borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}
	
	public Integer saveBorrowerWithId(Borrower borrower) throws ClassNotFoundException, SQLException {
		return saveWithID("INSERT INTO tbl_borrower (cardNo, name, address, phone) values(?, ?, ?, ?)", new Object[] { 
				borrower.getCardNo(), borrower.getName(), borrower.getAddress(), borrower.getPhone() });
	}

	public void editBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_borrower set name = ?, address = ?, phone = ? where cardNo = ?", new Object[] {
				borrower.getName(), borrower.getAddress(), borrower.getPhone(), borrower.getCardNo() });
	}

	public void deleteBorrower(Borrower borrower) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_borrower where cardNo = ?", new Object[] { borrower.getCardNo() });
	}

	public List<Borrower> readBorrowers() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_borrower", null);
	}

	@Override
	public List<Borrower> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<>();
		LoanDAO ldao = new LoanDAO(conn);
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrower.setLoans(ldao.readFirstLevel( "SELECT * FROM tbl_book_loans where cardNo = ?",
					new Object[] { borrower.getCardNo() }));
			borrowers.add(borrower);
		}
		return borrowers;
	}

	@Override
	public List<Borrower> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Borrower> borrowers = new ArrayList<>();
		while (rs.next()) {
			Borrower borrower = new Borrower();
			borrower.setCardNo(rs.getInt("cardNo"));
			borrower.setName(rs.getString("name"));
			borrower.setAddress(rs.getString("address"));
			borrower.setPhone(rs.getString("phone"));
			borrowers.add(borrower);
		}
		return borrowers;
	}

}
