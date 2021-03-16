<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://richfaces.org/a4j" prefix="a4j" %>
<%@ taglib uri="http://richfaces.org/rich" prefix="rich" %>
<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Venda</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<style type="text/css">
</style>

<script src="Scripts/AC_RunActiveContent.js" type="text/javascript"></script>
</head>

<body>
	<%@ include file = "menu.html" %>

<f:view>

<div id="impmi">

<h:form>


     
        <h3>Registro de Venda</h3><br></br>
        <h:panelGrid columns="2">
       
        <h:outputText value="Funcionario"></h:outputText>
        
        <h:selectOneMenu id="funcionario"
   		 value="#{vendaBean.idFuncionario }">
        <f:selectItems value="#{funcionarioBean.funcionarioNome }"  />
		</h:selectOneMenu>

        <h:outputText value="Serviço"></h:outputText>
        <h:selectOneMenu id="numeroUsuario" onchange="submit()"
   		valueChangeListener="#{vendaBean.countryLocaleCodeChanged}"
   		 value="#{vendaBean.idServico }">
        <f:selectItems value="#{servicoBean.servicoNome }"  />
		</h:selectOneMenu>
		
		<h:outputText value="Produto"></h:outputText>
		<h:selectOneMenu id="produto" 
			value="#{vendaBean.idProduto}"> 
		<f:selectItems value="#{vendaBean.produtoNome }" />
		</h:selectOneMenu>
			
		<h:outputText value="Valor"></h:outputText>
		
        <h:inputText id="valor" value="#{vendaBean.venda.valor }" 
        size="20"></h:inputText>

        </h:panelGrid>
        
        <h:commandButton value="Adicionar" action="#{vendaBean.salvar}"></h:commandButton>

        
        
 

</h:form>
</div>
</f:view>

</body>
</html>
