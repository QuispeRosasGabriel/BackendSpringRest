package com.elcajamarquino.springboot.backend.apirest.config.swagger;

public class prueba {
	public static int suma (int n){
		
	
		
		    if (n>1){
		       return suma(n-1) + suma(n-2);  //función recursiva
		    }
		    else if (n==1) {  // caso base
		        return 1;
		    }
		    else if (n==0){  // caso base
		        return 0;
		    }
		    else{ //error
		        System.out.println("Debes ingresar un tamaño mayor o igual a 1");
		        return -1; 
		    }
		
		
		
		
	}
	
	
	
}
