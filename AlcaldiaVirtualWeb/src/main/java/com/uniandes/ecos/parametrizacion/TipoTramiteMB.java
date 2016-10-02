package com.uniandes.ecos.parametrizacion;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.primefaces.event.FlowEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DualListModel;

import com.uniandes.ecos.comun.BaseMBean;
import com.uniandes.ecos.entities.DocsXTipoTramite;
import com.uniandes.ecos.entities.DocumentoRequerido;
import com.uniandes.ecos.entities.Municipio;
import com.uniandes.ecos.entities.TipoTramite;
import com.uniandes.ecos.entities.TramiteXMunicipio;
import com.uniandes.ecos.entities.UsuarioSesion;
import com.uniandes.ecos.entities.UsuariosFuncionario;
import com.uniandes.ecos.interfaz.facade.IParamTramitesFacade;
import com.uniandes.ecos.util.Constantes;
import com.uniandes.ecos.util.NegocioException;

@ViewScoped
@ManagedBean
public class TipoTramiteMB extends BaseMBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8698695809011793289L;

	/**
	 * Servicio de parametrizacion
	 */
	@Inject
	private IParamTramitesFacade iParamTramitesFacade;

	/**
	 * Lista de tipos tramite
	 */
	private List<TipoTramite> tiposTramite;
	/**
	 * Lista Municipios disponibles
	 */
	private List<Municipio> municipiosDisponibles;
	/**
	 * Lista Municipios seleccionados
	 */
	private List<Municipio> municipiosSeleccionados;
	/**
	 * Lista Documentos disponibles
	 */
	private List<DocumentoRequerido> documentosDisponibles;
	/**
	 * Lista documentos seleccionados
	 */
	private List<DocumentoRequerido> documentosSeleccionados;
	/**
	 * Lista de tramites por municipios
	 */
	private List<TramiteXMunicipio> tramitesXMunicipios;
	/**
	 * Lista de tramites por municipios
	 */
	private List<DocsXTipoTramite> documentosXTramites;
	/**
	 * Lista de municipios
	 */
	private DualListModel<Municipio> municipios;
	/**
	 * Lista de municipios
	 */
	private DualListModel<DocumentoRequerido> documentos;
	/**
	 * Tipo de tramite seleccionado para edicion
	 */
	private TipoTramite tipoTramiteSelecc;

	/**
	 * indica si se cra un nuevo funcionario
	 */
	private boolean creacion;

	/**
	 * Constructor
	 */
	public TipoTramiteMB() {
		creacion = false;
		this.initTiposTramite();
	}

	@PostConstruct
	public void init() {

		try {
			this.consultarTiposTramite();
			this.reiniciarDualList();
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	/**
	 * Se ejecuta antes de abril el modal de edicion de tipos tramite
	 */
	public void preModal() {
		System.out.println("Iniciando creacion / edicion");
		try {
			if (creacion) {
				System.out.println("Creacion");
				this.initTiposTramite();
				this.reiniciarDualList();
			} else {
				System.out.println("Edicion");
				tramitesXMunicipios = iParamTramitesFacade
						.obtenerTramiteXMunicipioXTipoTramiteId(tipoTramiteSelecc.getTipoTramiteId());
				documentosXTramites = iParamTramitesFacade
						.obtenerDocumentosXTramiteXTipoTramiteId(tipoTramiteSelecc.getTipoTramiteId());
				municipiosSeleccionados = iParamTramitesFacade
						.obtenerListaMunicipiosXTipoTramite(tipoTramiteSelecc.getTipoTramiteId());
				documentosSeleccionados = iParamTramitesFacade
						.obtenerListaDocumentosXTipoTramite(tipoTramiteSelecc.getTipoTramiteId());
				
				List<Municipio> municipiosEliminar = new ArrayList<>();
				List<DocumentoRequerido> documentosEliminar = new ArrayList<>();

				for (Municipio md : municipiosDisponibles) {
					for (Municipio ms : municipiosSeleccionados) {
						if (md.getMunicipioId() == ms.getMunicipioId()) {
							municipiosEliminar.add(md);
							break;
						}
					}
				}
				
				for (DocumentoRequerido dd : documentosDisponibles) {
					for (DocumentoRequerido ds : documentosSeleccionados) {
						if (dd.getDocRequeridoId() == ds.getDocRequeridoId()) {
							documentosEliminar.add(dd);
							break;
						}
					}
				}

				municipiosDisponibles.removeAll(municipiosEliminar);
				documentosDisponibles.removeAll(documentosEliminar);

				municipios.setTarget(municipiosSeleccionados);
				documentos.setTarget(documentosSeleccionados);

			}
		} catch (NegocioException e) {
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}

	public void consultarTiposTramite() throws NegocioException {
		tiposTramite = iParamTramitesFacade.obtenerListaTiposTramites(null);
	}

	public void persistirTipoTramite() {
		try {
			UsuarioSesion usuario = (UsuarioSesion) this.obtenerVariableSesion(Constantes.SESION_USUARIO);
			UsuariosFuncionario usuarioFuncionario = new UsuariosFuncionario();
			usuarioFuncionario.setUsuario(usuario.getUsuario());

			procesarMunicipios(usuarioFuncionario);
			procesarDocumentos();
			
			iParamTramitesFacade.actualizarTipoTramite(tipoTramiteSelecc);
			consultarTiposTramite();

			this.adicionarMensaje(Constantes.INFO, "Tipo Tramite Guardado");
		} catch (

		NegocioException e) {
			// TODO Auto-generated catch block
			this.adicionarMensaje(e.getTipo(), e.getMensaje());
		}
	}
	
	private void procesarMunicipios(UsuariosFuncionario usuarioFuncionario) throws NegocioException{
		List<Municipio> municipiosDisponibles = municipios.getSource();
		List<Municipio> municipiosSeleccionados = municipios.getTarget();
		List<TramiteXMunicipio> municipiosXTramite = new ArrayList<>();

		for (Municipio ms : municipiosSeleccionados) {

			if (!cambiarEstadoTramiteMunicipio(Constantes.ACTIVO, ms.getMunicipioId())) {
				TramiteXMunicipio txm = new TramiteXMunicipio();
				txm.setMunicipio(ms);
				txm.setTiposTramite(tipoTramiteSelecc);
				txm.setEstado("A");
				txm.setUsuariosFuncionario(usuarioFuncionario);
				municipiosXTramite.add(txm);
			}
		}

		for (Municipio md : municipiosDisponibles) {
			cambiarEstadoTramiteMunicipio(Constantes.INACTIVO, md.getMunicipioId());
		}

		tipoTramiteSelecc.setTramitesXMunicipios(municipiosXTramite);
		
	}
	
	private void procesarDocumentos() throws NegocioException{
		List<DocumentoRequerido> documentosDisponibles = documentos.getSource();
		List<DocumentoRequerido> documentosSeleccionados = documentos.getTarget();
		List<DocsXTipoTramite> documentosXTramite = new ArrayList<>();

		for (DocumentoRequerido ds : documentosSeleccionados) {

			if (!cambiarEstadoTramiteDocumento(Constantes.ACTIVO, ds.getDocRequeridoId())) {
				DocsXTipoTramite dxtt = new DocsXTipoTramite();
				dxtt.setDocumentosRequerido(ds);
				dxtt.setEstado("A");
				dxtt.setTiposTramite(tipoTramiteSelecc);
				documentosXTramite.add(dxtt);
			}
		}

		for (DocumentoRequerido dd : documentosDisponibles) {
			cambiarEstadoTramiteDocumento(Constantes.INACTIVO, dd.getDocRequeridoId());
		}

		tipoTramiteSelecc.setDocsXTipoTramites(documentosXTramite);
	}

	public void limpiarAtributos() {
		initTiposTramite();
		init();
	}

	private boolean cambiarEstadoTramiteMunicipio(String estado, long idMunicipio) throws NegocioException {
		boolean existe = false;
		for (TramiteXMunicipio txm : tramitesXMunicipios) {
			if (idMunicipio == txm.getMunicipio().getMunicipioId()) {
				txm.setEstado(estado);
				iParamTramitesFacade.actualizarTramiteXMunicipio(txm);
				existe = true;
				break;
			}
		}
		return existe;
	}
	
	private boolean cambiarEstadoTramiteDocumento(String estado, long idDocumento) throws NegocioException {
		boolean existe = false;
		for (DocsXTipoTramite dxtt : documentosXTramites) {
			if (idDocumento == dxtt.getDocumentosRequerido().getDocRequeridoId()) {
				dxtt.setEstado(estado);
				iParamTramitesFacade.actualizarDocumentoXTramite(dxtt);
				existe = true;
				break;
			}
		}
		return existe;
	}

	private void reiniciarDualList() throws NegocioException {
		municipiosDisponibles = iParamTramitesFacade.obtenerListaMunicipios();
		documentosDisponibles = iParamTramitesFacade.obtenerListaDocumentos();
		municipios = new DualListModel<>(municipiosDisponibles, new ArrayList<Municipio>());
		documentos = new DualListModel<>(documentosDisponibles, new ArrayList<DocumentoRequerido>());
	}

	public void onTabChange(TabChangeEvent event) {
		System.out.println("Cambiando Tab");

	}

	public String onFlowProcess(FlowEvent event) {
		return event.getNewStep();
	}

	/**
	 * Limpia la informacion del tipo tramite
	 */
	private void initTiposTramite() {
		tipoTramiteSelecc = new TipoTramite();
		tramitesXMunicipios = new ArrayList<>();
		documentosXTramites = new ArrayList<>();

	}

	/**
	 * @return the tiposTramite
	 */
	public List<TipoTramite> getTiposTramite() {
		return tiposTramite;
	}

	/**
	 * @param tiposTramite
	 *            the tiposTramite to set
	 */
	public void setTiposTramite(List<TipoTramite> tiposTramite) {
		this.tiposTramite = tiposTramite;
	}

	/**
	 * @return the tipoTramiteSelecc
	 */
	public TipoTramite getTipoTramiteSelecc() {
		return tipoTramiteSelecc;
	}

	/**
	 * @param tipoTramiteSelecc
	 *            the tipoTramiteSelecc to set
	 */
	public void setTipoTramiteSelecc(TipoTramite tipoTramiteSelecc) {
		this.tipoTramiteSelecc = tipoTramiteSelecc;
	}

	/**
	 * @return the municipios
	 */
	public DualListModel<Municipio> getMunicipios() {
		return municipios;
	}

	/**
	 * @param municipios
	 *            the municipios to set
	 */
	public void setMunicipios(DualListModel<Municipio> municipios) {
		this.municipios = municipios;
	}

	/**
	 * @return the creacion
	 */
	public boolean isCreacion() {
		return creacion;
	}

	/**
	 * @param creacion
	 *            the creacion to set
	 */
	public void setCreacion(boolean creacion) {
		this.creacion = creacion;
	}

	/**
	 * @return the documentos
	 */
	public DualListModel<DocumentoRequerido> getDocumentos() {
		return documentos;
	}

	/**
	 * @param documentos
	 *            the documentos to set
	 */
	public void setDocumentos(DualListModel<DocumentoRequerido> documentos) {
		this.documentos = documentos;
	}

}
