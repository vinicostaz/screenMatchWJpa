package br.com.screenmatch_with_jpa.main;

import br.com.screenmatch_with_jpa.model.SeriesData;
import br.com.screenmatch_with_jpa.model.SeasonData;
import br.com.screenmatch_with_jpa.service.ApiConsumption;
import br.com.screenmatch_with_jpa.service.DataConvert;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiConsumption consumption = new ApiConsumption();
    private final DataConvert convert = new DataConvert();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";

    public void showMenu() {
        var menu = """
                1 - Search series
                2 - Search episodes
               \s
                0 - Exit                                \s
               \s""";

        System.out.println(menu);
        var option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                searchSeriesWeb();
                break;
            case 2:
                searchEpisodeBySeries();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option");
        }
    }

    private void searchSeriesWeb() {
        SeriesData data = getSeriesData();
        System.out.println(data);
    }

    private SeriesData getSeriesData() {
        System.out.println("Enter the name of the series to search");
        var seriesName = scanner.nextLine();
        var json = consumption.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        return convert.getData(json, SeriesData.class);
    }

    private void searchEpisodeBySeries(){
        SeriesData seriesData = getSeriesData();
        List<SeasonData> seasons = new ArrayList<>();

        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            var json = consumption.getData(ADDRESS + seriesData.title().replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData seasonData = convert.getData(json, SeasonData.class);
            seasons.add(seasonData);
        }
        seasons.forEach(System.out::println);
    }
}