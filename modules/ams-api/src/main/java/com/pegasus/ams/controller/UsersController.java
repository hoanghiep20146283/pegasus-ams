package com.pegasus.ams.controller;

import com.pegasus.ams.mgmt.dto.CustomPageDto;
import com.pegasus.ams.mgmt.dto.request.LoginDTO;
import com.pegasus.ams.mgmt.dto.request.UserDTO;
import com.pegasus.ams.mgmt.dto.response.UserResponseDTO;
import com.pegasus.ams.mgmt.service.UserService;
import com.pegasus.base.utils.SortUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UsersController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasPermission('User', 'View')")
    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('User', 'View')")
    @GetMapping("/paging")
    public ResponseEntity<CustomPageDto<UserResponseDTO>> searchUsers(@RequestParam(value = "firstName", required = false) String firstName,
                                                                      @RequestParam(value = "lastName", required = false) String lastName,
                                                                      @RequestParam(value = "email", required = false) String email,
                                                                      @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                                      @RequestParam(value = "sortBy", defaultValue = "id,desc") String sortBy) {
        Sort sort = SortUtils.getSortForPaging(sortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        return new ResponseEntity<>(new CustomPageDto<>(userService.searchUsers(firstName, lastName, email, pageable), sortBy), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('User', 'View')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserDetail(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(userService.getUserDetail(id), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('User', 'Update')")
    @PutMapping("")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasPermission('User', 'Delete')")
    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(@RequestParam(value = "ids") List<Long> ids) {
        userService.deleteUser(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity getSignInUser(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(userService.signIn(loginDTO));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDTO> signUp(@Valid @RequestBody UserDTO userDTO) {
        log.debug("REST request to sign up User : {}", userDTO);
        return new ResponseEntity<>(userService.signUp(userDTO), HttpStatus.OK);
    }

}
