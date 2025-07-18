package springcourse.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import springcourse.models.Book;
import springcourse.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM PERSON", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findFirst().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into Person(full_name, year_of_birth) values (?,?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("update person set full_name = ?, year_of_birth = ? where id = ?",
                person.getFullName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("delete from Person where id = ?", id);
    }

    public List<Book> getPersonsBooks(int id){
        return jdbcTemplate.query("SELECT book_id as id, title, author, year_of_publish FROM book where book.id = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class));
    }
}
