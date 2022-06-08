package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.Tblcliente;
import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import Alejandro.BackendCentroNaturista.Model.Tblventa;
import Alejandro.BackendCentroNaturista.Repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class VentaController extends HttpServlet {
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ClienteController clienteController;
    @Autowired
    ProductoController productoController;
    @PostMapping("/venta")
    String newUser(@RequestBody Tblventa tblventa) throws FileNotFoundException, JRException {

        if(tblventa.getVencantidadunidades() <= 0){
            throw new Exception("P-400","Cantidad Unidades incorrecta");
        }
        if(tblventa.getVeniva() < 0){
            throw new Exception("P-400","IVA incorrecta");
        }
        if(tblventa.getVenvalorpagar() <= 0){
            throw new Exception("P-400","Valor a Pagar incorrecto");
        }
        boolean flag = false;
        for (Tblproducto variable : productoController.getAllProducts())
        {
            if (variable.getPronombre().equals(tblventa.getVenproducto())) {
                flag = true;
            }
        }
        if(!flag){
            throw new Exception("P-400","Producto Incorrecto!");
        }
        boolean flag2 = false;
        for (Tblcliente variable : clienteController.getAllCustomers()) {
            if (variable.getClinombre().equals(tblventa.getVencliente())) {
                flag2 = true;
            }
        }
        if(!flag2){
            throw new Exception("P-400","Cliente incorrecto");
        }
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        tblventa.setVenfechaactual(dtf2.format(LocalDateTime.now()));
        //System.out.println(tblventa.getVenfechaactual());
        ventaRepository.save(tblventa);
        //Reporte
        InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/venta.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        File fichero = new File("src/main/resources/logoFJ.png");
        List<Tblventa> venta = new ArrayList<>();
        venta.add(tblventa);
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(venta);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Created By","Alejandro");
        parameters.put("path", fichero.getAbsolutePath());
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        String pdfBase64 = Base64.getEncoder().encodeToString(pdf);
        return pdfBase64;
    }
    @GetMapping("/venta/{id}")
    Tblventa getVenta(@PathVariable Long id) {
        return ventaRepository.findById(id).orElseThrow(() -> new Exception("p-400","No se encontro la venta"));
    }
    @GetMapping("/venta")
    public String getAllCustomers(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, JRException, ServletException, IOException {
        try{
            InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/venta.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            File fichero = new File("src/main/resources/logoFJ.png");
            List<Tblventa> ventas = new ArrayList<>();
            ventas = (List<Tblventa>) ventaRepository.findAll();

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(ventas);
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
}
