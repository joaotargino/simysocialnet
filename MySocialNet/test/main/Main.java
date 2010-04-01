package main;
import java.util.ArrayList;
import java.util.List;
import easyaccept.EasyAcceptFacade;

public class Main {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();
		files.add("US01.txt");
		Facade yourTestFacade = new Facade();
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(yourTestFacade, files);
		eaFacade.executeTests();
		System.out.println(eaFacade.getCompleteResults());

	}

}