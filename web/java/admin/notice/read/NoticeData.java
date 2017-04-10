package admin.notice.read;

import admin.notice.NoticeList;

public class NoticeData {
	private NoticeList noticeList;
	private NoticeContent noticeContent;
	public NoticeData(NoticeList noticeList, NoticeContent noticeContent) {
		// TODO Auto-generated constructor stub
		this.noticeList = noticeList;
		this.noticeContent = noticeContent;
	}
	public NoticeList getNoticeList() {
		return noticeList;
	}
	public NoticeContent getNoticeContent() {
		return noticeContent;
	}
}
