package br.com.project.geral.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import br.com.framework.implementacao.crud.ImplementacaoCrud;
import br.com.framework.interfac.crud.InterfaceCrud;
import br.com.project.model.classes.Titulo;


@Controller
public class TituloController extends ImplementacaoCrud<Titulo> implements
		InterfaceCrud<Titulo> {

	private static final long serialVersionUID = 1L;

}
