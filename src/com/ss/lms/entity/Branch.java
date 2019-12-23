/**
 * 
 */
package com.ss.lms.entity;

import java.util.*;


public class Branch {
	private int branchId;
	private String branchName;
	private String branchAddress;
	private List<Copies> branchCopies;
	/**
	 * @return the branchId
	 */
	public int getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the branchAddress
	 */
	public String getBranchAddress() {
		return branchAddress;
	}
	/**
	 * @param branchAddress the branchAddress to set
	 */
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	/**
	 * @return the branchCopies
	 */
	public List<Copies> getBranchCopies() {
		return branchCopies;
	}
	/**
	 * @param branchCopies the branchCopies to set
	 */
	public void setBranchCopies(List<Copies> branchCopies) {
		this.branchCopies = branchCopies;
	}
}
