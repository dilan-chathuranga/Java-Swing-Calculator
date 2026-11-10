import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class my_calculator {

    JFrame frame;
    JButton button;
    JPanel keyJPanel;
    JTextField screen;
    Dimension screenSize;
    String current;

    public static void main(String[] args) {
        my_calculator calculator = new my_calculator();
        calculator.createFreame();
        calculator.createScreen();
        calculator.createPanel();
        calculator.setVisible();

        
    }

    public void createFreame(){
        frame = new JFrame("Java Calculator");
        frame.setSize(600,800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        

    }

    public void createScreen(){
        screen = new JTextField();
        screen.setFont(new Font("Arial",Font.BOLD,50));
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setEditable(false);
        screen.setBackground(Color.BLACK);
        screen.setForeground(Color.WHITE);
        screen.setBorder(null);

        screenSize = new Dimension(500,150);
        screen.setPreferredSize(screenSize);
        
        frame.add(screen,BorderLayout.NORTH);

    }

    public void createPanel(){
        keyJPanel = new JPanel();
        keyJPanel.setLayout(new GridLayout(5, 4, 20, 20));
        keyJPanel.setBackground(Color.black);
        keyJPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        createButtons();
        
        frame.add(keyJPanel,BorderLayout.CENTER);
    }
    
    
    
    public void createButtons(){
        
        List <String> number = new ArrayList<>(List.of(
            "AC","←","%","÷",
            "7","8","9","x",
            "4","5","6","-",
            "1","2","3","+",
            "↶","0",".","="));

        for (String text: number){
            button = new JButton(text); 
            button.setBorder(null);
            buttonColor();
            button.addActionListener(e ->{
                current = screen.getText();
                if (text.equals("AC")){
                    screen.setText("");
                }
                
                else if (text.equals("+")||
                    text.equals("-")||
                    text.equals("x")||
                    text.equals("÷")||
                    text.equals("%"))
                    {
                        char last = current.charAt(current.length()-1);
                        if (current.isEmpty()){
                            return;
                        }
                        else if (last == '+'||last == '-'||last == 'x'||last == '÷'||last == '%'){
                            return;
                        
                        }
                        else{
                            screen.setText(current + text);
                        }
                }

                else if (text.equals("←")){
                    if (current.isEmpty()){
                        return;
                    }
                    else{
                        screen.setText(current.substring(0, current.length() - 1)); //substring --> index 0 ඉඳන් last character ඇතුළත් නොකර characters ගන්න.
                    }
                }

                else if (text.equals("0")){

                    if (current.isEmpty()){
                        return;
                    }
                    screen.setText(current + text);
                }
                
                else if (text.equals(".")) {

                    if (current.isEmpty()) {
                        screen.setText("0.");
                    }

                    else {
                        char last = current.charAt(current.length() - 1);

                        if (last == '+' || last == '-' ||
                            last == 'x' || last == '÷' ||
                            last == '%') {

                            screen.setText(current + "0.");
                        }

                        else {
                            // Last number එකේ decimal point එක check කරනවා
                            int lastOperator = Math.max(
                                Math.max(current.lastIndexOf('+'), current.lastIndexOf('-')),
                                Math.max(current.lastIndexOf('x'),Math.max(current.lastIndexOf('÷'),current.lastIndexOf('%')))
                            );

                            String currentNumber = current.substring(lastOperator + 1);

                            if (currentNumber.contains(".")) {
                                return ;
                            }
                            else {
                                screen.setText(current + text);
                            }
                        }
                    }
                }
                else if(text.equals("=")){
                    current = screen.getText();
                    double answer = calculate(current);
                    screen.setText(String.valueOf(answer));
                    
                }
                else if (text.equals("↶")){
                    // open history tab
                }


                else{
                    screen.setText(current + text);
                }

            });
            
            keyJPanel.add(button);

        }
    }

    public double calculate(String expression){ //current = screen.getText();
        String number = "";
        double result = 0;
        List <String> numberList = new ArrayList<>();
        List <String> operationList = new ArrayList<>();
        for (int i= 0; i < expression.length(); i++){
            if (expression.charAt(i) == '+' ||
                expression.charAt(i) == 'x' ||
                expression.charAt(i) == '÷' ||
                expression.charAt(i) == '%' ||
                expression.charAt(i) == '-' 
                ){
                    operationList.add(String.valueOf(expression.charAt(i)));
                    numberList.add(number);
                    number = "";
                }
            else {
                
                number = number + String.valueOf(expression.charAt(i));
            }
        }
        numberList.add(number);
        
        for (int i = 0; i < operationList.size(); i++){
            //numberList    = ["12", "5", "3", "2","12", "5", "3"]
            //operationList = ["+", "x", "-","+", "x", "-"]
            String operation = operationList.get(i);
            if (operation.equals("x")||
                operation.equals("÷")||
                operation.equals("%")){

                    double number1 = Double.parseDouble(numberList.get(i));
                    double number2 = Double.parseDouble(numberList.get(i+1));
                    if (operation.equals("x")) {
                         result = number1 * number2;
                    }
                    else if (operation.equals("÷")) {
                         result = number1 / number2;
                    }
                    else if (operation.equals("%")) {
                         result = number1 % number2;
                    }
                numberList.set(i,String.valueOf(result));
                numberList.remove(i+1);
                operationList.remove(i);
                i--;
            }
            
            
            
        }
        for (int i = 0; i < operationList.size(); i++){
            //numberList    = ["10", "5", "3"]
            //operationList = ["+", "-"]
            String operation = operationList.get(i);
            if (operation.equals("+")||
                operation.equals("-")){
                    double number1 = Double.parseDouble(numberList.get(i));
                    double number2 = Double.parseDouble(numberList.get(i+1));
                    if (operation.equals("+")) {
                        result = number1 + number2;
                    }
                    else if (operation.equals("-")) {
                        result = number1 - number2;
                    }
                numberList.set(i,String.valueOf(result));
                numberList.remove(i+1);
                operationList.remove(i);
                i--;
            }
            
        }
        if (operationList.isEmpty()) {
            return Double.parseDouble(numberList.get(0));
        }
    
        
        return result;
    }

    public void buttonColor(){
        if (button.getText().equals("AC")||
            button.getText().equals("←")||
            button.getText().equals("%")){

                button.setFont(new Font("Arial",Font.BOLD,60));
                button.setBackground(Color.decode("#A5A5A5"));
                button.setForeground(Color.WHITE);
            
        }
        else if(button.getText().equals("÷")||
                button.getText().equals("x")||
                button.getText().equals("-")||
                button.getText().equals("+")||
                button.getText().equals("=")){

                    button.setFont(new Font("Arial",Font.BOLD,60));
                    button.setBackground(Color.decode("#FF9500"));
                    button.setForeground(Color.WHITE);
                }
        else{
            button.setFont(new Font("",Font.BOLD,60));
            button.setBackground(Color.decode("#333333"));
            button.setForeground(Color.WHITE);
        }
    }
    
    public void setVisible(){
        frame.setVisible(true);
    }

}