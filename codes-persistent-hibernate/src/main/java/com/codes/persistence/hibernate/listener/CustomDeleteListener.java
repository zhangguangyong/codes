package com.codes.persistence.hibernate.listener;

import org.hibernate.HibernateException;
import org.hibernate.event.internal.DefaultDeleteEventListener;
import org.hibernate.event.spi.DeleteEvent;

@SuppressWarnings("serial")
public class CustomDeleteListener extends DefaultDeleteEventListener{
	
	@Override
	public void onDelete(DeleteEvent event) throws HibernateException {
		System.out.println("----------CustomDeleteListener------------");
		super.onDelete(event);
	}
	
}
