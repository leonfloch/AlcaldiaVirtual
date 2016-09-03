package com.uniandes.ecos.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService;
import com.uniandes.ecos.util.NegocioException;

/**
 * Fachada implementacion encargada de toda la parametrizacion de tramites de la
 * aplicacion
 *
 * @author 80221940
 *
 */
@Stateless
public class ParamTramitesFacade implements IParamTramitesFacade {

    /**
     * Inyección de dependencia con componente de parametrización de
     * formularios.
     */
    @EJB
    private IFormulariosParamService iFormulariosParamService;


    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * crearFormulario(com.uniandes.ecos.entities.Formulario)
     */
    @Override
    public void crearFormulario(Formulario formulario) throws NegocioException {
        iFormulariosParamService.crearFormulario(formulario);
    }

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * actualizarFormulario(com.uniandes.ecos.entities.Formulario)
     */
    @Override
    public void actualizarFormulario(Formulario formulario) throws NegocioException {
        iFormulariosParamService.actualizarFormulario(formulario);
    }

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * obtenerFormularios()
     */
    @Override
    public List<Formulario> obtenerFormularios(String nombre) throws NegocioException {
        return iFormulariosParamService.obtenerFormularios(nombre);
    }

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * obtenerFormulario()
     */
    @Override
    public Formulario obtenerFormulario(long formularioId) throws NegocioException {
        return iFormulariosParamService.obtenerFormulario(formularioId);
    }

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * obtenerTiposCampoForm()
     */
    @Override
    public List<TipoCampo> obtenerTiposCampoForm() throws NegocioException {
        return iFormulariosParamService.obtenerTiposCampoForm();
    }

}
