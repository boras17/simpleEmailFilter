package controllers.binders;

import fxmodels.EmailFilterFilteredEmailId;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import services.EmailFilterService;

public class AddFilteredEmailToEmailFilterListener implements EventHandler<MouseEvent> {

    private final EmailFilterFilteredEmailId emailFilterFilteredEmailId;
    private final EmailFilterService emailFilterService;

    public AddFilteredEmailToEmailFilterListener(EmailFilterFilteredEmailId emailFilterFilteredEmailId,
                                                 EmailFilterService emailFilterService) {
        this.emailFilterFilteredEmailId = emailFilterFilteredEmailId;
        this.emailFilterService = emailFilterService;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        int emailId = Integer.parseInt(this.emailFilterFilteredEmailId.getFilteredEmailId());
        int filterId = Integer.parseInt(this.emailFilterFilteredEmailId.getFilterId());

        this.emailFilterService.addFilteredEmailToEmailFilter(emailId, filterId);
    }
}
