/**
 * class representing a series media
 * @author Lukáš Tománek
 */
public class Series extends Cinema {
    private int seriesCount;
    private int episodeCount;

    /**
     * constructor creating series media with name, author series count and episodes xount
     * @param name name of the media
     * @param author author of the media
     * @param series number of series
     * @param episodes number of episodes
     */
    public Series(String name, Creator author, int series, int episodes) {
        super(name, author);
        this.seriesCount = series;
        this.episodeCount = episodes;
    }

    /**
     * constructor creating series media with name, author and default value for series and episodes counts
     * @param name name of the media
     * @param author author of the media
     */
    public Series(String name, Creator author) {
        super(name, author);
        this.seriesCount = 0;
        this.episodeCount = 0;
    }

    /**
     * Sets series count
     * @param series number of series
     */
    public void setSeriesCount(int series){
        this.seriesCount = series;
    }

    /**
     * Sets episode count
     * @param episodes number of episodes
     */
    public void setEpisodeCount(int episodes){
        this.episodeCount = episodes;
    }

    /**
     * Gets series count
     * @return number of series
     */
    public int getSeriesCount(){
        return seriesCount;
    }

    /**
     * Gets episode count
     * @return number of episodes
     */
    public int getEpisodeCount(){
        return episodeCount;
    }

    /**
     * toString method from Cinema extended with series count and episode count
     * @return whole string with info about whole media
     */
    public String toString(){
        return super.toString()+"Number of Series: " + seriesCount + "\n"+"Number of Episodes: " + episodeCount+"\n";
    }
}
