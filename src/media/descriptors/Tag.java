package media.descriptors;

public class Tag {

    private final String name;
    private final Double confidence;

    public Tag(String name, Double confidence) {
        this.name = name;
        this.confidence = confidence;
    }

    public String getName() {
        return name;
    }

    private Double getConfidence() {
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