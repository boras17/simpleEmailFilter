package controllers.binders;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import services.EmailFilterService;

public class DeleteEmailFilterListener implements EventHandler<MouseEvent> {
    private final EmailFilterService emailFilterService;
    private final StringProperty id;

    public DeleteEmailFilterListener(EmailFilterService emailFilterService, StringProperty id){
        this.emailFilterService = emailFilterService;
        this.id = id;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        boolean result = this.emailFilterService.deleteEmailFilter(Integer.parseInt(this.id.getValue()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if(result){
            alert.setContentText("Success");
        }else{
            alert.setContentText("something went wrong");
        }
        alert.show();
    }
}
