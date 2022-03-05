package addressbook.lab4;

public class AddressBookNotFoundException extends RuntimeException {
    public AddressBookNotFoundException(Long id) {
        super("Could not find address book with id: " + id);
    }
}
