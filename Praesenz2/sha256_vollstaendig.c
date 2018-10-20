//Fachhochschule S�dwestfalen
//Fachbereich Elektrotechnik und Informationstechnik
//Prof. Dr. A. Meyer
//Wahlpflichtmodul IT-Sicherheit im Verbundstudiengang Elektronische Systeme
//2. Pr�senzveranstaltung (05.10.2017)



//Unvollst�ndige Implementierung des SHA-256

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//Ben�tigten 32-Bit Datentyp entsprechend dem Zielsystem setzen
#define uint32_t unsigned int

//LITTLE_ENDIAN wird gesetzt, falls die Bytereihenfolge des Zielsystems Little Endian ist
//Dann muss die Bytereihenfolge der Nachricht umgekehrt werden, damit diese
//im Speicher konsistent zu den Big Endian-Konstanten der SHA-256-Spezifikation liegt.
//#define LITTLE_ENDIAN 

//Rechtsrotation des 32-Bit-Werts c um n Bitpositionen
#define ROT_RIGHT(c,n) (((c)>>(n)) | ((c)<<(32-(n))))
//Rechtsshift des 32-Bit-Werts c um n Bitpositionen
#define SHIFT_RIGHT(c,n) ((c)>>(n))

//registers
#define a registers[0]
#define b registers[1]
#define c registers[2]
#define d registers[3]
#define e registers[4]
#define f registers[5]
#define g registers[6]
#define h registers[7]



unsigned char * padding(unsigned char * ptr_string, int *nblocks, int len);

void sha256(uint32_t *hashes, unsigned char *message, int len);


//Definitions
uint32_t ch(uint32_t x, uint32_t y, uint32_t z);
uint32_t maj(uint32_t x, uint32_t y, uint32_t z);
uint32_t sum0(uint32_t x);
uint32_t sum1(uint32_t x);
uint32_t o0(uint32_t x);
uint32_t o1(uint32_t x);

//Vollstaendige main-Funktion
int main(void)
{
	uint32_t hashes[8];
	// unsigned char message[]="abc";
	unsigned char message[]="abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq";
	int i, len=strlen((const char *)message);

	//Berechnung des Hashwerts
	sha256(hashes,message,len);

	//Ausgabe des Haswerts
	printf("The hash of \"%s\" is: \n", message);
	for(i=0;i<8;i++)
	{
		printf("%x ",hashes[i]);
	}
	printf("\n");

	getchar();
	return 0;
}

// swap byte endian
uint32_t swapE32(uint32_t val) {
    uint32_t x = val;
    x = (x & 0xffff0000) >> 16 | (x & 0x0000ffff) << 16;
    x = (x & 0xff00ff00) >>  8 | (x & 0x00ff00ff) <<  8;
    return x;
}

