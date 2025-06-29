package ait.cohort5860.accounting.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAccountDto {
    private Long id;
    private String username;
    private String email;
    private boolean active;
}
