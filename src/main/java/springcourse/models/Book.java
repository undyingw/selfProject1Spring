package springcourse.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NotEmpty(message = "title should not be empty")
    @Size(min = 2, max = 30, message = "title should be between 2 and 30 characters")
    private String title;
    @NotEmpty(message = "author should not be empty")
    @Size(min = 2, max = 30, message = "author should be between 2 and 30 characters")
    private String author;
    @Min(value = 1900, message="Age of book should be greater than 100")
    private int year_of_publish;

    public Book(int id,String title, String author, int year_of_publish) {
        this.title = title;
        this.author = author;
        this.year_of_publish = year_of_publish;
        this.id = id;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear_of_publish() {
        return year_of_publish;
    }

    public void setYear_of_publish(int year_of_publish) {
        this.year_of_publish = year_of_publish;
    }
}
