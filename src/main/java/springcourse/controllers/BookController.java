package springcourse.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springcourse.dao.BookDAO;
import springcourse.models.Book;
import springcourse.models.Person;


@Controller
@RequestMapping("/books")
public class BookController {

    private final BookDAO bookDAO;

    @Autowired
    public BookController(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }



    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("person", bookDAO.getHolderByBookId(id));
        model.addAttribute("people", bookDAO.getAllPotentialHolders());
        return "books/show";
    }

    @GetMapping("/new")
    public String create(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "books/new";
        }
        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id){
        if(bindingResult.hasErrors()){
            return "books/edit";
        }
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/setOwnership/{id}")
    public String setOwnership(@PathVariable("id") int bookId, @ModelAttribute("person") Person person){
        bookDAO.setOwner(bookId, person.getId());
        return "redirect:/books";
    }

    @PostMapping("/setFree/{id}")
    public String setOwnership(@PathVariable("id") int bookId){
        bookDAO.setOwner(bookId, null);
        return "redirect:/books";
    }



}
