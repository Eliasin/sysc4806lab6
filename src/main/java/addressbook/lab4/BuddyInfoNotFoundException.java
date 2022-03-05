package addressbook.lab4;

public class BuddyInfoNotFoundException extends RuntimeException {
    public BuddyInfoNotFoundException(Long id) {
        super("Buddy Info with id: " + id + " not found");
    }
}
