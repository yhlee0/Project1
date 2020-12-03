
#include <Wire.h>
#include "Adafruit_TCS34725.h"
#include <SoftwareSerial.h>
#define BT_RXD 8
#define BT_TXD 7
SoftwareSerial bluetooth(BT_RXD, BT_TXD);
Adafruit_TCS34725 tcs = Adafruit_TCS34725(TCS34725_INTEGRATIONTIME_50MS, TCS34725_GAIN_4X);
void setup()
{Serial.begin(9600);
 bluetooth.begin(9600);
 bluetooth.begin(9600);
  if (tcs.begin()) {
   Serial.println("Found sensor");
  } else {
    Serial.println("No TCS34725 found ... check your connections");
  }
}
void loop(){
  
  uint16_t clear, red, green, blue;
  tcs.getRawData(&red, &green, &blue, &clear);
  int r = map(red, 0, 21504, 0, 1025);
  int g = map(green, 0, 21504, 0, 1025);
  int b = map(blue, 0, 21504, 0, 1025);
  Serial.print("\tR:\t"); Serial.print(r+100);
  Serial.print("\tG:\t"); Serial.print(g+85);
  Serial.print("\tB:\t"); Serial.println(b+50); 

 
  if (Serial.available()) {
   Serial.write(Serial.read());
      bluetooth.print(r+100);
      bluetooth.print(",");

      bluetooth.print(g+85);
      bluetooth.print(",");
      bluetooth.print(b+50);

      bluetooth.println();
  }

delay(100);
}