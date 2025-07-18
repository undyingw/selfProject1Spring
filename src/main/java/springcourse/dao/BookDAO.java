package springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Book;
import springcourse.models.Person;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT book_id as id, title, author, year_of_publish FROM book",
                new BeanPropertyRowMapper<>(Book.class));
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT book_id as id, title, author, year_of_publish FROM book WHERE book_id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findFirst().orElse(null);
    }

    public void save(Book book) {
        jdbcTemplate.update("insert into book(title,author, year_of_publish) values (?,?,?)",
                book.getTitle(), book.getAuthor(),book.getYear_of_publish());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, year_of_publish = ? WHERE book_id = ?",
                book.getTitle(), book.getAuthor(), book.getYear_of_publish(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ?", id);
    }
    public Person getHolderByBookId(int id){
        return jdbcTemplate.query("select * from person join book on book.id = person.id where book.book_id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findFirst().orElse(null);
    }

    public List<Person> getAllPotentialHolders() {
        return jdbcTemplate.query("SELECT * FROM PERSON", new BeanPropertyRowMapper<>(Person.class));
    }

    public void setOwner(int bookId, Integer personId) {
        jdbcTemplate.update("UPDATE book SET id = ? WHERE book_id = ?", personId, bookId);
    }
}
