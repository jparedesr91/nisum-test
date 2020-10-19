package com.nisum.interviewtest.user.mapping;

import com.nisum.interviewtest.user.dto.PhoneDTO;
import com.nisum.interviewtest.user.dto.UserDataDTO;
import com.nisum.interviewtest.user.dto.UserResponseDTO;
import com.nisum.interviewtest.user.model.Phone;
import com.nisum.interviewtest.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMap {

    UserMap USER_MAP = Mappers.getMapper(UserMap.class);

    User map (UserDataDTO source);

    List<Phone> mapToPhone (List<PhoneDTO> source);

    List<PhoneDTO> map (List<Phone> source);

    PhoneDTO map (Phone source);

    Phone map (PhoneDTO source);

    UserDataDTO map (User source);

    @Mapping(target = "active", source = "source.active")
    @Mapping(target = "createdTime", source = "source.createdTime")
    @Mapping(target = "loginTime", source = "source.loginTime")
    @Mapping(target = "id", source = "source.id")
    @Mapping(target = "token", source = "token")
    @Mapping(target = "updatedTime", source = "source.updatedTime")
    UserResponseDTO mapToResponseDto (User source, String token);

    @Mapping(target = "token", ignore = true)
    UserResponseDTO mapToResponseDto (User source);
}
