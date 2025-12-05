package io.github.rickkybalboa;

/* import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; */

//import java.math.BigDecimal;

//import java.time.*;

public class BookTest extends AbstractLibItemTest<Book> {

    @Override
    protected Book createItemWith(String id) {
        return new Book(
            "001",
            "Mr Blobbo",
            "The Book of Blobbo",
            "Religion"
        );
    }

}
