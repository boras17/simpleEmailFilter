package services.implementation;

import models.filters.EmailFilter;
import models.saves.SavingStrategy;
import repositories.AbstractRepository;
import services.SavingStrategyService;

public class SavingByServiceImplementation implements SavingStrategyService {

    private final AbstractRepository<SavingStrategy> savingStrategyRepository;
    private final AbstractRepository<EmailFilter> emailFilterAbstractRepository;


    public SavingByServiceImplementation() {
        this.savingStrategyRepository = new AbstractRepository<>(SavingStrategy.class);
        this.emailFilterAbstractRepository = new AbstractRepository<>(EmailFilter.class);
    }

    @Override
    public void addNewSavingStrategy(SavingStrategy savingBy) {
        savingStrategyRepository.save(savingBy);
    }

    @Override
    public void modifySavingStrategy(SavingStrategy savingStrategy) {
        savingStrategyRepository.update(savingStrategy);
    }

    @Override
    public boolean deleteSavingStrategy(int id) {
        return this.savingStrategyRepository.deleteById(id);
    }

    @Override
    public void addSavingStrategyToEmailFilter(int emailFilterId, int savingStrategyId) {
        EmailFilter emailFilter = this.emailFilterAbstractRepository.findById(emailFilterId).orElseThrow();
        SavingStrategy savingStrategy = this.savingStrategyRepository.findById(savingStrategyId).orElseThrow();
        emailFilter.getSavingStrategies().add(savingStrategy);
        this.emailFilterAbstractRepository.update(emailFilter);
    }
}
