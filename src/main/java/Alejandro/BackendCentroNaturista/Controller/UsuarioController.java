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
    private List<Tblusuario> usuarios;
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
    List<String> validation(@RequestBody String credenciales){
        List<String> response =  new ArrayList<>();
        JSONObject json = new JSONObject(credenciales);
        String correo = json.getString("correo");
        String contrasena = json.getString("contrasena");
        List<Tblusuario> usuarios = new ArrayList<>();
        //usuarios = (List<Tblusuario>) usuarioRespository.findAll();
        //System.out.println("User:" + usuario + " Pass" + contrasena  );
        for (Tblusuario u:this.usuarios) {
            if((u.getUsucorreo().equals(correo)) && (ARGON2.verify(u.getUsucontrasena(), contrasena ))){
                int r = u.getTblrol_rolid();
                Tblrol rol = rolRepository.findById(Long.valueOf(r)).orElseThrow(() -> new Exception("p-400","No se encontro el rol"));
                response.add(rol.getRolnombre());
                response.add(u.getUsunombre());
                return response;
            }

        }
        throw new Exception("P-400","Credenciales incorrectas!");
    }
    @PostMapping("/usuario")
    Tblusuario newUser(@RequestBody Tblusuario tblusuario) {
        rolRepository.findById((long) tblusuario.getTblrol_rolid()).orElseThrow(() -> new Exception("p-400","No se encontro el rol " + tblusuario.getTblrol_rolid()));

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
        String pass = ARGON2.hash(ITERATIONS, MEMORY, PARALLELISM, tblusuario.getUsucontrasena());
        tblusuario.setUsucontrasena(pass);
        return usuarioRespository.save(tblusuario);
    }
    @GetMapping("/usuario")
    public List<Tblusuario> getAllUsers() {
        this.usuarios = (List<Tblusuario>) usuarioRespository.findAll();
        //System.out.println("ENTRO ACCAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        return this.usuarios;
    }
    @GetMapping("/usuarios")
    public String getAllUsersReport() throws FileNotFoundException, JRException {
        try {
            InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/usuario.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            File fichero = new File("src/main/resources/logoFJ.png");
            List<Tblusuario> usuarios = new ArrayList<>();
            //usuarios = (List<Tblusuario>) usuarioRespository.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.usuarios);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Created By","Alejandro");
            parameters.put("path", fichero.getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            String pdfBase64 = Base64.getEncoder().encodeToString(pdf);
            return pdfBase64;
        }catch(java.lang.Exception e){
            String error = "Error en el reporte: " + e;
            throw new Exception("P-400",error);
        }

    }
    public List<Tblusuario> getUsuarios() {
        return usuarios;
    }
    @PostMapping("/existenciaCorreo")
    String existenciaCorreo(@RequestBody String Correo){
        JSONObject json = new JSONObject(Correo);
        String correo = json.getString("usucorreo");
        //System.out.println(correo);
        for(Tblusuario u: this.usuarios){
            if(correo.equals(u.getUsucorreo())){
                return "Correo Existente";
            }
        }
        return "Correo Inexistente";
    }
    @PostMapping("/existenciaCedula")
    String existenciaCedula(@RequestBody String Cedula){
        JSONObject json = new JSONObject(Cedula);
        String cedula = json.getString("usucedula");
        //System.out.println(cedula);
        for(Tblusuario u: this.usuarios){
            if(cedula.equals(u.getUsucedula())){
                return "Cedula Existente";
            }
        }
        return "Cedula Inexistente";
    }
}
