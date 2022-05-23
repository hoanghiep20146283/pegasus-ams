package com.pegasus.ams.mgmt.service;

import com.pegasus.ams.mgmt.dto.request.LoginDTO;
import com.pegasus.ams.mgmt.dto.request.UserDTO;
import com.pegasus.ams.mgmt.dto.response.UserResponseDTO;
import com.pegasus.ams.mgmt.entity.User;
import com.pegasus.ams.mgmt.exception.error.BadRequestException;
import com.pegasus.ams.mgmt.exception.error.NotFoundException;
import com.pegasus.ams.mgmt.mapper.UserMapper;
import com.pegasus.ams.mgmt.repository.RoleRepository;
import com.pegasus.ams.mgmt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CasbinUpdatePolicyService casbinUpdatePolicyService;

    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toUserResponseDtoList(users);
    }

    public Page<UserResponseDTO> searchUsers(String firstName, String lastName, String email, Pageable pageable) {
        Page<User> users = userRepository.searchUsers(firstName, lastName, email, pageable);
        return users.map(user -> UserMapper.toUserResponseDto(user));
    }

    public UserResponseDTO getUserDetail(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new NotFoundException("User not found");
        }
        return UserMapper.toUserResponseDto(user.get());
    }

    public UserResponseDTO updateUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new NotFoundException("User not found"));
        if (userRepository.findExistByUsername(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new BadRequestException("Current Username has been use");
        } else if (userRepository.findExistByEmail(userDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Current Email has been use");
        } else {
            user.setUsername(userDTO.getUsername());
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setActivated(userDTO.getActivated());
            if(!CollectionUtils.isEmpty(userDTO.getRoleIds())) {
                user.setRoles(roleRepository.findRolesByIds(userDTO.getRoleIds()));
            }
            userRepository.save(user);
            return UserMapper.toUserResponseDto(user);
        }
    }

    public void deleteUser(List<Long> ids) {
        ids.forEach(id -> {
            Optional<User> user = userRepository.findById(id);
            if (!user.isPresent()) {
                throw new NotFoundException("User not found");
            }
            userRepository.deleteById(id);
        });
    }

    public UserResponseDTO signIn(LoginDTO request) {
        User user= userRepository.findFirstByEmailOrUsername(request.getUserNameOrEmail()).orElseThrow(
                () -> new NotFoundException("User not found")
        );

        boolean passMatch = passwordEncoder.matches(request.getPassword(),user.getPassword());

        if (!passMatch) {
            throw new BadRequestException("Password not correct");
        }
        UserResponseDTO result = UserMapper.toUserResponseDto(user);
        casbinUpdatePolicyService.updatePolicy(user.getId(), true);

        return result;
    }

    public UserResponseDTO signUp(UserDTO userDTO) {
        if (userRepository.findFirstByUsername(userDTO.getUsername().toLowerCase()).isPresent()) {
            throw new BadRequestException("Current Username has been use");
        } else if (userRepository.findFirstByEmail(userDTO.getEmail()).isPresent()) {
            throw new BadRequestException("Current Email has been use");
        } else {
            User user = UserMapper.toUser(userDTO);
            String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
            user.setPassword(encryptedPassword);
            user.setActivated(true);

            user.setRoles(new HashSet<>(Collections.singletonList(roleRepository.findByCode("user"))));
            userRepository.save(user);
            log.debug("Created Information for User: {}", user);
            return UserMapper.toUserResponseDto(user);
        }
    }
}
