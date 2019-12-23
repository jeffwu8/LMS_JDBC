/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;

public class BookDAO extends BaseDAO<Book> {

	public BookDAO(Connection conn) {
		super(conn);
	}

	public void saveBook(Book book) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book (title) values(?)", new Object[] { book.getTitle() });
	}
	
	public Integer saveBookWithId(Book book) throws ClassNotFoundException, SQLException {
		return saveWithID("INSERT INTO tbl_book (title) values(?)", new Object[] { book.getTitle() });
	}
	
	public void insertBookAuthors(Integer bookId, Integer authorId) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_authors values(?, ?)", new Object[] { bookId, authorId});
	}
	
	public void insertBookGenres(Integer bookId, Integer genreId) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_book_generes values(?, ?)", new Object[] { bookId, genreId});
	}
	
	public List<Book> readBooksByTitle(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";
		return read("SELECT * FROM tbl_book where title LIKE", new Object[] { searchString });
	}

	public void editBook(Book book) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_book set title = ? where bookId = ?", new Object[] { book.getTitle(), book.getBookId() });
	}

	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_book where bookId = ?", new Object[] { book.getBookId() });
	}

	public List<Book> readBooks() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_book", null);
	}

	@Override
	public List<Book> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		AuthorDAO adao = new AuthorDAO(conn);
		GenreDAO gdao = new GenreDAO(conn);
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			book.setAuthors(adao.readFirstLevel(
					"SELECT * FROM tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)",
					new Object[] { book.getBookId() }));
			book.setGenres(gdao.readFirstLevel(	
					"SELECT * FROM tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)",
					new Object[] { book.getBookId() }));
			// populate genres, branch etc.
			books.add(book);
		}
		return books;
	}

	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Book> books = new ArrayList<>();
		while (rs.next()) {
			Book book = new Book();
			book.setBookId(rs.getInt("bookId"));
			book.setTitle(rs.getString("title"));
			books.add(book);
		}
		return books;
	}

}
