package com.example.service;

import com.example.entity.Message;
import com.example.entity.Account;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public List<Message> getMessages()
    {
        return this.messageRepository.findAll();
    }

    public Message addMessage(Message message)
    {
        Optional<Account> retrievedAccount = this.accountRepository.findById(message.getPosted_by());
        if(retrievedAccount.isPresent())
        {
            return this.messageRepository.save(message);
        }
        return null;
    }

    public Message getMessageById(Integer messageID)
    {
        Optional<Message> retrievedMessage = this.messageRepository.findById(messageID);
        if(retrievedMessage.isPresent())
        {
            return retrievedMessage.get(); // retrieves the message
        }
        else
        {
            return null;
        }
    }

    @Transactional
    public Integer deleteMessageById(Integer messageID)
    {
        return this.messageRepository.deleteMessageByMessage_id(messageID);
    }

    @Transactional
    public Integer updateMessageById(Message message, Integer messageID)
    {
        return this.messageRepository.updateMessageByMessage_id(message.getMessage_text(), messageID);
    }

    public List<Message> getMessagesBasedOnUserID(int accountID)
    {
        Optional<Account> retrievedAccount = this.accountRepository.findById(accountID);
        if(retrievedAccount.isPresent())
        {
            Account covertedAccount = retrievedAccount.get();
            return this.messageRepository.getMessagesByPosted_by(covertedAccount.getAccount_id());
        }
        return null;
    }
}
