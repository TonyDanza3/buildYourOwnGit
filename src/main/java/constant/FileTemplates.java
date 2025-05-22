package constant;

public class FileTemplates {
    public static final String HEAD_FILE_TEMPLATE = "ref: refs/heads/main";
    public static final String CONFIG_FILE_TEMPLATE = "[core]\n" +
            "\trepositoryformatversion = 0\n" +
            "\tfilemode = true\n" +
            "\tbare = false\n" +
            "\tlogallrefupdates = true\n" +
            "\tignorecase = true\n" +
            "\tprecomposeunicode = true";
    public static final String DESCRIPTION_FILE_TEMPLATE = "Unnamed repository; edit this file 'description' to name the repository.";
}
