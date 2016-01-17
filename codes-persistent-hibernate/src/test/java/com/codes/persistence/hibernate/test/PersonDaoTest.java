package com.codes.persistence.hibernate.test;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.codes.common.util.$;
import com.codes.common.util.Datas;
import com.codes.common.util.Pinyins;
import com.codes.common.util.Reflections;
import com.codes.persistence.hibernate.domain.support.ExtendField;
import com.codes.persistence.hibernate.domain.support.ExtendFieldAnno;
import com.google.common.collect.Lists;

public class PersonDaoTest {
	PersonDao personDao = new PersonDao();
	
	@Before
	public void before(){
		personDao.setSessionFactory(HibernateUtil.getSessionFactory());
		 personDao.getSessionFactory().getCurrentSession().beginTransaction();
	}
	
	@After
	public void after(){
		personDao.getSessionFactory().getCurrentSession().getTransaction().commit();
	}
	
	@Test
	public void testSave(){
		Person entity = new Person();
		entity.setFirstname( Datas.getName() );
		personDao.saveOrUpdate(entity);
	}
	
	@Test
	public void testSaveTree(){
		Person root = createPerson();
		personDao.saveOrUpdate(root);
		
		//层级1
		for (int i = 1; i <= 5; i++) {
			Person p1 = createPerson();
			p1.setParent(root.getId());
			personDao.saveOrUpdate( p1 );
			
			//层级2
			for (int j = 1; j <= 5; j++) {
				Person p2 = createPerson();
				p2.setParent(p1.getId());
				personDao.saveOrUpdate( p2 );
				
				//层级3
				for (int k = 1; k <= 5; k++) {
					Person p3 = createPerson();
					p3.setParent(p2.getId());
					personDao.saveOrUpdate( p3 );
				}
			}
		}
	}
	
	@Test
	public void testMoveTreeNode(){
		Person person = createPerson();
		person.setId(71);
		person.setParent(83);
		personDao.saveOrUpdate(person);
	}
	
	@Test
	public void testMoveTreeNodeIndex(){
		
		Person person = personDao.findOne(84);
		person.setIndex(3);
		personDao.getSessionFactory().getCurrentSession().evict(person);
		personDao.saveOrUpdate(person);
	}
	
	@Test
	public void testDelete(){
	}
	
	@Test
	public void testUpdate(){
	}
	
	@Test
	public void testSubstringFunction(){
		// dbObject.getPath()
		Session session = personDao.getSessionFactory().getCurrentSession();
		String qs = "update " + Person.class.getName() + " set path=Concat(?, Substring(path, 1, 4)) where id = ?";
		session.createQuery(qs).setParameter(0, "abcd").setParameter(1, "4a4ccbe650ad0a900150ad0a92ba0000").executeUpdate();
	}
	
	@Test
	public void testBatchSave(){
		List<Person> list = Lists.newArrayList();
		for (int i = 1; i <= 5000; i++) {
			list.add( createPerson() );
		}
		personDao.saveOrUpdate(list);
	}
	
	private Person createPerson(){
		Person person = new Person();
		setPropsValue(person);
		return person;
	}
	
	private void setPropsValue(Person person){
		person.setAge( Datas.getAge() );
		person.setSex( Datas.getSex() );
		person.setPhone( Datas.getPhone() );
		person.setEmail( Datas.getEmail() );
		String name = Datas.getName();
		String firstname = name.substring(0, 1);
		String lastname = name.substring(1);
		if( name.length() > 3 ){
			firstname = name.substring(0, 2);
			lastname = name.substring(2);
		}
		person.setFirstname( firstname );
		person.setLastname( lastname );
		person.setNickname( Pinyins.getPinyin(name) );
		person.setQq( Datas.getQq() );
		person.setJob( Datas.getJob() );
	}
	
	@Test
	public void testFindAll(){
	}
	
	@Test
	public void testExists(){
	}
	
	@Test
	public void test(){
		Field[] fields = Reflections.getAllDeclaredFields(Person.class);
		for (Field field : fields) {
			ExtendFieldAnno anno = field.getAnnotation(ExtendFieldAnno.class);
			if( $.notNull( anno ) && anno.value() == ExtendField.Parent ){
				System.out.println( anno.value() );
			}
		}
	}
}
