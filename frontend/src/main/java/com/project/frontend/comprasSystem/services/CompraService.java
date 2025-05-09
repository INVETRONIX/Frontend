package com.project.frontend.comprasSystem.services;

import com.project.frontend.comprasSystem.models.Carrito;
import com.project.frontend.comprasSystem.models.Compra;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompraService {
    private final String API_URL = "http://localhost:8080/api/compras";
    private final RestTemplate restTemplate;

    public CompraService() {
        this.restTemplate = new RestTemplate();
    }

    public void realizarCompra(Carrito carrito) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construir el objeto compra en el formato que espera el backend
        Map<String, Object> compraRequest = new HashMap<>();
        
        // TODO: Reemplazar con el ID del usuario logueado cuando se implemente el login
        Map<String, Object> usuario = new HashMap<>();
        usuario.put("id", 1L); // Por ahora usamos un ID fijo
        compraRequest.put("usuario", usuario);

        // Construir los detalles de la compra
        List<Map<String, Object>> detalles = new ArrayList<>();
        for (var item : carrito.getItems()) {
            Map<String, Object> detalle = new HashMap<>();
            
            Map<String, Object> producto = new HashMap<>();
            producto.put("id", item.getProducto().getId());
            
            detalle.put("producto", producto);
            detalle.put("cantidad", item.getCantidad());
            detalles.add(detalle);
        }
        compraRequest.put("detalles", detalles);
        compraRequest.put("total", carrito.getTotal());

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(compraRequest, headers);
        
        try {
            restTemplate.postForObject(API_URL, request, Void.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al realizar la compra: " + e.getMessage());
        }
    }

    // Todas las compras (para admin)
    public List<Compra> obtenerTodasLasCompras() {
        Compra[] compras = restTemplate.getForObject(API_URL, Compra[].class);
        if (compras == null) return java.util.Collections.emptyList();
        return Arrays.asList(compras);
    }

    // Compras por usuario (para cliente)
    public List<Compra> obtenerComprasPorUsuario(Long usuarioId) {
        Compra[] compras = restTemplate.getForObject(API_URL + "/usuario/" + usuarioId, Compra[].class);
        if (compras == null) return java.util.Collections.emptyList();
        return Arrays.asList(compras);
    }

    public void eliminarCompra(Long id) {
        restTemplate.delete(API_URL + "/" + id);
    }

    public void actualizarCompra(Long id, Compra compra) {
        restTemplate.put(API_URL + "/" + id, compra);
    }

    public List<Compra> buscarComprasPorFiltros(Long usuarioId, String fechaInicio, String fechaFin, Double totalMin, Double totalMax) {
        StringBuilder url = new StringBuilder(API_URL + "/filtros?");
        if (usuarioId != null) url.append("usuarioId=").append(usuarioId).append("&");
        if (fechaInicio != null && !fechaInicio.isEmpty()) url.append("fechaInicio=").append(fechaInicio).append("&");
        if (fechaFin != null && !fechaFin.isEmpty()) url.append("fechaFin=").append(fechaFin).append("&");
        if (totalMin != null) url.append("totalMin=").append(totalMin).append("&");
        if (totalMax != null) url.append("totalMax=").append(totalMax);
        Compra[] compras = restTemplate.getForObject(url.toString(), Compra[].class);
        if (compras == null) return java.util.Collections.emptyList();
        return Arrays.asList(compras);
    }

    // Puedes agregar métodos para actualizar y buscar por filtros si lo necesitas
} 