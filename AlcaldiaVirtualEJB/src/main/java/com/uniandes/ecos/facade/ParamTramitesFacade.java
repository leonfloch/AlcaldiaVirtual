package com.uniandes.ecos.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.uniandes.ecos.entities.DocsXTipoTramite;
import com.uniandes.ecos.entities.DocumentoRequerido;
import com.uniandes.ecos.entities.Formulario;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoCampo;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.interfaz.services.parametrizacion.IFormulariosParamService;
import com.uniandes.ecos.interfaz.services.parametrizacion.ITramitesParamService;
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
    
    /**
     * Inyección de dependencia con componente de parametrización de
     * tramites.
     */
    @EJB
    private ITramitesParamService iTramitesParamService;


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

    /*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * crearTipoTramite()
     */
	@Override
	public void crearTipoTramite(TipoTramite tipoTramite) throws NegocioException {
		iTramitesParamService.crearTipoTramite(tipoTramite);
		
	}
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * actualizarTipoTramite()
     */
	@Override
	public void actualizarTipoTramite(TipoTramite tipoTramite) throws NegocioException{
		iTramitesParamService.actualizarTipoTramite(tipoTramite);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * obtenerListaTramites()
     */
	@Override
	public List<TipoTramite> obtenerListaTiposTramites(String nombre) throws NegocioException {
		return iTramitesParamService.obtenerListaTiposTramites(nombre);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * obtenerListaMunicipios()
     */
	@Override
	public List<Municipio> obtenerListaMunicipios() throws NegocioException{
		return iTramitesParamService.obtenerListaMunicipios();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * obtenerListaMunicipiosXTipoTramite()
     */
	@Override
	public List<Municipio> obtenerListaMunicipiosXTipoTramite(long tipoTramiteId) throws NegocioException{
		return iTramitesParamService.obtenerListaMunicipiosXTipoTramite(tipoTramiteId);
	}

	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * crearTramiteXMunicipio()
     */
	@Override
	public void crearTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException {
		iTramitesParamService.crearTramiteXMunicipio(tramiteXMunicipio);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.uniandes.ecos.interfaz.facade.IParamTramitesFacade#
	 * actualizarTramiteXMunicipio()
     */
	@Override
	public void actualizarTramiteXMunicipio(TramiteXMunicipio tramiteXMunicipio) throws NegocioException{
		iTramitesParamService.actualizarTramiteXMunicipio(tramiteXMunicipio);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerTramiteXMunicipioXTipoTramiteId()
	 */
	@Override
	public List<TramiteXMunicipio> obtenerTramiteXMunicipioXTipoTramiteId(long tipoTramiteId) throws NegocioException {
		return iTramitesParamService.obtenerTramiteXMunicipioXTipoTramiteId(tipoTramiteId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerListaDocumentos()
	 */
	@Override
	public List<DocumentoRequerido> obtenerListaDocumentos() throws NegocioException {
		return iTramitesParamService.obtenerListaDocumentos();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerListaDocumentosXTipoTramite()
	 */
	@Override
	public List<DocumentoRequerido> obtenerListaDocumentosXTipoTramite(long tipoTramiteId) throws NegocioException {
		return iTramitesParamService.obtenerListaDocumentosXTipoTramite(tipoTramiteId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * actualizarDocumentoXTramite()
	 */
	@Override
	public void actualizarDocumentoXTramite(DocsXTipoTramite docsXTipoTramite) throws NegocioException {
		iTramitesParamService.actualizarDocumentoXTramite(docsXTipoTramite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.uniandes.ecos.interfaz.services.parametrizacion.Municipio#
	 * obtenerDocumentosXTramiteXTipoTramiteId()
	 */
	@Override
	public List<DocsXTipoTramite> obtenerDocumentosXTramiteXTipoTramiteId(long tipoTramiteId) throws NegocioException {
		return iTramitesParamService.obtenerDocumentosXTramiteXTipoTramiteId(tipoTramiteId);
	}
}
