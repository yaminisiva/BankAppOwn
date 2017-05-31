package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientInitialAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.repository.AccountRepository;

public class AccountServiceImpl implements AccountService {
	AccountRepository accountRepository;
	
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}	
	/* (non-Javadoc)
	 * @see com.capgemini.service.AccountService#createAccount(int, int)
	 */
	@Override
	public Account createAccount(int accountNumber,int amount)throws InsufficientInitialAmountException
	{
		if(amount<500)
		{
			throw new InsufficientInitialAmountException();
		}
		
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		account.setAmount(amount);
		
		
		if(accountRepository.save(account))
		{
			return account;
		}
		
		return null;
			}
	
	@Override
	public int depositAmount(int accountNumber,int depamount) throws InvalidAccountNumberException
	{
		
		
		if(accountRepository.searchAccount(accountNumber)!=null)
		{
			Account account = accountRepository.searchAccount(accountNumber);
			return account.getAmount() + depamount;
		}
		else
		{
			throw new InvalidAccountNumberException();
		}
	}
	@Override
	public int withdrawAmount(int accountNumber, int amount)
			throws InsufficientBalanceException, InvalidAccountNumberException {
			
		
		if(accountRepository.searchAccount(accountNumber)!=null)
		{
			Account account = accountRepository.searchAccount(accountNumber);
			
			if(account.getAmount()>amount)
			{
				return account.getAmount() - amount;
			}
			else
			{
				throw new InsufficientBalanceException();	
			}
		}
		throw new InvalidAccountNumberException();
	}
	
}
