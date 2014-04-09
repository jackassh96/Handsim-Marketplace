package processing.data;


public class Category {
	private String categoryID;
	private String title;
	private Category parentCategory;
	private Category[] subCategoryList;
	
	public Category(String categoryID, String title, Category parentCategory,
			Category[] subCategoryList) {
		this.categoryID = categoryID;
		this.title = title;
		this.parentCategory = parentCategory;
		this.subCategoryList = subCategoryList;
	}

	/*
	 * Getters and Setters 
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

	public Category[] getSubCategoryList() {
		return subCategoryList;
	}

	public void setSubCategoryList(Category[] subCategoryList) {
		this.subCategoryList = subCategoryList;
	}

}
