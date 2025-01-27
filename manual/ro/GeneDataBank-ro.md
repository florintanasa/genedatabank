# Instalare

## Compilarea din surse în sistemul de operare Linux

Pentru instalarea aplicației din surse, într-un terminal din sistemul nostru sau printr-o 
conexiune la distanță, este necesară parcurgerea următorilor pași:  

* realizarea unui director denumit **git** și schimbarea căii de lucru în el:  

```bash
mkdir ~/git & cd ~/git
```
  
* clonarea surselor aferente aplicație, din https://github.com/florintanasa/genedatabank, în sistem:  
```bash
git clone https://github.com/florintanasa/genedatabank
```
  
* mutarea în directorul nou creat:  
```bash
cd genedatabank
```
  
* compilarea și realizarea fișierul binar:  
```bash
./gradlew -Pvaadin.productionMode=true bootJar
```
  
* fișierul binar (la momentul scrierii acestui manual este versiunea 0.2.0 a lui genedatabank) va fi realizat în 
directorul **build/libs**:  
```bash
ls build/libs/
genedatabank-0.2.0-SNAPSHOT.jar
```  
Pentru siguranța sistemului este recomandat ca aplicația să fie rulată de către un utilizator creat numai în acest scop,
neavând drept de logare și de acces la consolă.  
Acestea vor fi prezentate în următorul punct.

### Instalarea aplicației în sistemul de operare Linux
  
Pentru instalare este necesar accesul deplin la sistem ca utilizator **root** ori utilizatorul nostru să poată 
folosi comanda **sudo**. În continuare, este realizat (configurat) un utilizator cu denumirea  **genedatabank** cu 
directorul **_home_** în **/opt/genedatabank**:
```bash
sudo useradd -r -m -U -d /opt/genedatabank -s /bin/false genedatabank
```

Comanda de mai sus reprezintă:  

* **-r** va crea un utilizator în sistem  denumit **genedatabank**;
* **-U** va crea grupul denumit **genedatabank**;
* **-m** va crea directorul **_home_** dacă nu există;
* **-d** va seta directorul **_home_** în **/opt/genedatabank**;
* **-s** va seta ca și consolă de logare nimic **/bin/false**, utilizatorul va fi scos automat afară;  

După rularea comenzii de mai sus se poate copia aplicația noastră în directorul **_home_** al utilizatorului creat 
pentru acest scop:  
```bash
sudo cp ~/git/genedatabank/build/libs/genedatabank-0.2.0-SNAPSHOT.jar /opt/genedatabank/
```

După copiere, se impune schimbarea proprietarul fișierului binar pentru utilizatorul și grupul **genedatabank** nou 
creat, care va rula aplicația:  
```bash
sudo chown genedatabank:genedatabank /opt/genedatabank/genedatabank-0.2.0-SNAPSHOT.jar
```

### Pornirea serviciului GeneDataBank cu ajutorul unui manager de servicii  

