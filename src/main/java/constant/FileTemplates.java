package constant;

public class FileTemplates {
    public static final String HEAD = "ref: refs/heads/main";
    public static final String CONFIG = "[core]\n" +
            "\trepositoryformatversion = 0\n" +
            "\tfilemode = true\n" +
            "\tbare = false\n" +
            "\tlogallrefupdates = true\n" +
            "\tignorecase = true\n" +
            "\tprecomposeunicode = true";
    public static final String DESCRIPTION = "Unnamed repository; edit this file 'description' to name the repository.";
}
