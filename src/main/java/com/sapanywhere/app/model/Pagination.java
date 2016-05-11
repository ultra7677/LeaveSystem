package com.sapanywhere.app.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import com.sapanywhere.app.entity.Leave;

public class Pagination {

	public static final int Pagination_Button_Count = 5;

	public Pagination(Page<Leave> page) {
		if (page.getTotalPages() <= 1) {
			this.show = false;
		} else {

			this.currentIndex = page.getNumber();
			int startIndex = 0;
			int endIndex = 0;

			int PaginationIndex = this.currentIndex / Pagination_Button_Count;
			int LastPaginationIndex = (page.getTotalPages() - 1)
					/ Pagination_Button_Count;
			if (PaginationIndex == 0) {
				this.showPreviousButton = false;
			} else {
				startIndex = PaginationIndex * Pagination_Button_Count;
			}

			if (PaginationIndex == LastPaginationIndex) {
				endIndex = page.getTotalPages();
				this.showNextButton = false;
			} else {
				endIndex = (PaginationIndex + 1) * Pagination_Button_Count;
			}

			for (int index = startIndex + 1; index <= endIndex; index++) {
				this.pageNumbers.add(index);
			}
			this.previousPageIndex = startIndex - 1;
			this.nextPageIndex = endIndex + 1;
		}
	}

	private boolean show = true;
	private boolean showPreviousButton = true;
	private boolean showNextButton = true;
	private List<Integer> pageNumbers = new ArrayList<Integer>();
	private int previousPageIndex;
	private int nextPageIndex;
	private int currentIndex;

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public boolean isShowPreviousButton() {
		return showPreviousButton;
	}

	public void setShowPreviousButton(boolean showPreviousButton) {
		this.showPreviousButton = showPreviousButton;
	}

	public boolean isShowNextButton() {
		return showNextButton;
	}

	public void setShowNextButton(boolean showNextButton) {
		this.showNextButton = showNextButton;
	}

	public List<Integer> getPageNumbers() {
		return pageNumbers;
	}

	public int getPreviousPageIndex() {
		return previousPageIndex;
	}

	public int getNextPageIndex() {
		return nextPageIndex;
	}

	public int getCurrentIndex() {
		return currentIndex + 1;
	}
}
