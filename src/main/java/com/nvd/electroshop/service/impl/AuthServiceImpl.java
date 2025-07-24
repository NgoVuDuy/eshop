package com.nvd.electroshop.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.request.VerifyRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.AuthResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.AuthRepository;
import com.nvd.electroshop.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public Message register(AuthRequest authRequest) {

        Optional<User> userOptional = authRepository.findByUsername(authRequest.getUsername());

        if (userOptional.isPresent()) {
            throw new RuntimeException("Tên tài khoản đã tồn tại");
        }

        String passwordEd = passwordEncoder.encode(authRequest.getPassword());

        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEd)
                .role(authRequest.getRole())
                .build();

        authRepository.save(user);

        return new Message(1, "Tạo tài khoản thành công");
    }

    @Override
    public ApiResponse<AuthResponse> login(AuthRequest authRequest) {

        Optional<User> userOptional = authRepository.findByUsername(authRequest.getUsername());

        if(userOptional.isEmpty()) {
            throw new RuntimeException("Tên tài khoản hoặc mật khẩu không đúng");
        }

        User user = userOptional.get();

        if(!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {

            throw new RuntimeException("Tên tài khoản hoặc mật khẩu không đúng");
        }

        String token = generateToken(user);

        return new ApiResponse<>(1,
                AuthResponse.builder()
                        .token(token)
                        .build()
                );
    }

    // Tạo token
    public String generateToken(User user) {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

//        System.out.println(jwsHeader);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("eshop.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .claim("scope", user.getRole())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(jwsHeader, payload);

        try {
            JWSSigner jwsSigner = new MACSigner(secretKey.getBytes());

            jwsObject.sign(jwsSigner);

            return jwsObject.serialize();

        } catch (JOSEException e) {

            throw new RuntimeException(e);
        }

    }

//    private String getScopes(User user) {
//
//        StringJoiner stringJoiner = new StringJoiner(" ");
//
//        if(user.getRoles() != null) {
//
//            user.getRoles().forEach(role -> {
//                stringJoiner.add(role.toString());
//            });
//        }
//
//        return stringJoiner.toString();
//    }

    // Xác thực token
    public Message verifyToken(VerifyRequest verifyRequest) {

        String token = verifyRequest.getToken();

        try {
            // Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Tạo verifier
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());

            // Kiểm tra hết hạn
            boolean isExpiry = signedJWT.getJWTClaimsSet()
                    .getExpirationTime()
                    .after(new Date());

            // Kiểm tra chữ ký hợp lệ
            boolean isVerify = signedJWT.verify(jwsVerifier);

            if (isVerify && isExpiry) {
                return new Message(1, "Token còn hiệu lực");
            } else {
                return new Message(0, "Token không còn hiệu lực");
            }

        } catch (JOSEException | ParseException e) {
            // Có thể log lỗi ở đây
            throw new RuntimeException(e);
        }
    }

}
