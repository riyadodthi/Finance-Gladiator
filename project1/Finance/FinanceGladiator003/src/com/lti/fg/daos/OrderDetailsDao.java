package com.lti.fg.daos;

import com.lti.fg.entities.OrderDetails;
import com.lti.fg.exceptions.OrderException;

public interface OrderDetailsDao {
	
	public boolean addOrderRecord(OrderDetails order) throws OrderException; 
}
