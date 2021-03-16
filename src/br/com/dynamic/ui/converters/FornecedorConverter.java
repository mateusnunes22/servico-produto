package br.com.dynamic.ui.converters;

import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.dynamic.entidade.Fornecedor;

@ManagedBean(name = "fornecedorConverter")
@FacesConverter(forClass = Fornecedor.class)
public class FornecedorConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value) {
		if (value != null && !value.isEmpty()) {
            return (Fornecedor) component.getAttributes().get(value);
        }
        return null;
	}

	@Override
	public String getAsString(FacesContext fc, UIComponent uic, Object value) {
		if (value instanceof Fornecedor) {
			Fornecedor entity= (Fornecedor) value;
            if (entity != null && entity instanceof Fornecedor && entity.getId() != null) {
                uic.getAttributes().put( entity.getId().toString(), entity);
                return entity.getId().toString();
            }
        }
        return "";
	}

}