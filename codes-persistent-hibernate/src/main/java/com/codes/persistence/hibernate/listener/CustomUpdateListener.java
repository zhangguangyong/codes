package com.codes.persistence.hibernate.listener;

import org.hibernate.event.internal.DefaultUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;

@SuppressWarnings("serial")
public class CustomUpdateListener extends DefaultUpdateEventListener{
	
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		System.out.println("-------------CustomUpdateListener-------------");
		super.onSaveOrUpdate(event);
	}
	
}
