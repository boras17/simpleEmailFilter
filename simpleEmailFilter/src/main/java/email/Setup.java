package email;

import config.EmailConfiguration;

import javax.mail.*;
import javax.mail.search.FlagTerm;
import javax.mail.search.SearchTerm;

public class Setup{
    private String email;
    private String password;
    private Session session;
    private Folder inbox;
    private Store store;

    public enum Folders{
        INBOX
    }
    public enum Protocols{
        IMAP("imaps");
        String name;
        Protocols(String name){this.name = name;}
        public String getDisplay(){return this.name;}
    }

    public Setup(String email, String password){
        this.email = email;
        this.password = password;
    }

    public void openSession(){
        EmailConfiguration sessionCreator = new EmailConfiguration();
        this.session = sessionCreator.getSession(email, password);

    }

    public Store openStore(Protocols protocol) throws MessagingException {
        if(this.session == null){
            throw new RuntimeException("you have to open connection");
        }else{
            store = session.getStore(protocol.getDisplay());
            store.connect("imap.gmail.com",this.email, this.password);
            return store;
        }
    }

    public Folder openFolderRW(Folders folder) throws MessagingException {
        inbox = store.getFolder(folder.name());
        inbox.open(Folder.READ_WRITE);
        return inbox;
    }

    public Message[] getMessagesWithNotSeenFlag() throws MessagingException {
        // change to false after successful tests
        return this.getMessageByFlagTerms(Flags.Flag.SEEN, true);
    }
    /*
    if negation set to false then when you pass SEEN flag then NOT SEEN messages will be returned
     */
    public Message[] getMessageByFlagTerms(Flags.Flag flag, boolean negation) throws MessagingException {
        SearchTerm searchTerm = new FlagTerm(new Flags(flag), negation);
        return this.getInbox().search(searchTerm);
    }
    public void close() throws MessagingException {
        if(this.inbox != null){
            this.inbox.close(true);
        }
        if(this.store != null){
            this.store.close();
        }
    }

    public Session getSession() {
        return session;
    }
    public Folder getInbox() {
        return inbox;
    }

    public void setInbox(Folder inbox) {
        this.inbox = inbox;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
