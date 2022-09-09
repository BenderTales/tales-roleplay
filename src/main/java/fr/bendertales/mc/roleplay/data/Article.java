package fr.bendertales.mc.roleplay.data;

import java.time.LocalDateTime;


public class Article {

	private LocalDateTime newsDateTime;
	private String articleContent;

	public LocalDateTime getNewsDateTime() {
		return newsDateTime;
	}

	public void setNewsDateTime(LocalDateTime newsDateTime) {
		this.newsDateTime = newsDateTime;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
}
