package org.springframework.web.context.support;

public class Test {

	public static void main(String[] args) {
		try {
			String[] configLocations = {"NONE"};
			System.out.println(configLocations.toString());
			if (configLocations != null&& !configLocations[0].equalsIgnoreCase("NONE")) {
				System.out.println("TEST");
			}
			else {
				System.out.println("TEST1");
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}

	}

}
