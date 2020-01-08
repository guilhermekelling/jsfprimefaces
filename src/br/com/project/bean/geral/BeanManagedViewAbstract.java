package br.com.project.bean.geral;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.hibernate.Query;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.stereotype.Component;

import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.annotation.IdentificaCampoPesquisa;
import br.com.project.enums.CondicaoPesquisa;
import br.com.project.report.util.BeanReportView;
import br.com.project.util.all.Messagens;
import br.com.project.util.all.UtilitariaRegex;

@Component
public abstract class BeanManagedViewAbstract extends BeanReportView {

	private static final long serialVersionUID = 1L;
	
	protected abstract Class<?> getClassImplement();
	
	protected abstract InterfaceCrud<?> getController();
	
	public ObjetoCampoConsulta objetoCampoConsultaSelecionado;
	
	public List<SelectItem> listaCampoPesquisa;

	public List<SelectItem> listaCondicaoPesquisa;
	
	public CondicaoPesquisa condicaoPesquisaSelecionado;
	
	public String valorPesquisa;
	
	public void setValorPesquisa(String valorPesquisa) {
		this.valorPesquisa = valorPesquisa;
	}
	
	public String getValorPesquisa() {
		return valorPesquisa != null ? new  UtilitariaRegex().retiraAcentos(valorPesquisa.trim() ): "";
	}	
	
	public void setCondicaoPesquisaSelecionado(
			CondicaoPesquisa condicaoPesquisaSelecionado) {
		this.condicaoPesquisaSelecionado = condicaoPesquisaSelecionado;
	}
	
	public CondicaoPesquisa getCondicaoPesquisaSelecionado() {
		return condicaoPesquisaSelecionado;
	}
	
	public List<SelectItem> getListaCondicaoPesquisa() {
		listaCondicaoPesquisa = new ArrayList<SelectItem>();
		for (CondicaoPesquisa enumCp : CondicaoPesquisa.values()) {
			listaCondicaoPesquisa
					.add(new SelectItem(enumCp, enumCp.toString()));
		}
		return listaCondicaoPesquisa; 
	}

	public ObjetoCampoConsulta getObjetoCampoConsultaSelecionado() {
		return objetoCampoConsultaSelecionado;
	}

	public void setObjetoCampoConsultaSelecionado(
			ObjetoCampoConsulta objetoCampoConsultaSelecionado) {
		if (objetoCampoConsultaSelecionado != null) {
			for (Field field : getClassImplement().getDeclaredFields()) {
				if (field.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
					if (this.objetoCampoConsultaSelecionado.getCampoBanco().equalsIgnoreCase(field.getName())) {
						String descricaoCampo = field.getAnnotation(IdentificaCampoPesquisa.class).descricaoCampo();
						objetoCampoConsultaSelecionado.setDescricao(descricaoCampo);
						objetoCampoConsultaSelecionado.setTipoClass(field.getType().getCanonicalName());
						objetoCampoConsultaSelecionado.setTipoClass(field.getAnnotation(IdentificaCampoPesquisa.class).principal());
						break;
						
					}
				}
			}
			
		}
	}	
	
	public List<SelectItem> getListaCampoPesquisa() {
		listaCampoPesquisa = new ArrayList<SelectItem>();
		List<ObjetoCampoConsulta> listTemp = new ArrayList<ObjetoCampoConsulta>();
		
		for (Field field : getClassImplement().getDeclaredFields()) {
			if(field.isAnnotationPresent(IdentificaCampoPesquisa.class)) {
				String descicao = field.getAnnotation(IdentificaCampoPesquisa.class).descricaoCampo();
				String descicaoCampoPesquisa = field.getAnnotation(IdentificaCampoPesquisa.class).campoConsulta();
				int isPrincipal = field.getAnnotation(IdentificaCampoPesquisa.class).principal();
				
				ObjetoCampoConsulta objetoCampoConsulta = new ObjetoCampoConsulta();
				objetoCampoConsulta.setDescricao(descicao);
				objetoCampoConsulta.setCampoBanco(descicaoCampoPesquisa);
				objetoCampoConsulta.setTipoClass(field.getType().getCanonicalName());
				objetoCampoConsulta.setPrincipal(isPrincipal);
				listTemp.add(objetoCampoConsulta);
			}
		}
		
		orderReverse(listTemp);
		
		for(ObjetoCampoConsulta objetoCampoConsulta : listTemp) {
			listaCampoPesquisa.add(new SelectItem(objetoCampoConsulta));			
		}
		
		return listaCampoPesquisa;
	}

	private void orderReverse(List<ObjetoCampoConsulta> listTemp) {
		Collections.sort(listTemp, new Comparator<ObjetoCampoConsulta>() {

			@Override
			public int compare(ObjetoCampoConsulta objt1, ObjetoCampoConsulta objt2) {
				return objt1.getPrincipal().compareTo(objt2.getPrincipal());
			}
		});
	}
	
}
