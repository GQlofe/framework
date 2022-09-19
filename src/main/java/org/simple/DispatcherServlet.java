package org.simple;

import org.apache.commons.lang3.StringUtils;
import org.simple.bean.Data;
import org.simple.bean.Param;
import org.simple.bean.View;
import org.simple.core.*;
import org.simple.util.JsonUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 所有请求入口Servlet
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        registerServlet(config.getServletContext());
        // 初始化框架
        FrameInitialize.init();

    }

    private void registerServlet(ServletContext servletContext) {
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        jspServlet.addMapping("/WEB-INF/view/" + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String reuqestMethod = req.getMethod();
        String requestPath = req.getPathInfo();
        RequestMappingHandler handler = RequestMappingManager.getHandler(requestPath, reuqestMethod);

        if (handler != null){
            Class<?> controllerClass = handler.getControllerClass();
            Method method = handler.getMethod();

            Object controller = BeanManeger.getBeanMap().get(controllerClass);

            Param param= RequestHelper.createParam(req);

            Object result;
            try {
                method.setAccessible(true);
                int parameterCount = method.getParameterCount();
                if (parameterCount == 0 || param.isEmpty()){
                    result = method.invoke(controller);
                }
                else {
                    result = method.invoke(controller, param);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }

            if (result instanceof View) {
                handleViewResult((View) result, req, resp);
            } else if (result instanceof Data) {
                handleDataResult((Data) result, resp);
            }
        }
    }


    private void handleViewResult(View view, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = view.getPath();
        if (StringUtils.isNotEmpty(path)) {
            if (path.startsWith("/")) {
                response.sendRedirect(request.getContextPath() + path);
            } else {
                Map<String, Object> model = view.getModel();
                for (Map.Entry<String, Object> entry : model.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.getRequestDispatcher("/WEB-INF/view/" + path).forward(request, response);
            }
        }
    }

    private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null) {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();
        }
    }
}
