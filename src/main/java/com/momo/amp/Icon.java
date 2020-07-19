package com.momo.amp;

public class Icon {
	private String contentText;
	private String bgColor;
	private String contentColor;
	
	public Icon(String contentText, String bgColor, String contentColor) {
		this.contentText = contentText;
		this.bgColor = bgColor;
		this.contentColor = contentColor;
	}
	
	public String getContent() {
		return this.contentText;
	}
	
	public String getBgColor() {
		return this.bgColor;
	}
	
	public String getContentColor() {
		return this.contentColor;
	}
}
