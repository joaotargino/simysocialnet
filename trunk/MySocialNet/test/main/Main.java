package main;
import java.util.ArrayList;
import java.util.List;
import easyaccept.EasyAcceptFacade;

public class Main {

	public static void main(String[] args) throws Exception {

		List<String> files = new ArrayList<String>();
//		files.add("test/US01.txt");
//		files.add("test/US02.txt");
//		files.add("test/US03.txt");
		files.add("test/US04.txt");
//		files.add("test/US05.txt");
//		files.add("test/US06.txt");
//		files.add("test/US07.txt");
//		files.add("test/US08.txt");
		Facade yourTestFacade = new Facade();
		EasyAcceptFacade eaFacade = new EasyAcceptFacade(yourTestFacade, files);
		eaFacade.executeTests();
		System.out.println(eaFacade.getCompleteResults());

	}

}