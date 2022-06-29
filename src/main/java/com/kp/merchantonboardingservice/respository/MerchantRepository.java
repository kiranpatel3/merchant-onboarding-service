package com.kp.merchantonboardingservice.respository;

import com.kp.merchantonboardingservice.models.Merchant;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends PagingAndSortingRepository<Merchant, Long> {

}
