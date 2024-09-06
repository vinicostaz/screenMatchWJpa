package br.com.screenmatch_with_jpa.model;

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
            this.plot = seriesData.plot();
        }

}