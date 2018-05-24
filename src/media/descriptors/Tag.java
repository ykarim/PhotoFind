package media.descriptors;

public class Tag {

    private String name;
    private Double confidence;

    public Tag(String name) {
        this.name = name;
        this.confidence = Double.NaN;
    }

    public Tag(String name, Double confidence) {
        this.name = name;
        this.confidence = confidence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
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