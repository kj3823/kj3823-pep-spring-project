package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController
{
    private final AccountService accountService;
    private final MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @GetMapping("/messages")
    private @ResponseBody ResponseEntity<List<Message>>  getAllMessageHandler()
    {
        return ResponseEntity.status(200).body(this.messageService.getMessages());
    }

    @PostMapping("/register")
    private @ResponseBody ResponseEntity<Account> addAccountHandler(@RequestBody Account newAccount)
    {
        if(!newAccount.getUsername().isEmpty() && newAccount.getPassword().length() > 4)
        {
            Account addedAccount = this.accountService.addAccount(newAccount);
            if(addedAccount != null)
            {
                return ResponseEntity.status(200).body(addedAccount);
            }
            else
            {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @PostMapping("/messages")
    private @ResponseBody ResponseEntity<Message> addMessageHandler(@RequestBody Message message) // JSON -> POJO
    {
//        System.out.println(message);
        if (!message.getMessage_text().isEmpty() && message.getMessage_text().length() < 255) {

            Message newMessage = this.messageService.addMessage(message);
            if (newMessage != null) {
                return ResponseEntity.status(HttpStatus.OK).body(newMessage);
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);

    }

    @PostMapping("/login")
    private @ResponseBody ResponseEntity<Account> loginHandler(@RequestBody Account account)
    {
        Account retrievedAccount = null;
        if(account.getUsername() != null || account.getPassword() != null)
        {
            retrievedAccount = this.accountService.login(account);
        }
        if(retrievedAccount != null)
        {
            return ResponseEntity.status(200).body(retrievedAccount);
        }
        else
        {
            return ResponseEntity.status(401).body(null);
        }
    }

    @GetMapping("/messages/{message_id}")
    private @ResponseBody ResponseEntity<Message> getMessageHandler(@PathVariable Integer message_id)
    {
        System.out.println(message_id);
        return ResponseEntity.status(200).body(this.messageService.getMessageById(message_id));
    }

    @DeleteMapping("/messages/{message_id}")
    private @ResponseBody ResponseEntity<Integer> deleteMessageHandler(@PathVariable Integer message_id)
    {
        Integer numRowsAffected;
        numRowsAffected = this.messageService.deleteMessageById(message_id);
        if(numRowsAffected > 0)
        {
            return ResponseEntity.status(200).body(numRowsAffected);
        }
        return ResponseEntity.status(200).body(null);
    }

    @PatchMapping("/messages/{message_id}")
    private @ResponseBody ResponseEntity<Integer> updateMessageHandler(@PathVariable Integer message_id, @RequestBody Message toBeUpdated)
    {
        System.out.println("Hello!");
        System.out.println(message_id);
        Integer numRowsAffected;
        if(!toBeUpdated.getMessage_text().isEmpty() && toBeUpdated.getMessage_text().length() < 255)
        {
            numRowsAffected = this.messageService.updateMessageById(toBeUpdated, message_id);
            if(numRowsAffected > 0)
            {
                return ResponseEntity.status(200).body(numRowsAffected);
            }
        }
        return ResponseEntity.status(400).body(null);
    }
    @GetMapping("accounts/{account_id}/messages")
    private @ResponseBody ResponseEntity<List<Message>> getMessageUsingAccountIDHandle(@PathVariable Integer account_id)
    {
        System.out.println("Hello!");
        System.out.println(account_id);
        return ResponseEntity.status(200).body(this.messageService.getMessagesBasedOnUserID(account_id));
    }

}
