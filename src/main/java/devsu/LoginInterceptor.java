package devsu;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = "";
        // si es metodo options es un request de tipo pre flight, debe ser aceptada
        // pues este tipo de resquest no tienen la cabecera token ni ejecuta
        // el codigo del rest
        if (request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }

        try {
            token = request.getHeader("token").toString();
            if (!token.equals("noTokenBP")){
                return true;
            }

        } catch (Exception e) {
            // si no hay token

            response.setStatus(403);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("Token no válido");
            response.addHeader("Access-Control-Allow-Origin", "*");
            return false;
        }

        return true;
    }


}