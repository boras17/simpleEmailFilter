package services.implementation;

import models.MessageSaved;
import models.filters.EmailFilter;
import models.saves.SavingBy;
import models.saves.SavingStrategy;
import services.FilterBotOperations;
import services.MessageSavedService;
import utils.EmailUtils;

import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.Set;
import java.util.function.Consumer;

public class FilterBotImplementation implements FilterBotOperations {

    private final MessageSavedService messageSavedService;

    public FilterBotImplementation(MessageSavedService messageSavedService) {
        this.messageSavedService = messageSavedService;
    }

    @Override
    public MessageSaved saveOrDeleteMessage(Message message, Set<SavingStrategy> savingStrategies) {
        try{

            boolean shouldBe = this.checkIfMessageShouldBeSaved(message, savingStrategies);
            message.setFlag(Flags.Flag.SEEN, false);
            if(shouldBe){
                return this.messageSavedService.saveMessage(message);
            }else{
                //this.deleteMessage(message);
                return null;
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public MessageSaved saveOrDeleteMessage(Message message, Set<SavingStrategy> emailFilter, Consumer<MessageSaved> consumer) {
        MessageSaved messageSaved = this.saveOrDeleteMessage(message, emailFilter);
        System.out.println(messageSaved);
        if(messageSaved != null){
            System.out.println("saving: " + messageSaved.getAuthor());
            consumer.accept(messageSaved);
            return messageSaved;
        }
        return null;
    }


    private void deleteMessage(Message message) throws MessagingException {
        message.setFlag(Flags.Flag.DELETED, true);
    }
    private boolean checkIfMessageShouldBeSaved(Message message, Set<SavingStrategy> savingStrategies) throws MessagingException, IOException {
        boolean result = false;

        for(SavingStrategy savingStrategy: savingStrategies){
            SavingBy savingBy = savingStrategy.getSavingBy();
            if(savingBy.equals(SavingBy.AUTHOR)){
                if(SavingStrategy.matches(savingStrategy.getPattern(), InternetAddress.toString(message.getFrom()))){
                    result = true;
                }
            }else if(savingBy.equals(SavingBy.TITLE)){
                if(SavingStrategy.matches(savingStrategy.getPattern(), message.getSubject())){
                    result = true;
                }
            }else if(savingBy.equals(SavingBy.CONTENT)){
                if(SavingStrategy.matches(savingStrategy.getPattern(), EmailUtils.getTextFromMessage(message))){
                    result = true;
                }
            }
        }
        return result;
    }
}
