package services;

import models.MessageSaved;

import javax.mail.Message;
import java.util.List;
import java.util.function.Consumer;

public interface MessageSavedService {
    MessageSaved saveMessage(final Message message);
    void dynamicSaveMessage(final Message message, Consumer<MessageSaved> consumer);
    List<MessageSaved> getSavedMessages();
    boolean deleteMessage(int messageId);
}
