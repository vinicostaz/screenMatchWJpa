package br.com.screenmatch_with_jpa.main;

import br.com.screenmatch_with_jpa.model.Episode;
import br.com.screenmatch_with_jpa.model.Series;
import br.com.screenmatch_with_jpa.model.SeriesData;
import br.com.screenmatch_with_jpa.model.SeasonData;
import br.com.screenmatch_with_jpa.repository.SeriesRepository;
import br.com.screenmatch_with_jpa.service.ApiConsumption;
import br.com.screenmatch_with_jpa.service.DataConvert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final ApiConsumption consumption = new ApiConsumption();
    private final DataConvert convert = new DataConvert();
    private final String ADDRESS = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=6585022c";
    private List<SeriesData> seriesData = new ArrayList<>();
    private SeriesRepository repository;
    private List<Series> series = new ArrayList<>();

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
        repository.save(serie);
        System.out.println(data);
    }

    private SeriesData getSeriesData() {
        System.out.println("Enter the name of the series to search");
        var seriesName = scanner.nextLine();
        var json = consumption.getData(ADDRESS + seriesName.replace(" ", "+") + API_KEY);
        return convert.getData(json, SeriesData.class);
    }

    private void searchEpisodeBySeries(){
        listSearchedSeries();
        System.out.println("Enter the name of the series to search for episodes");
        var seriesName = scanner.nextLine();

        Optional<Series> serie = series.stream()
                .filter(s -> s.getTitle().toLowerCase().contains(seriesName.toLowerCase()))
                .findFirst();

        if(serie.isPresent()) {
            var seriesFound = serie.get();
            List<SeasonData> seasons = new ArrayList<>();

            for (int i = 1; i <= seriesFound.getTotalSeasons(); i++) {
                var json = consumption.getData(ADDRESS + seriesFound.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData seasonData = convert.getData(json, SeasonData.class);
                seasons.add(seasonData);
            }
            seasons.forEach(System.out::println);

            List<Episode> episodes = seasons.stream()
                    .flatMap(d -> d.episodes().stream()
                            .map(e -> new Episode(d.number(), e)))
                    .collect(Collectors.toList());
            seriesFound.setEpisodes(episodes);
            repository.save(seriesFound);
        } else {
            System.out.println("Series not found!");
        }
    }

    private void listSearchedSeries(){
        series = repository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Series::getGenre))
                .forEach(System.out::println);
    }
}