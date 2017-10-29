package com.granzotto.marcio.loadsmartchallenge.models;

import java.util.List;

public class FlickrResponse {

	private FlickrPhotosResponse photos;
	private String stat;

	//region getters and setters

	public FlickrPhotosResponse getPhotos() {
		return photos;
	}

	public void setPhotos(FlickrPhotosResponse photos) {
		this.photos = photos;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}


	//endregion

	public class FlickrPhotosResponse {

		private int page;
		private int pages;
		private int perpage;
		private int total;
		private List<FlickrPhoto> photo;

		//region getters and setters

		public int getPage() {
			return page;
		}

		public void setPage(int page) {
			this.page = page;
		}

		public int getPages() {
			return pages;
		}

		public void setPages(int pages) {
			this.pages = pages;
		}

		public int getPerpage() {
			return perpage;
		}

		public void setPerpage(int perpage) {
			this.perpage = perpage;
		}

		public int getTotal() {
			return total;
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public List<FlickrPhoto> getPhoto() {
			return photo;
		}

		public void setPhoto(List<FlickrPhoto> photo) {
			this.photo = photo;
		}

		//endregion

	}
}
