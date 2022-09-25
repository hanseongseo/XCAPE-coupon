package com.xcape.coupon.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    List<Coupon> findByPhoneNumber(String phoneNumber);
    Coupon findByCode(String code);
    List<Coupon> findAll();
}