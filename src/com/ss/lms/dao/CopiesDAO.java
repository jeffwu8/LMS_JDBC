/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Copies;

public class CopiesDAO extends BaseDAO<Copies> {

	public CopiesDAO(Connection conn) {
		super(conn);
	}

	public void saveCopies(Copies copies) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) values(?, ?, ?)", 
				new Object[] { copies.getBookId(), copies.getBranchId(), copies.getNoOfCopies() });
	}
	
	public Integer saveCopiesWithId(Copies copies) throws ClassNotFoundException, SQLException {
		return saveWithID("INSERT INTO tbl_book_copies (bookId, branchId, noOfCopies) values(?, ?, ?)", 
				new Object[] { copies.getBookId(), copies.getBranchId(), copies.getNoOfCopies() });
	}
	
	public void insertCopiesAuthors(Integer copiesId, Integer authorId) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_copies_authors values(?, ?)", new Object[] { copiesId, authorId});
	}

	public void editCopies(Copies copies) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book_copies noOfCopies = ? where bookId = ? AND branchId = ?, ", 
				new Object[] { copies.getNoOfCopies(), copies.getBookId(), copies.getBranchId() });
	}

	public void deleteCopies(Copies copies) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book_copies where bookId = ? AND branchId = ?", new Object[] { copies.getBookId(), copies.getBranchId() });
	}

	public List<Copies> readCopiess() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book_copies", null);
	}

	@Override
	public List<Copies> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Copies> copiess = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		BookDAO bodao = new BookDAO(conn);
		BranchDAO brdao = new BranchDAO(conn);
		while (rs.next()) {
			Copies copies = new Copies();
			copies.setBookId(rs.getInt("bookId"));
			copies.setBranchId(rs.getInt("branchId"));
			copies.setNoOfCopies(rs.getInt("noOfCopies"));
			copies.setTitle(bodao.readFirstLevel( "SELECT * FROM tbl_book where bookId = ?)",
					new Object[] { copies.getBookId() }).get(0).getTitle());
			copies.setBranch(brdao.readFirstLevel( "SELECT * FROM tbl_library_branch where branchId = ?)",
					new Object[] { copies.getBranchId() }).get(0).getBranchName());
			copies.setAuthors(adao.readFirstLevel(
					"SELECT * FROM tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)",
					new Object[] { copies.getBookId() }));
			copiess.add(copies);
		}
		return copiess;
	}

	@Override
	public List<Copies> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Copies> copiess = new ArrayList<>();
		while (rs.next()) {
			Copies copies = new Copies();
			copies.setBookId(rs.getInt("bookId"));
			copies.setBranchId(rs.getInt("branchId"));
			copies.setNoOfCopies(rs.getInt("noOfCopies"));
			copiess.add(copies);
		}
		return copiess;
	}

}
