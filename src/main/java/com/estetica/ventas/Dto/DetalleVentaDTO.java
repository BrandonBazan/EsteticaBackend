package com.estetica.ventas.Dto;

public class DetalleVentaDTO {
    private Long productoId; // O servicioId si es un servicio
    private Long servicioId;
    private Integer cantidad;
    private Long trabajadorId; // ID del trabajador asociado al detalle
    
    private String nombrePersonalizado;
    private Double precioPersonalizado;
    private Double comisionPersonalizada;

    // Getters y setters
    public Long getTrabajadorId() {
        return trabajadorId;
    }

    public void setTrabajadorId(Long trabajadorId) {
        this.trabajadorId = trabajadorId;
    }
    
	public Long getProductoId() {
		return productoId;
	}
	public void setProductoId(Long productoId) {
		this.productoId = productoId;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Long getServicioId() {
		return servicioId;
	}
	public void setServicioId(Long servicioId) {
		this.servicioId = servicioId;
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

	public Double getComisionPersonalizada() {
		return comisionPersonalizada;
	}

	public void setComisionPersonalizada(Double comisionPersonalizada) {
		this.comisionPersonalizada = comisionPersonalizada;
	}
    
    
    
}

