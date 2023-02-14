package com.ironhack.finalProject.model.accounts;

import com.ironhack.finalProject.enums.Status;
import com.ironhack.finalProject.model.users.AccountHolder;
import com.ironhack.finalProject.model.utils.Money;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;
@Entity

public class Checking extends Account{
    private BigDecimal minimumBalance = new BigDecimal(250);
    private BigDecimal monthlyMaintenanceFee = new BigDecimal(12);

    private LocalDate creationDate;
    @Enumerated(EnumType.STRING)
    private Status status;

    public Checking() {
    }

    public Checking(Money balance, int secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner, BigDecimal penaltyFee, BigDecimal minimumBalance, BigDecimal monthlyMaintenanceFee, LocalDate creationDate, Status status) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
        this.minimumBalance = minimumBalance;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.creationDate = creationDate;
        this.status = status;
    }



    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
