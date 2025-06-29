package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.UserAccountDto;

import java.util.List;

public interface UserAccountService {
    UserAccountDto createAccount(UserAccountDto userAccountDto);
    UserAccountDto updateAccount(Long id, UserAccountDto userAccountDto);
    UserAccountDto getAccount(Long id);
    List<UserAccountDto> getAllAccounts();
    void deleteAccount(Long id);
}
