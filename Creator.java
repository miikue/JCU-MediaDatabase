/**
 * Class representing authors
 * @Author Petr Jakubec
 */
class Creator{
    private final String name;
    private final String forename;
    private final String surname;
    private final int birthYear;
    private final Creators type;

    /**
     * constructor creating a creator
     * @param name name of the creator
     * @param birthYear birth year of the creator
     * @param type a type of the creatro, listed in Enum Creators
     */
    public Creator(String name, int birthYear, Creators type){
        this.name = name;
        this.birthYear = birthYear;
        this.type = type;
        String helper = "";

        String [] names = name.split(" ",0);
        this.surname = names[0];
        for (int i = 1; i < names.length; i++) {
            helper += names[i];
        }
        this.forename = helper;
    }

    /**
     * Returns surname of the creator
     * @return surname of the creator
     */
    public String getName() {
        return surname;
    }

    /**
     * Returns forename of the creator
     * @return forename of the creator
     */
    public String getForename() {
        return forename;
    }

    /**
     * Returns full name of the creator
     * @return full name of the creator
     */
    public String getFullName() {
        return name;
    }

    /**
     * Returns birth year of the creator
     * @return birth year of the creator
     */
    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Returns type of the creator
     * @return type of the creator
     */
    public Creators getType() {
        return type;
    }
}

/**
 * Enum class representing types of author
 */
enum Creators{
    DIRECTOR, ACTOR, COMPANY, DEVELOPER;
}