În acest scop folosim *_systemd_* și pentru care realizăm un fișier de configurare:  
```bash
sudo nano /etc/systemd/system/genedatabank.service
```  
În care introduce următorul conținut:  
```text
[Unit]
Description=GeneDataBank java server application
# depend by network and postgres
Wants=network-online.target postgresql.service
# start after network  and postgres service start
After=network-online.target postgresql.service

[Service]
# run as user genedatabank (need to exist before)
User=genedatabank
# from group genedatabank (need to exist before)
Group=genedatabank
# restart always
Restart=always
# activation state
Type=simple
# working directory for user (in this case the home is /opt/genedatabank)
WorkingDirectory=/opt/genedatabank/
# execute command to start the application
ExecStart=/usr/lib/jvm/java-17-openjdk-amd64/bin/java \
    -Xmx3072m -server -XX:+UseShenandoahGC -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/tmp/heapdump.bin -jar \
    /opt/genedatabank/genedatabank.jar \
    --spring.profiles.active=production \
    --server.port=8090
# send stdout to syslog
StandardOutput=syslog
# send stderr to syslog
StandardError=syslog
# identifier for syslog
SyslogIdentifier=genedatabank

[Install]
WantedBy=multi-user.target
```
Instalăm pachetul **rsyslog**, dacă nu este instalat în sistem:  
```bash
 sudo apt install rsyslog
```
Pornirea serviciului va deschide portul 8090 pentru acces, iar serviciul va fi monitorizat de către **_syslog_**
într-un fișier **genedatabank.log** în directorul **/var/log**.  
Pentru acestea informăm **syslog** prin crearea unui fișier de configurare:  
```bash
sudo nano /etc/rsyslog.d/genedatabank.conf
```  
În care edităm următorul conținut:  
```text
if $programname == 'genedatabank' then /var/log/genedatabank.log
& stop
```
după care se realizează fișierul pentru log-uri:  
```bash
sudo touch /var/log/genedatabank.log
```
în continuare se va schimba proprietarul și grupul fișierului creat anterior:  
```bash
sudo chown syslog:adm /var/log/genedatabank.log
```
Pentru ca **syslog** să știe de noul fișier va trebui să repornim serviciul:  
```bash
sudo systemctl restart rsyslog
```
Pentru a nu modifica la fiecare update fișierul de configurare **genedatabank.service** de pornire a serviciului se va 
realiza o legătură simbolică la fișierul **genedatabank-0.2.0-SNAPSHOT.jar** denumită **genedatabank.jar**:  
```bash
sudo -u genedatabank ln -sf /opt/genedatabank/genedatabank-0.2.0-SNAPSHOT.jar /opt/genedatabank/genedatabank.jar
```
> **ATENȚIE!**  
> 
> Pentru a continua, va trebui să existe instalat și pornit serviciul bazei de date PostgreSQL, în care a fost creat  
> un utilizator **svgenebank** cu parola **geneit** și care va fi proprietarul bazei de date **genedatabank**  
> 
> ```postgresql
> create role svgenebank with login createdb password 'geneit';
> create database genedatabank owner svgenebank;
> ```
> Modificarea utilizatorului, parolei și a bazei de date se face în fișierul **src/main/resources/application.properties** 

În final, putem porni serviciul folosind comanda:  
```bash
sudo systemctl start genedatabank.service
```
După care se va verifica folosind comanda:  
```bash
systemctl status genedatabank.service
```
pentru a vedea dacă a pornit corect, iar pentru a vedea un log mai extins folosim:  
```text
sudo tail -F /var/log/genedatabank.log
```

Pentru ca serviciul să pornească automat, la pornirea calculatorului/serverului, folosim comanda:  
```bash
sudo systemctl enable genedatabank.service
```
> **INFO**  
> 
> La prima pornire a serviciului se va popula baza de date cu structura necesară și cu o parte din datele necesare. 


> **ATENȚIE**  
> 
> Pentru listarea de rapoarte, etichete, etc va trebui să existe instalat în sistem **Libreoffice** și configurată 
> calea către program, consultați fișierul **src/main/resources/application.properties**
>  

Pentru a lista fișierele din directorul **_home_**, al utilizatorului **_genedatabank_**, se va rula comnanda:
```bash
sudo -u genedatabank ls -lah /opt/genedatabank/
```
iar rezultat comenzii va fi de forma:

