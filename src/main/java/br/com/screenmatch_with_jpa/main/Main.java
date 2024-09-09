package br.com.screenmatch_with_jpa.main;

import br.com.screenmatch_with_jpa.model.Series;
import br.com.screenmatch_with_jpa.model.SeriesData;
import br.com.screenmatch_with_jpa.model.SeasonData;
import br.com.screenmatch_with_jpa.repository.SeriesRepository;
import br.com.screenmatch_with_jpa.service.ApiConsumption;
import br.com.screenmatch_with_jpa.service.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiConsumption consumption = new ApiConsumption();
    private final DataConvert convert = new DataConvert();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<SeriesData> seriesData = new ArrayList<>();
    private SeriesRepository repository;

    public Main(SeriesRepository repository) {
        this.repository = repository;
    }

    public void showMenu() {
        var option = -1;
        while (option != 0) {
        var menu = """
               1 - Search series
               2 - Search episodes
               3 - List searched series
              
               0 - Exit
               """;

        System.out.println(menu);
        option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                searchSeriesWeb();
                break;
            case 2:
                searchEpisodeBySeries();
                break;
            case 3:
                listSearchedSeries();
                break;
            case 0:
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option");
        }
            }
    }

    private void searchSeriesWeb() {
        SeriesData data = getSeriesData();
        Series serie = new Series(data);
        // seriesData.add(data);
        repository.save(serie);1
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

    private void listSearchedSeries(){
        List<Series> series = new ArrayList<>();
        series = seriesData.stream()
                .map(d -> new Series(d))
                .collect(Collectors.toList());
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }
}