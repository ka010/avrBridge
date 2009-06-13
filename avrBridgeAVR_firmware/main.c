/* Name: main.c
 * Project: PowerSwitch based on AVR USB driver
 * Author: Christian Starkjohann
 * Creation Date: 2005-01-16
 * Tabsize: 4
 * Copyright: (c) 2005 by OBJECTIVE DEVELOPMENT Software GmbH
 * License: GNU GPL v2 (see License.txt) or proprietary (CommercialLicense.txt)
 * This Revision: $Id: main.c 523 2008-02-15 09:46:40Z cs $
 */

#include <avr/io.h>
#include <avr/interrupt.h>
#include <avr/pgmspace.h>

#include "usbdrv.h"

#include <util/delay.h>

#define CMD_ECHO  0
#define CMD_GET   1
#define CMD_ON    2
#define CMD_OFF   3
#define CMD_PORT  4
#define CMD_PIN   5
#define CMD_DDR   6
#define CMD_GPORT 7
#define CMD_GPIN  8
#define CMD_ADC   9
#define CMD_DAC   10

#define _PORTB 0
#define _PORTC 1
#define _PORTD 2

#define _PINB 0
#define _PINC 1
#define _PIND 2

#define _DDRB 0
#define _DDRC 1
#define _DDRD 2

#define PIN0 0
#define PIN1 1
#define PIN2 2
#define PIN3 3
#define PIN4 4
#define PIN5 5
#define PIN6 6
#define PIN7 7

#define DAC0 0;
#define DAC1 1;
#define DAC2 2;

#define ON 1
#define OFF 0
#define HIGH 1
#define LOW 0

static uchar    actionTimers[8];
unsigned char		PORTMASK = 196;
unsigned char		PINMASK = 60;
unsigned char		STATMASK = 3;

static void timerInterrupt(void)
{
	static uchar    prescaler;
	uchar           i;
	
    if(!prescaler--){
        prescaler = 8;  /* rate = 12M / 1024 * 256 * 9 */
        for(i=0;i<8;i++){
            if(actionTimers[i])
                actionTimers[i]--;
        }
		//computeOutputStatus();
    }
}

void adcInit(void) {
	ADCSRA = (1<<ADEN) | (1<<ADPS1) | (1<<ADPS0);    // Frequenzvorteiler 
	// setzen auf 8 (1) und ADC aktivieren (1)

}

void timer1_init(void)
{
	TCCR1B = 0x00; 																	//stop
	TCNT1H = 0xC1; 																//setup
	TCNT1L = 0x81;
	ICR1H  = 0x00;
	ICR1L  = 0xFF;
	OCR1A= 0x00;													//set initial output value
	OCR1B= 0x00;													//set initial output value
	TCCR1A = 0xA2;
	TCCR1B = 0x19; 																//start Timer
	
	
}

void timer2_init(void) {
    ASSR = 0x00;															// use internal clock 
    TCCR2 |= _BV(WGM21) | _BV(WGM20);										// set PWM mode 
    TCCR2 = (TCCR2 | _BV(COM21)) & ~_BV(COM20);								// do non-inverting PWM on pin OC2A 
	TCNT2 = 0x01;
    OCR2 =0;								    // set initial pulse width to the first sample.
    TCCR2 = (TCCR2 & ~(_BV(CS12) | _BV(CS11))) | _BV(CS10);					// start timer
}

uint16_t getAdcPortPin(uint8_t mux)
{
	uint8_t i;
	uint16_t result;
	
	ADMUX = mux;                      // Kanal waehlen
	ADMUX |= (1<<REFS1) | (1<<REFS0); // interne Referenzspannung nutzen
	
		
	/* nach Aktivieren des ADC wird ein "Dummy-Readout" empfohlen, man liest
     also einen Wert und verwirft diesen, um den ADC "warmlaufen zu lassen" */
	ADCSRA |= (1<<ADSC);              // eine ADC-Wandlung 
	while ( ADCSRA & (1<<ADSC) ) {
		;     // auf Abschluss der Konvertierung warten 
	}
	result = ADCW;  // ADCW muss einmal gelesen werden,
	// sonst wird Ergebnis der nächsten Wandlung
	// nicht übernommen.
	
	/* Eigentliche Messung - Mittelwert aus 4 aufeinanderfolgenden Wandlungen */
	result = 0; 
	for( i=0; i<4; i++ )
	{
		ADCSRA |= (1<<ADSC);            // eine Wandlung "single conversion"
		while ( ADCSRA & (1<<ADSC) ) {
			;   // auf Abschluss der Konvertierung warten
		}
		result += ADCW;		    // Wandlungsergebnisse aufaddieren
	}
	//ADCSRA &= ~(1<<ADEN);             // ADC deaktivieren (2)
	
	result /= 4;                     // Summe durch vier teilen = arithm. Mittelwert
	
	return result;
}

void setDac(pin, val) {
		switch (pin	) {
			case 0:
				OCR1A = val;
				break;
			case 1:
				OCR1B = val;
				break;
			case 2:
				OCR2 = val;
				break;

		}
	
}

void setLEDStatus(uchar status) {
	PORTC = status;
}

int getPortPin(int prt,int pin) {
	int ret=255;
	switch (prt) {
		case _PORTB:
			ret =  (PINB & (1 << pin));
			break;
		case _PORTC:
			ret = (PINC & (1 << pin));
			break;
		case _PORTD:
			ret = (PIND & (1 << pin));			
			break;
	}
	return ret;
}

