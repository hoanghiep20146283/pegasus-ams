package com.pegasus.ams.mgmt.mapper;

import com.pegasus.ams.mgmt.dto.request.UserDTO;
import com.pegasus.ams.mgmt.dto.response.UserResponseDTO;
import com.pegasus.ams.mgmt.entity.User;

public class UserMapper {

    private UserMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static UserDTO toUserDto(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .activated(user.getActivated())
                .username(user.getUsername())
                .build();
    }

    public static User toUser(UserDTO userDto) {
        return User.builder().id(userDto.getId())
                .id(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .activated(userDto.getActivated())
                .username(userDto.getUsername())
                .build();
    }

    public static UserResponseDTO toUserResponseDto(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .activated(user.getActivated())
                .build();
    }
}

