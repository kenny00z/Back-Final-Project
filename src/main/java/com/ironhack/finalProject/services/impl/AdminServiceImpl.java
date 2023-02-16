package com.ironhack.finalProject.services.impl;

import com.ironhack.finalProject.controller.dto.AccountDTO;
import com.ironhack.finalProject.model.accounts.*;
import com.ironhack.finalProject.model.users.AccountHolder;
import com.ironhack.finalProject.model.users.ThirdParty;
import com.ironhack.finalProject.repositories.accounts.CheckingRepository;
import com.ironhack.finalProject.repositories.accounts.CreditCardRepository;
import com.ironhack.finalProject.repositories.accounts.SavingRepository;
import com.ironhack.finalProject.repositories.accounts.StudentCheckingRepository;
import com.ironhack.finalProject.repositories.users.AccountHoldersRepository;
import com.ironhack.finalProject.repositories.users.ThirdPartyRepository;
import com.ironhack.finalProject.services.interfaces.AdminServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminServiceInterface {

    @Autowired
    CheckingRepository checkingRepository;
    @Autowired
    AccountHoldersRepository accountHoldersRepository;
    @Autowired
    StudentCheckingRepository studentCheckingRepository;
    @Autowired
    SavingRepository savingRepository;
    @Autowired
    CreditCardRepository creditCardRepository;
    @Autowired
    ThirdPartyRepository thirdPartyRepository;



    public List<Checking> getAllCheckingAccounts (){

        return checkingRepository.findAll();
    }


    public List<StudentChecking> getAllStudentAccounts() {
        return studentCheckingRepository.findAll();
    }

    public List<Savings> getAllSavingAccounts (){

        return savingRepository.findAll();
    }

    public List<CreditCard> getAllCreditCardAccounts (){

        return creditCardRepository.findAll();
    }



    public Optional<Checking> getAllCheckingAccountsById(Long id) {
        return checkingRepository.findById(id);
    }

    public Optional<StudentChecking> getAllStudentAccountsById(Long id) {
        return studentCheckingRepository.findById(id);
    }


    public Optional<Savings> getAllSavingAccountsById(Long id) {
        return savingRepository.findById(id);
    }
    public Optional<CreditCard> getAllCreditCardAccountsById(Long id){

        return creditCardRepository.findById(id);
    }


    public Account addChecking(AccountDTO accountDto) {

        AccountHolder primaryOwner = accountHoldersRepository.findById(accountDto.getPrimaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doesn't found"));
        AccountHolder secondaryOwner = null;
        if( accountDto.getSecondaryOwnerId() != null){
         secondaryOwner = accountHoldersRepository.findById(accountDto.getSecondaryOwnerId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doesn't found"));
        }
        LocalDate birthDate = primaryOwner.getBirthDate();
        LocalDate actualDate = LocalDate.now();
        Period period = Period.between(birthDate, actualDate);
        int age = period.getYears();

        Account newAccount;
        if (age <= 24) {
            newAccount = studentCheckingRepository.save(new StudentChecking(primaryOwner, secondaryOwner, accountDto.getSecretKey()));
        } else {
            newAccount = checkingRepository.save(new Checking(primaryOwner, secondaryOwner, accountDto.getSecretKey()));
        }

        return newAccount;
    }

    public ThirdParty addThirdParty(ThirdParty thirdParty) {
        ThirdParty newThirdParty = new ThirdParty(thirdParty.getHashKey(), thirdParty.getName());
        thirdPartyRepository.save(newThirdParty);
        return newThirdParty;
    }

    public Savings addSavings(Savings savings) {
        Savings newSavings = new Savings(savings.getPrimaryOwnerId(), savings.getSecondaryOwnerId(), savings.getStatus());
        savingRepository.save(newSavings);
        return newSavings;
    }

    public  CreditCard addCreditCard(CreditCard creditCard) {
        CreditCard newCreditCard = new CreditCard(creditCard.getCreditLimit(), creditCard.getInterestRate(),creditCard.getPrimaryOwnerId(), creditCard.getSecondaryOwnerId());
        creditCardRepository.save(newCreditCard);
        return newCreditCard;
    }




    public void deleteThirdParty(Long id) {
        if(!thirdPartyRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ThirdParty account not found");
        }
        thirdPartyRepository.deleteById(id);
    }

    public void deleteCreditCard(Long id) {
        if(!creditCardRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit card not found");
        }
        creditCardRepository.deleteById(id);
    }

    public void deleteSavings(Long id) {
        if(!savingRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings not found");
        }
        savingRepository.deleteById(id);
    }



    public void deleteChecking(Long id) {
        if(!checkingRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found");
        }
        checkingRepository.deleteById(id);
    }

    public void deleteStudent(Long id) {
        if(!studentCheckingRepository.findById(id).isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student account not found");
        }
        studentCheckingRepository.deleteById(id);
    }





}








//    public static int getAge(LocalDate dateOfBirth){
//
//        Calendar today = Calendar.getInstance();
//        Calendar birthDate = Calendar.getInstance();
//
//        int age = 0;
//
//        birthDate.getTime(dateOfBirth);
//
//        if(birthDate.after(dateOfBirth)){
//            throw new IllegalArgumentException("Can't be born in the future");
//        }
//        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
//
//        if( (birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
//                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))){
//            age--;
//        }else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) && (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))){
//            age--;
//        }
//        return age;
//    }


