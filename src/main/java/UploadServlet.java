import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UploadServlet")
public class UploadServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = "C:\\Users\\magzhan\\Desktop\\servletupload\\";
        File dir = new File(path);
        try {
            ServletFileUpload sf = new ServletFileUpload(new DiskFileItemFactory());
            List<FileItem> multiFiles = sf.parseRequest(request);
            for (FileItem file : multiFiles) {
                file.write(new File(path + file.getName()));
            }

            ArrayList<String> type = new ArrayList<String>();
//            System.out.println("HELLO");
            for (FileItem file : multiFiles) {
                type.add(getType(new File(path + file.getName())));
            }

            request.setAttribute("typeOfFiles", type);
            request.getRequestDispatcher("/jsp/guessLanguage.jsp").forward(request, response);
        } catch (FileUploadException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(html|jsp|java))$)");

    public boolean validateFileExtn(String userName) {
        Matcher mtch = fileExtnPtrn.matcher(userName);
        if (mtch.matches()) {
            return true;
        }
        return false;
    }

    static String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    String[] servletThings = {
            "protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {",
            "protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {",
            "import javax.servlet.ServletException;",
            "import javax.servlet.annotation.WebServlet;",
            "import javax.servlet.http.HttpServlet;",
            "import javax.servlet.http.HttpServletRequest;",
            "import javax.servlet.http.HttpServletResponse;",
            "import java.io.IOException;",
            "@WebServlet(name = ",
            "extends HttpServlet {"
    };

    private String isServletOrPureJava(File file)
            throws IOException {
        String content = readFile(file.getAbsolutePath(), StandardCharsets.UTF_8);

        for (String to: servletThings) {
            if (content.contains(to)) {
                return "SERVLET";
            }
        }

        return "JAVA";
    }

    private String getType(File file)
            throws IOException {
        if (!validateFileExtn(file.getName())) {
            return null;
        }
        String fileName = (String) file.getName();
        int i = fileName.lastIndexOf('.');
        String extension = fileName.substring(i + 1);
        switch ((extension).toLowerCase()) {
            case "html":
                return "HTML";
            case "jsp":
                return "JSP";
            case "java":
                return isServletOrPureJava(file);
        }
        //System.out.println(new String (file.toString()));
        return null;
    }
}
