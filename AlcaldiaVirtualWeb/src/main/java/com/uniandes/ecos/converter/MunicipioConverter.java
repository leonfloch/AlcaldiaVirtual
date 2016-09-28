package com.uniandes.ecos.converter;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import com.uniandes.ecos.entities.Municipio;

@FacesConverter("municipioConverter")
public class MunicipioConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromPickListComponent(component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String string;
		
		if(value == null)
			string="";
		else{
			string = String.valueOf(((Municipio)value).getMunicipioId());
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private Municipio getObjectFromPickListComponent(UIComponent component, String value){
		final DualListModel<Municipio> dualList;
		Municipio municipio;
		
		dualList = (DualListModel<Municipio>)((PickList)component).getValue();
		municipio = getObjectFromList(dualList.getSource(), Long.parseLong(value));
		
		if(municipio == null){
			municipio = getObjectFromList(dualList.getTarget(), Long.parseLong(value));
		}
		return municipio;
	}
	
	private Municipio getObjectFromList(final List<?> list, final long identifier){
		for(final Object object : list){
			final Municipio municipio = (Municipio)object;
			
			if(municipio.getMunicipioId() == identifier)
				return municipio;
		}
		return null;
	}
}
