package tw.teddysoft.tasks.entity;

import tw.teddysoft.ezddd.core.entity.ValueObject;

public record TaskId(String value)  implements ValueObject {

    public static String [] SPECIAL_CHARACTERS = {":", "\\", "/", "%", "$"};


    public TaskId {
        if (containsSpace(value)) throw new RuntimeException("Task Id cannot contains spaces");
        if (containsSpecialCharacters(value)) throw new RuntimeException("Task Id cannot contains special characters " + specialCharactersToString());
    }

    public static TaskId of(long id) {
        return new TaskId(String.valueOf(id));
    }

    public static TaskId of(String id) {
        return new TaskId(id);
    }

    @Override
    public String toString(){
        return value;
    }

    private static boolean containsSpace(String str){
        return str.matches(".*\\s+.*");
    }

    public static boolean containsSpecialCharacters(String str) {
        for (String specialChar : SPECIAL_CHARACTERS) {
            if (str.contains(specialChar)) {
                return true;
            }
        }
        return false;
    }

    private static String specialCharactersToString() {
        StringBuilder sb = new StringBuilder();

        for (String specialChar : SPECIAL_CHARACTERS) {
            sb.append(specialChar).append(", ");
        }
        if (!sb.isEmpty()) {
            sb.delete(sb.length() - 2, sb.length());
        }
        return sb.toString();
    }
}
