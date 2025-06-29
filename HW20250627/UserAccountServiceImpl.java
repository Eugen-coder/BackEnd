package ait.cohort5860.accounting.service;

import ait.cohort5860.accounting.dto.UserAccountDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    @Override
    public UserAccountDto createAccount(UserAccountDto userAccountDto) {
        return null;
    }

    @Override
    public UserAccountDto updateAccount(Long id, UserAccountDto userAccountDto) {
        return null;
    }

    @Override
    public UserAccountDto getAccount(Long id) {
        return null;
    }

    @Override
    public List<UserAccountDto> getAllAccounts() {
        return List.of();
    }

    @Override
    public void deleteAccount(Long id) {

    }
}