```txt
total 207M
drwxr-x--- 1 genedatabank genedatabank  256 ian 27 09:01 .
drwxr-xr-x 1 root         root          140 ian 23 10:22 ..
-rw-r--r-- 1 genedatabank genedatabank  220 mar 31  2024 .bash_logout
-rw-r--r-- 1 genedatabank genedatabank 3,7K mar 31  2024 .bashrc
drwxr-xr-x 1 genedatabank genedatabank  156 ian  8 07:29 .config
-rw-r--r-- 1 genedatabank genedatabank 4,0K sep 29  2022 .face
lrwxrwxrwx 1 genedatabank genedatabank   23 nov 25 19:15 .face.icon -> /opt/genedatabank/.face
-rw-r--r-- 1 genedatabank genedatabank 207M ian 22 13:16 genedatabank-0.2.0-SNAPSHOT.jar
lrwxrwxrwx 1 genedatabank genedatabank   49 ian 22 14:43 genedatabank.jar -> /opt/genedatabank/genedatabank-0.2.0-SNAPSHOT.jar
-rw-r--r-- 1 genedatabank genedatabank  140 nov 27  2021 .inputrc
drwxr-xr-x 1 genedatabank genedatabank    8 ian 22 14:48 .jmix
-rw-r--r-- 1 genedatabank genedatabank 1,7K apr  2  2024 .mkshrc
-rw-r--r-- 1 genedatabank genedatabank  807 mar 31  2024 .profile
-rw------- 1 genedatabank genedatabank   96 ian 27 09:01 .xauthr0OB0p

```

> **INFO**
> 
> În directorul **/opt/genedatabank/.jmix/work/filestorage** vor fi salvate fișierele încărcate în aplicație. În 
> baza de date vor fi salvate numai referințele generate în câmpul ID, care este cheie primară și este de tip UID. 
> Codul QR este generat în directorul **/opt/genedatabank/qrCodeImage**, directorul este creat automat, dacă nu există, 
> la prima generare de cod QR. Important de știut că în momentul în care este încărcat în aplicație fișierul cu imaginea
> codului QR va fi șters din acest director, practic va fi mutat în **/opt/genedatabank/.jmix/work/filestorage**.
> În directorul **/opt/genedatabank/.jmix/work/filestorage** informația (fișierele) sunt ținute în directoare pe ani, 
> luni și zile, de exemplu un fișier urcat în data de _25.01.2025_, va fi regăsit în locația: 
> **/opt/genedatabank/.jmix/work/filestorage/2025/01/25**


# Prima utilizare
Conectarea la server se realizează cu un browser web (de exemplu: chrome sau firefox) ca și client, folosind în bara de 
adrese IP-ul unde a fost pornit serviciul **genedatabank** și respectiv portul utilizat de acesta, în acest caz **8090**
, de exemplu http://localhost:8090/login, iar pentru autentificare inițială se va completa câmpurile pentru Utilizator 
**admin** și Parola **admin**:   
![Prima pornire](images/Prima_pornire_1.png)  
urmând a fi schimbată după logare din meniul _Aplicații_->_Utilizatori_  
completarea sau modificarea informațiilor, aferente unui utilizator existent, în acest caz **admin**, se realizează din
accesarea meniului _Aplicații_->_Utilizatori_ 
![Modificare info admin 1](images/Prima_pornire_2.png)  
după care se va deschide o nouă pagină a utilizatorului **admin** unde vom putea completa/modifica unele informații:  
![Modificare info admin 2](images/Prima_pornire_3.png)  
în acest caz am definit zona de timp.
>**INFO**
> 
> Pentru utilizatorul **admin** este de preferat de a nu-l înregistra într-o Instituție deoarece vizualizarea unor 
> informații vor fi accesibile pentru un anumit utilizator numai pentru acelea înregistrate de utilizatorii arondați
> unei anumite Instituție

Modificarea parolei unui utilizator se realizează din meniul _Aplicații_->_Utilizatori_->_Adițional_->_Schimbare parolă_:  
![Modificare parolă admin](images/Prima_pornire_4.png)  

## Definirea unor parametrii unici aferenți fiecărei Instituției ce utilizează baza de date  

La prima utilizare va trebui definit constantelor (seriilor) aferente fiecărei Instituție ce dorește a utiliza baza de date. 
Definirea acestora se va realiza din meniul _Setări_->_Administrative_->_Instituții_->_Adaugă_ sau _Modifică_, 
în imaginea de mai jos are loc vizualizarea unei Instituții existente ce are datele de identificare complete:  
![Vizualizarea seriilor 1](images/Prima_pornire_5.png)
> **ATENȚIE**
> 
> Unele câmpuri vor trebui să fie unice, ca de exemplu: **Cod**, **Serial pentru nr. de intrare**, **Serial pentru nr. 
> temporar de intrare**, **Serial VOS**, **Serial VNS**
> 
Câmpurile de mai sus reprezintă:  

