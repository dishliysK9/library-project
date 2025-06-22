package com.dishoo.library_project.responsemodels;

import com.dishoo.library_project.entity.History;
import lombok.Data;

@Data
public class HistoryResponse {
    private Long id;
    private String userEmail;
    private String checkoutDate;
    private String returnedDate;
    private String title;
    private String author;
    private String description;
    private String img;

    public HistoryResponse(History history) {
        this.id = history.getId();
        this.userEmail = history.getUser().getEmail();
        this.checkoutDate = history.getCheckoutDate();
        this.returnedDate = history.getReturnedDate();
        this.title = history.getTitle();
        this.author = history.getAuthor();
        this.description = history.getDescription();
        this.img = history.getImg();
    }

    // getters & setters
}
