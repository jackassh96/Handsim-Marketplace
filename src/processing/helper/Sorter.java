package processing.helper;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import processing.data.Category;

public class Sorter {
	
	/**
	 * Sorts the category array by category ID (ASC) 
	 * @param 	Category []			category array you want to sort
	 * @return	Category []			sorted category array
	 */
	public static Category [] sortCategoryByID(Category [] cats) {	
		Arrays.sort(cats, new Comparator<Category>(){
			@Override
			public int compare(Category arg0, Category arg1) {
				return ((Integer)Integer.parseInt(arg0.getCategoryID())).compareTo((Integer)(Integer.parseInt(arg1.getCategoryID())));
			}
		});
		return cats;	
		
	}
	

	/**
	 * Sorts the tableItems (myAssignemnt table) by string values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortStringTableItemsMyAssignments(Table table, int columnindex, boolean asc) {
	if (asc) {
		asc = false;
		TableItem[] items = table.getItems();
        Collator collator = Collator.getInstance(Locale.getDefault());
        int k = 0;
        while (k < items.length) {
	        for (int i = 1; i < items.length; i++) {
	          String value1 = items[i].getText(columnindex);
	          for (int j = 0; j < i; j++) {
	            String value2 = items[j].getText(columnindex);
	            if (collator.compare(value1, value2) < 0) {
	              String[] values = { items[i].getText(0),
	                  items[i].getText(1), items[i].getText(2) };
	              Image buf = items[i].getImage(3);
	              String data = (String) items[i].getData("id");
	              items[i].dispose();
	              TableItem item = new TableItem(table, SWT.NONE, j);
	              item.setText(values);
	              item.setImage(3,buf);
	              item.setData("id",data);
	              items = table.getItems();
	            }
	       
	          }
	        }
        k++;
        }
	} else {
			asc = true;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) > 0) {
		              String[] values = { items[i].getText(0),
		                  items[i].getText(1), items[i].getText(2) };
		              String data = (String) items[i].getData("id");
		              Image buf = items[i].getImage(3);
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setImage(3,buf);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		}
	}
	
	/**
	 * Sorts the tableItems (myAssignemnt table) by string values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortDateTableItemsMyAssignments(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
	        for (int i = 1; i < items.length; i++) {
	          String value1 = items[i].getText(columnindex);
	          for (int j = 0; j < i; j++) {
	            String value2 = items[j].getText(columnindex);
	            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
	              String[] values = { items[i].getText(0),
	                  items[i].getText(1), items[i].getText(2) };
	              String data = (String) items[i].getData("id");
	              Image buf = items[i].getImage(3);
	              items[i].dispose();
	              TableItem item = new TableItem(table, SWT.NONE, j);
	              item.setText(values);
	              item.setImage(3,buf);
	              item.setData("id",data);
	              items = table.getItems();
	            }
	       
	          }
	        }
	        k++;
	        }
			} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
			              String[] values = { items[i].getText(0),
			                  items[i].getText(1), items[i].getText(2) };
			              Image buf = items[i].getImage(3);
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setImage(3,buf);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	/**
	 * Sorts the tableItems (company table) by string values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortStringTableItemsCompanies(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) < 0) {
		              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
		            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id", data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        Collator collator = Collator.getInstance(Locale.getDefault());
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (collator.compare(value1, value2) > 0) {
			              String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id", data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	/**
	 * Sorts the tableItems (company table) by number values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortNumberTableItemsCompanies(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (((Integer.compare(Integer.parseInt(value1), Integer.parseInt(value2)))) < 0) {
		              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
		            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (((Integer.compare(Integer.parseInt(value1), Integer.parseInt(value2)))) > 0) {
			              String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3),
            		  			  items[i].getText(4), items[i].getText(5), items[i].getText(6), items[i].getText(7) };
			              String data = (String) items[i].getData("id");
			              Image buf = items[i].getImage(3);
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setImage(3,buf);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	/**
	 * Sorts the tableItems (Assignment table) by string values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortStringTableItemsAssignments(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) < 0) {
		              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
		            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5) };
		              Image buf = items[i].getImage(6);
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              item.setImage(6, buf);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        Collator collator = Collator.getInstance(Locale.getDefault());
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (collator.compare(value1, value2) > 0) {
			              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
			              Image buf = items[i].getImage(6);
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              item.setImage(6,buf);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
		
	/**
	 * Sorts the tableItems (Assignment table) by date values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortDateTableItemsAssignments(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
	        for (int i = 1; i < items.length; i++) {
	          String value1 = items[i].getText(columnindex);
	          for (int j = 0; j < i; j++) {
	            String value2 = items[j].getText(columnindex);
	            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
	              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
    		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
	              String data = (String) items[i].getData("id");
	              Image buf = items[i].getImage(6);
	              items[i].dispose();
	              TableItem item = new TableItem(table, SWT.NONE, j);
	              item.setText(values);
	              item.setData("id",data);
	              item.setImage(6,buf);
	              items = table.getItems();
	            }
	       
	          }
	        }
	        k++;
	        }
			} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
			              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
			              Image buf = items[i].getImage(6);
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              item.setImage(6, buf);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
		
	/**
	 * Sorts the tableItems (Offer table) by string values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortStringTableItemsOffer(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) < 0) {
		              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
		            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		          }
		        }
		        k++;
	        }
		} else {
			asc = true;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) > 0) {
		              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
        		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              items = table.getItems();
		              item.setData("id",data);
		            }
		       
		          }
		        }
	        k++;
	        }
		}
	}
	
	/**
	 * Sorts the tableItems (Offer table) by date values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortDateTableItemsOffer(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
	        for (int i = 1; i < items.length; i++) {
	          String value1 = items[i].getText(columnindex);
	          for (int j = 0; j < i; j++) {
	            String value2 = items[j].getText(columnindex);
	            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
	              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
    		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
	              String data = (String) items[i].getData("id");
	              items[i].dispose();
	              TableItem item = new TableItem(table, SWT.NONE, j);
	              item.setText(values);
	              item.setData("id",data);
	              items = table.getItems();
	            }
	       
	          }
	        }
	        k++;
	        }
			} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
			              String[] values = { items[i].getText(0),items[i].getText(1), items[i].getText(2),
            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5)  };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	/**
	 * Sorts the tableItems (Offer table) by number values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortNumberTableItemsOffer(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) < 0) {
		              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2),
		            		  			  items[i].getText(3), items[i].getText(4), items[i].getText(5) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) > 0) {
			              String[] values = {items[i].getText(0), items[i].getText(1), items[i].getText(2),
			            		  			 items[i].getText(3), items[i].getText(4), items[i].getText(5)};
			              Image buf = items[i].getImage(3);
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              item.setImage(3,buf);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}

	/**
	 * Sorts the tableItems (nextOffer table) by number values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortNumberTableItemsNextOffer(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) < 0) {
		              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (((Double.compare(Double.parseDouble(value1), Double.parseDouble(value2)))) > 0) {
			              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
			              Image buf = items[i].getImage(3);
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              item.setImage(3,buf);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	/**
	 * Sorts the tableItems (nextOffer table) by string date (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortDateTableItemsNextOffer(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        int k = 0;
	        while (k < items.length) {
	        for (int i = 1; i < items.length; i++) {
	          String value1 = items[i].getText(columnindex);
	          for (int j = 0; j < i; j++) {
	            String value2 = items[j].getText(columnindex);
	            if (new DatumFull(value1).compareTo(new DatumFull(value2)) < 0) {
	              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
	              String data = (String) items[i].getData("id");
	              items[i].dispose();
	              TableItem item = new TableItem(table, SWT.NONE, j);
	              item.setText(values);
	              item.setData("id",data);
	              items = table.getItems();
	            }
	       
	          }
	        }
	        k++;
	        }
			} else {
				asc = true;
				TableItem[] items = table.getItems();
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (new DatumFull(value1).compareTo(new DatumFull(value2)) > 0) {
			              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              item.setData("id",data);
			              items = table.getItems();
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}
	
	/**
	 * Sorts the tableItems (nextOffer table) by string values (ASC or DESC) 
	 * @param 	table (parent of the tableItems)
	 * @param	int (index of the column where you can find the value to compare) 
	 * @param 	boolean (true if sorting ascending and false if sorting descending)
	 */
	public static void sortStringTableItemsNextOffer(Table table, int columnindex, boolean asc) {
		if (asc) {
			asc = false;
			TableItem[] items = table.getItems();
	        Collator collator = Collator.getInstance(Locale.getDefault());
	        int k = 0;
	        while (k < items.length) {
		        for (int i = 1; i < items.length; i++) {
		          String value1 = items[i].getText(columnindex);
		          for (int j = 0; j < i; j++) {
		            String value2 = items[j].getText(columnindex);
		            if (collator.compare(value1, value2) < 0) {
		              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
		              String data = (String) items[i].getData("id");
		              items[i].dispose();
		              TableItem item = new TableItem(table, SWT.NONE, j);
		              item.setText(values);
		              item.setData("id",data);
		              items = table.getItems();
		            }
		       
		          }
		        }
	        k++;
	        }
		} else {
				asc = true;
				TableItem[] items = table.getItems();
		        Collator collator = Collator.getInstance(Locale.getDefault());
		        int k = 0;
		        while (k < items.length) {
			        for (int i = 1; i < items.length; i++) {
			          String value1 = items[i].getText(columnindex);
			          for (int j = 0; j < i; j++) {
			            String value2 = items[j].getText(columnindex);
			            if (collator.compare(value1, value2) > 0) {
			              String[] values = { items[i].getText(0), items[i].getText(1), items[i].getText(2), items[i].getText(3) };
			              String data = (String) items[i].getData("id");
			              items[i].dispose();
			              TableItem item = new TableItem(table, SWT.NONE, j);
			              item.setText(values);
			              items = table.getItems();
			              item.setData("id",data);
			            }
			       
			          }
			        }
		        k++;
		        }
			}
		}

}
