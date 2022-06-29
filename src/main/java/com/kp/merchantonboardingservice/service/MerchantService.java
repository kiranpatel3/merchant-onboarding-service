package com.kp.merchantonboardingservice.service;

import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.MerchantListResponse;


public interface MerchantService {

    Merchant createMerchant(Merchant merchant);

    Merchant updateMerchant(Merchant merchant);

    void deleteMerchant(long merchantId);

    Merchant getMerchant(long merchantId);

    MerchantListResponse getAllMerchants(int pageNumber, int size);


}
