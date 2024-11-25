package com.estetica.ventas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DetalleVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String categoriaVenta;
 // Nuevos campos para detalles personalizados
    private String nombrePersonalizado;
    private Double precioPersonalizado;
    
    

    @ManyToOne
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;
    
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "servicio_id")
    private Servicio servicio;
    private Double comisionTrabajador;
    private Integer cantidad;
    private Double subtotal;
    
    @Column(nullable = false)
    private Double comision; // Nueva columna para calcular la comisión

    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    private Trabajador trabajador; // Trabajador asociado al detalle
    
	public Venta getVenta() {
		return venta;
	}
	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public Long getId() {
		return id;
	}
	public String getCategoriaVenta() {
		return categoriaVenta;
	}
	public void setCategoriaVenta(String categoriaVenta) {
		this.categoriaVenta = categoriaVenta;
	}
	public Double getComisionTrabajador() {
		return comisionTrabajador;
	}
	public void setComisionTrabajador(Double comisionTrabajador) {
		this.comisionTrabajador = comisionTrabajador;
	}
	public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }
	
    public String getNombrePersonalizado() {
        return nombrePersonalizado;
    }

    public void setNombrePersonalizado(String nombrePersonalizado) {
        this.nombrePersonalizado = nombrePersonalizado;
    }

    public Double getPrecioPersonalizado() {
        return precioPersonalizado;
    }

    public void setPrecioPersonalizado(Double precioPersonalizado) {
        this.precioPersonalizado = precioPersonalizado;
    }
	
    // Getters y Setters
    
    
}

