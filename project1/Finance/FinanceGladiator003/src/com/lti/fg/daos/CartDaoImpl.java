package com.lti.fg.daos;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.lti.fg.entities.Cart;
import com.lti.fg.entities.User;
import com.lti.fg.exceptions.CartException;


@Repository
public class CartDaoImpl implements CartDao{
	
	@PersistenceContext
    private EntityManager manager;
	   
	    @Override
	    public List<Cart> veiwCart(int id) throws CartException {
	        Query query = manager.createQuery("from Cart where userId = " + id);
	        //query.setParameter(1, id);
	        List<Cart> cart = query.getResultList();
	        return cart;
	    }

	    @Override
	    public boolean insertIntoCart(Cart cart) throws CartException {
	    	Query query = manager.createQuery("select MAX(cartId) from Cart");
	    	Integer cartId = (Integer)query.getSingleResult();
	    	if(cartId == null)
	    		cartId=0;
	    	cartId+=1;
	    	cart.setCartId(cartId);
	    	//For calculating Product Quantity
	        Query query1 = manager.createQuery("from Cart where userId = " + cart.getUserId());
	        List <Cart> cartList = query1.getResultList();
	        for(Cart c:cartList)
	        {
	        	if(c.getProductId()==cart.getProductId())
	        	{
	        		int count= c.getQuantity();
	        		c.setQuantity(count+cart.getQuantity());
	        		return true;
	        		
	        	}
	        }
	    	manager.persist(cart);
	        return true;
	    }

		@Override
		public int getQuantity(int userId, int productId) throws CartException {
			 Query query = manager.createQuery("select quantity from Cart where userId = :userId and productId = :productId");
		     query.setParameter("userId", userId);
		     query.setParameter("productId", productId);
			 int prodQuant = (int)query.getSingleResult();
			 return prodQuant;
		}

		@Override
		public boolean deleteRecordById(int userId) throws CartException {
			Query query = manager.createQuery("delete from Cart where userId = " + userId);
			int numberOfRowsDeleted = query.executeUpdate();
			if(numberOfRowsDeleted>0)
				return true;
			return false;
		} 
	    
}
