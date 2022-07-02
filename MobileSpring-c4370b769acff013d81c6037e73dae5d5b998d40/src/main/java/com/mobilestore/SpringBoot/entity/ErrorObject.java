/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mobilestore.SpringBoot.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author ACER
 */
@Data
@AllArgsConstructor
public class ErrorObject {
    private String errorLine1;
    private String errorLine2;
    private String errorLine3;
    private String errorLine4;
    private String errorLine5;
    private String errorLine6;
    private String errorLine7;
    
    public ErrorObject (){
        
    }
}
