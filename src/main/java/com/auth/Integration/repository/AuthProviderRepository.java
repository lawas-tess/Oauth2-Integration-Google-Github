package com.auth.Integration.repository;

import com.auth.Integration.entity.AuthProvider;
import com.auth.Integration.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthProviderRepository extends JpaRepository<AuthProvider, Long> {

    Optional<AuthProvider> findByProviderAndProviderUserId(String provider, String providerUserId);

    Optional<AuthProvider> findByUserAndProvider(User user, String provider);
}