package cz.nigol.obec.services.impl;

import java.util.*;
import javax.ejb.*;
import javax.mail.*;
import javax.annotation.*;
import javax.persistence.*;
import javax.mail.internet.*;
import javax.inject.*;
import org.apache.commons.logging.*;
import cz.nigol.obec.entities.*;
import cz.nigol.obec.services.*;

@Stateless
public class MailServiceImpl implements MailService {
    @PersistenceContext(unitName="obecPU")
    private EntityManager em;
    @Resource(name = "mailServer")
    private Session session;
    @Inject
    private Log log;

    @Override
    public List<MailLog> getAllMailLogs() {
        TypedQuery<MailLog> typedQuery = em.createNamedQuery(MailLog.GET_ALL, MailLog.class);
        return typedQuery.getResultList();
    }

    @Override
    @Asynchronous
    public void sendEmail(String to, String subject, String body) {
        MimeMessage message = new MimeMessage(session);
        try {
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject, "UTF-8");
            message.setSentDate(new Date());
            message.setContent(body, "text/html; charset=utf-8");
            Properties properties = session.getProperties();
            Transport transport = session.getTransport();
            transport.connect(properties.getProperty("mail.smtp.host"),
                    properties.getProperty("mail.smtp.user"), 
                    properties.getProperty("mail.smtp.password"));
            message.saveChanges();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            MailLog mailLog = new MailLog();
            mailLog.setTo(to);
            mailLog.setSubject(subject);
            mailLog.setSentAt(new Date());
            saveMailLog(mailLog);
        } catch (MessagingException e) {
            log.error(e);
        }
    }

    private MailLog saveMailLog(MailLog mailLog) {
        return em.merge(mailLog);
    }
}