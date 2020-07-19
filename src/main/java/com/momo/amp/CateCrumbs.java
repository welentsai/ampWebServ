package com.momo.amp;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class CateCrumbs {

	private static boolean notFirst = false;

	public static String getHtml(List<String> cateList) {
		String html = "";
		for (String catName : cateList) {
			html += makeCateTag(catName);
		}

		return html;
	}

	private static String makeCateTag(String cateName) {
		String tagHtml = "<li></li>";
		Document doc = Jsoup.parse(tagHtml);

		if (notFirst) { // add SpanTag
			Element spanElem = doc.select("li").first().appendElement("span");
			spanElem.text(">");
		}

		Element aElem = doc.select("li").first().appendElement("a");
		aElem.text(cateName);
		notFirst = true;

		//System.out.println(doc.body().html());

		return doc.body().html();
	}

}
