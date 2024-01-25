import java.io.IOException;
import java.util.Scanner;


/**
 * Main class representing user input and program output
 * @Author Petr Jakubec
 */
public class Main {
    /*to do:
        path for database
        Predelat tridu media na abstraktni - zatim oddleat koment na jedne metode, pozdeji pridat abstraktni metody pro vsechny dcerine tridy
         = kazda metoda co bude v dcerinych tridach, bude v Media jako abstaktni
     */

    /*
     * notebook
     * metoda find vrati index hledane polozky nebo negativni cislo, pokud neni nalezena
     *
     * */
    private static  Database database = new Database("database.xml");
    private static Creator placeHolder = new Creator("Petr Jakubec", 2003, Creators.DIRECTOR);
    private static Scanner sc = new Scanner(System.in);

    /**
     * Main mathod running whole dtabase
     * @param args console line arguments
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        System.out.println("Welcome to The Database!");
        Thread.sleep(1000);
        do {
            switch (showMenu()) {
                case 1:
                    database.browse();
                    break;
                case 2:
                    Media media = CreateMedia(database);
                    System.out.println();
                    System.out.println(media.toStringBrief());
                    System.out.println();
                    System.out.println(media.toString());
                    if (media.getName().equals("||ALREADY ADDED||")) {
                        System.out.println("This was already added!");
                        break;
                    }
                    database.AddMedia(media);
                    break;
                case 3:
                    database.CloseDatabase();
                    System.exit(0);
            }


        } while (true);
    }

    /**
     * Method responsible for showing menu of the database and gathering user input for the menu
     * @return a byte with menu option selected from user
     */
    public static byte showMenu() {
        String out;

        String[] options = new String[]{"1) Browse Media", "2) Add Media", "3) Exit App"};

        while (true) {
            System.out.flush();
            System.out.println("MEDIA DATABASE");
            System.out.println();
            for (int i = 0; i < options.length; i++) {
                System.out.println(options[i]);
            }
            System.out.println();
            System.out.println("What would you like to do?");
            System.out.print("Type number to choose your option: ");

            out = sc.nextLine();
            if (!(out.equals("1") || out.equals("2") || out.equals("3"))) {
                System.out.println("Wrong input!");
                continue;
            }
            return (byte) Integer.parseInt(out);
        }
    }


    /**
     * method creating a media based on user input
     * @param database reference to the database
     * @return the Media created by the user
     */
    public static Media CreateMedia(Database database) {
        String Shelper;

        String name;
        Creator author;
        Genre genre;
        byte ratingGlobal;
        byte ratingPrivate;
        short releaseYear;
        String info;
        int length;
        String[] tag;

        int classOption;
        int[] atributes;

        Media media;

        //choose type of media
        System.out.flush();
        System.out.println("What kind if Media would you like to add?");
        System.out.println("1) Movie \n2) Series \n3) Music \n4) Videogame ");
        do {
            Shelper = sc.nextLine();
            if (Shelper.equals("1") || Shelper.equals("2") || Shelper.equals("3") || Shelper.equals("4")) {
                classOption = Integer.parseInt(Shelper);
                break;
            }
        }while(true);

        //type name
        System.out.flush();
        System.out.println("Type name of the media");
        name = sc.nextLine();
        int helper = database.find(database.getMediaList(),name);
        if (helper >= 0) {
            name = "||ALREADY ADDED||";
            author = placeHolder;
            //get<Media>[helper];
            return database.getMediaList().get(helper);
        }

        //creating author
        author = creatingAuthor();
        switch (classOption) {
            case 4:
                media = new VideoGame(name, author);
                break;
            case 3:
                media = new Music(name, author);
                break;
            case 2:
                media = new Series(name, author);
                break;
            default:
                media = new Cinema(name, author);
                break;
        }
        addingAtributes(media);

        System.out.flush();
        System.out.println("Would you like to add some info about the media?");
        System.out.print("Y/N");
        if(proceed()){
            System.out.flush();
            System.out.println("Type brief info about the media and end with Enter");
            info = sc.nextLine();

            media.setInfo(info);
        }
        //adding tags to the media
        System.out.flush();
        System.out.println("Would you like to add some tags for the media?");
        System.out.print("Y/N");
        if(proceed()){
            System.out.flush();
            System.out.println("Type single-word tags separated with a space and end with Enter");
            tag = sc.nextLine().split(" ",0);

            for(String s : tag){
                media.setTag(s);
            }
        }
        return media;
    }

