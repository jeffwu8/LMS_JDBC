/**
 * 
 */
package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.dao.AuthorDAO;
import com.ss.lms.dao.BookDAO;
import com.ss.lms.entity.Author;
import com.ss.lms.entity.Book;

/**
 * @author ppradhan
 *
 */
public class AdminService {

	ConnectionUtil connUtil = new ConnectionUtil();

	public List<Author> readAuthors() throws SQLException {
		Connection conn = null;
		List<Author> authors = new ArrayList<>();
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			authors = adao.readAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading authors faiiled");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return authors;
	}

	public String addBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			Integer bookId = bdao.saveBookWithId(book);
			// add bookid authorid into tbl_book_authors
			if(book.getAuthors()!=null){
				for(Author a: book.getAuthors()){
					bdao.insertBookAuthors(bookId, a.getAuthorId());
				}
			}
			// add bookid genreIdinto tbl_book_genres
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding Book failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return "Book added successfully";
	}

}
