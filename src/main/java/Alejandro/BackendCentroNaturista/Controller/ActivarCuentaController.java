package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.ActivarCuenta;
import Alejandro.BackendCentroNaturista.Model.RecuperarContraseña;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.ActivarCuentaRepository;
import Alejandro.BackendCentroNaturista.Repositories.RecuperContraseñaRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ActivarCuentaController {
    @Autowired
    ActivarCuentaRepository activarCuentaRepository;
    @Autowired
    private JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;
    private String Correo;
    private String Nombre;
    @PostMapping("/activarCuenta")
    String activarCuenta(@RequestBody String data) throws MessagingException {
        //System.out.println("hola");
        JSONObject json = new JSONObject(data);
        String correo = json.getString("usucorreo");
        String nombre = json.getString("usunombre");
        this.Correo = correo;
        this.Nombre = nombre;
        //Número
        int number;
        String finalNumber = "";
        number = (int)(10000 * Math.random());
        finalNumber = "" + number;
        for(int i = finalNumber.length(); i < 4; i++){
            finalNumber = "0" + finalNumber;
        }
        ActivarCuenta rc = new ActivarCuenta(correo,finalNumber);
        activarCuentaRepository.save(rc);
        //File fichero = new File("src/main/resources/logoFJ.png");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        Context context = new Context();
        Map<String, Object> properties = new HashMap<>();
        properties.put("name", nombre);
        //properties.put("imagen", fichero.getAbsolutePath());
        properties.put("codigo",finalNumber);
        context.setVariables(properties);
        //helper.setFrom(email.getFrom());
        helper.setTo(correo);
        helper.setSubject("Activación Cuenta");
        String html = templateEngine.process("ActivarCuenta.html", context);
        helper.setText(html, true);

        mailSender.send(message);
        return "Se ha enviado a su correo el código para activar la cuenta.";
    }
    @PostMapping("/verifyCodeActivate")
    String verifyCodeActivate(@RequestBody String code){
        JSONObject json = new JSONObject(code);
        String codigo = json.getString("code");
        ActivarCuenta ac = activarCuentaRepository.findById(this.Correo).orElseThrow(() -> new Exception("p-400","No se encontro el usuario"));;
        //System.out.println(ac.getCodigo());
        //System.out.println(codigo);
        if(ac.getCodigo().equals(codigo)){
            return "Bienvenido a la familia estimado " + this.Nombre + ".";
        }
        throw new Exception("P-400", "Código Incorrecto");
    }
}
