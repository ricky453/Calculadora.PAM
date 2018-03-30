package com.calculadora.ricky.calculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Calculadora extends AppCompatActivity {
    private TextView screen;
    private TextView screen2;
    private String display = "";
    private String operadorActual = "";
    private String resultado = "";
    boolean signo = false;
    private String operation1;
    private String res;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        screen = (TextView)findViewById(R.id.lblDisplay);
        screen.setText(display);
        screen2 = (TextView)findViewById(R.id.lblDisplay2);
    }

    public void actualizarScreen(){

        screen2.setText(display);
        res="";
    }

    public void teclearNumero(View v){//Cambiado por screen antes resultado
        if(screen.getText().toString() != ""){
            limpiar();
            actualizarScreen();
        }
        Button b = (Button) v;
        display += b.getText();
        actualizarScreen();
    }

    private boolean operador(char op){
        switch (op) {
            case '+':
            case '-':
            case '*':
            case '/': return true;
            default: return false;
            }
        }

    public  void teclearOperacion(View v){
        Button b = (Button) v;

        if (display== "")return;

        if(screen.getText().toString() != "") {//Cambiado por screen, antes resultado
            String _display = resultado;
            limpiar();
            display = _display;
        }
        System.out.println("Aqui compruebo si el operador es distinto a nada: "+operadorActual);
        if(operadorActual != ""){
            System.out.println("EL operador Actual es distinto a nada, es decir hay un operador");
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(operador(display.charAt(display.length()-1))){

                System.out.println("Display.length "+(display.length()-1));
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                operadorActual = b.getText().toString();
                actualizarScreen();
                return;
            }else{
                obtenerResultado();
                display = resultado;
                resultado = "";
            }
            operadorActual   = b.getText().toString();
        }
        System.out.println("Esto va si o si " + b.getText() +" el ex display era " + display);
        display += b.getText();
        System.out.println("Hoy el display es " + display);
        operadorActual = b.getText().toString();
        actualizarScreen();
    }
    public void limpiar(){
        display="";
        operadorActual="";
        resultado="";
        res="";
        screen.setText(display);
    }

    public double operar(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a)+Double.valueOf(b);
            case "-": return Double.valueOf(a)-Double.valueOf(b);
            case "*": return Double.valueOf(a)*Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a)/Double.valueOf(b);
            }catch (Exception e){
                Log.e("Calc", e.getMessage());

            }
                default: return -1;
        }


    }

    private boolean obtenerResultado(){
        if(operadorActual=="")return false;
        String [] operacion = display.split(Pattern.quote(operadorActual));
        if (operacion.length < 2) return false;
        System.out.println("El operador actual es "+operadorActual);
        System.out.println("EL display tiene "+display.substring(0,1).toString());
        System.out.println(display.substring(0,1).toString().equals("-"));
        System.out.println(operadorActual.contains("-"));
        if(display.substring(0,1).toString().equals("-") && operadorActual.contains("-")){
            res = display.substring(1,display.length());
            operadorActual="-";
            String []pun = res.split(Pattern.quote(operadorActual));
            //operacion[0]=pun[0].toString().substring(1,pun[0].length());
            operacion[0]="-"+pun[0];
            operacion[1]=pun[1];
            System.out.println("Esto es asi "+pun[0]+pun[1]);
        }
        System.out.println("ESTO MANDARE "+operacion[0] +" "+ operacion[1]+" "+ operadorActual);
        resultado = String.valueOf(operar(operacion[0], operacion[1], operadorActual));
        return true;
    }

    public void teclearIgual(View v){
        if(display=="")return;
        if(!obtenerResultado())return;
        //screen.setText(display + "\n" + String.valueOf(resultado));
        screen2.setText(display);
        screen.setText(String.valueOf(resultado));
    }

    public void teclearLimpiar(View v){
        limpiar();
        actualizarScreen();
    }


    public void teclearExtras(View v){
        String [] operacion = display.split(Pattern.quote(operadorActual));

        if(operadorActual!=""){
            if(operacion.length==1){return;}
            else{
                if(display.substring(0,1).contains("-")&&operadorActual.contains("-")){
                    operation1 = display.substring(1, display.length());
                    String[]pepe = operation1.split(Pattern.quote(operadorActual));
                    operacion[0]="-"+pepe[0].toString();
                    operacion[1]=pepe[1].toString();

                }
                double convertir = Double.parseDouble(operacion[1].toString())*(-1);
                System.out.println("AL presionar +/-: "+convertir +"<- NUMERO CONVERTIR " + operacion[0]+operadorActual+String.valueOf(convertir));
                display = operacion[0]+operadorActual+String.valueOf(convertir);
                if(String.valueOf(convertir).contains("-")){
                    double volver = convertir*(-1);
                    System.out.println(volver + " MIERDA ESTE ES VOLVER");
                    if(operacion[0].contains("-")){
                        res = operacion[0].substring(1,operacion[0].length())+operadorActual+String.valueOf(volver);
                        System.out.println("Aqui el res (SI OPERACION 0 CONTIENE -) es: "+res);
                        if(operadorActual.contains("-")){
                            operadorActual="+";
                            operacion[1]=""+volver;
                            display = operacion[0]+operadorActual+operacion[1];
                        }

                        //comprobarOperacion();
                        signo=true;

                        if(operadorActual=="-"){
                            operadorActual="+";
                            operacion[1]=""+volver;
                        }

                    }else{
                        signo=false;
                        res = operacion[0]+operadorActual+String.valueOf(volver);
                        System.out.println("Aqui el res (SI OPERACION 0 NO CONTIENE -) es: "+res);
                        //comprobarOperacion();
                        if(operadorActual=="-"){
                            operadorActual="+";
                            operacion[1]=""+volver;
                            display = operacion[0]+operadorActual+operacion[1];
                            System.out.println("La op seria: " +operacion[0]+operadorActual+operacion[1]);
                        }
                    }
                }else{
                    if(operacion[0].contains("-")){
                        res = operacion[0].substring(1,operacion[0].length())+operadorActual+operacion[1];
                        System.out.println("Aqui el res op0 contiene -, es: "+res);
                        //comprobarOperacion();
                    }else{
                        res = operacion[0]+operadorActual+operacion[1];
                        System.out.println("Aqui el res op0 no contiene - es: "+res);
                        //comprobarOperacion();
                    }
                }
            }
        }else{
            if(display.isEmpty()){return;}
            else{
                double convertir2 = Double.parseDouble(display.toString())*(-1);
                System.out.println(String.valueOf(convertir2));
                display = String.valueOf(convertir2);
            }
        }
        actualizarScreen();
    }

    public void teclearDEL(View v){
        int total = display.length();
        if(!display.isEmpty()){
            if(!screen.getText().toString().isEmpty()){
                display = screen.getText().toString();
                screen.setText("");
                actualizarScreen();
                comprobarOperacion();

            }else{
                display = display.substring(0,total-1);
                System.out.println(display + " lo de screen lo paso a display " + screen2.getText() +" que sera lo que hay ");
                actualizarScreen();
                comprobarOperacion();
            }
        }

    }

    public void teclearPunto(View v){
        if(!display.contains(".")) {
            display = display + ".";
        }else{
            if(operadorActual!=("")){
                String [] operacion = display.split(Pattern.quote(operadorActual));
                System.out.println(operacion.length);
                if(operacion.length==2 && !String.valueOf(operacion[1]).contains(".")){
                    display = display + ".";
                }else{
                    return;
                }
            }else{
                return;
            }
        }
        actualizarScreen();

    }

    public void comprobarOperacion() {
        System.out.println(operadorActual + " ANTIGUO");
        if (!screen2.getText().toString().contains("+") && !screen2.getText().toString().contains("-") && !screen2.getText().toString().contains("/") && !screen2.getText().toString().contains("*")) {
                operadorActual = "";
                System.out.println(operadorActual + " NUEVO");

        }
    }
}
