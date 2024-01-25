import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Database {
    // All lists
    private final String path;
    private final List<Media> mediaList = new LinkedList<>();
    private final List<Media> myMediaList = new LinkedList<>();
    private final List<VideoGame> gamesList = new LinkedList<>();
    private final List<Cinema> moviesList = new LinkedList<>();
    private final List<Series> seriesList = new LinkedList<>();
    private final List<Music> musicList = new LinkedList<>();
    private final List<Creator> creatorsList = new LinkedList<>();

    /**
     * Constructor for database
     *
     * @param path path to database file (XML)
     */
    public Database(String path) {
        this.path = path;
        try {
            LoadDatabase();
        } catch (Exception e) {
            Error("Error loading database! (" + e.getMessage() + ")");
        }
    }

    // Work with database

    /**
     * Method for loading database from XML file
     */
    public void LoadDatabase(){
        try {

            // Inicialization of XML parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load XML file
            Document document = builder.parse(new File(path));
            Element dataElement = document.getDocumentElement();
            // Get list of all creators

            // Authors
            NodeList authors = dataElement.getElementsByTagName("Creator");
            for (int i = 0; i < authors.getLength(); i++) {
                Element authorElement = (Element) authors.item(i);
                String name = authorElement.getElementsByTagName("Name").item(0).getTextContent();
                int birth = Integer.parseInt(authorElement.getElementsByTagName("Birth").item(0).getTextContent());
                Creators authorType = Creators.valueOf(authorElement.getElementsByTagName("Type").item(0).getTextContent());

                creatorsList.add(new Creator(name, birth, authorType));
            }

            // Movies
            NodeList movies = dataElement.getElementsByTagName("Movie");
            for (int i = 0; i < movies.getLength(); i++) {
                Element movieElement = (Element) movies.item(i);
                String name = movieElement.getElementsByTagName("Title").item(0).getTextContent();
                String authorName = movieElement.getElementsByTagName("Author").item(0).getTextContent();
                Creator author = creatorsList.get(findCreator(creatorsList, authorName));
                Genre genre = Genre.valueOf(movieElement.getElementsByTagName("Genre").item(0).getTextContent());
                byte ratingGlobal = Byte.parseByte(movieElement.getElementsByTagName("RatingGlobal").item(0).getTextContent());
                byte ratingPrivate = Byte.parseByte(movieElement.getElementsByTagName("RatingPersonal").item(0).getTextContent());
                short releaseYear = Short.parseShort(movieElement.getElementsByTagName("Year").item(0).getTextContent());
                int length = Integer.parseInt(movieElement.getElementsByTagName("Length").item(0).getTextContent());
                String info = movieElement.getElementsByTagName("Info").item(0).getTextContent();
                // Set tags
                String tag = movieElement.getElementsByTagName("Tags").item(0).getTextContent();
                List<String> tags = new LinkedList<>();
                String[] tagss = String.valueOf(tag).split(",");
                for (String s : tagss) {
                    tags.add(s);
                }

                // Set actors
                String actorsList = movieElement.getElementsByTagName("Actors").item(0).getTextContent();
                String[] actors = String.valueOf(actorsList).split(",");
                ArrayList<Creator> creators = new ArrayList<>();
                if (actors[0].equals("Undefined")) {
                    creators.add(0, new Creator("Undefined", 0, Creators.DEVELOPER));
                }else{

                    for (int j = 0; j < actors.length; j++) {
                        try {
                            creators.add(j,creatorsList.get(findCreator(creatorsList, actors[j])));
                        } catch (Exception e) {
                            Error("Error loading database! (Actor not found and not added)");
                        }
                    }
                }

                Cinema cinema = new Cinema(name, author, genre, ratingGlobal, ratingPrivate, releaseYear, length, info, tags.get(0), creators);
                tags.remove(0);
                cinema.setTag(tags);
                moviesList.add(cinema);
            }

            // Music
            NodeList songs = dataElement.getElementsByTagName("Song");
            for (int i = 0; i < songs.getLength(); i++) {
                Element songElement = (Element) songs.item(i);
                String name = songElement.getElementsByTagName("Title").item(0).getTextContent();
                String authorName = songElement.getElementsByTagName("Author").item(0).getTextContent();
                Creator author = creatorsList.get(findCreator(creatorsList, authorName));
                Genre genre = Genre.valueOf(songElement.getElementsByTagName("Genre").item(0).getTextContent());
                byte ratingGlobal = Byte.parseByte(songElement.getElementsByTagName("RatingGlobal").item(0).getTextContent());
                byte ratingPrivate = Byte.parseByte(songElement.getElementsByTagName("RatingPersonal").item(0).getTextContent());
                short releaseYear = Short.parseShort(songElement.getElementsByTagName("Year").item(0).getTextContent());
                int length = Integer.parseInt(songElement.getElementsByTagName("Length").item(0).getTextContent());
                String info = songElement.getElementsByTagName("Info").item(0).getTextContent();
                // Set tags
                String tag = songElement.getElementsByTagName("Tags").item(0).getTextContent();
                List<String> tags = new LinkedList<>();
                String[] tagss = String.valueOf(tag).split(",");
                for (String s : tagss) {
                    tags.add(s);
                }
                String album = songElement.getElementsByTagName("Album").item(0).getTextContent();
                Music music = new Music(name, author, genre, ratingGlobal, ratingPrivate, releaseYear, length, info, tags.get(0), album);
                tags.remove(0);
                music.setTag(tags);
                musicList.add(music);
            }


            // Video games
            NodeList games = dataElement.getElementsByTagName("Game");
            for (int i = 0; i < games.getLength(); i++) {
                Element gameElement = (Element) games.item(i);
                String name = gameElement.getElementsByTagName("Title").item(0).getTextContent();
                String authorName = gameElement.getElementsByTagName("Author").item(0).getTextContent();
                Creator author = creatorsList.get(findCreator(creatorsList, authorName));
                Genre genre = Genre.valueOf(gameElement.getElementsByTagName("Genre").item(0).getTextContent());
                byte ratingGlobal = Byte.parseByte(gameElement.getElementsByTagName("RatingGlobal").item(0).getTextContent());
                byte ratingPrivate = Byte.parseByte(gameElement.getElementsByTagName("RatingPersonal").item(0).getTextContent());
                short releaseYear = Short.parseShort(gameElement.getElementsByTagName("Year").item(0).getTextContent());
                int length = Integer.parseInt(gameElement.getElementsByTagName("Length").item(0).getTextContent());
                String info = gameElement.getElementsByTagName("Info").item(0).getTextContent();
                // Set tags
                String tag = gameElement.getElementsByTagName("Tags").item(0).getTextContent();
                List<String> tags = new LinkedList<>();
                String[] tagss = String.valueOf(tag).split(",");
                for (String s : tagss) {
                    tags.add(s);
                }
                Platform platform = Platform.valueOf(gameElement.getElementsByTagName("Platform").item(0).getTextContent());
                VideoGame videoGame = new VideoGame(name, author, genre, ratingGlobal, ratingPrivate, releaseYear, length, info, tags.get(0), platform);
                tags.remove(0);
                videoGame.setTag(tags);
                gamesList.add(videoGame);
            }

            // Series
            NodeList series = dataElement.getElementsByTagName("Show");
            for (int i = 0; i < series.getLength(); i++) {
                Element seriesElement = (Element) series.item(i);
                String name = seriesElement.getElementsByTagName("Title").item(0).getTextContent();
                String authorName = seriesElement.getElementsByTagName("Author").item(0).getTextContent();
                Creator author = creatorsList.get(findCreator(creatorsList, authorName));
                Genre genre = Genre.valueOf(seriesElement.getElementsByTagName("Genre").item(0).getTextContent());
                byte ratingGlobal = Byte.parseByte(seriesElement.getElementsByTagName("RatingGlobal").item(0).getTextContent());
                byte ratingPrivate = Byte.parseByte(seriesElement.getElementsByTagName("RatingPersonal").item(0).getTextContent());
                short releaseYear = Short.parseShort(seriesElement.getElementsByTagName("Year").item(0).getTextContent());
                int length = Integer.parseInt(seriesElement.getElementsByTagName("Length").item(0).getTextContent());
                int seasons = Integer.parseInt(seriesElement.getElementsByTagName("SeriesCount").item(0).getTextContent());
                int episodes = Integer.parseInt(seriesElement.getElementsByTagName("EpisodeCount").item(0).getTextContent());
                String info = seriesElement.getElementsByTagName("Info").item(0).getTextContent();
                // Set tags
                String tag = seriesElement.getElementsByTagName("Tags").item(0).getTextContent();
                List<String> tags = new LinkedList<String>();
                String[] tagss = String.valueOf(tag).split(",");
                for (String s : tagss) {
                    tags.add(s);
                }
                // Set actors

                String actorsList = seriesElement.getElementsByTagName("Actors").item(0).getTextContent();
                String[] actors = String.valueOf(actorsList).split(",");

                Creator[] creators = new Creator[actors.length];
                if (actors[0].equals("Undefined")) {
                    creators[0] = new Creator("Undefined", 0, Creators.DEVELOPER);
                }else {
                    for (int j = 0; j < actors.length; j++) {
                            try {
                                creators[j] = creatorsList.get(findCreator(creatorsList, actors[j]));
                            } catch (Exception e) {
                                Error("Error loading database! (Actor not found)");
                            }
                    }
                }

                // Create series
                Series series1 = new Series(name, author, seasons, episodes);
                series1.setGenre(genre);
                series1.setRatingGlobal(ratingGlobal);
                series1.setRatingPrivate(ratingPrivate);
                series1.setReleaseYear(releaseYear);
                series1.setLength(length);
                series1.setInfo(info);
                series1.setTag(tags);
                series1.setActors(new ArrayList<>(List.of(creators)));
                seriesList.add(series1);
            }


            // Make list of all media
            {
                mediaList.addAll(gamesList);
                mediaList.addAll(moviesList);
                mediaList.addAll(seriesList);
                mediaList.addAll(musicList);
                //mediaList.sort((Media m1, Media m2) -> m1.getName().compareTo(m2.getName()));
            }
            // Make my media list
            {
                for (Media media : mediaList) {
                    if (media.getRatingPrivate() > -1) {
                        myMediaList.add(media);
                    }
                }

                //System.out.println(seriesList.size());
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            Error("Error loading database! (" + e.getMessage() + ")");
        }


    }

    /**
     * Method for saving database to XML file
     */
    private void SaveDatabase() {
            try {
                // Inicialization of XML parser
                DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                Document doc = docBuilder.newDocument();

                // Root element "data"
                Element dataElement = doc.createElement("data");
                doc.appendChild(dataElement);

                // Element "Creators"
                Element creatorsElement = doc.createElement("Creators");
                dataElement.appendChild(creatorsElement);

                // Authors
                for (Creator creator : creatorsList) {
                    Element creatorElement = doc.createElement("Creator");
                    creatorsElement.appendChild(creatorElement);

                    // Element "Name"
                    Element nameElement = doc.createElement("Name");
                    nameElement.appendChild(doc.createTextNode(creator.getFullName()));
                    creatorElement.appendChild(nameElement);

                    // Element "Birth"
                    Element birthElement = doc.createElement("Birth");
                    birthElement.appendChild(doc.createTextNode(String.valueOf(creator.getBirthYear())));
                    creatorElement.appendChild(birthElement);

                    // Element "Type"
                    Element typeElement = doc.createElement("Type");
                    typeElement.appendChild(doc.createTextNode(creator.getType().toString()));
                    creatorElement.appendChild(typeElement);
                }
                Element mediaElements = doc.createElement("Media");
                dataElement.appendChild(mediaElements);

                // movies
                Element moviesElement = doc.createElement("Movies");
                mediaElements.appendChild(moviesElement);
                for (Cinema cinema : moviesList){
                    Element movieElement = doc.createElement("Movie");
                    moviesElement.appendChild(movieElement);

                    //movieElement
                    Element titleElement = doc.createElement("Title");
                    titleElement.appendChild(doc.createTextNode(cinema.getName()));
                    movieElement.appendChild(titleElement);

                    Element authorElement = doc.createElement("Author");
                    authorElement.appendChild(doc.createTextNode(cinema.getAuthor().getFullName()));
                    movieElement.appendChild(authorElement);

                    Element genreElement = doc.createElement("Genre");
                    genreElement.appendChild(doc.createTextNode(cinema.getGenre().toString()));
                    movieElement.appendChild(genreElement);

                    Element ratingGlobalElement = doc.createElement("RatingGlobal");
                    ratingGlobalElement.appendChild(doc.createTextNode(String.valueOf(cinema.getRatingGlobal())));
                    movieElement.appendChild(ratingGlobalElement);

                    Element ratingPrivateElement = doc.createElement("RatingPersonal");
                    ratingPrivateElement.appendChild(doc.createTextNode(String.valueOf(cinema.getRatingPrivate())));
                    movieElement.appendChild(ratingPrivateElement);

                    Element yearElement = doc.createElement("Year");
                    yearElement.appendChild(doc.createTextNode(String.valueOf(cinema.getReleaseYear())));
                    movieElement.appendChild(yearElement);

                    Element lengthElement = doc.createElement("Length");
                    lengthElement.appendChild(doc.createTextNode(String.valueOf(cinema.getLength())));
                    movieElement.appendChild(lengthElement);

                    Element infoElement = doc.createElement("Info");
                    infoElement.appendChild(doc.createTextNode(cinema.getInfo()));
                    movieElement.appendChild(infoElement);

                    Element tagsElement = doc.createElement("Tags");
                    String tags = "";
                    for(String tag : cinema.getTag()){
                        tags += tag + ",";
                    }
                    tagsElement.appendChild(doc.createTextNode(tags));
                    movieElement.appendChild(tagsElement);

                    Element actorsElement = doc.createElement("Actors");
                    String actors = "";
                    for(Creator actor : cinema.getActors()){
                        actors += actor.getFullName() + ",";
                    }
                    actorsElement.appendChild(doc.createTextNode(actors));
                    movieElement.appendChild(actorsElement);
                }

                // music
                Element musicElement = doc.createElement("Music");
                mediaElements.appendChild(musicElement);
                for (Music music : musicList){
                    Element songsElement = doc.createElement("Song");
                    musicElement.appendChild(songsElement);

                    Element titleElement = doc.createElement("Title");
                    titleElement.appendChild(doc.createTextNode(music.getName()));
                    songsElement.appendChild(titleElement);

                    Element authorElement = doc.createElement("Author");
                    authorElement.appendChild(doc.createTextNode(music.getAuthor().getFullName()));
                    songsElement.appendChild(authorElement);

                    Element genreElement = doc.createElement("Genre");
                    genreElement.appendChild(doc.createTextNode(music.getGenre().toString()));
                    songsElement.appendChild(genreElement);

                    Element ratingGlobalElement = doc.createElement("RatingGlobal");
                    ratingGlobalElement.appendChild(doc.createTextNode(String.valueOf(music.getRatingGlobal())));
                    songsElement.appendChild(ratingGlobalElement);

                    Element ratingPrivateElement = doc.createElement("RatingPersonal");
                    ratingPrivateElement.appendChild(doc.createTextNode(String.valueOf(music.getRatingPrivate())));
                    songsElement.appendChild(ratingPrivateElement);

                    Element yearElement = doc.createElement("Year");
                    yearElement.appendChild(doc.createTextNode(String.valueOf(music.getReleaseYear())));
                    songsElement.appendChild(yearElement);

                    Element lengthElement = doc.createElement("Length");
                    lengthElement.appendChild(doc.createTextNode(String.valueOf(music.getLength())));
                    songsElement.appendChild(lengthElement);

                    Element infoElement = doc.createElement("Info");
                    infoElement.appendChild(doc.createTextNode(music.getInfo()));
                    songsElement.appendChild(infoElement);

                    Element tagsElement = doc.createElement("Tags");
                    String tags = "";
                    for(String tag : music.getTag()){
                        tags += tag + ",";
                    }
                    tagsElement.appendChild(doc.createTextNode(tags));
                    songsElement.appendChild(tagsElement);

                    Element albumElement = doc.createElement("Album");
                    albumElement.appendChild(doc.createTextNode(music.getAlbum()));
                    songsElement.appendChild(albumElement);
                }

                // games
                Element gamesElement = doc.createElement("Games");
                mediaElements.appendChild(gamesElement);
                for (VideoGame game : gamesList){
                    Element gameElement = doc.createElement("Game");
                    gamesElement.appendChild(gameElement);

                    Element titleElement = doc.createElement("Title");
                    titleElement.appendChild(doc.createTextNode(game.getName()));
                    gameElement.appendChild(titleElement);

                    Element authorElement = doc.createElement("Author");
                    authorElement.appendChild(doc.createTextNode(game.getAuthor().getFullName()));
                    gameElement.appendChild(authorElement);

                    Element genreElement = doc.createElement("Genre");
                    genreElement.appendChild(doc.createTextNode(game.getGenre().toString()));
                    gameElement.appendChild(genreElement);

                    Element ratingGlobalElement = doc.createElement("RatingGlobal");
                    ratingGlobalElement.appendChild(doc.createTextNode(String.valueOf(game.getRatingGlobal())));
                    gameElement.appendChild(ratingGlobalElement);

                    Element ratingPrivateElement = doc.createElement("RatingPersonal");
                    ratingPrivateElement.appendChild(doc.createTextNode(String.valueOf(game.getRatingPrivate())));
                    gameElement.appendChild(ratingPrivateElement);

                    Element yearElement = doc.createElement("Year");
                    yearElement.appendChild(doc.createTextNode(String.valueOf(game.getReleaseYear())));
                    gameElement.appendChild(yearElement);

                    Element lengthElement = doc.createElement("Length");
                    lengthElement.appendChild(doc.createTextNode(String.valueOf(game.getLength())));
                    gameElement.appendChild(lengthElement);

                    Element infoElement = doc.createElement("Info");
                    infoElement.appendChild(doc.createTextNode(game.getInfo()));
                    gameElement.appendChild(infoElement);

                    Element tagsElement = doc.createElement("Tags");
                    String tags = "";
                    for(String tag : game.getTag()){
                        tags += tag + ",";
                    }
                    tagsElement.appendChild(doc.createTextNode(tags));
                    gameElement.appendChild(tagsElement);

                    Element platformElement = doc.createElement("Platform");
                    platformElement.appendChild(doc.createTextNode(game.getPlatform().toString()));
                    gameElement.appendChild(platformElement);
                }

                // series
                Element seriesElement = doc.createElement("Series");
                mediaElements.appendChild(seriesElement);
                for (Series show : seriesList){
                    Element showElement = doc.createElement("Show");
                    seriesElement.appendChild(showElement);

                    Element titleElement = doc.createElement("Title");
                    titleElement.appendChild(doc.createTextNode(show.getName()));
                    showElement.appendChild(titleElement);

                    Element authorElement = doc.createElement("Author");
                    authorElement.appendChild(doc.createTextNode(show.getAuthor().getFullName()));
                    showElement.appendChild(authorElement);

                    Element genreElement = doc.createElement("Genre");
                    genreElement.appendChild(doc.createTextNode(show.getGenre().toString()));
                    showElement.appendChild(genreElement);

                    Element ratingGlobalElement = doc.createElement("RatingGlobal");
                    ratingGlobalElement.appendChild(doc.createTextNode(String.valueOf(show.getRatingGlobal())));
                    showElement.appendChild(ratingGlobalElement);

                    Element ratingPrivateElement = doc.createElement("RatingPersonal");
                    ratingPrivateElement.appendChild(doc.createTextNode(String.valueOf(show.getRatingPrivate())));
                    showElement.appendChild(ratingPrivateElement);

                    Element yearElement = doc.createElement("Year");
                    yearElement.appendChild(doc.createTextNode(String.valueOf(show.getReleaseYear())));
                    showElement.appendChild(yearElement);

                    Element lengthElement = doc.createElement("Length");
                    lengthElement.appendChild(doc.createTextNode(String.valueOf(show.getLength())));
                    showElement.appendChild(lengthElement);

                    Element infoElement = doc.createElement("Info");
                    infoElement.appendChild(doc.createTextNode(show.getInfo()));
                    showElement.appendChild(infoElement);

                    Element tagsElement = doc.createElement("Tags");
                    String tags = "";
                    for(String tag : show.getTag()){
                        tags += tag + ",";
                    }
                    tagsElement.appendChild(doc.createTextNode(tags));
                    showElement.appendChild(tagsElement);

                    Element actorsElement = doc.createElement("Actors");
                    String actors = "";
                    for(Creator actor : show.getActors()){
                        actors += actor.getFullName() + ",";
                    }

                    Element seasonsElement = doc.createElement("SeriesCount");
                    seasonsElement.appendChild(doc.createTextNode(String.valueOf(show.getSeriesCount())));
                    showElement.appendChild(seasonsElement);

                    Element episodesElement = doc.createElement("EpisodeCount");
                    episodesElement.appendChild(doc.createTextNode(String.valueOf(show.getEpisodeCount())));
                    showElement.appendChild(episodesElement);

                    actorsElement.appendChild(doc.createTextNode(actors));
                    showElement.appendChild(actorsElement);
                }


                // Create XML file and save it
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(path));
                transformer.transform(source, result);

                System.out.println("Everything saved!");

            } catch (ParserConfigurationException | TransformerException e) {
                Error("Error saving database! (" + e.getMessage() + ")");
            }
        }

    /**
     * Method for closing database
     */
    public void CloseDatabase() {
        try {
            SaveDatabase();
        } catch (Exception e) {
            Error("Error saving database! (" + e.getMessage() + ")");
        }
    }

 // Add and remove media
    /**
     * Method for adding media to database
     *
     * @param media media to be added
     */
    public void AddMedia(Media media) {
        if (media == null) {
            Error("Error adding media to database! (Media is null)");
            return;
        }
        creatorsList.add(media.getAuthor());
        if (media instanceof VideoGame) {
            gamesList.add((VideoGame) media);
            gamesList.sort((Media m1, Media m2) -> m1.getName().compareTo(m2.getName()));
            mediaList.add(media);
        } else if (media instanceof Cinema) {
            moviesList.add((Cinema) media);
            moviesList.sort((Media m1, Media m2) -> m1.getName().compareTo(m2.getName()));
            mediaList.add(media);
        } else if (media instanceof Series) {
            seriesList.add((Series) media);
            seriesList.sort((Media m1, Media m2) -> m1.getName().compareTo(m2.getName()));
            mediaList.add(media);
        } else if (media instanceof Music) {
            musicList.add((Music) media);
            musicList.sort((Media m1, Media m2) -> m1.getName().compareTo(m2.getName()));
            mediaList.add(media);
        } else {
            Error("Error adding media to database!");
        }
        if (media.getRatingPrivate() > -1) {
            myMediaList.add(media);
            myMediaList.sort((Media m1, Media m2) -> m1.getName().compareTo(m2.getName()));
        }

    }

    /**
     * Method for removing media from database
     *
     * @param media media to be removed
     */
    public void RemoveMedia(Media media) {
        if (media == null) {
            Error("Error removing media from database! (Media is null)");
            return;
        }
        // Remove from all media list
        for (int i = 0; i < mediaList.size(); i++) {
            if (mediaList.get(i).getName() == media.getName()) {
                mediaList.remove(i);
            } else {
                Error("Error removing media from database! (Media not found)");
            }
        }
        // Remove from specific media list
        if (media instanceof VideoGame) {
            for (int i = 0; i < gamesList.size(); i++) {
                if (gamesList.get(i).getName() == media.getName()) {
                    gamesList.remove(i);
                } else {
                    Error("Error removing media from database! (Game not found)");
                }
            }
        } else if (media instanceof Cinema) {
            for (int i = 0; i < moviesList.size(); i++) {
                if (moviesList.get(i).getName() == media.getName()) {
                    moviesList.remove(i);
                } else {
                    Error("Error removing media from database! (Movie not found)");
                }
            }

        } else if (media instanceof Series) {
            for (int i = 0; i < seriesList.size(); i++) {
                if (seriesList.get(i).getName() == media.getName()) {
                    seriesList.remove(i);
                } else {
                    Error("Error removing media from database! (Series not found)");
                }
            }
        } else if (media instanceof Music) {
            for (int i = 0; i < musicList.size(); i++) {
                if (musicList.get(i).getName() == media.getName()) {
                    musicList.remove(i);
                } else {
                    Error("Error removing media from database! (Music not found)");
                }
            }
        } else {
            Error("Error removing media from database!");
        }
        // Remove from my media list
        if (media.getRatingPrivate() > -1) {
            for (int i = 0; i < myMediaList.size(); i++) {
                if (myMediaList.get(i).getName() == media.getName()) {
                    myMediaList.remove(i);
                } else {
                    Error("Error removing media from database! (Media not found)");
                }
            }
        }
    }

    /**
     * Method for adding creator to database
     * @param creator
     */
    public void AddCreator(Creator creator) {
        if (creator == null) {
            Error("Error adding creator to database! (Creator is null)");
            return;
        }
        creatorsList.add(creator);
    }

    /**
     * Method for removing creator from database by name
     * @param creator
     */
    public void RemoveCreator(Creator creator) {
        if (creator == null) {
            System.err.println("Error removing creator from database!");
            return;
        }
        for (int i = 0; i < creatorsList.size(); i++) {
            if (creatorsList.get(i).getName() == creator.getName()) {
                creatorsList.remove(i);
            } else {
                Error("Error removing creator from database! (Author not found)");
            }
        }
    }
    public static int find(List<Media> list, String name){
        int i =0;
        if(list == null) return -1;
        for(Media M : list){

            if(M.getName().equals(name)){
                return i;
            }
            i++;
        }
        return -1;
    }
    public static int findCreator(List<Creator> list, String name){
        int i =0;
        if(list == null) return -1;
        for(Creator M : list){

            if(M.getFullName().equals(name)){
                return i;
            }
            i++;
        }
        return -1;
    }

    /**
     * Simple method for printing error message (better than writing System.err.println() every time)
     * @param message message to be printed
     */
    private void Error(String message) {
        System.err.println(message);
    }

    /**
     * Method for browsing database
     */
    public void browse(){
        Scanner sc = new Scanner(System.in);
        String help;
        int option;

        String[] genres ={"All media", "Movies", "Series", "Music", "Video Games"};
        System.out.println("What would you like to browse?");
        for(int i = 1; i <=genres.length;i++){
            System.out.println(i + ") " + genres[i-1]);
        }
        do {
            help = sc.nextLine();
            if (!Main.testInput(help) || help.isEmpty()) {
                System.out.println("Wrong input!");
                continue;
            }
            option = Integer.parseInt(help);
            if(option >0 && option <genres.length)
                break;
        }while(true);
        int counter = 1;
        switch (option){
            case 1:
                int index;
                if(mediaList.isEmpty()){
                    System.out.println("Nothing here");
                    break;
                }

                for(Media media : mediaList){
                    System.out.println( counter + ") " + media.toStringBrief());
                    counter++;
                }
                do {
                    help = sc.nextLine();
                    if (!Main.testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    index = Integer.parseInt(help)-1;
                    if(index >=0 && index < mediaList.size())
                        showMedia(mediaList.get(index));
                    return;
                }while(true);

            case 2:
                if(moviesList.isEmpty()){
                    System.out.println("Nothing here");
                    break;
                }
                for(Cinema media : moviesList){
                    System.out.println( counter + ") " + media.toStringBrief());
                    counter++;
                }
                do {
                    help = sc.nextLine();
                    if (!Main.testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    index = Integer.parseInt(help)-1;
                    if(index >=0 && index < moviesList.size())
                        showMedia(moviesList.get(index));
                    return;

                }while(true);
            case 3:
                if(seriesList.isEmpty()){
                    System.out.println("Nothing here");
                    break;
                }
                for(Series media : seriesList){
                    System.out.println( counter + ") " + media.toStringBrief());
                    counter++;
                }
                do {
                    help = sc.nextLine();
                    if (!Main.testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    index = Integer.parseInt(help)-1;
                    if(index >=0 && index < seriesList.size())
                        showMedia(seriesList.get(index));
                    return;

                }while(true);
            case 4:
                if(musicList.isEmpty()){
                    System.out.println("Nothing here");
                    break;
                }
                for(Music media : musicList){
                    System.out.println( counter + ") " + media.toStringBrief());
                    counter++;
                }
                do {
                    help = sc.nextLine();
                    if (!Main.testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    index = Integer.parseInt(help)-1;
                    if(index >=0 && index < musicList.size())
                        showMedia(musicList.get(index));
                    return;

                }while(true);
            case 5:
                if(gamesList.isEmpty()){
                    System.out.println("Nothing here");
                    break;
                }
                for(VideoGame media : gamesList){
                    System.out.println( counter + ") " + media.toStringBrief());
                    counter++;
                }
                do {
                    help = sc.nextLine();
                    if (!Main.testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    index = Integer.parseInt(help)-1;
                    if(index >=0 && index < gamesList.size())
                        showMedia(gamesList.get(index));
                    return;

                }while(true);
        }

    }

    /**
     * Method for showing media
     * @param media media to be shown
     */
    private void showMedia(Media media) {
        System.out.println();
        System.out.println(media.toString());
        System.out.println();
        System.out.println("Would you like to edit the media?");
        System.out.println("Y/N");
        if (Main.proceed()) {
            Main.addingAtributes(media);
        }
    }

    // Getters for all lists of media
    public List<Media> getMediaList() {
        return mediaList;
    }
    public List<Media> getMyMediaList() {
        return myMediaList;
    }
    public List<VideoGame> getGamesList() {
        return gamesList;
    }
    public List<Cinema> getMoviesList() {
        return moviesList;
    }
    public List<Series> getSeriesList() {
        return seriesList;
    }
    public List<Music> getMusicList() {
        return musicList;
    }
    public List<Creator> getCreatorsList() {
        return creatorsList;
    }
}
