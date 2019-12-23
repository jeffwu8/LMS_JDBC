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

public class AuthorDAO extends BaseDAO<Author> {

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public void saveAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_author (authorName) values(?)", new Object[] { author.getAuthorName() });
	}

	public void editAuthor(Author author) throws ClassNotFoundException, SQLException {

		save("UPDATE tbl_author set authorName = ? where authorId = ?",
				new Object[] { author.getAuthorName(), author.getAuthorId() });
	}

	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_author where authorId = ?", new Object[] { author.getAuthorId() });
	}

	public List<Author> readAuthors() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_author", null);
	}

	public List<Author> readAuthorsByAuthorName(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";
		return read("SELECT * FROM tbl_author where authorName LIKE", new Object[] { searchString });
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		BookDAO bdao = new BookDAO(conn);
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			author.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book where bookId IN (select bookId from tbl_book_authors where authorId = ?)", new Object[] { author.getAuthorId()}));
			authors.add(author);
		}
		return authors;
	}
	
	@Override
	public List<Author> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Author> authors = new ArrayList<>();
		while (rs.next()) {
			Author author = new Author();
			author.setAuthorId(rs.getInt("authorId"));
			author.setAuthorName(rs.getString("authorName"));
			authors.add(author);
		}
		return authors;
	}

}
