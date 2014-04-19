package processing.data;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;


public class Category {
	//Attributes
	
	private String categoryID;
	private String title;
	private String parentCategory;
		
	
	// Constructor

	public Category(String categoryID, String title, String parentCategory) {
		this.categoryID = categoryID;
		this.title = title;
		this.parentCategory = parentCategory;
	}
	
	public TreeItem toMajorTreeItem(Tree tree) {
		TreeItem trItem = new TreeItem(tree, SWT.NONE);
		//TODO set correct data depending on columns
		String [] tem = new String [3];
		tem[0] = categoryID;
		tem[1] = title;
		tem[2] = parentCategory;
		trItem.setData(tem);
		return trItem;
	}
	
	public TreeItem toSubTreeItem(TreeItem treeitem) {
		TreeItem trItem = new TreeItem(treeitem, SWT.NONE);
		//TODO set correct data depending on columns
		String [] tem = new String [3];
		tem[0] = categoryID;
		tem[1] = title;
		tem[2] = parentCategory;
		trItem.setData(tem);
		return trItem;
	}
	

	// Getters and Setters 
	 
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

	public String getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(String parentCategory) {
		this.parentCategory = parentCategory;
	}


}
