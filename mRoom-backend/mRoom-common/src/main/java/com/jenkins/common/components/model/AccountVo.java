package com.jenkins.common.components.model;

import java.util.List;

/**
 * @author JenkinsZhang
 * @date 2020/4/7
 */
public class AccountVo {

    private Long total;
    private List<?> accounts;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<?> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "AccountVo{" +
                "total=" + total +
                ", accounts=" + accounts +
                '}';
    }
}