//Unvollstaendige Funktion zur Berechnung des SHA-256-Hashwerts
//Eingaben:
//message: Nachricht, deren Hash-Wert berechnet wird
//len: L�nge der Nachricht in Byte
//R�ckgabe:
//hashes: SHA-256-Hash-Wert von message
void sha256(uint32_t *hashes, unsigned char *message, int len)
{
	//Wird sp�ter belegt mit Startadresse der gepaddeten Nachricht
	unsigned char *ptr_string;

	//Anzahl der zu verarbeitenden Nachrichtenbl�cke
	int nblocks; 
	
	//Konstanten, die zur Berechnung der Rundenfunktion der Kompressionsfunktion ben�tigt werden
	uint32_t k[64]=
	{
		0x428a2f98,0x71374491,0xb5c0fbcf,0xe9b5dba5,0x3956c25b,0x59f111f1,0x923f82a4,0xab1c5ed5,
		0xd807aa98,0x12835b01,0x243185be,0x550c7dc3,0x72be5d74,0x80deb1fe,0x9bdc06a7,0xc19bf174,
		0xe49b69c1,0xefbe4786,0x0fc19dc6,0x240ca1cc,0x2de92c6f,0x4a7484aa,0x5cb0a9dc,0x76f988da,
		0x983e5152,0xa831c66d,0xb00327c8,0xbf597fc7,0xc6e00bf3,0xd5a79147,0x06ca6351,0x14292967,
		0x27b70a85,0x2e1b2138,0x4d2c6dfc,0x53380d13,0x650a7354,0x766a0abb,0x81c2c92e,0x92722c85,
		0xa2bfe8a1,0xa81a664b,0xc24b8b70,0xc76c51a3,0xd192e819,0xd6990624,0xf40e3585,0x106aa070,
		0x19a4c116,0x1e376c08,0x2748774c,0x34b0bcb5,0x391c0cb3,0x4ed8aa4a,0x5b9cca4f,0x682e6ff3,
		0x748f82ee,0x78a5636f,0x84c87814,0x8cc70208,0x90befffa,0xa4506ceb,0xbef9a3f7,0xc67178f2
	}; 

	//Hash-Werte mit IV initialisieren
	hashes[0]=0x6a09e667;
	hashes[1]=0xbb67ae85;
	hashes[2]=0x3c6ef372;
	hashes[3]=0xa54ff53a;
	hashes[4]=0x510e527f;
	hashes[5]=0x9b05688c;
	hashes[6]=0x1f83d9ab;
	hashes[7]=0x5be0cd19;

	//Padding der Nachricht
	if((ptr_string=padding(message,&nblocks,len))==NULL)
	{
		printf("Fehler beim Padding!\n");
		getchar();
		return;
	}

	//////////////////Hier bitte Ihren Code zur Berechnung des Hashwerts einfuegen//////////////////

	//Parse the message into N 512-bit blocks
	printf("Parse the message into N 512-bit blocks: \n");
	uint32_t m[nblocks][16];
	int messagei = 0;
	for(int iblock = 0; iblock < nblocks; iblock++) {
		for(int ijblock = 0; ijblock < 16; ijblock++) {
			m[iblock][ijblock] = 
				(ptr_string[messagei] << 24) + 
				(ptr_string[messagei+1] << 16) + 
				(ptr_string[messagei+2] << 8) + 
				(ptr_string[messagei+3]);

			printf("m[%2d][%2d] = ", iblock, ijblock);

			printf("\"%c", ptr_string[messagei]);
			printf("%c", ptr_string[messagei+1]);
			printf("%c", ptr_string[messagei+2]);
			printf("%c\"", ptr_string[messagei+3]);

			printf(" -> %08x \n", m[iblock][ijblock]);

			messagei += 4;
		}
	} 
		
		
	printf("\n\nCalculating Hash: \n");
	for (int iblock = 0; iblock < nblocks; iblock++) {
		//calculate w
		uint32_t w[64];
		for (int i = 0; i < 16; i ++) {
			w[i] = m[iblock][i];
		}
		for (int i = 16; i < 64; i ++) {
			w[i] = o1(w[i-2])+w[i-7]+o0(w[i-15])+w[i-16];
		}



		// Initialize registers a-h with the (i-1)st intermediate hash value
		uint32_t registers[8];
		for (int j = 0; j < 8; j++) {
			registers[j] = hashes[j];
		}

		printf("init\t");
		for (int l = 0; l < 8; l++) {
			printf("%08x ",registers[l]);
		}
		printf("\n");
		
		for (int j = 0; j < 64; j++) {



			uint32_t t1 = h + sum1(e) + ch(e,f,g) + k[j] + w[j];
			uint32_t t2 = sum0(a) + maj(a,b,c);
			// printf("uint32_t t1 = %08x + %08x + %08x + %08x + %08x; = %08x \n", h, sum1(e), ch(e,f,g), k[j], w[j], t1);
			// printf("uint32_t t2 = %08x + %08x; = %08x \n\n", sum0(a), maj(a,b,c), t2);
			h = g;
			g = f;
			f = e;
			e = d + t1;
			d = c;
			c = b;
			b = a;
			a = t1 + t2;
			
			printf("t = %2d\t", j);
			for (int l = 0; l < 8; l++) {
				printf("%08x ",registers[l]);
			}
			printf("\n");
		}
		
		
		// Compute the ith intermediate hash value H(i)
		
		printf("\nBlock %d has been processed. The values of H are: \n", iblock);
		for (int j = 0; j < 8; j++) {
			printf("H%d = %08x + %08x = %08x \n", j, hashes[j], registers[j], hashes[j] + registers[j]);
			hashes[j] += registers[j];
		}
		printf("\n\n");
	}
	
	//////////////////Ende eingefuegter Code////////////////////////////////////////////////////////

	//Dynamisch allokierten Speicher wieder freigeben
	free(ptr_string);

	return;
}

