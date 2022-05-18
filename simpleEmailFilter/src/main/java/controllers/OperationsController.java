package controllers;

import controllers.binders.*;
import controllers.loaders.FilteredEmailsLoader;
import controllers.loaders.EmailFiltersLoader;
import controllers.loaders.SavedMessagesLoader;
import controllers.loaders.SavingStrategiesLoader;
import fxmodels.EmailFilterFilteredEmailId;
import fxmodels.FilterStrategyId;
import fxmodels.FilteredEmailFX;
import fxmodels.SavingStrategyFX;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import mappers.SavingStrategyMapper;
import models.MessageSaved;
import models.filters.EmailFilter;
import models.filters.FilteredEmail;
import models.saves.SavingBy;
import models.saves.SavingStrategy;
import repositories.AbstractRepository;
import services.EmailFilterService;
import services.MessageSavedService;
import services.SavingStrategyService;
import services.implementation.EmailFilterServiceImplementation;
import services.implementation.FilterBotImplementation;
import services.implementation.MessageSavedServiceImplementation;
import services.implementation.SavingByServiceImplementation;

import java.util.Optional;
import java.util.function.Consumer;

public class OperationsController {
    private final SavingStrategyMapper savingStrategyMapper;
    private final SavingStrategyFX savingStrategyFX;

    public OperationsController() {
        this.savingStrategyMapper = new SavingStrategyMapper();
        this.savingStrategyFX = new SavingStrategyFX();
    }

    @FXML
    public Button deleteEmailFilter;
    @FXML
    public Button refreshStrategies;
    @FXML
    public TextField savingStrategyId;
    @FXML
    public Button addSavingStrategyTofilter;
    @FXML
    public TableView<SavingStrategy> availableSavingStrategies;
    @FXML
    public TextField emailFilterName;
    @FXML
    public Button createEmailFilter;
    @FXML
    public Button loadFilters;
    @FXML
    public TableView<FilteredEmail> availableEmails;
    @FXML
    public Button loadEmails;
    @FXML
    public TextField filterId;
    @FXML
    public TextField filteredEmailId;
    @FXML
    public Button addEmailToFilter;
    @FXML
    public TextField emailFilterIdAddSavingStrategy;
    @FXML
    public TreeView<EmailFilter> emailFilters;
    @FXML
    public TextField emailFilterIdToDelete;

    @FXML
    public TextField email;
    @FXML
    public TextField host;
    @FXML
    public Button addEmailBtn;
    @FXML
    public PasswordField password;

    @FXML
    public TextField svaingStrategyPattern;
    @FXML
    private ChoiceBox<SavingBy> choiceBox;
    @FXML
    private Button saveButton;

