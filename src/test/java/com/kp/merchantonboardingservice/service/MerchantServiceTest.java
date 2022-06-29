package com.kp.merchantonboardingservice.service;

import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.MerchantListResponse;
import com.kp.merchantonboardingservice.respository.MerchantRepository;
import com.kp.merchantonboardingservice.service.impl.MerchantServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @InjectMocks
    MerchantServiceImpl merchantService;

    @Mock
    MerchantRepository merchantRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
    @Test
    void createMerchant() {
        Merchant merchant = createTestMerchant();
        Merchant expectedMerchant = createTestMerchant();
        expectedMerchant.setId(1);
        Mockito.when(merchantRepository.save(any(Merchant.class))).thenReturn(expectedMerchant);
        merchant = merchantService.createMerchant(merchant);
        assertNotNull(merchant);
        assertEquals(expectedMerchant.getId(), merchant.getId());
    }

    @Test
    void createMerchantValidationError() {
        Merchant merchant = createTestMerchant();
        merchant.setName(null);
        assertThrows(IllegalArgumentException.class , () -> {
            merchantService.createMerchant(merchant);
        });
    }

    private Merchant createTestMerchant() {
        Merchant merchant = new Merchant();
        merchant.setId(1);
        merchant.setName("test-merchant");
        merchant.setStatus("ACTIVE");
        return merchant;
    }

    @Test
    void updateMerchant() {
        Merchant merchant = createTestMerchant();
        merchant.setName("updated-name");
        Merchant expectedMerchant = createTestMerchant();
        expectedMerchant.setId(1);
        expectedMerchant.setName(merchant.getName());
        Mockito.when(merchantRepository.findById(anyLong())).thenReturn(Optional.of(createTestMerchant()));
        Mockito.when(merchantRepository.save(any(Merchant.class))).thenReturn(expectedMerchant);
        merchant = merchantService.updateMerchant(merchant);
        assertNotNull(merchant);
        assertEquals(expectedMerchant.getName(), merchant.getName());
    }

    @Test
    void deleteMerchant() {
        Mockito.doNothing().when(merchantRepository).deleteById(anyLong());
        Mockito.when(merchantRepository.findById(anyLong())).thenReturn(Optional.of(createTestMerchant()));
        merchantService.deleteMerchant(1);
    }

    @Test
    void getMerchant() {
        Merchant expectedMerchant = createTestMerchant();
        expectedMerchant.setId(1);
        Mockito.when(merchantRepository.findById(anyLong())).thenReturn(Optional.of(expectedMerchant));
        Merchant merchant = merchantService.getMerchant(1);
        assertNotNull(merchant);
        assertEquals(1, merchant.getId());
    }
    @Test
    void getMerchantNotFound() {

        Mockito.when(merchantRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            merchantService.getMerchant(1);
        });
    }

    @Test
    void getAllMerchants() {
        List<Merchant> merchantList = Arrays.asList(
                createTestMerchant(),
                createTestMerchant()
        );
        Pageable pageable = Pageable.ofSize(20).withPage(0);
        Page<Merchant> merchantPage = new PageImpl<>(merchantList, pageable, 2);
        Mockito.when(merchantRepository.findAll(any(Pageable.class))).thenReturn(merchantPage);
        MerchantListResponse merchantListResponse = merchantService.getAllMerchants(0, 20);
        assertNotNull(merchantListResponse);
        assertNotNull(merchantListResponse.getMerchants());
        assertEquals(2, merchantListResponse.getMerchants().size());
        assertEquals(2, merchantListResponse.getTotalMerchantsCount());
    }
}