package com.uniandes.ecos.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;


/**
 * Clase gen�rica para manejo de persistencia. 
 * 
 * @author Juan Albarrac�n
 * @version 1.0
 * @date 18/07/2016
 */
public class BaseDao<E, K> {

	/** Clase de la entidad. */
	protected Class<E> entityClass;

	/** EntityManager del contexto de persistencia. */
	private EntityManager entityManager;

	/**
	 * Constructor sin par�metros de la clase. 
	 */
	@SuppressWarnings("unchecked")
	public BaseDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass
				.getActualTypeArguments()[0];
	}

	/**
	 * Constructor con par�metros. 
	 * 
	 * @param entityClass
	 * @param entityManager
	 */
	@SuppressWarnings("unchecked")
	public BaseDao(@SuppressWarnings("rawtypes") Class entityClass, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
	}

	/**
	 * Constructor con par�metros.
	 * 
	 * @param entityManager
	 */
	public BaseDao(EntityManager entityManager) {
		this();
		this.entityManager = entityManager;
	}

	/**
	 * Persiste la entidad enviada como par�metro.
	 * 
	 * @param entity
	 */
	public void persist(E entity) {
		entityManager.persist(entity);
	}

	/**
	 * Actualiza la entidad enviada como par�metro.
	 * 
	 * @param entity
	 */
	public void merge(E entity) {
		entityManager.merge(entity);
		entityManager.flush();
	}

	/**
	 * Elimina la entidad enviada como par�metro.
	 * 
	 * @param entity
	 */
	public void remove(E entity) {
		entityManager.remove(entity);
	}

	/**
	 * Obtiene la entidad filtrada por la llave primaria. 
	 * 
	 * @param id
	 * @return
	 */
	public E findById(K id) {
		return entityManager.find(entityClass, id);
	}

	/**
	 * M�todo gen�rico para obtenci�n de una entidad por su llave primaria. 
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object findSpecificEntityById(@SuppressWarnings("rawtypes") Class entityClass, Object id) {
		return entityManager.find(entityClass, id);
	}

	/**
	 * Retorna el EntityManager
	 * 
	 * @return
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/**
	 * Setea del EntityManager
	 * 
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}