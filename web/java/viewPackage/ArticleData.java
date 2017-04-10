package viewPackage;

public class ArticleData {
	private ArticleList articleList;
	private ViewContent viewContent;
	
	public ArticleData(ViewContent viewContent) {
		// TODO Auto-generated constructor stub
		this.viewContent = viewContent;
	}
	public ArticleData(ArticleList articleList, ViewContent viewContent) {
		// TODO Auto-generated constructor stub
		this.articleList = articleList;
		this.viewContent = viewContent;
	}
	public ArticleList getArticleList() {
		return articleList;
	}
	public ViewContent getViewContent() {
		return viewContent;
	}
}
