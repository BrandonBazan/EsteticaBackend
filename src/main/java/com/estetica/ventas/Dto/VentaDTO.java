	package com.estetica.ventas.Dto;

import java.time.LocalDate;
import java.util.List;

public class VentaDTO {
    //private Long trabajadorId;
    private Long cajeroId;     // Cajero que registra la venta
    private List<DetalleVentaDTO> detalles;
    private String tipoPago;
    private Double propina;
	
//	public Long getTrabajadorId() {
//		return trabajadorId;
//	}
//	public void setTrabajadorId(Long trabajadorId) {
//		this.trabajadorId = trabajadorId;
//	}
	public List<DetalleVentaDTO> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleVentaDTO> detalles) {
		this.detalles = detalles;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public Double getPropina() {
		return propina;
	}
	public void setPropina(Double propina) {
		this.propina = propina;
	}
	public Long getCajeroId() {
		return cajeroId;
	}
	public void setCajeroId(Long cajeroId) {
		this.cajeroId = cajeroId;
	}
	
	
    
	
    
}