* **Cod** - este codul aferent fiecărui Institut înregistrat la FAO;
* **Serial pentru nr. de intrare** - reprezintă seria codului unic acordat probelor aferente unui Institut;
* **Serial pentru nr. temporar de intrare** - reprezintă seria codului unic al probelor temporare aferente unui Institut;
* **Serial VOS** - reprezintă seria codului al determinării viabilității la semințele existente în depozit (Viability Old Seeds)
* **Serial VNS** - reprezintă seria codului al determinării viabilității la semințele noi intrate în Institut (Viability New Seeds)

![Vizualizarea seriilor 2](images/Prima_pornire_6.png)

> **INFO**
> 
> Câmpurile **Cheia api pentru Google Maps**, **Serial pentru nr. de intrare**, **Serial pentru nr.
> temporar de intrare**, **Serial VOS**, **Serial VNS** pot fi vizualizate și modificate numai de către utilizatorul
> **admin**, în plus, pentru o protecție la vizualizări accidentale de date sensibile, precum cheia api la Google Maps 
> acest câmp a fost mascat.  
> **Utilizatorii vor putea folosi numai cheia aferentă Instituției în care este arondat.**
> Utilizatorul **admin** _nu va putea folosi hărțile de la Google Maps_, deoarece nu este arondat unei instituții, în schimb
> va putea utiliza hărțile de la OpenStreetMaps, acestea fiind define a fi folosite implicit.

![Vizualizare cheie api Google MAps](images/Prima_pornire_7.png)

# Adăugarea unui utilizator
În vederea operării aplicației se vor putea înregistra **utilizatori** unici. Introducerea unui utilizator nou se 
realizează din meniul _Aplicații_->_Utilizatori_->_Adaugă:  

![Adăugare utilizator 1](images/Adaugare_utilizator_1.png)
  
Exemplu unui adăugării utilizator în imaginea de mai jos:  

![Adăugare utilizator 2](images/Adaugare_utilizator_2.png)

După ce apasă butonul **OK** utilizatorul va fi salvat și va apărea în listă:  

![Adăugare utilizator 3](images/Adaugare_utilizator_3.png)  

# Atribuirea rolurilor unui utilizator

Politica de acces la informații: meniuri, formulare, butoane, câmpuri etc. se realizează cu ajutorul rolurilor definite
prin cod sau prin configurarea unor roluri în baza de date.  
Pentru a atribui roluri se selectează utilizatorul dorit și se apăsa butonul _Afișare asignări roluri_, din meniul 
_Aplicații_->Utilizatori_:  

![Asignări roluri 1](images/Asignari_roluri_1.png)

Se va deschide o nouă pagină denumită **Atribuiri roluri pentru _Prenume Nume [utilizator]_ de unde putem atribui roluri 
pentru:
* **Permisiuni resursă**
  * drept de logare; 
  * acces la meniuri;
  * acces pentru introducere;
  * acces pentru vizualizare;
  * acces pentru ștergere;
  * etc.
* **Permisiuni nivel de rând**
  * acces la informațiile ce conține anumite informații
    * de exemplu la Probele care sunt declarate confidențiale;
  * etc.

De exemplu, prin apăsarea butonului **Permisiuni resursă** se va deschide o nouă fereastră, de unde se alege rolurile 
care sunt atribuite utilizatorului, ca în imaginea de mai jos:  

![Asignări roluri resursă](images/Asignari_roluri_2.png)

iar, prin apăsarea butonului **Permisiuni nivel de rând** se va deschide o altă fereastră, de unde se alege rolurile care
sunt atribuite utilizatorului, de exemplu **Institution and users** este rolul ce permite utilizatorului de a vedea 
numai înregistrările aferente **Institutului** din care face parte.

![Asignări roluri nivel de rând](images/Asignari_roluri_3.png)

