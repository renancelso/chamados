/*
 * Decimal.java
 *
 * Created on 19 de Novembro de 2007, 17:09
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package br.com.chamadosweb.control.converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.jfree.util.Log;

/** 
 * @author Renan Celso 
 */
@FacesConverter(value = "data", forClass = Date.class)
public class Data implements Converter {

	@Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String valorTela) {
        Date valorRetorno;
        try {
            if (valorTela.length() == 10) {

                Calendar c = Calendar.getInstance();

                int ano = Integer.parseInt(valorTela.substring(6));
                int mes = Integer.parseInt(valorTela.substring(3, 5)) - 1;
                int dia = Integer.parseInt(valorTela.substring(0, 2));

                c.set(ano, mes, dia);

                valorRetorno = c.getTime();
                return valorRetorno;
            } else {
                return null;
            }

        } catch (Exception e) {
            Log.error(e);            
            return null;
        }


    }
    
    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object valorTela) {
        try {
            String formato = "dd/MM/yyyy";
            SimpleDateFormat formatter = new SimpleDateFormat(formato);
            Date data = (Date) valorTela;
            return formatter.format(data);
        } catch (Exception e) {
        	Log.error(e);       
            return null;
        }
    }
}
