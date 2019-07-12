package cn.kcyf.tools.es.controller;

import cn.kcyf.tools.es.models.Book;
import cn.kcyf.tools.es.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/book")
@RestController
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping(value = "/add_index", method = RequestMethod.POST)
    public ResponseEntity<String> indexDoc(@RequestBody Book book) {
        System.out.println("book===" + book);
        bookRepository.save(book);
        return new ResponseEntity<>("save executed!", HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getAll(int page, int size) {
        Page<Book> all = bookRepository.findAll(PageRequest.of(page, size));
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<Iterable> getByName(@PathVariable("name") String name,int page, int size) {
        Page<Book> all = bookRepository.findByName(name, PageRequest.of(page, size));
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Book> updateBook(@PathVariable("id") String id,
                                           @RequestBody Book updateBook) {
        Book book = bookRepository.findById(id).get();
        if (!StringUtils.isEmpty(updateBook.getId())) {
            book.setId(updateBook.getId());
        }
        if (!StringUtils.isEmpty(updateBook.getName())) {
            book.setName(updateBook.getName());
        }
        if (!StringUtils.isEmpty(updateBook.getAuthor())) {
            book.setAuthor(updateBook.getAuthor());
        }
        bookRepository.save(book);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteBook(@PathVariable("id") String id) {
        bookRepository.deleteById(id);
        return new ResponseEntity<>("delete execute!", HttpStatus.OK);
    }
}
