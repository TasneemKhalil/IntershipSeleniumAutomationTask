package com.linkedIn.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import linkedin.core.MyCSVWriter;
import linkedin.core.CompareList;
import linkedin.pages.GoogleHomePage;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class SearchTest extends BaseTest {

    @DataProvider(name = "searchData")
    public Object[][] readData() throws IOException, CsvValidationException {
        CSVReader reader = new CSVReader(new FileReader("./src/main/resources/searchData.csv"));
        List<Object[]> data = new ArrayList<>();

        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            if (nextLine.length > 0 && nextLine[0].contains(" ")) {
                int lastSpaceIndex = nextLine[0].lastIndexOf(" ");
                String keyword = nextLine[0].substring(0, lastSpaceIndex);
                String filter = nextLine[0].substring(lastSpaceIndex + 1);
                data.add(new Object[]{keyword, filter});
            }
        }

        reader.close();
        return data.toArray(new Object[0][]);
    }

    @Test(dataProvider = "searchData")
    public void testSearch(String keyword, String filter) throws IOException {
        linkedInHomePage.searchFor(keyword);
        if ("People".equals(filter)) {
            linkedInHomePage.choosePeople();
        }
        List<String[]> linkedinResults = linkedInHomePage.extractResults();

        GoogleHomePage googleHomePage = new GoogleHomePage(driver);
        googleHomePage.openPage();
        googleHomePage.searchFor(keyword + " " + filter + " " + "linkedin.com");
        List<String> googleLinks = googleHomePage.extractLinks();

        // Write LinkedIn results to file
        String linkedInFilePath = "./src/main/resources/linkedinOutput.csv";
        MyCSVWriter linkedInWriter = null;
        try {
            linkedInWriter = new MyCSVWriter(linkedInFilePath);
            linkedInWriter.writeAll(linkedinResults.stream().limit(8).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (linkedInWriter != null) {
                try {
                    linkedInWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Write Google results to file
        String googleFilePath = "./src/main/resources/googleOutput.csv";
        MyCSVWriter googleWriter = null;
        try {
            googleWriter = new MyCSVWriter(googleFilePath);
            googleWriter.writeAll(googleLinks.stream().limit(8).map(link -> new String[]{link}).toList());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (googleWriter != null) {
                try {
                    googleWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Compare and assert
        CompareList compareList = new CompareList();
        compareList.compare(linkedinResults, googleLinks);
        // Prepare lists for the assertion
        List<String> linkedinLinks = linkedinResults.stream().map(result -> result[1]).collect(Collectors.toList());
        Assert.assertNotEquals(linkedinLinks, googleLinks, "LinkedIn and Google links unexpectedly match");
    }
}
