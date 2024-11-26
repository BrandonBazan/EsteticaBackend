package com.estetica.ventas.model;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime fecha;
    
    @ManyToOne
    @JoinColumn(name = "cajero_id", nullable = false) // Relación con Cajero
    private Cajero cajero;
    
//    @ManyToOne
//    @JoinColumn(name = "trabajador_id", nullable = false)
//    private Trabajador trabajador;
    
    private Double total;
    
    private Double propina; // Nuevo campo para la propina
    
    @Enumerated(EnumType.STRING) // Almacena los nombres como texto en la base de datos
    @Column(nullable = false)
    private TipoPago tipoPago; // Nuevo campo

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

//	public Trabajador getTrabajador() {
//		return trabajador;
//	}
//
//	public void setTrabajador(Trabajador trabajador) {
//		this.trabajador = trabajador;
//	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Long getId() {
		return id;
	}

	public TipoPago getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }
    
    
	
    
    
    // Getters y Setters
	
	public Cajero getCajero() {
		return cajero;
	}

	public void setCajero(Cajero cajero) {
		this.cajero = cajero;
	}

	public Double getPropina() {
		return propina;
	}

	public void setPropina(Double propina) {
		this.propina = propina;
	}



	public enum TipoPago {
	    EFECTIVO,
	    TARJETA,
	    TRANSFERENCIA,
	    OTRO
	}
    
}

