package pack;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("file");
        String filePath = "C:\\Tomcat 8.5\\webapps\\Login-signup_jsp _mvc _Copy\\upload\\" + fileName;
        
        File file = new File(filePath);
        if (!file.exists()) {
            response.getWriter().println("File not found!");
            return;
        }

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        FileInputStream in = new FileInputStream(file);
        OutputStream out = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
        in.close();
        out.close();
    }
}
