package br.com.screenmatch_with_jpa.model;

public enum Category {
    ACTION("Action"),
    ROMANCE("Romance"),
    COMEDY("Comedy"),
    DRAMA("Drama"),
    CRIME("Crime"),
    TERROR("Terror");

    private String imdbCategory;

    private Category(String imdbCategory) {
        this.imdbCategory = imdbCategory;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.imdbCategory.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No category found for the provided string. " + text);
    }
}
