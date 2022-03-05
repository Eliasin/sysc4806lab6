package addressbook.lab4;

import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AddressBookController {
    private final AddressBookRepository repository;

    public AddressBookController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/addressbooks")
    AddressBook newAddressBook() {
        return repository.save(new AddressBook());
    }

    @GetMapping("/addressbooks/{id}")
    AddressBook one(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new AddressBookNotFoundException(id));
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/addressbooks")
    List<AddressBook> allAddressBooks() {
        return repository.findAll();
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/addressbooks/add/{id}")
    AddressBook addBuddy(@RequestBody BuddyInfo buddyInfo, @PathVariable Long id) {
        return repository.findById(id).map(addressBook -> {
            addressBook.addBuddy(buddyInfo);

            return repository.save(addressBook);
        }).orElseThrow(() -> new AddressBookNotFoundException(id));
    }

    @DeleteMapping("/addressbooks/{addressBookId}/{buddyId}")
    AddressBook removeBuddy(@PathVariable Long addressBookId, @PathVariable Long buddyId) {
        return repository.findById(addressBookId).map(addressBook -> {
            int index = -1;

            for (int i = 0; i < addressBook.getBuddyInfos().size(); i++) {
                if (addressBook.getBuddyInfos().get(i).id == buddyId) {
                    index = i;
                    break;
                }
            }

            if (index != -1) {
                addressBook.removeBuddyByIndex(index);

                return repository.save(addressBook);
            }

            throw new BuddyInfoNotFoundException(buddyId);
        }).orElseThrow(() -> new AddressBookNotFoundException(addressBookId));
    }
}
