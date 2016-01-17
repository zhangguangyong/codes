package com.codes.persistence.hibernate.test;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codes.persistence.hibernate.dao.impl.SqlTemplate;
import com.codes.persistence.hibernate.domain.Page;
import com.codes.persistence.hibernate.domain.PageParameter;

public class SqlDaoTemplateTest {

	SqlTemplate sqlDao = new SqlTemplate();
	@Before
	public void before(){
		sqlDao.setSessionFactory(HibernateUtil.getSessionFactory());
		sqlDao.getSessionFactory().getCurrentSession().beginTransaction();
	}
	@After
	public void after(){
		sqlDao.getSessionFactory().getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void testFind() {
		String sql = "select * from person t where t.firstname like ? or t.nickname like ?";
		List<?> list = sqlDao.find(sql, "%张%", "ab");
		System.out.println(list);
	}

	@Test
	public void testFind1() {
		String sql = "select * from person t where t.firstname like :firstname or t.nickname like :nickname";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("firstname", "%张%");
		params.put("nickname", "%a%");
		List<?> list = sqlDao.find(sql, params);
		System.out.println(list);
	}

	@Test
	public void testFind2() {
		PageParameter pageParameter = new PageParameter(1, 10);
		String sql = "select * from person t where t.firstname like ? or t.nickname like ?";
		Page<?> page = sqlDao.findForPage(sql, pageParameter, "%张%", "ab");
		System.out.println(page.getTotalRows());
	}

	@Test
	public void testFind3() {
		Object object = sqlDao.findForObject("select firstname from person t where t.id=?", String.class, 4);
		System.out.println(object);
	}

	@Test
	public void testFind4() {
		Object object = sqlDao.findForList("select firstname from person ", String.class);
		System.out.println(object);
	}

	@Test
	public void testFind5() {
		Object object = sqlDao.findForMap("select firstname \"firstName\" from person where id = 4");
		System.out.println(object);
	}

}
