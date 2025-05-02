import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/EnrollServlet")
public class EnrollServlet extends HttpServlet {

    
    private List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        courses.add(new Course("CS101", "Intro to Computer Science", "Dr. Smith"));
        courses.add(new Course("MATH201", "Calculus I", "Prof. Johnson"));
        courses.add(new Course("PHY301", "Physics Fundamentals", "Dr. Allen"));
        return courses;
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        
        String courseId = request.getParameter("courseId");

        if (courseId != null && !courseId.isEmpty()) {
            
            HttpSession session = request.getSession();

            
            List<Course> enrolledCourses = (List<Course>) session.getAttribute("enrolledCourses");
            if (enrolledCourses == null) {
                enrolledCourses = new ArrayList<>();
            }

    
            List<Course> allCourses = getAllCourses();
            Course selectedCourse = null;

            for (Course course : allCourses) {
                if (course.getId().equals(courseId)) {
                    selectedCourse = course;
                    break;
                }
            }

            
            if (selectedCourse != null) {
                boolean alreadyEnrolled = enrolledCourses.stream()
                        .anyMatch(c -> c.getId().equals(courseId));

                if (!alreadyEnrolled) {
                    enrolledCourses.add(selectedCourse);
                    session.setAttribute("enrolledCourses", enrolledCourses);
                }
            }
        }

        
        response.sendRedirect("DashboardServlet");
    }
}
