#include <LiquidCrystal.h>

const int pingPin = 7;
String type = "";
String code = "";
LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

void setup() {
   //Author: Henry Mound
   Serial.begin(115200);
   
   lcd.begin(16, 2);
   setScreen("Barlow Robotics, Team 4572");
}

void loop() {
  
  if(Serial.available() > 0){
    
    char c = Serial.read();
    if(c == '!'){
        
          String codeTester = code;
          codeTester.toUpperCase();
          
          if(codeTester.equals("LCD")){
            type = "LCD";
            codeTester = "";
            code = "";
          }
          if(type.equals("LCD") && codeTester.length() >= 1){
            setScreen(codeTester);
            codeTester = "";
            type = "";
            code = "";
          }
            
      }else{
      code += c;
    }
  
  }
  
  long duration, inches, cm;

  pinMode(pingPin, OUTPUT);
  digitalWrite(pingPin, LOW);
  delayMicroseconds(2);
  digitalWrite(pingPin, HIGH);
  delayMicroseconds(5);
  digitalWrite(pingPin, LOW);

  pinMode(pingPin, INPUT);
  duration = pulseIn(pingPin, HIGH);

  inches = microsecondsToInches(duration);
  cm = microsecondsToCentimeters(duration);

  Serial.print(inches);
  Serial.println();
 
  delay(100);
}

void setScreen(String text){

  if(text.length() >= 16){
    
  String line1 = text.substring(0, 18);
  String line2 = text.substring(16);
  lcd.begin(16, 2); 
  lcd.print(line1);
  lcd.setCursor(0, 2);
  lcd.print(line2);
  
  }else{
  lcd.begin(16, 2); 
  lcd.print(text);
   }

}

long microsecondsToInches(long microseconds){
  return microseconds / 74 / 2;
}

long microsecondsToCentimeters(long microseconds){
  return microseconds / 29 / 2;
}


