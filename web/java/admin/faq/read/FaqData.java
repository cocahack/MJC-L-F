package admin.faq.read;

import admin.faq.FaqList;

public class FaqData {
	private FaqList faqList;

	private FaqContent faqContent;
	public FaqData(FaqList faqList, FaqContent faqContent) {
		this.faqList = faqList;
		this.faqContent = faqContent;
	}
	
	public FaqList getFaqList() {
		return faqList;
	}
	public FaqContent getFaqContent() {
		return faqContent;
	}
}
