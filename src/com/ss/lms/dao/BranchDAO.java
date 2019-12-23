/**
 * 
 */
package com.ss.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ss.lms.entity.Branch;

public class BranchDAO extends BaseDAO<Branch> {

	public BranchDAO(Connection conn) {
		super(conn);
	}

	public void saveBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("INSERT INTO tbl_library_branch (branchName, branchAddress) values(?, ?)", new Object[] { 
				branch.getBranchName(), branch.getBranchAddress() });
	}
	
	public Integer saveBranchWithId(Branch branch) throws ClassNotFoundException, SQLException {
		return saveWithID("INSERT INTO tbl_library_branch (branchName, branchAddress) values(?, ?)", new Object[] { 
				branch.getBranchName(), branch.getBranchAddress() });
	}

	public void editBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("UPDATE tbl_library_branch set branchName = ?, branchAddress = ? where branchId = ?", new Object[] { branch.getBranchName(), branch.getBranchAddress(), branch.getBranchId() });
	}

	public void deleteBranch(Branch branch) throws ClassNotFoundException, SQLException {
		save("DELETE FROM tbl_library_branch where branchId = ?", new Object[] { branch.getBranchId() });
	}

	public List<Branch> readBranchs() throws ClassNotFoundException, SQLException {
		return read("SELECT * FROM tbl_library_branch", null);
	}
	
	public List<Branch> readBranchByTitle(String searchString) throws ClassNotFoundException, SQLException {
		searchString = "%" + searchString + "%";
		return read("SELECT * FROM tbl_library_branch where branchName LIKE", new Object[] { searchString });
	}

	@Override
	public List<Branch> extractData(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Branch> branchs = new ArrayList<>();
		CopiesDAO cdao = new CopiesDAO(conn);
		while (rs.next()) {
			Branch branch = new Branch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branch.setBranchCopies(cdao.readFirstLevel("SELECT * FROM tbl_book_copies where branchId = ?",
					new Object[] { branch.getBranchId() }));
			branchs.add(branch);
		}
		return branchs;
	}

	@Override
	public List<Branch> extractDataFirstLevel(ResultSet rs) throws SQLException, ClassNotFoundException {
		List<Branch> branchs = new ArrayList<>();
		while (rs.next()) {
			Branch branch = new Branch();
			branch.setBranchId(rs.getInt("branchId"));
			branch.setBranchName(rs.getString("branchName"));
			branch.setBranchAddress(rs.getString("branchAddress"));
			branchs.add(branch);
		}
		return branchs;
	}

}
