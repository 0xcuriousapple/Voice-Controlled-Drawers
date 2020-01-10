#include<avr/io.h>
#include<stdlib.h>
#include<compat/deprecated.h>
#include<util/delay.h>
#include<avr/eeprom.h>
#include<io.h>
#include <inttypes.h>

//This function is used to initialize the USART
//at a given UBRR value
void SerialBegin(long baud_rate)
{

   //Set Baud rate
   uint16_t ubrr_value = (1000000/baud_rate) - 1;
   UBRRL = ubrr_value;
   UBRRH = (ubrr_value>>8);

   ///Set Frame Format
   //Asynchronous mode
   //No Parity
   //1 StopBit
   //char size 8


   UCSRC=(1<<URSEL)|(3<<UCSZ0);


   //Enable The receiver and transmitter

   UCSRB=(1<<RXEN)|(1<<TXEN);


}

//-----------------------------------------------------
//USART functions

/* Functions for Serial communication(UART) .for using CP2102 too.
Call SerialBegin(long baudrate) to initialise the UART.
Call SerialWriteChar(char x) to print single character.
Call SerialWriteString(char x[]) to print string. Pass string in double inverted commas.
Call SerialWriteInt(int x) to print integer(signed only)
Call SerialWriteLong(unsigned long int x) to print unsigned long integers.

*/

//This function is used to read the available data
//from USART. This function will wait untill data is
//available.
char SerialReadChar()
{
   //Wait untill a data is available

   while(!(UCSRA & (1<<RXC)))
   {
		;
      //Do nothing
   }
   //Data recieved in USART
   return UDR;
}


//This fuction prints a single character.
void SerialWriteChar(char data)
{
   //Wait untill the transmitter is ready

   while(!(UCSRA & (1<<UDRE)))
   {
      //Do nothing
   }

   //Now write the data to USART buffer

   UDR=data;
}


//This function prints a string. Pass in double inverted commas or a character array.
void SerialWriteString(char string[])
{
	for (int i=0; i<strlen(string); i++) 
	{
			SerialWriteChar(string[i]);
	}
		
		
}

//This function prints a signed integer.
void SerialWriteInt(int data)
{
	int i = 0, count = 0;
	
	if(data<0)
	{
		SerialWriteChar('-');
		data*=-1;
	}
	
	int x = data;
	
	//count number of digits
	while(x)
	{
		count++;
		x = x/10;
	}
	
   int temp[count];
   
   //store each digit in temp array
   while(data)
   {
	temp[i] = data%10;
	data = data/10;
	i++;
   }
   
   
   //print each digit by typecasting to character
   for(i = count-1; i>=0 ; i--)
   {
		SerialWriteChar(temp[i]+'0');
   }

  
}

//This function prints an unsigned long int.
void SerialWriteLong(unsigned long int data)
{
	int i = 0, count = 0;	
	unsigned long x = data;
	
	//count number of digits
	while(x)
	{
		count++;
		x = x/10;
	}
	
   int temp[count];
   
   //store each digit in temp array
   while(data)
   {
	temp[i] = data%10;
	data = data/10;
	i++;
   }
   
   
   //print each digit by typecasting to character
   for(i = count-1; i>=0 ; i--)
   {
		SerialWriteChar(temp[i]+'0');
   }
}
//----------------------------------------------------


void main()
{
DDRC=0b11111100;
unsigned char data;
SerialBegin(9600);
while(1)
		{
			data = SerialReadChar();

			switch(data)
			{
				case 'a' :
					PORTC=0b10000000;
					_delay_ms(1000);
					PORTC=0x00;
					break;
				case 'b' :
					PORTC=0b01000000;
					_delay_ms(1000);
					PORTC=0x00;

					break;
				case 'c':
					PORTC=0b00100000;
					_delay_ms(1000);
					PORTC=0x00;

					break;
				case 'd' :
					PORTC=0b00010000;
					_delay_ms(1000);
					PORTC=0x00;

					break;
				default :
				 {
					_delay_ms(100);
					LED = 0x00;
				 }
			}
			_delay_ms(500);
			
	}












}