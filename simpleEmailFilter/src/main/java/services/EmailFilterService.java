package services;

import models.filters.EmailFilter;
import models.filters.FilteredEmail;

import java.util.Set;

public interface EmailFilterService {
    void createNewEmailFilter(EmailFilter emailFilter);
    void addFilteredEmailToEmailFilter(int emailId, int emailFilterId);
    void deleteFilteredEmailFromEmailFilter(int emailFilterId, Set<Integer> filteredEmailsIds);
    boolean deleteEmailFilter(int emailFilterId);

}
