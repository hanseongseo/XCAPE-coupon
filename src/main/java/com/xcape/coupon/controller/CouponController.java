package com.xcape.coupon.controller;

import com.xcape.coupon.domain.Coupon;
import com.xcape.coupon.domain.CouponRepository;
import com.xcape.coupon.util.QRCodeUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RequestMapping("/")
@Controller
@RequiredArgsConstructor
public class CouponController {

    private final CouponRepository couponRepository;
    Logger logger = LoggerFactory.getLogger(getClass());

    //    QR code
    @RequestMapping(value = "code")
    public String getCode(Model model, String url) throws Exception {
        String qrCodeImage = QRCodeUtil.getQRCodeImage(url, 200, 200);
        model.addAttribute("qrCode", qrCodeImage);
        return "index";
    }

    //    전체 조회
    @GetMapping("coupons")
    public String coupons(Model model) {
        List<Coupon> coupons = couponRepository.findAll();
        model.addAttribute("coupons", coupons);
        return "basic/coupons";
    }

    //    쿠폰 등록 -> GET
    @GetMapping("coupons/add")
    public String addForm() {
        return "/basic/addform";
    }

    //    쿠폰 등록 -> POST
    @PostMapping("coupons/add")
    public String saveCoupon(
            @RequestParam String couponName,
            @RequestParam String phoneNumber,
            Model model
    ) {
        Coupon coupon = new Coupon(couponName, phoneNumber);
        couponRepository.save(coupon);
        model.addAttribute("coupon", coupon);
        return "basic/coupon";
    }

//    //    쿠폰 조회(휴대폰 번호)
//    @GetMapping("coupons/{couponPhoneNumber}")
//    public String coupon(@PathVariable String phoneNumber, Model model) {
//        List<Coupon> coupons = couponRepository.findByPhoneNumber(phoneNumber);
//        model.addAttribute("coupons", coupons);
//        return "basic/coupons";
//    }

    //    code 로 쿠폰 조회
    @GetMapping("coupons/read-qrcode")
    public String readQR_GET() {
        return "basic/read-qrcode";
    }

    @PostMapping("coupons/read-qrcode")
    public String readQR_POST(String code, Model model) {
        Coupon coupon = couponRepository.findByCode(code);
        model.addAttribute("coupon", coupon);
        return "basic/coupon";
    }

    @GetMapping("coupons/{code}")
    public String code(@PathVariable("code") String code, Model model) {
        Coupon coupon = couponRepository.findByCode(code);
        model.addAttribute("coupon", coupon);
        return "basic/coupon";
    }

    @PostMapping("coupons/{code}")
    public String useCoupon(@PathVariable("code") String code, Model model) {
        Coupon coupon = couponRepository.findByCode(code);
        coupon.setUsedYN(true);
        couponRepository.save(coupon);
        return "basic/useCoupon";
    }

    //  TODO: init data, 향후 삭제
    @PostConstruct
        public void init() {
        Coupon unusedCoupon = new Coupon("unusedCoupon", "010101");
        Coupon usedCoupon = new Coupon("usedCoupon", "0101010");
        usedCoupon.setUsedYN(true);
        couponRepository.save(unusedCoupon);
        couponRepository.save(usedCoupon);
    }
}
