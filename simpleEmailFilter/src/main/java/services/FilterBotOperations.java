package services;

import models.MessageSaved;
import models.saves.SavingStrategy;

import javax.mail.Message;
import java.util.Set;
import java.util.function.Consumer;

public interface FilterBotOperations {
    MessageSaved saveOrDeleteMessage(Message message, Set<SavingStrategy> emailFilter);
    MessageSaved saveOrDeleteMessage(Message message, Set<SavingStrategy> emailFilter, Consumer<MessageSaved> consumer);
}
