package controllers;

import config.EmailConfiguration;
import email.Setup;
import models.MessageSaved;
import models.filters.EmailFilter;
import models.filters.FilteredEmail;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.AbstractRepository;
import services.FilterBotOperations;
import utils.EmailUtils;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class Bot {
    private final FilterBotOperations botOperations;
    private final AbstractRepository<EmailFilter> emailFilterRepository;


    public Bot(FilterBotOperations botOperations, AbstractRepository<EmailFilter> emailFilterRepository) {
        this.botOperations = botOperations;
        this.emailFilterRepository = emailFilterRepository;
    }

    public void checkMessages(ExecutorService executorService) throws MessagingException, IOException{
        executorService.submit(() -> {
            List<EmailFilter> emailFilters = this.emailFilterRepository.getAll();
            for (EmailFilter emailFilter : emailFilters) {
                Set<FilteredEmail> filteredEmails = emailFilter.getFilteredEmails();
                for(FilteredEmail filteredEmail: filteredEmails){
                    try {
                        filterMessages(emailFilter, filteredEmail);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    public Thread checkMessages(Consumer<MessageSaved> savedConsumer) {
        Runnable loadingThread = () -> {
            List<EmailFilter> emailFilters = this.emailFilterRepository.getAll();
            for (EmailFilter emailFilter : emailFilters) {
                Set<FilteredEmail> filteredEmails = emailFilter.getFilteredEmails();
                for(FilteredEmail filteredEmail: filteredEmails){
                    try {
                        filterMessages(emailFilter, filteredEmail, savedConsumer);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(loadingThread);
        thread.start();
        return thread;
    }

    public void filterMessages(EmailFilter emailFilter, FilteredEmail filteredEmail, Consumer<MessageSaved> consumer) throws MessagingException {
        Setup setup = new Setup(filteredEmail.getEmail(), filteredEmail.getPassword());
        try {
            setup.openSession();
            setup.openStore(Setup.Protocols.IMAP);
            setup.openFolderRW(Setup.Folders.INBOX);
            System.out.println("filter messages invoked: ");
            Logger logger = LogManager.getLogger("BotOperationsLogger");
            for (Message m : setup.getMessagesWithNotSeenFlag()) {
                if(Thread.currentThread().isInterrupted()){
                    break;
                }
                System.out.println(m.getSubject());
                try{
                    MessageSaved saved = EmailUtils.mapEmailMessageToEntity(m);
                    if(logger.isInfoEnabled()){
                        logger.info(saved);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }

                this.botOperations.saveOrDeleteMessage(m, emailFilter.getSavingStrategies(), consumer);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            setup.close();
        }
    }
    public void filterMessages(EmailFilter emailFilter, FilteredEmail filteredEmail) throws MessagingException {
        Setup setup = new Setup(filteredEmail.getEmail(), filteredEmail.getPassword());
        String emailHost = filteredEmail.getHost();
        Setup.Protocols guessedProtocol = switch (emailHost){
            case "imap.gmail.com" -> Setup.Protocols.IMAP;
            default -> Setup.Protocols.IMAP;
        };
        try {
            setup.openSession();
            setup.openStore(Setup.Protocols.IMAP);
            setup.openFolderRW(Setup.Folders.INBOX);

            for (Message m : setup.getMessagesWithNotSeenFlag()) {
                Object d = this.botOperations.saveOrDeleteMessage(m, emailFilter.getSavingStrategies());
                if(d != null){
                    break;
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } finally {
            setup.close();
        }
    }


}
