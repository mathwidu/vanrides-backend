package com.vanrides.vanrides.filtros;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import com.vanrides.vanrides.repository.MotoristaRepository;
import com.vanrides.vanrides.repository.PassageiroRepository;
import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterAuthBasic extends OncePerRequestFilter {

    @Autowired
    private PassageiroRepository passageiroRepository;

    @Autowired
    private MotoristaRepository motoristaRepository;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain chain)
        throws ServletException, IOException {

        String path = request.getServletPath();
        String method = request.getMethod();

        // 🔓 Permitir POST sem autenticação para cadastro de passageiro e motorista
        if ((path.equals("/passageiros") || path.equals("/motoristas")) && method.equals("POST")) {
            chain.doFilter(request, response);
            return;
        }

        // 🔓 Permitir GET sem autenticação para listar motoristas
        if (path.equals("/motoristas") && method.equals("GET")) {
            chain.doFilter(request, response);
            return;
        }

        // 🔒 Protege os demais endpoints
        if (path.startsWith("/presencas") || path.startsWith("/passageiros") || path.startsWith("/motoristas")) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Basic ")) {
                response.sendError(401, "Cabeçalho Authorization ausente");
                return;
            }

            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded);
            String[] values = credentials.split(":", 2);

            if (values.length != 2) {
                response.sendError(400, "Formato de autenticação inválido");
                return;
            }

            String email = values[0];
            String senha = values[1];

            var passageiro = passageiroRepository.findByEmail(email);
            if (passageiro != null &&
                BCrypt.verifyer().verify(senha.toCharArray(), passageiro.getSenha().toCharArray()).verified) {
                request.setAttribute("userRole", "PASSAGEIRO");
                request.setAttribute("userId", passageiro.getId());
                chain.doFilter(request, response);
                return;
            }

            var motorista = motoristaRepository.findByEmail(email);
            if (motorista != null &&
                BCrypt.verifyer().verify(senha.toCharArray(), motorista.getSenha().toCharArray()).verified) {
                request.setAttribute("userRole", "MOTORISTA");
                request.setAttribute("userId", motorista.getId());
                chain.doFilter(request, response);
                return;
            }

            response.sendError(401, "Credenciais inválidas");
        } else {
            chain.doFilter(request, response);
        }
    }
}
