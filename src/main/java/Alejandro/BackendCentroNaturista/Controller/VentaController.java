package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.Tblcliente;
import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import Alejandro.BackendCentroNaturista.Model.Tblventa;
import Alejandro.BackendCentroNaturista.Repositories.ClienteRepository;
import Alejandro.BackendCentroNaturista.Repositories.ProductoRepository;
import Alejandro.BackendCentroNaturista.Repositories.VentaRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.json.JSONArray;
import org.json.JSONObject;
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
    private List<Tblventa> ventas;
    private Integer total;
    private Integer monto;
    private Integer devolucion;
    @Autowired
    VentaRepository ventaRepository;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    ProductoController productoController;
    @PutMapping("/sale/{id}")
    public Tblventa updateSale(@PathVariable("id") long id, @RequestBody Tblventa ven) {
        Tblproducto producto = productoRepository.findNamebyId(ven.getVenproducto()).orElseThrow(() -> new Exception("p-400","No se encontro el producto"));
        try {
            Tblventa venta = ventaRepository.findById(id).orElseThrow(() -> new Exception("p-400", "No se encontro la venta"));
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fecha = dtf2.format(LocalDateTime.now());
            venta.setVenfechaactual(fecha);
            venta.setVencliente(ven.getVencliente());
            venta.setVenproducto(ven.getVenproducto());
            venta.setVeniva(ven.getVeniva());
            if(venta.getVencantidadunidades() > ven.getVencantidadunidades()){
                int ud = producto.getProunidadesdisponibles() + (venta.getVencantidadunidades() - ven.getVencantidadunidades());
                producto.setProunidadesdisponibles(ud);
            }else{
                int ud = producto.getProunidadesdisponibles() - (ven.getVencantidadunidades() - venta.getVencantidadunidades());
                producto.setProunidadesdisponibles(ud);
            }
            venta.setVencantidadunidades(ven.getVencantidadunidades());
            venta.setVenvalorpagar(ven.getVenvalorpagar());
            productoRepository.save(producto);
            return ventaRepository.save(venta);
        }catch (Exception e){
            throw new Exception("P-400","NO");
        }

    }
    @DeleteMapping("/sale/{id}")
    String delete(@PathVariable("id") long id){
        Tblventa ven = ventaRepository.findById(id).orElseThrow(() -> new Exception("p-400", "No se encontro la venta"));
        Tblproducto producto = productoRepository.findNamebyId(ven.getVenproducto()).orElseThrow(() -> new Exception("p-400","No se encontro el producto"));
        try {
            producto.setProunidadesdisponibles(producto.getProunidadesdisponibles() + ven.getVencantidadunidades());
            productoRepository.save(producto);
            ventaRepository.deleteById(id);
            return "OK";
        } catch (Exception e) {
            return "NO";
        }

    }
    @PostMapping("/data")
    String datos(@RequestBody String object ){
        try{
            JSONObject json = new JSONObject(object);
            Integer total = json.getInt("total");
            Integer monto = json.getInt("monto");
            Integer devolucion = json.getInt("devolucion");
            this.total = total;
            this.monto = monto;
            this.devolucion = devolucion;

            return "Ok";
        }catch (Exception e){
           return "NO";
        }

    }
    @PostMapping("/factura")
    String invoice(@RequestBody ArrayList<Object> productos) throws JRException {
        //Reporte
        List<Tblventa> venta = new ArrayList<>();
        for (Object variable : productos){
            Tblventa ven = new Tblventa();
            List<Object> p;
            p = (List<Object>) variable;
            ven.setVencantidadunidades((Integer) p.get(1));
            ven.setVeniva((Integer) p.get(2));
            ven.setVenvalorpagar((Integer) p.get(3));
            ven.setVenproducto((String) p.get(4));
            ven.setVencliente((String) p.get(5));
            ven.setVenfechaactual((String) p.get(6));
            venta.add(ven);
        }
        //System.out.println(total);
        InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/venta2.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
        File fichero = new File("src/main/resources/logoFJ.png");
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(venta);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Created By","Alejandro");
        parameters.put("path", fichero.getAbsolutePath());
        parameters.put("total", this.total);
        parameters.put("monto", this.monto);
        parameters.put("devolucion", this.devolucion);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameters,dataSource);
        byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
        String pdfBase64 = Base64.getEncoder().encodeToString(pdf);
        return pdfBase64;
    }
    @PostMapping("/venta")
    Tblventa newSale(@RequestBody Tblventa tblventa) throws FileNotFoundException, JRException {
        if(tblventa.getVencantidadunidades() <= 0){
            throw new Exception("P-400","Cantidad Unidades incorrecta");
        }
        if(tblventa.getVeniva() < 0){
            throw new Exception("P-400","IVA incorrecta");
        }
        if(tblventa.getVenvalorpagar() <= 0){
            throw new Exception("P-400","Valor a Pagar incorrecto");
        }
        Tblproducto producto = productoRepository.findNamebyId(tblventa.getVenproducto()).orElseThrow(() -> new Exception("p-400","No se encontro el producto"));
        if(producto.getProunidadesdisponibles() < 1 ){
            throw new Exception("P-400","No hay unidades!");
        }
        if(producto.getProunidadesdisponibles() < tblventa.getVencantidadunidades()){
            throw new Exception("P-400","No hay esta cantidad!");
        }
        Tblcliente cli = clienteRepository.findNamebyId(tblventa.getVencliente()).orElseThrow(() -> new Exception("p-400","No se encontro el cliente"));
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fecha = dtf2.format(LocalDateTime.now());
        tblventa.setVenfechaactual(fecha);
        producto.setProunidadesdisponibles(producto.getProunidadesdisponibles() - tblventa.getVencantidadunidades());
        productoRepository.save(producto);
        return ventaRepository.save(tblventa);
    }
    @GetMapping("/venta/{id}")
    Tblventa getVenta(@PathVariable Long id) {
        return ventaRepository.findById(id).orElseThrow(() -> new Exception("p-400","No se encontro la venta"));
    }
    @GetMapping("/ventasAll")
    List<Tblventa> allventas(){
        this.ventas = (List<Tblventa>) ventaRepository.findAll();
        return this.ventas;
    }
    @GetMapping("/venta")
    public String createReportSales(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, JRException, ServletException, IOException {
        try{
            InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/venta.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            File fichero = new File("src/main/resources/logoFJ.png");
            List<Tblventa> ventas = new ArrayList<>();
            //ventas = (List<Tblventa>) ventaRepository.findAll();
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.ventas);
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