uint32_t ch(uint32_t x, uint32_t y, uint32_t z) {
	return (x&y)^(~x&z);
}
uint32_t maj(uint32_t x, uint32_t y, uint32_t z) {
	return (x&y)^(x&z)^(y&z);
}
uint32_t sum0(uint32_t x) {
	return ROT_RIGHT(x,2)^ROT_RIGHT(x,13)^ROT_RIGHT(x,22);
}
uint32_t sum1(uint32_t x) {
	return ROT_RIGHT(x,6)^ROT_RIGHT(x,11)^ROT_RIGHT(x,25);
}
uint32_t o0(uint32_t x) {
	return ROT_RIGHT(x,7)^ROT_RIGHT(x,18)^SHIFT_RIGHT(x,3);
}
uint32_t o1(uint32_t x) {
	return ROT_RIGHT(x,17)^ROT_RIGHT(x,19)^SHIFT_RIGHT(x,10);
}


//Vollstaendige Funktion zum Padden der Nachricht vor der Verarbeitung durch die Hash-Funktion
//Eingaben:
//ptr_string: Adresse, an der die Nachricht gespeichert ist
//len: L�nge der Nachricht in Byte
//R�ckgaben:
//nblocks: Anzahl der Nachrichtenbl�cke nach dem Padding
//R�ckgabewert per return: Adresse, an der der gepaddete Nachrichtenblock gespeichert ist
unsigned char * padding(unsigned char * ptr_string, int *nblocks, int len)
{
	//Variablen f�r L�ngen
	int padded_len;
	long long int len_in_bits;

	//Iteratoren
	int byte_ctr, iter;

	//Variable f�r die Startadresse des dynamisch allokierten gepaddeten Strings
	unsigned char *padded_string;

	//Ermitteln der L�nge der Nachricht nach dem Padding
	padded_len=len+(64-len%64);
	if(padded_len-len<=8) padded_len+=64;

	//Ermitteln der Anzahl der zu verarbeitenden Nachrichtenbl�cke
	//Ein Block ist 64 Byte = 512 Bit lang
	*nblocks=padded_len/64;

	//Dynamischen Speicher f�r die Nachricht mit Padding reservieren
	padded_string=(unsigned char *)calloc(padded_len,1);
	if(padded_string==NULL) return padded_string;

	//Nachricht in dynamischen Speicher kopieren
	memcpy(padded_string,ptr_string,len);

	//Padden
	//1 anh�ngen und mit Nullen auff�llen, dabei noch 8 Byte Platz lassen
	padded_string[len]=0x80;
	iter=len;
	do
	{
		iter++;
		padded_string[iter]=0;
	}
	while(padded_len-iter>8);
	//L�nge der urspr�nglichen Nachricht in Bit als 8-Byte Big Endian Integer anh�ngen
	len_in_bits=8*len;
	byte_ctr=0;
	while(iter%64)
	{
		padded_string[iter]=(char)((len_in_bits>>(8*(7-byte_ctr)))&0x00000000000000ff);
		byte_ctr++;
		iter++;
	}

	return padded_string;
}
