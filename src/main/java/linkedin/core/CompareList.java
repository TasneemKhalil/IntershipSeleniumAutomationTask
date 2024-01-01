package linkedin.core;

import java.util.ArrayList;
import java.util.List;

public class CompareList {
    public void compare(List<String[]> linkedinResults, List<String> googleLinks) {
        // Extract LinkedIn links from the results
        List<String> linkedinLinks = new ArrayList<>();
        for (String[] result : linkedinResults) {
            linkedinLinks.add(result[1]); // Assuming the link is in the second position
        }

        // Find and print differences
        printDifferences(linkedinLinks, googleLinks);
    }

    private void printDifferences(List<String> list1, List<String> list2) {
        // Lists for unique links
        List<String> uniqueInList1 = new ArrayList<>(list1);
        uniqueInList1.removeAll(list2);

        List<String> uniqueInList2 = new ArrayList<>(list2);
        uniqueInList2.removeAll(list1);

        // Print differences
        if (!uniqueInList1.isEmpty() || !uniqueInList2.isEmpty()) {
            System.out.println("Differences found:");
            if (!uniqueInList1.isEmpty()) {
                System.out.println("Unique in List 1 (LinkedIn):");
                uniqueInList1.forEach(System.out::println);
            }
            if (!uniqueInList2.isEmpty()) {
                System.out.println("Unique in List 2 (Google):");
                uniqueInList2.forEach(System.out::println);
            }
        } else {
            System.out.println("No differences found. Lists match.");
        }
    }
}
