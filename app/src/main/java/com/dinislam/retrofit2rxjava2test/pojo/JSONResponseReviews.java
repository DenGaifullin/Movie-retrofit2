package com.dinislam.retrofit2rxjava2test.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JSONResponseReviews {
        @SerializedName("id")
        @Expose
        private int id;
        @SerializedName("page")
        @Expose
        private int page;
        @SerializedName("results")
        @Expose
        private List<Comment> comments = null;
        @SerializedName("total_pages")
        @Expose
        private int totalPages;
        @SerializedName("total_results")
        @Expose
        private int totalResults;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<Comment> getComments() {
            return comments;
        }

        public void setComments(List<Comment> comments) {
            this.comments = comments;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }


}
