package services;

import models.saves.SavingBy;
import models.saves.SavingStrategy;

public interface SavingStrategyService {
    void addNewSavingStrategy(SavingStrategy savingBy);
    void modifySavingStrategy(SavingStrategy savingStrategy);
    boolean deleteSavingStrategy(int id);
    void addSavingStrategyToEmailFilter(int emailFilterId, int savingStrategyId);
}
