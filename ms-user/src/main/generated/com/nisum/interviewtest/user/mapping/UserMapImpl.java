package com.nisum.interviewtest.user.mapping;

import com.nisum.interviewtest.user.dto.PhoneDTO;
import com.nisum.interviewtest.user.dto.UserDataDTO;
import com.nisum.interviewtest.user.dto.UserResponseDTO;
import com.nisum.interviewtest.user.model.Phone;
import com.nisum.interviewtest.user.model.Role;
import com.nisum.interviewtest.user.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-19T03:10:40-0400",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_261 (Oracle Corporation)"
)
public class UserMapImpl implements UserMap {

    @Override
    public User map(UserDataDTO source) {
        if ( source == null ) {
            return null;
        }

        User user = new User();

        user.setUsername( source.getUsername() );
        user.setName( source.getName() );
        user.setPassword( source.getPassword() );
        user.setPhones( phoneDTOListToPhoneList( source.getPhones() ) );
        List<Role> list1 = source.getRoles();
        if ( list1 != null ) {
            user.setRoles( new ArrayList<Role>( list1 ) );
        }

        return user;
    }

    @Override
    public UserDataDTO map(User source) {
        if ( source == null ) {
            return null;
        }

        UserDataDTO userDataDTO = new UserDataDTO();

        userDataDTO.setName( source.getName() );
        userDataDTO.setUsername( source.getUsername() );
        userDataDTO.setPassword( source.getPassword() );
        userDataDTO.setPhones( phoneListToPhoneDTOList( source.getPhones() ) );
        List<Role> list1 = source.getRoles();
        if ( list1 != null ) {
            userDataDTO.setRoles( new ArrayList<Role>( list1 ) );
        }

        return userDataDTO;
    }

    @Override
    public UserResponseDTO mapToResponseDto(User source, String token) {
        if ( source == null && token == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        if ( source != null ) {
            userResponseDTO.setUpdatedTime( source.getUpdatedTime() );
            userResponseDTO.setLoginTime( source.getLoginTime() );
            userResponseDTO.setActive( source.isActive() );
            userResponseDTO.setCreatedTime( source.getCreatedTime() );
            userResponseDTO.setId( source.getId() );
        }
        if ( token != null ) {
            userResponseDTO.setToken( token );
        }

        return userResponseDTO;
    }

    @Override
    public UserResponseDTO mapToResponseDto(User source) {
        if ( source == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( source.getId() );
        userResponseDTO.setActive( source.isActive() );
        userResponseDTO.setCreatedTime( source.getCreatedTime() );
        userResponseDTO.setUpdatedTime( source.getUpdatedTime() );
        userResponseDTO.setLoginTime( source.getLoginTime() );

        return userResponseDTO;
    }

    protected Phone phoneDTOToPhone(PhoneDTO phoneDTO) {
        if ( phoneDTO == null ) {
            return null;
        }

        Phone phone = new Phone();

        phone.setNumber( phoneDTO.getNumber() );
        phone.setCityCode( phoneDTO.getCityCode() );
        phone.setCountryCode( phoneDTO.getCountryCode() );

        return phone;
    }

    protected List<Phone> phoneDTOListToPhoneList(List<PhoneDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Phone> list1 = new ArrayList<Phone>( list.size() );
        for ( PhoneDTO phoneDTO : list ) {
            list1.add( phoneDTOToPhone( phoneDTO ) );
        }

        return list1;
    }

    protected PhoneDTO phoneToPhoneDTO(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneDTO phoneDTO = new PhoneDTO();

        phoneDTO.setNumber( phone.getNumber() );
        phoneDTO.setCityCode( phone.getCityCode() );
        phoneDTO.setCountryCode( phone.getCountryCode() );

        return phoneDTO;
    }

    protected List<PhoneDTO> phoneListToPhoneDTOList(List<Phone> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneDTO> list1 = new ArrayList<PhoneDTO>( list.size() );
        for ( Phone phone : list ) {
            list1.add( phoneToPhoneDTO( phone ) );
        }

        return list1;
    }
}
