class User {
    private int id;
    private String name;
    private String major;
    private String extension;

    public User(int id, String name, String major, String extension) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.extension = extension;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", major='" + major + '\'' +
                ", extension='" + extension + '\'' +
                '}';
    }
}