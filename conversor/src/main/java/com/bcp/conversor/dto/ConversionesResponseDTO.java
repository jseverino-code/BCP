/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bcp.conversor.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Julio
 */
@Getter
@Setter
public class ConversionesResponseDTO {
    public BigDecimal montoOrigen;
    public String monedaOrigen;
    public String monedaDescripcionOrigen;
    public String monedaDestino;
    public String monedaDescripcionDestino;
    public String fecha;
    public BigDecimal cotizacion;
    public BigDecimal montoDestino;
}
