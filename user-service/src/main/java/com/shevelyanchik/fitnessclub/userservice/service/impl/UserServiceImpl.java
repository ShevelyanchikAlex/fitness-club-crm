package com.shevelyanchik.fitnessclub.userservice.service.impl;

import com.shevelyanchik.fitnessclub.userservice.constant.Role;
import com.shevelyanchik.fitnessclub.userservice.constant.Status;
import com.shevelyanchik.fitnessclub.userservice.exception.EntityNotFoundException;
import com.shevelyanchik.fitnessclub.userservice.exception.ValidationException;
import com.shevelyanchik.fitnessclub.userservice.model.dto.FilePayload;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserDto;
import com.shevelyanchik.fitnessclub.userservice.model.dto.UserProfile;
import com.shevelyanchik.fitnessclub.userservice.model.entity.User;
import com.shevelyanchik.fitnessclub.userservice.model.mapper.UserMapper;
import com.shevelyanchik.fitnessclub.userservice.persistence.UserRepository;
import com.shevelyanchik.fitnessclub.userservice.service.FileService;
import com.shevelyanchik.fitnessclub.userservice.service.UserService;
import com.shevelyanchik.fitnessclub.userservice.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileService fileService;


    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto) {
        UserDto actualUserDto = findUserById(userDto.getId());
        String updatedEmail = userDto.getEmail();
        if (!actualUserDto.getEmail().equals(updatedEmail) && existsUserByEmail(updatedEmail)) {
            throw new ValidationException("User with the same email exists.");
        }
        actualUserDto.setEmail(updatedEmail);
        actualUserDto.setName(userDto.getName());
        actualUserDto.setSurname(userDto.getSurname());
        actualUserDto.setPhoneNumber(userDto.getPhoneNumber());
        User preUpdatedUser = userMapper.toEntity(actualUserDto);
        User updatedUser = userRepository.save(preUpdatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto findUserByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .map(userMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfile findUserProfileByEmail(String email) {
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
        FilePayload filePayload = null;
        if (Objects.nonNull(user.getProfileImageId())) {
            filePayload = fileService.findFileById(user.getProfileImageId());
        }
        return UserUtils.buildUserProfile(user, filePayload);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> findAllUsers(Pageable pageable) {
        List<UserDto> users = userRepository.findAll(pageable).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
        return new PageImpl<>(users, pageable, userRepository.count());
    }

    @Override
    @Transactional
    public void updateUserStatusById(Long id, String statusName) {
        try {
            Status status = Status.getStatusByName(statusName);
            userRepository.updateUserStatusById(id, status);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public void updateUserRoleById(Long id, String roleName) {
        try {
            Role role = Role.getRoleByName(roleName);
            userRepository.updateUserRoleById(id, role);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    @Transactional
    public UserDto updateUserProfileImageByEmail(String email, MultipartFile profileImage) {
        UserDto actualUserDto = findUserByEmail(email);
        String fileId = fileService.saveFile(profileImage);
        actualUserDto.setProfileImageId(fileId);
        User preUpdatedUser = userMapper.toEntity(actualUserDto);
        User updatedUser = userRepository.save(preUpdatedUser);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countUsers() {
        return userRepository.count();
    }

    @Override
    @Transactional
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

}
