package controllers.binders;

import fxmodels.FilteredEmailFX;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import mappers.FilteredEmailMapper;
import models.filters.FilteredEmail;
import repositories.AbstractRepository;

public class CreateNewFilteredEmail implements EventHandler<MouseEvent> {
    private final FilteredEmailFX filteredEmailFX;
    private final AbstractRepository<FilteredEmail> filteredEmailAbstractRepository;

    public CreateNewFilteredEmail (FilteredEmailFX filteredEmailFX) {
        this.filteredEmailFX = filteredEmailFX;
        this.filteredEmailAbstractRepository = new AbstractRepository<>(FilteredEmail.class);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        FilteredEmail filteredEmail = FilteredEmailMapper.fromFilteredEmailFXtoFilteredEmail(this.filteredEmailFX);
        this.filteredEmailAbstractRepository.save(filteredEmail);
    }
}
