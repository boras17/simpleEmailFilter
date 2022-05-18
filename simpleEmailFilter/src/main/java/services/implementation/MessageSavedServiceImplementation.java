package services.implementation;

import models.MessageSaved;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.AbstractRepository;
import services.MessageSavedService;
import utils.EmailUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class MessageSavedServiceImplementation implements MessageSavedService {
    private final Logger logger = LogManager.getLogger("MessageSavedService");
    private final AbstractRepository<MessageSaved> messageSavedAbstractRepository;

    public MessageSavedServiceImplementation(AbstractRepository<MessageSaved> messageSavedAbstractRepository) {
        this.messageSavedAbstractRepository = messageSavedAbstractRepository;
    }

    @Override
    public MessageSaved saveMessage(final Message message) {
        try{
            MessageSaved messageSaved = EmailUtils.mapEmailMessageToEntity(message);
            this.messageSavedAbstractRepository.save(messageSaved);
            return messageSaved;
        }catch (MessagingException | IOException e){
            e.printStackTrace();
            if(logger.isErrorEnabled()){
                System.out.println("Error log level does not work");
                this.logger.error("Method EmailUtils.mapEmailMessageToEntity(message) caused exception. Message={}",
                        e.getMessage());
            }
            return null;
        }
    }

    @Override
    public void dynamicSaveMessage(Message message, Consumer<MessageSaved> consumer) {
        MessageSaved messageSaved = this.saveMessage(message);
        consumer.accept(messageSaved);
    }

    @Override
    public List<MessageSaved> getSavedMessages() {
        return this.messageSavedAbstractRepository.getAll();
    }

    @Override
    public boolean deleteMessage(int messageId) {
        return this.messageSavedAbstractRepository.deleteById(messageId);
    }
}
