package controllers.binders;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.filters.EmailFilter;
import services.EmailFilterService;

public class CreateNewEmailfilter implements EventHandler<MouseEvent> {
    private final EmailFilterService emailFilterService;
    private final StringProperty emailFilterName;

    public CreateNewEmailfilter(final EmailFilterService emailFilterService, StringProperty emailFilterName) {
        this.emailFilterService = emailFilterService;
        this.emailFilterName = emailFilterName;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        EmailFilter emailFilter = new EmailFilter();
        emailFilter.setName(this.emailFilterName.getValue());
        this.emailFilterService.createNewEmailFilter(emailFilter);
    }
}
