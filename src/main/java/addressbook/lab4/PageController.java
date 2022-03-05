package addressbook.lab4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

class BuddyInfoForm {
    private String name;
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}

@Controller
public class PageController {

    private final AddressBookRepository repository;

    public PageController(AddressBookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/classic/addressbooks/add/{id}")
    public RedirectView addAddressbookForm(@ModelAttribute BuddyInfoForm buddyInfoForm, @PathVariable Long id) {
        repository.findById(id).map(addressBook -> {
            addressBook.addBuddy(new BuddyInfo(buddyInfoForm.getName(), buddyInfoForm.getNumber()));

            return repository.save(addressBook);
        }).orElseThrow(() -> new AddressBookNotFoundException(id));

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/view/addressbook/" + id);

        return redirectView;
    }

    @GetMapping("/classic")
    public String classic(Model model) {
        List<AddressBook> addressBooks = repository.findAll();

        model.addAttribute("addressBooks", addressBooks);

        return "classic";
    }

    @GetMapping("/spa")
    public String spa() {
       return "spa";
    }

    @PostMapping("/classic/addressbooks")
    public RedirectView newAddressBook() {
        repository.save(new AddressBook());

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/classic");

        return redirectView;
    }

    @GetMapping("/view/addressbook/{id}")
    public String home(@PathVariable Long id, Model model) {
        AddressBook addressBook = repository.findById(id).orElseThrow(() -> new AddressBookNotFoundException(id));
        model.addAttribute("buddies", addressBook.getBuddyInfos());
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddyInfoForm", new BuddyInfoForm());

        return "classic-address-book";
    }
}
