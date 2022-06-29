package com.kp.merchantonboardingservice.controller;

import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.MerchantListResponse;
import com.kp.merchantonboardingservice.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    @Autowired
    MerchantService merchantService;

    @PostMapping("/")
    public ResponseEntity<Merchant> createMerchant(@RequestBody Merchant merchant) {
        return ResponseEntity.ok(merchantService.createMerchant(merchant));
    }

    @PutMapping("/")
    public ResponseEntity<Merchant> updateMerchant(@RequestBody Merchant merchant) {
        return ResponseEntity.ok(merchantService.updateMerchant(merchant));
    }

    @GetMapping("/{merchantId}")
    public ResponseEntity<Merchant> getMerchant(@PathVariable("merchantId") int merchantId) {
        return ResponseEntity.ok(merchantService.getMerchant(merchantId));
    }

    @DeleteMapping("/{merchantId}")
    public ResponseEntity deleteMerchant(@PathVariable("merchantId") int merchantId) {
        merchantService.deleteMerchant(merchantId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{pageNumber}/size/{pageSize}")
    public ResponseEntity<MerchantListResponse> getMerchants(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        return ResponseEntity.ok(merchantService.getAllMerchants(pageNumber, pageSize));
    }
}
