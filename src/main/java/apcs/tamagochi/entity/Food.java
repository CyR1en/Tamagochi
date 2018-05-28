package apcs.tamagochi.entity;

public class Food {

    private int quality;
    private String name;

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public Food(int quality, String name) {
        this.quality = quality;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuality() {
        return quality;
    }

}
