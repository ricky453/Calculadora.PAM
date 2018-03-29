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
    }

    public void teclearNumero(View v){
        if(resultado != ""){
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

        if(resultado != "") {
            String _display = resultado;
            limpiar();
            display = _display;
        }

        if(operadorActual != ""){
            Log.d("CalcX", ""+display.charAt(display.length()-1));
            if(operador(display.charAt(display.length()-1))){
                display = display.replace(display.charAt(display.length()-1), b.getText().charAt(0));
                operadorActual = b.getText().toString();
                actualizarScreen();
                return;
            }else{
                obtenerResultado();
                display = resultado;
                resultado = "";
            }
            operadorActual = b.getText().toString();
        }
        display += b.getText();
        operadorActual = b.getText().toString();
        actualizarScreen();
    }
    public void limpiar(){
        display="";
        operadorActual="";
        resultado="";
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

    public void extras(String ex){
        int total = display.length();
        switch (ex){
            case ".":
                if(!display.contains(".")) {
                    display = display + ".";
                }else{
                    if(operadorActual!=("")){
                        String [] operacion = display.split(Pattern.quote(operadorActual));
                        System.out.println(operacion.length);
                        if(operacion.length==1 || !String.valueOf(operacion[1]).contains(".")){
                            display = display + ".";
                        }else{
                            return;
                        }
                    }else{
                        return;
                    }
                }
                break;

            /*case "+/-":
                String [] operacion = display.split(Pattern.quote(operadorActual));
                if(operadorActual!=""){
                    if(operacion.length==1){
                        display = display + "-";
                    }else{
                        if(operacion[1].contains("-")==true){
                            display = operacion[0]+operadorActual+operacion[1].substring(1,operacion[1].length());
                        }else{
                            display = operacion[0]+operadorActual+"-"+operacion[1];
                        }
                    }
                }else{
                    String op = display;
                    if(operacion.length==0){
                        display = "-";
                    }else{
                        if(op.contains("-")){
                            display = op.substring(1);
                        }else{
                            display = "-"+op;
                        }
                    }
                }
                break;*/
            case "DEL":
                if(!display.isEmpty()){
                    display = display.substring(0,total-1);
                }
                break;
            default:

        }
    }

    public void teclearExtras(View v){
        Button b = (Button) v;
        extras(b.getText().toString());
        actualizarScreen();
    }


}
