package com.tapestry.vote.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.BeanEditForm;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import com.tapestry.vote.entities.Item;

/**
 * Start page of application vote.
 */
public class Index
{
	
	@Property
	private Item newItem;
	
	@Property
	private Item item;
	
	@Component
	private BeanEditForm form;
	
	@Component
	private Zone listZone;
	
	@Inject
	private Session session;
	
	@CommitAfter
	public void onSuccess() {
		session.persist(newItem);
	}
    
	public void onValidate() {
		try {
			URL url = new URL(newItem.getUrl());
		} catch (MalformedURLException e) {
			form.recordError("Invalid url!");
		}
	}
	
	public List<Item> getItems() {
		return session.createCriteria(Item.class).addOrder(Order.desc("votes")).list();
	}
	
	@CommitAfter
	public Object onActionFromVote(Item item) {
		item.setVotes(item.getVotes()+1);
		session.persist(item);
		return listZone.getBody();
	}
    public Date getCurrentTime()
    {
        return new Date();
    }
}
