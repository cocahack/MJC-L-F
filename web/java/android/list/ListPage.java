package android.list;

import java.util.List;

public class ListPage {
	private int total, currentPage, totalPages, startPage, endPage;
	private List<?> content;
	
	public ListPage(int total, int currentPage,int size, List<?> content) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		sizeHandler(size);
	}
	private void sizeHandler(int size){
		if(total==0){
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		}
		else{
			totalPages = total/size;
			if(total%size>0){
				totalPages++;
			}
		}
		int modVal = currentPage % 5;
		startPage = currentPage/5*5+1;
		if(modVal==0)startPage -= 5;
		endPage = startPage +4;
		if(endPage>totalPages)endPage = totalPages;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public List<?> getContent() {
		return content;
	}
	public void setContent(List<?> content) {
		this.content = content;
	}
}
