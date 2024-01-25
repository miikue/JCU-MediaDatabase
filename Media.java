import java.util.LinkedList;
import java.util.List;


/**
 * abstract class representing any type of media
 * mother class for any media used in the program
 *
 * @Author Petr Jakubec
 */
abstract class Media{
    private final String name;
    private final Creator author;
    private Genre genre;
    private byte ratingGlobal;
    private byte ratingPrivate;
    private short releaseYear;
    private String info;
    private int length;
    private final List<String> tag = new LinkedList<>();
    private boolean seen;

    /**
     * constructor for media with all info
     * @param name name of media
     * @param author author of media
     * @param genre genre of media
     * @param ratingGlobal global rating of media
     * @param ratingPrivate private rating of media
     * @param releaseYear release year of media
     * @param length length of media
     * @param info info about media
     * @param tag tag of media
     */
    protected Media(String name, Creator author, Genre genre,
                 byte ratingGlobal, byte ratingPrivate, short releaseYear,
                 int length, String info , String tag) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.ratingGlobal = ratingGlobal;
        this.ratingPrivate = ratingPrivate;
        this.releaseYear = releaseYear;
        this.info = info;
        this.length = length;
        this.tag.add(tag);
        this.seen = true;
    }

    /**
     * constructor for media without any info
     * @param name name of media
     * @param author author of media
     */
    protected Media(String name, Creator author){
        this(name, author, Genre.UNDEFINED, (byte)-1, (byte)-1, (short)0, 0, "NO INFO ADDED","NO TAGS ADDED");
        this.seen = false;
    }

    /**
     * get name of media
     * @return name of media
     */
    public String getName() {
        return name;
    }

    /**
     * get author of media
     * @return author of media
     */
    public Creator getAuthor() {
        return author;
    }

    /**
     * get genre of media
     * @return genre of media
     */
    public Genre getGenre() {
        return genre;
    }

    /**
     * get global rating of media
     * @return global rating of media
     */
    public byte getRatingGlobal() {
        return ratingGlobal;
    }

    /**
     * get private rating of media
     * @return private rating of media
     */
    public byte getRatingPrivate() {
        return ratingPrivate;
    }

    /**
     * get release year of media
     * @return release year of media
     */
    public short getReleaseYear() {
        return releaseYear;
    }

    /**
     * get info about media
     * @return info about media
     */
    public String getInfo() {
        return info;
    }

    /**
     * get length of media
     * @return length of media
     */
    public int getLength() {
        return length;
    }

    /**
     * get list of tags
     * @return list of tags
     */
    public List<String> getTag() {
        return tag;
    }

    /**
     * set genre of media
     * @param genre genre of media
     */
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    /**
     * set global rating of media
     * @param ratingGlobal global rating of media
     */
    public void setRatingGlobal(byte ratingGlobal) {
        this.ratingGlobal = ratingGlobal;
    }

    /**
     * set private rating of media
     * @param ratingPrivate private rating of media
     */
    public void setRatingPrivate(byte ratingPrivate) {
        this.ratingPrivate = ratingPrivate;
        this.seen = true;
    }

    /**
     * set release year of media
     * @param releaseYear release year of media
     */
    public void setReleaseYear(short releaseYear) {
        this.releaseYear = releaseYear;
    }

    /**
     * set info about media
     * @param info info about media
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * set length of media
     * @param length length of media
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * set one tag
     * @param tag tag
     */
    public void setTag(String tag){
        this.tag.remove("NO TAGS ADDED");
        this.tag.add(tag);
    }

    /**
     * set multiple tags
     * @param tags list of tags
     */
    public void setTag(List<String> tags){
        this.tag.remove("NO TAGS ADDED");
        tag.addAll(tags);
    }

    /**
     *brief info about media:
     * Name,genre , author, release year and global rating
     *
     * @return brief info about a media
     */
    public String toStringBrief(){
        return name + " " + this.getClass().getSimpleName() +
                " Author: " + author.getName() +
                " Releas year: " + releaseYear;
    }

    /**
     * toString method with info about the media
     * @return string with complete info about media
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(name).append("\n");
        sb.append("Author: ").append(author.getFullName()).append("\n");
        sb.append("Genre: ").append(genre).append("\n");
        sb.append("Global Rating: ").append(ratingGlobal).append("\n");
        sb.append("Private Rating: ").append(ratingPrivate).append("\n");
        sb.append("Release Year: ").append(releaseYear).append("\n");
        sb.append("Length: ").append(length).append(" minutes\n");
        sb.append("Info: ").append(info).append("\n");
        sb.append("Tags: ").append(tag).append("\n");
        sb.append("Seen: ").append(seen).append("\n");

        return sb.toString();
    }
}

/**
 * enum of all (most) genres
 */
enum Genre{
    HORROR, SCIFI, FANTASY, ACTION, DRAMA, COMEDY, CRIME, WESTERN, DOCUMENTARY, HISTORY, ANIMATED,
    RPG, PLATFORMER, MMO, STRATEGY, SIMULATOR, FIGHTING, RACING, PUZZLE, INTERACTIVEMOVIE, INDIE, SURVIVAL,UNDEFINED
}