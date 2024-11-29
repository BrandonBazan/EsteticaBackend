package com.estetica.ventas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal monto;

    private LocalDate fecha;
    
    private String formaPago;
    
    private String concepto;

    @ManyToOne
    @JoinColumn(name = "trabajador_id", nullable = false)
    @JsonIgnoreProperties("pagos") // Evita ciclos al serializar
    private Trabajador trabajador;

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	
	@PrePersist
    public void setFecha() {
        if (this.fecha == null) {
            this.fecha = LocalDate.now();
        }
    }

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public Trabajador getTrabajador() {
		return trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}

	public Long getId() {
		return id;
	}

    // Getters y Setters
    
    
}


