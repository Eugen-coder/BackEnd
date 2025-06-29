package ait.cohort5860.accounting.controller;

import ait.cohort5860.accounting.dto.UserAccountDto;
import ait.cohort5860.accounting.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping
    public UserAccountDto createAccount(@RequestBody UserAccountDto dto) {
        return userAccountService.createAccount(dto);
    }

    @PostMapping("/{id}")
    public UserAccountDto updateAccount(@PathVariable Long id, @RequestBody UserAccountDto dto) {
        return userAccountService.updateAccount(id, dto);
    }

    @GetMapping("/{id}")
    public UserAccountDto getAccount(@PathVariable Long id) {
        return userAccountService.getAccount(id);
    }

    @GetMapping
    public List<UserAccountDto> getAllAccounts() {
        return userAccountService.getAllAccounts();
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        userAccountService.deleteAccount(id);
    }

}
