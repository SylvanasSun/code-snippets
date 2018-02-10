public enum ResourceType {

    JAR("jar"),
    FILE("file"),
    CLASS_FILE("class"),
    INVALID("invalid");

    private String typeName;

    public String getTypeName() {
        return this.typeName;
    }

    private ResourceType(String typeName) {
        this.typeName = typeName;
    }

}
