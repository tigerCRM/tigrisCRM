package com.tiger.crm.common.util;

import java.util.ArrayList;

public class PageUtils
{
	public static ArrayList makePages(int Ncount, int recordCountPerPage, int page) {

		int maxPage = (int) (Ncount / recordCountPerPage + ((Ncount % recordCountPerPage)>0?1:0));
		ArrayList pages = new ArrayList();
		pages.add(""+1);
		int startPage = page - 5;
		int endPage = page + 5;
		if (startPage > 2) pages.add("..");
		for (int i=startPage; i<=endPage; i++) {
			if (i<=1) continue;
			if (i>maxPage) break;
			pages.add(""+i);
		}
		if (endPage < maxPage) {
			pages.add("..");
			pages.add(""+maxPage);
		}
		return pages;
	}
}
