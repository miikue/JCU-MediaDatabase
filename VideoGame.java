/**
 * a class representing a Video Game media
 * @author Lukáš Tománek
 */
public class VideoGame extends Media{
    /**
     * platform of the game
     */
    private Platform platform;
    /**
     * a constructor creating movie media with all atributes
     * @param name name of the media
     * @param author author of the media
     * @param genre genre of the media
     * @param ratingGlobal global rating of the media
     * @param ratingPrivate private rating of the media
     * @param releaseYear release year of the media
     * @param length length of the media
     * @param info brief info about the media
     * @param tag tags of the media
     * @param platform a platform on which the game can be played on - enum value
     */
    public VideoGame(String name, Creator author, Genre genre, byte ratingGlobal, byte ratingPrivate, short releaseYear, int length, String info, String tag, Platform platform) {
        super(name, author, genre, ratingGlobal, ratingPrivate, releaseYear, length, info, tag);
        this.platform=platform;
    }

    /**
     * constructor creating media with only name and author, rest of atributes are default values
     * @param name name of the media
     * @param author author of the media
     */
    public VideoGame(String name, Creator author) {
        super(name, author);
        this.platform=Platform.Undefined;
    }

    /**
     * Method returning a platform of the game
     * @return a platform on which the game can be played on - enum value
     */
    public Platform getPlatform(){
        return platform;
    }

    /**
     * Method setting a platform of the game
     * @param platform a platform on which the game can be played on - enum value
     */
    public void setPlatform(Platform platform){
        this.platform=platform;
    }

    /**
     * toString Method from Media, extended by a platform of the game
     * @return
     */
    public String toString(){
        return super.toString()+"Platform: " + platform + "\n";
    }
}

/**
 * enum of platforms
 */
enum Platform{
    PC,PLayStation,Xbox,Switch,Mobile,Fridge,Undefined
}