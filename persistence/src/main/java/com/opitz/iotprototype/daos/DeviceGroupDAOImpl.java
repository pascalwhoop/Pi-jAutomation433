package com.opitz.iotprototype.daos;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.opitz.iotprototype.entities.DeviceGroup;

/**
 * User: Pascal Date: 04.04.14 Time: 14:50
 */

@Repository
public class DeviceGroupDAOImpl implements DeviceGroupDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Serializable save(DeviceGroup devicegroup) {
		Session session = sessionFactory.getCurrentSession();
		return session.save(devicegroup);
	}

	@Override
	public void update(DeviceGroup devicegroup) {
		Session session = sessionFactory.getCurrentSession();
		session.update(devicegroup);
	}

	@Override
	public void delete(Serializable id) {
		DeviceGroup deviceGroup = load(id);
		Session session = sessionFactory.getCurrentSession();
		session.delete(deviceGroup);
	}

	@Override
	public DeviceGroup findByLabel(String label) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
		    .createQuery("from DeviceGroup dv where (lower(dv.label)  LIKE lower('%' || :name || '%')) ");
		query.setParameter("name", label);
		return (DeviceGroup) query.uniqueResult();
	}

	@Override
	public DeviceGroup load(Serializable id) {
		Session session = sessionFactory.getCurrentSession();
		DeviceGroup deviceGroup = (DeviceGroup) session.get(DeviceGroup.class, id);
		return deviceGroup;
	}

	@Override
	public List<DeviceGroup> listAll() {
		Session session = sessionFactory.getCurrentSession();
		List<DeviceGroup> deviceGroups = session.createCriteria(DeviceGroup.class)
		    .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
		    .list();
		return deviceGroups;
	}
}
