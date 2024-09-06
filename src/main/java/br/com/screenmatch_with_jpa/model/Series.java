package br.com.screenmatch_with_jpa.model;

import br.com.screenmatch_with_jpa.service.QueryMyMemory;

import java.util.OptionalDouble;

public class Series {
        private String title;
        private Integer totalSeasons;
        private Double rating;
        private Category genre;
        private String actors;
        private String poster;
        private String plot;

        public Series (SeriesData seriesData) {
            this.title = seriesData.title();
            this.totalSeasons = seriesData.totalSeasons();
            this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0.0);
            this.genre = Category.fromString(seriesData.genre().split(",")[0].trim());
            this.actors = seriesData.actors();
            this.poster = seriesData.poster();
            this.plot = QueryMyMemory.getTranslation(seriesData.plot()).trim();
            // this.plot = seriesData.plot();
        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "genre=" + genre +
                ", title = '" + title + '\'' +
                ", totalSeasons = " + totalSeasons +
                ", rating = " + rating +
                ", actors = '" + actors + '\'' +
                ", poster = '" + poster + '\'' +
                ", plot = '" + plot;
    }

}