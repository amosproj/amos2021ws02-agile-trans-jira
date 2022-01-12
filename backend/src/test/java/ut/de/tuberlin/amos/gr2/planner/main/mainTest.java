package ut.de.tuberlin.amos.gr2.planner.main;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class mainTest {

    @Mock
    private HttpServletRequest mockRequest;
    @Mock private HttpServletResponse mockResponse;
    @Mock private ServletConfig servletConfig;
    @Mock private ServletContext servletContext;

    private de.tuberlin.amos.gr2.planner.main.MainPageServlet servlet;

    @Before
    public void setup() {//initialize
//        mockRequest = mock(HttpServletRequest.class);
//        mockResponse = mock(HttpServletResponse.class);
        MockitoAnnotations.initMocks(this);
        servlet = new de.tuberlin.amos.gr2.planner.main.MainPageServlet();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testServlet() throws Exception {
        servlet.init(servletConfig);
        given(servletConfig.getServletContext()).willReturn(servletContext);

        PrintWriter pw = new PrintWriter(System.out, true);
        given(mockResponse.getWriter()).willReturn(pw);
        servlet.doGet(mockRequest, mockResponse);
        pw.flush();
        verify(mockResponse);
    }

    private void verify(HttpServletResponse response) {
        System.out.println("Response:"+response);
        Assert.assertNotNull(response);
    }


    @Test
    public void testSomething () {
        String expected = "test";
        when(mockRequest.getParameter(Mockito.anyString())).thenReturn(expected);
        assertEquals(expected, mockRequest.getParameter("some string"));

    }

    @Test
    public void testDoGet () throws ServletException, IOException {

        servlet.doGet(mockRequest, mockResponse);
        assertEquals(null, mockResponse.getContentType());

    }

}
