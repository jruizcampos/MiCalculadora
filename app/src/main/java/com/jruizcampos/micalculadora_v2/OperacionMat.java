package com.jruizcampos.micalculadora_v2;

public class OperacionMat {
    private StringBuilder operacion;
    private byte estado;
    private double ope1;
    private double ope2;
    private char OP;
    private double resultado;

    // 0 => primer operando
    // 1 => po y operador
    // 2 => po, op y segundo operando: Único estado ejecutable
    // 3 => po con punto (no terminado)
    // 4 => so con punto (no terminado)

    public OperacionMat(){
        this.operacion = new StringBuilder("0");
        this.estado = 0;
        this.ope1 = 0.0;
        this.ope2 = 0.0;
        this.OP = '?';
        this.resultado = 0.0;
    }

    public String getOperacion(){
        return operacion.toString();
    }

    public void limpiar(){
        this.operacion = new StringBuilder("0");
        this.estado = 0;
        this.ope1 = 0.0;
        this.ope2 = 0.0;
        this.resultado = 0.0;
    }

    public void borrarItemDerecho(){
        String str = operacion.toString();
        char penultimo = '0';

        if(estado == 0){
            if(operacion.length()>1){
                penultimo = str.charAt(str.length()-2);

                if(penultimo=='.'){
                    estado = 3;
                }
                operacion.deleteCharAt(str.length()-1);
            }
            else{
                this.limpiar();
            }
        }
        else if(estado==3){
            operacion.deleteCharAt(str.length()-1);
            estado = 0;
        }
        else if(estado==1){
            operacion.deleteCharAt(str.length()-1);
            estado = 0;
        }
        else if(estado==2){
            penultimo = str.charAt(str.length()-2);

            if(getOp2().length()>2 && penultimo=='.'){
                estado = 4;
            }
            else if(getOp2().length()==1){
                estado = 1;
            }
            operacion.deleteCharAt(str.length()-1);
        }
        else if(estado==4){
            operacion.deleteCharAt(str.length()-1);
            estado = 2;
        }

    }

    public boolean agregarItem(char car){
        boolean result = false;
        String opet = operacion.toString();
        //char carend = opet.charAt(opet.length()-1);

        if(car=='0' || car=='1' || car=='2' || car=='3' || car=='4' || car=='5' || car=='6'
                || car=='7' || car=='8' || car=='9'){
            if(opet.compareTo("0")==0){
                operacion.setLength(0);
                operacion.append(car);
            }
            else{
                operacion.append(car);

                if(estado == 1){
                    estado = 2;
                }
                else if(estado==3){
                    estado = 0;
                }
                else if(estado==4){
                    estado = 2;
                }
            }
            result = true;
        }
        else if (car=='+' || car=='-' || car=='x' || car=='÷'){
            if(estado == 0){
                operacion.append(car);
                estado = 1;
                OP = car;
                result = true;
            }
            else if(estado == 1){
                operacion.setCharAt(opet.length()-1,car);
                OP = car;
                result = true;
            }
            else {
                result = false;
            }
        }
        else if(car=='.'){
            if(estado == 0 && !getOp1().contains(".")){
                operacion.append(car);
                estado = 3;
                result = true;
            }
            else if (estado == 2 && !getOp2().contains(".")){
                operacion.append(car);
                estado = 4;
                result = true;
            }
            else{
                result = false;
            }
        }

        return result;
    }

    public byte getEstado(){
        return estado;
    }

    private String getOp1(){
        String str = operacion.toString();
        String csplit = "";

        if(estado==0){
            return str;
        }
        else if(estado==1 || estado==2){

            if(OP=='+'){
                csplit = "\\+";
            }
            else{
                csplit = String.valueOf(OP);
            }

            return  str.split(csplit)[0];
        }
        else{
            return "";
        }

    }

    private String getOp2(){
        String str = operacion.toString();
        String csplit = "";

        if(estado==2){
            if(OP=='+'){
                csplit = "\\+";
            }
            else{
                csplit = String.valueOf(OP);
            }
            return str.split(csplit)[1];
        }
        else{
            return "";
        }
    }

    public String getResultado(){
        return numberToString(resultado);
    }

    private static String numberToString(double val){

        if(val == Math.floor(val)){ //si val es un entero
            return String.valueOf((int)val);
        }
        else{                       //val es un decimal
            return String.valueOf(val);
        }
    }

    public boolean ejecutarOperacion(){

        if(estado == 2){ // Único estado de operación ejecutable
            ope1 = Double.valueOf(getOp1());
            ope2 = Double.valueOf(getOp2());

            switch (this.OP){
                case '+':
                    resultado = ope1 + ope2;
                    break;
                case '-':
                    resultado = ope1 - ope2;
                    break;
                case 'x':
                    resultado = ope1 * ope2;
                    break;
                case '÷':
                    resultado = ope1 / ope2;
                    break;
            }

            operacion.setLength(0);
            operacion.append(numberToString(resultado));
            estado = 0;

            return true;
        }
        else{
            return false;
        }
    }

}
