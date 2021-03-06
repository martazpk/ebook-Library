package pl.marta.kopp.library;

import org.junit.Test;
import pl.marta.kopp.communication.Response;
import pl.marta.kopp.domain.model.Author;
import pl.marta.kopp.domain.model.Book;
import pl.marta.kopp.domain.model.User;
import pl.marta.kopp.domain.service.BookService;
import pl.marta.kopp.domain.service.BorrowingService;
import pl.marta.kopp.persistence.*;

import java.util.List;

import static org.junit.Assert.*;

public class BorrowingControllerTest {
    private final UserStorage userStorage=new UserStorageJpa();
    private final BookStorageJpa bookStorage=new BookStorageJpa();
    private final BookService bookService=new BookService(bookStorage);
    private final BorrowStorage borrowStorage=new BorrowStorageJpa();
    private final BorrowingService borrowingService=new BorrowingService(borrowStorage,userStorage,bookStorage);
    private final BorrowingController controller=new BorrowingController(bookService,borrowingService);
    private static final String SOME_TITLE="Harry Potter";
    private static final String SOME_ISBN="9876543456";
    private static final List<Author> AUTHORS=null;
    private static final String SOME_LOGIN="marta";
    private static final String ANOTHER_LOGIN="agnieszka";
    private static final String SOME_PASSWORD="kopp";
    private static final long SOME_USER_ID=123456L;
    private static final long SOME_BOOK_ID=123456L;


    @Test
    public void shouldNotBorrowBookWhenBookIsNotInStorage() throws Exception {
        Response result=controller.borrowBook(SOME_BOOK_ID,SOME_USER_ID);

        assertFalse(result.getSuccess());
        assertEquals("Sorry, this book is not our catalog.",result.getMessage());
    }

    @Test
    public void shouldNotBorrowBookWhenBookIsAlreadyBorrowed() throws Exception {
        long bookId=givenBook(SOME_ISBN,SOME_TITLE,AUTHORS);
        long user1ID=givenUser(SOME_LOGIN,SOME_PASSWORD);
        long user2Id=givenUser(ANOTHER_LOGIN,SOME_PASSWORD);
        controller.borrowBook(bookId,user1ID);

        Response result=controller.borrowBook(bookId,user2Id);
        assertFalse(result.getSuccess());
        assertEquals("Sorry, this book is already borrowed",result.getMessage());
    }

    @Test
    public void shouldBorrowBook() throws Exception {
        long bookId=givenBook(SOME_ISBN,SOME_TITLE,AUTHORS);
        long userId=givenUser(SOME_LOGIN,SOME_PASSWORD);
        Response result=controller.borrowBook(bookId,userId);

        assertTrue(result.getSuccess());
        assertTrue(borrowStorage.isExistsBookId(bookId));

    }

    private long givenUser(String name, String surname){
        User user=new User(name,surname);
        userStorage.addUser(user);
        return user.getId();
    }

    private long givenBook(String isbn,String title, List<Author>authors){
        Book book=new Book(isbn,title,authors);
       return bookService.add(book);
    }
}