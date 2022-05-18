package mappers;

import fxmodels.SavingStrategyFX;
import models.saves.SavingStrategy;

public class SavingStrategyMapper {
    public SavingStrategy fromSavingStrategyFxToSavingStrategy(SavingStrategyFX savingStrategyFX) {
        SavingStrategy savingStrategy = new SavingStrategy();
        savingStrategy.setSavingBy(savingStrategyFX.getSaveBy());
        savingStrategy.setPattern(savingStrategyFX.getPattern());
        return savingStrategy;
    }
}