int setPortDir(uchar val, int prt,int pin) {
	switch (prt) {
		case _PORTB:
			if (val) DDRB |= _BV(pin);
			else DDRB &= ~(_BV(pin)); 
			break;
		case _PORTC:
			if (val==1) DDRC |= _BV(pin);
			else DDRC &= ~(_BV(pin));
			break;
		case _PORTD:
			if (val) DDRD |= _BV(pin);
			else DDRD &= ~(_BV(pin));
			break;
			
	}
	return 0;
}

int setPortPin(uchar val, int prt,int pin) {
	switch (prt) {
		case _PORTB:
			if (val) PORTB |= _BV(pin);
			else PORTB &= ~(_BV(pin)); 
			break;
		case _PORTC:
			if (val) PORTC |= _BV(pin);
			else PORTC &= ~(_BV(pin));
			break;
		case _PORTD:
			if (val) PORTD |= _BV(pin);
			else PORTD &= ~(_BV(pin));
			break;

	}
	return 0;
}

int setPort(uchar val, int prt) {
	switch (prt) {
		case _PORTB:
			PORTB = val;
			break;
		case _PORTC:
			PORTC = val;
			break;
		case _PORTD:
			PORTD = val;
			break;
		}
	return 0;
}


USB_PUBLIC uchar usbFunctionSetup(uchar data[8])
{
	usbRequest_t *rq = (void *)data;   // cast to structured data for parsing
	static uchar replyBuf[4];
	usbMsgPtr = replyBuf;
	uchar tmp=0;	
	unsigned char port=0;
	unsigned char pin =0;
	unsigned char stat =0;
	unsigned char dir =0;
	unsigned   val =0;

	uint16_t adcval =0;
	    switch(rq->bRequest){
			case CMD_ECHO:
				replyBuf[0] = rq->wValue.bytes[0];
				replyBuf[1] = rq->wValue.bytes[1];
				return 2;
			case CMD_GET:
				replyBuf[0] = rq->wValue.bytes[0];
				replyBuf[1] = rq->wValue.bytes[1];
				replyBuf[2] = rq->wValue.bytes[2];
				replyBuf[3] = rq->wValue.bytes[3] | rq->wValue.bytes[2] ;
				return 4;                            // no data block sent or received
			case CMD_ON:
				setLEDStatus(0xff);
				return 2;
			case CMD_OFF:
				setLEDStatus(0x00);
				return 2;
			case CMD_PORT:
				tmp = rq->wValue.bytes[0];
				port = (tmp & PORTMASK)>>6;
				setPort(rq->wValue.bytes[1],port);
				return 2;
			case CMD_PIN:
				tmp = rq->wValue.bytes[0];
				 port = (tmp & PORTMASK)>>6;
				 pin = (tmp & PINMASK)>>2;
				 stat = (tmp & STATMASK);
				setPortPin(stat, port, pin);
				return 2;
			case CMD_DDR:
				tmp = rq->wValue.bytes[0];
				 port = (tmp & PORTMASK)>>6;
				 pin = (tmp & PINMASK)>>2;
				 dir = (tmp & STATMASK);
				setPortDir(dir,port,pin);
				return 2;
			case CMD_GPORT:
				tmp = rq->wValue.bytes[0];
				port = (tmp & PORTMASK)>>6;
				switch(port) {
					case _PORTB:
						replyBuf[0]=PINB;
						break;
					case _PORTC:
						replyBuf[0]=PINC;
						break;
					case _PORTD:
						replyBuf[0]=PIND;
						break;
				}
				return 1;
			case CMD_GPIN:
				tmp = rq->wValue.bytes[0];
				port = (tmp & PORTMASK)>>6;
				pin  = (tmp & PINMASK)>>2;
				replyBuf[0] = getPortPin(port,pin);
				return 1;
			case CMD_ADC:
				tmp = rq->wValue.bytes[0];
				pin = (tmp & PINMASK)>>2;
				adcval = getAdcPortPin(pin);
	
				replyBuf[0] = (adcval >> 8);
				replyBuf[1] = adcval;
				return 2;
			case CMD_DAC:
				tmp = rq->wValue.bytes[0];
				pin = (tmp & PINMASK)>>2;
				val = rq->wValue.bytes[1];
				setDac(pin,val);
				replyBuf[0] = val;
				return 1;

			
	    }
	    return 0;                               // ignore all unk
}

int main(void)
{
	uchar   i;
	cli();
	timer1_init();
	timer2_init();
	adcInit();
	DDRB |= _BV(PB0);
	DDRC = 0xff;
	DDRD = ~(1 << 2);   /* all outputs except PD2 = INT0 */
    PORTD = 0;
    PORTB = 0;          /* no pullups on USB pins */
	
    usbDeviceDisconnect();  /* enforce re-enumeration, do th is while interrupts are disabled! */
	
    i = 0;
    while(--i){         /* fake USB disconnect for > 500 ms */
        _delay_ms(2);
    }
	
    usbDeviceConnect();
    TCCR0 = 5;          /* set prescaler to 1/1024 */
    usbInit();
    sei();
    for(;;){    /* main event loop */
        usbPoll();
        if(TIFR & (1 << TOV0)){
            TIFR |= 1 << TOV0;  /* clear pending flag */
            timerInterrupt();
        }
    }
    return 0;
}

