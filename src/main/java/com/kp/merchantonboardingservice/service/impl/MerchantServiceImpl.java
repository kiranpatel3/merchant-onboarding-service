package com.kp.merchantonboardingservice.service.impl;

import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.MerchantListResponse;
import com.kp.merchantonboardingservice.respository.MerchantRepository;
import com.kp.merchantonboardingservice.service.MerchantService;
import com.kp.merchantonboardingservice.utils.MerchantsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MerchantServiceImpl implements MerchantService {

    @Autowired
    MerchantRepository merchantRepository;

    @Override
    public Merchant createMerchant(Merchant merchant) {
        validateMerchant(merchant);
        return merchantRepository.save(merchant);
    }

    private void validateMerchant(Merchant merchant) {
        if (merchant == null
                || StringUtils.isEmpty(merchant.getName())) {
            throw new IllegalArgumentException("Merchant is invalid");
        }
    }

    @Override
    public Merchant updateMerchant(Merchant merchant) {
        validateMerchant(merchant);
        validateMerchantAvailability(merchant.getId());
        return merchantRepository.save(merchant);
    }

    private void validateMerchantAvailability(long merchantId) {
        boolean isMerchantAvailable = merchantRepository.findById(merchantId).isPresent();
        if (!isMerchantAvailable) {
            throw new IllegalArgumentException("Merchant with " + merchantId + " not available");
        }
    }

    @Override
    public void deleteMerchant(long merchantId) {
        validateMerchantAvailability(merchantId);
        merchantRepository.deleteById(merchantId);
    }

    @Override
    public Merchant getMerchant(long merchantId) {
        return merchantRepository.findById(merchantId).orElseThrow(
                () -> new IllegalArgumentException("Merchant with " + merchantId + " not available")
        );
    }

    @Override
    public MerchantListResponse getAllMerchants(int pageNumber, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(pageNumber);
        Page<Merchant> merchantPage = merchantRepository.findAll(pageable);
        return MerchantsUtils.getMerchantListResponse(merchantPage, pageNumber);
    }
}
