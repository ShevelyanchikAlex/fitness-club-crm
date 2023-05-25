package com.shevelyanchik.fitnessclub.userservice.model.dto;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private Long id;

    private String name;

    private String surname;

    private String email;

    private String phoneNumber;

    private FilePayload profileImagePayload;

    private Role role;

    private Status status;

}
