package com.ss.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.dao.*;
import com.ss.lms.entity.*;

public class AdminService {
	ConnectionUtil connUtil = new ConnectionUtil();

	public List<Author> readAuthors() throws SQLException {
		Connection conn = null;
		List<Author> authors = new ArrayList<Author>();
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			authors = adao.readAuthors();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading authors failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return authors;
	}

	public List<Book> readBooks() throws SQLException {
		Connection conn = null;
		List<Book> books = new ArrayList<Book>();
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			books = bdao.readBooks();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading books failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return books;
	}

	public List<Copies> readCopies() throws SQLException {
		Connection conn = null;
		List<Copies> copies = new ArrayList<Copies>();
		try {
			conn = connUtil.getConnection();
			CopiesDAO cdao = new CopiesDAO(conn);
			copies = cdao.readCopies();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading books failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return copies;
	}

	public List<Loan> readBookLoans() throws SQLException {
		Connection conn = null;
		List<Loan> loans = new ArrayList<Loan>();
		try {
			conn = connUtil.getConnection();
			LoanDAO ldao = new LoanDAO(conn);
			loans = ldao.readLoans();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading books failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return loans;
	}

	public List<Borrower> readBorrowers() throws SQLException {
		Connection conn = null;
		List<Borrower> borrowers = new ArrayList<Borrower>();
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			borrowers = bdao.readBorrowers();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading authors failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return borrowers;
	}

	public List<Branch> readBranches() throws SQLException {
		Connection conn = null;
		List<Branch> branches = new ArrayList<Branch>();
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			branches = bdao.readBranches();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading books failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return branches;
	}

	public List<Genre> readGenres() throws SQLException {
		Connection conn = null;
		List<Genre> genres = new ArrayList<Genre>();
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			genres = gdao.readGenres();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading genres failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return genres;
	}

	public List<Publisher> readPublishers() throws SQLException {
		Connection conn = null;
		List<Publisher> publishers = new ArrayList<Publisher>();
		try {
			conn = connUtil.getConnection();
			PublisherDAO dao = new PublisherDAO(conn);
			publishers = dao.readPublishers();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Reading publishers failed");
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
		return publishers;
	}

	public String addAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			BookDAO bdao = new BookDAO(conn);
			Integer authorId = adao.saveAuthorWithId(author);
			if (author.getBooks() != null) {
				for (Book b : author.getBooks())
					bdao.insertBookAuthors(b.getBookId(), authorId);
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding author failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Author successfully added";
	}

	public String addBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			Integer bookId = bdao.saveBookWithId(book);
			if (book.getAuthors() != null) {
				for (Author a : book.getAuthors())
					bdao.insertBookAuthors(bookId, a.getAuthorId());
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding book failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Book successfully added";
	}

	public String addCopies(Copies copies) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			CopiesDAO cdao = new CopiesDAO(conn);
			Integer bookId = cdao.saveCopiesWithId(copies);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding book copies failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Copy successfully added";
	}

	public String addBookLoans(Loan loan) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO ldao = new LoanDAO(conn);
			Integer bookId = ldao.saveLoanWithId(loan);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding loan failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Loan successfully added";
	}

	public String addBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			Integer bookId = bdao.saveBorrowerWithId(borrower);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding borrower failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Borrower successfully added";
	}

	public String addBranch(Branch b) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			Integer bookId = bdao.saveBranchWithId(b);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding branch failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Branch successfully added";
	}

	public String addGenre(Genre genre) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			BookDAO bdao = new BookDAO(conn);
			Integer bookId = gdao.saveGenreWithId(genre);
			if (genre.getBooks() != null) {
				for (Book book : genre.getBooks())
					bdao.insertBookGenres(genre.getGenreId(), book.getBookId());
			}
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding genre failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Genre successfully added";
	}

	public String addPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			Integer bookId = pdao.savePublisherWithId(publisher);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Adding publisher failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Publisher successfully added";
	}

	public String editAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.editAuthor(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating author failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Author successfully updated";
	}

	public String editBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.editBook(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating book failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Book successfully updated";
	}

	public String editCopies(Copies copies) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			CopiesDAO cdao = new CopiesDAO(conn);
			cdao.editCopies(copies);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating copies failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Copies successfully updated";
	}

	public String editBookLoans(Loan loan) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO ldao = new LoanDAO(conn);
			ldao.editLoan(loan);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating loan failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Loan successfully updated";
	}

	public String editBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.editBorrower(borrower);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating borrower failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Borrower successfully updated";
	}

	public String editBranch(Branch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO dao = new BranchDAO(conn);
			dao.editBranch(branch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating branch failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Branch successfully updated";
	}

	public String editGenre(Genre genre) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.editGenre(genre);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating genre failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Genre successfully updated";
	}

	public String editPublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.editPublisher(publisher);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Updating publisher failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Publisher successfully updated";
	}

	public String deleteAuthor(Author author) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			AuthorDAO adao = new AuthorDAO(conn);
			adao.deleteAuthor(author);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Deleting author failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Author deleted";
	}

	public String deleteBook(Book book) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Delete book failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Book deleted";
	}

	public String deleteCopies(Copies bc) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			CopiesDAO cdao = new CopiesDAO(conn);
			cdao.deleteCopies(bc);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Delete copies failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Copies deleted";
	}

	public String deleteBookLoans(Loan loan) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			LoanDAO ldao = new LoanDAO(conn);
			ldao.deleteLoan(loan);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Delete loan failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Loan deleted";
	}

	public String deleteBorrower(Borrower borrower) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BorrowerDAO bdao = new BorrowerDAO(conn);
			bdao.deleteBorrower(borrower);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Delete borrower failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Borrower deleted";
	}

	public String deleteBranch(Branch branch) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			BranchDAO bdao = new BranchDAO(conn);
			bdao.deleteBranch(branch);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Delete branch failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Branch deleted";
	}

	public String deleteGenre(Genre genre) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			GenreDAO gdao = new GenreDAO(conn);
			gdao.deleteGenre(genre);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Delete genre failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Genre deleted";
	}

	public String deletePublisher(Publisher publisher) throws SQLException {
		Connection conn = null;
		try {
			conn = connUtil.getConnection();
			PublisherDAO pdao = new PublisherDAO(conn);
			pdao.deletePublisher(publisher);
			conn.commit();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			System.out.println("Deleting publisher failed");
			if (conn != null) {
				conn.rollback();
			}
		} finally {
			if (conn != null)
				conn.close();
		}
		return "Publisher deleted";
	}
}