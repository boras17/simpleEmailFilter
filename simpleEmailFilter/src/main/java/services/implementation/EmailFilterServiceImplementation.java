package services.implementation;

import models.filters.EmailFilter;
import models.filters.FilteredEmail;
import models.saves.SavingStrategy;
import repositories.AbstractRepository;
import services.EmailFilterService;

import java.util.Set;

public class EmailFilterServiceImplementation implements EmailFilterService {

    private AbstractRepository<EmailFilter> emailFilterRepository;
    private AbstractRepository<FilteredEmail> filteredEmailRepository;

    public EmailFilterServiceImplementation(AbstractRepository<EmailFilter> emailFilterRepository,
                                            AbstractRepository<FilteredEmail> filteredEmailRepository) {
        this.emailFilterRepository = emailFilterRepository;
        this.filteredEmailRepository = filteredEmailRepository;
    }
    public EmailFilterServiceImplementation(AbstractRepository<EmailFilter> emailFilterRepository) {
        this.emailFilterRepository = emailFilterRepository;
    }


    @Override
    public void createNewEmailFilter(EmailFilter emailFilter) {
        this.emailFilterRepository.save(emailFilter);
    }

    @Override
    public void addFilteredEmailToEmailFilter(int filteredEmailId, int emailFilterId) {
        EmailFilter emailFilter = this.emailFilterRepository.findById(emailFilterId).orElseThrow();

        FilteredEmail filteredEmail = this.filteredEmailRepository.findById(filteredEmailId).orElseThrow();

        emailFilter.addFilteredEmail(filteredEmail);

        this.emailFilterRepository.update(emailFilter);
    }

    @Override
    public void deleteFilteredEmailFromEmailFilter(int emailFilterId, Set<Integer> filteredEmailsIds) {
        EmailFilter emailFilter = this.emailFilterRepository.findById(emailFilterId).orElseThrow();
        for(int id: filteredEmailsIds){
            emailFilter.deleteFilteredEmail(id);
        }
        this.emailFilterRepository.update(emailFilter);
    }

    @Override
    public boolean deleteEmailFilter(int emailFilterId) {
        return this.emailFilterRepository.deleteById(emailFilterId);
    }

}
