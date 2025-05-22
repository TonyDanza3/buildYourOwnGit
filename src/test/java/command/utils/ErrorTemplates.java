package command.utils;

public class ErrorTemplates {
    public static String WRONG_INIT_INFO_MESSAGE = "Expected message is:\n'%s'\n but actual was:\n'%s'";
    public static String FILE_WAS_NOT_CREATED = "File %s does not exist but it should have been created";
    public static String FOLDER_WAS_NOT_CREATED = "Folder %s does not exist but it should have been created";
    public static String FILE_CONTENTS_WRONG = "File %s does not have a proper content: it should have contain \n\n%s \n\nbut contains\n\n%s\n\n";
}
