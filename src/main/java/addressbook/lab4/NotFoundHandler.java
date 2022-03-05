package addressbook.lab4;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class NotFoundHandler {

    @ResponseBody
    @ExceptionHandler(AddressBookNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String addressBookNotFoundHandler(AddressBookNotFoundException e) {
        return e.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BuddyInfoNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String buddyInfoNotFoundHandler(BuddyInfoNotFoundException e) {
        return e.getMessage();
    }
}
