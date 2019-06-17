package com.app.service;

import com.app.helpers.AuthorityType;
import com.app.helpers.RoomTypeEnum;
import com.app.model.Room;
import com.app.model.User;
import com.app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class InitializeTables {

    private final AuthorityService authorityService;
    private final UserServiceImpl userService;
    private final UserRepository daoUser; //musi być bo nadpisana metoda save w userServiceImpl zmienia hasło
    private final PasswordEncoder passwordEncoder;
    private final RoomTypeService roomTypeService;
    private final RoomService roomService;

    public InitializeTables(AuthorityService authorityService, UserServiceImpl userService, UserRepository daoUser, PasswordEncoder passwordEncoder, RoomTypeService roomTypeService, RoomService roomService){

        this.authorityService=authorityService;
        this.userService = userService;
        this.daoUser=daoUser;
        this.passwordEncoder=passwordEncoder;
        this.roomTypeService=roomTypeService;
        this.roomService=roomService;

        initialize();



    }

    private void initialize(){
        if (authorityService.findByType(AuthorityType.ROLE_ADMIN) == null && authorityService.findByType(AuthorityType.ROLE_USER) == null){
            authorityService.saveAuthority(authorityService.newAuthority(AuthorityType.ROLE_USER));
            authorityService.saveAuthority(authorityService.newAuthority(AuthorityType.ROLE_ADMIN));
        }

        if (userService.findByEmail("admin@mail.com") == null){
            User user = userService.newUser();
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setUniqueName("admin");
            user.setEmail("admin@mail.com");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setAuthorities(Arrays.asList(authorityService.newAuthority(AuthorityType.ROLE_ADMIN)));
            daoUser.save(user);
        }
        if (userService.findByEmail("johndoe_199x@mail.com") == null){
            User user = userService.newUser();
            user.setFirstName("John");
            user.setLastName("Doe");
            user.setUniqueName("johndoe");
            user.setEmail("johndoe_199x@mail.com");
            user.setPassword(passwordEncoder.encode("j"));
            user.setAuthorities(Arrays.asList(authorityService.newAuthority(AuthorityType.ROLE_USER)));
            daoUser.save(user);
        }

        if (roomTypeService.findByType(RoomTypeEnum.APARTMENT) == null && roomTypeService.findByType(RoomTypeEnum.EXCLUSIVE) == null && roomTypeService.findByType(RoomTypeEnum.NORMAL) == null){
            com.app.model.RoomType roomTypeNORMAL = new com.app.model.RoomType(RoomTypeEnum.NORMAL, 200);
            com.app.model.RoomType roomTypeAPARTMENT = new com.app.model.RoomType(RoomTypeEnum.APARTMENT, 700);
            com.app.model.RoomType roomTypeEXCLUSIVE = new com.app.model.RoomType(RoomTypeEnum.EXCLUSIVE, 500);

            roomTypeService.save(roomTypeNORMAL);
            roomTypeService.save(roomTypeAPARTMENT);
            roomTypeService.save(roomTypeEXCLUSIVE);
        }

        if (roomService.findByName("1") == null){
            roomService.save(new Room("1", 200, 2, 1, true, 1, roomTypeService.findByType(RoomTypeEnum.NORMAL)));
            roomService.save(new Room("2", 200, 2, 1, true, 1, roomTypeService.findByType(RoomTypeEnum.NORMAL)));
            roomService.save(new Room("3", 400, 2, 2, true, 2, roomTypeService.findByType(RoomTypeEnum.EXCLUSIVE)));
            roomService.save(new Room("4", 400, 2, 2, true, 2, roomTypeService.findByType(RoomTypeEnum.EXCLUSIVE)));
            roomService.save(new Room("5", 300, 2, 2, true, 2, roomTypeService.findByType(RoomTypeEnum.APARTMENT)));
            roomService.save(new Room("6", 300, 2, 2, true, 2, roomTypeService.findByType(RoomTypeEnum.APARTMENT)));
            roomService.save(new Room("7", 100, 1, 1, false, 1, roomTypeService.findByType(RoomTypeEnum.NORMAL)));
            roomService.save(new Room("8", 100, 1, 1, false, 1, roomTypeService.findByType(RoomTypeEnum.NORMAL)));
            roomService.save(new Room("9", 500, 3, 3, true, 2, roomTypeService.findByType(RoomTypeEnum.APARTMENT)));
            roomService.save(new Room("10", 500, 3, 3, true, 2, roomTypeService.findByType(RoomTypeEnum.APARTMENT)));
        }
    }
}
