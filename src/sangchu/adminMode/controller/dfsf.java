package sangchu.adminMode.controller;

import java.util.Random;

public class dfsf {

	
	
	public static void main(String[] args) {
        for (int i = 0; i < 50; i++) {
            String fakeEmail = generateFakeEmail();
            System.out.println(fakeEmail);
        }
    }

    private static String generateFakeEmail() {
        String[] domains = {"gmail.com", "yahoo.com", "hotmail.com", "example.com", "fakeemail.com"};

        Random random = new Random();
        int nameLength = random.nextInt(10) + 5; // Random name length between 5 and 14 characters
        StringBuilder fakeName = new StringBuilder();

        for (int i = 0; i < nameLength; i++) {
            char randomChar = (char) (random.nextInt(26) + 'a');
            fakeName.append(randomChar);
        }

        String fakeDomain = domains[random.nextInt(domains.length)];
        return fakeName.toString() + "@" + fakeDomain;
    }
}
