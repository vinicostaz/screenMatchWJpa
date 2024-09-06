package br.com.screenmatch_with_jpa.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episodeNumber;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(Integer seasonNumber, EpisodesData episodesData) {
        this.season = seasonNumber;
        this.title = episodesData.title();
        this.episodeNumber = episodesData.number();

        try {
            this.rating = Double.valueOf(episodesData.rating());
        } catch (NumberFormatException ex) {
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodesData.releaseData());
        } catch (DateTimeParseException ex) {
            this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "season = " + season +
                ", title = '" + title + '\'' +
                ", episodeNumber = " + episodeNumber +
                ", rating = " + rating +
                ", releaseData = " + releaseDate ;
    }
}