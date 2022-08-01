package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.RecuperarContraseña;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.RecuperContraseñaRepository;
import Alejandro.BackendCentroNaturista.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RecuperarContraseñaController {
    private Tblusuario usuario;
    @Autowired
    UsuarioController usuarioController;
    @Autowired
    UsuarioRepository usuarioRespository;
    @Autowired
    RecuperContraseñaRepository recuperContraseñaRepository;
    @Autowired
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    @PostMapping("/recoverPassword")
    String recoverPassword(@RequestBody String data) throws MessagingException {
        //System.out.println("hola");
        JSONObject json = new JSONObject(data);
        String correo = json.getString("correo");
        boolean flag = false;
        for(Tblusuario u: usuarioController.getUsuarios()){
            if(u.getUsucorreo().equals(correo)){
                this.usuario = u;
                flag = true;
            }
        }
        if(!flag){
            throw new Exception("P-400", "El correo que ingreso no corresponde a ningun usuario.");
        }
        //Número
        int number;
        String finalNumber = "";
        number = (int)(10000 * Math.random());
        finalNumber = "" + number;
        for(int i = finalNumber.length(); i < 4; i++){
            finalNumber = "0" + finalNumber;
        }
        RecuperarContraseña rc = new RecuperarContraseña(correo,finalNumber);
        recuperContraseñaRepository.save(rc);
        File fichero = new File("src/main/resources/logoFJ.png");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", this.usuario.getUsunombre());
        properties.put("imagen", fichero.getAbsolutePath());
        properties.put("codigo",finalNumber);
        context.setVariables(properties);
        //helper.setFrom(email.getFrom());
        helper.setTo(correo);
        helper.setSubject("Recuperación Contraseña");
        String html = templateEngine.process("welcome-email.html", context);
        helper.setText(html, true);

        mailSender.send(message);
        return "Se ha enviado a su correo el código para restablecer la contraseña.";
    }
    @PostMapping("/verifyCode")
    String verifyCode(@RequestBody String code){
        JSONObject json = new JSONObject(code);
        String codigo = String.valueOf(json.getInt("code"));
        RecuperarContraseña rc = recuperContraseñaRepository.findById(usuario.getUsucorreo()).orElseThrow(() -> new Exception("p-400","No se encontro el usuario"));;
        if(rc.getCodigo().equals(codigo)){
            return "Código Correcto. Ingrese su nueva contraseña.";
        }
        throw new Exception("P-400", "Código Incorrecto");
    }
    @PostMapping("/changePass")
    Tblusuario changePass(@RequestBody String contraseña){
        JSONObject json = new JSONObject(contraseña);
        String pass = json.getString("pass1");
        this.usuario.setUsucontrasena(pass);
        return usuarioController.newUser(this.usuario);
    }
}
