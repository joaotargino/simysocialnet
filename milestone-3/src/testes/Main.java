package testes;

import java.util.ArrayList;
import java.util.List;

import easyaccept.EasyAcceptFacade;

public class Main {

    public static void main(String[] args) throws Exception {
    	
    	int QTD_DE_TESTES = 8;
        List<String> files = new ArrayList<String>();
        for (int i = 1; i <= QTD_DE_TESTES; i++) {
        	files.add("src/testes/US/US0"+i+".txt");
		}
        EasyAcceptFacade eaFacade = new EasyAcceptFacade(new Fachada(), files);
        eaFacade.executeTests();
        System.out.println(eaFacade.getCompleteResults());

    }

}

