package addressbook.lab4;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BuddyInfo> buddyInfos;

    public AddressBook() {
        this.buddyInfos = new ArrayList<>();
    }

    public List<BuddyInfo> getBuddyInfos() {
        return buddyInfos;
    }

    public void setBuddyInfos(List<BuddyInfo> buddyInfos) {
        this.buddyInfos = buddyInfos;
    }

    public void removeBuddyByIndex(int index) {
        if (index >= 0 && index < this.buddyInfos.size()) {
            this.buddyInfos.remove(index);
        }
    }

    public void addBuddy(BuddyInfo buddyInfo) {
        this.buddyInfos.add(buddyInfo);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (BuddyInfo buddyInfo : this.getBuddyInfos()) {
            stringBuilder.append(buddyInfo.toString()).append("\n");
        }

        return stringBuilder.toString();
    }
}
