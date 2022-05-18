package controllers.binders;

import fxmodels.FilterStrategyId;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import services.SavingStrategyService;

public class AddSavingStrategyToFilterListener implements EventHandler<MouseEvent> {
        private final FilterStrategyId filterStrategyId;
        private final SavingStrategyService savingStrategyService;

        public AddSavingStrategyToFilterListener(final FilterStrategyId filterStrategyId, SavingStrategyService savingStrategyService){
                this.filterStrategyId = filterStrategyId;
                this.savingStrategyService = savingStrategyService;
        }

        @Override
        public void handle(MouseEvent mouseEvent) {
                int filterId = Integer.parseInt(this.filterStrategyId.getFilterId());
                int strategyId = Integer.parseInt(this.filterStrategyId.getSavingStrategyId());

                this.savingStrategyService.addSavingStrategyToEmailFilter(filterId, strategyId);
        }
}