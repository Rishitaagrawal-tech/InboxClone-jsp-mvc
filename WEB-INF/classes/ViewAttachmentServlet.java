package pack;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;

public class ViewAttachmentServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("file");
        if (fileName == null || fileName.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "File not specified");
            return;
        }

        // Change this to your actual upload folder path
        String uploadPath = "C:\\Tomcat 8.5\\webapps\\Login-signup_jsp _mvc _Copy\\upload";
        File file = new File(uploadPath, fileName);

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
            return;
        }

        // Detect content type
        String mimeType = Files.probeContentType(file.toPath());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }

        response.setContentType(mimeType);
        // Use inline to display in browser
        response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
        response.setContentLength((int) file.length());

        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }
}
