package com.nvd.electroshop.service.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nvd.electroshop.dto.request.AuthRequest;
import com.nvd.electroshop.dto.request.LogoutRequest;
import com.nvd.electroshop.dto.request.RefreshTokenRequest;
import com.nvd.electroshop.dto.request.VerifyRequest;
import com.nvd.electroshop.dto.response.*;
import com.nvd.electroshop.entity.BlackListToken;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.enums.Role;
import com.nvd.electroshop.exception.BadRequestException;
import com.nvd.electroshop.exception.ConflictException;
import com.nvd.electroshop.repository.AuthRepository;
import com.nvd.electroshop.repository.BlackListTokenRepository;
import com.nvd.electroshop.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final BlackListTokenRepository blackListTokenRepository;

    public AuthServiceImpl(AuthRepository authRepository, PasswordEncoder passwordEncoder, BlackListTokenRepository blackListTokenRepository) {
        this.authRepository = authRepository;
        this.passwordEncoder = passwordEncoder;
        this.blackListTokenRepository = blackListTokenRepository;
    }

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public Message register(AuthRequest authRequest) {

        Optional<User> userOptional = authRepository.findByUsername(authRequest.getUsername());

        if (userOptional.isPresent()) {
            throw new ConflictException("Tên tài khoản đã tồn tại");
        }

        String passwordEd = passwordEncoder.encode(authRequest.getPassword());

        User user = User.builder()
                .username(authRequest.getUsername())
                .password(passwordEd)
                .role(Role.USER)
                .build();

        authRepository.save(user);

        return new Message(1, "Tạo tài khoản thành công");
    }

    @Override
    public ApiResponse<AuthResponse> login(AuthRequest authRequest) {

        User user = getUserByUserName(authRequest.getUsername());

        if (user.isDelete()) { // Kiểm tra trạng thái
            throw new BadRequestException("Tên tài khoản hoặc mật khẩu không đúng");
        }

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) { // Kiểm tra mật khẩu

            throw new BadRequestException("Tên tài khoản hoặc mật khẩu không đúng");
        }

        String accessToken = generateToken(user, false); // Tạo access token
        String refreshToken = generateToken(user, true); // Tạo refresh token

        return new ApiResponse<>(1,
                AuthResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build()
        );
    }

    @Override
    public Message logout(LogoutRequest logoutRequest) {

        List<String> tokens = new ArrayList<>();
        tokens.add(logoutRequest.getAccessToken());
        tokens.add(logoutRequest.getRefreshToken());

        for (String token : tokens) {
            // parse token lấy id và expiration time
            try {
                SignedJWT signedJWT = SignedJWT.parse(token);

                JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

                String idToken = jwtClaimsSet.getJWTID();
                Date expirationTime = jwtClaimsSet.getExpirationTime();

                BlackListToken blackListToken = BlackListToken.builder()
                        .id(idToken)
                        .expirationTime(expirationTime)
                        .build();

                blackListTokenRepository.save(blackListToken);

            } catch (ParseException e) {
                throw new RuntimeException("Lỗi parse token");
            }
        }

        return new Message(1, "Đăng xuất thành công");
    }

    // refresh token
    @Override
    public ApiResponse<RefreshTokenResponse> refresh(RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.getRefreshToken();
        // Xác thực token
        boolean isVerify = verifyToken(refreshToken);
        if (!isVerify) {

            throw new BadRequestException("Refresh Token không còn hiệu lực");
        }

        // Kiểm tra type = refresh
        try {
            SignedJWT signedJWT = SignedJWT.parse(refreshToken);

            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

            String type = jwtClaimsSet.getClaim("type").toString();

            if(!type.equals("refresh-token")) {

                throw new BadRequestException("Đây không phải là refresh token");
            }

            // lấy username - subject
            String username = jwtClaimsSet.getSubject();
            // Lấy ra user
            User user = getUserByUserName(username);

            // Trả về access token mới
            String accessToken = generateToken(user, false);

            return new ApiResponse<>(1,
                    RefreshTokenResponse.builder()
                            .accessToken(accessToken)
                            .build());

        } catch (ParseException e) {
            throw new RuntimeException("Lỗi parse token");
        }

    }

    // Tạo access token
    public String generateToken(User user, boolean isRefreshToken) {

        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet;

        if (!isRefreshToken) {

            jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("eshop.com")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                    .claim("scope", user.getRole())
                    .claim("type", "access-token")
                    .jwtID(UUID.randomUUID().toString())
                    .build();
        } else {

            jwtClaimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer("eshop.com")
                    .issueTime(new Date())
                    .expirationTime(Date.from(Instant.now().plus(7, ChronoUnit.DAYS)))
                    .claim("scope", user.getRole())
                    .claim("type", "refresh-token")
                    .jwtID(UUID.randomUUID().toString())
                    .build();
        }

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


    // Xác thực token
    public ApiResponse<VerifyResponse> verifyToken(VerifyRequest verifyRequest) {

        String token = verifyRequest.getToken();

        try {
            // Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Tạo verifier
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

            String idToken = jwtClaimsSet.getJWTID();
            // Kiểm tra xem token có trong blacklist không
            if (blackListTokenRepository.existsById(idToken)) {

                return new ApiResponse<>(1, VerifyResponse.builder()
                        .isVerify(false)
                        .build());
            }

            // Kiểm tra hết hạn
            boolean isExpiry = jwtClaimsSet
                    .getExpirationTime()
                    .after(new Date());

            // Kiểm tra chữ ký hợp lệ
            boolean isVerify = signedJWT.verify(jwsVerifier);

            if (isVerify && isExpiry) {
                return new ApiResponse<>(1, VerifyResponse.builder()
                        .isVerify(true)
                        .build());
            } else {
                return new ApiResponse<>(1, VerifyResponse.builder()
                        .isVerify(false)
                        .build());
            }

        } catch (JOSEException | ParseException e) {
            // Có thể log lỗi ở đây
            throw new RuntimeException(e);
        }
    }

    // Xác thực token
    public boolean verifyToken(String token) {

        try {
            // Parse token
            SignedJWT signedJWT = SignedJWT.parse(token);

            // Tạo verifier
            JWSVerifier jwsVerifier = new MACVerifier(secretKey.getBytes());
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

            String idToken = jwtClaimsSet.getJWTID();
            // Kiểm tra xem token có trong blacklist không
            if (blackListTokenRepository.existsById(idToken)) {

                return false;
            }

            // Kiểm tra hết hạn
            boolean isExpiry = jwtClaimsSet
                    .getExpirationTime()
                    .after(new Date());

            // Kiểm tra chữ ký hợp lệ
            boolean isVerify = signedJWT.verify(jwsVerifier);

            if (isVerify && isExpiry) {
                return true;
            } else {
                return false;
            }

        } catch (JOSEException | ParseException e) {
            // Có thể log lỗi ở đây
            throw new RuntimeException(e);
        }
    }

    private User getUserByUserName(String username) {

        Optional<User> userOptional = authRepository.findByUsername(username);

        if (userOptional.isEmpty()) { // kiểm tra tên tài khoản
            throw new BadRequestException("Tên tài khoản hoặc mật khẩu không đúng");
        }

        return userOptional.get();
    }

}