    /**
     * User will create new author based on his input
     *
     * @return Creator created by the user
     */
    public static Creator creatingAuthor() {

        String name;
        int birthYear;
        Creators type;

        System.out.flush();
        System.out.println("Creating Author");
        do {
            System.out.println("Type name of the author - Surname first!");
            name = sc.nextLine();
            if(name.isEmpty())continue;
            String[] names = name.split(" ", 0);
            int helper = database.findCreator(database.getCreatorsList(), names[0]);
            if (helper >= 0) {
                System.out.println("Author already added!");
                return database.getCreatorsList().get(helper);
            }
            break;
        }while(true);
        //birth year
        System.out.flush();
        System.out.println("type birth year of the author");
        String HYear;
        do {
            HYear = sc.nextLine();
            if (!testInput(HYear) || HYear.isEmpty()) {
                System.out.println("Wrong input!");
                continue;
            }
            birthYear = Integer.parseInt(HYear);
            break;
        }while(true);




        //type of the author
        System.out.flush();
        System.out.println("Choose type of the author");
        for (int i = 0; i < Creators.values().length; i++) {
            System.out.println((i + 1) + ") " + Creators.values()[i]);
        }
        do {
            String helpingIn = sc.nextLine();

            if (!(helpingIn.equals("1") || helpingIn.equals("2") || helpingIn.equals("3") || helpingIn.equals("4"))) {
                System.out.println("Wrong input!");
                continue;
            }
            type = Creators.values()[Integer.parseInt(helpingIn) - 1];
            break;

        } while (true);

        return new Creator(name, birthYear, type);
    }

    /**
     * method testing if parameter S is parsable to int
     * @param S tested String
     * @return true if String s is parsable to int, false if it isn't
     */
    public static boolean testInput(String S){
        int size = S.length();
        for(int i =0;i<size;i++){
            if(!(S.charAt(i)=='0'||S.charAt(i)=='1'||S.charAt(i)=='2'||S.charAt(i)=='3'||S.charAt(i)=='4'||S.charAt(i)=='5'||S.charAt(i)=='6'||S.charAt(i)=='7'||S.charAt(i)=='8'||S.charAt(i)=='9'))
                return false;
        }
        return true;
    }

    //method returns user input if he wants to proceed or not
    /**
     * method asking user if he wants to proceed or not
     * @return true if yes, false if no
     */
    public static boolean proceed() {
        while(true){
            switch(sc.nextLine()){
                case "y":

                    return true;
                case "n":

                    return false;
            }
        }
    }
    //method gathering atributes from user, creates an array of integers and each number on index means which atribute to add, if negative user did not input it
    //index: 0 - numbers means index in Genre.values(), 1 - global rating number (0-10), 2 - private rating of user, 3 - release year, 4 - length

    /**
     * method asking user if he wants to add any atributes and adds them to the media using private atribSet() method
     * @param media Media in which the atributes are being added
     */
    public static void addingAtributes(Media media){
        String[] atributes ={"genre","global rating", "private rating", "release year", "length"};
        int[] returnal= new int[]{-1,-1,-1,-1,-1};

        for (int i = 0; i < returnal.length; i++) {
            System.out.flush();
            System.out.println("Do you wish to add "+atributes[i]+" to the media?");
            System.out.print("Y/N");
            if(!proceed()){
                continue;
            }
            atribSet(i, sc, media);
        }
        System.out.flush();
    }
    
    private static void atribSet(int i, Scanner sc, Media media){
        int input;
        String help;
        switch (i){
            case 0:
                System.out.flush();
                System.out.println("Choose a number:");
                for (int j = 0; j < Genre.values().length; j++) {
                    System.out.println((j+1) + ") " + Genre.values()[j]);
                }


                do {
                    help = sc.nextLine();
                    if (!testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    input = Integer.parseInt(help);
                    break;
                }while(true);


                if(input <=Genre.values().length && input >0)
                    media.setGenre(Genre.values()[input]);
                return;
            case 1:
                System.out.print("Type a global rating of the media: ");
                do {
                    help = sc.nextLine();
                    if (!testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    input = Integer.parseInt(help);
                    break;
                }while(true);
                if(input>=0 && input<=10) media.setRatingGlobal((byte)input);
                return;
            case 2:
                System.out.print("Type a private rating of the media: ");
                do {
                    help = sc.nextLine();
                    if (!testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    input = Integer.parseInt(help);
                    break;
                }while(true);
                if(input>=0 && input<=10) media.setRatingPrivate(((byte)input));
                return;
            case 3:
                System.out.print("Type a release year of the media: ");
                do {
                    help = sc.nextLine();
                    if (!testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    input = Integer.parseInt(help);
                    break;
                }while(true);
                if(input>1800) media.setReleaseYear((short) input);
                return;
            case 4:
                System.out.print("Type how long is the media (type in minutes, in case of Series type length of one normal episode) ");
                do {
                    help = sc.nextLine();
                    if (!testInput(help) || help.isEmpty()) {
                        System.out.println("Wrong input!");
                        continue;
                    }
                    input = Integer.parseInt(help);
                    break;
                }while(true);
                if(input>0) media.setLength(input);
                return;

            default:
                return;
        }
    }
}

