package ar.edu.unq.epers.bichomon.backend.dao.impl;


import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.query.Query;

import ar.edu.unq.epers.bichomon.backend.dao.EvolucionDAO;
import ar.edu.unq.epers.bichomon.backend.dao.jdbc.JDBCEspecieDAO;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.CriterioEvolucion;
import ar.edu.unq.epers.bichomon.backend.model.evolucion.Evolucion;
import ar.edu.unq.epers.bichomon.backend.service.runner.Runner;

/**
 * Una implementacion de {@link JDBCEspecieDAO} que persiste
 * en una base de datos relacional utilizando JDBC
 * 
 */
public class HibernateEvolucionDAO implements EvolucionDAO {

	/**
	 * TODO
	 */
	@Override
	public Evolucion getEvolucion(String nombreEspecie) {
		Session session = Runner.getCurrentSession();
		return session.get(Evolucion.class, nombreEspecie);
	}

	/**
	 * TODO
	 */
	@Override
	public Collection<CriterioEvolucion> getCriteriosDeEvolucion(String nombreEspecie) {
		
		Session session = Runner.getCurrentSession();
		
		String hql = "from CriterioEvolucion c "
				+ "where c.especie == :unaEspecie";
		
		Query<CriterioEvolucion> query = session.createQuery(hql,  CriterioEvolucion.class);
		query.setParameter("unValorDado", nombreEspecie);

		return query.getResultList();
	}

}
