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
public class CambiosUpdateDTO {
    public Long id;
    public BigDecimal tipocambio;
    public String usuario;
}
