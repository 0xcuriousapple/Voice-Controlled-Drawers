#include <ESP8266WiFi.h>

const char* ssid = "Mayank";
const char* password = "12345678";

WiFiServer server(80);

// setup fuction runs only once
void setup() {
  Serial.begin(9600);
  
  // Connect to WiFi network
  Serial.println();
  Serial.println();
  Serial.print("Connecting to ");
  Serial.println(ssid);

  WiFi.begin(ssid, password);

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");

  // Start the server
  server.begin();
  Serial.println("Server started");

  // Print the IP address
  Serial.println(WiFi.localIP());
}

// the loop function runs over and over again forever
void loop() {
  // Check if a client has connected
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  
  // Wait until the client sends some data
  //Serial.println("new client");
  while (!client.available()) {
    delay(1);
  }
  
  // Read the first line of the request
  String req = client.readStringUntil('\r');
  String req_extracted = req.substring(5, 12);
  char data = req[5];
  client.flush();
  if (req_extracted != "favicon")
  {
    Serial.println(data);
    String s = "HTTP/1.1 200 OK\r\n\r Client Data Recieved \n\r\n<!DOCTYPE HTML>\r\n<html>\r\n";
    s += "Request Recieved";
    s += "</html>\n";
    client.print(s);
    delay(1);
  }
}