În final pagina utilizatorului va cuprinde listele cu rolurile atribuite acestuia, ca în imaginea următoare:  

![Asignări roluri final](images/Asignari_roluri_4.png)

Listă ce va fi salvată prin apăsarea butonului **OK**.

# Pașaport

În meniul **Pașaport** se înregistrează probele ce vor intra în **Institut** spre păstrare, multiplicare, cercetare etc.  
O probă poate fi înregistrată cu număr temporar sau cu număr definitiv. Numerele sunt generate automat de către baza de 
date în sens crescător și sunt formate din :
* Pentru numerele definitive: **Serial pentru nr. de intrare** - **nr. de ordine**, de exemplu SVGB-1;
* Pentru numerele definitive: **Serial pentru nr. temporar de intrare** - **nr. de ordine**, de exemplu TSVGB-1;

seriile au fost atribuite anterior, la pasul **Definirea unor parametrii unici aferenți fiecărei Instituției ce utilizează
baza de date**.  
După accesul în aplicație, la adresei unde se află aplicația **GeneDataBank**, se deschide o pagină, ca mai jos:

![Accesare aplicație](images/Pasaport_1.png)

## Accesarea paginii Pașaport

Pagina **Pașaport** poate fi vizualizată din meniul _Aplicații_->_Pașaport_:

![Accesare pașaport](images/Pasaport_2.png)

## Introducerea unei probe

Pentru introducerea unei probe se vor urma următorii pași:  

* Se apasă butonul **Adaugă**:  

![Adăugare pașaport](images/Pasaport_3.png)

* Se va deschide o pagină cu câmpurile necesare a fi introduse:
![Adăugare pașaport - câmpuri](images/Pasaport_4.png)
în care sunt următoarele câmpuri:

  * **Institutul păstrător** - Reprezintă **Institutul** care va păstra proba. Se completează automat, deoarece este 
același cu **Institutul** din care face parte **utilizatorul**. Institutul păstrător este introdus, dacă acesta nu 
există, prin accesarea meniului _Setări_->_Administrative_->_Instituții_. Prin interogări se poate obține codul 
**INSTCODE** din descriptorii EURISCO;
  * **Număr de intrare temporar** - Este o căsuță de marcare, pentru cazul în care este bifată se va genera un număr de
intrare temporar. În cazul în care se dorește a introduce o probă cu număr definitiv nu se va bifa. După salvare, dacă proba a fost marcată 
ca fiind definitivă, căsuța va fi invalidată (nu se mai poate schimba starea), iar dacă proba a fost marcată temporară, 
căsuța va rămâne validă (se poate modifica starea). 
  * **Număr intrare** - Este numărul intrare definitiv atribuit probei și reprezintă câmpul **ACCENUMB** din 
descriptorii EURISCO;
  * **Număr temporar** - Este numărul intrare definitiv atribuit probei. Acesta va fi completat automat, cu numărul 
probei temporare, în momentul în care se scoate bifa de la **Număr de intrare temporar**. Astfel se va păstra și numărul
temporar al probei;
  * **PUID/DOI** - Identificator unic persistent (PUID) / Identificator digital de obiecte (DOI). Orice identificator 
