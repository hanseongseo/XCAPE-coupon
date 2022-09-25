package com.xcape.coupon.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void findByPhoneNumber() {
        Coupon coupon1 = new Coupon("couponA", "010101010");
        Coupon coupon2 = new Coupon("couponB", "010101010");

        couponRepository.save(coupon1);
        couponRepository.save(coupon2);
        List<Coupon> findCoupon = couponRepository.findByPhoneNumber("010101010");
        Iterator<Coupon> iterator = findCoupon.iterator();

        assertThat(findCoupon.size()).isEqualTo(4);    //  @PostConstruct coupon 2 개 포함
        assertThat(iterator.next().getPhoneNumber()).isEqualTo("010101010");
        assertThat(iterator.next().getPhoneNumber()).isEqualTo("010101010");
    }

    @Test
    void findByCodeTest() {
        Coupon coupon = new Coupon("couponA", "010101010");
        couponRepository.save(coupon);
        Coupon findCoupon = couponRepository.findByCode(coupon.getCode());

        assertThat(findCoupon.getCouponName()).isEqualTo(coupon.getCouponName());
        assertThat(findCoupon.getCode()).isEqualTo(coupon.getCode());
        assertThat(findCoupon.getPhoneNumber()).isEqualTo(coupon.getPhoneNumber());
    }

    @Test
    void findAll() {

        Coupon coupon1 = new Coupon("couponA", "010101010");
        Coupon coupon2 = new Coupon("couponB", "010101010");
        couponRepository.save(coupon1);
        couponRepository.save(coupon2);

        List<Coupon> all = couponRepository.findAll();
        assertThat(all.size()).isEqualTo(4);    //  @PostConstruct coupon 2 개 포함
    }

    @Test
    void useCoupon() {
        Coupon coupon = new Coupon("couponA", "010101010");
        couponRepository.save(coupon);
        coupon.setUsedYN(true);
        Coupon savedCoupon = couponRepository.save(coupon);
        assertThat(savedCoupon.getId()).isEqualTo(3);
        assertThat(savedCoupon.getUsedYN()).isEqualTo(true);
        assertThat(savedCoupon.getCouponName()).isEqualTo("couponA");
    }
}