package Alejandro.BackendCentroNaturista.Controller;


import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import Alejandro.BackendCentroNaturista.Model.Tblrol;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.RolRepository;
import Alejandro.BackendCentroNaturista.Repositories.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
//import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//import org.springframework.security.crypto.password.PasswordEncoder;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;


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
    private static final Argon2 ARGON2 = Argon2Factory.create();

    private static final int ITERATIONS = 2;
    private static final int MEMORY= 65536;
    private static final int PARALLELISM = 1;
    @Autowired
    UsuarioRepository usuarioRespository;
    @Autowired
    RolRepository rolRepository;
    @Autowired
    RolController rolController;
    @PostMapping("/usuarios")
    String validation(@RequestBody String credenciales){
        JSONObject json = new JSONObject(credenciales);
        String usuario = json.getString("usuario");
        String contrasena = json.getString("contrasena");
        List<Tblusuario> usuarios = new ArrayList<>();
        usuarios = (List<Tblusuario>) usuarioRespository.findAll();
        //System.out.println("User:" + usuario + " Pass" + contrasena  );
        for (Tblusuario u:usuarios) {
            if((u.getUsucorreo().equals(usuario)) && (ARGON2.verify(u.getUsucontrasena(), contrasena ))){
                int r = u.getTblrol_rolid();
                Tblrol rol = rolRepository.findById(Long.valueOf(r)).orElseThrow(() -> new Exception("p-400","No se encontro el producto"));
                return rol.getRolnombre();
            }

        }
        return "NO";
    }
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
        //String pass  = BCrypt.hashpw(tblusuario.getUsucontrasena(), BCrypt.gensalt());
        String pass = ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, tblusuario.getUsucontrasena());
        tblusuario.setUsucontrasena(pass);
        //System.out.println("contraseña:" + tblusuario.getUsucontrasena());
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
