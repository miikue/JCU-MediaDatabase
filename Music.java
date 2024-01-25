/**
 * class representing Music media
 * @author Lukáš Tománek
 */
public class Music extends Media {
    /**
     * album of the music
     */
    private String album;

    /**
     * a constructor creating music media with all atributes
     * @param name name of the media
     * @param author author of the media
     * @param genre genre of the media
     * @param ratingGlobal global rating of the media
     * @param ratingPrivate private rating of the media
     * @param releaseYear release year of the media
     * @param length length of the media
     * @param info brief info about the media
     * @param tag tags of the media
     * @param album name of album of the media
     */
    public Music(String name, Creator author, Genre genre, byte ratingGlobal, byte ratingPrivate, short releaseYear, int length, String info, String tag, String album) {
        super(name, author, genre, ratingGlobal, ratingPrivate, releaseYear, length, info, tag);
        this.album=album;
    }

    /**
     * shortened constructor with only name and author
     * @param name name of the media
     * @param author author of the media
     */
    public Music(String name, Creator author) {
        super(name, author);
        this.album="Undefined";
    }

    /**
     * gets album of the media
     * @return name of the album
     */
    public String getAlbum(){
        return album;
    }

    /**
     * sets album of the media
     * @param album name of the album
     */
    public void setAlbum(String album){
        this.album=album;
    }

    /**
     * toString method from Media extended with name of the album
     * @return while info about the media
     */
    public String toString(){
        return super.toString()+"Album: " + album+ "\n";
    }
}
