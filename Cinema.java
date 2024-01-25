import java.util.ArrayList;

/**
 * Class representing a movie media
 * @author Lukáš Tománek
 */
public class Cinema extends Media {
    /**
     * list of actors of the media
     */
    private ArrayList<Creator> actors = new ArrayList<>();

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
     * @param actors list of actors of the media
     */
    public Cinema(String name, Creator author, Genre genre, byte ratingGlobal, byte ratingPrivate, short releaseYear, int length, String info, String tag, ArrayList<Creator> actors) {
        super(name, author, genre, ratingGlobal, ratingPrivate, releaseYear, length, info, tag);
        this.actors = actors;
    }

    /**
     * a simpler constructor creating media with name, author and default values for rest of the atributes
     * @param name name of the media
     * @param author author of the media
     */
    public Cinema(String name, Creator author){
        super(name,author);
        this.actors.add(new Creator("Undefined",0,Creators.ACTOR));
    }

    /**
     * method setting list of actors to the media
     * @param newActors list of actors
     */
    public void setActors(ArrayList<Creator> newActors) {
        this.actors = newActors;
    }

    /**
     * method setting a single actor to the media actor list
     * @param newActor actor being added
     */
    public void setActor(Creator newActor) {
        this.actors.add(newActor);
    }

    /**
     * method returning list of actors
     * @return list of actors
     */
    public ArrayList<Creator> getActors(){
        return actors;
    }

    /**
     * metod returning all actors in a single string
     * @return string of all actors
     */
    private String getStringActors(){
        String s = "";
        for(Creator placeholder : actors){
            if(placeholder!= null)
            s += placeholder.getFullName() + "";
        }
        return s;
    }

    /**
     * toString method using toString from Media and adding list of actors to the end
     * @return whole info about the instance of the media
     */
    public String toString(){
        return super.toString()+"Actors: " + getStringActors() + "\n";
                }
} 

