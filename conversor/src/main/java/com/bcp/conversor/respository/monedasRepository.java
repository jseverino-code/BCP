/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bcp.conversor.respository;

import com.bcp.conversor.entities.monedas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Julio
 */
@Repository
public interface monedasRepository extends JpaRepository<monedas, Long> {
    monedas findByCodigo(String codigo);
    monedas findByNombre(String nombre);
}