/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Genre;

public class GenreDAO extends BaseDAO<Genre> {

	public GenreDAO(Connection conn) {
		super(conn);
	}

	public void saveGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_genre (genreName) values(?)", new Object[] { genre.getGenreName() });
	}

	public void editGenre(Genre genre) throws ClassNotFoundException, SQLException {

		save("UPDATE tbl_genre set genreName = ? where genreId = ?",
				new Object[] { genre.getGenreName(), genre.getGenreId() });
	}

	public void deleteGenre(Genre genre) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_genre where genreId = ?", new Object[] { genre.getGenreId() });
	}

	public List<Genre> readGenres() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_genre", null);
	}

	public List<Genre> readGenresByGenreName(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";
		return read("SELECT * FROM tbl_genre where genreName LIKE", new Object[] { searchString });
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		BookDAO bdao = new BookDAO(conn);
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genreId"));
			genre.setGenreName(rs.getString("genreName"));
			genre.setBooks(bdao.readFirstLevel("SELECT * FROM tbl_book where bookId IN (select bookId from tbl_book_genres where genreId = ?)", new Object[] { genre.getGenreId()}));
			genres.add(genre);
		}
		return genres;
	}
	
	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Genre> genres = new ArrayList<>();
		while (rs.next()) {
			Genre genre = new Genre();
			genre.setGenreId(rs.getInt("genreId"));
			genre.setGenreName(rs.getString("genreName"));
			genres.add(genre);
		}
		return genres;
	}

}
