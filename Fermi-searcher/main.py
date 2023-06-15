import requests
import os
import io

#le tue credenziali del fermi (base64) 
auth = 'tue credenziali'

def request_url(s):
	
	nomefile = s
	with requests.Session() as s:
		s = s.get("https://admin.fermi.mn.it/docenti/index.php?section=classi&version=2023-03-03", headers={'Authorization': auth})

	index = s.text.find("Elenco classi")

	str = s.text[index:]
	str = str[str.find("Genitori"):]
	str = str[str.find("<tr>"):]
	str = str[:str.find("</table")]
	buff = io.StringIO(str)
			
	#file contenente ogni classe e il relativo link 			
	f = open(nomefile, "w")

	for line in buff:
		url = line
		url = url[url.find("href=")+6:]
		url = url[:url.find("\">")]
		url = url.replace('amp;', '')
		
		classe = line 
		classe = classe[classe.find("\">")+2:]
		classe = classe[:classe.find("<")]
		
		f.write(classe)
		f.write("\n")
		f.write(url)
		f.write("\n")
		
	f.close()	

def request_classi(s1, s2):
	
	nomefile_url = s1
	nomefile_studenti = s2
	print("request classi")
	fr = open(nomefile_url, "r")
	#file contenente la lista degli studenti di ogni classe
	fw = open(nomefile_studenti, "w")
	
	lines = fr.readlines()
	#per ogni classe scrivo tutti gli studenti appartenenti
	for line in lines:
		if(line[0].isdigit()):
			#classe
			fw.write(line)
		else:
			#url
			with requests.Session() as s:
				url = "https://admin.fermi.mn.it/docenti/"
				url+=line
				url = url.strip()
				print(url)
				s = s.get(url, headers={'Authorization': auth})
				str = s.text
				str = str[str.find("Azioni"):]
				str = str[str.find("<tr>"):]
				str = str[:str.find("</table")]

				buff = io.StringIO(str)
				for row in buff:
					
					if(row.find("<tr><td rowspan=")==0):
						#riga con nome studente
						row = row[row.find("rowspan=")+1:]
						row = row[row.find("rowspan=")+1:]
						row = row[:row.find("</td>")]
						row = row[row.find(">")+1:]
						fw.write(row)
						fw.write("\n")
					elif(row[row.find("<tr><td>")+8].isdigit()):
						row = row[(row.find("</td>")+9):]
						row = row[:row.find("</td>")]
						fw.write(row)
						fw.write("\n")
						
	fw.close()
	fr.close()
				

#la funzione config_url crea un file accedendo al sito del fermi
#basta chimarla una volta, se il file è stato creato i il programma legge i dati da esso
#senza bisogno di connettersi al sito ogni volta
def config_url():

	nomefile = "url.txt"
	
	if os.path.exists(nomefile):
		print("file url già esistente, sovrascriverlo? y/n:")
		scelta = input()
		if scelta == "y":
			os.remove(nomefile)
			request_url(nomefile)
			return
		else:
			return
			
	request_url(nomefile)
	return
			
def config_classi():
	
	nomefile_url = "url.txt"
	if not os.path.exists(nomefile_url):
		print("file url non trovato, crearlo? y/n:")
		scelta = input()
		if scelta == "y":
			os.remove(nomefile)
			request_url(nomefile)
		else:
			return
	
	nomefile_studenti = "studenti.txt"
	if os.path.exists(nomefile_studenti):
		print("file studenti già esistente, sovrascriverlo? y/n:")
		scelta = input()
		if scelta == "y":
			os.remove(nomefile_studenti)
			request_classi(nomefile_url, nomefile_studenti)
			return
		else:
			return
	
	request_classi(nomefile_url, nomefile_studenti)
	return		
	
def config():
	
	config_url()
	config_classi()		
	return
	
def check_configf():
	nomefile_url = "url.txt"
	if not os.path.exists(nomefile_url):
		print("errore file URL, configurazione necessaria")
		return False
	
	nomefile_url = "studenti.txt"
	if not os.path.exists(nomefile_url):
		print("errore file STUDENTI, configurazione necessaria")
		return False
		
	return True
	
def studente():
	print("inserisci nome studente[cognome nome]:")
	print(">", end="")
	nome = input()
	nome = nome.upper()
	
	nomefile_studenti = "studenti.txt"
	fr = open(nomefile_studenti, "r")
	
	lines = fr.readlines()
	classe = ""
	trovato = False
	#per ogni classe
	for line in lines:
		if(line[0].isdigit()):
			#classe
			classe = line
		elif(line.find(nome) != -1):
				print("\nstudente: ", end="")
				print(line, end="")
				print("classe: ", end="")
				print(classe, end="")
				trovato = True
	
	fr.close()
	if(trovato == False):
		print("studente non trovato")
	return

print("*****[Ricerca studenti Fermi]*****\n")
print("\nricerca per: \n\n[1]classe \n[2]studente \n[3]config \n[0]esci\n\n")
print(">", end="")
ricerca = input()

valido = False
while (valido == False):

	if(ricerca == "1"):
		valido = check_configf()
		if(valido == True):
			classe()
	elif(ricerca == "2"):
		valido = check_configf()
		if(valido == True):
			studente()
	elif(ricerca == "3"):
		valido = True
		config()
	elif(ricerca == "0"):
		valido = True
	else:
		print("input non valido")
		print(">", end="")
		ricerca = input()
