package controllers.loaders;

import com.sun.source.tree.Tree;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.MouseEvent;
import models.filters.EmailFilter;
import models.filters.FilteredEmail;
import models.saves.SavingBy;
import models.saves.SavingStrategy;

import java.util.List;
import java.util.Set;

public class EmailFiltersLoader extends AbstractTreeViewDataLoader<EmailFilter> implements EventHandler<MouseEvent>{
    public EmailFiltersLoader(TreeView<EmailFilter> context) {
        super(context, EmailFilter.class);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        List<EmailFilter> emailFilterList = this.getDataRepository().getAll();
        if(emailFilterList.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("You do not have email filters in data base");
        }else{
            for(EmailFilter emailFilter: emailFilterList) {
                TreeItem treeItem = new TreeItem<>();
                treeItem.setValue("filter name: " + emailFilter.getName() + " id: "+ emailFilter.getId());

                TreeItem<String> savingStrategies = new TreeItem<>();
                savingStrategies.setValue("saving strategies");
                for(SavingStrategy savingStrategy: emailFilter.getSavingStrategies()){
                    TreeItem<String> savingSt = new TreeItem<>("id: " + savingStrategy.getId());
                    TreeItem<String> pattern = new TreeItem<>("pattern: " + savingStrategy.getPattern());
                    TreeItem<String> saveBy = new TreeItem<>("save by: " + savingStrategy.getSavingBy().name());
                    savingStrategies.getChildren().addAll(savingSt, pattern, saveBy);
                }
                treeItem.getChildren().add(savingStrategies);

                TreeItem<String> emails = new TreeItem<>();
                emails.setValue("emails");
                System.out.println(emailFilter.getFilteredEmails().size() + " FILTERED EMAILS");
                for(FilteredEmail filteredEmail: emailFilter.getFilteredEmails()){
                    TreeItem<String> t4 = new TreeItem<>("id: "+filteredEmail.getId());
                    TreeItem<String> t = new TreeItem<>("email: "+filteredEmail.getEmail());
                    TreeItem<String> t3 = new TreeItem<>("host: "+filteredEmail.getHost());
                    emails.getChildren().addAll(t,t4,t3);
                }
                treeItem.getChildren().add(emails);
                this.getContext().setRoot(treeItem);
            }
        }

    }

}
