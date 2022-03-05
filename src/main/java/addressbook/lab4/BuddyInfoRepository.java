package addressbook.lab4;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BuddyInfoRepository extends CrudRepository<BuddyInfo, Long> {

    List<BuddyInfo> findByName(String lastName);
    List<BuddyInfo> findByPhoneNumber(String lastName);

    BuddyInfo findById(long id);
}
