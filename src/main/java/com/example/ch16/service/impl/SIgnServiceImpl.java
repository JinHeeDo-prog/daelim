package com.example.ch16.service.impl;

import com.example.ch16.config.security.JwtTokenProvider;
import com.example.ch16.dto.CommonResponse;
import com.example.ch16.dto.SignInResultDto;
import com.example.ch16.dto.SignUpResultDto;
import com.example.ch16.entity.User;
import com.example.ch16.repository.UserRepository;
import com.example.ch16.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SIgnServiceImpl implements SignService {
    public UserRepository userRepository;
    public JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SIgnServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public PasswordEncoder passwordEncoder;



    @Override
    public SignUpResultDto signUp(String id, String password, String name, String email, String role) {
        System.out.println("회원가입");
        User user;
        if(role.equalsIgnoreCase("admin")){
            user = User.builder().uid(id).name(name)
                    .password(passwordEncoder
                            .encode(password))
                    .roles(Collections.singletonList("ROLE_ADMIN")).build();
        }else{
            user = User.builder().uid(id).name(name).email(email)
                    .password(passwordEncoder
                            .encode(password))
                    .roles(Collections.singletonList("ROLE_USER")).build();
        }
        User savedUser = userRepository.save(user);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if(!savedUser.getName().isEmpty()){
            setSuccessResult(signUpResultDto);
        }else{
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }
    private void setSuccessResult(SignUpResultDto signUpResultDto){
        signUpResultDto.setSuccess(true);
        signUpResultDto.setCode(CommonResponse.SUCCESS.getCode());
        signUpResultDto.setMsg(CommonResponse.SUCCESS.getMsg());
    }

    private void setFailResult(SignUpResultDto signUpResultDto){
        signUpResultDto.setSuccess(false);
        signUpResultDto.setCode(CommonResponse.FAIL.getCode());
        signUpResultDto.setMsg(CommonResponse.FAIL.getMsg());
    }


    @Override
    public SignInResultDto signIn(String id, String password) throws Exception {
        User user = userRepository.getByUid(id);
        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new RuntimeException();
        }
        String token = jwtTokenProvider.createToken(String.valueOf(user.getUid()),user.getRoles());
        SignInResultDto signInResultDto = SignInResultDto.builder().token(token).build();
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }
}
