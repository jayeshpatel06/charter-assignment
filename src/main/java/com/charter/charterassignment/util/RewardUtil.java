package com.charter.charterassignment.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RewardUtil {
	
	@Value("${rewardLimit1}")
	private Double rewardLimit1;
	
	@Value("${rewardLimit1Points}")
	private Double rewardLimit1Points;
	
	@Value("${rewardLimit2}")
	private Double rewardLimit2;
	
	@Value("${rewardlimit2Points}")
	private Double rewardlimit2Points;
	

	public Double calulateReward(Double amount) 
	{ 
		try {
			if (amount >=rewardLimit1 && amount < rewardLimit2) { 
				return (rewardLimit1Points*(amount-rewardLimit1));
			} else if (amount >rewardLimit2){ 
				return (rewardlimit2Points*(amount-rewardLimit2) + rewardLimit1); 
			} 
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0d; 
	} 
	
	public Date getDateMonthsAgo(int numOfMonthsAgo)
	{
	    Calendar c = Calendar.getInstance(); 
	    c.setTime(new Date()); 
	    c.add(Calendar.MONTH, -1 * numOfMonthsAgo);
	    return c.getTime();
	}
	
	
}
