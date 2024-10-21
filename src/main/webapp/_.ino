int ledPin = 13;

void setup() {
  Serial.begin(9600);
  pinMode(ledPin, OUTPUT); // Thiết lập chân LED là đầu ra
}
 
void loop() {
  int value = analogRead(A0);//(value luôn nằm trong khoảng 0-1023)
  digitalWrite(ledPin, HIGH);
  delay(value * 100);
  digitalWrite(ledPin, LOW);
  delay(value * 100);
}