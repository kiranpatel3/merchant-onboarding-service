package com.kp.merchantonboardingservice.utils;

import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.MerchantListResponse;
import org.springframework.data.domain.Page;

public class MerchantsUtils {
    public static MerchantListResponse getMerchantListResponse(Page<Merchant> merchantPage, int currentPage) {
        MerchantListResponse response = new MerchantListResponse();
        response.setMerchants(merchantPage.getContent());
        response.setCurrentPage(currentPage);
        response.setTotalMerchantsCount(merchantPage.getTotalElements());
        response.setTotalPage(merchantPage.getTotalPages());
        return response;
    }
}
