package hu.unideb.webdev.dao.enums;

public enum Rating {
    G("G"),
    PG("PG"),
    PG13("PG-13"),
    R("R"),
    NC17("NC-17");

    private final String rate;
    private Rating(String rate) {
        this.rate = rate;
    }
    public String getRate() {
        return this.rate;
    }
}