package Alejandro.BackendCentroNaturista.Controller;

import Alejandro.BackendCentroNaturista.Excepcion.Exception;
import Alejandro.BackendCentroNaturista.Model.Tblproducto;
import Alejandro.BackendCentroNaturista.Model.Tblproveedor;
import Alejandro.BackendCentroNaturista.Model.Tblusuario;
import Alejandro.BackendCentroNaturista.Repositories.ProductoRepository;
import Alejandro.BackendCentroNaturista.Repositories.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor

public class ProductoController {
    private List<Tblproducto> productos;
    @Autowired
    ProductoRepository productoRepository;
    @Autowired
    ProveedorController proveedorController;
    @Autowired
    ProveedorRepository proveedorRepository;
    @Autowired
    UsuarioController usuarioController;
    @PostMapping("/producto")
    Tblproducto newProduct(@RequestBody Tblproducto tblproducto) throws ParseException {
        for (Tblproducto i : this.productos){
            if (i.getProcodigo().equals(tblproducto.getProcodigo())){
                throw new Exception("P-400","Código existente!");
            }
        }
        boolean flag = false;
        for (Tblproveedor variable : proveedorController.getAllSupplier())
        {
            if (variable.getProid().equals(tblproducto.getTblproveedor_proid())) {
                flag = true;
            }
        }
        if(!flag){
            throw new Exception("P-400","Proveedor Incorrecto!");
        }
        boolean flag2 = false;
        for (Tblusuario variable : usuarioController.getAllUsers())
        {
            if (variable.getUsucorreo().equals(tblproducto.getTblusuario_usuid())) {
                flag2 = true;
            }
        }
        if(!flag2){
            throw new Exception("P-400","Usuario Incorrecto!");
        }
        if(tblproducto.getPronombre().equals("") ){
            throw new Exception("P-400","Nombre Incorrecto!");
        }
        if(tblproducto.getProcaracteristica().equals("") ){
            throw new Exception("P-400","Caracteristica Incorrecta!");
        }
        if(tblproducto.getProcategoria().equals("") ){
            throw new Exception("P-400","Categoria Incorrecta!");
        }
        if(tblproducto.getPropreciosugerido() <= 0 ){
            throw new Exception("P-400","Precio Incorrecto!");
        }
        if(tblproducto.getProunidadesdisponibles() <= 0 ){
            throw new Exception("P-400","Unidades Incorrectas!");
        }
        if(tblproducto.getProfechaingreso().equals("") ){
            throw new Exception("P-400","Fecha Ingreso Incorrecta!");
        }
        if(tblproducto.getProfechavencimiento().equals("") ){
            throw new Exception("P-400","Fecha Vencimiento Incorrecta!");
        }

        return productoRepository.save(tblproducto);
    }
    @GetMapping("/producto")
    public List<Tblproducto> getAllProducts() {
        this.productos = (List<Tblproducto>) productoRepository.findAll();
        return this.productos;
    }
    @GetMapping("/productoView")
    public List<Tblproducto> getAllProductsView() {
        List<Tblproducto> productosV = (List<Tblproducto>) productoRepository.findAll();
        System.out.println(productosV);
        for(Tblproducto i: productosV){

            Tblproveedor p = proveedorRepository.findById(i.getTblproveedor_proid()).orElseThrow(() -> new Exception("p-400","No se encontro El producto"));
            i.setTblproveedor_proid(p.getPronombre());
            System.out.println(i.getTblproveedor_proid());
        }
        System.out.println(productosV);
        this.productos = productosV;
        System.out.println(this.productos);
        return this.productos;
    }
    @GetMapping("/producto/{id}")
    List<Tblproducto>  getProduct(@PathVariable String id) {
        List<Tblproducto> productos = new ArrayList<>();
        productos.add(productoRepository.findById(id).orElseThrow(() -> new Exception("p-400","No se encontro El producto")));
        return productos;
    }
    @GetMapping("/productos")
    public String getAllProductsReport() throws FileNotFoundException, JRException {
        try {
            InputStream reportStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("Reports/producto.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            File fichero = new File("src/main/resources/logoFJ.png");
            List<Tblproducto> productos = new ArrayList<>();
            //productos = (List<Tblproducto>) productoRepository.findAll();
            System.out.println(this.productos);
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(this.productos);
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("Created By", "Alejandro");
            parameters.put("path", fichero.getAbsolutePath());
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
            byte[] pdf = JasperExportManager.exportReportToPdf(jasperPrint);
            String pdfBase64 = Base64.getEncoder().encodeToString(pdf);
            return pdfBase64;
        }catch (java.lang.Exception e){
            String error = "Error en el reporte: " + e;
            throw new Exception("P-400",error);
        }
    }
}