persistent, unic, atribuit probei, astfel încât să poată fi referit fără ambiguitate la nivel global și informațiile 
asociate cu acesta sunt recoltate prin mijloace automate. Raportați câte un PUID pentru fiecare probă. 
Secretariatul Tratatului Internațional privind Resursele Fitogenetice pentru Alimentație și Agricultură (PGRFA) 
facilitează atribuirea unui identificator unic persistent (PUID), sub forma unui DOI (Identificator Digital de Obiecte),
către PGRFA la nivel de probă (http://www.planttreaty.org/doi). Băncile de gene care nu aplică un PUID adevărat 
aderărilor lor ar trebui să utilizeze și să solicite destinatarilor să utilizeze concatenarea descriptorilor INSTCODE 
(codul institutului), ACCENUMB (numărul de intrare )și GENUS (genul probei) ca identificator unic la nivel global, 
similar în cele mai multe privințe cu PUID ori de câte ori fac schimb de informații despre probe cu terți (de exemplu: 
ROM007:SVGB-20205:ZEA).
  * 

 

# Viabilitate semințe noi
Formularul este utilizat pentru vizualizarea și înregistrarea analizelor și testelor de viabilitate (germinare).

## Înregistrarea unei analize
Pentru înregistrarea unei noi analize se alege meniul **Viabilitate semințe noi** ca în figura de mai jos:  

![Viabilitate semințe noi](images/Viabilitate_1.jpg)

După alegerea meniului se va deschide următorul ecran:  

![Viabilitate semințe noi listă](images/Viabilitate_2.jpg)

Aici vom putea vizualiza, modifica și adăuga noi analize aferente unei noi probe sau eșantion fie el cu număr temporar sau nu.

### Adăugarea unei analize
Pentru a adăuga o nouă analiză se apasă butonul **Adaugă**, evidențiat în figura de mai jos:  

![Viabilitate semințe noi - **Adăugă**](images/Viabilitate_3.jpg)

moment în care se va deschide un nou ecran, prin care suntem informați, că este necesar alegerea probei de analizat după **Nr. de intrare**, câmpul fiind obligatoriu, ca în figura de mai jos: 

![Viabilitate semințe noi - **Alegere Nr. de intrare**](images/Viabilitate_4.jpg)

Celelalte câmpuri sunt de tip **numai citire**, deoarece vor fi completate automat, ca urmare a acțiunilor viitoare, ale utilizatorului conectat. De exemplu s-a ales proba cu numărul de intrare SVGB-1, pentru care dorim să realizăm analiza de viabilitate (germinație):

![Viabilitate semințe noi - **Nr. de intrare ales**](images/Viabilitate_5.jpg)

În cadrul fiecărei analize, aferentă unui **Nr. de intrare**, se vor adăuga teste, cu ajutorul butonului **Adaugă**, evidențiat mai jos:  

![Viabilitate semințe noi - **Adăugare teste noi**](images/Viabilitate_6.jpg)

moment în care se va deschide un nou ecran, ca în imaginea de mai jos:  

![Viabilitate semințe noi - **Adăugare test nou**](images/Viabilitate_7.png)

după cum se observă câmpul cu **Nr. de semințe testate** este obligatoriu. Câmpul acceptă numai numere întregi pozitive (mai mari ca zero). Câmpul **Data punerii la germinat** este obligatoriu și el, este de tip dată și se completează automat cu data curentă.

Pentru exemplu de față considerăm că la testare au fost puse la germinat 50 de semințe în dată 08/10/2024, iar **Data evaluării germinației** a fost 18/10/2024, ecranul nostru va fi completat ca mai jos:

![Viabilitate semințe noi - **Adăugare test nou** - completare câmpuri](images/Viabilitate_8.png)

Observăm că o parte din câmpuri sunt de tip **numai citire** și au fost completate automat:  

* **Viabilitate [%]** - reprezintă calculul în procente al viabilității, raportat la nr. de semințe puse la germinat;
*  **Durata germinației [zile]** - reprezintă durata de timp, între data punerii la germinat și data analizei germinației;

Câmpul **Durata tratamentului [zile]**, este de tip întreg pozitiv și se completează cu numărul de zile aferente unui eventual tratament, iar la câmpul **Observații** se poate detalia cu ceea ce considerăm necesar, ca de exemplu nr. și tipul de tratamente (fizice - pre-răcire, chimice - tratarea cu anumite substanței, etc.), câmpul este nelimitat la nr. de caractere.  

Pentru adăugare, se apasă butonul **OK**, iar testul nostru va fi adăugat în lista aferentă probei, cu **Nr. de intrare** SVGB-1.

![Viabilitate semințe noi - **Adăugare test nou** - ađăugare în listă](images/Viabilitate_9.jpg)

Pentru salvarea efectivă a testului, la analiza probei cu **Nr. de intrare** SVGB-1, va trebui să apăsăm butonul **OK**, ca mai jos:  

![Viabilitate semințe noi - **Adăugare test nou** - salvare la analiză](images/Viabilitate_10.jpg)

Înainte de salvare analiza va primi o serie și un număr, ca în imaginea de mai jos:  
  
![Viabilitate seminței noi - **Adăugare analiză**](images/Viabilitate_11.jpg)

Câmpurile aferente unei analize sunt:  
  
* **Nr. analizei** - format din serie și număr, se completează și se indexează automat;
* **Număr de intrare** - reprezintă numărul de intrare aferent probei (eșantionului) supus determinării viabilității;
* **Anul testării** - reprezintă anul în care proba a fost supusă determinării viabilității, se completează automat cu anul curent; 
* **Viabilitatea medie [%]** - reprezintă viabilitatea medie, în [%], pentru calcul se utilizează toate testele din lista aferentă analizei;
* **Stare** - reprezintă starea în care se află analiza **În lucru** sau **Finalizat**; 

> **Atenție**
  >
> **Viabilitatea medie [%]** ține cont de toate testele din cadrul analizei. În cazul în care există un test aflat în desfășurare acesta va influență valoarea viabilității deoarece se consideră viabilitatea minimă posibilă **0** și este luat în calcul, ca în figura de mai jos:

![Viabilitate semințe noi - **Adăugare test nou** - parțial](images/Viabilitate_12.jpg)

După cum se observă introducerea parțială a unui nou test va influența calculul valorii medii a viabilității.  

Pentru protecție butonul **Finalizare** este invalidat dacă există cel puțin un test, aferent unei analize, pentru care nu s-a calculat viabilitatea, ca în figura de mai jos:  

![Viabilitate semințe noi - **Adăugare analiză** - cu test parțial](images/Viabilitate_13.jpg)

### Finalizarea unei analize

Pentru a finaliza o analiză, va trebui să avem toate testele din cadrul ei complete, astfel la testul de mai sus va fi necesar a completa cu evaluare germinației, apăsând butonul **Modifică** și vom intra în lista cu teste, unde vom alege testul nostru parțial, urmând a fi completat, ca în figura de mai jos: 

![Viabilitate semințe noi - **Adăugare analiză** - cu teste completate](images/Viabilitate_14.jpg)

După cum se observă, se realizează iarăși calcul viabilității medii, dar de această dată folosind și contribuția viabilității testului/repetiției 2.

La o analiză, se pot realiza câte teste sau repetiții se consideră a fi necesare, la calcularea viabilității medii se va ține cont de toate.

Cu toate testele finalizate, programul va activa butonul **Finalizare**, ca în figura de mai jos:  

![Viabilitate semințe noi - **Adăugare analiză** - activare buton **Finalizare**](images/Viabilitate_15.jpg)

> **Atenție** 
> Un utilizator nu poate **Finaliza** analiza **În lucru**, chiar dacă este completă al altui utilizator și nici nu o poate modifică. În schimb este posibilă vizualizarea.  

![Viabilitate semințe noi - **Adăugare analiză** - inactivare buton **Finalizare** și activare **Modificare**](images/Viabilitate_16.jpg)

în cazul de mai sus, analiza **ROM007_VNS-9** aparține altui utilizator.  

După apăsarea butonului **Finalizare**, se va schimba automat **Starea** în **Finalizat** , ca în imaginea de mai jos:  

![Viabilitate semințe noi - **Adăugare analiză** - avertizare](images/Viabilitate_17.png)

 inițial, vom fi avertizați că marcăm ca **Finalizat** analiza, iar dacă suntem de acord și apăsăm butonul **OK**  atunci analiza va fi marcată ca și **Finalizat**, iar analiza nu va mai putea fi modificată, dar va putea fi vizualizată, ca în imaginea de mai jos:  

![Viabilitate semințe noi - **Adăugare analiză** - marcată **Finalizat**](images/Viabilitate_18.jpg)



