package com.xcape.coupon.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Table
@Entity
@Getter
public class Coupon {
    @Id @Column(nullable = false, unique = true) @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String couponName;
    @Setter @Column(nullable = false) private String phoneNumber;
    @Setter @Column(nullable = false, unique = true) private String code = UUID.randomUUID().toString();
    @Setter @Column(nullable = false) private Boolean usedYN;

    protected Coupon() {
        this.usedYN = false;
    }

    public Coupon(String couponName, String phoneNumber) {
        this.couponName = couponName;
        this.phoneNumber = phoneNumber;
        this.usedYN = false;
    }
}
