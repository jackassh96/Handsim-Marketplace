package processing.data;

import java.util.ArrayList;

public class Category {
	private String categoryID;
	private String title;
	private Category parentCategory;
	private ArrayList<Category> subCategoryList;
	
	public Category() {
		// TODO Auto-generated constructor stub
	}

	/*Getters and Setters 
	 */
	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public ArrayList<Category> getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(ArrayList<Category> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

}
