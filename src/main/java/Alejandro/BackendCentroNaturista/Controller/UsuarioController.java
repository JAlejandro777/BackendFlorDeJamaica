package Alejandro.BackendCentroNaturista.Controller;


import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.Tblrol;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

//@RequestMapping("/FlorDeJamaica")
public class UsuarioController{
    @Autowired
    UsuarioRepository usuarioRespository;
    @Autowired
    RolController rolController;
    @PostMapping("/usuario")
    Tblusuario newUser(@RequestBody Tblusuario tblusuario) {
        boolean flag = false;
        for (Tblrol variable : rolController.getAllRol())
        {
            if (variable.getRolid() == tblusuario.getTblrol_rolid()) {
                flag = true;
            }
        }
        if(!flag){
            String rol = "Rol " + tblusuario.getTblrol_rolid() + " incorrecto!";
            throw new Exception("P-400", rol);
        }
        if(tblusuario.getUsucedula().equals("")  || (tblusuario.getUsucedula().length() > 10)){
            throw new Exception("P-400","Cedula incorrecta");
        }
        if(tblusuario.getUsunombre().equals("")){
            throw new Exception("P-400","Nombre incorrecto");
        }
        if(tblusuario.getUsucelular().equals("") ||(tblusuario.getUsucelular().length() != 10)){
            throw new Exception("P-400","Celular incorrecto");
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tblusuario.getUsucorreo());
        if(tblusuario.getUsucorreo().equals("") || (!matcher.matches())){
            throw new Exception("P-400","Correo incorrecto");
        }
        if(tblusuario.getUsucontrasena().equals("")){
            throw new Exception("P-400","Contraseña incorrecta");
        }
        return usuarioRespository.save(tblusuario);
    }
    @GetMapping("/usuario")
    public List<Tblusuario> getAllUsers() {
        return (List<Tblusuario>) usuarioRespository.findAll();
    }
    @GetMapping("/usuarios")
    public String getAllUsersReport() throws FileNotFoundException, JRException {
        InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/usuario.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        File fichero = new File("src/main/resources/logoFJ.png");
        List<Tblusuario> usuarios = new ArrayList<>();
        usuarios = (List<Tblusuario>) usuarioRespository.findAll();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(usuarios);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Created By","Alejandro");
        parameters.put("path", fichero.getAbsolutePath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        String pdfBase64 = Base64.getEncoder().encodeToString(pdf);
        return pdfBase64;

    }
}
