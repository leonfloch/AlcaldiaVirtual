/**
 *
 */
package com.uniandes.ecos.facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.interfaz.facade.ISeguridadFacade;
import com.uniandes.ecos.interfaz.services.seguridad.IAutenticacionService;
import com.uniandes.ecos.interfaz.services.seguridad.IPermisosService;
import com.uniandes.ecos.util.SeguridadException;

/**
 * Fachada implementacion encargada de toda la parte de seguridad de la
 * aplicacion
 *
 * @author leonardovalbuenacalderon
 *
 */
@Stateless
public class SeguridadFacade implements ISeguridadFacade {

    /**
     * Servicio encargado del manejo de la seguridad
     */
    @EJB
    private IAutenticacionService seguridadService;

    /**
     * Servicio encargado del manejo de los permisos del usuario
     */
    @EJB
    private IPermisosService permisosService;

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.facadeInterface.ISeguridadFacade#autenticar(int, java.lang.String)
     */
    @Override
    public UsuarioSesion autenticar(int cedula, String password, boolean esFuncionario)
            throws SeguridadException {
        return seguridadService.autenticar(cedula, password, esFuncionario);
    }

}
