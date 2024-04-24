/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.carloscortez.dto;

import org.carloscortez.model.Cliente;

/**
 *
 * @author Carlos
 */
public class ClienteDTO {
    private static ClienteDTO clienteDTO;
    private Cliente cliente;

    private ClienteDTO() {
    }
    
    public static ClienteDTO getClienteDTO(){
        if(clienteDTO == null){
            clienteDTO = new ClienteDTO();
        }
        return clienteDTO;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    
}
