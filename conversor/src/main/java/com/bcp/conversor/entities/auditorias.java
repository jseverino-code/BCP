/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bcp.conversor.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Julio
 */
@Data
@Entity
@Getter
@Setter
@Table(name="auditorias")
public class auditorias implements Serializable{
    
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
     @Column(nullable = false)     
     private Long id;
     
     @Column(nullable = false)
     @Temporal(javax.persistence.TemporalType.DATE)
     private Date fecha;
     
     @Column(nullable = false)
     private String monedaOrigen;
     
     @Column(nullable = false)
     private String monedaDestino;
     
     @Column(nullable = false)
     private BigDecimal cotizacionAnterior;
     
     @Column(nullable = false)
     private BigDecimal cotizacionActual;
     
     @Column(nullable = false)
     private String usuario;
     
     @Column(nullable = false)
     @Temporal(javax.persistence.TemporalType.DATE)
     private Date fechaCambio;
}
