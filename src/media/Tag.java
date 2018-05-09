package media;

public class Tag<String, Double> {

    private final String name;
    private final Double confidence;

    Tag(String name, Double confidence) {
        this.name = name;
        this.confidence = confidence;
    }

    String getName() {
        return name;
    }

    Double getConfidence() {
        return confidence;
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ confidence.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Tag) {
            Tag pairo = (Tag) o;
            return this.name.equals(pairo.getName()) &&
                    this.confidence.equals(pairo.getConfidence());
        }

        return false;
    }

}