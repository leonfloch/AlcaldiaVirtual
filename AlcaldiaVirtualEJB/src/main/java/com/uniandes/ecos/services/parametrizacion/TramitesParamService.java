/**
 *
 */
package com.uniandes.ecos.services.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.uniandes.ecos.dao.BaseDao;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.interfaz.services.parametrizacion.ITramitesParamService;
import com.uniandes.ecos.util.NegocioException;

/**
 * Implementacion Servicio encargado de crear los tipo tramites
 *
 * @author 80221940
 *
 */
@Stateless
public class TramitesParamService implements ITramitesParamService {

	/**
     * Inyecciï¿½n del contexto de persistencia de la aplicaciï¿½n.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Instanciación del objeto dao para el manejo de persistencia.
     */
    private BaseDao<TipoTramite, Long> tipoTramiteDao;

    /**
     * Inicialización de objetos del bean.
     */
    @PostConstruct
    public void init() {
        this.tipoTramiteDao = new BaseDao<TipoTramite, Long>(TipoTramite.class, this.em);
    }
    
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.ITramitesParamService#
	 * crearTipoTramite()
     */
	@Override
	public void crearTipoTramite(TipoTramite tipoTramite) throws NegocioException {
		this.tipoTramiteDao.persist(tipoTramite);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.ITramitesParamService#
	 * obtenerListaTramites(String nombre)
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoTramite> obtenerListaTramites(String nombre)throws NegocioException{
		Query query;
        List<TipoTramite> lstTipoTramite = new ArrayList<>();

        if (nombre != null && !nombre.isEmpty()) {
            query = this.em.createNamedQuery("TipoTramite.findByNombreLike");
            query.setParameter("nombre", "%" + nombre.toUpperCase() + "%");
        } else {
            query = this.em.createNamedQuery("TipoTramite.findAll");
        }

        //Se ejecuta la consulta
        try {
            lstTipoTramite = query.getResultList();
        } catch (NoResultException e) {
            e.printStackTrace();
        }

        return lstTipoTramite;
	}

}