    @FXML
    private Button loadSavedMessagesBtn;
    @FXML
    private TableView<MessageSaved> savedMessagesTable;
    @FXML
    private Button dynamicLoadSavedMessagesBtn;
    @FXML
    private Button removeAll;
    @FXML
    private Button stopLoading;
    @FXML
    public void initialize() {
        FilterStrategyId filterStrategyId = new FilterStrategyId();

        FilteredEmailFX filteredEmailFX = new FilteredEmailFX();
        filteredEmailFX.emailProperty().bind(this.email.textProperty());
        filteredEmailFX.hostProperty().bind(this.host.textProperty());
        filteredEmailFX.passwordProperty().bind(this.password.textProperty());

        this.addEmailBtn.setOnMouseClicked(new CreateNewFilteredEmail(filteredEmailFX));

        filterStrategyId.savingStrategyIdProperty().bind(this.savingStrategyId.textProperty());
        filterStrategyId.filterIdProperty().bind(this.emailFilterIdAddSavingStrategy.textProperty());

        this.loadEmails.setOnMouseClicked(new FilteredEmailsLoader(this.availableEmails));

        this.refreshStrategies.setOnMouseClicked(new SavingStrategiesLoader(this.availableSavingStrategies));
        this.loadFilters.setOnMouseClicked(new EmailFiltersLoader(this.emailFilters));

        SavingStrategyService savingStrategyService = new SavingByServiceImplementation();
        this.addSavingStrategyTofilter.setOnMouseClicked(new AddSavingStrategyToFilterListener(filterStrategyId, savingStrategyService));


        EmailFilterService emailFilterService = new EmailFilterServiceImplementation(new AbstractRepository<>(EmailFilter.class), new AbstractRepository<>(FilteredEmail.class));
        this.createEmailFilter.setOnMouseClicked(new CreateNewEmailfilter(emailFilterService, this.emailFilterName.textProperty()));

        this.choiceBox.setItems(FXCollections.observableArrayList(SavingBy.AUTHOR, SavingBy.CONTENT, SavingBy.TITLE));
        this.choiceBox.setValue(SavingBy.TITLE);

        this.savingStrategyFX.patternProperty().bind(this.svaingStrategyPattern.textProperty());
        this.savingStrategyFX.saveByProperty().bind(this.choiceBox.valueProperty());

        this.saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                SavingStrategy savingStrategy = savingStrategyMapper.fromSavingStrategyFxToSavingStrategy(savingStrategyFX);
                savingStrategyService.addNewSavingStrategy(savingStrategy);
            }
        });

        this.deleteEmailFilter.setOnMouseClicked(new DeleteEmailFilterListener(emailFilterService, this.emailFilterIdToDelete.textProperty()));

        EmailFilterFilteredEmailId emailFilterFilteredEmailId = new EmailFilterFilteredEmailId();

        emailFilterFilteredEmailId.filterIdProperty().bind(this.filterId.textProperty());
        emailFilterFilteredEmailId.filteredEmailIdProperty().bind(this.filteredEmailId.textProperty());

        this.addEmailToFilter.setOnMouseClicked(new AddFilteredEmailToEmailFilterListener(emailFilterFilteredEmailId, emailFilterService));

        AbstractRepository<MessageSaved> messageSavedAbstractRepository = new AbstractRepository<>(MessageSaved.class);
        AbstractRepository<EmailFilter> emailFilterAbstractRepository = new AbstractRepository<>(EmailFilter.class);
        Bot bot = new Bot(new FilterBotImplementation(new MessageSavedServiceImplementation(messageSavedAbstractRepository)),emailFilterAbstractRepository);

        SavedMessagesLoader messagesLoader = new SavedMessagesLoader(this.savedMessagesTable, MessageSaved.class, bot);

        SavedMessagesLoader dynamicMessagesLoader = new SavedMessagesLoader(this.savedMessagesTable,
                                                                            MessageSaved.class, bot,
                                                                            new Consumer<MessageSaved>() {
                        @Override
                        public void accept(MessageSaved messageSaved) {
                            savedMessagesTable.getItems().add(messageSaved);
                        }
        });

        this.loadSavedMessagesBtn.setOnMouseClicked(messagesLoader);
        this.dynamicLoadSavedMessagesBtn.setOnMouseClicked(dynamicMessagesLoader);

        this.removeAll.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                savedMessagesTable.getItems()
                        .clear();
            }
        });
        this.savedMessagesTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                MessageSaved toDelete = savedMessagesTable.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("if you confirm the you delete this message from server");
                alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                    @Override
                    public void handle(DialogEvent dialogEvent) {
                        ButtonType result = alert.getResult();
                        boolean cancel = result.getButtonData().isCancelButton();
                        if(!cancel){
                            savedMessagesTable.getItems().remove(toDelete);
                            int toDeleteId = toDelete.getId();
                            messageSavedAbstractRepository.deleteById(toDeleteId);
                        }
                    }
                });
                alert.showAndWait();
            }
        });
        this.stopLoading.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Optional<Thread> workingThreadOpt = SavedMessagesLoader.getWorkingLoaderThread();
                workingThreadOpt.ifPresentOrElse(thread -> {
                    Thread.State state = thread.getState();
                    if(!state.equals(Thread.State.TERMINATED)){
                        if(thread.isAlive()) {
                            thread.interrupt();
                        }
                    }
                }, () -> {
                    Alert thereIsNoWorkingLoader = new Alert(Alert.AlertType.INFORMATION);
                    thereIsNoWorkingLoader.setContentText("There is no working messages loader");
                    thereIsNoWorkingLoader.show();
                });
            }
        });
    }

}
