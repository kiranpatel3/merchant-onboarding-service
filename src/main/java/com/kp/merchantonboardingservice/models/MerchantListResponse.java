package com.kp.merchantonboardingservice.models;

import java.util.List;

public class MerchantListResponse {

    private List<Merchant> merchants;

    private int currentPage;

    private int totalPage;

    private long totalMerchantsCount;

    public List<Merchant> getMerchants() {
        return merchants;
    }

    public void setMerchants(List<Merchant> merchants) {
        this.merchants = merchants;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalMerchantsCount() {
        return totalMerchantsCount;
    }

    public void setTotalMerchantsCount(long totalMerchantsCount) {
        this.totalMerchantsCount = totalMerchantsCount;
    }
}
