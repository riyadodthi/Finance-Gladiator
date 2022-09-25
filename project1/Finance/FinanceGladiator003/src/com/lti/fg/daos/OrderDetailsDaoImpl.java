package com.lti.fg.daos;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lti.fg.entities.OrderDetails;
import com.lti.fg.exceptions.OrderException;

@Repository
public class OrderDetailsDaoImpl implements OrderDetailsDao{
	@PersistenceContext
	EntityManager manager;
	
	@Override
	public boolean addOrderRecord(OrderDetails order) throws OrderException {
		Query query = manager.createQuery("select MAX(orderId) from OrderDetails");
    	Integer orderId = (Integer)query.getSingleResult();
    	if(orderId == null)
    		orderId=0;
    	orderId+=1;
    	order.setOrderId(orderId);
		manager.persist(order);
		return true;
	}

}
