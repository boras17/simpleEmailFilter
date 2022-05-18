package utils;

import models.Attachment;
import models.MessageSaved;
import org.jsoup.Jsoup;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class EmailUtils {
    public static MessageSaved mapEmailMessageToEntity(Message message) throws MessagingException, IOException {
        StringBuilder messageBodyForMultiPart = new StringBuilder();

        MessageSaved saved = new MessageSaved();
        saved.setTitle(message.getSubject());
        saved.setAuthor(InternetAddress.toString(message.getFrom()).split(" ")[0].replace('"',' ').trim());
        saved.setSentDate(LocalDateTime.ofInstant(message.getSentDate().toInstant(), ZoneId.systemDefault()));

        List<Attachment> attachmentList = new ArrayList<>(); // Attachment list can be empty

        if(message.isMimeType("text/plain")){
            saved.setBody(message.getContent().toString());
        }
        else if(message.isMimeType("multipart/*")){
            MimeMultipart multipart = (MimeMultipart)message.getContent();
            int parts = multipart.getCount();
            for (int i = 0; i < parts; i++) {
                BodyPart part = multipart.getBodyPart(i);
                if(Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())){
                    addAttachmentToAttachmentsList(part, attachmentList);
                }
                else if(part.isMimeType("text/plain")){
                    messageBodyForMultiPart.append(part.getContent().toString());
                    break;
                }else if(part.isMimeType("text/html")){
                    messageBodyForMultiPart.append(org.jsoup.Jsoup.parse((String)part.getContent()).text());
                }
            }
            saved.setBody(messageBodyForMultiPart.toString());

        }else{
            String contentType = message.getContentType();
            if(contentType.equals("text/html")){
                System.out.println("Content text/html");
                saved.setBody( Jsoup.parse( ((String) message.getContent())).text() );
            }else{
                saved.setBody((String)message.getContent());
            }
        }
        if(attachmentList.size()>0){
            saved.setAttachments(attachmentList);
        }
        return saved;
    }

    public static void addAttachmentToAttachmentsList(BodyPart bodyPart, List<Attachment> attachments) throws IOException, MessagingException {
        InputStream partIn = bodyPart.getInputStream();
        byte[] content = partIn.readAllBytes();
        int contentLen = content.length;
        String contentType = bodyPart.getContentType();
        String fileName = bodyPart.getFileName();
        Attachment attachment = Attachment.builder()
                .attachmentName(fileName)
                .content(content)
                .mimeType(contentType)
                .contentLen(contentLen)
                .build();
        attachments.add(attachment);
    }

    public static String getTextFromMessage(Message message) throws MessagingException, IOException {
        String result = "";
        if (message.isMimeType("text/plain")) {
            result = message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            result = getTextFromMimeMultipart(mimeMultipart);
        }
        return result;
    }

    private static String getTextFromMimeMultipart(
            MimeMultipart mimeMultipart)  throws MessagingException, IOException {
        String result = "";
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result = result + "\n" + bodyPart.getContent();
                break;
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
            } else if (bodyPart.getContent() instanceof MimeMultipart){
                result = result + getTextFromMimeMultipart((MimeMultipart)bodyPart.getContent());
            }
        }
        return result;
    }


}
