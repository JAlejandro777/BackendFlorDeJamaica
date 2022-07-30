package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.Tblcliente;
import Alejandro.BackendCentroNaturista.Repositories.ClienteRepository;
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


public class ClienteController {
    private List<Tblcliente> clientes;
    @Autowired
    ClienteRepository clienteRepository;
    @PostMapping("/cliente")
    Tblcliente newCustomer(@RequestBody Tblcliente tblCliente) {
        if(tblCliente.getClicedula().equals("") ||  (tblCliente.getClicedula().length() > 10)){
            throw new Exception("P-400","Cedula Incorrecta!");
        }
        if(tblCliente.getClinombre().equals("") ){
            throw new Exception("P-400","Nombre Incorrecto!");
        }
        if(tblCliente.getClicelular().equals("") || (tblCliente.getClicelular().length() != 10) ){
            throw new Exception("P-400","Celular Incorrecto!");
        }
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tblCliente.getClicorreo());
        if(tblCliente.getClicorreo().equals("") || (!matcher.matches()) ){
            throw new Exception("P-400","Correo Incorrecto!");
        }

        return clienteRepository.save(tblCliente);
    }
    @GetMapping("/cliente")
    public List<Tblcliente> getAllCustomers() {
        this.clientes = (List<Tblcliente>) clienteRepository.findAll();
        return this.clientes;
    }
    @GetMapping("/cliente/{id}")
    public List<Tblcliente>  getCustomer(@PathVariable String id) {
        List<Tblcliente> customers = new ArrayList<>();
        //System.out.println(id);
        customers.add(clienteRepository.findById(id).orElseThrow(() -> new Exception("p-400","No se encontro el Cliente")));
        //System.out.println(customers.get(0).getClicorreo());
        return customers;
    }
    @GetMapping("/clientes")
    public String getAllCustomersReport() throws FileNotFoundException, JRException {
        try {
            InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/cliente.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            File fichero = new File("src/main/resources/logoFJ.png");
            List<Tblcliente> clientes = new ArrayList<>();
            //clientes = (List<Tblcliente>) clienteRepository.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.clientes);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Created By", "Alejandro");
            parameters.put("path", fichero.getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            String pdfBase64 = Base64.getEncoder().encodeToString(pdf);
            return pdfBase64;
        }catch(java.lang.Exception e){
            String error = "Error en el reporte: " + e;
            throw new Exception("P-400",error);
        }
    }
}
